package edu.java.bot.service.bot_service.bot_body.data_base;

import edu.java.bot.service.bot_service.bot_body.data_classes.Link;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public final class InMemoryIdLinkDataBase implements InMemoryDataBase<Integer, Link> {
    private final Map<Integer, Set<Link>> dataBase = new ConcurrentHashMap<>();
    private final Map<Integer, String> waitingNextCommand = new ConcurrentHashMap<>();

    @Autowired
    public InMemoryIdLinkDataBase() {

    }

    @Override
    public Map<Integer, Set<Link>> dataBase() {
        return dataBase;
    }

    @Override
    public Map<Integer, String> waitingNextCommand() {
        return waitingNextCommand;
    }
}
