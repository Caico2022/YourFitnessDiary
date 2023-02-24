package com.studienarbeit.YourFitnessDiary.GetterSetter;

public class GetterSetterNahrungsmittel {

    private int _id_;
    private String marke;
    private String beschreibung;
    private double portionsgroesse;
    private double portionen;
    private double kcal;
    private double carbsGes;
    private double fetteGes;
    private double eiweissGes;
    private String einheit;
    private String portionsmenge;
    private int mainID;
    private double newKcal;
    private double newCarbs;
    private double newFette;
    private double newEiweiss;


    public int getId() {
        return _id_;
    }

    public void setId(int _id_) {
        this._id_ = _id_;
    }

    public String getMarke() {
        return marke;
    }

    public void setMarke(String marke) {
        this.marke = marke;
    }

    public String getBeschreibung() {
        return beschreibung;
    }

    public void setBeschreibung(String beschreibung) {
        this.beschreibung = beschreibung;
    }

    public double getPortionsgroesse() {
        return portionsgroesse;
    }

    public void setPortionsgroesse(double portionsgroesse) {
        this.portionsgroesse = portionsgroesse;
    }

    public double getPortionen() {
        return portionen;
    }

    public void setPortionen(double portionen) {
        this.portionen = portionen;
    }

    public double getKcal() {
        return kcal;
    }

    public void setKcal(double kcal) {
        this.kcal = kcal;
    }

    public double getCarbsGes() {
        return carbsGes;
    }

    public void setCarbsGes(double carbsGes) {
        this.carbsGes = carbsGes;
    }

    public double getFetteGes() {
        return fetteGes;
    }

    public void setFetteGes(double fetteGes) {
        this.fetteGes = fetteGes;
    }

    public double getEiweissGes() {
        return eiweissGes;
    }

    public void setEiweissGes(double eiweissGes) {
        this.eiweissGes = eiweissGes;
    }

    public String getEinheit() {
        return einheit;
    }

    public void setEinheit(String einheit) {
        this.einheit = einheit;
    }

    public String getPortionsmenge() {
        return portionsmenge;
    }

    public void setPortionsmenge(String portionsmenge) {
        this.portionsmenge = portionsmenge;
    }



    public GetterSetterNahrungsmittel(int _id_, String marke, String beschreibung, double portionsgroesse,
                                      double portionen, double kcal, double carbsGes, double fetteGes,
                                      double eiweissGes, String einheit, String portionsmenge)
    {
        this._id_ = _id_;
        this.marke = marke;
        this.beschreibung = beschreibung;
        this.portionsgroesse = portionsgroesse;
        this.portionen = portionen;
        this.kcal = kcal;
        this.carbsGes = carbsGes;
        this.fetteGes = fetteGes;
        this.eiweissGes = eiweissGes;
        this.einheit = einheit;
        this.portionsmenge = portionsmenge;
    }

    public int getMainID() {
        return mainID;
    }

    public void setMainID(int mainID) {
        this.mainID = mainID;
    }

    public double getNewKcal() {
        return newKcal;
    }

    public void setNewKcal(double newKcal) {
        this.newKcal = newKcal;
    }

    public double getNewCarbs() {
        return newCarbs;
    }

    public void setNewCarbs(double newCarbs) {
        this.newCarbs = newCarbs;
    }

    public double getNewFette() {
        return newFette;
    }

    public void setNewFette(double newFette) {
        this.newFette = newFette;
    }

    public double getNewEiweiss() {
        return newEiweiss;
    }

    public void setNewEiweiss(double newEiweiss) {
        this.newEiweiss = newEiweiss;
    }

    public GetterSetterNahrungsmittel(int _id_, String marke, String beschreibung, double portionsgroesse,
                                      double portionen, double kcal, double carbsGes, double fetteGes,
                                      double eiweissGes, String einheit, String portionsmenge, int mainID,
                                      double newKcal, double newCarbs, double newFette, double newEiweiss) {
        this._id_ = _id_;
        this.marke = marke;
        this.beschreibung = beschreibung;
        this.portionsgroesse = portionsgroesse;
        this.portionen = portionen;
        this.kcal = kcal;
        this.carbsGes = carbsGes;
        this.fetteGes = fetteGes;
        this.eiweissGes = eiweissGes;
        this.einheit = einheit;
        this.portionsmenge = portionsmenge;
        this.mainID = mainID;
        this.newKcal = newKcal;
        this.newCarbs = newCarbs;
        this.newFette = newFette;
        this.newEiweiss = newEiweiss;
    }

}


