package com.studienarbeit.YourFitnessDiary.Main;

import android.app.Dialog;
import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.studienarbeit.YourFitnessDiary.DBHelper;
import com.studienarbeit.YourFitnessDiary.R;

import java.util.Locale;

public class KcalRechnerPopup extends Dialog implements View.OnClickListener {

    public KcalRechnerPopup(@NonNull Context context) {
        super(context);
    }

    private static final String TAG = "creation";
    private static final String TEST = "Test";

    private Button btn_mainPopupClose, btn_mainPopupSpeichern;
    private EditText gewichtEt, alterEt, groesseEt;
    private TextView schritteEt, kalorienzufuhrEt, ergebnisTv, sportEt;
    private String geschlechtS, gewichtS, alterS, groesseS, schritteS, kalorienzufuhrS, ergebnisS, idUd, sportS;
    private double gewichtD, alterD, groesseD, kalorienzufuhrD, ergebnisD, sportD;
    private double schritteIVerbrauchD;
    private int schritteI;
    DBHelper dbHelper = new DBHelper(getContext());

    String [] dd_items = {"Männlich", "Weiblich"};
    AutoCompleteTextView dd_geschlecht;
    ArrayAdapter<String> adapterItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_kcalrechner_popup);

        btn_mainPopupClose = findViewById(R.id.btn_mainPopupClose);
        sportEt = findViewById(R.id.sportEt);
        btn_mainPopupSpeichern = findViewById(R.id.btn_mainPopupSpeichern);
        gewichtEt = findViewById(R.id.gewichtEt);
        alterEt = findViewById(R.id.alterEt);
        groesseEt = findViewById(R.id.groesseEt);
        schritteEt = findViewById(R.id.schritteEt);
        kalorienzufuhrEt = findViewById(R.id.kalorienzufuhrEt);
        ergebnisTv = findViewById(R.id.ergebnisTv);
        btn_mainPopupClose.setOnClickListener(this);
        btn_mainPopupSpeichern.setOnClickListener(this);
        Log.i(TAG, "MainCustomPopup gestartet");

        dd_geschlecht = findViewById(R.id.dd_geschlecht);
        adapterItems = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_dropdown_item, dd_items);
        dd_geschlecht.setAdapter(adapterItems);

        // Breite und Höhe des Popups einstellen
        WindowManager.LayoutParams params = getWindow().getAttributes();
        params.width = WindowManager.LayoutParams.MATCH_PARENT;
        params.height = WindowManager.LayoutParams.MATCH_PARENT;

        getAndSetUserdata();
        kalorienverbrauchsrechner();

        TextWatcherErgebnis();
    }


    private void getAndSetUserdata() {
        String maxID = dbHelper.getMaxIdUserdata();
        Cursor cursor = dbHelper.readUserdataBody(maxID);
        int i = 0;
        if (cursor.moveToFirst()) {
            do {
                idUd = cursor.getString(0); // ID
                geschlechtS = cursor.getString(1); // geschlecht
                gewichtS = cursor.getString(2); // gewicht
                alterS = cursor.getString(3); // alter
                groesseS = cursor.getString(4); // groesse
                schritteS = cursor.getString(5); // schritte
                kalorienzufuhrS = cursor.getString(6); // kalorienzufuhr
                sportS = cursor.getString(7); // sport
                i++;
            } while (cursor.moveToNext());
        }
        cursor.close();
        dd_geschlecht.setText(geschlechtS);
        adapterItems = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_dropdown_item, dd_items);
        dd_geschlecht.setAdapter(adapterItems);
        gewichtEt.setText(gewichtS);
        alterEt.setText(alterS);
        groesseEt.setText(groesseS);

        // dailySteps und dailyKcal von Datenbank abfragen
        String maxID2 = dbHelper.getMaxIdUserdata();
        String[] values = dbHelper.getDailyStepsAndKcal(maxID2);  // Stelle 0 sind Schritte
        schritteEt.setText(String.valueOf(Integer.parseInt(values[0])));
        kalorienzufuhrEt.setText(values[1]);

        // Verbrannte Kcal der Tätigkeiten von Datenbank abfragen
        String[] sportA = dbHelper.getAllVerbrKcalMainTaet();
        sportD = 0.0;
        for (String value : sportA) {
            double num = Double.parseDouble(value);
            sportD += num;
        }
        sportS = String.valueOf(sportD);
        sportEt.setText(sportS);
    }


    private void kalorienverbrauchsrechner() {
        // Kalorienverbrauchsrechner
        gewichtS = gewichtEt.getText().toString().trim();
        alterS = alterEt.getText().toString().trim();
        groesseS = groesseEt.getText().toString().trim();
        schritteS = schritteEt.getText().toString().trim();
        kalorienzufuhrS = kalorienzufuhrEt.getText().toString().trim();
        sportS = sportEt.getText().toString().trim();
        if (dd_geschlecht.getText().toString().equals("Männlich")) {
            if ((!gewichtS.equals("") && (!gewichtS.equals("."))) && (!alterS.equals("") && (!alterS.equals(".")))
                    && (!groesseS.equals("") && (!groesseS.equals("."))) && (!schritteS.equals("") && (!schritteS.equals(".")))
                    && (!kalorienzufuhrS.equals("") && (!kalorienzufuhrS.equals(".")))
                    && (!sportS.equals("") && (!sportS.equals(".")))) {
                gewichtD = Double.parseDouble(gewichtS);
                alterD = Double.parseDouble(alterS);
                groesseD = Double.parseDouble(groesseS);
                schritteI = (int) Double.parseDouble(schritteS);
                kalorienzufuhrD = Double.parseDouble(kalorienzufuhrS);
                sportD = Double.parseDouble(sportS);
                // Kalorienverbrauch pro Schritt Männer = (0,0005 * Körpergewicht in kg) + (0,0009 * Körpergröße in cm) - (0,0024 * Alter in Jahren) + 0,324
                //schritteIVerbrauchD = ((0.0005 * gewichtD) + (0.0009 * groesseD) - (0.0024 * alterD) + 0.324)/10;
                // Mifflin-St.Jeor-Formel Männer
                // Grundumsatz = (10 x Gewicht in kg) + (6,25 x Körpergröße in cm) - (5 x Alter in Jahren) + 5
                ergebnisD = (10 * gewichtD) + (6.25 * groesseD) - (5 * alterD) + 5
                        + 0.04 * schritteI + kalorienzufuhrD/10 + sportD;
                ergebnisTv.setText(String.format(Locale.US, "%.1f", ergebnisD));
            }
        }
        if (dd_geschlecht.getText().toString().equals("Weiblich")) {
            if ((!gewichtS.equals("") && (!gewichtS.equals("."))) && (!alterS.equals("") && (!alterS.equals(".")))
                    && (!groesseS.equals("") && (!groesseS.equals("."))) && (!schritteS.equals("") && (!schritteS.equals(".")))
                    && (!kalorienzufuhrS.equals("") && (!kalorienzufuhrS.equals(".")))
                    && (!sportS.equals("") && (!sportS.equals(".")))) {
                gewichtD = Double.parseDouble(gewichtS);
                alterD = Double.parseDouble(alterS);
                groesseD = Double.parseDouble(groesseS);
                schritteI = (int) Double.parseDouble(schritteS);
                kalorienzufuhrD = Double.parseDouble(kalorienzufuhrS);
                sportD = Double.parseDouble(sportS);
                // Kalorienverbrauch pro Schritt Frauen = (0,0004 * Körpergewicht in kg) + (0,0008 * Körpergröße in cm) - (0,0023 * Alter in Jahren) + 0,372
                //schritteIVerbrauchD = ((0.0004 * gewichtD) + (0.0008 * groesseD) - (0.0023 * alterD) + 0.372)/10;
                // Mifflin-St.Jeor-Formel Frauen
                // Grundumsatz = (10 x Gewicht in kg) + (6,25 x Körpergröße in cm) - (5 x Alter in Jahren) - 161
                ergebnisD = (10 * gewichtD) + (6.25 * groesseD) - (5 * alterD) - 161
                        + 0.04 * schritteI + kalorienzufuhrD/10 + sportD;
                ergebnisTv.setText(String.format(Locale.US, "%.1f", ergebnisD));
            }
        }
    }


    private void TextWatcherErgebnis() {
        TextWatcher textWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) { }
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                kalorienverbrauchsrechner();
            }
            @Override
            public void afterTextChanged(Editable editable) { }
        };
        dd_geschlecht.addTextChangedListener(textWatcher);
        gewichtEt.addTextChangedListener(textWatcher);
        alterEt.addTextChangedListener(textWatcher);
        groesseEt.addTextChangedListener(textWatcher);
        schritteEt.addTextChangedListener(textWatcher);
        kalorienzufuhrEt.addTextChangedListener(textWatcher);
        sportEt.addTextChangedListener(textWatcher);
    }




    public void onClick(View v) {
        if (v.getId() == R.id.btn_mainPopupClose) {
            setOnDismissListener(null);
            cancel();
        }
        if (v.getId() == R.id.btn_mainPopupSpeichern) {
            if ((!gewichtS.equals("") && (!gewichtS.equals("."))) && (!alterS.equals("") && (!alterS.equals(".")))
                    && (!groesseS.equals("") && (!groesseS.equals("."))) && (!schritteS.equals("") && (!schritteS.equals(".")))
                    && (!kalorienzufuhrS.equals("") && (!kalorienzufuhrS.equals(".")))
                    && (!sportS.equals("") && (!sportS.equals(".")))) {
                DBHelper dbHelper = new DBHelper(getContext());
                geschlechtS = dd_geschlecht.getText().toString();
                gewichtS = gewichtEt.getText().toString().trim();
                alterS = alterEt.getText().toString().trim();
                groesseS = groesseEt.getText().toString().trim();
                schritteS = schritteEt.getText().toString().trim();
                kalorienzufuhrS = kalorienzufuhrEt.getText().toString().trim();
                sportS = sportEt.getText().toString().trim();
                ergebnisS = ergebnisTv.getText().toString().trim();
                dbHelper.updateUserdata(idUd ,geschlechtS, gewichtS, alterS, groesseS, schritteS, kalorienzufuhrS,
                        sportS, ergebnisS);
                dismiss();
            } else {
                Toast.makeText(getContext(), "Bitte gib nur gültige Zahlen ein.", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void onBackPressed() {
        setOnDismissListener(null);
        cancel();
    }
}

