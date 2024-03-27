package edu.java.scrapper_body.scrapper_body.clients_body;

import java.net.URI;
import java.util.List;

public interface Client {
    List<? extends Response> newUpdates(URI uri);
}
