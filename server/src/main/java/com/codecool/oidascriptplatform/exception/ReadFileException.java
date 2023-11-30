package com.codecool.oidascriptplatform.exception;

import java.nio.file.Path;

public class ReadFileException extends RuntimeException {
    public ReadFileException(Path path, Throwable cause) {
        super(String.format("Couldn't read to file \"%s\"", path), cause);
    }

    public ReadFileException(Path path) {
        this(path, null);
    }
}
