package com.bigdata.bokong.common

import org.apache.spark.rdd.RDD
import org.apache.spark.sql.Row

import scala.collection.mutable


/**
  * @created by imp ON 2018/6/15
  */

object LogCommon {

  def LogHadle(rdd: RDD[mutable.Map[String, String]]): RDD[Row] = {
    rdd.map(map => {
      //         {"cp_id":"699104","bc_refuse_r":"","ip":"10.20.3.172","cr_cp_id":"699104","sync_time":"20190309205241","city":"unknow","prd_type":"11",
      // "is_delete":"0","en":"e_sx","country":"局域网","form_type":"8","provence":"局域网","dura":"181","prd_name":"咪咕视频0元包月",
      // "m_id":"1003169782","l_bc_person":"","name":"复联","c_type":"1","c_id":"1502512803","bc_status":"0","status":"0","prd_id":"1002601",
      // "lm_time":"20190309205232","bc_person":"poms","n_cp_id":"699104","src_log":"1","bc_time":"","p_id":"653816091","s_time":"1552135961.681"}
      /**
        * 117.176.69.118^A1552619946.393^A/cntv.gif?en=e_st&api_v=1.0&pl=1&app_id=3&ch_id=xiaomi&ver=2.8.5&uuid=0806e1e0-06b7-3def-864d-f4bc460f30e5&user_id=986279&c_time=20190315111842372&net_t=WIFI
        * 113.224.82.137^A1552619946.440^A/cntv.gif?en=e_st&api_v=1.0&pl=1&app_id=3&ch_id=huawei&ver=2.8.5&uuid=6a8ca6cd-ef56-32f3-84ae-56871fad6112&user_id=0&c_time=20190315111910973&net_t=WIFI
        * 60.252.98.208^A1552619947.159^A/cntv.gif?en=e_st&api_v=1.0&pl=1&app_id=3&ch_id=huawei&ver=2.8.5&uuid=6746052a-eafa-3f6d-a3a7-ec91d043850f&user_id=0&c_time=20190315111908986&net_t=WIFI
        * 112.97.63.233^A1552619947.298^A/cntv.gif?en=e_st&api_v=1.0&pl=1&app_id=3&ch_id=oppo&ver=2.8.4&uuid=0671cfce-fcb3-32db-a3e6-f5387cb3f704&user_id=0&c_time=20190315111907985&net_t=4G
        * 182.104.63.88^A1552619947.383^A/cntv.gif?api_v=1.0&app_id=3&c_time=20190315111907235&ch_id=AppStore&en=e_st&net_t=WWAN&pl=2&us_id=&user_id=&uuid=5396481B-E565-47DA-A7E5-98A7CD33EE38&ver=2.8.6
        * 112.224.20.148^A1552619948.307^A/cntv.gif?en=e_la&api_v=1.0&pl=1&app_id=3&ch_id=meizu&ver=2.8.5&uuid=e5a81fa1-f00b-30de-a04e-3ab5dc7a2bdc&user_id=0&c_time=20190315111907793&net_t=4G
        * 123.178.69.119^A1552619948.648^A/cntv.gif?en=e_st&api_v=1.0&pl=1&app_id=3&ch_id=vivo&ver=2.8.5&uuid=dfb4946f-c5b3-37c9-a689-fa360ecd33e1&user_id=0&c_time=20190315111908550&net_t=4G
        */

      val cp_id = map.getOrElse("cp_id", "")
      val bc_refuse_r = map.getOrElse("bc_refuse_r", "")
      val ip = map.getOrElse("ip", "")
      val cr_cp_id = map.getOrElse("cr_cp_id", "")
      val sync_time = map.getOrElse("sync_time", "")
      val  sp_time= map.getOrElse("sp_time", "")
      val city = map.getOrElse("city", "")
      val prd_type = map.getOrElse("prd_type", "")
      val is_delete = map.getOrElse("is_delete", "")
      val en = map.getOrElse("en", "")
      val country = map.getOrElse("country", "")
      val form_type = map.getOrElse("form_type", "")
      val provence = map.getOrElse("provence", "")
      val dura = map.getOrElse("dura", "")
      val prd_name = map.getOrElse("prd_name", "")
      val m_id = map.getOrElse("m_id", "")
      val l_bc_person = map.getOrElse("l_bc_person", "")
      val name = map.getOrElse("name", "")
      val c_type = map.getOrElse("c_type", "")
      val c_id = map.getOrElse("c_id", "")
      val bc_status = map.getOrElse("bc_status", "")
      val status = map.getOrElse("status", "")
      val prd_id = map.getOrElse("prd_id", "")
      val lm_time = map.getOrElse("lm_time", "")
      val bc_person = map.getOrElse("bc_person", "")
      val n_cp_id = map.getOrElse("n_cp_id", "")
      val src_log = map.getOrElse("src_log", "")
      val bc_time = map.getOrElse("bc_time", "")
      val p_id = map.getOrElse("p_id", "")
      val s_time = map.getOrElse("s_time", "")
      Row(cp_id, bc_refuse_r, ip, cr_cp_id, sync_time, sp_time,city, prd_type, is_delete, en, country, form_type,
        provence, dura, prd_name, m_id, l_bc_person, name, c_type, c_id, bc_status, status, prd_id, lm_time, bc_person, n_cp_id, src_log, bc_time, p_id, s_time)


    })
  }
}
