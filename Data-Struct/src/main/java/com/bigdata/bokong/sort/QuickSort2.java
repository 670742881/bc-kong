package com.bigdata.bokong.sort;

/**
 * @created by imp ON 2019/4/1
 */
public class QuickSort2 {

    public void qsort1(int[] a, int p, int r) {
        // 0个或1个元素，返回
        if (p >= r)
            return;
        // 选择左端点为pivot
        int x = a[p];
        int j = p;
        for (int i = p + 1; i <= r; i++) {
            // 小于pivot的放到左边
            if (a[i] < x) {
                swap(a, ++j, i);
            }
        }
        // 交换左端点和pivot位置
        swap(a, p, j);
        // 递归子序列
        qsort1(a, p, j - 1);
        qsort1(a, j + 1, r);
    }

    public static void swap(int[] a, int i, int j) {
        int temp = a[i];
        a[i] = a[j];
        a[j] = temp;
    }


    //自己理解快排的思想


    public void quickSort2(int[] arr, int begin, int end) {
        //判断只有一个元素或者 开头大于结尾的直接返回
        if (arr.length == 0 | begin > end) {
            return;
        }

        //定义开始元素 结束元素
        int i = arr[begin];
        int j =begin;
        //从第二个元素开始 开始值小的交换
      for (int d=i+1;d<=end;d++){
      if (arr[d]<begin) {
          swap2(arr,++j,end);
      }
      }
      }


    public static void swap2(int[] a, int i, int j) {
        int temp = a[i];
        a[i] = a[j];
        a[j] = temp;
    }
}
