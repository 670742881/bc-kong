package com.bigdata.bokong

import com.bigdata.bokong.common.LogUtils
import com.bigdata.bokong.utils.JedisUtil
import com.typesafe.config.ConfigFactory
import kafka.common.TopicAndPartition
import kafka.message.MessageAndMetadata
import kafka.serializer.StringDecoder
import org.apache.spark.SparkConf
import org.apache.spark.rdd.RDD
import org.apache.spark.sql.SparkSession
import org.apache.spark.streaming.kafka.{HasOffsetRanges, KafkaCluster, KafkaUtils}
import org.apache.spark.streaming.{Seconds, StreamingContext}
import scalikejdbc._
import scalikejdbc.config.DBs

import scala.collection.mutable

/**
  * @created by imp ON 2019/4/2
  */
object DmRealStat {

  def main(args: Array[String]): Unit = {
    /**
      *  1.集成kafka进行数据进行数据读取
      * 程序第一次启动从数据库获取偏移量,开始读取
      */

    val sparkConf = new SparkConf().setMaster("local[4]").setAppName("实时监控")
    //开启背压  开启后spark自动根据系统负载选择最优消费速率
    sparkConf.set("spark.streaming.backpressure.enabled", "true")
    //spark.streaming.backpressure.initialRate （整数） 默认直接读取所有
    sparkConf.set(" spark.streaming.backpressure.initialRate", "1000")
    //（4）限制每秒每个消费线程读取每个kafka分区最大的数据量 （整数） 默认直接读取所有
    sparkConf.set(" spark.streaming.kafka.maxRatePerPartition ", "500")
    sparkConf.set("spark.streaming.stopGracefullyOnShutdown", "true")
    //    sparkConf.set("spark.driver.memory","2G")
    val ssc = new StreamingContext(sparkConf, Seconds(2))
    val sc = ssc.sparkContext


    //sparksql
    val spark = SparkSession.builder().config(sparkConf).enableHiveSupport().getOrCreate()

    //程序第一次启动,无偏移量
    /*

    def createDirectStream[
    K: ClassTag,     key的类型
    V: ClassTag,     value的类型
    KD <: Decoder[K]: ClassTag,
    VD <: Decoder[V]: ClassTag] (
      ssc: StreamingContext,
      kafkaParams: Map[String, String],
      topics: Set[String]
  ): InputDStream[(K, V)] = {
    val messageHandler = (mmd: MessageAndMetadata[K, V]) => (mmd.key, mmd.message)
    val kc = new KafkaCluster(kafkaParams)
    val fromOffsets = getFromOffsets(kc, kafkaParams, topics)
    new DirectKafkaInputDStream[K, V, KD, VD, (K, V)](
      ssc, kafkaParams, fromOffsets, messageHandler)
  }
     */
    val conf = ConfigFactory.load()
    val brokers = conf.getString("kafka.broker.list")
    val topic = conf.getString("kafka.topic")
    val groupid = "11"
    val kafkaParams = Map(
      "metadata.broker.list" -> brokers,
      "auto.offset.reset" -> "smallest",
      "group.id" -> groupid
    )



    //加载配置信息 默认加载default.jdbc 如需设置生产环境 scalajdbcTest
    DBs.setup()
    val fromOffsets: Map[TopicAndPartition, Long] = DB.readOnly { implicit session =>
      sql"select topic,partitions,offset from stream_offset where groupid=? and topic=? and brokerlist=?".bind(groupid, topic, brokers).map(rs => {
        (TopicAndPartition(rs.get[String]("topic"), rs.get[Int]("partitions")), rs.long("offset"))
      }).list().apply()
    }.toMap

    val topics = Set(topic)

    val stream = if (fromOffsets.size == 0) {
      //     程序第一次启动
      KafkaUtils.createDirectStream[String, String, StringDecoder, StringDecoder](ssc, kafkaParams, topics)
    }
    else {
      //程序非第一次启动
      var checkOffset = Map[TopicAndPartition, Long]()
      //思考:kafka默认的保存数据是7天 但在过程中在没有启动过消费者 ,保存的offset是过期的偏移量 所以
      //        必须查询偏移量与当前有效的最早的偏移量进行比较 如果保存的比当前的小,说明过期了

      val kafkaCluste = new KafkaCluster(kafkaParams);
      //传进去TopicAndPartition
      val earliestLeaderOffsets = kafkaCluste.getEarliestLeaderOffsets(fromOffsets.keySet)
      if (earliestLeaderOffsets.isRight) {
        //得到了分区和对应的偏移量
        val topicAndOffset: Map[TopicAndPartition, KafkaCluster.LeaderOffset] = earliestLeaderOffsets.right.get
        checkOffset = fromOffsets.map(selectOffset => {
          //拿到当前集群的分区 最早偏移量
          val currentOffset = topicAndOffset.get(selectOffset._1).get.offset
          if (selectOffset._2 >= currentOffset) {
            //数据库的大于当前集群的 就使用数据库offfset
            selectOffset
          } else {
            (selectOffset._1, currentOffset)
          }

        })
        checkOffset
      }
      //此处从数据库获取偏移量 ,程序启动从此处开始往后消费
      val messageHandler = (mm: MessageAndMetadata[String, String]) => {
        (mm.key(), mm.message())
      }
      KafkaUtils.createDirectStream[String, String, StringDecoder, StringDecoder, (String, String)](ssc, kafkaParams, checkOffset, messageHandler)

    }

    //2.处理数据
    stream
      .foreachRDD(kafkardd => {
        //           val a: RDD[(String, String)] =kafkardd
        val mapdata = LogUtils.logParse(kafkardd.map(_._2)).filter(log => log.contains("en") && log("en") == "e_dm")


        mapdata.foreach(println(_))
        var minute = ""
        //2实时进行审核信息统计

          //看一下偏移量
          //3.自主管理偏移量存入redis/或者mysql
          val offsetRanges = kafkardd.asInstanceOf[HasOffsetRanges].offsetRanges
          offsetRanges.foreach(offsetRange => {
            DB.autoCommit(implicit session =>
              sql"replace into stream_offset(topic,partitions,groupid,brokerlist,offset)values (?,?,?,?,?)".bind(
                offsetRange.topic,
                offsetRange.partition,
                groupid,
                brokers,
                offsetRange.untilOffset
              ).update().apply()
            )

            println("topic:" + offsetRange.topic + "分区:" + offsetRange.partition + "开始消费" + offsetRange.fromOffset + "消费到" + offsetRange.untilOffset + "共计" + offsetRange.count())
          }


          )
        })

        ssc.start()
        ssc.awaitTermination()
      }


 def dongmanStat(mapdata:RDD[mutable.Map[String,String]]): Unit ={
   val baseData = mapdata.filter(map => map.contains("c_type_name") && map.contains("status")).map(_map => {
     val baseData = mapdata.map(_map => {
       //          String contId = _map.get("c_id");
       //          String cpId = _map.get("cp_id");
       //          String contTypeName = _map.get("c_type_name");
       //          String status = _map.get("status");
       //          String duration = _map.get("dura");
       //          String operator = _map.get("operator");
       //          String bcTime = _map.get("bc_time");
       val minute = _map("s_time").substring(0, 12)
       val day = _map("s_time").substring(0, 8)
       val c_type = _map("c_type_name");
       val progId = _map("cp_id");
       val bcTotal = if (_map("status").toInt >= 8) 1 else 0
       val receive = if (_map("status").toInt == 8) 1 else 0
       val waitingBc = if (_map("status").toInt == 8) 1 else 0
       val bcPerson = _map.getOrElse("operator", " ");
       val syncTime = _map.getOrElse("sync_time", "");
       //             val srcLog = _map.getOrElse("src_log");
       //             val isDel = _map.getOrElse("is_delete",0)
       //             val isBcReview = _map.getOrElse("is_bc_review","")
       (day, c_type, progId, bcPerson, syncTime, List[Int](bcTotal, receive, waitingBc))
     })


     //        //内容统计
     //        val contBcStat = baseData.map {
     //          case (day, contId, progId, bcPerson, syncTime, list) => {
     //            ((day, contId), list)
     //          }
     //        }.distinct().reduceByKey((list1, list2) => {
     //          list1.zip(list2).map(i => {
     //            i._1 + i._2
     //          })
     //        }).foreachPartition(rdd => {
     //          val jedis = JedisUtil.getJedisClient()
     //          rdd.foreach(data => {
     //            val key: String = "cidStat" + "_" + data._1._1
     //            val a = jedis.hincrBy(key, "bcTotal", data._2(0))
     //            if (a > 0) println("自增成功") else println("自增失败")
     //            jedis.hincrBy(key, "receive", data._2(1))
     //            jedis.hincrBy(key, "waitingBc", data._2(2) - data._2(0))
     //          })
     //          jedis.close()
     //        })


     //播控人内容统计 如果是相同的内容播控 条数去重
     val bcPersonStat = baseData.map(t => ((t._1, t._4, t._2))).distinct()
       //             .updateStateByKey[Long]((seq: Seq[Int], state: Option[Long]) => {
       //             //seq:Seq[Long] 当前批次中每个相同key的value组成的Seq
       //             val currentValue = seq.sum
       //             //state:Option[Long] 代表当前批次之前的所有批次的累计的结果，val对于wordcount而言就是先前所有批次中相同单词出现的总次数
       //             val preValue = state.getOrElse(0L)
       //             Some(currentValue + preValue)
       //           })
       .map(t => ((t._1, t._2), 1))
       .reduceByKey(_ + _)

       .foreachPartition(rdd => {
         val jedis = JedisUtil.getJedisClient()
         rdd.foreach(data => {
           val key: String = data._1._1 + "_" + data._1._2
           jedis.hincrBy(key, "bcPersonStat", data._2.toLong)
         })
         //不释放的 会发生线程阻塞 无法进行数据插入
         jedis.close()
       })
   })
 }

}




