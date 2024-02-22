package edu.java.configuration;

import edu.java.scrapper_body.clients.ClientChain;
import edu.java.scrapper_body.clients_body.Client;
import edu.java.shadulers.LinkUpdaterScheduler;
import edu.java.shadulers.UpdateScheduler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.validation.annotation.Validated;

@Validated
@Configuration
@EnableScheduling
public class ClientConfiguration {
    @Bean(name = "clientBean")
    public Client clientChain() {
        return ClientChain.defaultChain();
    }

    @Bean(name = "linkUpdateScheduler")
    public UpdateScheduler linkUpdateScheduler() {
        return new LinkUpdaterScheduler();
    }
}
