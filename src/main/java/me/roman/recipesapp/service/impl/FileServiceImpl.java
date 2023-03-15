package me.roman.recipesapp.service.impl;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

import static java.nio.file.StandardOpenOption.CREATE_NEW;

@Service
@RequiredArgsConstructor
public class FileServiceImpl {

    private final ObjectMapper objectMapper;

    public <T> void saveMapToFile(Map<Long, T> map, Path path) {
        try {
            createNewFile(path);
            String json = objectMapper.writeValueAsString(map);
            Files.writeString(path, json);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public <T> Map<Long, T> readMapFromFile(Path path, TypeReference<HashMap<Long, T>> typeReference) {
        try {
            String json = Files.readString(path);

            if (json.isEmpty()) {
                return new HashMap<>();
            }

            return objectMapper.readValue(json, typeReference);
        } catch (NoSuchFileException e) {
            return new HashMap<>();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void uploadFile(MultipartFile file, Path filePath) throws IOException {
        Files.createDirectories(filePath.getParent());
        Files.deleteIfExists(filePath);

        try (
                InputStream is = file.getInputStream();
                OutputStream os = Files.newOutputStream(filePath, CREATE_NEW);
                BufferedInputStream bis = new BufferedInputStream(is, 1024);
                BufferedOutputStream bos = new BufferedOutputStream(os, 1024)
        ) {
            bis.transferTo(bos);
        }
    }

    private void createNewFile(Path path) throws IOException {
        Files.deleteIfExists(path);
        Files.createFile(path);
    }

    public Path saveToFile(String content, Path path) throws IOException {
        createNewFile(path);
        return Files.writeString(path, content);
    }
}
