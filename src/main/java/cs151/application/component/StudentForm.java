package cs151.application.component;

import cs151.application.domain.ProgrammingLanguage;
import cs151.application.domain.Student;
import cs151.application.service.LanguageCatalog;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.util.Pair;

import java.util.ArrayList;

public class StudentForm extends VBox {

    private final LanguageCatalog languageCatalog = new LanguageCatalog("src/main/resources/userdata/programmingLanguages.csv");

    // Student form parameters
    private TextField fullName;
    private ComboBox academicStatus;
    private RadioButton employed;
    private TextField job;
    private ArrayList<Pair<CheckBox, ProgrammingLanguage>> availableLanguageCheckBoxes;
    private ArrayList<Pair<CheckBox, Student.Databases>> availableDatabaseCheckBoxes;
    private ComboBox preferredRole;
    private CheckBox whiteList;
    private CheckBox blackList;

    // 3 Constructors: New Student, Edit Student, Edit Student View Only
    public StudentForm(){
        Build(null, true);
    }
    public StudentForm(Student student){
        Build(student, true);
    }
    public StudentForm(Student student, boolean bool){
        Build(student, bool);
    }

    private void Build(Student student, boolean editable){
        this.setPadding(new Insets(6, 6, 6, 6));

        Label title = new Label("Define Student:");

        // this fields
        // Name
        Label fullNameTitle = new Label("Enter Student's Full Name:");
        fullName = new TextField();
        fullName.setPromptText("Full Name");

        this.getChildren().addAll(fullNameTitle, fullName);

        // Academic Status
        Label academicStatusTitle = new Label("Choose Academic Status:");
        academicStatus = new ComboBox();
        academicStatus.getItems().addAll(Student.AcademicStatuses.values());

        this.getChildren().addAll(academicStatusTitle, academicStatus);

        // Employed
        Label employmentTitle = new Label("Choose Employment:");
        ToggleGroup employmentGroup = new ToggleGroup();

        RadioButton unemployed= new RadioButton(Student.JobStatuses.UNEMPLOYED.getName());
        unemployed.setToggleGroup(employmentGroup);
        unemployed.setSelected(true);

        employed = new RadioButton(Student.JobStatuses.EMPLOYED.getName());
        employed.setToggleGroup(employmentGroup);

        this.getChildren().addAll(employmentTitle, unemployed, employed);

        // Job details
        Label jobTitle = new Label("Enter Current Job:");
        job = new TextField();
        job.setPromptText("Current Job");
        job.setText("");
        jobTitle.visibleProperty().bind(employed.selectedProperty());
        job.visibleProperty().bind(employed.selectedProperty());

        this.getChildren().addAll(jobTitle, job);

        // Programming languages known
        Label languagesTitle = new Label("Enter known Programming Languages:");
        this.getChildren().add(languagesTitle);
        ObservableList<ProgrammingLanguage> availableLanguages = languageCatalog.readSavedLanguages();
        availableLanguageCheckBoxes = new ArrayList<Pair<CheckBox, ProgrammingLanguage>>();
        for(ProgrammingLanguage pL : availableLanguages){
            CheckBox availableLanguageCB = new CheckBox(pL.getName());

            availableLanguageCheckBoxes.add(new Pair<>(availableLanguageCB, pL));
            this.getChildren().add(availableLanguageCB);
        }

        // Databases known
        Label databaseTitle = new Label("Enter Known Databases:");
        this.getChildren().add(databaseTitle);
        availableDatabaseCheckBoxes = new ArrayList<Pair<CheckBox, Student.Databases>>();
        for(Student.Databases db : Student.Databases.values()){
            CheckBox availableDatabaseCB = new CheckBox(db.getName());

            availableDatabaseCheckBoxes.add(new Pair<>(availableDatabaseCB, db));
            this.getChildren().add(availableDatabaseCB);
        }

        // Preferred Professional Role
        Label preferredRoleTitle = new Label("Choose Preferred Role:");
        preferredRole = new ComboBox();

        preferredRole.getItems().addAll(Student.ProfessionalRoles.values());

        this.getChildren().addAll(preferredRoleTitle, preferredRole);



        // Future Service Flags
        Label whiteListTitle = new Label("Add To Whitelist:");
        whiteList = new CheckBox();
        Label blackListTitle = new Label("Add To Backlist:");
        blackList = new CheckBox();

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

        this.getChildren().addAll(whiteListTitle, whiteList, blackListTitle, blackList);

    }

    public String getFullName(){
        return fullName.getText().trim();
    }

    public Student.AcademicStatuses getAcademicStatus() {
        return (Student.AcademicStatuses) academicStatus.getValue();
    }

    public Student.JobStatuses getEmployed(){
        return (employed.isSelected()) ? Student.JobStatuses.EMPLOYED : Student.JobStatuses.UNEMPLOYED;
    }

    public String getJob(){
        return job.getText().trim();
    }

    public ObservableList<ProgrammingLanguage> getLanguages(){
        ObservableList<ProgrammingLanguage> selectedLanguages = FXCollections.observableArrayList();
        for(Pair<CheckBox, ProgrammingLanguage> aLPair : availableLanguageCheckBoxes){
            if(aLPair.getKey().isSelected()){
                selectedLanguages.add(aLPair.getValue());
            }
        }
        return selectedLanguages;
    }

    public ObservableList<Student.Databases> getDatabases(){
        ObservableList<Student.Databases> selectedDbs = FXCollections.observableArrayList();
        for(Pair<CheckBox, Student.Databases> kDPair : availableDatabaseCheckBoxes){
            if(kDPair.getKey().isSelected()){
                selectedDbs.add(kDPair.getValue());
            }
        }
        return selectedDbs;
    }

    public Student.ProfessionalRoles getProfessionalRole(){
        return (Student.ProfessionalRoles) preferredRole.getValue();
    }

    public Student.FutureServiceFlags getFutureServiceFlags(){
        Student.FutureServiceFlags fSF;
        if(whiteList.isSelected()){
            fSF = Student.FutureServiceFlags.WHITELISTED;
        } else if(blackList.isSelected()){
            fSF = Student.FutureServiceFlags.BLACKLISTED;
        } else {
            fSF = Student.FutureServiceFlags.NONE;
        }
        return fSF;
    }
}
