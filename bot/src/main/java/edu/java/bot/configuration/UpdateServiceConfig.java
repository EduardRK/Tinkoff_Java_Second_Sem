package edu.java.bot.configuration;

import edu.java.bot.service.handlers.Handler;
import edu.java.bot.service.services.update_service.UpdateHandlerService;
import edu.java.bot.service.services.update_service.UpdateListenerService;
import edu.java.bot.service.services.update_service.UpdateService;
import edu.java.requests.LinkUpdateRequest;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UpdateServiceConfig {

    public UpdateServiceConfig() {

    }

    @Bean
    @ConditionalOnProperty(prefix = "app", name = "use-queue", havingValue = "false")
    public UpdateService updateHandlerService(Handler<LinkUpdateRequest> handler) {
        return new UpdateHandlerService(handler);
    }

    @Bean
    @ConditionalOnProperty(prefix = "app", name = "use-queue", havingValue = "true")
    public UpdateService updateListenerService(Handler<LinkUpdateRequest> handler) {
        return new UpdateListenerService(handler);
    }
}
