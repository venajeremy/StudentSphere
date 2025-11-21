package cs151.application.pages;

import cs151.application.domain.Comment;
import cs151.application.domain.Student;
import cs151.application.service.StudentCatalog;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.time.format.DateTimeFormatter;
import java.util.Optional;

public class CommentDetailPage extends Page {

    private final Comment comment;
    private final StudentCatalog catalog;
    private final Runnable onBack;

    private final Label studentNameLabel = new Label();
    private final Label dateLabel = new Label();
    private final TextArea bodyArea = new TextArea();

    private static final DateTimeFormatter DATE_FMT = DateTimeFormatter.ofPattern("dd-MM-yyyy");

    /**
     * Reusable page for displaying a single Comment.
     *
     * @param comment   the comment to display
     * @param catalog   student catalog used to resolve student name from studentID
     * @param onBack    callback invoked when the Back button is pressed
     */
    public CommentDetailPage(Comment comment, StudentCatalog catalog, Runnable onBack) {
        super();
        this.comment = comment;
        this.catalog = catalog;
        this.onBack = onBack;

        setSpacing(0);
        setPadding(new Insets(0));

        BorderPane root = new BorderPane();

        // top: back + title
        Button back = new Button("← Back");
        back.setOnAction(e -> {
            if (onBack != null) {
                onBack.run();
            }
        });

        Label title = new Label("Comment Details");
        HBox header = new HBox(12, back, title);
        header.setPadding(new Insets(12, 12, 12, 12));

        root.setTop(header);

        // center: student + date + body
        VBox content = new VBox(10);
        content.setPadding(new Insets(16));

        // resolve student name from studentID
        String studentName = resolveStudentName(comment.getStudentID());

        studentNameLabel.setText("Student: " + studentName);
        dateLabel.setText("Date: " + formatDateSafe(comment));

        bodyArea.setText(comment.getMessage() == null ? "" : comment.getMessage());
        bodyArea.setEditable(false);          // read-only display
        bodyArea.setWrapText(true);
        bodyArea.setPrefRowCount(6);

        content.getChildren().addAll(
                studentNameLabel,
                dateLabel,
                new Label("Comment:"),
                bodyArea
        );

        root.setCenter(content);

        this.getChildren().add(root);
    }

    private String resolveStudentName(int studentId) {
        // ensure catalog has up-to-date data
        try {
            catalog.readSavedStudents();
        } catch (Exception ignored) {
            // if this fails, we still return a fallback label below
        }

        Optional<Student> match = catalog.items().stream()
                .filter(s -> s.getID() != null && s.getID() == studentId)
                .findFirst();

        return match
                .map(Student::getName)
                .orElse("Unknown student (ID " + studentId + ")");
    }

    private String formatDateSafe(Comment c) {
        if (c.getDate() == null) return "";
        try {
            return c.getDate().format(DATE_FMT);
        } catch (Exception e) {
            // Fallback to default toString if pattern fails
            return c.getDate().toString();
        }
    }
}
