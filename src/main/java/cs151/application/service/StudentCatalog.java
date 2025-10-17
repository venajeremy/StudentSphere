package cs151.application.service;

import com.opencsv.*;
import cs151.application.domain.ProgrammingLanguage;
import cs151.application.domain.Student;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class StudentCatalog extends Catalog {

    private final ObservableList<Student> items = FXCollections.observableArrayList();
    public ObservableList<Student> items() { return items; }
    private static final char SEP = '\t';
    private static final String DEL = ";";

    public StudentCatalog(String fileName){
        super(fileName);


        // Test Student
        /*
        Student JohnDoe = new Student("John Doe",
                Student.AcademicStatuses.FRESHMAN,
                Student.JobStatuses.UNEMPLOYED,
                "None.",
                FXCollections.observableArrayList(new ProgrammingLanguage("Python"), new ProgrammingLanguage("Java")),
                FXCollections.observableArrayList(Student.Databases.MONGODB, Student.Databases.POSTGRES),
                Student.ProfessionalRoles.FRONTEND,
                "No notes yet!",
                Student.FutureServiceFlags.NONE);
        items.add(JohnDoe);
        saveAll();
        */
        readSavedStudents();

    }

    // Private CSV Encoding Helper Functions
    private <E extends Enum<E>> String enumToString(E inEnum){
        return inEnum.name();
    }

    private <E extends Enum<E>> E stringToEnum(Class<E> enumType, String inName){
        return E.valueOf(enumType, inName);
    }

    private <E extends Enum<E>> List<String> enumListToStringList(List<E> enumList){
        List<String> stringList = new ArrayList<>();
        for(E item : enumList){
            stringList.add(enumToString(item));
        }
        return stringList;
    }

    private <E extends Enum<E>> ObservableList<E> stringListToEnumList(Class<E> enumType, List<String> inList){
        ObservableList<E> enumList = FXCollections.observableArrayList();
        for(String item : inList){
            enumList.add(stringToEnum(enumType, item));
        }
        return enumList;
    }

    private List<String> languageListToStringList(List<ProgrammingLanguage> objectList){
        List<String> stringList = new ArrayList<>();
        for(ProgrammingLanguage item : objectList){
            stringList.add(item.toString());
        }
        return stringList;
    }

    private ObservableList<ProgrammingLanguage> stringListToLanguageList(List<String> stringList){
        ObservableList<ProgrammingLanguage> languageList = FXCollections.observableArrayList();
        for(String language : stringList){
            languageList.add(new ProgrammingLanguage(language));
        }
        return languageList;
    }

    private String stringListToDelimitedString(List<String> list){
        // Clean list of delimiters
        list.replaceAll(s -> s.replace(DEL, ""));
        return String.join(DEL, list);
    }

    private List<String> delimitedStringToStringList(String str){
        return Arrays.asList(str.split(DEL));
    }

    // Public Methods
    // add student to items list, sort items list, then save to csv
    public boolean add(Student student, StringBuilder errorOut) {
        if (items.stream().anyMatch(l -> l.equals(student))) {
            errorOut.append("Student already in database.");
            return false;
        }
        items.add(student);
        saveAll();
        return true;
    }

    // update items with students in csv file and return them
    public ObservableList<Student> readSavedStudents(){
        items.clear();
        CSVParser parser = new CSVParserBuilder()
                .withSeparator('\t')
                .build();
        CSVReader reader = new CSVReaderBuilder(getFileReader()).withCSVParser(parser).build();
        List<String[]> entries;
        try{
            entries = reader.readAll();
        } catch(Exception e){
            System.err.println("Could not read csv file: "+e.getMessage());
            return null;
        }
        for(String[] s : entries){
            // Read and format csv entry back into Java Objects
            String fullName = s[0];
            Student.AcademicStatuses academicStatus = stringToEnum(Student.AcademicStatuses.class, s[1]);
            Student.JobStatuses jobStatus = stringToEnum(Student.JobStatuses.class, s[2]);
            String currentJob = s[3];
            ObservableList<ProgrammingLanguage> knownLanguages = stringListToLanguageList(delimitedStringToStringList(s[4]));
            ObservableList<Student.Databases> knownDatabases = stringListToEnumList(Student.Databases.class, delimitedStringToStringList(s[5]));
            Student.ProfessionalRoles preferredRole = stringToEnum(Student.ProfessionalRoles.class, s[6]);
            String facultyEvaluation = s[7];
            Student.FutureServiceFlags futureServiceFlag = stringToEnum(Student.FutureServiceFlags.class, s[8]);

            // Create and add student to list
            Student newStudent = new Student(fullName, academicStatus, jobStatus, currentJob, knownLanguages, knownDatabases, preferredRole, facultyEvaluation, futureServiceFlag);
            items.add(newStudent);
        }
        return items;
    }

    // Private function
    // persists the current list by overwriting the CSV
    private void saveAll() {
        // sort language list before saving
        items.sort((Student a, Student b) -> a.getName().compareTo(b.getName()));

        var fw = getFileWriter();              // overwrite file
        if (fw == null) {
            System.err.println("LanguageCatalog: could not open writer to save all.");
            return;
        }
        try (ICSVWriter writer = new CSVWriterBuilder(fw)
                .withSeparator(SEP)
                .build()) {
            for (Student student : items) {
                String[] parameters = {
                        student.getName(),
                        enumToString(student.getAcademicStatus()),
                        enumToString(student.getJobStatus()),
                        student.getCurrentJob(),
                        stringListToDelimitedString(languageListToStringList(student.getKnownLanguages())),
                        stringListToDelimitedString(enumListToStringList(student.getKnownDatabases())),
                        enumToString(student.getPreferredProfessionalRole()),
                        student.getFacultyEvaluation(),
                        enumToString(student.getFutureServiceFlags())
                };
                writer.writeNext(parameters);
            }
        } catch (Exception e) {
            System.err.println("LanguageCatalog: saveAll error: " + e.getMessage());
        }
    }

}
