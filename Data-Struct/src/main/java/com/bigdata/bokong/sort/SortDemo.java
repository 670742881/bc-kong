package com.bigdata.bokong.sort;

/**
 * @created by imp ON 2019/3/27
 */
public class SortDemo {
    public static int binarySearch(int [] srcArray,int des){
        //{1,2,3,4,5,6}
        //开头
        int low=0;
        //数组长度
        int height=srcArray.length-1;
        while(low<=height){
            //找出中间位置的index
            int middle=(low+height)/2;
            //如果找的就是中间那个数 直接返回
            if(des==srcArray[middle]){
                return middle;
                //如果是在左边
            }else if(des<srcArray[middle]){
                //中间位置减一  height+low/2 就左移一位 再循环 找到了就会返回了  没找到在左移
                height=middle-1;
            }else{
                //开始加一
                low=middle+1;
            }
        }
        return -1;
    }

   //递归的方式实现：
    public static int binarySearch(int[] dataset,int data,int beginIndex,int endIndex){
        int midIndex=(beginIndex+endIndex)/2;
        if(data<dataset[beginIndex] || data>dataset[endIndex] || beginIndex>endIndex){
            return -1;
        }
        if(data<dataset[midIndex]){
            return binarySearch(dataset,data,beginIndex,midIndex-1);
        }else if(data>dataset[midIndex]){
            return binarySearch(dataset,data,midIndex+1,endIndex);
        }else{
            return midIndex;
        }
    }

    class ListNode { int val; ListNode next; ListNode(int x) { val = x; } }
    //递归，在反转当前节点之前先反转后续节点  1->2->3->4->5     1<-2<-3<-4<-5
    public static ListNode reverseList1(ListNode head){
        ListNode prev=null; //
        while(head != null){
            //   tmp=2的next 3
            ListNode temp=head.next;
            head.next=prev; //prev=3
            prev =head;  //2=prev
            head =temp;  //当前=2
        }
        return prev;
    }
   // 递归的方式实现：
    public ListNode reverseList(ListNode head) {
        if(head==null||head.next ==null)
        return head;
        ListNode prev = reverseList(head.next);
        head.next.next = head;
        head.next = null; return prev; }

    public static void main(String[] args) {
        int [] a={1,3,5,7,9,10,20,100};
        System.out.println( binarySearch(a,7,0,a.length-1)); ;
        System.out.println(binarySearch(a,1));
    }
}
