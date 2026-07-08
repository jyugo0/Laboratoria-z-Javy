package pwr.karmil.lab14.gui;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import pwr.karmil.lab14.mbean.Agent;
import pwr.karmil.lab14.Bilet;
import pwr.karmil.lab14.Kolejka;
import pwr.karmil.lab14.Stanowisko;

import java.net.URL;
import java.util.ResourceBundle;

public class stanowiskaController implements Initializable {

    private Stanowisko stan1;
    private Stanowisko stan2;
    private Stanowisko stan3;

    @FXML
    private Label case1Label;

    @FXML
    private Label case2Label;

    @FXML
    private Label case3Label;

    @FXML
    private Button confirm1Btn;

    @FXML
    private Button confirm2Btn;

    @FXML
    private Button confirm3Btn;

    @FXML
    private ChoiceBox<String> changeCategoriesChoiceBox;

    @FXML
    private ChoiceBox<String> changeCategoriesChoiceBox2;

    @FXML
    private ChoiceBox<String> changeCategoriesChoiceBox3;

    @FXML
    void onAddBtn(ActionEvent event) {
        zmienKategorie(stan1, changeCategoriesChoiceBox, true);
    }

    @FXML
    void onAddBtn2(ActionEvent event) {
        zmienKategorie(stan2, changeCategoriesChoiceBox2, true);
    }

    @FXML
    void onAddBtn3(ActionEvent event) {
        zmienKategorie(stan3, changeCategoriesChoiceBox3, true);
    }

    @FXML
    void onDelBtnClicked(ActionEvent event) {
        zmienKategorie(stan1, changeCategoriesChoiceBox, false);
    }

    @FXML
    void onDelBtn2Clicked(ActionEvent event) {
        zmienKategorie(stan2, changeCategoriesChoiceBox2, false);
    }

    @FXML
    void onDelBtn3Clicked(ActionEvent event) {
        zmienKategorie(stan3, changeCategoriesChoiceBox3, false);
    }

    @FXML
    void onConfirmBtnClicked(ActionEvent event) {
        Button clickedButton = (Button) event.getSource();
        if (clickedButton == confirm1Btn) obsluzNastepnego(stan1, case1Label);
        else if (clickedButton == confirm2Btn) obsluzNastepnego(stan2, case2Label);
        else if (clickedButton == confirm3Btn) obsluzNastepnego(stan3, case3Label);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        stan1 = new Stanowisko(1, "Stypendium,Legitymacja");
        stan2 = new Stanowisko(2, "Praca dyplomowa,Zaliczenia");
        stan3 = new Stanowisko(3, "Wszystkie");

        Kolejka.getInstance().addStanowisko(stan1);
        Kolejka.getInstance().addStanowisko(stan2);
        Kolejka.getInstance().addStanowisko(stan3);

        var kategorie = FXCollections.observableArrayList(
                "Stypendium", "Praca dyplomowa", "Legitymacja", "Zaliczenia", "Praktyki", "Inne", "Wszystkie"
        );
        changeCategoriesChoiceBox.setItems(kategorie);
        changeCategoriesChoiceBox2.setItems(kategorie);
        changeCategoriesChoiceBox3.setItems(kategorie);

        Kolejka.getInstance().setOdswiezStanowiska(() -> {
            aktualizujLabelOperatora(stan1, case1Label);
            aktualizujLabelOperatora(stan2, case2Label);
            aktualizujLabelOperatora(stan3, case3Label);
        });
    }


    private void aktualizujLabelOperatora(Stanowisko stan, Label lbl) {
        if (stan.getObecny() != null) {
            lbl.setText(String.valueOf(stan.getObecny().getQueueNum()));
        } else {
            lbl.setText("Brak");
        }
    }

    private void obsluzNastepnego(Stanowisko stanowisko, Label displayLabel) {
        stanowisko.setObecny(null);
        Kolejka.getInstance().weryfikuj();
    }

    private void zmienKategorie(Stanowisko stanowisko, ChoiceBox<String> choiceBox, boolean czyDodac) {
        String wybranaKategoria = choiceBox.getValue();
        if (wybranaKategoria == null) return;

        String stareKategorie = stanowisko.getKategorieString();

        if (czyDodac) {
            stanowisko.addKategorie(wybranaKategoria);
        } else {
            stanowisko.usunKategorie(wybranaKategoria);
        }

        Agent ziarenko = Kolejka.getInstance().getAgent();
        if (ziarenko != null) {
            ziarenko.wyslijNotyfikacjeOZmianie(
                    "Kategorie Stanowiska " + stanowisko.getNumerStanowiska(),
                    stareKategorie,
                    stanowisko.getKategorieString()
            );
        }
        System.out.println("Stanowisko " + stanowisko.getNumerStanowiska() + " obsługuje teraz: " + stanowisko.getKategorieString());

        Kolejka.getInstance().weryfikuj();
    }
}