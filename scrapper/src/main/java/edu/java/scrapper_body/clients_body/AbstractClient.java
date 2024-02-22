package edu.java.scrapper_body.clients_body;

import org.springframework.web.reactive.function.client.WebClient;

import java.net.URI;

public abstract class AbstractClient implements Client {
    protected final WebClient webClient;
    protected final Client nextClient;

    protected AbstractClient(WebClient webClient, Client nextClient) {
        this.webClient = webClient;
        this.nextClient = nextClient;
    }

    protected abstract boolean notValid(URI uri);

}
