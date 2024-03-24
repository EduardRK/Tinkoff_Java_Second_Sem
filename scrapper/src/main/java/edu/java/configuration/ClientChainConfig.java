package edu.java.configuration;

import edu.java.service.scrapper_body.clients.ClientChain;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;

@Configuration
@EnableScheduling
public class ClientChainConfig {
    public ClientChainConfig() {

    }

    @Bean(name = "clientBean")
    public ClientChain clientChain() {
        return ClientChain.defaultChain();
    }
}
