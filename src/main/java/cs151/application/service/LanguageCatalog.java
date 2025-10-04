package cs151.application.service;

import cs151.application.domain.ProgrammingLanguage;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class LanguageCatalog {
    private final ObservableList<ProgrammingLanguage> items = FXCollections.observableArrayList();
    public ObservableList<ProgrammingLanguage> items() { return items; }

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
        return true;
    }
}

// This class represents the Service layer --> manages collection of 'Programming Language' entities/objects
// In-memory Catalog (no persistence yet): contains an ObservableList<ProgrammingLanguage>
// handles validation for adding language : no empty/whitespace , duplicate prevention
// designed for planned .CSV persistence swap --> no UI touches required