package com.codecool.oidascriptplatform.exception;

import java.nio.file.Path;

public class WriteFileException extends RuntimeException {
    public WriteFileException(Path path, Throwable cause) {
        super(String.format("Couldn't write to file \"%s\"", path), cause);
    }

    public WriteFileException(Path path) {
        this(path, null);
    }
}
