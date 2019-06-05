package samples

import scala.util.matching.Regex

/**
  * @created by imp ON 2019/4/29
  */
object zhenze {
  def main(args: Array[String]): Unit = {
    val p: Regex = "[0-9]+".r
    val s = "546465sfgidg"
    p.findAllIn(s).toArray

    p.findAllIn(s).foreach(x=>println(x))

    p.findAllIn(s).mkString("")
//    mkString("[","","]")
//    取标点，只取数字和字符
    val p2 = "[0-9a-zA-Z]+".r
//    lines.flatMap(_.split(" "))
//      .map(x=>(p.findAllIn(x).mkString(""),1))
//      .groupBy(_._1)
//      .mapValues(_.size)
//      .toArray
//      .sortWith(_._2>_._2)
//      .slice(0,10)
//
//    lines.flatMap(_.split(" ")).map(x=>(p.findAllIn(x).mkString(""),1))
  }
}
