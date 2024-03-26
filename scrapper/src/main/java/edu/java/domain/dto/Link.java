package edu.java.domain.dto;

import java.net.URI;
import java.time.OffsetDateTime;

public record Link(
    long id,
    String uri,
    OffsetDateTime lastCheck,
    OffsetDateTime lastUpdate
) {
    public static Link link(URI uri) {
        return new Link(0, uri.toString(), OffsetDateTime.now(), OffsetDateTime.now());
    }
}
