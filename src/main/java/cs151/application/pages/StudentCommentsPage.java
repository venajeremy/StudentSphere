package cs151.application.pages;

import cs151.application.domain.Comment;
import cs151.application.domain.Student;
import cs151.application.service.StudentCatalog;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;

public class StudentCommentsPage extends Page {

    private final Student student;
    private final StudentCatalog catalog;

    private final TableView<Comment> table = new TableView<>();

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

        // table (existing comments)
        TableColumn<Comment, String> dateCol = new TableColumn<>("Date");
        dateCol.setCellValueFactory(cd -> {
            var d = cd.getValue().getDate();
            return new ReadOnlyStringWrapper(d == null ? "" : d.toString());
        });
        dateCol.setPrefWidth(120);

        TableColumn<Comment, String> msgCol = new TableColumn<>("Comment");
        msgCol.setCellValueFactory(cd -> new ReadOnlyStringWrapper(cd.getValue().getMessage()));
        msgCol.setPrefWidth(600);

        table.getColumns().addAll(dateCol, msgCol);
        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        table.setPlaceholder(new Label("No comments yet."));
        table.setItems(student.getComments()); // already populated by StudentCatalog.readSavedStudents()

        // layout
        BorderPane shell = new BorderPane();
        shell.setTop(header);
        shell.setCenter(table);

        getChildren().add(shell);
    }
}
