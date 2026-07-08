package pwr.karmil.lab14.gui;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import pwr.karmil.lab14.Bilet;
import pwr.karmil.lab14.Kolejka;

import java.net.URL;
import java.util.ResourceBundle;

public class biletomatController implements Initializable {

    @FXML
    private ChoiceBox<String> caseChoiceBox;

    @FXML
    private Pane ticketPane;

    @FXML
    private Label numberLabel;

    @FXML
    private Label priorityLabel;

    private int licznik = 1;

    @FXML
    void onGetTicketBtnClicked(ActionEvent event) {
        String kategoria = caseChoiceBox.getSelectionModel().getSelectedItem();
        String priorytet = Kolejka.getInstance().getPriority(kategoria);
        Bilet bilet = new Bilet(licznik++, kategoria, "OCZEKIWANIE", priorytet);
        Kolejka.getInstance().addBilet(bilet);
        showBilet(bilet);
    }

    void showBilet(Bilet bilet) {
        numberLabel.setText(String.valueOf(bilet.getQueueNum()));
        priorityLabel.setText(bilet.getPriority());
        ticketPane.setVisible(true);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        caseChoiceBox.setItems(FXCollections.observableArrayList(
                "Stypendium", "Praca dyplomowa", "Legitymacja", "Zaliczenia", "Praktyki", "Inne"
        ));
        caseChoiceBox.getSelectionModel().select(0);
        ticketPane.setVisible(false);
    }
}