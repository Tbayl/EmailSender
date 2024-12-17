module org.example.course_project {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.mail;
    requires java.desktop;
    opens sample to javafx.fxml; // Открытие пакета для FXML
    exports org.example.course_project; // Экспорт пакета с вашим приложением
}
