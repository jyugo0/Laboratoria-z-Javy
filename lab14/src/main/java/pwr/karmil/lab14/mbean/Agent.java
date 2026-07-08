package pwr.karmil.lab14.mbean;

import pwr.karmil.lab14.Kolejka;
import javax.management.AttributeChangeNotification;
import javax.management.Notification;
import javax.management.NotificationBroadcasterSupport;

public class Agent extends NotificationBroadcasterSupport implements AgentMBean {
    private long sequenceNumber = 1;

    @Override
    public String[] getKategorie() {
        return Kolejka.getInstance().getWszystkieKategorie();
    }

    @Override
    public void setKategorie(String[] kategorie) {
        Kolejka.getInstance().setWszystkieKategorie(kategorie);
    }

    @Override
    public void zmienPriorytet(String kategoria, int nowyPriorytet) {
        Kolejka.getInstance().ustawPriorytet(kategoria, nowyPriorytet);
    }

    @Override
    public void przypiszKategorieDoStanowiska(int numStanowiska, String kategorie) {
        Kolejka.getInstance().zmienKategorieStanowiska(numStanowiska, kategorie);
    }

    public void wyslijNotyfikacjeOZmianie(String nazwaZmiany, String stara, String nowa) {
        Notification n = new AttributeChangeNotification(
                this,
                sequenceNumber++,
                System.currentTimeMillis(),
                "Zmieniono parametr: " + nazwaZmiany,
                nazwaZmiany,
                "String",
                stara,
                nowa
        );
        sendNotification(n);
    }
}