package edu.java.bot.service.services.update_service;

import edu.java.bot.configuration.UpdateKafkaConfig;
import edu.java.bot.service.handlers.Handler;
import edu.java.bot.service.processors.BotProcessor;
import edu.java.bot.service.scrapper_client.ScrapperClient;
import edu.java.bot.service.telegram_bot.TelegramBotComponent;
import edu.java.bot.service.telegram_bot.TrackerBot;
import edu.java.exceptions.BadRequestException.BadRequestException;
import edu.java.requests.LinkUpdateRequest;
import java.util.List;
import java.util.concurrent.ExecutionException;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.mockito.stubbing.Answer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;

@Slf4j
@SpringBootTest
class UpdateListenerServiceIntegrationTest extends KafkaIntegrationTest {
    @MockBean
    private TelegramBotComponent telegramBotComponent;
    @MockBean
    private ScrapperClient scrapperClient;
    @MockBean
    private BotProcessor botProcessor;
    @MockBean
    private TrackerBot trackerBot;
    @Autowired
    private KafkaTemplate<String, LinkUpdateRequest> kafkaTemplate;
    @Autowired
    private UpdateKafkaConfig kafkaConfig;
    @Autowired
    private UpdateService updateService;
    @MockBean
    private Handler<LinkUpdateRequest> handler;

    @Test
    void handleUpdate() throws ExecutionException, InterruptedException, BadRequestException {
        LinkUpdateRequest linkUpdateRequest = new LinkUpdateRequest(
            12,
            "Somelink.com",
            "Some description",
            List.of(12L, 3L)
        );

        Mockito.doAnswer((Answer<Void>) invocationOnMock -> {
            log.debug("Message complete");
            return null;
        }).when(handler).put(linkUpdateRequest);

        SendResult<String, LinkUpdateRequest> result =
            kafkaTemplate.send(kafkaConfig.topicName(), linkUpdateRequest).get();

        Assertions.assertEquals(
            linkUpdateRequest,
            result.getProducerRecord().value()
        );
    }
}
