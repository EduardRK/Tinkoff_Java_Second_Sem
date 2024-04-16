package edu.java.scrapper_body.scrapper_body.clients_body;

import java.net.URI;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.util.retry.Retry;

public abstract class AbstractClient implements Client {
    protected final WebClient webClient;
    protected final Retry retry;
    protected final Client nextClient;

    protected AbstractClient(WebClient webClient, Retry retry, Client nextClient) {
        this.webClient = webClient;
        this.retry = retry;
        this.nextClient = nextClient;
    }

    protected abstract boolean notValid(URI uri);

}
