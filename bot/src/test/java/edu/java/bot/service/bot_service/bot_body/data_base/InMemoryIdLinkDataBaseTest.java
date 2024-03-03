package edu.java.bot.service.bot_service.bot_body.data_base;

import edu.java.bot.service.bot_service.bot_body.data_classes.Link;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class InMemoryIdLinkDataBaseTest {

    @Test
    void dataBase() {
        InMemoryDataBase<Integer, Link> inMemoryDataBase = new InMemoryIdLinkDataBase();
        Map<Integer, Set<Link>> map = new ConcurrentHashMap<>();

        Assertions.assertEquals(
            map,
            inMemoryDataBase.dataBase()
        );
    }

    @Test
    void waitingNextCommand() {
        InMemoryDataBase<Integer, Link> inMemoryDataBase = new InMemoryIdLinkDataBase();
        Map<Integer, String> map = new ConcurrentHashMap<>();

        Assertions.assertEquals(
            map,
            inMemoryDataBase.waitingNextCommand()
        );
    }
}
