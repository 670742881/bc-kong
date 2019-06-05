package com.bigdata.bokong.tree;

import scala.math.Ordering;

/**
 * @created by imp ON 2019/3/22
 */
public class StringDemo {

    public static void main(String[] args) {
        int index=-1;

        String str="ayoxjdhnsll;dlldd";
        System.out.println(str.charAt(1));
        for (int i=0;i<=str.length();i++){
            char e=str.charAt(i);
            //查找此元素 从此元素下标 往后的部分
            if (str.indexOf(e,i+1)!=-1){
                index=i;
                break;
            }
        }
        System.out.println(str.charAt(index));
    }
}
