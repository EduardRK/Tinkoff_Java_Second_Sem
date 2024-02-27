package edu.java.bot.service.data_base;

import java.util.Map;
import java.util.Set;

public interface InMemoryDataBase<K, V> {
    Map<K, Set<V>> dataBase();

    Map<K, String> waitingNextCommand();
}
