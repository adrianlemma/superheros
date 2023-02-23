package com.challenge.w2m.superheros.util;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class FileUtils {

    public static void saveToFile(String text, String path) throws IOException {
        File file = new File(path);
        File directory = new File(file.getParent());
        if (!directory.exists() && !directory.mkdirs()) {
            throw new IOException("Error: Cannot create timer tracker directory " + directory.getPath());
        }
        if (!file.exists() && !file.createNewFile()) {
            throw new IOException("Error: Cannot create timer tracker file");
        }
        FileWriter fileWriter = new FileWriter(file, true);
        try (BufferedWriter bufferedWriter = new BufferedWriter(fileWriter)) {
            bufferedWriter.write(text);
            bufferedWriter.newLine();
        }
    }

}
