package com.bigdata.bokong.hashmap;

/**
 * @created by imp ON 2019/3/20
 */
public interface MapDemo<K,V>{
    public V put(K k,V v);
    public V get(K k);
    public  interface  Entry<K,V>{
        public V put(K k,V v);
        public V get(K k);
        Entry next = null;
    }
}
