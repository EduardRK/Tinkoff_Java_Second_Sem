package edu.java.bot.service.bot_service.handlers;

import com.pengrad.telegrambot.model.Update;
import edu.java.bot.service.bot_service.handlers.tasks.UpdateExecutionTask;
import edu.java.bot.service.bot_service.telegram_bot.DefaultTelegramBotComponent;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public final class UpdateHandler extends BaseHandler<List<Update>> {

    @Autowired
    public UpdateHandler(DefaultTelegramBotComponent defaultTelegramBotComponent) {
        super(defaultTelegramBotComponent);
    }

    @Override
    public void put(List<Update> value) {
        value.forEach(update -> EXECUTOR_SERVICE.execute(
                new UpdateExecutionTask(
                    defaultTelegramBotComponent.telegramBot(),
                    DATA_BASE,
                    update
                )
            )
        );
    }
}
