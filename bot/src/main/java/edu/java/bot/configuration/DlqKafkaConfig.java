package edu.java.bot.configuration;

import jakarta.validation.constraints.NotNull;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

@Validated
@ConfigurationProperties(prefix = "kafka.dlq", ignoreUnknownFields = false)
public record DlqKafkaConfig(
    @NotNull
    String bootstrapServer,
    @NotNull
    String groupId,
    @NotNull
    String autoOffsetReset,
    int lingerMs,
    @NotNull
    String topicName
) {
}
