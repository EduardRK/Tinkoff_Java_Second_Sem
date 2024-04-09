package edu.java.bot.configuration;

import jakarta.validation.constraints.NotBlank;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

@Validated
@ConfigurationProperties(prefix = "kafka.update-queue", ignoreUnknownFields = false)
public record UpdateKafkaConfig(
    @NotBlank
    String bootstrapServer,
    @NotBlank
    String groupId,
    @NotBlank
    String topicName,
    int concurrency,
    int pollTimeout,
    int partitions,
    int replicationsFactor
) {
}
