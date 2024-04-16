package edu.java.configuration;

import edu.java.scrapper_body.scrapper_body.clients.ClientChain;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.scheduling.annotation.EnableScheduling;
import reactor.util.retry.Retry;

@Configuration
@EnableScheduling
public class ClientConfig {
    public ClientConfig() {

    }

    @Bean(name = "clientBean")
    public ClientChain clientChain(Retry retry) {
        return ClientChain.defaultChain(retry);
    }

    @Bean
    public Converter<String, ApplicationConfig.AccessType> stringToAccessTypeConverter() {
        return new StringToAccessTypeConverter();
    }
}
