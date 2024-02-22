package edu.java.scrapper_body.clients_body;

import com.fasterxml.jackson.core.JsonProcessingException;
import java.net.URI;
import java.util.List;

public interface Client {
    List<Response> newUpdates(URI uri) throws JsonProcessingException;
}
