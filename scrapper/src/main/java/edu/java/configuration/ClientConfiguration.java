package edu.java.configuration;

import edu.java.service.scrapper_body.clients.ClientChain;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.validation.annotation.Validated;

@Validated
@Configuration
@EnableScheduling
public class ClientConfiguration {
    @Bean(name = "clientBean")
    public ClientChain clientChain() {
        return ClientChain.defaultChain();
    }
}
