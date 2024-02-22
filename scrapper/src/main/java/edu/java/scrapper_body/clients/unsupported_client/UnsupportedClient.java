package edu.java.scrapper_body.clients.unsupported_client;

import edu.java.scrapper_body.clients_body.Client;
import edu.java.scrapper_body.clients_body.Response;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

public final class UnsupportedClient implements Client {
    public UnsupportedClient() {
    }

    @Override
    public List<Response> newUpdates(URI uri) {
        return new ArrayList<>();
    }

}
