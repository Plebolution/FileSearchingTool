package main.java.sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("window.fxml"));
        primaryStage.setTitle("Text Searching tool");
        primaryStage.setScene(new Scene(root, 820, 500));
        primaryStage.setMinHeight(500);
        primaryStage.setMinWidth(820);
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
