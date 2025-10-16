module cs151.application {
    requires javafx.controls;
    requires javafx.fxml;

    requires com.dlsc.formsfx;
    requires com.opencsv;

    requires javafx.base;
    requires javafx.graphics;
    requires org.apache.commons.lang3;


    opens cs151.application to javafx.fxml;
    exports cs151.application;
}