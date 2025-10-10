package cs151.application.service;

import java.io.*;

public class Catalog {
    private final String fileName;
    private FileReader fileReader;
    private FileWriter fileWriter;

    public Catalog(String catalogFileName){
        fileName = catalogFileName;
        initializeSaveFile();
    }

    public FileReader getFileReader(){
        FileReader reader;
        try {
            reader = new FileReader(fileName);
        } catch(FileNotFoundException e){
            System.err.println("File could not be found: "+e.getMessage());
            return null;
        }
        return reader;
    }

    public FileWriter getFileWriter(){
        FileWriter writer;
        try {
            writer = new FileWriter(fileName);
        } catch(FileNotFoundException e){
            System.err.println("File could not be found: "+e.getMessage());
            return null;
        } catch(IOException i){
            System.err.println("Could not write to file: "+i.getMessage());
            return null;
        }
        return writer;
    }

    public FileWriter getFileWriterAppend(){
        FileWriter writer;
        try {
            writer = new FileWriter(fileName, true);
        } catch(FileNotFoundException e){
            System.err.println("File could not be found: "+e.getMessage());
            return null;
        } catch(IOException i){
            System.err.println("Could not write to file: "+i.getMessage());
            return null;
        }
        return writer;
    }

    private void initializeSaveFile(){
        // Ensure file is present
        File newFile = new File(fileName);
        try {
            // Creates file if it does not already exist
            newFile.createNewFile();
        } catch(IOException i){
            System.err.println("An error occurred while creating the file: " + i.getMessage());
        }
    }

}
