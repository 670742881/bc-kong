package com.bigdata.bokong.linklist;

/**
 * @created by imp ON 2019/3/21
 */
public class LinklistReverse {
    //定义类节点类 封装节点信息

    static class Node {
        private int value;
        private Node next;

        public Node(int value) {
            this.value = value;
            this.next = null;
        }

        public int getValue() {
            return value;
        }

        public void setValue(int value) {
            this.value = value;
        }

        public Node getNext() {
            return next;
        }

        public void setNext(Node next) {
            this.next = next;
        }
    }


    public static Node reverse3(Node head) {
        //当前节点  1
        Node cur = head;
        //当前节点的下一个节点  2
        Node post = head.next;
        //将当前节点的指向设置为空  1->null
        head.next = null;
        while (post != null) {
            //将下一个节点的值 给一个临时变量 2=tmp
            Node tmp = post;
            //将下一个的下一个值给 下个值  3->2
            post = post.next;
            //临时节点的再赋值 将1 赋给他的next节点 1=tmp.next
            tmp.next = cur;
            //tmp现在是 2->1
            cur = tmp;
        }
        return cur;
    }

    public static Node reverse4(Node head){
        Node current=head;
        Node post=head.next;
        while (post.next!=null){
            Node tmp=post;
            post=post.next;
            tmp.next=current;

            current=tmp;
        }
        return  current;
    }






    //1->2->3->4->5     1<-2<-3<-4<-5
    public static Node reverse(Node node) {
//        //如果进来的节点是第一个或者最后一个
//        Node current=node;
//        Node next=node.getNext();
//       while (next!=null){
//           Node tmp=next;
//           next=next.getNext();
//           tmp
//       }
//    }
        return null;
    }


//    public void reverse4(Node node) {
//        ///记录current的节点是head大的下一个节点。
//        Entry<T> current = head.next;
//
//        //切断head.next指向current，（当前的head变为链表的尾，所以next为空）
//        head.next = null;
//        while(current != null) {
//            //记录currentNext的节点是currentNext大的下一个节点。
//            Entry<T> currentNext = current.next;
//            //current.next反方向指向以前的节点
//            current.next = head;
//            //移动head和current指针，到后面head重新成为头节点
//            head = current;
//            current = currentNext;
//        }
//    }

    public static void main(String[] args) {
        Node head = new Node(0);
        Node node1 = new Node(1);
        Node node2 = new Node(2);
        Node node3 = new Node(3);
        head.setNext(node1);
        node1.setNext(node2);
        node2.setNext(node3);
        Node h = head;
        while (h != null) {
            System.out.println(h.getValue() + " ");
            h = h.getNext();
        }
     head=   reverse3(head);
        System.out.println("===========");
        while (head != null) {
            System.out.println(head.getValue() + " ");
            head = head.getNext();
        }
    }
}

