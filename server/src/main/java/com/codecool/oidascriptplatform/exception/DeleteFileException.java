package com.codecool.oidascriptplatform.exception;

import java.nio.file.Path;

public class DeleteFileException extends RuntimeException {
    public DeleteFileException(Path path, Throwable cause) {
        super(String.format("Couldn't delete file \"%s\"", path), cause);
    }

    public DeleteFileException(Path path) {
        this(path, null);
    }
}
