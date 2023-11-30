package com.codecool.oidascriptplatform.repository;

import com.codecool.oidascriptplatform.exception.CreateDirectoryException;
import com.codecool.oidascriptplatform.exception.WriteFileException;
import com.codecool.oidascriptplatform.model.ScriptBody;
import org.springframework.beans.factory.annotation.Value;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;

public class ScriptBodyRepositoryImpl implements ScriptBodyRepository {
    private static Charset ENCODING = StandardCharsets.UTF_8;

    private final Path directory;

    public ScriptBodyRepositoryImpl(
            @Value("${scripts.directory}") String directory
    ) {
        this.directory = Path.of(directory);
    }

    @Override
    public <S extends ScriptBody> S save(S entity) {
        maybeCreateDirectory();

        Path path = directory.resolve(entity.getPath());

        try {
            Files.writeString(path, entity.getBody(), ENCODING);
        } catch (IOException ex) {
            throw new WriteFileException(path, ex);
        }

        return null;
    }

    private void maybeCreateDirectory() {
        if (Files.exists(directory)) {
            return;
        }

        try {
            Files.createDirectory(directory);
        } catch (IOException ex) {
            throw new CreateDirectoryException(directory, ex);
        }
    }

    @Override
    public <S extends ScriptBody> Iterable<S> saveAll(Iterable<S> entities) {
        return null;
    }

    @Override
    public Optional<ScriptBody> findById(String path) {
        return Optional.empty();
    }

    @Override
    public boolean existsById(String path) {
        return false;
    }

    @Override
    public Iterable<ScriptBody> findAll() {
        return null;
    }

    @Override
    public Iterable<ScriptBody> findAllById(Iterable<String> path) {
        return null;
    }

    @Override
    public long count() {
        return 0;
    }

    @Override
    public void deleteById(String path) {

    }

    @Override
    public void delete(ScriptBody entity) {

    }

    @Override
    public void deleteAllById(Iterable<? extends String> paths) {

    }

    @Override
    public void deleteAll(Iterable<? extends ScriptBody> entities) {

    }

    @Override
    public void deleteAll() {

    }
}
