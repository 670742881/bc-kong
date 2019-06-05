package com.bigdata.bokong.string;

/**
 *    对象比较大小compare的用法 字符串排序
 *    练习代码, 给定字符串" nba" "cba" "ncaa" "wba" ...    
    
    思路: 
    1.排序,想到数组的排序.数组排序想到选择排序,或者冒泡排序
    2.用数组排序的思路做此题.(字符串同理)
    3.遍历数组,比较每一个元素与下一个元素的大小关系
    4.最后交换两个元素位置
    5.输出结果
    
    经过学习总结:
    1.String本身是不具备比较大小功能的,但是由于String对象实现了
    Comparable的接口.这个接口是可比较功能的接口.这里定义了比较方法compareTo方法.
    Api手册中的描述:
    此接口强行对实现它的每个类的对象进行整体排序。
    这种排序被称为类的自然排序，类的 compareTo 方法被称为它的自然比较方法
    
    2.所以如果我们定义了一个类,这个类产生的对象,想要比大小时,我们就不用自己写方法了,
    直接实现Comparable接口,重写compareTo方法就可以了.注:本题中String类已经重写了
    comparTo方法.
    
    3.
    该方法比较对象 与 指定对象的 顺序
    写法: 
    campareTo(T o); 
    返回值: int  返回 0(两者相等),负数(对象小于参数),正数 (对象大于参数) 
    参数:   o 为要比较的对象

 */
public class CompareTo{

    public static void main(String[] args) {
        String[] str = {"nba","cba" ,"ncaa" ,"wba","ccba","abc"};
        printArr(str);    
        strSort(str);        //练习排序方法
//        Arrays.sort(str);    排序用法
        printArr(str);
        strSort2(str);

        System.out.println(22^22);;
        
    }
    
    /**
     * 对字符串进行由小到大排序
     * @param str    String[] 需要排序的字符串数组
     */
    public static void strSort(String[] str){
        for (int i = 0; i < str.length; i++) {
            for (int j = i+1; j < str.length; j++) {
                if(str[i].compareTo(str[j])>0){    //对象排序用camparTo方法
                    swap(str,i,j);
                }
            }
        }
    }
    public static  void  strSort2(String[] str){
        for (int i=0;i<str.length-1;i++){
            for (int j=0;j<str.length-i-1;j++){
                if (str[j].compareTo(str[j+1])>0){
                    String tmp=str[j];
                    str[j]=str[j+1];
                    str[j+1]=tmp;
                }
            }
        }
      for (String s:str){
          System.out.println(s);
      }
    }

    /**
     * 交换两个元素的位置的方法
     * @param strSort    需要交换元素的数组    
     * @param i    索引i
     * @param j 索引j
     */
    private static void swap(String[] strSort, int i, int j) {
        String t = strSort[i];
        strSort[i] = strSort[j];
        strSort[j] = t;
    }
    /**
     * 打印字符串数组
     * @param str
     */
    private static void printArr(String[] str) {
        for (int i = 0; i < str.length; i++) {
            System.out.print(str[i]+"\t");
        }
        System.out.println();
    }


}