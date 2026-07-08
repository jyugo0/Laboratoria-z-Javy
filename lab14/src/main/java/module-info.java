module pwr.karmil.lab14 {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;
    requires java.management;


    opens pwr.karmil.lab14 to javafx.fxml;
    exports pwr.karmil.lab14;
    exports pwr.karmil.lab14.mbean;
    opens pwr.karmil.lab14.mbean to javafx.fxml;
    exports pwr.karmil.lab14.gui;
    opens pwr.karmil.lab14.gui to javafx.fxml;
}