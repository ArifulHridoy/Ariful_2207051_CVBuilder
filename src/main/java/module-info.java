module com.example.sheikh_2207051_cvbuilder {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires java.desktop;
    requires javafx.swing;
    requires com.fasterxml.jackson.databind;

    opens com.example.sheikh_2207051_cvbuilder.controllers to javafx.fxml;
    opens com.example.sheikh_2207051_cvbuilder.model to javafx.fxml, com.fasterxml.jackson.databind;   // ➜ Jackson needs reflection
    opens com.example.sheikh_2207051_cvbuilder to javafx.fxml;
    opens com.example.sheikh_2207051_cvbuilder.db to javafx.fxml;
    opens com.example.sheikh_2207051_cvbuilder.util to javafx.fxml, com.fasterxml.jackson.databind;

    exports com.example.sheikh_2207051_cvbuilder;
}
