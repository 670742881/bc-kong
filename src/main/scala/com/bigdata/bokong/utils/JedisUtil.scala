package com.bigdata.bokong.utils

import org.apache.commons.pool2.impl.GenericObjectPoolConfig
import redis.clients.jedis.{Jedis, JedisPool}

/**
  * @created by imp ON 2019/3/11
  */
object JedisUtil {

  //Redis服务器IP
  val ADDR = "192.168.121.12";

  //Redis的端口号
  val PORT = 6379

  //访问密码
  val AUTH = "test123";

  //可用连接实例的最大数目，默认值为8；
  //如果赋值为-1，则表示不限制；如果pool已经分配了maxActive个jedis实例，则此时pool的状态为exhausted(耗尽)。
  val MAX_ACTIVE: Int = 1024;

  //控制一个pool最多有多少个状态为idle(空闲的)的jedis实例，默认值也是8。
  val MAX_IDLE = 200;

  //等待可用连接的最大时间，单位毫秒，默认值为-1，表示永不超时。如果超过等待时间，则直接抛出JedisConnectionException；
  val MAX_WAIT = 10000;

  val TIMEOUT = 10000;

  //在borrow一个jedis实例时，是否提前进行validate操作；如果为true，则得到的jedis实例均是可用的；
  val TEST_ON_BORROW = true;


  /**
    * 初始化Redis连接池
    */
  def getJedis() = {
    val poolConfig = new GenericObjectPoolConfig()
    poolConfig.setMaxWaitMillis(MAX_WAIT);
    poolConfig.setMaxTotal(MAX_ACTIVE);
    //     版本没有这个方法       config.setMaxActive(MAX_ACTIVE);
    poolConfig.setMaxIdle(MAX_IDLE);
    //            config.setMaxWait(MAX_WAIT);
    poolConfig.setTestOnBorrow(TEST_ON_BORROW);
    val jedisPool = new JedisPool(new GenericObjectPoolConfig(), ADDR,PORT,TIMEOUT,AUTH)
    jedisPool.getResource
  }

  val jedisPool = new JedisPool(new GenericObjectPoolConfig(), ADDR,PORT,TIMEOUT,AUTH)

  def getJedisClient(): Jedis = {
    jedisPool.getResource
  }

  def main(args: Array[String]): Unit = {
 val j=   getJedisClient()
    println(j.hget("20190327_zhouzhisheng","bcPersonStat"))

  }
}
