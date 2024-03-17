package edu.java.bot.service.handlers;

import edu.java.bot.service.bot_body.data_classes.Link;
import edu.java.bot.service.handlers.tasks.LinkUpdateExecutionTask;
import edu.java.bot.service.telegram_bot.TelegramBotComponent;
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
    public LinkUpdateHandler(TelegramBotComponent telegramBotComponent) {
        super(telegramBotComponent);
    }

    @Override
    public void put(LinkUpdateRequest value) throws BadRequestException {
        chatsNotRegistered(value);
        uriNotTracked(value);

        EXECUTOR_SERVICE.execute(
            new LinkUpdateExecutionTask(
                telegramBotComponent.telegramBot(),
                value
            )
        );
    }

    private void chatsNotRegistered(LinkUpdateRequest linkUpdateRequest) throws BadRequestException {
        List<Long> ids = linkUpdateRequest.tgChatIds()
            .stream()
            .filter(id -> !DATA_BASE.dataBase().containsKey(id))
            .toList();

        if (!ids.isEmpty()) {
            throw new ChatsNotRegisteredException(ids, linkUpdateRequest.uri());
        }
    }

    private void uriNotTracked(LinkUpdateRequest linkUpdateRequest) throws BadRequestException {
        List<Long> ids = linkUpdateRequest.tgChatIds()
            .stream()
            .filter(id -> {
                try {
                    return !DATA_BASE.dataBase().get(id).contains(new Link(linkUpdateRequest.uri()));
                } catch (URISyntaxException e) {
                    throw new RuntimeException(e);
                }
            })
            .toList();

        if (!ids.isEmpty()) {
            throw new ChatsNotTrackedUriException(ids, linkUpdateRequest.uri());
        }
    }
}
