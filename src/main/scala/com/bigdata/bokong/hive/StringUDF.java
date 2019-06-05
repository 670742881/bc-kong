package com.bigdata.bokong.hive;

import org.apache.commons.lang.StringUtils;
import org.apache.hadoop.hive.ql.exec.UDF;
import org.junit.jupiter.api.Test;

import java.text.ParseException;

/**
 * @created by imp ON 2019/4/1
 */
public class StringUDF extends UDF {

    public static String evaluate(String str){
        if (str!=null || StringUtils.isNotBlank(str)){
            str.toLowerCase();
            return str.toUpperCase();
        }
        return  null;
    }

    @Test
    public void  test() throws ParseException {
        System.out.println( evaluate("sUUaaaaaaaaaaaasqwasWW"))
       ;
    }

}
