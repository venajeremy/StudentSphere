package cs151.application.service;

import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.CSVWriterBuilder;
import com.opencsv.ICSVWriter;
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
        Student JohnDoe = new Student("John Doe",
                Student.AcademicStatuses.FRESHMAN,
                Student.JobStatuses.UNEMPLOYED,
                FXCollections.observableArrayList(new ProgrammingLanguage("Python"), new ProgrammingLanguage("Java")),
                FXCollections.observableArrayList(Student.Databases.MONGODB, Student.Databases.POSTGRES),
                "No notes yet!",
                Student.FutureServiceFlags.NONE);
        items.add(JohnDoe);
        saveAll();
    }

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

    private <E extends Enum<E>> List<E> stringListToEnumList(Class<E> enumType, List<String> inList){
        List<E> enumList = new ArrayList<>();
        for(String item : inList){
            enumList.add(stringToEnum(enumType, item));
        }
        return enumList;
    }

    private <T> List<String> objectListToStringList(List<T> objectList){
        List<String> stringList = new ArrayList<>();
        for(T item : objectList){
            stringList.add(item.toString());
        }
        return stringList;
    }

    private String stringListToDelimitedString(List<String> list){
        // Clean list of delimiters
        list.replaceAll(s -> s.replace(DEL, ""));
        return String.join(DEL, list);
    }

    private List<String> delimitedStringToStringList(String str){
        return Arrays.asList(str.split(DEL));
    }

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
                        stringListToDelimitedString(objectListToStringList(student.getKnownLanguages())),
                        stringListToDelimitedString(enumListToStringList(student.getKnownDatabases())),
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
