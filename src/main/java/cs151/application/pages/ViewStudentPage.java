package cs151.application.pages;

import cs151.application.component.CommentsView;
import cs151.application.component.StudentForm;
import cs151.application.domain.Student;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.BorderPane;

public class ViewStudentPage extends Page {
    public ViewStudentPage(Student student) {
        super();

        Label title = new Label("View "+student.getName()+"'s student account: ");

        //Back Button
        Button backButton = new Button("← Back");
        backButton.setOnAction(e -> {
            BorderPane root = (BorderPane) getScene().getRoot();
            ViewStudentsPage viewPage = new ViewStudentsPage();
            viewPage.onNavigatedTo();   // force reload from CSV
            root.setCenter(viewPage);
        });

        // Load Student Form in as non-editable
        StudentForm studentForm = new StudentForm(student, false);

        // Load Comments Table
        CommentsView commentsTable = new CommentsView(student.getComments());

        ScrollPane studentFormScrollPane = new ScrollPane();
        studentFormScrollPane.setContent(studentForm);
        ScrollPane commentsTableScrollPane = new ScrollPane();
        commentsTableScrollPane.setContent(commentsTable);

        double paneHeight = 250;

        studentFormScrollPane.setPrefViewportHeight(paneHeight);
        commentsTableScrollPane.setPrefViewportHeight(paneHeight);

        this.getChildren().addAll(title, backButton, new Label("Student Details:"), studentFormScrollPane, new Label("Student Comments:"), commentsTableScrollPane);
    }
}
