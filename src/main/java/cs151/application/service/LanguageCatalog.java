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
    // use same separator as addSavedLanguage()
    private static final char SEP = '\t';

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
        saveAll();
        return true;
    }

    // persists the current list by overwriting the CSV
    private void saveAll() {
        // sort language list before saving
        items.sort((ProgrammingLanguage a, ProgrammingLanguage b) -> a.getName().compareTo(b.getName()));

        var fw = getFileWriter();              // overwrite file
        if (fw == null) {
            System.err.println("LanguageCatalog: could not open writer to save all.");
            return;
        }
        try (ICSVWriter writer = new CSVWriterBuilder(fw)
                .withSeparator(SEP)
                .build()) {
            for (ProgrammingLanguage pl : items) {
                writer.writeNext(new String[]{pl.getName()});
            }
        } catch (Exception e) {
            System.err.println("LanguageCatalog: saveAll error: " + e.getMessage());
        }
    }

    // delete an entry, returning true if successfully removed and persisted
    public boolean remove(ProgrammingLanguage toRemove) {
        if (toRemove == null) return false;
        boolean removed = items.remove(toRemove);
        if (removed) saveAll();
        return removed;
    }

    // edit entry, renames entry by replacing with new immutable instance
    public boolean update(ProgrammingLanguage original, String newRawName, StringBuilder errorOut) {

        // null check
        if (original == null) {
            if (errorOut != null) errorOut.append("Nothing selected to edit.");
            return false;
        }

        String cleaned = newRawName == null ? "" : newRawName.trim();
        if (cleaned.isEmpty()) {
            if (errorOut != null) errorOut.append("Language name is required.");
            return false;
        }
        ProgrammingLanguage candidate = new ProgrammingLanguage(cleaned);

        // if name unchanged, do nothing (but still considered success).
        if (candidate.equals(original)) return true;

        // prevent duplicates
        if (items.stream().anyMatch(l -> l.equals(candidate))) {
            if (errorOut != null) errorOut.append("Language already exists.");
            return false;
        }

        int idx = items.indexOf(original);
        if (idx < 0) { // original not found; refresh from disk and inform user
            if (errorOut != null) errorOut.append("Original entry no longer exists.");
            return false;
        }

        items.set(idx, candidate); // replace in observable list
        saveAll();                 // persist full list
        return true;
    }
}

// This class represents the Service layer --> manages collection of 'Programming Language' entities/objects
// In-memory Catalog (no persistence yet): contains an ObservableList<ProgrammingLanguage>
// handles validation for adding language : no empty/whitespace , duplicate prevention
// designed for planned .CSV persistence swap --> no UI touches required
