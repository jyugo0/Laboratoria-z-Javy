package pwr.karmil.laby13;

import javafx.application.Application;

public class Launcher {
    public static void main(String[] args) {
        System.setProperty("javafx.allowjs", "true");
        System.setProperty("polyglot.js.nashorn-compat", "true");
        Application.launch(HelloApplication.class, args);
    }
}
