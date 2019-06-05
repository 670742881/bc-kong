package com.bigdata.bokong.tree;

/**
 * @created by imp ON 2019/3/25
 */
public class SearchTreeNode {
    public static void main(String[] args) {
      int [] a={30,12,56,122,90,1,-1};
      SearchTreeNode searchTreeNode=new SearchTreeNode();
      for (int e:a){
          searchTreeNode.put(e) ;
      }
        searchTreeNode.midIter(searchTreeNode.root);
    }

    private  TreeNode1 root;
    /**
     * 创建查找二叉树 添加元素
     */

public  TreeNode1 put(int data){
    TreeNode1 node = null;
    TreeNode1 parent=null;

 if (root==null){
     //没有树创建树的根节点
     node =new TreeNode1(0,data);
     root=node;
 }
 while (node!=null){
     //把当前节点父节点
     parent=node;
     //进来的值大于根节点 往右边走
     if (data>node.data){
            node=node.rightChild;
     }else {
         node=node.leftChild;
     }

 }
   node=new TreeNode1(0,data);
 if (parent.data<data){
     parent.leftChild=node;
     node.parent=parent;
 }else {
     parent.rightChild=node;
     node.parent=parent;
 }
 return  node;
}

//中序遍历
  public void midIter(TreeNode1 node1){
    if (node1==null){
        return;
    }
   else {
        midIter(node1.leftChild);
        System.out.println(node1);
        midIter(node1.parent);
    }
  }





    class  TreeNode1{
        private int index;
        private TreeNode1 leftChild;
        private int data;
        private TreeNode1 rightChild;

        public TreeNode1 getParent() {
            return parent;
        }

        public void setParent(TreeNode1 parent) {
            this.parent = parent;
        }

        private TreeNode1 parent;

        public TreeNode1 getLeftChild() {
            return leftChild;
        }

        public void setLeftChild(TreeNode1 leftChild) {
            this.leftChild = leftChild;
        }


        private TreeNode1(int index, int data) {

            this.index = index;
            this.data = data;
            leftChild = null;
            rightChild = null;
            parent=null;
        }
        public int getIndex() {
            return index;
        }

        public void setIndex(int index) {
            this.index = index;
        }



        public int getData() {
            return data;
        }

        public void setData(int data) {
            this.data = data;
        }

        public TreeNode1 getRightChild() {
            return rightChild;
        }

        public void setRightChild(TreeNode1 rightChild) {
            this.rightChild = rightChild;
        }


    }
}
