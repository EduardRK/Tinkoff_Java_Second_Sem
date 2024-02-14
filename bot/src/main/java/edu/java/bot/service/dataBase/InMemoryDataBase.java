package edu.java.bot.service.dataBase;

import java.util.Map;
import java.util.Set;

public interface InMemoryDataBase<K, V> {
    Map<K, Set<V>> dataBase();

    Map<K, String> waitingNextCommand();
}
