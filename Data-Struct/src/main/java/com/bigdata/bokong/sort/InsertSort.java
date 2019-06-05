package com.bigdata.bokong.sort;

/**
 * @created by imp ON 2019/3/28
 */
public class InsertSort {
    //插入排序
    public static void main(String[] args) {
        int[] arr = new int[]{-1,9999, 9, 7, 100, 67, 4, 93,4,292929,1111,1223,-11111};
//        insertSort(arr);
        heerSort(arr);
    }


    public static void insertSort(int[] arr) {
        //{-1, 9, 7, 100, 67, 4, 93}
        //
        for (int i = 1; i < arr.length; i++) {
            //当前遍历的值
            int tmp = arr[i];
            //从当前值的前面 的部分遍历
            int j;
            for (j = i - 1; j >= 0; j--) {
                //当内循环遍历到大于当前值 就往后移一位
                if (arr[j] <tmp) {
                    //就是移位 把当前值 赋给他后面的值
                    arr[j + 1] = arr[j];
                } else {  //比当前值晓得就不进行遍历了 最后就停在刚好大于他的那个值位置
                    break;
                }
            }
            arr[j + 1] = tmp;
        }
        for (int i : arr) {
            System.out.println(i);
        }

    }

    //希尔排序    使用步长进行比较  {-1, 9, 7, 100, 67, 4, 93};
    public static void heerSort(int[] arr) {
        //定义步长
        int d = arr.length / 2;
        while (d!=0) {
            for (int i = 0; i < arr.length; i++) {

                for (int j = 0; j + d < arr.length; j += d) {
                    int tmp = 0;
                    if (arr[j] < arr[j+d]){
                        tmp=arr[j];
                        arr[j]=arr[j+d];
                        arr[j+d]=tmp;
                    }
                }
            }
            d--;

        }

        for (int a:arr){
            System.out.println(a);
        }
    }


}
