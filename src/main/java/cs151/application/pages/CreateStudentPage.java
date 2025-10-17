package cs151.application.pages;

import cs151.application.domain.ProgrammingLanguage;
import cs151.application.domain.Student;
import cs151.application.service.StudentCatalog;
import cs151.application.service.LanguageCatalog;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.util.Pair;

import java.awt.event.ActionListener;
import java.util.ArrayList;

public class CreateStudentPage extends Page{

    private final StudentCatalog studentCatalog = new StudentCatalog("src/main/resources/userdata/students.csv");
    private final LanguageCatalog languageCatalog = new LanguageCatalog("src/main/resources/userdata/programmingLanguages.csv");

    public CreateStudentPage() {
        super();

        VBox input = new VBox(15);
        input.setPadding(new Insets(6, 6, 6, 6));

        Label title = new Label("Define Student:");

        // input fields
        // Name
        Label fullNameTitle = new Label("Enter Student's Full Name:");
        TextField fullName = new TextField();
        fullName.setPromptText("Full Name");

        input.getChildren().addAll(fullNameTitle, fullName);

        // Academic Status
        Label academicStatusTitle = new Label("Choose Academic Status:");
        ComboBox academicStatus = new ComboBox();

        academicStatus.getItems().addAll(Student.AcademicStatuses.values());

        input.getChildren().addAll(academicStatusTitle, academicStatus);

        // Employeed
        Label employmentTitle = new Label("Choose Employment:");
        ToggleGroup employmentGroup = new ToggleGroup();

        RadioButton unemployed= new RadioButton("Unemployed");
        unemployed.setToggleGroup(employmentGroup);
        unemployed.setSelected(true);

        RadioButton employed  = new RadioButton("Employed");
        employed .setToggleGroup(employmentGroup);

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
            CheckBox availableDatabaseCB = new CheckBox(db.name());

            availableDatabaseCheckBoxes.add(new Pair<>(availableDatabaseCB, db));
            input.getChildren().add(availableDatabaseCB);
        }

        // Preferred Professional Role
        Label preferredRoleTitle = new Label("Choose Preferred Role:");
        ComboBox preferredRole = new ComboBox();

        preferredRole.getItems().addAll(Student.ProfessionalRoles.values());

        input.getChildren().addAll(preferredRoleTitle, preferredRole);

        // Faculty Evaluation
        Label evaluationTitle = new Label("Input Current Faculty Evaluation:");
        TextArea evaluation = new TextArea();
        evaluation.setPromptText("Enter student notes...");

        input.getChildren().addAll(evaluationTitle, evaluation);

        // buttons, submit + delete
        Button submit = new Button("Submit");



        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setContent(input);
        this.getChildren().addAll(title, scrollPane, new Label("Add New Student To The Database:"), submit);

    }
}
