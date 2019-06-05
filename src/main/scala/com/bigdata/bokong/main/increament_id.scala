package com.bigdata.bokong.main


/**
  * @created by imp ON 2019/3/18
  */
object increament_id {
  def main(args: Array[String]): Unit = {
    import org.apache.spark.sql.SparkSession
    val sparks = SparkSession.builder.master("local[4]").appName("test1").getOrCreate
    val sc = sparks.sparkContext




    case  class Log(map:scala.collection.mutable.Map[String,String],ID: Long)
    import sparks.implicits._
  val data2 =  sc.parallelize(Seq((Map("uuid"->"sxexx","ip"->"192.168")),Map("uuid"->"man","ip"->"192.168.10.1"))).zipWithIndex()
    .map(i=>(i._1,i._2))
    data2.collect().foreach(print(_))
    /**
      * 先创造一个Rdd[map] 使用zipWithIndex 看看效果  第二个元素为id主键
      *
      *
      * (Map(uuid -> sxexx, ip -> 192.168),0)
      * (Map(uuid -> man, ip -> 192.168.10.1),1)
      */



    val data=  sc.textFile("file:///C:/Users/imp/Desktop/bo-kong/data/data")
      .zipWithIndex().toDF("id","value")
    data.show()

    /**
      * 使用数据的得出结果
      * +---+-----+
      * | id|value|
      * +---+-----+
      * | aa|    0|
      * | bb|    1|
      * | cc|    2|
      * | dd|    3|
      * | ee|    4|
      * | ff|    5|
      * +---+-----+
      */











    //    data2.createOrReplaceTempView("data")
//    sparks.sql("select * from data").show




  }

}
