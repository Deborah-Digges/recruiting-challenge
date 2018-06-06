package com.role.play.game.model;

import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import static java.nio.file.Files.*;

/**
 * {@inheritDoc}
 */
public class GameStateMarshallerImpl implements GameStateMarshaller{
    @Override
    public void save(GameState gameState, String fileName) {
        Path filePath = Paths.get(fileName);
        createFileIfNotExist(filePath);
        try (ObjectOutput output = new ObjectOutputStream(newOutputStream(filePath))) {
            output.writeObject(gameState);
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    private boolean createFileIfNotExist(Path path) {
        if (exists(path)) {
            return false;
        }
        try {
            Files.createFile(path);
            return true;
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    @Override
    public GameState restore(String path) {
        Path filePath = Paths.get(path);
        if (notExists(filePath)) {
            throw new IllegalStateException("File does not exist" + filePath.toAbsolutePath().toString());
        }
        try (ObjectInput input = new ObjectInputStream(newInputStream(filePath))) {
            return (GameState) input.readObject();
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        } catch (ClassNotFoundException e) {
            throw new IllegalStateException(e);
        }
    }
}
