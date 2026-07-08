module pwr.karmil.laby12 {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.graalvm.polyglot;


    opens pwr.karmil.laby12 to javafx.fxml;
    exports pwr.karmil.laby12;
}