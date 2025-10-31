package cs151.application.pages;

import cs151.application.domain.ProgrammingLanguage;
import cs151.application.service.LanguageCatalog;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;

public class AddLanguagePage extends Page {

    private final LanguageCatalog catalog =
            new LanguageCatalog("src/main/resources/userdata/programmingLanguages.csv");

    public AddLanguagePage() {
        super();

        Label title = new Label("Add a new programming language!");

        //Back Button
        Button backButton = new Button("← Back");
        backButton.setOnAction(e -> {
            BorderPane root = (BorderPane) getScene().getRoot();
            HomePage home = new HomePage(
                    () -> root.setCenter(new CreateStudentPage()),   // Create Profile
                    () -> root.setCenter(new ViewStudentsPage())    // Generate Report
            );
            root.setCenter(home);
        });

        // Align back button to the far right
        HBox header = new HBox();
        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);
        header.getChildren().addAll(title, spacer, backButton);
        header.setPadding(new Insets(0, 0, 10, 0));
        header.setAlignment(Pos.CENTER_LEFT);

        // input fields
        TextField name = new TextField();
        name.setPromptText("Enter Programming Language...");

        // buttons, submit + delete
        Button submit = new Button("Submit");
        Button deleteBtn = new Button("Delete");

        HBox input = new HBox(10, name, submit, deleteBtn);
        input.setPadding(new Insets(0, 0, 6, 0));

        Label message = new Label();

        // table creation
        TableView<ProgrammingLanguage> list = new TableView<>(catalog.items());
        list.setPrefHeight(320);
        list.setEditable(true);

        // column creation
        TableColumn<ProgrammingLanguage, String> languageCol =
                new TableColumn<>("Programming Language");

        languageCol.setCellValueFactory(cell ->
                new ReadOnlyStringWrapper(cell.getValue().getName())
        );

        // enable edit
        languageCol.setEditable(true);
        languageCol.setCellFactory(TextFieldTableCell.forTableColumn());

        languageCol.setOnEditCommit(evt -> {
            ProgrammingLanguage original = evt.getRowValue();
            String newName = evt.getNewValue();
            StringBuilder err = new StringBuilder();
            if (!catalog.update(original, newName, err)) {
                list.refresh();
                message.setStyle("-fx-text-fill: #c62828;");
                message.setText(err.toString());
            } else {
                message.setStyle("-fx-text-fill: #2e7d32;");
                message.setText("Updated: " + newName.trim());
            }
        });

        list.getColumns().setAll(languageCol);
        list.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        submit.setOnAction((ActionEvent e) -> {
            StringBuilder error = new StringBuilder();
            if (catalog.add(name.getText(), error)) {
                message.setStyle("-fx-text-fill: #2e7d32;");
                message.setText("Added: " + name.getText().trim());
                name.clear();
            } else {
                message.setStyle("-fx-text-fill: #c62828;");
                message.setText(error.toString());
            }
            name.requestFocus();
        });

        submit.disableProperty().bind(name.textProperty().isEmpty());
        name.setOnAction(e -> submit.fire());

        // delete logic
        deleteBtn.disableProperty().bind(
                list.getSelectionModel().selectedItemProperty().isNull()
        );
        deleteBtn.setOnAction(e -> {
            ProgrammingLanguage sel = list.getSelectionModel().getSelectedItem();
            if (sel == null) return;
            if (catalog.remove(sel)) {
                message.setStyle("-fx-text-fill: #2e7d32;");
                message.setText("Deleted: " + sel.getName());
            } else {
                message.setStyle("-fx-text-fill: #c62828;");
                message.setText("Could not delete selection.");
            }
        });

        list.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.DELETE) {
                deleteBtn.fire();
            }
        });

        ContextMenu cm = new ContextMenu();
        MenuItem miEdit = new MenuItem("Edit");
        MenuItem miDelete = new MenuItem("Delete");
        cm.getItems().addAll(miEdit, miDelete);

        list.setRowFactory(tv -> {
            TableRow<ProgrammingLanguage> row = new TableRow<>();
            row.itemProperty().addListener((obs, oldItem, newItem) -> {
                row.setContextMenu(newItem == null ? null : cm);
            });
            return row;
        });

        miEdit.setOnAction(e -> {
            int rowIdx = list.getSelectionModel().getSelectedIndex();
            if (rowIdx >= 0) list.edit(rowIdx, languageCol);
        });
        miDelete.setOnAction(e -> deleteBtn.fire());

        this.getChildren().addAll(header, title, input, message, new Label("Defined languages:"), list);
    }
}
