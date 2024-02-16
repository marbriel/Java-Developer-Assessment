module com.example.javarassessment {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.controlsfx.controls;


    opens com.example.javarassessment to javafx.fxml;
    exports com.example.javarassessment;
}