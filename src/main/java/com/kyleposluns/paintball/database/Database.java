package com.kyleposluns.paintball.database;

public interface Database<K, V> {

  void create(K key, V value);

  V read(K key);

  V update(K key, V newValue);

  V delete(K key);

}
