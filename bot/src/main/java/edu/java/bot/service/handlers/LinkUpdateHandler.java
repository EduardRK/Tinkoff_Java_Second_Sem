package edu.java.bot.service.handlers;

import edu.java.bot.service.handlers.tasks.LinkUpdateExecutionTask;
import edu.java.bot.service.scrapper_client.ScrapperClient;
import edu.java.bot.service.telegram_bot.TelegramBotComponent;
import edu.java.exceptions.BadRequestException.BadRequestException;
import edu.java.requests.LinkUpdateRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public final class LinkUpdateHandler extends BaseHandler<LinkUpdateRequest> {
    @Autowired
    public LinkUpdateHandler(TelegramBotComponent telegramBotComponent, ScrapperClient scrapperClient) {
        super(telegramBotComponent, scrapperClient);
    }

    @Override
    public void put(LinkUpdateRequest linkUpdateRequest) {
        EXECUTOR_SERVICE.execute(
            new LinkUpdateExecutionTask(
                telegramBotComponent.telegramBot(),
                linkUpdateRequest
            )
        );
    }
}
