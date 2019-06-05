package samples

import kafka.common.TopicAndPartition
import scalikejdbc._
import scalikejdbc.config.DBs

/**
  * @created by imp ON 2019/3/15
  */
object ScalaJdbcTest {

  def main(args: Array[String]): Unit = {
//    val conf = ConfigFactory.load()
//     println(conf.getString("aa"))
    DBs.setup( 'default)
//    scalikejdbc.config.DBs.setupAll()
    val fromOffsets: Map[TopicAndPartition, Long] = NamedDB( 'default).readOnly { implicit session =>
//      sql"select * from stream_offset where groupid=? and topic=? and brokerlist=?".bind(groupid, toppic, brokers).map(rs => {
      sql"select * from stream_offset where groupid=? ".bind("spark").map(rs => {
        (TopicAndPartition(rs.get[String]("topic"), rs.get[Int]("partitions")), rs.long("offset"))
      }).list().apply()
    }.toMap

    println(fromOffsets)
  }

}
