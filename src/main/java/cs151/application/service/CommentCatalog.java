package cs151.application.service;

import com.opencsv.*;
import cs151.application.domain.Comment;
import cs151.application.domain.ProgrammingLanguage;
import cs151.application.domain.Student;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class CommentCatalog extends Catalog{

    private final static ObservableList<Comment> items = FXCollections.observableArrayList();
    public ObservableList<Comment> items() { return items; }
    private int maxID;
    private static final char SEP = '\t';

    private HashMap<Integer, ArrayList<Comment>> studentComments = new HashMap<Integer, ArrayList<Comment>>();

    public CommentCatalog(String fileName){
        super(fileName);

        readSavedComments();
    }

    private void clearCommentCahce(){
        studentComments.clear();
    }

    private void addCommentCache(int studentID, Comment comment){
        if(studentComments.containsKey(studentID)){
            studentComments.get(studentID).add(comment);
        } else {
            studentComments.put(studentID, new ArrayList<Comment>());
            studentComments.get(studentID).add(comment);
        }
    }

    public ArrayList<Comment> getCommentsForID(int studentID){
        return studentComments.get(studentID);
    }

    public boolean add(Comment comment) {

        // Set students id
        comment.setID(maxID+1);
        maxID++;

        // Add student
        addCommentCache(comment.getStudentID(), comment);
        items.add(comment);
        saveAll();
        return true;
    }

    // remove comments to this user
    public void removeFromStudent(int studentID){
        items.removeIf(comment -> comment.getStudentID() == studentID);
        saveAll();
    }

    // Private functions
    // persists the current list by overwriting the CSV
    private void saveAll() {
        var fw = getFileWriter();              // overwrite file
        if (fw == null) {
            System.err.println("CommentCatalog: could not open writer to save all.");
            return;
        }
        try (ICSVWriter writer = new CSVWriterBuilder(fw)
                .withSeparator(SEP)
                .build()) {
            for (Comment comment : items) {
                try {
                    String[] parameters = {
                            Integer.toString(comment.getID()),
                            Integer.toString(comment.getStudentID()),
                            comment.getMessage(),
                            dateToString(comment.getDate())
                    };
                    writer.writeNext(parameters);
                } catch (Exception e){
                    System.err.println("CommentCatalog: error saving comment: "+comment.getID()+", with error: " + e.getMessage());
                }

            }
        } catch (Exception e) {
            System.err.println("CommentCatalog: saveAll error: " + e.getMessage());
        }
    }

    // update items with comments in csv file and return them
    public ObservableList<Comment> readSavedComments(){
        CSVParser parser = new CSVParserBuilder()
                .withSeparator(SEP)
                .build();
        CSVReader reader = new CSVReaderBuilder(getFileReader()).withCSVParser(parser).build();
        List<String[]> entries;
        try{
            entries = reader.readAll();
        } catch(Exception e){
            System.err.println("Could not read csv file: "+e.getMessage());
            return null;
        }
        items.clear();
        clearCommentCahce();
        for(String[] s : entries){
            // Read and format csv entry back into Java Objects
            int iD = Integer.parseInt(s[0]);
            int studentID = Integer.parseInt(s[1]);
            String message = s[2];
            LocalDate date = stringToDate(s[3]);

            // Update max ID
            maxID = Math.max(maxID, iD);

            // Create and add student to list
            Comment newComment = new Comment(iD, studentID, message, date);
            addCommentCache(newComment.getStudentID(), newComment);
            items.add(newComment);
        }
        return items;
    }


    // Private helper methods
    private String dateToString(LocalDate date){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        return date.format(formatter);
    }
    private LocalDate stringToDate(String date){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        return  LocalDate.parse(date, formatter);
    }
}
