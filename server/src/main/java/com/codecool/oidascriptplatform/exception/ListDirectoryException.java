package com.codecool.oidascriptplatform.exception;

import java.nio.file.Path;

public class ListDirectoryException extends RuntimeException {
    public ListDirectoryException(Path path, Throwable cause) {
        super(String.format("Couldn't list files in directory \"%s\"", path), cause);
    }

    public ListDirectoryException(Path path) {
        this(path, null);
    }
}
