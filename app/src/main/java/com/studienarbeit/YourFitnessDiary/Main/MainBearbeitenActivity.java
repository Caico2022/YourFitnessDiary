package com.studienarbeit.YourFitnessDiary.Main;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.studienarbeit.YourFitnessDiary.DBHelper;
import com.studienarbeit.YourFitnessDiary.R;

import java.text.DecimalFormat;

public class MainBearbeitenActivity extends AppCompatActivity implements View.OnClickListener {

    TextView tVbrbMain_EintragBearbeiten;
    EditText pTbrbMain_Marke, pTbrbMain_Beschreibung, pTBrbMain_Portionsgroesse, pTbrbMain_Einheiten, pTbrbMain_Portionen,
            pTbrbMain_Kcal, pTbrbMain_CarbsGes, pTbrbMain_FetteGes, pTbrbMain_EiweissGes, pTBrbMain_PrtgrEinh;
    Button btnBrbMain_aktualisieren, btnBrbMain_loeschen;
    String id__, marke, beschreibung, portionsgroesse, portionen, kcal, carbsGes, fetteGes,
            eiweissGes, einheit, portionsmenge, mainID, newKcal, newCarbs, newFette, newEiweiss;
    DBHelper myDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_bearbeiten);
        DBHelper dbHelper = new DBHelper(MainBearbeitenActivity.this);

        pTbrbMain_Marke = findViewById(R.id.pTbrbMain_Marke);
        pTbrbMain_Beschreibung = findViewById(R.id.pTbrbMain_Beschreibung);
        pTBrbMain_Portionsgroesse = findViewById(R.id.pTBrbMain_Portionsgroesse);
        pTbrbMain_Einheiten = findViewById(R.id.pTbrbMain_Einheiten);
        pTbrbMain_Portionen = findViewById(R.id.pTbrbMain_Portionen);
        pTbrbMain_Kcal = findViewById(R.id.pTbrbMain_Kcal);
        pTbrbMain_CarbsGes = findViewById(R.id.pTbrbMain_CarbsGes);
        pTbrbMain_FetteGes = findViewById(R.id.pTbrbMain_FetteGes);
        pTbrbMain_EiweissGes = findViewById(R.id.pTbrbMain_EiweissGes);
        pTBrbMain_PrtgrEinh = findViewById(R.id.pTBrbMain_PrtgrEinh);
        btnBrbMain_aktualisieren = findViewById(R.id.btnBrbMain_aktualisieren);
        btnBrbMain_loeschen = findViewById(R.id.btnBrbMain_loeschen);
        pTbrbMain_Marke.setEnabled(false);
        pTbrbMain_Beschreibung.setEnabled(false);
        pTBrbMain_Portionsgroesse.setEnabled(false);
        pTbrbMain_Einheiten.setEnabled(false);
        // Portionen soll editierbar sein
        pTbrbMain_Kcal.setEnabled(false);
        pTbrbMain_CarbsGes.setEnabled(false);
        pTbrbMain_FetteGes.setEnabled(false);
        pTbrbMain_EiweissGes.setEnabled(false);
        pTBrbMain_PrtgrEinh.setEnabled(false);

        btnBrbMain_aktualisieren.setOnClickListener(this);
        btnBrbMain_loeschen.setOnClickListener(this);

        String[] rowData;
        rowData = getAndSetIntentData_mainBrb();

        double originalKcal = Double.parseDouble(rowData[0]);
        double originalCarbs = Double.parseDouble(rowData[1]);
        double originalFette = Double.parseDouble(rowData[2]);
        double originalEiweiss = Double.parseDouble(rowData[3]);

        TextWatcher(originalKcal, originalCarbs, originalFette, originalEiweiss);



    }

    public void TextWatcher(double originalKcal, double originalCarbs, double originalFette, double originalEiweiss) {
        pTbrbMain_Portionen.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) { }
            @SuppressLint("DefaultLocale")
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (!pTbrbMain_Portionen.getText().toString().trim().equals("") &&
                        (!pTbrbMain_Portionen.getText().toString().trim().equals("."))) {
                    double PrtProPackung = Double.parseDouble(pTbrbMain_Portionen.getText().toString().trim());
                    double newKcal = originalKcal * PrtProPackung;
                    pTbrbMain_Kcal.setText(String.format("%.2f", newKcal));
                    double newCarbs = originalCarbs * PrtProPackung;
                    pTbrbMain_CarbsGes.setText(String.format("%.2f", newCarbs));
                    double newFette = originalFette * PrtProPackung;
                    pTbrbMain_FetteGes.setText(String.format("%.2f", newFette));
                    double newEiweiss = originalEiweiss * PrtProPackung;
                    pTbrbMain_EiweissGes.setText(String.format("%.2f", newEiweiss));
                }
            }
            @Override
            public void afterTextChanged(Editable editable) { }
        });
    }


    @SuppressLint("SetTextI18n")
    public String[] getAndSetIntentData_mainBrb() {
        if (getIntent().hasExtra("mainID")) {
            String prtgrEinh = PortionsgroesseUndEinheit();
            id__ = getIntent().getStringExtra("id");
            marke = getIntent().getStringExtra("marke");
            beschreibung = getIntent().getStringExtra("beschreibung");
            portionsgroesse = getIntent().getStringExtra("portionsgroesse");
            einheit = getIntent().getStringExtra("einheit");
            portionen = getIntent().getStringExtra("portionen");
            kcal = getIntent().getStringExtra("kcal");
            fetteGes = getIntent().getStringExtra("fetteGes");
            carbsGes = getIntent().getStringExtra("carbsGes");
            eiweissGes = getIntent().getStringExtra("eiweissGes");
            portionsmenge = getIntent().getStringExtra("portionsmenge");
            mainID = getIntent().getStringExtra("mainID");

            pTbrbMain_Marke.setText(marke);
            pTbrbMain_Beschreibung.setText(beschreibung);
            pTBrbMain_PrtgrEinh.setText(prtgrEinh);
            pTbrbMain_Portionen.setText(portionen);
            pTbrbMain_Kcal.setText(kcal);
            pTbrbMain_FetteGes.setText(fetteGes);
            pTbrbMain_CarbsGes.setText(carbsGes);
            pTbrbMain_EiweissGes.setText(eiweissGes);

            DBHelper dbHelper = new DBHelper(MainBearbeitenActivity.this);
            String[] rowData;
            rowData = dbHelper.readNaehrwerteOneRow_main(mainID);
            return rowData;
        } else {
            Toast.makeText(this, "Keine Daten in Main", Toast.LENGTH_SHORT).show();
        }
        return new String[0];
    }


    public String PortionsgroesseUndEinheit() {
        portionsgroesse = getIntent().getStringExtra("portionsgroesse");
        einheit = getIntent().getStringExtra("einheit");

        // Schneidet Kommazahl ab, wenn ".0"
        StringBuilder sb = new StringBuilder(portionsgroesse);
        String prtgrEinh = "";  // Endergebnis
        for (int i = 0; i < portionsgroesse.length(); i++) {
            if (sb.charAt(i) == '.' && (sb.charAt(i+1) == '0')) {  // Wenn 0er Nachkommastelle gefunden, dann zu int (Nachkommastelle abschneiden)
                int portionsmengeInt = 0;
                int portionsgroesseInt = (int) Double.parseDouble(portionsgroesse);
                prtgrEinh = portionsgroesseInt + " " + einheit;  // Fügt Portionsmenge mit Einheit zusammen als einzelner String
            }
            if (sb.charAt(i) == '.' && (sb.charAt(i+1) != '0')) {   // Wenn keine 0er Nachkommastelle gefunden, dann aufrunden
                DecimalFormat portionsgroesse_Double = new DecimalFormat(".00");
                portionsgroesse_Double.format(portionsgroesse);
                StringBuilder sb2 = new StringBuilder(portionsgroesse);
                portionsmenge = portionsgroesse_Double.format(portionsgroesse) + " " + einheit;
            }
        }
        return prtgrEinh;
    }


    public String PortionsmengeRechnerMainBrb() {
        String portionsgroesseString = getIntent().getStringExtra("portionsgroesse");
        double portionsgroesse = Double.parseDouble(portionsgroesseString);
        double portionenProPackung = Double.parseDouble(pTbrbMain_Portionen.getText().toString());
        einheit = getIntent().getStringExtra("einheit");
        double portionsmenge_zahl = portionsgroesse * portionenProPackung;
        String portionsmenge_zahlString = String.valueOf(portionsmenge_zahl);

        // Schneidet Kommazahl ab, wenn ".0"
        StringBuilder sb = new StringBuilder(portionsmenge_zahlString);
        String portionsmenge = "";  // Endergebnis
        for (int i = 0; i < portionsmenge_zahlString.length(); i++) {
            if (sb.charAt(i) == '.' && (sb.charAt(i+1) == '0')) {  // Wenn 0er Nachkommastelle gefunden, dann zu int (Nachkommastelle abschneiden)
                int portionsmengeInt = 0;
                portionsmengeInt = (int) portionsmenge_zahl;
                portionsmenge = portionsmengeInt + " " + einheit;  // Fügt Portionsmenge mit Einheit zusammen als einzelner String
            }
            if (sb.charAt(i) == '.' && (sb.charAt(i+1) != '0')) {   // Wenn keine 0er Nachkommastelle gefunden, dann aufrunden
                DecimalFormat portionsmenge_Double = new DecimalFormat(".00");
                portionsmenge_Double.format(portionsmenge_zahl);
                StringBuilder sb2 = new StringBuilder(portionsmenge_zahlString);
                portionsmenge = portionsmenge_Double.format(portionsmenge_zahl) + " " + einheit;
            }
        }
        return portionsmenge;
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btnBrbMain_aktualisieren) {
            if (pTbrbMain_Portionen.getText().toString().equals("")) {
                Toast.makeText(this, "Bitte einen Wert für die Portionen pro Packung eingeben", Toast.LENGTH_SHORT).show();
            } else {
                DBHelper dbHelper = new DBHelper(MainBearbeitenActivity.this);
                String portionsmenge = PortionsmengeRechnerMainBrb();
                portionen = pTbrbMain_Portionen.getText().toString().trim();
                newKcal = pTbrbMain_Kcal.getText().toString().trim();
                newCarbs = pTbrbMain_CarbsGes.getText().toString().trim();
                newFette = pTbrbMain_FetteGes.getText().toString().trim();
                newEiweiss = pTbrbMain_EiweissGes.getText().toString().trim();
                dbHelper.updateData_main(mainID, portionen, portionsmenge,
                        newKcal, newCarbs, newFette, newEiweiss);
            }
        }
    }
}