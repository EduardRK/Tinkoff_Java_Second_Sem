package edu.java.bot.service.bot_service.exeptions;

import java.util.List;

public class UriNotTrackedException extends RuntimeException {
    private final List<Integer> ids;
    private final String url;

    public UriNotTrackedException(List<Integer> ids, String url) {
        this.ids = ids;
        this.url = url;
    }

    public List<Integer> ids() {
        return ids;
    }

    public String url() {
        return url;
    }
}
