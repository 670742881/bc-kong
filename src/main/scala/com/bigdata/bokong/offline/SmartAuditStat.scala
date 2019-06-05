package com.bigdata.bokong.offline

import java.sql.{Connection, DriverManager}

import com.bigdata.bokong.common.LogUtils
import com.typesafe.config.ConfigFactory
import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

/**
  * @created by imp ON 2019/4/9
  */
object SmartAuditStat {

  def main(args: Array[String]): Unit = {
////    if (args.length != 2) {
////      println(
////        """
////          |com.bigdata.bokong.offline.OffLineStat
////          |参数：
////          | 输入路径
////          |
////          | 分区数
////        """.stripMargin)
////      sys.exit()
//    }

   val Array(inputPath, outputPath) = args


//    val path="/flume/nginxlogs/2019/03/12/access_logs.1552320001371"
// val path=   "file:///C:\\Users\\imp\\Desktop\\b.txt"


    val sparkConf = new SparkConf()
    sparkConf.setAppName(s"${this.getClass.getSimpleName}")
    sparkConf.setMaster("local[4]")
    // RDD 序列化到磁盘 worker与worker之间的数据传输
    sparkConf.set("spark.serializer", "org.apache.spark.serializer.KryoSerializer")
    val sc = new SparkContext(sparkConf)
    sc.setLogLevel("ERROR")

    val text = sc.textFile(inputPath).filter(_.contains("en=e_sx"))
    val sx_map = LogUtils.logParse(text)
      .filter(map => map("bc_status").eq("10") || map("bc_status").eq("11"))
      .map(map => (map("c_id"), map("bc_status"))).collect().toMap

    //将缓存的map集合广播出去
    val broadcast = sc.broadcast(sx_map)

    val aicheck_rdd: RDD[((String, String), List[Int])] = sc.textFile(inputPath).filter(_.contains("aicheck")).repartition(10)
      .mapPartitions(rdd => {
        val jsonUtil= new JsonUtil()
        rdd.map(i => {
          val aicheck = jsonUtil.jsonParse(i)
          if (aicheck.getString("msgType").equals("1")) {
            jsonUtil.msgTypeToRedis(aicheck)
            ("1", "1", "1", "1", "1", "1")
          }else {
            val map = jsonUtil.returnData(aicheck)
            val pContId = map.get("pContId")
            val auditor = map.get("auditor")
            val day = map.get("day")
            val terrorism = map.get("terrorism")
            val porn = map.get("porn")
            val ad = map.get("ad")
            (day, auditor, pContId, terrorism, porn, ad)
          }
        })
      }).filter(!_._1.eq("1")).map{
      case (day, auditor, pContId, terrorism, porn, ad) => {
        //sendNum", "passNum", "refuseNum", "suggestNum", "exclusivenessNum
        //        val key = day + "-" + auditor
        val sendNum = if (pContId != null) 1 else 0
        val passNum = if ("pass".equals(terrorism) && "pass".equals(porn) && "pass".equals(ad)) 1 else 0
        val refuseNum = if ("review" == terrorism || "review" == porn || "review" == ad || "block" == terrorism || "block" == porn || "block" == ad) 1 else 0
        val suggestNum = if ("review".equals(terrorism) || "review".equals(porn) || "review".equals(ad)) 1 else 0

        //互斥条数
        //从bc中获取 缓存的
        val passValue = broadcast.value.getOrElse(pContId, "0")
        val refuseValue = broadcast.value.getOrElse(pContId, "0")
        //存入redis的状态为6的key 二审通过的  与这边的数据互斥  false
        val exclusivenessNum = if (passValue != null && "10".equals(passValue) && refuseNum == 1) 1
        else if (refuseValue != null && "11".equals(refuseValue) && passNum == 1) 1 else 0
        val list = List[Int](sendNum, passNum, refuseNum, suggestNum, exclusivenessNum)
        ((day, auditor), list)
      }
    }

    val result: RDD[((String, String), List[Int])] = aicheck_rdd.reduceByKey((list1, list2) => {
      list1.zip(list2).map(i => i._1 + i._2)
    }, 10)


    result.zipWithIndex()
//    result.coalesce(1).saveAsTextFile("file:///E:\\MyProject\\bc-kong\\data\\smartstat")

    //存入mysql
    result.foreachPartition(f = partitionOfRecords => {
      if (partitionOfRecords.isEmpty) {

        println("This RDD is not null but partition is null")
      } else {
        val conf = ConfigFactory.load()
        val url = conf.getString("bigdata.url")
        val username = conf.getString("bigdata.username")
        val password = conf.getString("bigdata.password")
        Class.forName(conf.getString("bigdata.driver"))
        var connection: Connection = null
        try {
          connection = DriverManager.getConnection(url, username, password)
          connection.setAutoCommit(false)
          val sql = conf.getString("spark.sql")

          val deleteSql="delete * from smartAuditor where statday=?"
         val pstmt2= connection.prepareStatement(deleteSql)

          val pstmt = connection.prepareStatement(sql)
          var count = 0
          //sendNum, passNum, refuseNum, suggestNum, exclusivenessNum
          partitionOfRecords.foreach(record => {
            pstmt.setString(1, record._1._1)
            pstmt.setString(2, record._1._2)
            pstmt.setInt(3, record._2(0))
            pstmt.setInt(4, record._2(1))
            pstmt.setInt(5, record._2(2))
            pstmt.setInt(6, record._2(3))
            pstmt.setInt(7, record._2(4))
            pstmt.addBatch()
            count += 1
            if (count % 100 == 0) {
              pstmt.executeBatch()
              connection.commit()
            }
          })
          pstmt.execute()
          connection.commit()
        } finally {

        }
        connection.close()
      }
    })
  }


}





