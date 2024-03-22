package edu.java.bot.domain;

import edu.java.bot.service.bot_body.data_classes.Link;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public final class InMemoryIdLinkDataBase implements InMemoryDataBase<Long, Link> {
    private final Map<Long, Set<Link>> dataBase = new ConcurrentHashMap<>();
    private final Map<Long, String> waitingNextCommand = new ConcurrentHashMap<>();

    @Autowired
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
