package pwr.karmil.lab14;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import javax.management.MBeanServer;
import javax.management.ObjectName;
import java.io.IOException;
import java.lang.management.ManagementFactory;

public class DziekanatApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader loaderBiletomat = new FXMLLoader(getClass().getResource("biletomat.fxml")); // Podmień ścieżki do swoich plików .fxml
        stage.setTitle("Dziekanat");
        Image image = new Image("dziekanat.png");
        stage.getIcons().add(image);
        stage.setScene(new Scene(loaderBiletomat.load()));
        stage.show();

        Stage queueStage = new Stage();
        FXMLLoader loaderKolejka = new FXMLLoader(getClass().getResource("kolejka.fxml"));
        queueStage.setTitle("Tablica Informacyjna");
        Image image2 = new Image("busy.png");
        queueStage.getIcons().add(image2);
        queueStage.setScene(new Scene(loaderKolejka.load()));
        queueStage.show();

        Stage ticketStage = new Stage();
        FXMLLoader loaderStanowiska = new FXMLLoader(getClass().getResource("stanowiska.fxml"));
        ticketStage.setTitle("Biletomat");
        Image image3 = new Image("ticket.png");
        ticketStage.getIcons().add(image3);
        ticketStage.setScene(new Scene(loaderStanowiska.load()));
        ticketStage.show();
    }

    public static void main(String[] args) {
        try {
            MBeanServer mbs = ManagementFactory.getPlatformMBeanServer();
            ObjectName name = new ObjectName("pwr.karmil.lab14.mbean:type=Agent");
            pwr.karmil.lab14.mbean.Agent ziarenko = new pwr.karmil.lab14.mbean.Agent();
            Kolejka.getInstance().setAgent(ziarenko);
            mbs.registerMBean(ziarenko, name);
            System.out.println("Ziarenko zrobione");
        } catch (Exception e) {
            e.printStackTrace();
        }
        launch(args);
    }
}
