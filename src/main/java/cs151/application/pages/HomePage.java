package cs151.application.pages;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import java.util.Objects;

public class HomePage extends Page {

    public HomePage(Runnable onCreateProfile, Runnable onGenerateReport) {
        super();

        // --- Headline + subheadline (Left side) ---
        Label headline = new Label("Know your students, shape their future");
        headline.getStyleClass().add("hero-title");

        Label sub = new Label(
                "Academic insights at your fingertips, allowing faculty to create and retrieve student profiles."
        );
        sub.getStyleClass().add("hero-subtitle");
        sub.setWrapText(true);

        // --- CTA Buttons ---
        Button createProfileBtn = new Button("Create Profile");
        createProfileBtn.getStyleClass().add("cta-primary");
        createProfileBtn.setOnAction(e -> onCreateProfile.run());

        Button generateReportBtn = new Button("Generate Report");
        generateReportBtn.getStyleClass().add("cta-secondary");
        generateReportBtn.setOnAction(e -> onGenerateReport.run());

        HBox ctas = new HBox(12, createProfileBtn, generateReportBtn);
        ctas.setAlignment(Pos.CENTER_LEFT);

        VBox left = new VBox(12, headline, sub, ctas);
        left.setAlignment(Pos.CENTER_LEFT);
        left.setMaxWidth(560);

        // --- Logo (Right side) ---
        ImageView logo = new ImageView(new Image(
                Objects.requireNonNull(getClass().getResourceAsStream("/images/studentsphere_logo.png"))
        ));
        logo.setPreserveRatio(true);
        logo.setSmooth(true);
        logo.setFitHeight(320);

        // Wrap logo in a StackPane to center it in its column
        StackPane logoPane = new StackPane(logo);
        logoPane.setPrefWidth(420);
        logoPane.setAlignment(Pos.CENTER);

        // --- Hero Layout: text (left) + logo (right) ---
        HBox hero = new HBox(60, left, logoPane);
        hero.setAlignment(Pos.CENTER);
        hero.setPadding(new Insets(24));
        HBox.setHgrow(left, Priority.ALWAYS);

        // --- Final Page Layout ---
        this.setAlignment(Pos.CENTER);
        this.getChildren().add(hero);
    }
}
