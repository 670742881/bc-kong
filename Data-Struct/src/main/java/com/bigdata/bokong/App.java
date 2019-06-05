package com.bigdata.bokong;

import com.bigdata.bokong.hashmap.HahMap;
import org.junit.jupiter.api.Test;


import java.util.Random;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        System.out.println( "Hello World!" );

        System.out.println("aa".hashCode()%16);
        System.out.println(10%2);
    }

    @Test
    public void hashMapTest(){
      HahMap hahMap=  new HahMap<String,Integer>();

//        System.out.println(hahMap.get("aa"));
        System.out.println( hahMap.put("aa",1));


        System.out.println(hahMap.put("aa",2));
    }

    @Test
     public void genaterData(){

        new Thread(new Runnable() {
            @Override
            public void run() {
                int i=0;
                while (i<=1000) {
                    int userid = new Random().nextInt(100);
                    String[] arr = {"男", "nv"};
                    int sex = new Random().nextInt(arr.length);
                    String[] webs = {"baidu", "ali", "tx", "google", "taobao", "apache"};
                    int web = new Random().nextInt(webs.length);
                    System.out.println(userid + "\t" + arr[sex] + "\t" + webs[web]);

                    i++;

                }
            }
        }).start();
     }
}
