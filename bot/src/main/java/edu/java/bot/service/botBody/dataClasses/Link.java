package edu.java.bot.service.botBody.dataClasses;

import java.net.URI;
import java.net.URISyntaxException;

public record Link(URI uri) {
    public Link(String uri) throws URISyntaxException {
        this(new URI(uri));
    }

    public Link(Link link) {
        this(link.uri);
    }

    @Override
    public String toString() {
        return uri.toString();
    }
}
