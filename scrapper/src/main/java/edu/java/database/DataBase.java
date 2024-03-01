package edu.java.database;

import java.util.List;

public interface DataBase<K, V> {
    List<V> allDataByKey(K key);

    void addValue(K key, V value);

    void deleteValue(K key, V value);

    void addNewUserByKey(K key);

    void deleteUserByKey(K key);
}
