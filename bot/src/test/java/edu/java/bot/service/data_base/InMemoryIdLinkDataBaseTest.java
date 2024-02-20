package edu.java.bot.service.data_base;

import edu.java.bot.service.bot_body.data_classes.Link;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

class InMemoryIdLinkDataBaseTest {

    @Test
    void dataBase() {
        InMemoryDataBase<Long, Link> inMemoryDataBase = new InMemoryIdLinkDataBase();
        Map<Long, Set<Link>> map = new ConcurrentHashMap<>();

        Assertions.assertEquals(
            map,
            inMemoryDataBase.dataBase()
        );
    }

    @Test
    void waitingNextCommand() {
        InMemoryDataBase<Long, Link> inMemoryDataBase = new InMemoryIdLinkDataBase();
        Map<Long, String> map = new ConcurrentHashMap<>();

        Assertions.assertEquals(
            map,
            inMemoryDataBase.waitingNextCommand()
        );
    }
}
