package cs151.application.pages;

import javafx.event.ActionEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.control.TextField;
import javafx.scene.control.Label;
import javafx.scene.control.Button;

public class AddLanguagePage extends Page {
    public AddLanguagePage() {
        super();

        //Creating a GridPane container
        GridPane grid = new GridPane();

        //Defining the Name text field
        final TextField name = new TextField();
        name.setPromptText("Enter Programming Language...");
        GridPane.setConstraints(name, 0, 0);
        grid.getChildren().add(name);

        //Defining the Submit button
        Button submit = new Button("Submit");
        submit.setOnAction((ActionEvent e) -> {
            String languageName = name.getText();
            System.out.println(languageName);
        });
        GridPane.setConstraints(submit, 1, 0);
        grid.getChildren().add(submit);

        this.getChildren().add(new Label("Add a new programming language!"));
        this.getChildren().add(grid);
    }
}
