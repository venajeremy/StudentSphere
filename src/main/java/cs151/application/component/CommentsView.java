package cs151.application.component;

import cs151.application.domain.Comment;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.ObservableList;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class CommentsView extends TableView<Comment> {

    public CommentsView(ObservableList<Comment> comments){
        super();

        TableColumn<Comment, String> dateCol = new TableColumn<>("Date");
        dateCol.setCellValueFactory(cd -> {
            var d = cd.getValue().getDate();
            return new ReadOnlyStringWrapper(d == null ? "" : d.toString());
        });
        dateCol.setPrefWidth(120);

        TableColumn<Comment, String> msgCol = new TableColumn<>("Comment");
        msgCol.setCellValueFactory(cd -> new ReadOnlyStringWrapper(cd.getValue().getMessage()));
        msgCol.setPrefWidth(600);

        this.getColumns().addAll(dateCol, msgCol);
        this.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        this.setPlaceholder(new Label("No comments yet."));
        this.setItems(comments); // already populated by StudentCatalog.readSavedStudents()
    }

}
