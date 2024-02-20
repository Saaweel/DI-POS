module org.example {
    requires javafx.controls;
    requires javafx.fxml;

    opens com.saaweel to javafx.fxml;
    exports com.saaweel;
}
