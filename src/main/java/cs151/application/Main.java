package cs151.application;

import cs151.application.pages.AddLanguagePage;
import cs151.application.pages.CreateStudentPage;
import cs151.application.pages.SearchStudentPage;
import cs151.application.pages.ViewStudentsPage;
import cs151.application.pages.HomePage;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.Cursor;
import javafx.stage.Stage;


import java.net.URL;

public class Main extends Application {

    @Override
    public void start(Stage stage) {

        // Root layout: Top (header) + Center (page content)
        BorderPane root = new BorderPane();

        // Instantiate pages (single instances to retain state)
        CreateStudentPage createStudent = new CreateStudentPage();
        AddLanguagePage languagePage = new AddLanguagePage();
        ViewStudentsPage viewStudents = new ViewStudentsPage();
        SearchStudentPage searchStudent = new SearchStudentPage();

        // Home page: wire CTAs to navigate to other pages
        HomePage home = new HomePage(
                () -> root.setCenter(createStudent),   // "Create Profile" CTA
                () -> root.setCenter(viewStudents)    
        );

        // Top header: brand (left) + nav buttons (right)
        Label brand = new Label("StudentSphere");
        brand.getStyleClass().add("brand");

        // click → go home
        brand.setOnMouseClicked(e -> root.setCenter(home));
        brand.setCursor(Cursor.HAND);


        Button defineLanguageBtn = new Button("Define Language");
        defineLanguageBtn.setOnAction(e -> root.setCenter(languagePage));

        Button createStudentBtn = new Button("Create Profile");
        createStudentBtn.setOnAction(e -> root.setCenter(createStudent));


        Button generateReportBtn = new Button("Generate Report");
        generateReportBtn.setOnAction(e -> {
            ViewStudentsPage viewPage = new ViewStudentsPage();
            viewPage.onNavigatedTo();   // force reload from CSV
            root.setCenter(viewPage);
        });


        HBox nav = new HBox(10, defineLanguageBtn, createStudentBtn, generateReportBtn);
        nav.setAlignment(Pos.CENTER_RIGHT);
        nav.setPadding(new Insets(8, 8, 8, 8));

        BorderPane topBar = new BorderPane();
        topBar.setLeft(brand);
        topBar.setRight(nav);
        topBar.getStyleClass().add("topbar");

        // Apply header + set default center to Home
        root.setTop(topBar);
        root.setCenter(home);

        //  Scene & stage
        Scene scene = new Scene(root, 1200, 600);

        // Optional: attach stylesheet if present
        URL css = getClass().getResource("/ui/application.css");
        if (css != null) {
            scene.getStylesheets().add(css.toExternalForm());
        }

        stage.setTitle("Student Sphere");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
