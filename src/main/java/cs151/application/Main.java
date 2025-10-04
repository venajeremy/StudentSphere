package cs151.application;

import cs151.application.pages.AddLanguagePage;
import cs151.application.pages.CreateStudentPage;
import cs151.application.pages.SearchStudentPage;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.control.*;
import javafx.scene.layout.*;

import java.io.IOException;

public class Main extends Application {

    @Override
    public void start(Stage stage) throws IOException {

        // Root contains window layout for all components
        BorderPane root = new BorderPane();

        // Initialize Pages
        CreateStudentPage createStudent = new CreateStudentPage();
        AddLanguagePage languagePage = new AddLanguagePage();
        SearchStudentPage searchStudent = new SearchStudentPage();

        // Toolbar
        ToolBar toolBar = new ToolBar();
        Button studentSearchButton = new Button("Student Search");
        studentSearchButton.setOnAction((ActionEvent t)->{
            root.setCenter(searchStudent);
        });
        Button createStudentButton = new Button("Create Student");
        createStudentButton.setOnAction((ActionEvent t)->{
            root.setCenter(createStudent);
        });
        Button defineLanguageButton = new Button("Define Programming Language");
        defineLanguageButton.setOnAction((ActionEvent t)->{
            root.setCenter(languagePage);
        });
        toolBar.getItems().addAll(studentSearchButton,createStudentButton,defineLanguageButton);

        // Initialize Root
        root.setTop(toolBar);
        root.setCenter(searchStudent);

        stage.setTitle("Student Sphere");
        Scene scene = new Scene(root, 1200, 600);

        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}