package com.bigdata.bokong.offline

import com.bigdata.bokong.common.LogUtils
import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

import scala.collection.mutable

/**
  * @created by imp ON 2019/3/29
  */
object OffLineShiXunStat {
  def main(args: Array[String]): Unit = {
    //    if (args.length != 2) {
    //      println(
    //        """
    //          |com.bigdata.bokong.offline.OffLineStat
    //          |参数：
    //          | 输入路径
    //          |
    //          | 输出路径
    //        """.stripMargin)
    //      sys.exit()
    //    }
    //
    //    val Array(inputPath, outputPath) = args
    //

    // 2 创建sparkconf->sparkContext
    val sparkConf = new SparkConf()
    sparkConf.setAppName(s"${this.getClass.getSimpleName}")
    sparkConf.setMaster("local[4]")
    // RDD 序列化到磁盘 worker与worker之间的数据传输
    sparkConf.set("spark.serializer", "org.apache.spark.serializer.KryoSerializer")


    val sc = new SparkContext(sparkConf)
    sc.setLogLevel("ERROR")

    val text = sc.textFile("hdfs://hadoop01:8020/flume/nginxlogs/2019/04/01", 10)
 val res: RDD[mutable.Map[String, String]] =   LogUtils.logParse(text)
   .filter(map => map.contains("en")&&map("en")=="e_dm"&&map("status").toInt>8)
//        .map(map=>{
//        val status=if(map("status")=="10")2 else 1
//          val bc_people=map("operator")
//          val c_type_name=map("c_type_name")
//          val dura=map("dura")
//          val cid=map("c_id")
//        })


    //    val uv = rdd.map(map => {
    ////      val day = map("s_time").substring(0, 8)
    //     val amt=if(map("status").toInt>8) 1 else 0
    //      val minute = map("s_time").substring(0, 12)
    //      (minute,amt)
    //    }).reduceByKey(_+_,10).coalesce(1)
    ////    rdd.foreach(println(_))
   res.foreach(println(_))
//    val a: Long = rdd.count()
//    println(a)
//   res.saveAsTextFile("/data/dm")
  }
}
