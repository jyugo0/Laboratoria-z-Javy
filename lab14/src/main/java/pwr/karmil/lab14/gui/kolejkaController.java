package pwr.karmil.lab14.gui;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import pwr.karmil.lab14.Bilet;
import pwr.karmil.lab14.Kolejka;
import pwr.karmil.lab14.Stanowisko;
import java.net.URL;
import java.util.ResourceBundle;

public class kolejkaController implements Initializable {

    @FXML
    private ListView<Bilet> listView;

    @FXML
    private Button stan1Button;

    @FXML
    private Label stan1Label;

    @FXML
    private Button stan2Button;

    @FXML
    private Label stan2Label;

    @FXML
    private Button stan3Button;

    @FXML
    private Label stan3Label;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        listView.setItems(Kolejka.getInstance().getBilety());
        stan1Button.setDisable(true);
        stan2Button.setDisable(true);
        stan3Button.setDisable(true);

        Kolejka.getInstance().setOdswiezTab(() -> {
            updateBooth(1, Kolejka.getInstance().getStanowisko(1));
            updateBooth(2, Kolejka.getInstance().getStanowisko(2));
            updateBooth(3, Kolejka.getInstance().getStanowisko(3));
            listView.refresh();
        });
    }

    public void updateBooth(int boothNum, Stanowisko booth) {
        if (booth == null) return;

        Button btn = null; Label lbl = null;
        if (boothNum == 1) { btn = stan1Button; lbl = stan1Label; }
        else if (boothNum == 2) { btn = stan2Button; lbl = stan2Label; }
        else if (boothNum == 3) { btn = stan3Button; lbl = stan3Label; }

        if (btn != null && lbl != null) {

            if (booth.getObecny() != null) {
                ustawWygadPrzycisku(btn, false);
            } else {
                ustawWygadPrzycisku(btn, true);
            }
            if (booth.getNast() != null) {
                lbl.setText(String.valueOf(booth.getNast().getQueueNum()));
            } else {
                lbl.setText("-");
            }
        }
    }

    private void ustawWygadPrzycisku(Button btn, boolean wolne) {
        if (wolne) {
            btn.setText("WOLNE");
            btn.setStyle("-fx-background-color: lightgreen; -fx-text-fill: black; -fx-opacity: 1; -fx-font-weight: bold;");
        } else {
            btn.setText("ZAJĘTE");
            btn.setStyle("-fx-background-color: salmon; -fx-text-fill: black; -fx-opacity: 1; -fx-font-weight: bold;");
        }
    }
}