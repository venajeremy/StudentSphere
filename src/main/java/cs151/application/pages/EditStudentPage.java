package cs151.application.pages;

import cs151.application.domain.Comment;
import cs151.application.domain.ProgrammingLanguage;
import cs151.application.domain.Student;
import cs151.application.service.LanguageCatalog;
import cs151.application.service.StudentCatalog;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.util.Pair;

import java.time.LocalDate;
import java.util.ArrayList;

public class EditStudentPage extends Page{

    private final StudentCatalog studentCatalog = new StudentCatalog("src/main/resources/userdata/students.csv", "src/main/resources/userdata/comments.csv");
    private final LanguageCatalog languageCatalog = new LanguageCatalog("src/main/resources/userdata/programmingLanguages.csv");

    public EditStudentPage(Student student) {
        super();

        VBox input = new VBox(15);
        input.setPadding(new Insets(6, 6, 6, 6));

        Label title = new Label("Edit "+student.getName()+"'s student account: ");

        // input fields
        // Name
        Label fullNameTitle = new Label("Enter Student's Full Name:");
        TextField fullName = new TextField();
        fullName.setPromptText(student.getName());

        input.getChildren().addAll(fullNameTitle, fullName);

        // Academic Status
        Label academicStatusTitle = new Label("Choose Academic Status:");
        ComboBox academicStatus = new ComboBox();

        academicStatus.getItems().addAll(Student.AcademicStatuses.values());
        academicStatus.getSelectionModel().select(student.getAcademicStatus());

        input.getChildren().addAll(academicStatusTitle, academicStatus);

        // Employeed
        Label employmentTitle = new Label("Choose Employment:");
        ToggleGroup employmentGroup = new ToggleGroup();

        RadioButton unemployed= new RadioButton(Student.JobStatuses.UNEMPLOYED.getName());
        unemployed.setToggleGroup(employmentGroup);
        unemployed.setSelected(true);

        RadioButton employed  = new RadioButton(Student.JobStatuses.EMPLOYED.getName());
        employed.setToggleGroup(employmentGroup);

        input.getChildren().addAll(employmentTitle, unemployed, employed);

        // Job details
        Label jobTitle = new Label("Enter Current Job:");
        TextField job = new TextField();
        job.setPromptText("Current Job");
        jobTitle.visibleProperty().bind(employed.selectedProperty());
        job.visibleProperty().bind(employed.selectedProperty());

        input.getChildren().addAll(jobTitle, job);

        // Programming languages known
        Label languagesTitle = new Label("Enter known Programming Languages:");
        input.getChildren().add(languagesTitle);
        ObservableList<ProgrammingLanguage> availableLanguages = languageCatalog.readSavedLanguages();
        ArrayList<Pair<CheckBox, ProgrammingLanguage>> availableLanguageCheckBoxes = new ArrayList<Pair<CheckBox, ProgrammingLanguage>>();
        for(ProgrammingLanguage pL : availableLanguages){
            CheckBox availableLanguageCB = new CheckBox(pL.getName());

            availableLanguageCheckBoxes.add(new Pair<>(availableLanguageCB, pL));
            input.getChildren().add(availableLanguageCB);
        }

        // Databases known
        Label databaseTitle = new Label("Enter Known Databases:");
        input.getChildren().add(databaseTitle);
        ArrayList<Pair<CheckBox, Student.Databases>> availableDatabaseCheckBoxes = new ArrayList<Pair<CheckBox, Student.Databases>>();
        for(Student.Databases db : Student.Databases.values()){
            CheckBox availableDatabaseCB = new CheckBox(db.getName());

            availableDatabaseCheckBoxes.add(new Pair<>(availableDatabaseCB, db));
            input.getChildren().add(availableDatabaseCB);
        }

        // Preferred Professional Role
        Label preferredRoleTitle = new Label("Choose Preferred Role:");
        ComboBox preferredRole = new ComboBox();

        preferredRole.getItems().addAll(Student.ProfessionalRoles.values());

        input.getChildren().addAll(preferredRoleTitle, preferredRole);

        // Initial Comment
        Label firstCommentTitle = new Label("Input Initial Student Comment:");
        TextArea firstComment = new TextArea();
        firstComment.setPromptText("Enter student notes...");

        input.getChildren().addAll(firstCommentTitle, firstComment);

        // Future Service Flags
        Label whiteListTitle = new Label("Add To Whitelist:");
        CheckBox whiteList = new CheckBox();
        Label blackListTitle = new Label("Add To Backlist:");
        CheckBox blackList = new CheckBox();

        whiteList.setOnAction(event -> {
            if (whiteList.isSelected()){
                blackList.setSelected((false));
            }
        });
        blackList.setOnAction(event -> {
            if (blackList.isSelected()){
                whiteList.setSelected((false));
            }
        });

        input.getChildren().addAll(whiteListTitle, whiteList, blackListTitle, blackList);

        // buttons, submit + delete
        Button submit = new Button("Submit");
        Label submitMessage = new Label("");

        submit.setOnAction((ActionEvent e) -> {
            String fN = fullName.getText().trim();
            Student.AcademicStatuses aS = (Student.AcademicStatuses) academicStatus.getValue();
            Student.JobStatuses jS = (employed.isSelected()) ? Student.JobStatuses.EMPLOYED : Student.JobStatuses.UNEMPLOYED;
            String cJ = job.getText();

            ObservableList<ProgrammingLanguage> selectedLanguages = FXCollections.observableArrayList();
            for(Pair<CheckBox, ProgrammingLanguage> aLPair : availableLanguageCheckBoxes){
                if(aLPair.getKey().isSelected()){
                    selectedLanguages.add(aLPair.getValue());
                }
            }
            ObservableList<ProgrammingLanguage> kL = selectedLanguages;

            ObservableList<Student.Databases> selectedDbs = FXCollections.observableArrayList();
            for(Pair<CheckBox, Student.Databases> kDPair : availableDatabaseCheckBoxes){
                if(kDPair.getKey().isSelected()){
                    selectedDbs.add(kDPair.getValue());
                }
            }
            ObservableList<Student.Databases> kD = selectedDbs;

            Student.ProfessionalRoles pR = (Student.ProfessionalRoles) preferredRole.getValue();

            Student.FutureServiceFlags fSF;
            if(whiteList.isSelected()){
                fSF = Student.FutureServiceFlags.WHITELISTED;
            } else if(blackList.isSelected()){
                fSF = Student.FutureServiceFlags.BLACKLISTED;
            } else {
                fSF = Student.FutureServiceFlags.NONE;
            }

            // Create new student
            Student newStudent = new Student(fN, aS, jS, cJ, kL, kD, pR, fSF);

            // Add student to catalog
            StringBuilder error = new StringBuilder();
            int newStudentID = studentCatalog.add(newStudent, error);
            if (newStudentID != -1) {
                submitMessage.setStyle("-fx-text-fill: #2e7d32;");
                submitMessage.setText("Added: " + fN);
            } else {
                submitMessage.setStyle("-fx-text-fill: #c62828;");
                submitMessage.setText(error.toString());
            }

            // Add first comment to new student
            String firstCommentText = firstComment.getText();
            LocalDate currentDate = LocalDate.now();
            Comment newComment = new Comment(newStudentID, firstCommentText, currentDate);

            studentCatalog.addComment(newComment);

        });


        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setContent(input);
        this.getChildren().addAll(title, scrollPane, new Label("Add New Student To The Database:"), submit, submitMessage);

    }
}
