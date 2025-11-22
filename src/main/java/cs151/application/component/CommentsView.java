package cs151.application.component;

import cs151.application.domain.Comment;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.ObservableList;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.Tooltip;

public class CommentsView extends TableView<Comment> {

    public CommentsView(ObservableList<Comment> comments) {
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

        // Custom cell factory to limit comment display to 3 lines
        msgCol.setCellFactory(column -> new TableCell<>() {
            private final Label label = new Label();

            {
                label.setWrapText(true);
                label.setMaxHeight(60); // 3 Lines
                setGraphic(label);
            }

            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    label.setText(null);
                    setTooltip(null);
                } else {
                    String[] lines = item.split("\n");
                    StringBuilder displayText = new StringBuilder();
                    int lineCount = 0;

                    for (String line : lines) {
                        for (int i = 0; i < line.length(); i += 80) { // Approx. 80 chars per line
                            if (lineCount++ >= 3) break;
                            int end = Math.min(i + 80, line.length());
                            displayText.append(line, i, end).append("\n");
                        }
                        if (lineCount >= 3) break;
                    }

                    if (lineCount >= 3) {
                        displayText.append("...");
                    }

                    label.setText(displayText.toString().trim());

                }
            }
        });

        this.getColumns().addAll(dateCol, msgCol);
        this.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        this.setPlaceholder(new Label("No comments yet."));
        this.setItems(comments);
    }
}
