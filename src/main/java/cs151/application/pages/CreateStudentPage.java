package cs151.application.pages;

import javafx.scene.control.Label;

public class CreateStudentPage extends Page{
    public CreateStudentPage() {
        super();
        this.getChildren().add(new Label("Add a new student to database"));
    }
}
