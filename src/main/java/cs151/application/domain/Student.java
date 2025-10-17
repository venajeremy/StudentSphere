package cs151.application.domain;

import javafx.collections.ObservableList;

import java.util.Objects;

public class Student {

    // Student Settings
    private String fullName;

    private AcademicStatuses academicStatus;

    private JobStatuses jobStatus;

    private String currentJob;

    private ObservableList<ProgrammingLanguage> knownLanguages;

    private ObservableList<Databases> knownDatabases;

    private ProfessionalRoles preferredProfessionalRole;

    private String facultyEvaluation;

    private FutureServiceFlags futureServiceFlag;

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

    // Student Constructor
    public Student(String fullName,
                   AcademicStatuses academicStatus,
                   JobStatuses jobStatus,
                   String currentJob,
                   ObservableList<ProgrammingLanguage> knownLanguages,
                   ObservableList<Databases> knownDatabases,
                   ProfessionalRoles preferredProfessionalRole,
                   String facultyEvaluation,
                   FutureServiceFlags futureServiceFlag
    ) {
        this.fullName = fullName == null ? "" : fullName.trim();
        this.academicStatus = academicStatus;
        this.jobStatus = jobStatus;
        this.currentJob = currentJob;
        this.knownLanguages = knownLanguages;
        this.knownDatabases = knownDatabases;
        this.preferredProfessionalRole = preferredProfessionalRole;
        this.facultyEvaluation = facultyEvaluation;
        this.futureServiceFlag = futureServiceFlag;
    }

    // Student Methods
    public String getName() { return fullName; }
    public AcademicStatuses getAcademicStatus() { return academicStatus; }
    public JobStatuses getJobStatus() { return jobStatus; }
    public String getCurrentJob() { return currentJob; }
    public ObservableList<ProgrammingLanguage> getKnownLanguages() { return knownLanguages; }
    public ObservableList<Databases> getKnownDatabases() { return knownDatabases; }
    public ProfessionalRoles getPreferredProfessionalRole() { return preferredProfessionalRole; }
    public String getFacultyEvaluation() { return facultyEvaluation; }
    public FutureServiceFlags getFutureServiceFlags() { return futureServiceFlag; }

    @Override public String toString() { return fullName; }

    @Override public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Student)) return false;
        Student that = (Student) o;
        return fullName.equalsIgnoreCase(that.fullName);
    }

    @Override public int hashCode() { return Objects.hash(fullName.toLowerCase()); }

}
