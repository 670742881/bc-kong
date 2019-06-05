package samples

import com.bigdata.bokong.offline.JsonUtil
import org.apache.spark.rdd.RDD
import org.apache.spark.sql.SparkSession


/**
  * @created by imp ON 2019/4/1
  */
object BroadCastDemo {
  def main(args: Array[String]): Unit = {

    val sparks = SparkSession.builder.master("local[4]").appName("test1").getOrCreate
    val sc = sparks.sparkContext

    //    val dictMap = sc.textFile("file:///E:\\MyProject\\bc-kong\\data\\provenceInfo")
    //      .map(i => i.split("\\|")).map(i => (i(0), i(1))).collect().toMap
    //
    //
    //    val broadcast: Broadcast[Map[String, String]] = sc.broadcast(dictMap)
    //
    //    val data2 = sc.textFile("file:///E:\\MyProject\\bc-kong\\data\\provenceInfo").map(i => i.split("\\|")).map(i => (i(0), i(1)))
    //
    //  println(data2.partitions)
    //    println(data2.partitioner)
    //  val res: RDD[(String, String, String)] =  data2.map(f = log => {
    //    val id = log._1
    //    var value1="111"
    //    if (!StringUtils.isBlank(id)) {
    //      value1 = broadcast.value(id)
    //
    //    }
    //    (id, log._2, value1)
    //  })
    ////   res.collect().foreach(println(_))
    //
    //
    //
    //    def function(tuple: (String,String)): (String,(String,String)) ={
    //      for(value <- broadcast.value){
    //        if(value._1.equals(tuple._1))
    //          return (tuple._1,(tuple._2,value._2.toString))
    //      }
    //      (tuple._1,(tuple._2,null))
    //    }
    //
    //    // 在rdd1.map算子中，可以从rdd2DataBroadcast中，获取rdd2的所有数据。
    //    // 然后进行遍历，如果发现rdd2中某条数据的key与rdd1的当前数据的key是相同的，那么就判定可以进行join。
    //    // 此时就可以根据自己需要的方式，将rdd1当前数据与rdd2中可以连接的数据，拼接在一起（String或Tuple）。
    //    val rdd3 = data2.map(function(_))
    //
    //    rdd3.collect().foreach(println(_))


    val path = "file:///C:\\Users\\imp\\Desktop\\b.txt"

    val aicheck_rdd: RDD[(String, String, String, String, String, String)] = sc.textFile(path).filter(_.contains("aicheck")).repartition(10)
      .mapPartitions(rdd => {
        val jsonUtil = new JsonUtil()
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


      })

    aicheck_rdd.foreach(println(_))


  }
}