module com.example.demo15 {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.demo15 to javafx.fxml;
    exports com.example.demo15;
}