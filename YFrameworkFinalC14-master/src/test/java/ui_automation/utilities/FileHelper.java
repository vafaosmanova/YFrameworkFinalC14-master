package ui_automation.utilities;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class FileHelper {
    public static boolean fileExists(String filePath) {
        File file = new File(filePath);
        return file.exists() && file.isFile();
    }

    public static boolean compareFilesOnBytes(String expectedFilePath, String actualFilePath) {
        File actualFile = new File(actualFilePath);
        File expectedFile = new File(expectedFilePath);
        try {
            return FileUtils.contentEquals(expectedFile, actualFile);
        } catch (IOException e) {
            throw new RuntimeException("An issue occur when reading one of these files: \n File 1: " + actualFilePath + "\n File 2: " + expectedFilePath, e);
        }
    }

    public static void deleteFile(String filePath) {
        Path path = Paths.get(filePath);
        try {
            Files.delete(path);
        } catch (IOException e) {
            throw new RuntimeException("There was an issie when trying to delete the file at: " + filePath, e);
        }
    }


}
