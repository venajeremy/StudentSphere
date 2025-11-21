package cs151.application.pages;

import cs151.application.component.StudentForm;
import cs151.application.domain.ProgrammingLanguage;
import cs151.application.domain.Student;
import cs151.application.service.StudentCatalog;
import cs151.application.service.LanguageCatalog;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.util.Pair;

import java.util.ArrayList;

public class CreateStudentPage extends Page{

    private final StudentCatalog studentCatalog = new StudentCatalog("src/main/resources/userdata/students.csv", "src/main/resources/userdata/comments.csv");
    private final LanguageCatalog languageCatalog = new LanguageCatalog("src/main/resources/userdata/programmingLanguages.csv");

    public CreateStudentPage() {
        super();

        Label title = new Label("Create New Student: ");

        StudentForm sF = new StudentForm();

        // buttons, submit
        Button submit = new Button("Submit");
        Label submitMessage = new Label("");

        submit.setOnAction((ActionEvent e) -> {

            // Create new student
            Student newStudent = new Student(sF.getFullName(), sF.getAcademicStatus(), sF.getEmployed(), sF.getJob(), sF.getLanguages(), sF.getDatabases(), sF.getProfessionalRole(), sF.getFutureServiceFlags());

            // Add student to catalog
            StringBuilder error = new StringBuilder();
            int newStudentID = studentCatalog.add(newStudent, error);
            if (newStudentID != -1) {
                submitMessage.setStyle("-fx-text-fill: #2e7d32;");
                submitMessage.setText("Added: " + sF.getFullName());
            } else {
                submitMessage.setStyle("-fx-text-fill: #c62828;");
                submitMessage.setText(error.toString());
            }

           
        });


        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setContent(sF);
        this.getChildren().addAll(title, scrollPane, new Label("Add New Student To The Database:"), submit, submitMessage);

    }
}
