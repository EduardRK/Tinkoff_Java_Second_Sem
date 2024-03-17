package edu.java.service.schedulers;

import com.fasterxml.jackson.core.JsonProcessingException;
import edu.java.domain.dto.Chat;
import edu.java.domain.dto.Link;
import edu.java.domain.repository.ChatLinkRepository;
import edu.java.domain.repository.LinkRepository;
import edu.java.requests.LinkUpdateRequest;
import edu.java.service.bot_client.BotClient;
import edu.java.service.scrapper_body.clients.ClientChain;
import edu.java.service.scrapper_body.clients_body.Response;
import java.time.Duration;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public final class LinkUpdaterScheduler implements UpdateScheduler {
    private static final Logger LOGGER = LogManager.getLogger();
    private static final Duration UPDATE_CHECK_TIME = Duration.ofHours(2);
    private final BotClient botClient;
    private final ClientChain clientChain;
    private final LinkRepository linkRepository;
    private final ChatLinkRepository chatLinkRepository;

    @Autowired
    public LinkUpdaterScheduler(
        BotClient botClient,
        ClientChain clientChain,
        LinkRepository linkRepository,
        ChatLinkRepository chatLinkRepository
    ) {
        this.botClient = botClient;
        this.clientChain = clientChain;
        this.linkRepository = linkRepository;
        this.chatLinkRepository = chatLinkRepository;
    }

    @Override
    @Scheduled(fixedDelayString = "#{@scheduler.interval}")
    public void update() {
        LOGGER.info("Link update");
        List<Link> linkList = linkRepository.findAllWithFilter(UPDATE_CHECK_TIME);
        linkList.parallelStream()
            .forEach(link -> {
                    try {

                        clientChain.newUpdates(link.uri())
                            .parallelStream()
                            .filter(response -> response.date().isAfter(link.lastUpdate()))
                            .map(response -> new LinkUpdateRequest(
                                link.id(),
                                link.uri().toString(),
                                creteDescription(response),
                                chatLinkRepository.allChats(link.id()).stream().map(Chat::tgChatId).toList()
                            ))
                            .forEach(botClient::sendUpdate);

                    } catch (JsonProcessingException e) {
                        throw new RuntimeException(e);
                    }
                }
            );
    }

    private String creteDescription(Response response) {
        return "Author: " + response.author() + '\n'
            + "Message: " + response.message();
    }
}
