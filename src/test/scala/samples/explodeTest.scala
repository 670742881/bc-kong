package samples
import org.apache.spark.sql.SparkSession
/**
  * @created by imp ON 2019/3/18
  */
object explodeTest {
  def main(args: Array[String]): Unit = {

    val sparks = SparkSession.builder.master("local[4]").appName("test1").getOrCreate
    val sc = sparks.sparkContext

    val df=  sparks.read.json("file:///E:\\MyProject\\bc-kong\\data\\josn")

  //  df.show()
    //spark  读取json 数据
    /**+---+--------------------+-------+
|age|             myScore|   name|
+---+--------------------+-------+
| 25|  [[19,23], [58,50]]|Michael|
| 30|[[29,33], [38,52]...|   Andy|
| 19|  [[39,43], [28,53]]| Justin|
| 25|  [[19,23], [58,50]]|Michael|
| 30|[[29,33], [38,52]...|   Andy|
| 19|  [[39,43], [28,53]]| Justin|
| 25|  [[19,23], [58,50]]|Michael|
| 30|[[29,33], [38,52]...|   Andy|
| 19|  [[39,43], [28,53]]| Justin|
+---+--------------------+-------+
      *
      *
      *
      */

    //使用spark.sql.functions._ explode函数进行压平操作  行转列
    import org.apache.spark.sql.functions._
    val dfScore = df.select(df("name"),explode(df("myScore"))).toDF("name","myScore")
    val dfMyScore = dfScore.select("name","myScore.score1", "myScore.score2")
  //  dfScore.show()
   df.createOrReplaceTempView("df")
    //u.answer, ''
    /**
      *
      *
      *
      * +-------+-------+
      * |   name|myScore|
      * +-------+-------+
      * |Michael|[19,23]|
      * |Michael|[58,50]|
      * |   Andy|[29,33]|
      * |   Andy|[38,52]|
      * |   Andy|[88,71]|
      * | Justin|[39,43]|
      * | Justin|[28,53]|
      * |Michael|[19,23]|
      * |Michael|[58,50]|
      * |   Andy|[29,33]|
      * |   Andy|[38,52]|
      * |   Andy|[88,71]|
      * | Justin|[39,43]|
      * | Justin|[28,53]|
      * |Michael|[19,23]|
      * |Michael|[58,50]|
      * |   Andy|[29,33]|
      * |   Andy|[38,52]|
      * |   Andy|[88,71]|
      * | Justin|[39,43]|
      * +-------+-------+
      * only showing top 20 rows
      */

   val data3=sc.parallelize(List(("a",1),("a",2),("b",1)))
      .groupByKey()
   // data3.foreach(println(_))


    //
    //select  cast(substring(regexp_replace('2016-06-05 00:00:00.0', '-', ''),1,8) as int);
    sparks.sql("select cast(substring('2016-06-05 00:00:00.0',1,10) as int)").show
    sparks.sql(" select  cast(substring(regexp_replace('2016-06-05 00:00:00.0', '-', ''),1,8) as int )-20190418").show()
    sparks.sql("select regexp_replace('2016-06-05 00:00:00.0', '-', '')").show()


  }
}
