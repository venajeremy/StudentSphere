package cs151.application.pages;

import cs151.application.domain.ProgrammingLanguage;
import cs151.application.domain.Student;
import cs151.application.service.StudentCatalog;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;

import java.util.Comparator;
import java.util.List;
import java.util.ArrayList;
import java.util.stream.Collectors;

/**
 * ViewStudentsPage:
 * - imports StudentCatalog
 * - calls readSavedStudents()
 * - sorts students
 * - displays in a JavaFX TableView
 */

public class ViewStudentsPage extends Page {

    private final BorderPane container = new BorderPane();
    private final TableView<Student> table = new TableView<>();
    private ObservableList<Student> master = FXCollections.observableArrayList();
    private final TextField search = new TextField();


    private final StudentCatalog catalog =
            new StudentCatalog("src/main/resources/userdata/students.csv", "src/main/resources/userdata/comments.csv");

    public ViewStudentsPage() {
        super();
        setSpacing(0);
        setPadding(new Insets(0));

        // header: Back + search + refresh
        Button back = new Button("← Back");
        back.setOnAction(e -> {
            var root = (BorderPane) getScene().getRoot();
            HomePage home = new HomePage(
                    () -> root.setCenter(new CreateStudentPage()),
                    () -> {
                        ViewStudentsPage v = new ViewStudentsPage();
                        v.onNavigatedTo();          // ensures fresh data on entry
                        root.setCenter(v);
                }       
            );
            root.setCenter(home);
        });


        search.setPromptText("Search (comma-separated, e.g. 'unemployed, python')");
        search.textProperty().addListener((obs, ov, nv) -> applyFilter(nv));

        Button refresh = new Button("Refresh");
        Button deleteBtn = new Button("Delete");
        Button editBtn = new Button("Edit");
        Button showWhitelistedBtn = new Button("Whitelisted");
        Button showBlacklistedBtn = new Button("Blacklisted");
        Button addCommentBtn = new Button("Add Comment");

        Label message = new Label();




        // whiteList button logic
        showWhitelistedBtn.setOnAction(e -> search.setText("whitelisted"));

        // blackList button logic
        showBlacklistedBtn.setOnAction(e -> search.setText("blacklisted"));

        // edit logic: disable until a row is selected
        editBtn.disableProperty().bind(
                table.getSelectionModel().selectedItemProperty().isNull()
        );

        // navigate to the edit page for the selected student
        editBtn.setOnAction(e -> {
            Student student = table.getSelectionModel().getSelectedItem();
            if (student == null) return;
            var root = (BorderPane) getScene().getRoot();
            root.setCenter(new EditStudentPage(student));
        });

        // view comments btn : only enable when a row is selected
        addCommentBtn.disableProperty().bind(table.getSelectionModel().selectedItemProperty().isNull());
        addCommentBtn.setOnAction(e -> {
            Student sel = table.getSelectionModel().getSelectedItem();
            if (sel == null) return;
            var root = (BorderPane) getScene().getRoot();
            // pass the *same* catalog so comments write to the same files
            root.setCenter(new StudentCommentsPage(sel, catalog));
        });



        // delete logic
        deleteBtn.disableProperty().bind(
                table.getSelectionModel().selectedItemProperty().isNull()
        );
        deleteBtn.setOnAction(e -> {
            Student sel = table.getSelectionModel().getSelectedItem();
            if (sel == null) return;
            if (catalog.remove(sel)) {
                refresh.fire();
                message.setStyle("-fx-text-fill: #2e7d32;");
                message.setText("Deleted: " + sel.getName());
            } else {
                message.setStyle("-fx-text-fill: #c62828;");
                message.setText("Could not delete selection.");
            }
        });

        refresh.setOnAction(e -> loadData());

        HBox top = new HBox(8, back, search, refresh, deleteBtn, editBtn, addCommentBtn, showWhitelistedBtn, showBlacklistedBtn, message);
        top.setPadding(new Insets(12));
        container.setTop(top);

        // Table columns (aligned with StudentCatalog getters)
        table.setEditable(true);

        TableColumn<Student, String> nameCol = new TableColumn<>("Name");
        nameCol.setEditable(true);
        nameCol.setCellValueFactory(cd -> new ReadOnlyStringWrapper(nz(cd.getValue().getName())));
        nameCol.setPrefWidth(180);

        TableColumn<Student, String> acadCol = new TableColumn<>("Academic Status");
        acadCol.setCellValueFactory(cd -> new ReadOnlyStringWrapper(
                cd.getValue().getAcademicStatus() == null ? "" : cd.getValue().getAcademicStatus().name()
        ));
        acadCol.setPrefWidth(140);

        TableColumn<Student, String> jobStatCol = new TableColumn<>("Job Status");
        jobStatCol.setCellValueFactory(cd -> new ReadOnlyStringWrapper(
                cd.getValue().getJobStatus() == null ? "" : cd.getValue().getJobStatus().name()
        ));
        jobStatCol.setPrefWidth(110);

        TableColumn<Student, String> jobCol = new TableColumn<>("Current Job");
        jobCol.setCellValueFactory(cd -> new ReadOnlyStringWrapper(nz(cd.getValue().getCurrentJob())));
        jobCol.setPrefWidth(150);

        jobCol.setCellFactory(col -> new TableCell<Student, String>() {
            @Override
            protected void updateItem(String job, boolean empty) {
                super.updateItem(job, empty);
                if (empty) { setText(null); return; }

                Student s = getTableView().getItems().get(getIndex());
                String status = (s.getJobStatus() == null) ? "" : s.getJobStatus().name();
                boolean unemployed = status.equalsIgnoreCase("UNEMPLOYED");

                setText(unemployed ? "" : job);
            }
        });


        
        TableColumn<Student, String> langsCol = new TableColumn<>("Known Languages");
        langsCol.setCellValueFactory(cd -> {
                var langs = cd.getValue().getKnownLanguages(); // never null now
                String s = langs.stream()
                        .map(pl -> nz(pl.getName()))
                        .filter(t -> !t.isBlank())
                        .collect(Collectors.joining(", "));
                return new ReadOnlyStringWrapper(s); // "" when empty
        });


        TableColumn<Student, String> dbCol = new TableColumn<>("Known Databases");
        dbCol.setCellValueFactory(cd -> {
                var dbs = cd.getValue().getKnownDatabases(); // never null now
                String s = dbs.stream()
                        .map(Enum::name)
                        .collect(Collectors.joining(", "));
                return new ReadOnlyStringWrapper(s); // "" when empty
        });


        
        TableColumn<Student, String> roleCol = new TableColumn<>("Preferred Role");
        roleCol.setCellValueFactory(cd -> new ReadOnlyStringWrapper(
                cd.getValue().getPreferredProfessionalRole() == null ? "" :
                        cd.getValue().getPreferredProfessionalRole().name()
        ));
        roleCol.setPrefWidth(160);

        TableColumn<Student, String> flagCol = new TableColumn<>("Future Service Flag");
        flagCol.setCellValueFactory(cd -> new ReadOnlyStringWrapper(
                cd.getValue().getFutureServiceFlags() == null ? "" :
                        cd.getValue().getFutureServiceFlags().name()
        ));
        flagCol.setPrefWidth(160);


        // Right click context menu

        ContextMenu cm = new ContextMenu();
        MenuItem miEdit = new MenuItem("Edit");
        MenuItem miDelete = new MenuItem("Delete");
        cm.getItems().addAll(miEdit, miDelete);

        table.setRowFactory(tv -> {
            TableRow<Student> row = new TableRow<>();
            row.itemProperty().addListener((obs, oldItem, newItem) -> {
                row.setContextMenu(newItem == null ? null : cm);
            });

            // Double click handle for opening view student page
            row.setOnMouseClicked(event -> {
                if(event.getClickCount() == 2 && !row.isEmpty()){
                    Student selected = row.getItem();

                    var root = (BorderPane) getScene().getRoot();

                    root.setCenter(new ViewStudentPage(selected, catalog));
                }
            });
            return row;
        });

        miEdit.setOnAction(e -> {
            Student student = table.getSelectionModel().getSelectedItem();
            var root = (BorderPane) getScene().getRoot();
            root.setCenter(new EditStudentPage(student));
        });
        miDelete.setOnAction(e -> deleteBtn.fire());


        table.getColumns().addAll(nameCol, acadCol, jobStatCol, jobCol, langsCol, dbCol, roleCol, flagCol);
        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        table.setPlaceholder(new Label("No students found."));

        container.setCenter(table);
        getChildren().add(container);

        loadData();
    }

    public void onNavigatedTo() {
        loadData();      // re-read CSV + rebind table items
        table.refresh(); // ensure UI reflects the latest data
        }       

    private void loadData() {
        try {
            ObservableList<Student> fromCsv = catalog.readSavedStudents();
            var list = (fromCsv == null) ? FXCollections.<Student>observableArrayList() : fromCsv;

            // Sort alphabetically by student name
            list.sort(Comparator.comparing(s -> nz(s.getName()), String.CASE_INSENSITIVE_ORDER));

            master = FXCollections.observableArrayList(list);
            table.setItems(master);
            applyFilter(search.getText());
        } catch (Exception ex) {
            ex.printStackTrace();
            Alert a = new Alert(Alert.AlertType.ERROR, ex.getMessage(), ButtonType.OK);
            a.setHeaderText("Failed to read saved students");
            a.showAndWait();
        }
    }


    private void applyFilter(String q) {
    if (q == null) q = "";
    String trimmed = q.trim();

    // If nothing typed, show all
    if (trimmed.isEmpty()) {
        table.setItems(master);
        return;
    }

    // Split on commas → AND terms
    String[] rawTerms = trimmed.split(",");
    java.util.List<String> terms = new ArrayList<>();
    for (String part : rawTerms) {
        String t = part.trim().toLowerCase();
        if (!t.isEmpty()) {
            terms.add(t);
        }
    }

    if (terms.isEmpty()) {
        table.setItems(master);
        return;
    }

    table.setItems(master.filtered(s -> {
        if (s == null) return false;

        // Build a big string with all relevant fields for this row
        StringBuilder sb = new StringBuilder();

        sb.append(nz(s.getName())).append(' ');

        if (s.getAcademicStatus() != null)
            sb.append(s.getAcademicStatus().name()).append(' ');

        if (s.getJobStatus() != null)
            sb.append(s.getJobStatus().name()).append(' ');

        sb.append(nz(s.getCurrentJob())).append(' ');

        if (s.getPreferredProfessionalRole() != null)
            sb.append(s.getPreferredProfessionalRole().name()).append(' ');

        if (s.getFutureServiceFlags() != null)
            sb.append(s.getFutureServiceFlags().name()).append(' ');

        if (s.getKnownLanguages() != null) {
            s.getKnownLanguages().forEach(pl ->
                    sb.append(nz(pl.getName())).append(' ')
            );
        }

        if (s.getKnownDatabases() != null) {
            s.getKnownDatabases().forEach(db ->
                    sb.append(db.name()).append(' ')
            );
        }

        String row = sb.toString().toLowerCase();

        // Every term must appear somewhere in this row
        for (String term : terms) {
            if (!row.contains(term)) {
                return false;   // AND semantics
            }
        }
        return true;
    }));
}




    private static String nz(String s) { return s == null ? "" : s; }
}