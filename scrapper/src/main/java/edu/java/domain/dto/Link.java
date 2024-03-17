package edu.java.domain.dto;

import java.net.URI;
import java.time.OffsetDateTime;

public record Link(
    long id,
    URI uri,
    OffsetDateTime lastCheck,
    OffsetDateTime lastUpdate
) {
    public Link(URI uri) {
        this(0, uri, OffsetDateTime.now(), OffsetDateTime.now());
    }
}
