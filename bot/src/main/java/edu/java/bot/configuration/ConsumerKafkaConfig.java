package edu.java.bot.configuration;

import edu.java.requests.LinkUpdateRequest;
import java.util.HashMap;
import java.util.Map;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.config.KafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.listener.ConcurrentMessageListenerContainer;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.kafka.support.serializer.JsonSerializer;

@Configuration
@EnableKafka
public class ConsumerKafkaConfig {

    private final UpdateKafkaConfig updateKafkaConfig;
    private final DlqKafkaConfig dlqKafkaConfig;

    public ConsumerKafkaConfig(UpdateKafkaConfig updateKafkaConfig, DlqKafkaConfig dlqKafkaConfig) {
        this.updateKafkaConfig = updateKafkaConfig;
        this.dlqKafkaConfig = dlqKafkaConfig;
    }

    @Bean
    public KafkaListenerContainerFactory<ConcurrentMessageListenerContainer<String, LinkUpdateRequest>>
    kafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, LinkUpdateRequest> factory =
            new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory());
        factory.setConcurrency(updateKafkaConfig.concurrency());
        factory.getContainerProperties().setPollTimeout(updateKafkaConfig.pollTimeout());

        return factory;
    }

    @Bean
    public ConsumerFactory<String, LinkUpdateRequest> consumerFactory() {
        return new DefaultKafkaConsumerFactory<>(consumerConfigs());
    }

    @Bean
    public Map<String, Object> consumerConfigs() {
        Map<String, Object> props = new HashMap<>();

        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, updateKafkaConfig.bootstrapServer());
        props.put(ConsumerConfig.GROUP_ID_CONFIG, updateKafkaConfig.groupId());
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class);

        return props;
    }

    @Bean
    public KafkaTemplate<String, LinkUpdateRequest> kafkaTemplate(
        ProducerFactory<String, LinkUpdateRequest> producerFactory
    ) {
        return new KafkaTemplate<>(producerFactory);
    }

    @Bean
    public ProducerFactory<String, LinkUpdateRequest> producerFactory() {
        return new DefaultKafkaProducerFactory<>(senderProps());
    }

    private Map<String, Object> senderProps() {
        Map<String, Object> props = new HashMap<>();

        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, dlqKafkaConfig.bootstrapServer());
        props.put(ConsumerConfig.GROUP_ID_CONFIG, dlqKafkaConfig.groupId());
        props.put(ProducerConfig.LINGER_MS_CONFIG, dlqKafkaConfig.lingerMs());
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);

        return props;
    }
}
