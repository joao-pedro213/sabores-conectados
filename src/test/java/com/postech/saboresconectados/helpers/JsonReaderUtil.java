package com.postech.saboresconectados.helpers;

import org.springframework.util.StreamUtils;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

public class JsonReaderUtil {
    private final String basePath;

    public JsonReaderUtil(String basePath) {
        this.basePath = basePath;
    }

    public String readJsonFromFile(String filePath) throws IOException {
        ClassLoader classLoader = JsonReaderUtil.class.getClassLoader();
        try (InputStream inputStream = classLoader.getResourceAsStream("%s/%s".formatted(this.basePath, filePath))) {
            if (inputStream == null) {
                throw new IOException("JSON file not found in classpath: " + filePath);
            }
            return StreamUtils.copyToString(inputStream, StandardCharsets.UTF_8);
        }
    }
}
