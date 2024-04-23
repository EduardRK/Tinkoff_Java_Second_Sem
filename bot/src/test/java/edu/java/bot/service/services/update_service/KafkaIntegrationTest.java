package edu.java.bot.service.services.update_service;

import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.KafkaContainer;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

@Testcontainers
public abstract class KafkaIntegrationTest {
    public static KafkaContainer KAFKA;

    static {
        KAFKA = new KafkaContainer(DockerImageName.parse("confluentinc/cp-kafka:7.3.2"));
        KAFKA.start();
    }

    @DynamicPropertySource
    static void kafkaProperty(DynamicPropertyRegistry registry) {
        registry.add("kafka.update-queue.bootstrap-server", KAFKA::getBootstrapServers);
        registry.add("kafka.dlq.bootstrap-server", KAFKA::getBootstrapServers);
        registry.add("app.use-queue", () -> "true");
    }
}
