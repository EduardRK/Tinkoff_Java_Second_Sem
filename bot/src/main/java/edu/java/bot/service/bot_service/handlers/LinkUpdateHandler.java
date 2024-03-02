package edu.java.bot.service.bot_service.handlers;

import edu.java.bot.service.bot_service.bot_body.data_classes.Link;
import edu.java.bot.service.bot_service.handlers.tasks.LinkUpdateExecutionTask;
import edu.java.bot.service.bot_service.telegram_bot.DefaultTelegramBotComponent;
import edu.java.exceptions.BadRequestException.BadRequestException;
import edu.java.exceptions.BadRequestException.ChatsNotRegisteredException;
import edu.java.exceptions.BadRequestException.ChatsNotTrackedUriException;
import edu.java.requests.LinkUpdateRequest;
import java.net.URISyntaxException;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public final class LinkUpdateHandler extends BaseHandler<LinkUpdateRequest> {
    @Autowired
    public LinkUpdateHandler(DefaultTelegramBotComponent defaultTelegramBotComponent) {
        super(defaultTelegramBotComponent);
    }

    @Override
    public void put(LinkUpdateRequest value) throws BadRequestException {
        chatsNotRegistered(value);
        uriNotTracked(value);

        EXECUTOR_SERVICE.execute(
            new LinkUpdateExecutionTask(
                defaultTelegramBotComponent.telegramBot(),
                value
            )
        );
    }

    private void chatsNotRegistered(LinkUpdateRequest linkUpdateRequest) throws BadRequestException {
        List<Integer> ids = linkUpdateRequest.tgChatIds()
            .stream()
            .filter(id -> !DATA_BASE.dataBase().containsKey(id.longValue()))
            .toList();

        if (!ids.isEmpty()) {
            throw new ChatsNotRegisteredException(ids, linkUpdateRequest.url());
        }
    }

    private void uriNotTracked(LinkUpdateRequest linkUpdateRequest) throws BadRequestException {
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
            throw new ChatsNotTrackedUriException(ids, linkUpdateRequest.url());
        }
    }
}
