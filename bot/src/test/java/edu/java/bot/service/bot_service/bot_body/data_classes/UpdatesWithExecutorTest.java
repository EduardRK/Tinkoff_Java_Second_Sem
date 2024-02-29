package edu.java.bot.service.bot_service.bot_body.data_classes;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.Update;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class UpdatesWithExecutorTest {

    @Test
    void executor() {
        TelegramBot telegramBot = new TelegramBot("Some token");
        List<Update> updateList = new ArrayList<>(List.of(new Update()));
        UpdatesWithExecutor updatesWithExecutor = new UpdatesWithExecutor(telegramBot, updateList);

        Assertions.assertEquals(
            telegramBot,
            updatesWithExecutor.executor()
        );
    }

    @Test
    void updates() {
        TelegramBot telegramBot = new TelegramBot("Some token");
        List<Update> updateList = new ArrayList<>(List.of(new Update()));
        UpdatesWithExecutor updatesWithExecutor = new UpdatesWithExecutor(telegramBot, updateList);

        Assertions.assertEquals(
            updateList,
            updatesWithExecutor.updates()
        );
    }
}
