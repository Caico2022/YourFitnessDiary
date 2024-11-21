package com.studienarbeit.YourFitnessDiary.GetterSetter;

public class GetterSetterNahrungsmittel {

    private int _id_;
    private String marke;
    private String beschreibung;
    private String portionsgroesse;
    private String portionen;
    private String kcal;
    private String carbs;
    private String fette;
    private String eiweiss;
    private String einheit;
    private String portionsmenge;
    private int mainID;
    private String newKcal;
    private String newCarbs;
    private String newFette;
    private String newEiweiss;
    private String barcode;
    private String servingSize;
    private String quantitySize;
    private String kcal100g;
    private String carbs100g;
    private String fette100g;
    private String eiweiss100g;
    private String ausgewSize;


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

    public String getPortionsgroesse() {
        return portionsgroesse;
    }

    public void setPortionsgroesse(String portionsgroesse) {
        this.portionsgroesse = portionsgroesse;
    }

    public String getPortionen() {
        return portionen;
    }

    public void setPortionen(String portionen) {
        this.portionen = portionen;
    }

    public String getKcal() {
        return kcal;
    }

    public void setKcal(String kcal) {
        this.kcal = kcal;
    }

    public String getcarbs() {
        return carbs;
    }

    public void setcarbs(String carbs) {
        this.carbs = carbs;
    }

    public String getfette() {
        return fette;
    }

    public void setfette(String fette) {
        this.fette = fette;
    }

    public String geteiweiss() {
        return eiweiss;
    }

    public void seteiweiss(String eiweiss) {
        this.eiweiss = eiweiss;
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


    public int getMainID() {
        return mainID;
    }

    public void setMainID(int mainID) {
        this.mainID = mainID;
    }

    public String getNewKcal() {
        return newKcal;
    }

    public void setNewKcal(String newKcal) {
        this.newKcal = newKcal;
    }

    public String getNewCarbs() {
        return newCarbs;
    }

    public void setNewCarbs(String newCarbs) {
        this.newCarbs = newCarbs;
    }

    public String getNewFette() {
        return newFette;
    }

    public void setNewFette(String newFette) {
        this.newFette = newFette;
    }

    public String getNewEiweiss() {
        return newEiweiss;
    }

    public void setNewEiweiss(String newEiweiss) {
        this.newEiweiss = newEiweiss;
    }

    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    public String getservingSize() {
        return servingSize;
    }

    public void setservingSize(String servingSize) {
        this.servingSize = servingSize;
    }

    public String getquantitySize() {
        return quantitySize;
    }

    public void setquantitySize(String quantitySize) {
        this.quantitySize = quantitySize;
    }

    public String getKcal100g() {
        return kcal100g;
    }

    public void setKcal100g(String kcal100g) {
        this.kcal100g = kcal100g;
    }

    public String getCarbs100g() {
        return carbs100g;
    }

    public void setCarbs100g(String carbs100g) {
        this.carbs100g = carbs100g;
    }

    public String getFette100g() {
        return fette100g;
    }

    public void setFette100g(String fette100g) {
        this.fette100g = fette100g;
    }

    public String getEiweiss100g() {
        return eiweiss100g;
    }

    public void setEiweiss100g(String eiweiss100g) {
        this.eiweiss100g = eiweiss100g;
    }

    public String getAusgewSize() {
        return ausgewSize;
    }

    public void setAusgewSize(String ausgewSize) {
        this.ausgewSize = ausgewSize;
    }

    public GetterSetterNahrungsmittel(int _id_, String marke, String beschreibung, String portionsgroesse,
                                      String portionen, String kcal, String carbs, String fette,
                                      String eiweiss, String einheit, String portionsmenge, int mainID,
                                      String newKcal, String newCarbs, String newFette, String newEiweiss,
                                      String barcode, String servingSize, String quantitySize,
                                      String kcal100g, String carbs100g, String fette100g, String eiweiss100g,
                                      String ausgewSize) {
        this._id_ = _id_;
        this.marke = marke;
        this.beschreibung = beschreibung;
        this.portionsgroesse = portionsgroesse;
        this.portionen = portionen;
        this.kcal = kcal;
        this.carbs = carbs;
        this.fette = fette;
        this.eiweiss = eiweiss;
        this.einheit = einheit;
        this.portionsmenge = portionsmenge;
        this.mainID = mainID;
        this.newKcal = newKcal;
        this.newCarbs = newCarbs;
        this.newFette = newFette;
        this.newEiweiss = newEiweiss;
        this.barcode = barcode;
        this.servingSize = servingSize;
        this.quantitySize = quantitySize;
        this.kcal100g = kcal100g;
        this.carbs100g = carbs100g;
        this.fette100g = fette100g;
        this.eiweiss100g = eiweiss100g;
        this.ausgewSize = ausgewSize;

    }


    // Für Nahrungsliste
    public GetterSetterNahrungsmittel(int _id_, String marke, String beschreibung, String portionsgroesse,
                                      String portionen, String kcal, String carbs, String fette,
                                      String eiweiss, String einheit, String portionsmenge,
                                      String barcode, String servingSize, String quantitySize,
                                      String kcal100g, String carbs100g, String fette100g, String eiweiss100g,
                                      String ausgewSize)
    {
        this._id_ = _id_;
        this.marke = marke;
        this.beschreibung = beschreibung;
        this.portionsgroesse = portionsgroesse;
        this.portionen = portionen;
        this.kcal = kcal;
        this.carbs = carbs;
        this.fette = fette;
        this.eiweiss = eiweiss;
        this.einheit = einheit;
        this.portionsmenge = portionsmenge;
        this.barcode = barcode;
        this.servingSize = servingSize;
        this.quantitySize = quantitySize;
        this.kcal100g = kcal100g;
        this.carbs100g = carbs100g;
        this.fette100g = fette100g;
        this.eiweiss100g = eiweiss100g;
        this.ausgewSize = ausgewSize;

    }
}


