package pwr.karmil.lab14.mbean;

public interface AgentMBean {

    String[] getKategorie();
    void setKategorie(String[] kategorie);

    void zmienPriorytet(String kategoria, int nowyPriorytet);
    void przypiszKategorieDoStanowiska(int numerStanowiska, String kategorieZPrzecinkiem);
}