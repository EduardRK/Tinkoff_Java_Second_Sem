package edu.java.bot.domain;

import java.util.Map;
import java.util.Set;

public interface InMemoryDataBase<K, V> {
    Map<K, Set<V>> dataBase();

    Map<K, String> waitingNextCommand();
}
