package edu.java.bot.service.botBody.handlers;

import com.pengrad.telegrambot.model.Update;
import edu.java.bot.service.botBody.dataClasses.Link;
import edu.java.bot.service.botBody.dataClasses.UpdatesWithExecutor;
import edu.java.bot.service.dataBase.InMemoryDataBase;
import edu.java.bot.service.dataBase.InMemoryIdLinkDataBase;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import org.jetbrains.annotations.NotNull;

public final class UpdateHandler implements Handler<UpdatesWithExecutor> {
    private final ExecutorService executorService = Executors.newVirtualThreadPerTaskExecutor();
    private final InMemoryDataBase<Long, Link> dataBase = new InMemoryIdLinkDataBase();

    public UpdateHandler() {
    }

    @Override
    public void put(@NotNull UpdatesWithExecutor value) {
        for (Update update : value.updates()) {
            executorService.execute(
                new UpdateExecutionTask(
                    value.executor(),
                    dataBase,
                    update
                )
            );
        }
    }
}
