package com.studienarbeit.YourFitnessDiary.Nahrungsmittel;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.studienarbeit.YourFitnessDiary.DBHelper;
import com.studienarbeit.YourFitnessDiary.R;

import java.text.DecimalFormat;

public class BearbeitenActivity extends AppCompatActivity implements View.OnClickListener {

    EditText pTbrb_Marke, pTbrb_Beschreibung, pTBrb_Portionsgroesse, pTbrb_Portionen,
            pTbrb_Kcal,pTbrb_FetteGes, pTbrb_CarbsGes, pTbrb_EiweissGes;
    Button btnBrb_aktualisieren, btnBrb_loeschen;
    TextView tVbrb_EintragBearbeiten;
    String id__, marke, beschreibung, portionsgroesse, portionen,
            kcal, fetteGes, carbsGes, eiweissGes, einheit, portionsmenge;

    String [] dd_items = {"gramm", "kg", "ml", "liter", "Scheiben", "Tassen", "Stücke", "TL", "EL"};
    AutoCompleteTextView ddBrb_Einheiten;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bearbeiten);

        pTbrb_Marke = findViewById(R.id.pTbrb_Marke);
        pTbrb_Beschreibung = findViewById(R.id.pTbrb_Beschreibung);
        pTBrb_Portionsgroesse = findViewById(R.id.pTBrb_Portionsgroesse);
        pTbrb_Portionen = findViewById(R.id.pTbrb_Portionen);
        pTbrb_Kcal = findViewById(R.id.pTbrb_Kcal);
        pTbrb_FetteGes = findViewById(R.id.pTbrb_FetteGes);
        pTbrb_CarbsGes = findViewById(R.id.pTbrb_CarbsGes);
        pTbrb_EiweissGes = findViewById(R.id.pTbrb_EiweissGes);
        tVbrb_EintragBearbeiten = findViewById(R.id.tVbrb_EintragBearbeiten);
        ddBrb_Einheiten = findViewById(R.id.ddBrb_Einheiten);
        ddBrb_Einheiten = findViewById(R.id.ddBrb_Einheiten);

        btnBrb_aktualisieren = findViewById(R.id.btnBrb_aktualisieren);
        btnBrb_loeschen = findViewById(R.id.btnBrb_loeschen);
        btnBrb_aktualisieren.setOnClickListener(this);
        btnBrb_loeschen.setOnClickListener(this);

        // vor ArrayAdapter<String> ..., damit noch die anderen Einheiten ausgewählt werden können
        getAndSetIntentData();

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, dd_items);
        ddBrb_Einheiten.setAdapter(adapter);

/*        ActionBar ab = getSupportActionBar();
        if (ab != null) {
            ab.setTitle(beschreibung);
        }*/
    }

    // Kopieren der Daten des RecyclerView-Items in die UpdateActivity
    @SuppressLint("SetTextI18n")
    void getAndSetIntentData() {  // Gettet Wert von Methode onBindViewHolder (CustomAdapter) und Settet diesen an die PlainText
        if (getIntent().hasExtra("id")) {
            // Getten
            id__ = getIntent().getStringExtra("id"); // "id" von putExtra CustomAdapter/onBindViewHolder()
            marke = getIntent().getStringExtra("marke");
            beschreibung = getIntent().getStringExtra("beschreibung"); // Lädt gespeichert String von "beschreibung" in beschreibung
            einheit = getIntent().getStringExtra("einheit");
            portionsgroesse = getIntent().getStringExtra("portionsgroesse");
            portionen = getIntent().getStringExtra("portionen");
            kcal = getIntent().getStringExtra("kcal");
            fetteGes = getIntent().getStringExtra("fetteGes");
            carbsGes = getIntent().getStringExtra("carbsGes");
            eiweissGes = getIntent().getStringExtra("eiweissGes");
            portionsmenge = getIntent().getStringExtra("portionsmenge");

            // Setten
            pTbrb_Marke.setText(marke);
            pTbrb_Beschreibung.setText(beschreibung);  // Lädt gespeicherten Wert von beschreibung in pT_UpdBeschreibung
            pTBrb_Portionsgroesse.setText(portionsgroesse);
            ddBrb_Einheiten.setText(einheit);
            pTbrb_Portionen.setText(portionen);
            pTbrb_Kcal.setText(kcal);
            pTbrb_FetteGes.setText(fetteGes);
            pTbrb_CarbsGes.setText(carbsGes);
            pTbrb_EiweissGes.setText(eiweissGes);
            tVbrb_EintragBearbeiten.setText("\"" + beschreibung + "\"" + " bearbeiten");
        } else {
            Toast.makeText(this, "Keine Daten in Nahrungsliste", Toast.LENGTH_SHORT).show();
        }
    }

    public String PortionsmengeRechnerBrb() {
        double portionsgroesse = Double.parseDouble(pTBrb_Portionsgroesse.getText().toString());
        double portionenProPackung = Double.parseDouble(pTbrb_Portionen.getText().toString());
        String einheit = ddBrb_Einheiten.getText().toString();
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

    void confirmDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Löschen von " + beschreibung);
        builder.setMessage("Sind Sie sicher, dass Sie " +  beschreibung + " löschen möchten?");
        builder.setPositiveButton("Ja", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                DBHelper dbHelper = new DBHelper(BearbeitenActivity.this);
                dbHelper.deleteOneRow(id__);  // Wichtig: Getter von getAndSetIntentData()
                finish();
            }
        });
        builder.setNegativeButton("Nein", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        builder.create().show();
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, Nahrungsliste.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btnBrb_aktualisieren) {
            DBHelper dbHelper = new DBHelper(BearbeitenActivity.this);
            String portionsmenge = PortionsmengeRechnerBrb();
            marke = pTbrb_Marke.getText().toString().trim();
            beschreibung = pTbrb_Beschreibung.getText().toString().trim();
            portionsgroesse = pTBrb_Portionsgroesse.getText().toString().trim();
            einheit = ddBrb_Einheiten.getText().toString().trim();
            portionen = pTbrb_Portionen.getText().toString().trim();
            kcal = pTbrb_Kcal.getText().toString().trim();
            carbsGes = pTbrb_CarbsGes.getText().toString().trim();
            fetteGes = pTbrb_FetteGes.getText().toString().trim();
            eiweissGes = pTbrb_EiweissGes.getText().toString().trim();
            dbHelper.updateData(id__, marke, beschreibung, portionsgroesse, einheit, portionen,
                    kcal, carbsGes, fetteGes, eiweissGes, portionsmenge);
            Intent intent = new Intent(BearbeitenActivity.this, Nahrungsliste.class);
            startActivity(intent);
        }
        if (v.getId() == R.id.btnBrb_loeschen) {
           confirmDialog();
        }
    }
}