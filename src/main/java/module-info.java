module com.example.sheikh_2207051_cvbuilder {
    requires javafx.controls;
    requires javafx.fxml;

    opens com.example.sheikh_2207051_cvbuilder.controllers to javafx.fxml;
    opens com.example.sheikh_2207051_cvbuilder.model to javafx.fxml;
    opens com.example.sheikh_2207051_cvbuilder to javafx.fxml;

    exports com.example.sheikh_2207051_cvbuilder;
}
