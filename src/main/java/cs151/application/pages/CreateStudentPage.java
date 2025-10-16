package cs151.application.pages;

import cs151.application.service.StudentCatalog;
import javafx.scene.control.Label;

public class CreateStudentPage extends Page{

    private final StudentCatalog catalog = new StudentCatalog("src/main/resources/userdata/students.csv");

    public CreateStudentPage() {
        super();



        this.getChildren().add(new Label("Add a new student to database"));

    }
}
