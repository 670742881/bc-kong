package com.bigdata.bokong.case_class

import org.apache.spark.sql.types.{StringType, StructField, StructType}

/**
  * @created by imp ON 2019/3/15
  */
case class ShiXunLogSchema(
                            cp_id: String,
                            bc_refuse_r: String,
                            ip: String,
                            cr_cp_id: String,
                            sync_time: String,
                            sp_time: String,
                            city: String,
                            prd_type: String,
                            is_delete: String,
                            en: String,
                            country: String,
                            form_type: String,
                            provence: String,
                            dura: String,
                            prd_name: String,
                            m_id: String,
                            l_bc_person: String,
                            name: String,
                            c_type: String,
                            c_id: String,
                            bc_status: String,
                            status: String,
                            prd_id: String,
                            lm_time: String,
                            bc_person: String,
                            n_cp_id: String,
                            src_log: String,
                            bc_time: String,
                            p_id: String,
                            s_time: String


                          ) extends Product with Serializable {




  override def productElement(n: Int): Any = ???

  override def productArity: Int = ???

  override def canEqual(that: Any): Boolean = ???
}
object ShiXunLogSchema{
  def getSchema() = {
    /**
      * (30,Map(cp_id -> 699017, bc_refuse_r -> , ip -> 10.20.3.169, cr_cp_id -> 699017,
      * sync_time -> 20190315164248, sp_time -> 10, city -> unknow, prd_type -> 12, is_delete -> 0,
      * en -> e_sx, country -> 局域网, form_type -> 7, provence -> 局域网, dura -> 65, prd_name -> 咪咕直播0元包,
      * m_id -> 1003189217, l_bc_person -> , name -> 新成员王源来啦，快投入大自然的怀抱吧, c_type -> 1, c_id -> 1502532090,
      * bc_status -> 1, prd_id -> 1003301, status -> 0, lm_time -> 20190315164242,
      * bc_person -> platform, n_cp_id -> 699017, src_log -> 1, bc_time -> 20190315164248, p_id -> 654032385, s_time -> 1552639368.222))
      */
    StructType(Seq(
      StructField("cp_id", StringType),
      StructField("bc_refuse_r", StringType),
      StructField("ip", StringType),
      StructField("cr_cp_id", StringType),
      StructField("sync_time", StringType),
      StructField("sp_time", StringType),
      StructField("city", StringType),
      StructField("prd_type", StringType),
      StructField("is_delete", StringType),
      StructField("en", StringType),
      StructField("country", StringType),
      StructField("form_type", StringType),
      StructField("provence", StringType),
      StructField("dura", StringType),
      StructField("prd_name", StringType),
      StructField("m_id", StringType),
      StructField("l_bc_person", StringType),
      StructField("name", StringType),
      StructField("c_type", StringType),
      StructField("c_id", StringType),
      StructField("bc_status", StringType),
      StructField("status", StringType),
      StructField("prd_id", StringType),
      StructField("lm_time", StringType),
      StructField("bc_person", StringType),
      StructField("n_cp_id", StringType),
      StructField("src_log", StringType),
      StructField("bc_time", StringType),
      StructField("p_id", StringType),
      StructField("s_time", StringType))
    )


  }
}