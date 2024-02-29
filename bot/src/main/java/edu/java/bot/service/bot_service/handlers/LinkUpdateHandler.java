package edu.java.bot.service.bot_service.handlers;

import edu.java.bot.api.requests.LinkUpdateRequest;
import edu.java.bot.service.bot_service.bot_body.data_classes.Link;
import edu.java.bot.service.bot_service.bot_body.exeptions.ChatNotRegisteredException;
import edu.java.bot.service.bot_service.bot_body.exeptions.UriNotTrackedException;
import edu.java.bot.service.bot_service.handlers.tasks.LinkUpdateExecutionTask;
import edu.java.bot.service.bot_service.telegram_bot.TelegramBotComponent;
import java.net.URISyntaxException;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public final class LinkUpdateHandler extends BaseHandler<LinkUpdateRequest> {
    @Autowired
    public LinkUpdateHandler(TelegramBotComponent telegramBotComponent) {
        super(telegramBotComponent);
    }

    @Override
    public void put(LinkUpdateRequest value) {
        chatNotRegistered(value);
        uriNotTracked(value);

        EXECUTOR_SERVICE.execute(
            new LinkUpdateExecutionTask(
                telegramBotComponent.telegramBot(),
                value
            )
        );
    }

    private void chatNotRegistered(LinkUpdateRequest linkUpdateRequest) {
        List<Integer> ids = linkUpdateRequest.tgChatIds()
            .stream()
            .filter(id -> !DATA_BASE.dataBase().containsKey(id.longValue()))
            .toList();

        if (!ids.isEmpty()) {
            throw new ChatNotRegisteredException(ids);
        }
    }

    private void uriNotTracked(LinkUpdateRequest linkUpdateRequest) {
        List<Integer> ids = linkUpdateRequest.tgChatIds()
            .stream()
            .filter(id -> {
                try {
                    return !DATA_BASE.dataBase().get(id.longValue()).contains(new Link(linkUpdateRequest.url()));
                } catch (URISyntaxException e) {
                    throw new RuntimeException(e);
                }
            })
            .toList();

        if (!ids.isEmpty()) {
            throw new UriNotTrackedException(ids, linkUpdateRequest.url());
        }
    }
}
