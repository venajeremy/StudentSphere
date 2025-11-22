package cs151.application.pages;

import cs151.application.component.CommentsView;
import cs151.application.domain.Comment;
import cs151.application.domain.Student;
import cs151.application.service.StudentCatalog;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.time.LocalDate;

public class StudentCommentsPage extends Page {

    private final Student student;
    private final StudentCatalog catalog;

    public StudentCommentsPage(Student student, StudentCatalog catalog) {
        super();
        this.student = student;
        this.catalog = catalog;

        setSpacing(0);
        setPadding(new Insets(0));

        // header (Back)
        Button back = new Button("← Back");
        back.setOnAction(e -> {
            var root = (BorderPane) getScene().getRoot();
            ViewStudentsPage v = new ViewStudentsPage();
            v.onNavigatedTo();
            root.setCenter(v);
        });

        Label title = new Label("Comments for: " + (student.getName() == null ? "(unknown)" : student.getName()));
        HBox header = new HBox(12, back, title);
        header.setPadding(new Insets(12));

        // add comment
        VBox input = new VBox(15);
        input.setPadding(new Insets(6, 6, 6, 6));

        Label commentTitle = new Label("Add Comment To Student:");
        TextArea comment = new TextArea();
        comment.setPromptText("Enter student notes...");
        comment.setMinHeight(150);

        // comments table
        CommentsView table = new CommentsView(student.getComments());

        // buttons, submit
        Button submit = new Button("Submit");

        // buttons, delete
        Button delete = new Button("Delete Selected");
        // disable when nothing selected
        delete.disableProperty().bind(
                table.getSelectionModel().selectedItemProperty().isNull()
        );

        delete.setOnAction(e -> {
            Comment selected = table.getSelectionModel().getSelectedItem();
            if (selected == null) return;

            // call into the catalog to delete from storage
            boolean removed = catalog.deleteComment(selected);

            if (removed) {
                // also remove from the in-memory list for this student
                student.getComments().removeIf(c -> c.getID() == selected.getID());
                // table is backed by student.getComments(), so it will refresh automatically
            }
        });


        submit.setOnAction((ActionEvent e) -> {
            // Add first comment to new student
            String commentText = comment.getText();
            LocalDate currentDate = LocalDate.now();
            Comment newComment = new Comment(student.getID(), commentText, currentDate);

            catalog.addComment(newComment);
        });

        HBox buttons = new HBox(10, submit, delete);
        input.getChildren().addAll(commentTitle, comment, buttons);


        // layout
        BorderPane shell = new BorderPane();
        shell.setTop(header);
        shell.setCenter(input);
        shell.setBottom(table);

        getChildren().add(shell);
    }
}
