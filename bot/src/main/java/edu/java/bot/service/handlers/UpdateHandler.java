package edu.java.bot.service.handlers;

import com.pengrad.telegrambot.model.Update;
import edu.java.bot.service.handlers.tasks.UpdateExecutionTask;
import edu.java.bot.service.scrapper_client.ScrapperClient;
import edu.java.bot.service.telegram_bot.TelegramBotComponent;
import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public final class UpdateHandler extends BaseHandler<List<Update>> {
    private final Counter counter;

    @Autowired
    public UpdateHandler(
        TelegramBotComponent telegramBotComponent,
        ScrapperClient scrapperClient,
        MeterRegistry meterRegistry
    ) {
        super(telegramBotComponent, scrapperClient);
        this.counter = meterRegistry.counter(
            "messages_processed_total",
            "description", "Total number of processed messages"
        );
    }

    @Override
    public void put(List<Update> value) {
        value.forEach(update -> {
                EXECUTOR_SERVICE.execute(
                    new UpdateExecutionTask(
                        telegramBotComponent.telegramBot(),
                        scrapperClient,
                        update
                    )
                );
                counter.increment();
            }
        );
    }
}
