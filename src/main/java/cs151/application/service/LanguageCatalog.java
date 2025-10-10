package cs151.application.service;

import com.opencsv.*;
import cs151.application.domain.ProgrammingLanguage;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.IOException;
import java.util.List;

public class LanguageCatalog extends Catalog {
    private final ObservableList<ProgrammingLanguage> items = FXCollections.observableArrayList();
    public ObservableList<ProgrammingLanguage> items() { return items; }

    public LanguageCatalog(String fileName){
        super(fileName);
        readSavedLanguages();
    }

    private void readSavedLanguages(){
        items.clear();
        CSVReader reader = new CSVReaderBuilder(getFileReader()).build();
        List<String[]> entries;
        try{
            entries = reader.readAll();
        } catch(Exception e){
            System.err.println("Could not read csv file: "+e.getMessage());
            return;
        }
        for(String[] s : entries){
            String languageName = s[0];
            items.add(new ProgrammingLanguage(languageName));
        }
    }

    private void addSavedLanguage(String lang){
        ICSVWriter writer = new CSVWriterBuilder(getFileWriterAppend()).withSeparator('\t').build();
        // feed in your array (or convert your data to an array)
        String[] entries = {lang};
        writer.writeNext(entries);
        try {
            writer.close();
        }catch (IOException e){
            System.err.println("Could not write to csv file: "+e.getMessage());
        }
    }

    /** Adds a language; returns false and writes an error message if invalid. */
    public boolean add(String rawName, StringBuilder errorOut) {
        if (rawName == null || rawName.trim().isEmpty()) {
            errorOut.append("Language name is required.");
            return false;
        }
        ProgrammingLanguage candidate = new ProgrammingLanguage(rawName);
        if (items.stream().anyMatch(l -> l.equals(candidate))) {
            errorOut.append("Language already exists.");
            return false;
        }
        items.add(candidate);
        addSavedLanguage(rawName);
        return true;
    }
}

// This class represents the Service layer --> manages collection of 'Programming Language' entities/objects
// In-memory Catalog (no persistence yet): contains an ObservableList<ProgrammingLanguage>
// handles validation for adding language : no empty/whitespace , duplicate prevention
// designed for planned .CSV persistence swap --> no UI touches required