package cs151.application;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader homePage = new FXMLLoader(Main.class.getResource("home-page.fxml"));
        FXMLLoader defineLanguages = new FXMLLoader(Main.class.getResource("define-languages.fxml"));
        Scene scene = new Scene(defineLanguages.load(), 1200, 800);
        stage.setTitle("StudentSphere");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}