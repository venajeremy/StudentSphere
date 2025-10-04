package cs151.application.pages;

import javafx.scene.control.Label;

public class AddLanguagePage extends Page {
    public AddLanguagePage() {
        super();
        this.getChildren().add(new Label("Add a new programming language!"));
    }
}
