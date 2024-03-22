package edu.java.responses;

import java.util.List;

public record ListLinksResponse(
    List<LinkResponse> links,
    int size
) {
}
