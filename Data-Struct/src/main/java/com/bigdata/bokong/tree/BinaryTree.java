package com.bigdata.bokong.tree;

import java.util.ArrayList;
import java.util.Stack;

/**
 * @created by imp ON 2019/3/22
 */
public class BinaryTree {

    //定义树节点类
    public class TreeNode<K, V> {
        private int index;
        private TreeNode leftChild;
        private V data;
        private TreeNode rightChild;
        private TreeNode parent;

        public TreeNode getParent() {
            return parent;
        }

        public void setParent(TreeNode parent) {
            this.parent = parent;
        }

        //        private TreeNode(V data) {
//            this.data = data;
//        }
        //构造器
        private TreeNode(int index, V data) {

            this.index = index;
            this.data = data;
            leftChild = null;
            rightChild = null;
        }

        public int getIndex() {
            return index;
        }

        public void setIndex(int index) {
            this.index = index;
        }

        public TreeNode getLeftChild() {
            return leftChild;
        }

        public void setLeftChild(TreeNode leftChild) {
            this.leftChild = leftChild;
        }

        public V getData() {
            return data;
        }

        public void setData(V data) {
            this.data = data;
        }

        public TreeNode getRightChild() {
            return rightChild;
        }

        public void setRightChild(TreeNode rightChild) {
            this.rightChild = rightChild;
        }
    }


    private TreeNode root = null;

    public TreeNode getRoot() {
        return root;
    }

    public void setRoot(TreeNode root) {
        this.root = root;
    }

    BinaryTree() {
        //初始化根节点为a节点
        root = new TreeNode(1, "A");
    }


    /**
     * 构建二叉树
     * A
     * B     C
     * D  E     F
     */
    public void createTree() {

        TreeNode nodeB = new TreeNode(2, "B");
        TreeNode nodeC = new TreeNode(2, "C");
        TreeNode nodeD = new TreeNode(2, "D");
        TreeNode nodeE = new TreeNode(2, "E");
        TreeNode nodeF = new TreeNode(2, "F");
        // 进行关系表示
        root.leftChild = nodeB;
        root.rightChild = nodeC;
        nodeB.leftChild = nodeD;
        nodeB.rightChild = nodeE;
        nodeC.rightChild = nodeF;
    }

//获取二叉树的高度

    public int getHeight(TreeNode node) {
        if (node == null) {
            return 0;
        } else {
            int i = getHeight(node.leftChild);
            int j = getHeight(node.rightChild);
            return i < j ? i + 1 : j + 1;
        }
    }


    //获取二叉树的节点数
    public int getSize(TreeNode node) {
        if (node == null) {
            return 0;

        } else {
            //如果只有A 左右都没有值 返回1
            //进行递归迭代
            return 1 + getSize(node.leftChild) + getSize(node.rightChild);
        }

    }


    /**
     *
     *
     *
     * @param node
     *
     * 查找二叉树
     */
 public  void  SearchBtree(TreeNode node){
         if (root==null){

         }
 }
    // 前序遍历  根左右
    public void preOrder(TreeNode node) {
        if (node == null) {
            return;
        }
        {
            System.out.println("preOrder " + node.getData());
            preOrder(node.leftChild);
            preOrder(node.rightChild);
        }
    }

    /**
     * 通过前序遍历反向生成二叉树
     */

    public TreeNode generateTree(int size, ArrayList<String> data) {
       if (data.size()==0){
           return null;
       }
       String d=data.get(0);
       TreeNode node ;
       int index=size-data.size();
      if (d.equals("#")){
          node=null;
          data.remove( 0);
          return  node;
      }
      node =new TreeNode(index,d);
      if (index==0){
          //创建根节点
          root=node;
      }
      data.remove(0);
      //创建b节点的 左右节点
      node.leftChild=generateTree(size,data);
      node.rightChild=generateTree(size,data);

      return node;

    }


    // 查找根左右
    public void preGet(TreeNode node) {
        if (node == null) {
            return;
        }
        {
            System.out.println("preOrder " + node.getData());
            preGet(node.leftChild);
            preGet(node.rightChild);
        }
    }


    //非迭代方式

    public void nonOrder(TreeNode node) {
        Stack<TreeNode> stack = new Stack();
    }


    //中序遍历 左根右
    public void midOrder(TreeNode node) {
        if (node == null) {
            return;
        }
        {
            midOrder(node.leftChild);
            System.out.println("midOrder " + node.getData());
            midOrder(node.rightChild);
        }
    }


    //后序遍历 左根右
    public void postOrder(TreeNode node) {
        if (node == null) {
            return;
        }
        {
            postOrder(node.leftChild);
            postOrder(node.rightChild);
            System.out.println("postOrder" + node.getData());
        }
    }


    public static void main(String[] args) {
        BinaryTree tree = new BinaryTree();
        tree.createTree();
        TreeNode root = tree.getRoot();

        System.out.println("数的节点数" + tree.getSize(root));
        System.out.println("数的高度" + tree.getHeight(root));
        tree.preOrder(root);
        tree.midOrder(root);
        tree.postOrder(root);

    }


}
