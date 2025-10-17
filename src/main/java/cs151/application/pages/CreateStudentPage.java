package cs151.application.pages;

import cs151.application.domain.Student;
import cs151.application.service.StudentCatalog;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class CreateStudentPage extends Page{

    private final StudentCatalog catalog = new StudentCatalog("src/main/resources/userdata/students.csv");

    public CreateStudentPage() {
        super();

        Label title = new Label("Define Student:");

        // input fields
        // Name
        Label fullNameTitle = new Label("Enter Student's Full Name:");
        TextField fullName = new TextField();
        fullName.setPromptText("Full Name");

        // Academic Status
        Label academicStatusTitle = new Label("Choose Academic Status:");
        ComboBox academicStatus = new ComboBox();
        academicStatus.getItems().addAll(Student.AcademicStatuses.values());

        //


        // buttons, submit + delete
        Button submit = new Button("Submit");

        VBox input = new VBox(15, fullNameTitle, fullName, academicStatusTitle, academicStatus, submit);
        input.setPadding(new Insets(6, 6, 6, 6));

        this.getChildren().addAll(input, new Label("Add a new student to database"));

    }
}
