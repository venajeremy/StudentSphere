package cs151.application.domain;

import java.time.LocalDate;

public class Comment {
    int iD;
    int studentID;
    String message;
    LocalDate LocalDate;

    public Comment(int studentID, String message, LocalDate LocalDate){
        this.studentID = studentID;
        this.message = message;
        this.LocalDate = LocalDate;
    }

    public Comment(int iD, int studentID, String message, LocalDate LocalDate){
        this.iD = iD;
        this.studentID = studentID;
        this.message = message;
        this.LocalDate = LocalDate;
    }

    public void setID(int iD){ this.iD = iD; }
    public int getID(){ return this.iD; }
    public int getStudentID(){ return studentID; }
    public String getMessage(){ return message; }
    public LocalDate getDate(){ return LocalDate; }

}
