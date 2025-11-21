package cs151.application.pages;

import cs151.application.component.StudentForm;
import cs151.application.domain.Comment;
import cs151.application.domain.ProgrammingLanguage;
import cs151.application.domain.Student;
import cs151.application.service.LanguageCatalog;
import cs151.application.service.StudentCatalog;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.util.Pair;

import java.time.LocalDate;
import java.util.ArrayList;

public class EditStudentPage extends Page{

    private final StudentCatalog studentCatalog = new StudentCatalog("src/main/resources/userdata/students.csv", "src/main/resources/userdata/comments.csv");
    private final LanguageCatalog languageCatalog = new LanguageCatalog("src/main/resources/userdata/programmingLanguages.csv");

    public EditStudentPage(Student student) {
        super();

        Label title = new Label("Edit "+student.getName()+"'s student account: ");

        //Back Button
        Button backButton = new Button("← Back");
        backButton.setOnAction(e -> {
            BorderPane root = (BorderPane) getScene().getRoot();
            ViewStudentsPage viewPage = new ViewStudentsPage();
            viewPage.onNavigatedTo();   // force reload from CSV
            root.setCenter(viewPage);
        });

        // Student Form In Student Edit Form
        StudentForm sF = new StudentForm(student);


        // buttons, update
        Button update = new Button("Update");
        Label submitMessage = new Label("");

        update.setOnAction((ActionEvent e) -> {

            // Create new student with ID so we can update the specified student
            Student newStudent = new Student(student.getID(), sF.getFullName(), sF.getAcademicStatus(), sF.getEmployed(), sF.getJob(), sF.getLanguages(), sF.getDatabases(), sF.getProfessionalRole(), sF.getFutureServiceFlags());

            // Add student to catalog
            StringBuilder error = new StringBuilder();
            if (studentCatalog.update(newStudent, error)) {
                submitMessage.setStyle("-fx-text-fill: #2e7d32;");
                submitMessage.setText("Updated "+sF.getFullName()+"'s student profile!");
            } else {
                submitMessage.setStyle("-fx-text-fill: #c62828;");
                submitMessage.setText(error.toString());
            }

        });


        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setContent(sF);
        this.getChildren().addAll(title, backButton, scrollPane, new Label("Update Student's Account:"), update, submitMessage);

    }
}
