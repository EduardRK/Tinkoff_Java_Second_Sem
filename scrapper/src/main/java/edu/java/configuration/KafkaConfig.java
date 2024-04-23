package edu.java.configuration;

import jakarta.validation.constraints.NotBlank;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

@Validated
@ConfigurationProperties(prefix = "kafka", ignoreUnknownFields = false)
public record KafkaConfig(
    @NotBlank
    String bootstrapServer,
    @NotBlank
    String groupId,
    @NotBlank
    String autoOffsetReset,
    int lingerMs,
    @NotBlank
    String topicName,
    int partitions,
    short replicationsFactor
) {
}
