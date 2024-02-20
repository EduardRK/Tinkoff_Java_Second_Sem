package edu.java.bot.service.data_base;

import edu.java.bot.service.bot_body.data_classes.Link;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public final class InMemoryIdLinkDataBase implements InMemoryDataBase<Long, Link> {
    private final Map<Long, Set<Link>> dataBase = new ConcurrentHashMap<>();
    private final Map<Long, String> waitingNextCommand = new ConcurrentHashMap<>();

    public InMemoryIdLinkDataBase() {

    }

    @Override
    public Map<Long, Set<Link>> dataBase() {
        return dataBase;
    }

    @Override
    public Map<Long, String> waitingNextCommand() {
        return waitingNextCommand;
    }
}
