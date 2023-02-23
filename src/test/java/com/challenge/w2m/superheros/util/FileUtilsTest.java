package com.challenge.w2m.superheros.util;

import java.io.File;
import java.io.IOException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.challenge.w2m.superheros.constants.Constants.*;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class FileUtilsTest {

    @AfterEach
    void removeTestData() {
        File file = new File(FILE_PATH);
        File directory = new File(file.getParent());
        if (file.exists()) {
            file.delete();
        }
        if (directory.exists()) {
            directory.delete();
        }
    }

    @Test
    @DisplayName("Test when file is successfully created")
    void testWhenFileIsSuccessfullyCreated() throws IOException {
        FileUtils.saveToFile("any text", FILE_PATH);
        File result = new File(FILE_PATH);
        assertTrue(result.exists());
        assertTrue(result.length() > 0);
    }

    @Test
    @DisplayName("Test when directory cannot be created")
    void testWhenDirectoryICannotBeCreated() throws IOException {
        assertThrows(IOException.class, () -> FileUtils.saveToFile("any text", ERROR_DIRECTORY));
    }

    @Test
    @DisplayName("Test when file cannot be created")
    void testWhenFileICannotBeCreated() throws IOException {
        assertThrows(IOException.class, () -> FileUtils.saveToFile("any text", ERROR_FILE));
    }

}
