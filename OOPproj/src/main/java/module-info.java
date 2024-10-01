module com.example.oopproj {
    requires javafx.controls;
    requires javafx.fxml;

    requires com.dlsc.formsfx;
    requires java.desktop;

    opens com.example.oopproj to javafx.fxml;
    exports com.example.oopproj;
}