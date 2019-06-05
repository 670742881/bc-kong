package com.bigdata.bokong.offline;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.bigdata.bokong.utils.TimeUtil;
import com.bigdata.bokong.utils2.RedisBase;
import com.bigdata.bokong.utils2.RedisClient;
import org.apache.commons.lang.StringUtils;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;

/**
 * @created by imp ON 2019/4/9
 */
public class JsonUtil extends RedisBase {


    public JsonUtil() {
        this.redisClient = RedisClient.getRedisClient();
        ;
    }


    public JSONObject jsonParse(String log) {

        Map<String, String> logMap = null;
        if (StringUtils.isNotBlank(log)) {
            int length = log.indexOf(":");
            String result = log.substring(length + 1);
            //解析得到我们需要用到的字段
            String logs = null;
            try {
                logs = URLDecoder.decode(result, "utf-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            JSONObject json = JSONObject.parseObject(logs);
            JSONObject aicheck = json.getJSONObject("aicheck");
            return aicheck;

        }
        return null;
    }

    //播控 对返回的数据进行修改 添加了auditor 字段 不用再存入redis进行join
    public long msgTypeToRedis(JSONObject aicheck) {
        //向阿里 网易 请求的的数据
        /**
         *
         * aicheck:{"aicheck":{"createTime":1552358985474,"startLenthTime":1,"disPlayName":"å¨±ä¹",
         * "auditor":"yc_alibaba","pContId":"8500316544","msgType":"1",
         * "endLengthTime":3600,"cpid":"800033","contentName":"ã
         * é£é©°äººçãåï¼æ²è¾åä¸é¨çµå½±æ¥è¢­ï¼çè§å¥³ä¸»ç½åæå¤çæ¬¾ï¼"}
         */
        long code = 1;
        String auditor = aicheck.getString("auditor");
        String pContId = aicheck.getString("pContId");
        String createTime = aicheck.getString("createTime");
        //把访问的接口类型 创建时间 cid存入redis

        String day = TimeUtil.parseLong2String(Long.parseLong(createTime), TimeUtil.HBASE_TABLE_NAME_SUFFIX_FORMAT);
        code = this.hset(this.SMART_REQUEST, pContId, auditor);
        this.expire(this.SMART_REQUEST, this.KEY_EXPIRE_DAY_ONE);

        return code;
    }


    public Map<String, String> returnData(JSONObject aicheck) {
        //阿里与网易返回的智能审核数据
        /**
         * aicheck:{"aicheck":{"createTime":1552359273879,"results":[{"rate":99.9,"scene":"terrorism","suggestion":"pass","label":"normal"},
         * {"rate":99.9,"scene":"porn","suggestion":"pass","label":"normal"},
         * {"rate":99.9,"scene":"ad","suggestion":"pass","label":"normal"}],"pContId":"8500316544","msgType":"2"}}
         *
         */
        Map<String, String> logMap = null;
        String pContId = aicheck.getString("pContId");
//        String auditor = aicheck.getString("auditor");
        String createTime = aicheck.getString("createTime");
        JSONArray results = aicheck.getJSONArray("results");
        String day = TimeUtil.parseLong2String(Long.parseLong(createTime), TimeUtil.HBASE_TABLE_NAME_SUFFIX_FORMAT);
        //对results进行遍历
        logMap = new HashMap<String, String>();
        logMap.put("pContId", pContId);
        String auditor = this.hget(this.SMART_REQUEST, pContId);
        if (auditor != null) {
            //又返回的数据
            logMap.put("auditor", auditor);
        } else {
            //没有返回的数据
            logMap.put("auditor", "unknown");
        }
        logMap.put("day", day);
        for (int i = 0; i <= results.size() - 1; i++) {
            JSONObject jsonObject = results.getJSONObject(i);
            String scene = jsonObject.getString("scene");
            String suggestion = jsonObject.getString("suggestion");
            logMap.put(scene, suggestion);
        }

        return logMap;
    }


}



