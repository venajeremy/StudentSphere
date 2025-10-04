package cs151.application.pages;

import javafx.scene.control.Label;

public class SearchStudentPage extends Page {
    public SearchStudentPage(){
        super();
        this.getChildren().add(new Label("Search through students entered into system!"));
    }
}
