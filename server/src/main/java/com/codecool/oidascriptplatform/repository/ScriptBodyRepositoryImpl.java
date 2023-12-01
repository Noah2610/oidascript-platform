package com.codecool.oidascriptplatform.repository;

import com.codecool.oidascriptplatform.exception.*;
import com.codecool.oidascriptplatform.model.ScriptBody;
import org.springframework.beans.factory.annotation.Value;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

public class ScriptBodyRepositoryImpl implements ScriptBodyRepository {
    private static final Charset ENCODING = StandardCharsets.UTF_8;
    private static final String OIDASCRIPT_EXTENSION = ".oida";

    private final Path directory;

    public ScriptBodyRepositoryImpl(
            @Value("${scripts.directory}") String directory
    ) {
        this.directory = Path.of(directory);
    }

    @Override
    public <S extends ScriptBody> S save(S entity) {
        maybeCreateDirectory();

        Path path = getScriptBodyPathFromId(entity.getId());
        writeFile(path, entity.getBody());

        return entity;
    }

    private Path getScriptBodyPathFromId(String id) {
        return directory.resolve(id + OIDASCRIPT_EXTENSION);
    }

    private String getScriptBodyIdFromPath(Path path) {
        return path.getFileName().toString().replace(OIDASCRIPT_EXTENSION, "");
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

    private void writeFile(Path path, String body) {
        try {
            Files.writeString(path, body, ENCODING);
        } catch (IOException ex) {
            throw new WriteFileException(path, ex);
        }
    }

    private Optional<String> readFile(Path path) {
        try {
            return Optional.of(Files.readString(path, ENCODING));
        } catch (NoSuchFileException ex) {
            return Optional.empty();
        } catch (IOException ex) {
            throw new ReadFileException(path, ex);
        }
    }

    private boolean fileExists(Path path) {
        return Files.exists(path);
    }

    @Override
    public <S extends ScriptBody> Iterable<S> saveAll(Iterable<S> entities) {
        List<S> savedEntities = new LinkedList();
        for (S entity : entities) {
            savedEntities.add(save(entity));
        }
        return savedEntities;
    }

    @Override
    public Optional<ScriptBody> findById(String id) {
        Path path = getScriptBodyPathFromId(id);
        return readFile(path).map(body -> new ScriptBody(id, body));
    }

    @Override
    public boolean existsById(String id) {
        Path path = getScriptBodyPathFromId(id);
        return fileExists(path);
    }

    @Override
    public Iterable<ScriptBody> findAll() {
        return streamScriptBodyFiles()
                .map(file -> new ScriptBody(
                        getScriptBodyIdFromPath(file),
                        readFile(file).orElseThrow()
                )).toList();
    }

    private Stream<Path> streamScriptBodyFiles() {
        try {
            return Files.list(directory)
                    .filter(file -> Files.isRegularFile(file) && file.toString().endsWith(OIDASCRIPT_EXTENSION));
        } catch (IOException ex) {
            throw new ListDirectoryException(directory, ex);
        }
    }

    @Override
    public Iterable<ScriptBody> findAllById(Iterable<String> ids) {
        List<ScriptBody> scriptBodies = new LinkedList();
        for (String id : ids) {
            findById(id).ifPresent(scriptBody -> scriptBodies.add(scriptBody));
        }
        return scriptBodies;
    }

    @Override
    public long count() {
        return streamScriptBodyFiles().count();
    }

    @Override
    public void deleteById(String id) {
        Path path = getScriptBodyPathFromId(id);
        try {
            Files.delete(path);
        } catch (IOException ex) {
            throw new DeleteFileException(path, ex);
        }
    }

    @Override
    public void delete(ScriptBody scriptBody) {
        deleteById(scriptBody.getId());
    }

    @Override
    public void deleteAllById(Iterable<? extends String> ids) {
        for (String id : ids) {
            deleteById(id);
        }
    }

    @Override
    public void deleteAll(Iterable<? extends ScriptBody> scriptBodies) {
        for (ScriptBody scriptBody : scriptBodies) {
            delete(scriptBody);
        }
    }

    @Override
    public void deleteAll() {
        streamScriptBodyFiles().forEach(file -> deleteById(getScriptBodyIdFromPath(file)));
    }
}
