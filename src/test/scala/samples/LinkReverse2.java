package samples;

import java.util.LinkedList;

/**
 * @created by imp ON 2019/3/19
 */
public class LinkReverse2 {

    public static void main(String[] args) {
        Node node1 = new Node(1);
        Node node2 = new Node(2);
        Node node3 = new Node(3);
        Node node4 = new Node(4);
        Node node5 = new Node(5);
        node1.next = node2;
        node2.next = node3;
        node3.next = node4;
        node4.next = node5;

        Node tmp = node1;
        while(tmp != null){
            System.out.print(tmp.val+">");
            tmp = tmp.next;
        }
        System.out.println();

        Node head = reverse(node1);//反转
        while(head != null){
            System.out.print(head.val+">");
            head = head.next;
        }
    }

    public static Node reverse(Node head){
        //node 当前 =0
        Node cur = head;
        //node past=1
        Node post = head.next;
        head.next = null;
        while(post != null){
            //node tmp=1
            Node tmp = post;
            //post =1.next =2
            post = post.next;
            //tmp.next=0
            tmp.next = cur;
            //   0=1
            cur = tmp;
        }
        return cur;
    }

    static class Node{
        int val;
        Node next;
        Node(int val){
            this.val = val;
            this.next = null;
        }
    }
}

//执行结果：
//反转前：1 2 3 4
//反转后：4 3 2 1
