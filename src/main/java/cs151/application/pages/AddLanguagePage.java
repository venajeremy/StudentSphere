package cs151.application.pages;

import cs151.application.domain.ProgrammingLanguage;
import cs151.application.service.LanguageCatalog;
import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.*;

public class AddLanguagePage extends Page {

    private final LanguageCatalog catalog = new LanguageCatalog("src/main/resources/userdata/programmingLanguages.csv"); // in-memory for this assignment

    public AddLanguagePage() {
        super();

        Label title = new Label("Add a new programming language!");

        TextField name = new TextField();
        name.setPromptText("Enter Programming Language...");

        Button submit = new Button("Submit");
        HBox input = new HBox(10, name, submit);
        input.setPadding(new Insets(0, 0, 6, 0));

        Label message = new Label();

        // Create Table
        TableView<ProgrammingLanguage> list = new TableView<ProgrammingLanguage>(catalog.items());
        // Create Language Name Column
        TableColumn<ProgrammingLanguage,String> languageCol = new TableColumn<ProgrammingLanguage,String>("Programming Language");
        // Define Column Data
        languageCol.setCellValueFactory(cellData -> {
            ProgrammingLanguage language = cellData.getValue();
            return new SimpleStringProperty(language.getName());
        });
        // Add Column to table
        list.getColumns().setAll(languageCol);
        list.setPrefHeight(320);

        submit.setOnAction((ActionEvent e) -> {
            StringBuilder error = new StringBuilder();
            if (catalog.add(name.getText(), error)) {
                message.setStyle("-fx-text-fill: #2e7d32;"); // green
                message.setText("Added: " + name.getText().trim());
                name.clear();
            } else {
                message.setStyle("-fx-text-fill: #c62828;"); // red
                message.setText(error.toString());
            }
            name.requestFocus();
        });

        this.getChildren().addAll(title, input, message, new Label("Defined languages:"), list);
    }
}


// This class represents the UI layer --> displaying the list of 'Programming Language' entities/objects