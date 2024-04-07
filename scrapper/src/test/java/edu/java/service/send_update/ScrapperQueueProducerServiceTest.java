package edu.java.service.send_update;

import edu.java.configuration.KafkaConfig;
import edu.java.requests.LinkUpdateRequest;
import java.time.Duration;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.testcontainers.shaded.org.awaitility.Awaitility;
import static java.util.concurrent.TimeUnit.SECONDS;

@SpringBootTest
class ScrapperQueueProducerServiceTest extends KafkaIntegrationTest {
    @Autowired
    private KafkaTemplate<String, LinkUpdateRequest> kafkaTemplate;
    @Autowired
    private KafkaConfig kafkaConfig;
    @Autowired
    private ConsumerFactory<String, LinkUpdateRequest> consumerFactory;
    private ScrapperQueueProducerService producerService;
    private KafkaConsumer<String, LinkUpdateRequest> consumer;

    @BeforeEach
    void setUp() {
        producerService = new ScrapperQueueProducerService(kafkaConfig, kafkaTemplate);
        consumer = (KafkaConsumer<String, LinkUpdateRequest>) consumerFactory.createConsumer();
    }

    @Test
    void testSendMessage() throws InterruptedException {
        LinkUpdateRequest linkUpdateRequest = new LinkUpdateRequest(
            12,
            "Somelink.com",
            "Some description",
            List.of(12L, 3L)
        );
        producerService.send(linkUpdateRequest);

        consumer.subscribe(Collections.singleton("test_topic"));

        AtomicReference<ConsumerRecords<String, LinkUpdateRequest>> records = new AtomicReference<>();

        // Ждем, пока сообщение не будет получено в топике
        Awaitility.await().atMost(10, SECONDS).until(() -> {
            ConsumerRecords<String, LinkUpdateRequest> polledRecords = consumer.poll(Duration.ofSeconds(1));
            records.set(polledRecords);
            return !polledRecords.isEmpty();
        });

        ConsumerRecords<String, LinkUpdateRequest> recordsValue = records.get();

        Assertions.assertFalse(recordsValue.isEmpty());

        for (ConsumerRecord<String, LinkUpdateRequest> record : recordsValue) {
            Assertions.assertEquals(linkUpdateRequest, record.value());
        }
    }
}
