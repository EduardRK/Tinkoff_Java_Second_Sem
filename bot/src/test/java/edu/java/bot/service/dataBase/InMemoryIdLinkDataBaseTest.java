package edu.java.bot.service.dataBase;

import edu.java.bot.service.botBody.dataClasses.Link;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

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
