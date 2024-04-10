package edu.java.bot.service.handlers;

import com.pengrad.telegrambot.model.Update;
import edu.java.bot.service.handlers.tasks.UpdateExecutionTask;
import edu.java.bot.service.scrapper_client.ScrapperClient;
import edu.java.bot.service.telegram_bot.TelegramBotComponent;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public final class UpdateHandler extends BaseHandler<List<Update>> {

    @Autowired
    public UpdateHandler(TelegramBotComponent telegramBotComponent, ScrapperClient scrapperClient) {
        super(telegramBotComponent, scrapperClient);
    }

    @Override
    public void put(List<Update> value) {
        value.forEach(update -> EXECUTOR_SERVICE.execute(
                new UpdateExecutionTask(
                    telegramBotComponent.telegramBot(),
                    scrapperClient,
                    update
                )
            )
        );
    }
}
