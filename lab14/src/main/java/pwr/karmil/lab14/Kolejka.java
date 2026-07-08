package pwr.karmil.lab14;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Kolejka {
    private static Kolejka instance;
    private ObservableList<Bilet> bilety = FXCollections.observableArrayList();
    private Map<String, String> piorytety = new HashMap<>();
    private pwr.karmil.lab14.mbean.Agent agent;
    private Runnable odswiezStanowiska;
    private Runnable odswiezTab;
    private Map<Integer, Stanowisko> stanowiska = new HashMap<>();

    private Kolejka() {
        piorytety.put("Stypendium", "Wysoki");
        piorytety.put("Praca dyplomowa", "Wysoki");
        piorytety.put("Legitymacja", "Normalny");
        piorytety.put("Zaliczenia", "Normalny");
        piorytety.put("Praktyki", "Niski");
        piorytety.put("Inne", "Niski");
    }

    public void setAgent(pwr.karmil.lab14.mbean.Agent agent) {
        this.agent = agent;
    }

    public pwr.karmil.lab14.mbean.Agent getAgent() {
        return agent;
    }

    public static Kolejka getInstance() {
        if (instance == null) {
            instance = new Kolejka();
        }
        return instance;
    }

    public ObservableList<Bilet> getBilety() {
        return bilety;
    }

    public Stanowisko getStanowisko(int numer) {
        return stanowiska.get(numer);
    }

    public void addBilet(Bilet ticket) {
        bilety.add(ticket);
        weryfikuj();
    }

    public void setOdswiezStanowiska(Runnable akcja) {
        this.odswiezStanowiska = akcja;
    }

    public void setOdswiezTab(Runnable akcja) {
        this.odswiezTab = akcja;
    }

    public void odswiezWszystkieWidoki() {
        if (odswiezStanowiska != null) {
            javafx.application.Platform.runLater(odswiezStanowiska);
        }
        if (odswiezTab != null) {
            javafx.application.Platform.runLater(odswiezTab);
        }
    }

    public void weryfikuj() {
        for (Stanowisko s : stanowiska.values()) {

            if (s.getObecny() == null && s.getNast() != null) {
                Bilet obecny = s.getNast();
                s.setObecny(obecny);
                s.setNast(null);
                bilety.removeIf(b -> b.getQueueNum() == obecny.getQueueNum());
            }

            if (s.getObecny() == null) {
                Bilet obecny = pobierzBilet(s);
                if (obecny != null) {
                    s.setObecny(obecny);
                    bilety.removeIf(b -> b.getQueueNum() == obecny.getQueueNum());
                }
            }

            if (s.getNast() == null) {
                Bilet nast = pobierzBilet(s);
                if (nast != null) {
                    nast.setState("KOLEJNY");
                    s.setNast(nast);
                }
            }
        }
        odswiezWszystkieWidoki();
    }

    public void addStanowisko(Stanowisko stanowisko) {
        stanowiska.put(stanowisko.getNumerStanowiska(), stanowisko);
    }

    public String[] getWszystkieKategorie() {
        return piorytety.keySet().toArray(new String[0]);
    }

    public void setWszystkieKategorie(String[] kategorie) {
        Map<String, String> nowaMapa = new HashMap<>();
        for (String kat : kategorie) {
            nowaMapa.put(kat, piorytety.getOrDefault(kat, "Normalny"));
        }
        piorytety = nowaMapa;
    }

    public void ustawPriorytet(String kategoria, int nowyPriorytet) {
        String priotytet;
        if (nowyPriorytet == 3) priotytet = "Wysoki";
        else if (nowyPriorytet == 1) priotytet = "Niski";
        else priotytet = "Normalny";

        piorytety.put(kategoria, priotytet);
    }

    public void zmienKategorieStanowiska(int numerStanowiska, String kategorie) {
        Stanowisko s = stanowiska.get(numerStanowiska);
        if (s != null) {
            s.setKategorie(kategorie);
            System.out.println("Zmieniono kategorie dla stanowiska " + numerStanowiska + " na: " + kategorie);
        } else {
            System.out.println("Nie znaleziono stanowiska o numerze: " + numerStanowiska);
        }
    }

    public void removeBilet(Bilet ticket) {
        bilety.remove(ticket);
    }

    public String getPriority(String category) {
        return piorytety.getOrDefault(category, "Brak");
    }

    private int pobierzWagePriorytetu(String kategoria) {
        String p = getPriority(kategoria);
        if ("Wysoki".equals(p)) return 3;
        if ("Normalny".equals(p)) return 2;
        return 1;
    }

    public Bilet pobierzBilet(Stanowisko stanowisko) {
        List<Bilet> dostepneBilety = new ArrayList<>();

        for (Bilet b : bilety) {
            if (!"KOLEJNY".equals(b.getState()) && stanowisko.obslugujeKategorie(b.getCategory())) {
                dostepneBilety.add(b);
            }
        }

        if (dostepneBilety.isEmpty()) return null;

        int sumaPriorytetow = 0;
        for (Bilet b : dostepneBilety) {
            sumaPriorytetow += pobierzWagePriorytetu(b.getCategory());
        }

        if (sumaPriorytetow == 0) return dostepneBilety.get(0);
        int wylosowana = new java.util.Random().nextInt(sumaPriorytetow);
        int obecnaSuma = 0;
        for (Bilet b : dostepneBilety) {
            obecnaSuma += pobierzWagePriorytetu(b.getCategory());
            if (obecnaSuma > wylosowana) {
                bilety.remove(b);
                return b;
            }
        }

        return null;
    }

}