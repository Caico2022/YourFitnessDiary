package com.studienarbeit.YourFitnessDiary;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;

public class DBHelper extends SQLiteOpenHelper {

    private Context context;
    public static final String DATABASE_NAME = "Nahrungsliste.db";  // Name der Datenbank
    public static final int DATABASE_VERSION = 20;  // Bei Hinzufügen neuer Zeilen inkrementieren
    public static final String TABLE_NAME = "Nahrungsliste";
    public static final String COLUMN_ID = "ID";
    public static final String COLUMN_Markenname = "nahrung_marke";
    public static final String COLUMN_Beschreibung = "nahrung_beschreibung";  // Name der Spalten
    public static final String COLUMN_Portionsgroesse = "nahrung_portionsgroesse";
    public static final String COLUMN_Portionen = "nahrung_portionen";
    public static final String COLUMN_Kalorien = "nahrung_kalorien";
    public static final String COLUMN_CarbsGes = "nahrung_carbsGes";
    public static final String COLUMN_FetteGes = "nahrung_fetteGes";
    public static final String COLUMN_ProteineGes = "nahrung_ProteineGes";
    public static final String COLUMN_Einheit = "nahrung_Einheit";
    public static final String COLUMN_Portionsmenge = "nahrung_Portionsmenge";

    public static final String TABLE_NAME_main = "NahrungslisteMain";
    public static final String COLUMN_ID_main = "IDMain";
    public static final String COLUMN_Markenname_main = "MarkeMain";
    public static final String COLUMN_Beschreibung_main = "BeschreibungMain";
    public static final String COLUMN_Portionsgroesse_main = "PortionsgrösseMain";
    public static final String COLUMN_Portionen_main = "PortionenMain";
    public static final String COLUMN_Kalorien_main = "KalorienMain";
    public static final String COLUMN_CarbsGes_main = "CarbsMain";
    public static final String COLUMN_FetteGes_main = "FetteMain";
    public static final String COLUMN_ProteineGes_main = "EiweissMain";
    public static final String COLUMN_Einheit_main = "EinheitMain";
    public static final String COLUMN_Portionsmenge_main = "PortionsmengeMain";
    public static final String COLUMN_newID_main = "newIDMain";
    public static final String COLUMN_newKalorien_main = "newKalorien";
    public static final String COLUMN_newCarbs_main = "newCarbs";
    public static final String COLUMN_newFette_main = "newFette";
    public static final String COLUMN_newProteine_main = "newProteine";

    public static final String TABLE_NAME_mainTaet = "TaetigkeitenMain";
    public static final String COLUMN_IDMainTaet = "IDMainTaet";
    public static final String COLUMN_BeschreibungMainTaet = "BeschreibungMainTaet";
    public static final String COLUMN_LaengeMainTaet = "LängeMainTaet";
    public static final String COLUMN_VerbrKcalMainTaet = "VerbrKcalMainTaet";
    public static final String COLUMN_StartzeitMainTaet = "StartzeitMainTaet";
    public static final String COLUMN_newIDMainTaet = "newIDMainTaet";


    public DBHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE " + TABLE_NAME +  // Spaltennamen
                        " (" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        COLUMN_Markenname + " TEXT, " +
                        COLUMN_Beschreibung + " TEXT, " +
                        COLUMN_Portionsgroesse + " TEXT, " +
                        COLUMN_Portionen + " TEXT, " +
                        COLUMN_Kalorien + " TEXT, " +
                        COLUMN_CarbsGes + " TEXT, " +
                        COLUMN_FetteGes + " TEXT, " +
                        COLUMN_ProteineGes + " TEXT, " +
                        COLUMN_Einheit + " TEXT, " +
                        COLUMN_Portionsmenge + " TEXT);";
        String query_main = "CREATE TABLE " + TABLE_NAME_main +
                " (" + COLUMN_ID_main + " TEXT, " +
                COLUMN_Markenname_main + " TEXT, " +
                COLUMN_Beschreibung_main + " TEXT, " +
                COLUMN_Portionsgroesse_main + " TEXT, " +
                COLUMN_Portionen_main + " TEXT, " +
                COLUMN_Kalorien_main + " TEXT, " +
                COLUMN_CarbsGes_main + " TEXT, " +
                COLUMN_FetteGes_main + " TEXT, " +
                COLUMN_ProteineGes_main + " TEXT, " +
                COLUMN_Einheit_main + " TEXT, " +
                COLUMN_Portionsmenge_main + " TEXT, " +
                COLUMN_newID_main + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_newKalorien_main + " TEXT, " +
                COLUMN_newCarbs_main + " TEXT, " +
                COLUMN_newFette_main + " TEXT, " +
                COLUMN_newProteine_main + " TEXT);";
        String query_mainTaet = "CREATE TABLE " + TABLE_NAME_mainTaet +
                " (" + COLUMN_IDMainTaet + " TEXT, " +
                COLUMN_BeschreibungMainTaet + " TEXT, " +
                COLUMN_LaengeMainTaet + " TEXT, " +
                COLUMN_VerbrKcalMainTaet + " TEXT, " +
                COLUMN_StartzeitMainTaet + " TEXT, " +
                COLUMN_newIDMainTaet + " INTEGER PRIMARY KEY AUTOINCREMENT);";

        db.execSQL(query);  // Abfrage der Spaltennamen ausführen
        db.execSQL(query_main);
        db.execSQL(query_mainTaet);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {  // Wird ausgeführt beim erstmaligen Starten
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME_main);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME_mainTaet);
        onCreate(db);
    }

    // Alle Daten werden in cv gespeichert und dann in db gespeichert
    public void addNahrung(String Marke, String Beschreibung, String Portionsgroesse, String Portionen,  // Übergebene Werte werden in die Datenbank gespeichert
                           String Kalorien, String CarbsGes, String FetteGes, String EiweissGes,
                           String Einheit, String Portionsmenge) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();  // Speichert alle Daten in cv
        // Keine COLUMN_ID, da es automatisch inkrementiert (primary key)
        cv.put(COLUMN_Markenname, Marke);  // in Datenbank einpflegen
        cv.put(COLUMN_Beschreibung, Beschreibung);
        cv.put(COLUMN_Portionsgroesse, Portionsgroesse);
        cv.put(COLUMN_Portionen, Portionen);
        cv.put(COLUMN_Kalorien, Kalorien);
        cv.put(COLUMN_CarbsGes, CarbsGes);
        cv.put(COLUMN_FetteGes, FetteGes);
        cv.put(COLUMN_ProteineGes, EiweissGes);
        cv.put(COLUMN_Einheit, Einheit);
        cv.put(COLUMN_Portionsmenge, Portionsmenge);
        long result = db.insert(TABLE_NAME, null, cv);  // Daten von cv in db einfügen
        if (result == -1) {  // Bei -1 heißt das, dass die Daten nicht eingefügt worden konnten (weil nichts eingegeben wurde)
            Toast.makeText(context, "Hinzufügen der Daten gescheitert", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "Hinzufügen des Nahrungsmittels erfolgreich", Toast.LENGTH_SHORT).show();
        }
    }

    public void addNahrung_main(String id, String Marke, String Beschreibung, String Portionsgroesse,
                                String Portionen, String Kalorien, String CarbsGes, String FetteGes,
                                String EiweissGes, String Einheit, String Portionsmenge,
                                String newKcal, String newCarbs, String newFette, String newEiweiss) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();  // Speichert alle Daten in cv
        cv.put(COLUMN_ID_main, id);
        cv.put(COLUMN_Markenname_main, Marke);  // in Datenbank einpflegen
        cv.put(COLUMN_Beschreibung_main, Beschreibung);
        cv.put(COLUMN_Portionsgroesse_main, Portionsgroesse);
        cv.put(COLUMN_Portionen_main, Portionen);
        cv.put(COLUMN_Kalorien_main, Kalorien);
        cv.put(COLUMN_CarbsGes_main, CarbsGes);
        cv.put(COLUMN_FetteGes_main, FetteGes);
        cv.put(COLUMN_ProteineGes_main, EiweissGes);
        cv.put(COLUMN_Einheit_main, Einheit);
        cv.put(COLUMN_Portionsmenge_main, Portionsmenge);
        cv.put(COLUMN_newKalorien_main, newKcal);
        cv.put(COLUMN_newCarbs_main, newCarbs);
        cv.put(COLUMN_newFette_main, newFette);
        cv.put(COLUMN_newProteine_main, newEiweiss);
        // Keine COLUMN_mainID, da es automatisch inkrementiert (primary key)
        long result = db.insert(TABLE_NAME_main, null, cv);  // Daten von cv in db einfügen
        if (result == -1) {  // Bei -1 heißt das, dass die Daten nicht eingefügt worden konnten (weil nichts eingegeben wurde)
            Toast.makeText(context, "Hinzufügen der Daten gescheitert", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "Hinzufügen des Nahrungsmittels erfolgreich", Toast.LENGTH_SHORT).show();
        }
    }

    public void addNahrung_mainTaet(String id, String Beschreibung, String Laenge, String VerbrKcal,
                                    String Startzeit) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();  // Speichert alle Daten in cv
        cv.put(COLUMN_IDMainTaet, id);
        cv.put(COLUMN_BeschreibungMainTaet, Beschreibung);
        cv.put(COLUMN_LaengeMainTaet, Laenge);
        cv.put(COLUMN_VerbrKcalMainTaet, VerbrKcal);
        cv.put(COLUMN_StartzeitMainTaet, Startzeit);
        // Keine COLUMN_mainID, da es automatisch inkrementiert (primary key)
        long result = db.insert(TABLE_NAME_mainTaet, null, cv);  // Daten von cv in db einfügen
        if (result == -1) {  // Bei -1 heißt das, dass die Daten nicht eingefügt worden konnten (weil nichts eingegeben wurde)
            Toast.makeText(context, "Hinzufügen der Daten gescheitert", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "Hinzufügen der Tätigkeit erfolgreich", Toast.LENGTH_SHORT).show();
        }
    }

    // Liest alle Daten von der Datenbank
    public Cursor readAllData() {
        String query = "SELECT * FROM " + TABLE_NAME;  // speichert alle Daten der Datenbank in query
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = null;
        if (db != null) { // Wenn Daten in der Datenbank sind
            cursor = db.rawQuery(query, null);  // Liest die Daten von query ab und speichert sie in cursor
        }
        return cursor;
    }

    public Cursor readAllData_main() {
        String query_main = "SELECT * FROM " + TABLE_NAME_main;
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor_main = null;
        if (db != null) {  // Wenn Daten in der Datenbank sind
            cursor_main = db.rawQuery(query_main, null);
        }
        return cursor_main;
    }

    public Cursor readAllData_mainTaet() {
        String query = "SELECT * FROM " + TABLE_NAME_mainTaet;
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = null;
        if (db != null) {  // Wenn Daten in der Datenbank sind
            cursor = db.rawQuery(query, null);
        }
        return cursor;
    }

    // Gibt cursor für die Werte der Nährwertespalten zurück (als Array ist umständlich, da zweidimensional wäre)
    public Cursor readNaehrwerte_main() {
        String query_naehrwerte_main = "SELECT " + COLUMN_newKalorien_main + ", " +
                COLUMN_newCarbs_main +  ", " + COLUMN_newFette_main + ", " +
                COLUMN_newProteine_main + ", " + COLUMN_Portionen_main +
                " FROM " + TABLE_NAME_main;
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor_naehrwerte_main = null;
        if (db != null) {  // Wenn Daten in der Datenbank sind
            cursor_naehrwerte_main = db.rawQuery(query_naehrwerte_main, null);
            cursor_naehrwerte_main.moveToFirst();
        }
        return cursor_naehrwerte_main;
    }

    // Gibt Werte einer Zeile von bestimmten Spalten zurück
    public String[] readNaehrwerteOneRow_main(String row_id) {
        SQLiteDatabase database = getReadableDatabase();
        Cursor cursor = database.query(TABLE_NAME_main, new String[] {COLUMN_Kalorien_main, COLUMN_CarbsGes_main, COLUMN_FetteGes_main, COLUMN_ProteineGes_main},
                COLUMN_newID_main + "=?", new String[]{String.valueOf(row_id)}, null, null, null);
        if (cursor.moveToFirst()) {
            int columnCount = cursor.getColumnCount();
            String[] rowData = new String[columnCount];
            for (int i = 0; i < columnCount; i++) {
                rowData[i] = cursor.getString(i);
            }
            cursor.close();
            return rowData;
        }
        cursor.close();
        return null;
    }


    // Gibt Werte einer Zeile von jeder Spalte zurück
    public String[] findRow(String row_id) {
        SQLiteDatabase database = getReadableDatabase();
        // 1) Table name - 2) Array aller Spalten, die abgerufen werden sollen (null=alle) - 3) Welche zeilen sollen abgerufen werden (Vergleich)
        // 4) ein Array mit der übergebenen row_id, mit der die id der Datenbank verglichen werden soll
        Cursor cursor = database.query(TABLE_NAME_main, null, COLUMN_newID_main + "=?", new String[]{String.valueOf(row_id)}, null, null, null);
        if (cursor.moveToFirst()) {  // Ob es mind. ein Ergebnis in der Datenbank gibt
            int columnCount = cursor.getColumnCount();  // Anzahl der Spalten ermitteln
            String[] rowData = new String[columnCount];  // String rowData erhält Größe der Anzahl der Spalten
            for (int i = 0; i < columnCount; i++) {
                rowData[i] = cursor.getString(i);  // Wert der Spalten in Array rowData speichern
            }
            cursor.close();
            return rowData;
        }
        cursor.close();
        return null;
    }


    public void updateData(String row_id, String marke, String beschreibung, String portionsgroesse, String einheit, String portionen,
                           String kcal, String carbsGes, String fetteGes, String eiweissGes, String portionsmenge) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_Markenname, marke);
        cv.put(COLUMN_Beschreibung, beschreibung);
        cv.put(COLUMN_Portionsgroesse, portionsgroesse);
        cv.put(COLUMN_Einheit, einheit);
        cv.put(COLUMN_Portionen, portionen);
        cv.put(COLUMN_Kalorien, kcal);
        cv.put(COLUMN_CarbsGes, carbsGes);
        cv.put(COLUMN_FetteGes, fetteGes);
        cv.put(COLUMN_ProteineGes, eiweissGes);
        cv.put(COLUMN_Portionsmenge, portionsmenge);
        long result = db.update(TABLE_NAME, cv, COLUMN_ID + "=" + row_id, new String[]{});
        if (result == -1) {
            Toast.makeText(context, "Aktualisierung des Nahrungsmittels gescheitert", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "Aktualisierung des Nahrungsmittels erfolgreich", Toast.LENGTH_LONG).show();
        }
    }

    public void updateData_main(String newIDMain, String portionen, String portionsmenge, String newKcal, String newCarbs, String newFette, String newEiweiss) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_Portionen_main, portionen);
        cv.put(COLUMN_Portionsmenge_main, portionsmenge);
        cv.put(COLUMN_newKalorien_main, newKcal);
        cv.put(COLUMN_newCarbs_main, newCarbs);
        cv.put(COLUMN_newFette_main, newFette);
        cv.put(COLUMN_newProteine_main, newEiweiss);
        long result = db.update(TABLE_NAME_main, cv, COLUMN_newID_main + "=" + newIDMain, new String[]{});
        if (result == -1) {
            Toast.makeText(context, "Aktualisieren des Nahrungsmittels gescheitert", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "Aktualisierung des Nahrungsmittels erfolgreich", Toast.LENGTH_LONG).show();
        }
    }


    public void deleteOneRow(String row_id) {
        SQLiteDatabase db = this.getWritableDatabase();
        long result = db.delete(TABLE_NAME, COLUMN_ID + "=" + row_id, null);  // Wird gelöscht, wenn COLUMN_ID der übergebenen row_id entspricht
        // long result = db.delete(TABLE_NAME, "_id=?", new String[]{row_id});
        if (result == -1) {
            Toast.makeText(context, "Konnte nicht gelöscht werden", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "Erfolgreich gelöscht", Toast.LENGTH_SHORT).show();
        }
    }

    public void deleteOneRow_main(String row_id) {
        SQLiteDatabase db = this.getWritableDatabase();
        long result = db.delete(TABLE_NAME_main, COLUMN_newID_main + "= ?", new String[] {row_id});
        // long result = db.delete(TABLE_NAME, "_id=?", new String[]{row_id});
        if (result == -1) {
            Toast.makeText(context, "Konnte nicht gelöscht werden", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "Erfolgreich gelöscht", Toast.LENGTH_SHORT).show();
        }
    }

    public void deleteOneRow_mainTaet(String row_id) {
        SQLiteDatabase db = this.getWritableDatabase();
        long result = db.delete(TABLE_NAME_mainTaet, COLUMN_newIDMainTaet + "= ?", new String[] {row_id});
        // long result = db.delete(TABLE_NAME, "_id=?", new String[]{row_id});
        if (result == -1) {
            Toast.makeText(context, "Konnte nicht gelöscht werden", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "Erfolgreich gelöscht", Toast.LENGTH_SHORT).show();
        }
    }

    public void deleteAllData() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM " + TABLE_NAME);
    }

    public void deleteAllData_main() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM " + TABLE_NAME_main);
    }
}

