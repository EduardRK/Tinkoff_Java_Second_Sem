package edu.java.service.schedulers;

import com.fasterxml.jackson.core.JsonProcessingException;
import edu.java.domain.dto.Chat;
import edu.java.domain.dto.Link;
import edu.java.requests.LinkUpdateRequest;
import edu.java.service.bot_client.BotClient;
import edu.java.service.scrapper_body.clients.ClientChain;
import edu.java.service.scrapper_body.clients_body.Response;
import edu.java.service.services.ScrapperService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.net.URI;
import java.time.Duration;
import java.time.OffsetDateTime;
import java.util.List;

@Component
public final class LinkUpdaterScheduler implements UpdateScheduler {
    private static final Logger LOGGER = LogManager.getLogger();
    private static final Duration UPDATE_CHECK_TIME = Duration.ofSeconds(30);
    private final BotClient botClient;
    private final ClientChain clientChain;
    private final ScrapperService scrapperService;

    @Autowired
    public LinkUpdaterScheduler(
            BotClient botClient,
            ClientChain clientChain,
            ScrapperService scrapperService
    ) {
        this.botClient = botClient;
        this.clientChain = clientChain;
        this.scrapperService = scrapperService;
    }

    @Override
    @Scheduled(fixedDelayString = "#{@'app-edu.java.configuration.ApplicationConfig'.scheduler.interval}")
    public void update() {
        LOGGER.info("Start update");
        scrapperService.findAllWithFilter(UPDATE_CHECK_TIME)
                .parallelStream()
                .forEach(link -> {
                            try {

                                List<Response> responseList = clientChain.newUpdates(URI.create(link.uri()));

                                responseList.parallelStream()
                                        .filter(response -> response.date().isAfter(link.lastUpdate()))
                                        .map(response -> new LinkUpdateRequest(
                                                link.id(),
                                                link.uri(),
                                                creteDescription(response),
                                                scrapperService.allChats(link.id())
                                                        .parallelStream()
                                                        .map(Chat::chatId)
                                                        .toList()
                                        ))
                                        .forEach(botClient::sendUpdate);
                                scrapperService.updateLastUpdateTime(
                                        new Link(
                                                link.id(),
                                                link.uri(),
                                                link.lastCheck(),
                                                responseList.parallelStream()
                                                        .map(Response::date)
                                                        .max(OffsetDateTime::compareTo)
                                                        .orElse(OffsetDateTime.now())
                                        )
                                );

                            } catch (JsonProcessingException e) {
                                throw new RuntimeException(e);
                            }
                        }
                );
        LOGGER.info("End update");
    }

    private String creteDescription(Response response) {
        return "Author: " + response.author() + '\n'
                + "Message: " + response.message();
    }
}
