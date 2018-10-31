package main.java.fileSearching;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class TextSearching {
    private static List<String> filesWithText = new ArrayList<>();

    public static List<String> Search(List<File> files, String searchingText) throws IOException {
        filesWithText.clear();
        for(File file: files) {
            if(Files.lines(Paths.get(file.toString())).anyMatch(line -> line.contains(searchingText))) {
                filesWithText.add(file.toString());
            }
        }
        return filesWithText;
    }
}
