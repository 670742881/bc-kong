package com.bigdata.bokong.offline

import com.bigdata.bokong.common.LogUtils
import org.apache.spark.{SparkConf, SparkContext}

/**
  * @created by imp ON 2019/3/21
  */
object OffLineStat {
  def main(args: Array[String]): Unit = {
    if (args.length != 2) {
      println(
        """
          |com.bigdata.bokong.offline.OffLineStat
          |参数：
          | 输入路径
          |
          | 输出路径
        """.stripMargin)
      sys.exit()
    }

    val Array(inputPath, outputPath) = args


    // 2 创建sparkconf->sparkContext
    val sparkConf = new SparkConf()
    sparkConf.setAppName(s"${this.getClass.getSimpleName}")
    sparkConf.setMaster("local[4]")
    // RDD 序列化到磁盘 worker与worker之间的数据传输
    sparkConf.set("spark.serializer", "org.apache.spark.serializer.KryoSerializer")


    val sc = new SparkContext(sparkConf)
    sc.setLogLevel("ERROR")

    val text = sc.textFile("data/text")
    val rdd = LogUtils.logParse(text)
      .filter(map => map.contains("en") && (map("en") == "e_st" || map("en") == "e_pv" || map("en") == "e_la")).cache()

    val uv = rdd.map(map => {
      val day = map("s_time").substring(0, 8)
      val uuid = map.getOrElse("uuid", " ")
      val hour = map("s_time").substring(0, 10)
      //      val ip=map.getOrElse("ip"," ")
     (day,uuid,hour)
    }).distinct()

     val day_uv=uv.map(t=>(t._1,1)).reduceByKey(_+_)
    day_uv.foreach(print(_))
    val hour_uv=uv.map(t=>(t._3,1)).reduceByKey(_+_)
    hour_uv.foreach(print(_))



    val pv = rdd.map(map => {
      val day = map("s_time").substring(0, 8)

      val hour = map("s_time").substring(0, 10)
      (day, hour)
    })
    val day_pv=pv.map(t=>(t._1,1)).reduceByKey(_+_)
    day_pv .foreach(print(_))
    val hour_pv=pv.map(t=>(t._2,1)).reduceByKey(_+_)
    hour_pv.foreach(print(_))


    val ip = rdd.map(map => {
      val day = map("s_time").substring(0, 8)
      val hour = map("s_time").substring(0, 10)
      val ip = map.getOrElse("ip", "un")
      //      val ip=map.getOrElse("ip"," ")
      (day,ip,hour)
    }).distinct()

    val day_ip=ip.map(t=>(t._1,1)).reduceByKey(_+_)
    day_ip .foreach(print(_))
    val hour_ip=ip.map(t=>(t._3,1)).reduceByKey(_+_)
    hour_ip.foreach(print(_))

     val day_pv_uv_ip=day_pv.join(day_uv).join(day_ip).coalesce(1)
       .saveAsTextFile("data/day_stat")
    val hour_pv_uv_ip=hour_pv.join(hour_uv).join(hour_ip).coalesce(1)
      .saveAsTextFile("data/hour_stat")



//独立ip第二种算法
val ip2 = rdd.map(map => {
  val day = map("s_time").substring(0, 8)
  val hour = map("s_time").substring(0, 10)
  val ip = map.getOrElse("ip", "un")
  (day,ip,hour)
})
    val day_ip2 =ip2.map(t=>(t._1,t._2)).groupByKey()
      .map{
        case(day,list)=>{
          (day,list.toList.distinct.size)
        }

      }.sortByKey(false).take(10)
    println("第二种方法")
   day_ip2.foreach(println(_))
    val hour_ip2 =ip2.map(t=>(t._3,t._2)).groupByKey()
      .map{
        case(day,list)=>{
          (day,list.toList.distinct.size)
        }

      }.sortByKey(false).take(10)
    hour_ip2.foreach(println(_))


  }


}
