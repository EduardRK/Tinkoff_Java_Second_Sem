package edu.java.service.scrapper_body.clients_body;

import java.net.URI;
import java.util.List;

public interface Client {
    List<Response> newUpdates(URI uri);
}
