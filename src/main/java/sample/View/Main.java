package sample.View;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class Main extends Application {

    private static Stage primaryStage;

    @Override
    public void start(Stage primaryStage) throws Exception{
        Main.primaryStage = primaryStage;
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/login.fxml"));
        primaryStage.setTitle("CineMates20");
        primaryStage.setScene(new Scene(root, 400, 300));
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    public static Stage getPrimaryStage(){
        return primaryStage;
    }

    public static void main(String[] args) {
        launch(args);
    }
}
