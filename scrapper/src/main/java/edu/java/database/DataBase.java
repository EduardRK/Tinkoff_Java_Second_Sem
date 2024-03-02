package edu.java.database;

import edu.java.exceptions.BadRequestException.BadRequestException;
import edu.java.exceptions.NotFoundException.NotFoundException;
import java.util.Set;

public interface DataBase<K, V> {
    Set<V> allDataByKey(K key) throws BadRequestException;

    void addValue(K key, V value) throws BadRequestException;

    void deleteValue(K key, V value) throws BadRequestException, NotFoundException;

    void addNewUserByKey(K key) throws BadRequestException;

    void deleteUserByKey(K key) throws BadRequestException, NotFoundException;
}
