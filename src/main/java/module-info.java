module com.example.tpo_chatapp {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.tpo_chatapp to javafx.fxml;
    exports com.example.tpo_chatapp;
}