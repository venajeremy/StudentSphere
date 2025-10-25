package cs151.application.domain;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.Objects;

public class Student {

    // Student Settings
    private int iD;

    private String fullName;

    private AcademicStatuses academicStatus;

    private JobStatuses jobStatus;

    private String currentJob;

    private ObservableList<ProgrammingLanguage> knownLanguages;

    private ObservableList<Databases> knownDatabases;

    private ProfessionalRoles preferredProfessionalRole;

    private ObservableList<Comment> comments = FXCollections.observableArrayList();

    private FutureServiceFlags futureServiceFlag;

    // Student Constructor
    public Student(String fullName,
                   AcademicStatuses academicStatus,
                   JobStatuses jobStatus,
                   String currentJob,
                   ObservableList<ProgrammingLanguage> knownLanguages,
                   ObservableList<Databases> knownDatabases,
                   ProfessionalRoles preferredProfessionalRole,
                   FutureServiceFlags futureServiceFlag
    ) {
        this.fullName = fullName == null ? "" : fullName.trim();
        this.academicStatus = academicStatus;
        this.jobStatus = jobStatus;
        this.currentJob = currentJob;
        this.knownLanguages = (knownLanguages == null)
                ? FXCollections.observableArrayList() : knownLanguages;
        this.knownDatabases = (knownDatabases == null)
                ? FXCollections.observableArrayList() : knownDatabases;
        this.preferredProfessionalRole = preferredProfessionalRole;
        this.futureServiceFlag = futureServiceFlag;
    }

    // Student Constructor With ID
    public Student(int iD,
                   String fullName,
                   AcademicStatuses academicStatus,
                   JobStatuses jobStatus,
                   String currentJob,
                   ObservableList<ProgrammingLanguage> knownLanguages,
                   ObservableList<Databases> knownDatabases,
                   ProfessionalRoles preferredProfessionalRole,
                   FutureServiceFlags futureServiceFlag
    ) {
        this.iD = iD;
        this.fullName = fullName == null ? "" : fullName.trim();
        this.academicStatus = academicStatus;
        this.jobStatus = jobStatus;
        this.currentJob = currentJob;
        this.knownLanguages = (knownLanguages == null)
                ? FXCollections.observableArrayList() : knownLanguages;
        this.knownDatabases = (knownDatabases == null)
                ? FXCollections.observableArrayList() : knownDatabases;
        this.preferredProfessionalRole = preferredProfessionalRole;
        this.futureServiceFlag = futureServiceFlag;
    }

    // Student Methods
    public Integer getID() { return iD; }
    public String getName() { return fullName; }
    public AcademicStatuses getAcademicStatus() { return academicStatus; }
    public JobStatuses getJobStatus() { return jobStatus; }
    public String getCurrentJob() { return currentJob; }
    public ObservableList<ProgrammingLanguage> getKnownLanguages() {
        return (knownLanguages == null) ? FXCollections.observableArrayList() : knownLanguages;
    }
    public ObservableList<Databases> getKnownDatabases() {
        return (knownDatabases == null) ? FXCollections.observableArrayList() : knownDatabases; }
    public ProfessionalRoles getPreferredProfessionalRole() { return preferredProfessionalRole; }
    public FutureServiceFlags getFutureServiceFlags() { return futureServiceFlag; }

    public void addComment(Comment newComment){
        comments.add(newComment);
    }

    public ObservableList<Comment> getComments(){
        return comments;
    }

    public void setID(int iD){
        this.iD = iD;
    }

    @Override public String toString() { return fullName; }

    @Override public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Student)) return false;
        Student that = (Student) o;
        return fullName.equalsIgnoreCase(that.fullName);
    }

    @Override public int hashCode() { return Objects.hash(fullName.toLowerCase()); }

    // Student Setting Enums
    public enum FutureServiceFlags {
        WHITELISTED("Whitelisted"),
        BLACKLISTED("Blacklisted"),
        NONE("None");

        private final String name;

        FutureServiceFlags(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }

        @Override
        public String toString() {
            return name;
        }
    }

    public enum ProfessionalRoles {
        FRONTEND("Front-End"),
        BACKEND("Back-End"),
        FULLSTACK("Full-Stack"),
        DATA("Data"),
        OTHER("Other");

        private final String name;

        ProfessionalRoles(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }

        @Override
        public String toString() {
            return name;
        }
    }

    public enum Databases {
        MYSQL("MySQL"),
        POSTGRES("Postgres"),
        MONGODB("MongoDB");

        private final String name;

        Databases(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }

        @Override
        public String toString() {
            return name;
        }
    }

    public enum AcademicStatuses {
        FRESHMAN("Freshman"),
        SOPHOMORE("Sophomore"),
        JUNIOR("Junior"),
        SENIOR("Senior"),
        GRADUATE("Graduate");

        private final String name;

        AcademicStatuses(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }

        @Override
        public String toString() {
            return name;
        }
    }

    public enum JobStatuses {
        EMPLOYED("Employed"),
        UNEMPLOYED("Unemployed");

        private final String name;

        JobStatuses(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }

        @Override
        public String toString() {
            return name;
        }
    }

}
