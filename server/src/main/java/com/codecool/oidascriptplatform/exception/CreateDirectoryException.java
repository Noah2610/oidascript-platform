package com.codecool.oidascriptplatform.exception;

import java.nio.file.Path;

public class CreateDirectoryException extends RuntimeException {
    public CreateDirectoryException(Path path, Throwable cause) {
        super(String.format("Couldn't create directory \"%s\"", path), cause);
    }

    public CreateDirectoryException(Path path) {
        this(path, null);
    }
}
