package com.studienarbeit.YourFitnessDiary;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;

public class DBHelper extends SQLiteOpenHelper {
    private static final String TAG = "creation";

    private Context context;
    public static final String DATABASE_NAME = "YourFitnessDiary.db";  // Name der Datenbank
    public static final int DATABASE_VERSION = 99;  // Bei Hinzufügen neuer Zeilen inkrementieren
    public static final String TABLE_Nahrungsliste = "Nahrungsliste";
    public static final String COLUMN_ID = "ID";
    public static final String COLUMN_Markenname = "marke";
    public static final String COLUMN_Beschreibung = "beschreibung";  // Name der Spalten
    public static final String COLUMN_Portionsgroesse = "portionsgroesse";
    public static final String COLUMN_Portionen = "portionen";
    public static final String COLUMN_Kalorien = "kalorien";
    public static final String COLUMN_carbs = "carbs";
    public static final String COLUMN_fette = "fette";
    public static final String COLUMN_eiweiss = "Eiweiss";
    public static final String COLUMN_Einheit = "Einheit";
    public static final String COLUMN_Portionsmenge = "Portionsmenge";
    public static final String COLUMN_Barcode = "Barcode";
    public static final String COLUMN_ServingSize = "ServingSize";
    public static final String COLUMN_QuantitySize = "QuantitySize";
    public static final String COLUMN_kcal100g = "kcal100g";
    public static final String COLUMN_carbs100g = "carbs100g";
    public static final String COLUMN_fette100g = "fette100g";
    public static final String COLUMN_eiweiss100g = "eiweiss100g";
    public static final String COLUMN_AusgewSize = "AusgewSize";


    public static final String TABLE_Tagesliste = "Tagesliste";
    public static final String COLUMN_ID_main = "IDMain";
    public static final String COLUMN_Markenname_main = "Marke";
    public static final String COLUMN_Beschreibung_main = "Beschreibung";
    public static final String COLUMN_Portionsgroesse_main = "Portionsgrösse";
    public static final String COLUMN_Portionen_main = "Portionen";
    public static final String COLUMN_Kalorien_main = "Kalorien";
    public static final String COLUMN_carbs_main = "Carbs";
    public static final String COLUMN_fette_main = "Fette";
    public static final String COLUMN_eiweiss_main = "Eiweiss";
    public static final String COLUMN_Einheit_main = "Einheit";
    public static final String COLUMN_Portionsmenge_main = "Portionsmenge";
    public static final String COLUMN_newID_main = "newID";
    public static final String COLUMN_newKalorien_main = "newKalorien";
    public static final String COLUMN_newCarbs_main = "newCarbs";
    public static final String COLUMN_newFette_main = "newFette";
    public static final String COLUMN_newEiweiss_main = "newEiweiss";
    public static final String COLUMN_Barcode_main = "Barcode";
    public static final String COLUMN_ServingSize_main = "ServingSize";
    public static final String COLUMN_QuantitySize_main = "QuantitySize";
    public static final String COLUMN_kcal100g_main = "kcal100g";
    public static final String COLUMN_carbs100g_main = "carbs100g";
    public static final String COLUMN_fette100g_main = "fette100g";
    public static final String COLUMN_eiweiss100g_main = "eiweiss100g";
    public static final String COLUMN_AusgewSize_main = "AusgewSize";


    public static final String TABLE_TaetigkeitenTagesliste = "Tätigkeitentagesliste";
    public static final String COLUMN_IDMainTaet = "ID";
    public static final String COLUMN_BeschreibungMainTaet = "Beschreibung";
    public static final String COLUMN_LaengeMainTaet = "Länge";
    public static final String COLUMN_VerbrKcalMainTaet = "VerbrKcal";
    public static final String COLUMN_StartzeitMainTaet = "Startzeit";
    public static final String COLUMN_newIDMainTaet = "newID";  // Autoincrement


    public static final String TABLE_Userdata = "Userdata";
    public static final String COLUMN_IDUd = "ID";
    public static final String COLUMN_Geschlecht = "Geschlecht";
    public static final String COLUMN_Gewicht = "Gewicht";
    public static final String COLUMN_Alter = "Alter_";
    public static final String COLUMN_Groesse = "Groesse";
    public static final String COLUMN_DailySteps = "DailySteps";
    public static final String COLUMN_DailyKcal = "DailyKcal";
    public static final String COLUMN_Sport = "Sport";
    public static final String COLUMN_Ergebnis = "Ergebnis";
    public static final String COLUMN_Ziel = "Ziel";
    public static final String COLUMN_YesterdaySteps = "YesterdaySteps";
    public static final String COLUMN_YesterdayKcal = "YesterdayKcal";
    public static final String COLUMN_WeekSteps = "WeekSteps";
    public static final String COLUMN_WeekKcal = "WeekKcal";
    public static final String COLUMN_MonthSteps = "MonthSteps";
    public static final String COLUMN_MonthKcal = "MonthKcal";
    public static final String COLUMN_YearSteps = "YearSteps";
    public static final String COLUMN_YearKcal = "YearKcal";


    public static final String TABLE_YesterdayData = "YesterdayData";
    public static final String COLUMN_ID_YD = "ID";
    public static final String COLUMN_KonsumKcal = "KonsumKcal";
    public static final String COLUMN_VerbrauchKcal = "VerbrauchKcal";
    public static final String COLUMN_Differenz = "Differenz";
    public static final String COLUMN_Schritte = "Schritte";



    public DBHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE " + TABLE_Nahrungsliste +  // Spaltennamen
                " (" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_Markenname + " TEXT, " +
                COLUMN_Beschreibung + " TEXT, " +
                COLUMN_Portionsgroesse + " TEXT, " +
                COLUMN_Portionen + " TEXT, " +
                COLUMN_Kalorien + " TEXT, " +
                COLUMN_carbs + " TEXT, " +
                COLUMN_fette + " TEXT, " +
                COLUMN_eiweiss + " TEXT, " +
                COLUMN_Einheit + " TEXT, " +
                COLUMN_Portionsmenge + " TEXT, " +
                COLUMN_Barcode + " TEXT, " +
                COLUMN_ServingSize + " TEXT, " +
                COLUMN_QuantitySize + " TEXT, " +
                COLUMN_kcal100g + " TEXT, " +
                COLUMN_carbs100g + " TEXT, " +
                COLUMN_fette100g + " TEXT, " +
                COLUMN_eiweiss100g + " TEXT, " +
                COLUMN_AusgewSize + " TEXT);";
        String queryMain = "CREATE TABLE " + TABLE_Tagesliste +
                " (" + COLUMN_ID_main + " TEXT, " +
                COLUMN_Markenname_main + " TEXT, " +
                COLUMN_Beschreibung_main + " TEXT, " +
                COLUMN_Portionsgroesse_main + " TEXT, " +
                COLUMN_Portionen_main + " TEXT, " +
                COLUMN_Kalorien_main + " TEXT, " +
                COLUMN_carbs_main + " TEXT, " +
                COLUMN_fette_main + " TEXT, " +
                COLUMN_eiweiss_main + " TEXT, " +
                COLUMN_Einheit_main + " TEXT, " +
                COLUMN_Portionsmenge_main + " TEXT, " +
                COLUMN_newID_main + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_newKalorien_main + " TEXT, " +
                COLUMN_newCarbs_main + " TEXT, " +
                COLUMN_newFette_main + " TEXT, " +
                COLUMN_newEiweiss_main + " TEXT, " +
                COLUMN_Barcode_main + " TEXT, " +
                COLUMN_ServingSize_main + " TEXT, " +
                COLUMN_QuantitySize_main + " TEXT, " +
                COLUMN_kcal100g_main + " TEXT, " +
                COLUMN_carbs100g_main + " TEXT, " +
                COLUMN_fette100g_main + " TEXT, " +
                COLUMN_eiweiss100g_main + " TEXT, " +
                COLUMN_AusgewSize_main + " TEXT);";
        String queryMainTaet = "CREATE TABLE " + TABLE_TaetigkeitenTagesliste +
                " (" + COLUMN_IDMainTaet + " TEXT, " +
                COLUMN_BeschreibungMainTaet + " TEXT, " +
                COLUMN_LaengeMainTaet + " TEXT, " +
                COLUMN_VerbrKcalMainTaet + " TEXT, " +
                COLUMN_StartzeitMainTaet + " TEXT, " +
                COLUMN_newIDMainTaet + " INTEGER PRIMARY KEY AUTOINCREMENT);";
        String queryUserdata = "CREATE TABLE " + TABLE_Userdata +  // Spaltennamen
                " (" + COLUMN_IDUd + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_Geschlecht + " TEXT, " +
                COLUMN_Gewicht + " TEXT, " +
                COLUMN_Alter + " TEXT, " +
                COLUMN_Groesse + " TEXT, " +
                COLUMN_DailySteps + " TEXT, " +
                COLUMN_DailyKcal + " TEXT, " +
                COLUMN_Sport + " TEXT, " +
                COLUMN_Ergebnis + " TEXT, " +
                COLUMN_Ziel + " TEXT, " +
                COLUMN_YesterdaySteps + " TEXT, " +
                COLUMN_YesterdayKcal + " TEXT, " +
                COLUMN_WeekSteps + " TEXT, " +
                COLUMN_WeekKcal + " TEXT, " +
                COLUMN_MonthSteps + " TEXT, " +
                COLUMN_MonthKcal + " TEXT, " +
                COLUMN_YearSteps + " TEXT, " +
                COLUMN_YearKcal + " TEXT);";
        String queryYesterdayData = "CREATE TABLE " + TABLE_YesterdayData +  // Spaltennamen
                " (" + COLUMN_ID_YD + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_KonsumKcal + " TEXT, " +
                COLUMN_VerbrauchKcal + " TEXT, " +
                COLUMN_Differenz + " TEXT, " +
                COLUMN_Schritte + " TEXT);";

        db.execSQL(query);  // Abfrage der Spaltennamen ausführen
        db.execSQL(queryMain);
        db.execSQL(queryMainTaet);
        db.execSQL(queryUserdata);
        db.execSQL(queryYesterdayData);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {  // Wird ausgeführt beim erstmaligen Starten
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_Nahrungsliste);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_Tagesliste);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_TaetigkeitenTagesliste);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_Userdata);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_YesterdayData);
        onCreate(db);
    }



    // Alle Daten werden in cv gespeichert und dann in db gespeichert
    public void addNahrung(String Marke, String Beschreibung, String Portionsgroesse, String Portionen,  // Übergebene Werte werden in die Datenbank gespeichert
                           String Kalorien, String carbs, String fette, String eiweiss,
                           String Einheit, String Portionsmenge, String kcal100g, String carbs100g, String fette100g, String eiweiss100g) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        // Keine COLUMN_ID, da es automatisch inkrementiert (primary key)
        cv.put(COLUMN_Markenname, Marke);
        cv.put(COLUMN_Beschreibung, Beschreibung);
        cv.put(COLUMN_Portionsgroesse, Portionsgroesse);
        cv.put(COLUMN_Portionen, Portionen);
        cv.put(COLUMN_Kalorien, Kalorien);
        cv.put(COLUMN_carbs, carbs);
        cv.put(COLUMN_fette, fette);
        cv.put(COLUMN_eiweiss, eiweiss);
        cv.put(COLUMN_Einheit, Einheit);
        cv.put(COLUMN_Portionsmenge, Portionsmenge);
        cv.put(COLUMN_kcal100g, kcal100g);
        cv.put(COLUMN_carbs100g, carbs100g);
        cv.put(COLUMN_fette100g, fette100g);
        cv.put(COLUMN_eiweiss100g, eiweiss100g);

        long result = db.insert(TABLE_Nahrungsliste, null, cv);  // Daten von cv in db einfügen
        if (result == -1) {  // Bei -1 heißt das, dass die Daten nicht eingefügt worden konnten (weil nichts eingegeben wurde)
            Toast.makeText(context, "Hinzufügen der Daten gescheitert", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "Hinzufügen des Nahrungsmittels erfolgreich", Toast.LENGTH_SHORT).show();
        }
    }

    public void addNahrungOff(String marke, String beschreibung, String portionsgroesse, String portionen,  // Übergebene Werte werden in die Datenbank gespeichert
                              String kcal, String carbs, String fette, String eiweiss,
                              String einheit, String portionsmenge,
                              String barcode, String servingSize, String quantitySize,
                              String kcal100g, String carbs100g, String fette100g, String eiweiss100g,
                              String ausgewSize) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();  // Speichert alle Daten in cv
        // Keine COLUMN_ID, da es automatisch inkrementiert (primary key)
        cv.put(COLUMN_Markenname, marke);  // in Datenbank einpflegen
        cv.put(COLUMN_Beschreibung, beschreibung);
        cv.put(COLUMN_Portionsgroesse, portionsgroesse);
        cv.put(COLUMN_Portionen, portionen);
        cv.put(COLUMN_Kalorien, kcal);
        cv.put(COLUMN_carbs, carbs);
        cv.put(COLUMN_fette, fette);
        cv.put(COLUMN_eiweiss, eiweiss);
        cv.put(COLUMN_Einheit, einheit);
        cv.put(COLUMN_Portionsmenge, portionsmenge);
        cv.put(COLUMN_Barcode, barcode);
        cv.put(COLUMN_ServingSize, servingSize);
        cv.put(COLUMN_QuantitySize, quantitySize);
        cv.put(COLUMN_kcal100g, kcal100g);
        cv.put(COLUMN_carbs100g, carbs100g);
        cv.put(COLUMN_fette100g, fette100g);
        cv.put(COLUMN_eiweiss100g, eiweiss100g);
        cv.put(COLUMN_AusgewSize, ausgewSize);

        long result = db.insert(TABLE_Nahrungsliste, null, cv);  // Daten von cv in db einfügen
        if (result == -1) {  // Bei -1 heißt das, dass die Daten nicht eingefügt worden konnten (weil nichts eingegeben wurde)
            Toast.makeText(context, "Hinzufügen der Daten gescheitert", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "Hinzufügen des Nahrungsmittels erfolgreich", Toast.LENGTH_SHORT).show();
        }
    }

    public void addYesterdayData(String konsumKcal, String verbrauchKcal, String differenz, String schritte) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();  // Speichert alle Daten in cv
        // Keine COLUMN_ID, da es automatisch inkrementiert (primary key)
        cv.put(COLUMN_KonsumKcal, konsumKcal);  // in Datenbank einpflegen
        cv.put(COLUMN_VerbrauchKcal, verbrauchKcal);
        cv.put(COLUMN_Differenz, differenz);
        cv.put(COLUMN_Schritte, schritte);

        long result = db.insert(TABLE_YesterdayData, null, cv);  // Daten von cv in db einfügen
        if (result == -1) {  // Bei -1 heißt das, dass die Daten nicht eingefügt worden konnten (weil nichts eingegeben wurde)
            Log.i("creation", "addYesterdayData erfolgreich");
        } else {
            Log.e("creation", "addYesterdayData gescheitert");
        }
    }

    public void addNahrungMain(String id, String Marke, String Beschreibung, String Portionsgroesse,
                               String Portionen, String Kalorien, String carbs, String fette,
                               String eiweiss, String Einheit, String Portionsmenge,
                               String newKcal, String newCarbs, String newFette, String newEiweiss) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_ID_main, id);
        cv.put(COLUMN_Markenname_main, Marke);
        cv.put(COLUMN_Beschreibung_main, Beschreibung);
        cv.put(COLUMN_Portionsgroesse_main, Portionsgroesse);
        cv.put(COLUMN_Portionen_main, Portionen);
        cv.put(COLUMN_Kalorien_main, Kalorien);
        cv.put(COLUMN_carbs_main, carbs);
        cv.put(COLUMN_fette_main, fette);
        cv.put(COLUMN_eiweiss_main, eiweiss);
        cv.put(COLUMN_Einheit_main, Einheit);
        cv.put(COLUMN_Portionsmenge_main, Portionsmenge);
        cv.put(COLUMN_newKalorien_main, newKcal);
        cv.put(COLUMN_newCarbs_main, newCarbs);
        cv.put(COLUMN_newFette_main, newFette);
        cv.put(COLUMN_newEiweiss_main, newEiweiss);
        // Keine COLUMN_mainID, da es automatisch inkrementiert (primary key)
        long result = db.insert(TABLE_Tagesliste, null, cv);  // Daten von cv in db einfügen
        if (result == -1) {  // Bei -1 heißt das, dass die Daten nicht eingefügt worden konnten (weil nichts eingegeben wurde)
            Toast.makeText(context, "Hinzufügen der Daten gescheitert", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "Hinzufügen des Nahrungsmittels erfolgreich", Toast.LENGTH_SHORT).show();
        }
    }

    public void addNahrungMainOff(String id, String Marke, String Beschreibung, String Portionsgroesse,
                                  String Portionen, String Kalorien, String carbs, String fette,
                                  String eiweiss, String Einheit, String Portionsmenge,
                                  String newKcal, String newCarbs, String newFette, String newEiweiss,
                                  String barcode, String servingSize, String quantitySize, String kcal100g,
                                  String carbs100g, String fette100g, String eiweiss100g,
                                  String ausgewSize) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();  // Speichert alle Daten in cv
        cv.put(COLUMN_ID_main, id);
        cv.put(COLUMN_Markenname_main, Marke);  // in Datenbank einpflegen
        cv.put(COLUMN_Beschreibung_main, Beschreibung);
        cv.put(COLUMN_Portionsgroesse_main, Portionsgroesse);
        cv.put(COLUMN_Portionen_main, Portionen);
        cv.put(COLUMN_Kalorien_main, Kalorien);
        cv.put(COLUMN_carbs_main, carbs);
        cv.put(COLUMN_fette_main, fette);
        cv.put(COLUMN_eiweiss_main, eiweiss);
        cv.put(COLUMN_Einheit_main, Einheit);
        cv.put(COLUMN_Portionsmenge_main, Portionsmenge);
        cv.put(COLUMN_newKalorien_main, newKcal);
        cv.put(COLUMN_newCarbs_main, newCarbs);
        cv.put(COLUMN_newFette_main, newFette);
        cv.put(COLUMN_newEiweiss_main, newEiweiss);
        cv.put(COLUMN_Barcode_main, barcode);
        cv.put(COLUMN_ServingSize_main, servingSize);
        cv.put(COLUMN_QuantitySize_main, quantitySize);
        cv.put(COLUMN_kcal100g_main, kcal100g);
        cv.put(COLUMN_carbs100g_main, carbs100g);
        cv.put(COLUMN_fette100g_main, fette100g);
        cv.put(COLUMN_eiweiss100g_main, eiweiss100g);
        cv.put(COLUMN_AusgewSize_main, ausgewSize);

        // Keine COLUMN_mainID, da es automatisch inkrementiert (primary key)
        long result = db.insert(TABLE_Tagesliste, null, cv);  // Daten von cv in db einfügen
        if (result == -1) {  // Bei -1 heißt das, dass die Daten nicht eingefügt worden konnten (weil nichts eingegeben wurde)
            Toast.makeText(context, "Hinzufügen der Daten gescheitert", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "Hinzufügen des Nahrungsmittels erfolgreich", Toast.LENGTH_SHORT).show();
        }
    }


    public void addNahrungMainTaet(String id, String Beschreibung, String Laenge, String VerbrKcal,
                                   String Startzeit) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();  // Speichert alle Daten in cv
        cv.put(COLUMN_IDMainTaet, id);
        cv.put(COLUMN_BeschreibungMainTaet, Beschreibung);
        cv.put(COLUMN_LaengeMainTaet, Laenge);
        cv.put(COLUMN_VerbrKcalMainTaet, VerbrKcal);
        cv.put(COLUMN_StartzeitMainTaet, Startzeit);
        // Keine COLUMN_mainID, da es automatisch inkrementiert (primary key)
        long result = db.insert(TABLE_TaetigkeitenTagesliste, null, cv);  // Daten von cv in db einfügen
        if (result == -1) {  // Bei -1 heißt das, dass die Daten nicht eingefügt worden konnten (weil nichts eingegeben wurde)
            Toast.makeText(context, "Hinzufügen der Daten gescheitert", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "Hinzufügen der Tätigkeit erfolgreich", Toast.LENGTH_SHORT).show();
        }
    }


    // Gibt cursor für die Werte der Nährwertespalten zurück (als Array ist umständlich, da zweidimensional wäre)
    public Cursor readNaehrwerteMain() {
        String query_naehrwerte_main = "SELECT " + COLUMN_newKalorien_main + ", " +
                COLUMN_newCarbs_main +  ", " + COLUMN_newFette_main + ", " +
                COLUMN_newEiweiss_main + ", " + COLUMN_Portionen_main +
                " FROM " + TABLE_Tagesliste;
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = null;
        if (db != null) {  // Wenn Daten in der Datenbank sind
            cursor = db.rawQuery(query_naehrwerte_main, null);
            cursor.moveToFirst();
        }
        return cursor;
    }


    public String[] getAllVerbrKcalMainTaet() {
        SQLiteDatabase database = getReadableDatabase();
        String[] projection = {COLUMN_VerbrKcalMainTaet};
        Cursor cursor = database.query(TABLE_TaetigkeitenTagesliste, projection, null, null, null, null, null);
        String[] values = new String[cursor.getCount()];
        int i = 0;
        while (cursor.moveToNext()) {
            String value = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_VerbrKcalMainTaet));
            values[i] = value;
            i++;
        }
        cursor.close();
        return values;
    }


    public Cursor readUserdataBody(String rowID) {
        String query = "SELECT " + COLUMN_IDUd + ", " + COLUMN_Geschlecht + ", " + COLUMN_Gewicht + ", " + COLUMN_Alter + ", " + COLUMN_Groesse +
                ", " + COLUMN_DailySteps + ", " + COLUMN_DailyKcal + ", " + COLUMN_Sport +  " FROM " + TABLE_Userdata + " WHERE " + COLUMN_IDUd + " = ?";
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = null;
        if (db != null) {
            cursor = db.rawQuery(query, new String[] {rowID});
        }
        return cursor;
    }


    // Höchste ID von TABLE_NAME zurückgeben
    public String getMaxIdUserdata() {
        SQLiteDatabase database = getReadableDatabase();
        String query = "SELECT MAX(" + COLUMN_IDUd + ") FROM " + TABLE_Userdata;
        Cursor cursor = database.rawQuery(query, null);
        String maxId = null;
        if (cursor.moveToFirst()) {
            maxId = String.valueOf(cursor.getInt(0));
        }
        cursor.close();
        return maxId;
    }

    public void addUserdata(String geschlecht, String gewicht, String alter, String groesse, String dailySteps,
                            String dailyKcal, String sport, String ergebnis, String ziel, String yesterdaySteps, String yesterdayKcal, String weekSteps,
                            String weekKcal, String monthSteps, String monthKcal,
                            String yearSteps, String yearKcal) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();  // Speichert alle Daten in cv
        // Keine COLUMN_ID, da es automatisch inkrementiert (primary key)
        cv.put(COLUMN_Geschlecht, geschlecht);
        cv.put(COLUMN_Gewicht, gewicht);
        cv.put(COLUMN_Alter, alter);
        cv.put(COLUMN_Groesse, groesse);
        cv.put(COLUMN_DailySteps, dailySteps);
        cv.put(COLUMN_DailyKcal, dailyKcal);
        cv.put(COLUMN_Sport, sport);
        cv.put(COLUMN_Ergebnis, ergebnis);
        cv.put(COLUMN_Ziel, ziel);
        cv.put(COLUMN_YesterdaySteps, yesterdaySteps);
        cv.put(COLUMN_YesterdayKcal, yesterdayKcal);
        cv.put(COLUMN_WeekSteps, weekSteps);
        cv.put(COLUMN_WeekKcal, weekKcal);
        cv.put(COLUMN_MonthSteps, monthSteps);
        cv.put(COLUMN_MonthKcal, monthKcal);
        cv.put(COLUMN_YearSteps, yearSteps);
        cv.put(COLUMN_YearKcal, yearKcal);

        long result = db.insert(TABLE_Userdata, null, cv);  // Daten von cv in db einfügen
        if (result == -1) {  // Bei -1 heißt das, dass die Daten nicht eingefügt worden konnten (weil nichts eingegeben wurde)
            Log.i("creation", "addUserdata fehlgeschlagen");
        } else {
            Log.e("creation", "addUserdata fehlgeschlagen");
        }
    }

    public void updateUserdata(String rowID, String geschlecht, String gewicht, String alter, String groesse, String dailySteps,
                               String dailyKcal, String sport, String ergebnis) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_Geschlecht, geschlecht);
        cv.put(COLUMN_Gewicht, gewicht);
        cv.put(COLUMN_Alter, alter);
        cv.put(COLUMN_Groesse, groesse);
        cv.put(COLUMN_DailySteps, dailySteps);
        cv.put(COLUMN_DailyKcal, dailyKcal);
        cv.put(COLUMN_Sport, sport);
        cv.put(COLUMN_Ergebnis, ergebnis);

        long result = db.update(TABLE_Userdata, cv, COLUMN_IDUd + "=" + rowID, new String[]{});
        if (result == -1) {
            Log.i("creation", "updateUserdata erfolgreich");
        } else {
            Log.e("creation", "updateUserdata gescheitert");
        }
    }

    // Updatet nur Ziel von Userdata
    public void updateZielUserdata(String rowID, String ziel) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_Ziel, ziel);
        int result = db.update(TABLE_Userdata, cv, COLUMN_IDUd + " = ?", new String[]{String.valueOf(rowID)});
        if (result == 1) {
            Log.i("update", "Ziel erfolgreich aktualisiert");
        } else {
            Log.e("update", "Fehler beim Aktualisieren des Ziels");
        }
    }


    public void updateData(String row_id, String marke, String beschreibung, String portionsgroesse, String portionen,
                           String kcal, String carbs, String fette, String eiweiss, String einheit, String portionsmenge,
                           String kcal100g, String carbs100g, String fette100g, String eiweiss100g) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_Markenname, marke);
        cv.put(COLUMN_Beschreibung, beschreibung);
        cv.put(COLUMN_Portionsgroesse, portionsgroesse);
        cv.put(COLUMN_Portionen, portionen);
        cv.put(COLUMN_Kalorien, kcal);
        cv.put(COLUMN_carbs, carbs);
        cv.put(COLUMN_fette, fette);
        cv.put(COLUMN_eiweiss, eiweiss);
        cv.put(COLUMN_Einheit, einheit);
        cv.put(COLUMN_Portionsmenge, portionsmenge);
        cv.put(COLUMN_kcal100g, kcal100g);
        cv.put(COLUMN_carbs100g, carbs100g);
        cv.put(COLUMN_fette100g, fette100g);
        cv.put(COLUMN_eiweiss100g, eiweiss100g);
        long result = db.update(TABLE_Nahrungsliste, cv, COLUMN_ID + "=" + row_id, new String[]{});
        if (result == -1) {
            Toast.makeText(context, "Aktualisierung des Nahrungsmittels gescheitert", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "Aktualisiert", Toast.LENGTH_LONG).show();
        }
    }

    public void updateDailyValues(String id, String dailySteps, String dailyKcal) {
        SQLiteDatabase database = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_DailySteps, dailySteps);
        values.put(COLUMN_DailyKcal, dailyKcal);
        String selection = COLUMN_IDUd + " = ?";
        String[] selectionArgs = { String.valueOf(id) };
        database.update(TABLE_Userdata, values, selection, selectionArgs);
    }

    public String[] getDailyStepsAndKcal(String id) {
        SQLiteDatabase database = getReadableDatabase();
        String[] projection = {COLUMN_DailySteps, COLUMN_DailyKcal};
        String selection = COLUMN_IDUd + " = ?";
        String[] selectionArgs = { String.valueOf(id) };
        Cursor cursor = database.query(TABLE_Userdata, projection, selection, selectionArgs, null, null, null);
        String[] values = new String[2];
        if (cursor.moveToFirst()) {
            values[0] = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_DailySteps));
            values[1] = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_DailyKcal));
        }
        cursor.close();
        return values;
    }

    public Cursor readAllYesterdayData() {
        String query = "SELECT * FROM " + TABLE_YesterdayData;
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = null;
        if (db != null) {
            cursor = db.rawQuery(query, null);
        }
        return cursor;
    }

    public Cursor readAllData() {
        String query = "SELECT * FROM " + TABLE_Nahrungsliste;  // speichert alle Daten der Datenbank in query
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = null;
        if (db != null) { // Wenn Daten in der Datenbank sind
            cursor = db.rawQuery(query, null);  // Liest die Daten von query ab und speichert sie in cursor
        }
        return cursor;
    }

    public Cursor readAllDataMain() {
        String query_main = "SELECT * FROM " + TABLE_Tagesliste;
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = null;
        if (db != null) {
            cursor = db.rawQuery(query_main, null);
        }
        return cursor;
    }

    public Cursor readAllDataMainTaet() {
        String query_main = "SELECT * FROM " + TABLE_TaetigkeitenTagesliste;
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = null;
        if (db != null) {
            cursor = db.rawQuery(query_main, null);
        }
        return cursor;
    }

    // Sucht nach übergebenem Barcode und gibt  1 zurück, wenn dieser existiert. Sonst 0
    public int checkIfBarcodeExists(String barcode) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM " + TABLE_Nahrungsliste + " WHERE " + COLUMN_Barcode + " = ?";
        Cursor cursor = db.rawQuery(query, new String[]{barcode});
        if (cursor.moveToFirst()) {
            // Der Barcode wurde gefunden
            cursor.close();
            return 1;
        } else {
            // Der Barcode wurde nicht gefunden
            cursor.close();
            return 0;
        }
    }

    // Sucht mit übergebenem Barcode die ID dazu raus
    @SuppressLint("Range")
    public int getIDFromBarcode(String barcode) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT " + COLUMN_ID + " FROM " + TABLE_Nahrungsliste + " WHERE " + COLUMN_Barcode + " = ?";
        Cursor cursor = db.rawQuery(query, new String[]{barcode});
        int id = -1;
        if (cursor.moveToFirst()) {
            id = cursor.getInt(cursor.getColumnIndex(COLUMN_ID));
        }

        cursor.close();
        return id;
    }


    // Sucht mit übergebener ID das Ziel von Userdata
    @SuppressLint("Range")
    public String getZielByID(String id) {
        SQLiteDatabase db = this.getReadableDatabase();
        String[] projection = {COLUMN_Ziel};
        String selection = COLUMN_ID + " = ?";
        String[] selectionArgs = {String.valueOf(id)};
        Cursor cursor = db.query(
                TABLE_Userdata,
                projection,
                selection,
                selectionArgs,
                null,
                null,
                null
        );
        String ergebnis = null;
        if (cursor.moveToFirst()) {
            ergebnis = cursor.getString(cursor.getColumnIndex(COLUMN_Ziel));
        }
        cursor.close();
        return ergebnis;
    }



    // Höchste ID von TABLE_Nahrungsliste zurückgeben
    public String getMaxIdNahrungsliste() {
        SQLiteDatabase database = getReadableDatabase();
        String query = "SELECT MAX(" + COLUMN_ID + ") FROM " + TABLE_Nahrungsliste;
        Cursor cursor = database.rawQuery(query, null);
        String maxId = null;
        if (cursor.moveToFirst()) {
            maxId = String.valueOf(cursor.getInt(0));
        }
        cursor.close();
        return maxId;
    }

    public String getMaxIdYesterdayData() {
        SQLiteDatabase database = getReadableDatabase();
        String query = "SELECT MAX(" + COLUMN_ID_YD + ") FROM " + TABLE_YesterdayData;
        Cursor cursor = database.rawQuery(query, null);
        String maxId = null;
        if (cursor.moveToFirst()) {
            maxId = String.valueOf(cursor.getInt(0));
        }
        cursor.close();
        return maxId;
    }


    public void updateDataOff(String row_id, String portionsgroesse, String portionen, String kcal,
                              String carbs, String fette, String eiweiss, String einheit, String portionsmenge,
                              String ausgewSize) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_Portionsgroesse, portionsgroesse);
        cv.put(COLUMN_Portionen, portionen);
        cv.put(COLUMN_Kalorien, kcal);
        cv.put(COLUMN_carbs, carbs);
        cv.put(COLUMN_fette, fette);
        cv.put(COLUMN_eiweiss, eiweiss);
        cv.put(COLUMN_Einheit, einheit);
        cv.put(COLUMN_Portionsmenge, portionsmenge);
        cv.put(COLUMN_AusgewSize, ausgewSize);
        long result = db.update(TABLE_Nahrungsliste, cv, COLUMN_ID + "=" + row_id, new String[]{});
        if (result == -1) {
            Toast.makeText(context, "Aktualisierung des Nahrungsmittels gescheitert", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "Aktualisiert", Toast.LENGTH_LONG).show();
        }
    }

    public void updateDataMain(String newIDMain, String portionen, String portionsmenge, String newKcal, String newCarbs, String newFette, String newEiweiss) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_Portionen_main, portionen);
        cv.put(COLUMN_Portionsmenge_main, portionsmenge);
        cv.put(COLUMN_newKalorien_main, newKcal);
        cv.put(COLUMN_newCarbs_main, newCarbs);
        cv.put(COLUMN_newFette_main, newFette);
        cv.put(COLUMN_newEiweiss_main, newEiweiss);
        long result = db.update(TABLE_Tagesliste, cv, COLUMN_newID_main + "=" + newIDMain, new String[]{});
        if (result == -1) {
            Toast.makeText(context, "Aktualisieren des Nahrungsmittels gescheitert", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "Aktualisiert", Toast.LENGTH_LONG).show();
        }
    }

    public void updateDataMainOff(String portionsgroesse, String portionen, String einheit,
                                  String portionsmenge, String newIDMain, String newKcal,
                                  String newCarbs, String newFette, String newEiweiss,
                                  String ausgewSize) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_Portionsgroesse_main, portionsgroesse);
        cv.put(COLUMN_Portionen_main, portionen);
        cv.put(COLUMN_Einheit_main, einheit);
        cv.put(COLUMN_Portionsmenge_main, portionsmenge);
        cv.put(COLUMN_newKalorien_main, newKcal);
        cv.put(COLUMN_newCarbs_main, newCarbs);
        cv.put(COLUMN_newFette_main, newFette);
        cv.put(COLUMN_newEiweiss_main, newEiweiss);
        cv.put(COLUMN_AusgewSize_main, ausgewSize);
        long result = db.update(TABLE_Tagesliste, cv, COLUMN_newID_main + "=" + newIDMain, new String[]{});
        if (result == -1) {
            Toast.makeText(context, "Aktualisieren des Nahrungsmittels gescheitert", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "Aktualisiert", Toast.LENGTH_LONG).show();
        }
    }


    public void deleteOneRow(String row_id) {
        SQLiteDatabase db = this.getWritableDatabase();
        long result = db.delete(TABLE_Nahrungsliste, COLUMN_ID + "=" + row_id, null);  // Wird gelöscht, wenn COLUMN_ID der übergebenen row_id entspricht
        // long result = db.delete(TABLE_NAME, "_id=?", new String[]{row_id});
        if (result == -1) {
            Toast.makeText(context, "Konnte nicht gelöscht werden", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "Gelöscht", Toast.LENGTH_SHORT).show();
        }
    }

    public void deleteOneRow_main(String row_id) {
        SQLiteDatabase db = this.getWritableDatabase();
        long result = db.delete(TABLE_Tagesliste, COLUMN_newID_main + "= ?", new String[] {row_id});
        // long result = db.delete(TABLE_NAME, "_id=?", new String[]{row_id});
        if (result == -1) {
            Toast.makeText(context, "Konnte nicht gelöscht werden", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, " Gelöscht", Toast.LENGTH_SHORT).show();
        }
    }

    public void deleteOneRow_mainTaet(String row_id) {
        SQLiteDatabase db = this.getWritableDatabase();
        long result = db.delete(TABLE_TaetigkeitenTagesliste, COLUMN_newIDMainTaet + "= ?", new String[] {row_id});
        // long result = db.delete(TABLE_NAME, "_id=?", new String[]{row_id});
        if (result == -1) {
            Toast.makeText(context, "Konnte nicht gelöscht werden", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "Gelöscht", Toast.LENGTH_SHORT).show();
        }
    }


    public void deleteAllDataMain() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM " + TABLE_Tagesliste);
    }

    public void deleteAllDataMainTaet() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM " + TABLE_TaetigkeitenTagesliste);

    }
}

