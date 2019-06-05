package com.bigdata.bokong.hashmap;

/**
 * @created by imp ON 2019/3/20
 */
public class HahMap<K,V> implements MapDemo<K,V>{
    Entry<K,V>[] table=null;
    public static int DefaulLength=16;
   //默认达到当前容量的3/4进行扩容
   static  double DEFAULT_LOAD_FACTOR = 0.75f;
   public HahMap(){
      this(DefaulLength,DEFAULT_LOAD_FACTOR);
   }

   public HahMap(int length,double default_factor){
       DefaulLength=length;
       DEFAULT_LOAD_FACTOR=default_factor;
      table=new Entry[length];

   }

    @Override
    public V put(K k, V v) {
       //传进来的key进行hash值
       int index=hash(k);
       Entry e=table[index];
       if (e==null){
           //hash没有冲突 这个kv不在table数组里面
           table[index]=new Entry<>(k,v,null);
       }
       else {
           //重复了的话 把kv穿进去  将原先的Entry对象e赋给next属性 相当于
           table[index]=new Entry<>(k,v,e);
           System.out.println("我们的oldvalue是====="+table[index].next.getV());
       }
        return table[index].getV() ;
    }

    public int hash(K k){
        int m=DefaulLength;
       int i= k.hashCode()%m;
       return  i>=0? i:-i;
    }

    @Override
    public V get(K k) {
        int index=hash(k);
        //没找到就返回null
        if (table[index]==null){
            return  null;
        }


        return find(table[index],k
        );
    }

    private V find(Entry<K,V> entry, K k) {
       if (k==entry.getK()||k.equals(entry.getK())){
           return  entry.getV();
       }
         else {
             if (entry.next!=null){
                 return find(entry.next,k);
             }
       }
         return null;
    }


    class Entry<K,V> implements MapDemo.Entry<K,V>{
        K k;
        V v;
        Entry<K,V> next;

         public Entry(K k, V v, Entry<K, V> next) {
             this.k = k;
             this.v = v;
             this.next = next;
         }

         public K getK() {
             return k;
         }

         public void setK(K k) {
             this.k = k;
         }

         public V getV() {
             return v;
         }

         public void setV(V v) {
             this.v = v;
         }

         public Entry<K, V> getNext() {
             return next;
         }

         public void setNext(Entry<K, V> next) {
             this.next = next;
         }

         @Override
         public V put(K k, V v) {
             return v;
         }

         @Override
         public V get(K k) {
             return v;
         }
     }
}
