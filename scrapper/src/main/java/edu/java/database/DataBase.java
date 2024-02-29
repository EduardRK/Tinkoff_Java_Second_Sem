package edu.java.database;

import java.util.Set;

public interface DataBase<K, V> {
    Set<V> allDataByKey(K key);

    void addValue(K key, V value);

    void deleteValue(K key, V value);

    void addNewUserByKey(K key);

    void deleteUserByKey(K key);
}
