package main.java.fileSearching;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class FileSearching {
    public static List<File> fileSearching(File dirName, String fileExtension) {
        File[] fileList = dirName.listFiles();
        List<File> files = new ArrayList<>();

        if (fileList != null) {
            for(File file: fileList) {
                if(file.isFile() && file.toString().endsWith(fileExtension)) {
                    files.add(file);
                } else if(file.isDirectory()) {
                    fileSearching(file, fileExtension);
                }
            }
        }
        return files;
    }

    public static List<String> fileReading(String filePath) throws IOException {
        return Files.lines(Paths.get(filePath)).collect(Collectors.toList());
    }
}
