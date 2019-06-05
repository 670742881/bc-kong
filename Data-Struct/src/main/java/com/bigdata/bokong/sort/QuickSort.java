package com.bigdata.bokong.sort;

/**
 * @created by imp ON 2019/3/29
 */
public class QuickSort {
    public static void sort(int[] a, int lo, int hi) {
        if (hi <= lo) return;
        //切分
        int j = partition(a, lo, hi);
        sort(a, lo, j - 1);
        sort(a, j + 1, hi);

    }

    //a[j]就是那个切分元素，从数组的左端开始向右扫描直到找到一个大于等于它的元素
    //再从数组的右端开始向左扫描直到找到一个小于等于它的元素，将这两个元素交换位置。
    public static int partition(int[] a, int low, int high) {
        //左右扫描数组  基数与low和high不断的相比 停下的位置就是他的位置
        //{2, 78, 23, 34, 67, 45, 73, 90, 120, 12, 20, 9, 2};
        int temp = a[low]; //基准元素
        while (low < high) {
            //從高到底 如果比temp大就一直找
            while (low < high && a[high] >= temp) {
                high--;
            }
            //发现了比temp大的
            a[low] = a[high];
            while (low < high && a[high] <= temp) {
                low++;
            }
            //发现了比temp小的
            a[high] = a[low];
        }
        a[low] = temp;
        return low;

    }

    public static void main(String[] args) {
        int[] a = {5, 78, 23, 34, 67, 45, 73, 90, 120, 12, 20, 9, 15};
        int lo = 0;
        int hi = a.length - 1;
        sort(a, lo, hi);
        for (int i = 0; i < a.length; i++) {
            System.out.print(a[i] + " ");
        }

    }
    public static int getMid(int[] a, int low, int high){
        int tmp=a[low];
        while (low<high){
            while (low<high&&a[high]>=tmp){
                high--;
            }
           a [low]=a[high];
            while (low<high&&a[high]<=tmp){
                low++;
            }
            a [high]=a[low];

        }
        a[low]=tmp;


        return low;
    }

}
