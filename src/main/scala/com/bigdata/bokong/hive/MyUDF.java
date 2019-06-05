package com.bigdata.bokong.hive;

import org.apache.hadoop.hive.ql.exec.UDF;
import org.apache.hadoop.hive.ql.exec.UDFMethodResolver;
import org.junit.jupiter.api.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * @created by imp ON 2019/4/1
 */
public class MyUDF extends UDF {
    private SimpleDateFormat sdf;

    public MyUDF() {

        this.sdf = new SimpleDateFormat("yyyy-MM-dd");
    }

    /**
     * 1 第一季度 2 第二季度 3 第三季度 4 第四季度
     *
     * @param date
     * @return
     */
    public static int getSeason(Date date) {

        int season = 0;

        Calendar c = Calendar.getInstance();
        c.setTime(date);
        int month = c.get(Calendar.MONTH);
        switch (month) {
            case Calendar.JANUARY:
            case Calendar.FEBRUARY:
            case Calendar.MARCH:
                season = 1;
                break;
            case Calendar.APRIL:
            case Calendar.MAY:
            case Calendar.JUNE:
                season = 2;
                break;
            case Calendar.JULY:
            case Calendar.AUGUST:
            case Calendar.SEPTEMBER:
                season = 3;
                break;
            case Calendar.OCTOBER:
            case Calendar.NOVEMBER:
            case Calendar.DECEMBER:
                season = 4;
                break;
            default:
                break;
        }
        return season;
    }

    public String evaluate(String sdate) throws ParseException {


        Date date = sdf.parse(sdate);
        return String.valueOf(getSeason(date));
    }

    @Test
    public void  test() throws ParseException {
      String date=  new Date().toString();
        System.out.println(date);
        System.out.println( evaluate("2019-04-01"));;
    }
}


