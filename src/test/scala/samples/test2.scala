package samples

import java.net.URLDecoder

/**
  * @created by imp ON 2019/3/21
  */
object test2 {
  def main(args: Array[String]): Unit = {
    //    println("20190320211".substring(0,8))
    //
    //    println("221.179.52.10, 10.2.9.95".split(",")(0))
    //
    //    println(TimeUtil.parseLong2String(1550537281032L,"yyyyMMddHHmmssSSS"))
    //
    ////   println(java.lang.Double.parseDouble("1550537281.032")*1000)
    //
    //    val a ="1550537281.032".toDouble*1000.toLong
    //    println(TimeUtil.parseLong2String(a.toLong,"yyyyMMddHHmmssSSS"))
    ////    println("".toFloat*1000)
    //
    //    println(TimeUtil.parseNginxServerTime2Date("1550537281.032"))
    //
    //
    //    val sparks = SparkSession.builder.master("local[4]").appName("test1").getOrCreate
    //    val sc = sparks.sparkContext
    //    sc.setLogLevel("ERROR")
    //    val data3=sc.parallelize(List(("a",1),("a",2),("b",1)))
    //      //flatMapValues是对值的扁平化操作  返回值是RDD[(K, U)]
    //      .groupByKey().map(d=>(d._1,d._2)).flatMapValues(_.toIterable)
    //    data3.foreach(println(_))
    //  }
    //
    //  println(List(1, 2, 3).reduce(_ + _))
    //  println(List(2, 2, 3).reduce(_ * _))


      val a = URLDecoder.decode("c_name=%F0%9F%91%A0%F0%9F%91%A0%F0%9F%91%A0%F0%9F%91%A0%F0%9F%91%A0%F0%9F%91%A0%F0%9F%91%A0%F0%9F%91%A0%F0%9F%91%A0%F0%9F%91%A0%F0%9F%91%A0%F0%9F%91%A0%F0%9F%91%A0%F0%9F%91%A0%F0%9F%91%A0%F0%9F%91%A0%F0%9F%91%A0%F0%9F%91%A013845318276", "utf-8")
      println(a)

    val m=Map("a"->1)
    println(m.get("b"))

  }
}
