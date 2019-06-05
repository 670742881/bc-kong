package com.bigdata.bokong.offline

import com.bigdata.bokong.common.LogUtils
import org.apache.spark.{SparkConf, SparkContext}

/**
  * @created by imp ON 2019/4/2
  */
object DmStat {
  def main(args: Array[String]): Unit = {
    val sparkConf = new SparkConf()
    sparkConf.setAppName(s"${this.getClass.getSimpleName}")
    sparkConf.setMaster("local[6]")
    // RDD 序列化到磁盘 worker与worker之间的数据传输
    sparkConf.set("spark.serializer", "org.apache.spark.serializer.KryoSerializer")


    val sc = new SparkContext(sparkConf)
    sc.setLogLevel("ERROR")

    //
    //    val a = sc.textFile("file:///E:\\MyProject\\bc-kong\\data\\test.info")
    //      .map(line => line.split("\t")).map(arr => (0+arr(2)+"_"+(arr(7)),"有")).collect().toMap

    //    val a = sc.textFile("file:///E:\\MyProject\\bc-kong\\data\\dm_log.txt")
    //      .map(line => line.split("\t")).
    //      map(arr => {
    //        val code = "0" + arr(1)
    //        val ctype = arr(7)
    //        if (ctype == "UGC来电秀") {
    //          (code, "callShow")
    //        } else if (ctype == "本地趣拍") {
    //          (code, "videoClips")
    //        } else if (ctype == "香港UGC视频" |ctype=="本地UGC视频") {
    //          (code, "ugcVideoPart")
    //        } else {
    //          (code, ctype)
    //        }
    //      }).map((_, 1))


    val a = sc.textFile("file:///C:\\Users\\imp\\Desktop\\pyspark\\dm_log.txt")
      .map(line => line.split("\t")).
      map(arr => ("0" + arr(0), 1))

    println(a)
    //    println(a.map(_._2).sum())
    //    a.saveAsTextFile("file:///E:\\\\MyProject\\\\bc-kong\\\\data\\\\aa")
    //    val broadcast = sc.broadcast(a)

    //    val text1 = sc.textFile("file:///C:\\Users\\imp\\Desktop\\ba.txt", 6)
    //    val b = LogUtils.logParse(text1)
    //      .filter(map => map.contains("en") && map("en") == "e_dm" && map("status").toInt > 8)
    //      .map(map => ((map("c_code"), 1))).cache()
    //    println("bb=="+b.count()+"===========aa"+a.count())

    val text1 = sc.textFile("/flume/nginxlogs/2019/05/09/access_logs.1557331202490.tmp", 6)
    val b = LogUtils.logParse(text1)
      //      .filter(map => map.contains("en") && map("en") == "e_dm" && map("status").toInt != 8&& map("c_type_name").equals("videoClips") ).cache()
      .filter(map => map.contains("en") && map("en") .equals("e_dm") ).
      filter(map=>map.get("bc_is_review").equals(Some("0"))||map.get("bc_is_review")==None)
    println(b.count())
    b.coalesce(1).saveAsTextFile("file:///E:\\\\\\\\MyProject\\\\\\\\bc-kong\\\\\\\\data\\\\\\\\check5")

    //    val c = a.leftOuterJoin(b, 6)
    //      .filter(i => i._2._2 == None).coalesce(1)
    //    ////    val c =b.map(function(_))
    //    //
    //    c.foreach(println(_))
    //    c.saveAsTextFile("file:///E:\\\\MyProject\\\\bc-kong\\\\data\\\\check1")
    ////    c.map(i=>(i._1._2,1)).reduceByKey(_+_).saveAsTextFile("file:///E:\\\\MyProject\\\\bc-kong\\\\data\\\\check23")


  }


}
