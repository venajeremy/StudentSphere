package cs151.application.domain;

import java.util.Objects;

public final class ProgrammingLanguage {
    private final String name;

    public ProgrammingLanguage(String name) {
        this.name = name == null ? "" : name.trim();
    }

    public String getName() { return name; }

    @Override public String toString() { return name; }

    @Override public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ProgrammingLanguage)) return false;
        ProgrammingLanguage that = (ProgrammingLanguage) o;
        return name.equalsIgnoreCase(that.name);
    }

    @Override public int hashCode() { return Objects.hash(name.toLowerCase()); }
}

// ProgrammingLanguage Domain Model
//  this domain model represents a single programming language known to StudentSphere
//  models a core data type the 'Programming Language' entity ; used throughout application