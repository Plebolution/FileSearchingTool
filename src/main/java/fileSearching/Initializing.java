package main.java.fileSearching;

import java.io.File;
import java.util.List;

import static main.java.fileSearching.FileSearching.fileSearching;

public class Initializing{
    private  String dirName;
    private  String fileExtension;

    private List<File> files;

    public Initializing(String dirName, String fileExtension) {
            this.dirName = dirName;
            this.fileExtension = fileExtension;
    }

    public List<File> find() {
        File dir = new File(dirName);
        files = fileSearching(dir, fileExtension);

        return files;
    }
}