package com.studienarbeit.YourFitnessDiary.GetterSetter;

public class GetterSetterTaetigkeiten {
    private int mainTaetID;
    private String taetBeschreibung;
    private String laenge;
    private String anzahlKcal;
    private String startzeit;
    private int newMainTaetID;

    public int getMainTaetID() {
        return mainTaetID;
    }

    public void setMainTaetID(int mainTaetID) {
        this.mainTaetID = mainTaetID;
    }

    public String getTaetBeschreibung() {
        return taetBeschreibung;
    }

    public void setTaetBeschreibung(String taetBeschreibung) {
        this.taetBeschreibung = taetBeschreibung;
    }

    public String getLaenge() {
        return laenge;
    }

    public void setLaenge(String laenge) {
        this.laenge = laenge;
    }

    public String getAnzahlKcal() {
        return anzahlKcal;
    }

    public void setAnzahlKcal(String anzahlKcal) {
        this.anzahlKcal = anzahlKcal;
    }

    public String getStartzeit() {
        return startzeit;
    }

    public void setStartzeit(String startzeit) {
        this.startzeit = startzeit;
    }


    public int getNewMainTaetID() {
        return newMainTaetID;
    }

    public void setNewMainTaetID(int newMainTaetID) {
        this.newMainTaetID = newMainTaetID;
    }

    public GetterSetterTaetigkeiten(int mainTaetID, String taetBeschreibung, String laenge,
                                    String anzahlKcal, String startzeit, int newMainTaetID) {
        this.mainTaetID = mainTaetID;
        this.taetBeschreibung = taetBeschreibung;
        this.anzahlKcal = anzahlKcal;
        this.laenge = laenge;
        this.startzeit = startzeit;
        this.newMainTaetID = newMainTaetID;
    }


}
