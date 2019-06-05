package com.bigdata.bokong.sort;

import breeze.linalg.Options;

/**
 * @created by imp ON 2019/3/29
 */
public class HeapSort {
    public static void main(String[] args) {
        int[] arr = new int[]{-1,9999, 9, 7, 100, 67, 4, 93,4,292929,1111,1223,-11111};
    }
    
    
    public static void heapSort(int[] a){
        if (a.length<=1 |a==null){
            return;
        }
        //创建大堆
        createBigHeap(a);
        
    }

    private static void createBigHeap(int[] a) {
 //大堆 父节点 必须必子节点大
        int half=(a.length-1)/2;
        for (int i=half;half>=0;half--){
            maxHeap(a,a.length,i);
        }

    }

    private static void maxHeap(int[] a, int length, int i) {
    }

}
