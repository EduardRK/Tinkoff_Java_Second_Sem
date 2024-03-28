package edu.java.bot.service.bot_body.data_classes;

import java.net.URI;
import org.jetbrains.annotations.NotNull;

public record Link(URI uri) {
    public Link(String uri) {
        this(URI.create(uri));
    }

    public Link(@NotNull Link link) {
        this(link.uri);
    }

    @Override
    public String toString() {
        return uri.toString();
    }
}
