package pwr.karmil.lab14;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Stanowisko {
    private int numerStanowiska;
    private Bilet obecny;
    private Bilet nastepny;
    private List<String> obslugiwaneKategorie;

    public Stanowisko(int numerStanowiska, String kategorieZPrzecinkiem) {
        this.numerStanowiska = numerStanowiska;
        setKategorie(kategorieZPrzecinkiem);
    }

    public boolean obslugujeKategorie(String kategoria) {
        return obslugiwaneKategorie.contains("Wszystkie") || obslugiwaneKategorie.contains(kategoria);
    }

    public void setObecny(Bilet bilet) {
        this.obecny = bilet;
    }

    public Bilet getObecny() {
        return obecny;
    }

    public void setNast(Bilet bilet) {
        this.nastepny = bilet;
    }

    public Bilet getNast() {
        return nastepny;
    }

    public boolean isFree() {
        return obecny == null;
    }

    public int getNumerStanowiska() {
        return numerStanowiska;
    }

    public String getKategorieString() {
        return String.join(",", obslugiwaneKategorie);
    }

    public void setKategorie(String kategorie) {
        this.obslugiwaneKategorie = new ArrayList<>(Arrays.asList(kategorie.split(",")));
    }

    public void addKategorie(String kategoria) {
        if (kategoria.equals("Wszystkie")) {
            obslugiwaneKategorie.clear();
            obslugiwaneKategorie.add("Wszystkie");
        } else {
            obslugiwaneKategorie.remove("Wszystkie");
            if (!obslugiwaneKategorie.contains(kategoria)) {
                obslugiwaneKategorie.add(kategoria);
            }
        }
    }

    public void usunKategorie(String kategoria) {
        if (kategoria.equals("Wszystkie")) {
            obslugiwaneKategorie.clear();
        } else if (obslugiwaneKategorie.contains("Wszystkie")) {
            obslugiwaneKategorie.remove("Wszystkie");
            obslugiwaneKategorie.addAll(Arrays.asList(
                    "Stypendium", "Praca dyplomowa", "Legitymacja", "Zaliczenia", "Praktyki", "Inne"
            ));
            obslugiwaneKategorie.remove(kategoria);
        } else {
            obslugiwaneKategorie.remove(kategoria);
        }
    }
}