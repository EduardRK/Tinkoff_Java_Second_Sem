package edu.java.scrapper_body.scrapper_body.clients.support;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public final class FileContent implements Content {
    private final Path path;

    public FileContent(Path path) {
        this.path = path;
    }

    public FileContent(String path) {
        this(Path.of(path));
    }

    public FileContent(File path) {
        this(path.getPath());
    }

    @Override
    public String content() throws IOException {
        return Files.readString(path);
    }
}
