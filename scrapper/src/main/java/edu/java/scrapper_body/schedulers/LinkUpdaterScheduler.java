package edu.java.scrapper_body.schedulers;

import edu.java.domain.dto.Chat;
import edu.java.requests.LinkUpdateRequest;
import edu.java.scrapper_body.scrapper_body.clients.ClientChain;
import edu.java.scrapper_body.scrapper_body.clients_body.Response;
import edu.java.service.ScrapperService;
import edu.java.service.send_update.SendUpdateService;
import java.net.URI;
import java.time.Duration;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public final class LinkUpdaterScheduler implements UpdateScheduler {
    private static final Duration UPDATE_CHECK_TIME = Duration.ofSeconds(30);
    private final SendUpdateService sendUpdateService;
    private final ClientChain clientChain;
    private final ScrapperService scrapperService;

    @Autowired
    public LinkUpdaterScheduler(
        SendUpdateService sendUpdateService,
        ClientChain clientChain,
        ScrapperService scrapperService
    ) {
        this.sendUpdateService = sendUpdateService;
        this.clientChain = clientChain;
        this.scrapperService = scrapperService;
    }

    @Override
    @Scheduled(fixedDelayString = "#{@'app-edu.java.configuration.ApplicationConfig'.scheduler.interval}")
    public void update() {
        log.info("Start update");

        scrapperService
            .findAllWithFilter(UPDATE_CHECK_TIME)
            .parallelStream()
            .forEach(link -> {
                    List<? extends Response> responseList = clientChain.newUpdates(URI.create(link.uri()));

                    sendUpdateService.send(
                        responseList
                            .parallelStream()
                            .filter(response -> response
                                .date()
                                .isAfter(link.lastUpdate().withOffsetSameInstant(ZoneOffset.UTC))
                            )
                            .peek(response -> scrapperService.updateLastUpdateTime(
                                link,
                                OffsetDateTime.now(ZoneOffset.UTC)
                            ))
                            .map(
                                response -> new LinkUpdateRequest(
                                    link.id(),
                                    link.uri(),
                                    creteDescription(response),
                                    scrapperService
                                        .getAllChats(link.id())
                                        .parallelStream()
                                        .map(Chat::chatId)
                                        .toList()
                                )
                            )
                            .peek(linkUpdateRequest -> log.info(linkUpdateRequest.uri()))
                            .toList()
                    );
                }
            );

        scrapperService.updateAllLastUpdateTime();

        log.info("Finish update");
    }

    private String creteDescription(Response response) {
        return "Author: " + response.author() + '\n'
            + "Message: " + response.message();
    }
}
