package edu.java.bot.configuration;

import jakarta.validation.constraints.NotBlank;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

@Validated
@ConfigurationProperties(prefix = "kafka.dlq", ignoreUnknownFields = false)
public record DlqKafkaConfig(
    @NotBlank
    String bootstrapServer,
    @NotBlank
    String groupId,
    @NotBlank
    String autoOffsetReset,
    int lingerMs,
    @NotBlank
    String topicName
) {
}
