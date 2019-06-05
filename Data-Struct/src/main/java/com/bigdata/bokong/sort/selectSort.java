package com.bigdata.bokong.sort;

/**
 * @created by imp ON 2019/3/28
 */
public class selectSort {
    public static void selectionSort(int[] a) {
        int n = a.length;
        for (int i = 0; i < n; i++) {
            int k = i;
            // 找出最小值的下标
            for (int j = i + 1; j < n; j++) {
                if (a[j] < a[k]) { //
                    k = j;
                }
            }
            // 将最小值放到未排序记录的第一个位置
            if (k > i) {
                int tmp = a[i];
                a[i] = a[k];
                a[k] = tmp;
            }
        }
       for (int i:a){
           System.out.println(i);
       }
    }
    public static void selectionSort2(int[] a){
       // 10, -1, 9, 7, 100, 67, 4, 93
        int min=0;
        for (int i=0;i<a.length;i++){
            min=a[i];
            for (int j=i+1;j<a.length;j++){
               if (a[j]<min){
                   //把最小值赋给min
                   min=a[j];
                   //将前面的第一个值赋给临时变量
                  int tmp= a[i];
                  //将目前比较出来的小值  给第一个
                  a[i]=min;
                  a[j]=tmp;

               }
            }
        }
        for (int i:a){
            System.out.println(i);
        }

    }

    public static void main(String[] args) {
        int[] arr= new int[]{-1, 9, 7, 100, 67, 4, 93};
//       selectionSort(arr);
        selectionSort2(arr);
    }
}
