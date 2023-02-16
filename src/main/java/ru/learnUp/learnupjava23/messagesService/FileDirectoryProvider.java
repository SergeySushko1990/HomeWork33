package ru.learnUp.learnupjava23.messagesService;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@Component
public class FileDirectoryProvider {

    private final String dir;

    public FileDirectoryProvider(@Value("${files-dir}") String dir) {
        this.dir = dir;
    }

    @PostConstruct
    private void init() throws IOException {
        Path path = Path.of(dir);
        if (!Files.exists(path)) {
            Files.createDirectory(path);
        }
    }

    public void writeString(String fileName, String text) throws IOException {
        Files.writeString(Path.of(dir, fileName), text);
    }

    public byte[] getFile(String fileName) throws IOException {
        return Files.readAllBytes(Path.of(dir, fileName));
    }
}
