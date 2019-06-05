package com.bigdata.bokong.utils2;


import org.apache.commons.lang.StringUtils;

import java.util.Map;

public abstract class RedisBase {
    public RedisClient redisClient;

    // 视讯播控人集合的key
    public final String KEY_VIDEO_BC_PERSON = "VIDEO_BC_PERSON";
    // 视讯待审核内容集合的key
    public final String  KEY_VIDEO_CONT_WAIT = "VIDEO_CONT_WAIT_";
    // 视讯已播控内容集合的key
    public final String KEY_VIDEO_CONT_BC = "VIDEO_CONT_BC_";
    // 动漫作品分类集合的key
    public final String KEY_MAN_CONT_TYPE = "MAN_CONT_TYPE";
    // 动漫播控人集合的key
    public final String KEY_MAN_BC_PERSON = "MAN_BC_PERSON";
    // 当天的待播控节目
    public final String KEY_VIDEO_PROGRAM_WAIT = "VIDEO_PROGRAM_WAIT_";
    // 播控历史的已播控节目
    public final String KEY_VIDEO_PROGRAM_BC_OLD = "VIDEO_PROGRAM_BC_OLD_";
    // 播控历史的已播控内容
    public final String KEY_VIDEO_CONTENT_BC_OLD = "VIDEO_CONTENT_BC_OLD_";

    public final String KEY_VIDEO_BC_PERSON_CONT = "VIDEO_BC_PERSON_CONT";

    //提交审核的内容的节目key
    public final String KEY_PRO_SEND="REVIEW_PRO_SEND";
    //提交审核的内容key
    public final String KEY_CONTENT_SEND="REVIEW_CONTENT_SEND";
    //提交审核内容时长key
    public final String KEY_PROTIME_SEND="REVIEW_PROTIME_SEND";
    //提交审核分类key
    public final String KEY_DISPLAYNAME_SEND="REVIEW_DISPLAYNAME_SEND";
    //提交审核时间key
    public final String KEY_CREATETIME_SEND="REVIEW_CREATETIME_SEND";

    //阿里审核完发回的内容的节目key
    public final String PRO_CONTENT_ID="PROGRAM_CONTENT_ID";

    //向阿里\网易发送请求的key
    public final String SMART_REQUEST="SMART_REQUEST";
    // redis中key的过期时间为1天
    public final int KEY_EXPIRE_DAY_ONE = 24 * 60;

    /**
     * redis hash
     * @param key
     * @param field
     * @param value
     * @return 	!= 0 --> 写成功；==0 -->写失败
     */
    public long hsetnx(String key,String field,String value){
        return redisClient.getJedisCluster().hsetnx(key,field,value);
    }

    /**
     * redis hash
     * @param key
     * @param field
     * @param value
     * @return ==1 新建；==0 更新
     */
    public long hset(String key,String field,String value){
        return redisClient.getJedisCluster().hset(key,field,value);
    }

    public Boolean hexists(String key,String field){
        return redisClient.getJedisCluster().hexists(key,field);
    }

    public long hmset(String key, Map<String, String> hashMap){
        String status  = redisClient.getJedisCluster().hmset(key, hashMap);
        return "OK".equals(status)?1:0;
    }



    public String hget(String key,String progId){
        String hash = redisClient.getJedisCluster().hget(key, progId);
        if(StringUtils.isNotBlank(hash)){
            return hash;
        }
        return null;
    }

    /**
     * 删除存在的key，返回1；否则，返回0
     * @param key
     * @param progId
     * @return
     */
    public long hdel(String key, String progId){
        return redisClient.getJedisCluster().hdel(key, progId);
    }

    /**
     * redis 过期
     * @param key
     * @param minutes
     * @return 1:设置成功
     */
    public Long expire(String key,int minutes){
        return redisClient.getJedisCluster().expire(key,minutes * 60);
    }
    /**
     * redis set 添加
     * @param key
     * @param member
     * @return 0：redis已经存在
     */
    public long sadd(String key,String member){
        return redisClient.getJedisCluster().sadd(key,member);
    }

    public long incr(String key){
        return redisClient.getJedisCluster().incr(key);
    }
}
