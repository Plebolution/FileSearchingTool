package main.java.sample.Controllers;

import javafx.concurrent.Task;
import main.java.fileSearching.Initializing;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import java.io.File;
import java.io.IOException;
import java.util.List;

import static main.java.fileSearching.FileSearching.fileReading;
import static main.java.fileSearching.TextSearching.Search;

public class InputController {

    private int fromIndex = 0;

    private String dirName;
    private String fileExt;
    private String searchText;

    private String defaultDirName = "C:/work";
    private String defaultFileExt = ".log";
    private String defaultSearchingText = " ";

    private List<File> findedFiles;
    private List<String> filesWithText;


    @FXML
    private TextArea findedFilesTextArea;

    @FXML
    private TextField dirNameTextField;

    @FXML
    private TextField fileExtTextField;

    @FXML
    private TextField textSearchingTextField;

    @FXML
    private Button inputButton;

    @FXML
    private TabPane findedFileTabPane;

    @FXML
    private Button selectAllButton;

    @FXML
    private Button searchTextInOpenFileButton;

    @FXML
    private TextField searchTextInFileTextField;

    @FXML
    void initialize() {
        inputButton.setOnAction(event -> {
            Task task = new Task<Void>() {
                @Override
                protected Void call() {
                    findTextInfo();
                    return null;
                }
            };
            new Thread(task).start();
        });

        selectAllButton.setOnAction(event -> {
            selectAllText();
        });

        searchTextInOpenFileButton.setOnAction(event -> {
            searchTextInOpenFile();
        });
    }


    /**
     * Seaching for files and text function.
     *
     * Getting text from input fields, searching for needed files with required exteinsion,
     * if finded files contains required text, output it in new tab per file.
     *
     */

    private void findTextInfo() {
        if(dirNameTextField.getText().trim().isEmpty()) {
            dirName = defaultDirName;
        } else {
            dirName = dirNameTextField.getText();
        }
        if(fileExtTextField.getText().trim().isEmpty()) {
            fileExt = defaultFileExt;
        } else {
            fileExt = fileExtTextField.getText();
        }
        if(textSearchingTextField.getText().trim().isEmpty()) {
            searchText = defaultSearchingText;
        } else {
            searchText = textSearchingTextField.getText();
        }

        Initializing init = new Initializing(dirName, fileExt);
        findedFiles = init.find();
        if(findedFiles.size() == 0) {
            findedFilesTextArea.setPromptText("There are no such files :(");
        } else {
            try {
                filesWithText = Search(findedFiles, searchText);
            } catch (IOException e) {
                //TODO: some exception handler
                e.printStackTrace();
            }
            if(filesWithText.size() != 0) {
                findedFilesTextArea.clear();
                for (String file : filesWithText) {
                    findedFilesTextArea.appendText(file + "\n");
                    try {
                        createNewTab(file);
                    } catch (IOException e) {
                        //TODO: some exception handler
                        e.printStackTrace();
                    }
                }
            } else {
                findedFilesTextArea.clear();
                findedFilesTextArea.setPromptText("There are no such files :(");
            }
        }
    }


    /**
     * Function for creating new tab and filling it with text from file.
     * @param tabName - string parameter for naming tab, which will be created, it contains file's directory and used for opening files.
     * @throws IOException
     */

    private void createNewTab(String tabName) throws IOException {
        List<String> tmp = fileReading(tabName);
        StringBuilder sb = new StringBuilder();
        for(String item: tmp){
            sb.append(item);
            sb.append("\n");
        }

        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                Tab tab = new Tab(tabName);
                TextArea txtArea = new TextArea();
                tab.setContent(txtArea);
                txtArea.setText(sb.toString());
                findedFileTabPane.getTabs().add(tab);
            }
        });
    }


    /**
     * Function for selecting all text in focused tab.
     */

    private void selectAllText() {
        Node tmp = findedFileTabPane.getSelectionModel().getSelectedItem().getContent();
        TextArea area = (TextArea) tmp.lookup("TextArea");
        area.selectAll();
    }


    /**
     * Function for searching input text in focused tab.
     */

    private void searchTextInOpenFile() {
        Node tmp = findedFileTabPane.getSelectionModel().getSelectedItem().getContent();
        TextArea area = (TextArea) tmp.lookup("TextArea");
        String text = area.getText();
        String textToSearch = searchTextInFileTextField.getText();

        int firstSymbolIndex = text.indexOf(textToSearch, fromIndex + textToSearch.length());

        if(firstSymbolIndex != -1) {
            fromIndex = firstSymbolIndex;
        } else {
            fromIndex = 0;
            fromIndex = text.indexOf(textToSearch, fromIndex + textToSearch.length());
        }

        area.selectRange(fromIndex, fromIndex + textToSearch.length());
    }

}