package com.bigdata.bokong.common

import java.net.URLDecoder

import com.bigdata.bokong.utils.{EventLogConstants, TimeUtil}
import com.bigdata.bokong.utils.IP_parse.{IPInfo, IPv4FormatException}
import org.apache.spark.rdd.RDD

/**
  * @created by imp ON 2019/3/15
  */
object LogUtils {

  def logParse(rdd: RDD[String])= {
    rdd.map(line => {
      line.split("\\?", -1)
    }).filter(_.length == 2).map(arr => {
      val headARR = arr(0).split("\\^A", -1)
      val tailarrr = arr(1).split("&", -1)
      (headARR, tailarrr)
    }).filter(tup => tup._1.length == 3 || tup._2.length >= 8)

   .map {
        case (arr1, arr2) => {
          val ip = if (!arr1(0).isEmpty && arr1(0).contains(",")) arr1(0).split(",")(0).trim else arr1(0)

          //        println("========"+ip+"================")
          val a = arr1(1).toDouble * 1000.toLong
          val serviceTime = TimeUtil.parseLong2String(a.toLong, "yyyyMMddHHmmssSSS")
          val map = scala.collection.mutable.Map[String, String]()
          for (e <- arr2) {
            val fieds = e.split("=", -1)
            if (fieds.length==2){
              val key = fieds(0)
              var value=""
              try {
               value = URLDecoder.decode(fieds(1), EventLogConstants.LOG_PARAM_CHARSET)
              }catch {
                  case e:Exception =>println(value+"------无法识别此url")


              }

              map.+=(key -> value)
            }
            else {
              map
            }


          }
          val areaInfo: Array[String] = try {
            if (ip.nonEmpty) IPInfo.getInfo(ip) else Array("un", "un", "un")
          } catch {
            case e:IPv4FormatException =>println(ip+"------无法解析")
              println(e.getMessage)
//           rdd.foreachPartition(println(_))
              Array("un", "un", "un")
          }
          map.+=("ip" -> ip, "s_time" -> serviceTime, "country" -> areaInfo(0), "provence" -> areaInfo(1), "city" -> areaInfo(2))
          map
        }
      }
  }

}
