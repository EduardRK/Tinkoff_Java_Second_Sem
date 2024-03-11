package edu.java.bot.service.handlers;

import com.pengrad.telegrambot.model.Update;
import edu.java.bot.service.handlers.tasks.UpdateExecutionTask;
import edu.java.bot.service.telegram_bot.TelegramBotComponent;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public final class UpdateHandler extends BaseHandler<List<Update>> {

    @Autowired
    public UpdateHandler(TelegramBotComponent telegramBotComponent) {
        super(telegramBotComponent);
    }

    @Override
    public void put(List<Update> value) {
        value.forEach(update -> EXECUTOR_SERVICE.execute(
                new UpdateExecutionTask(
                    telegramBotComponent.telegramBot(),
                    DATA_BASE,
                    update
                )
            )
        );
    }
}
