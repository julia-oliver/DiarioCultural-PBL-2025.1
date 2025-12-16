module com.example.n {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.google.gson;

    opens com.example.n to javafx.fxml;
    opens com.example.n.model to com.google.gson; // Permite que o GSON acesse as classes do pacote model

    exports com.example.n;
    exports com.example.n.model;
    exports com.example.n.controllerjavafx;
    opens com.example.n.controllerjavafx to javafx.fxml;
}