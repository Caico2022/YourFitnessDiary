package com.studienarbeit.YourFitnessDiary.Nahrungsliste;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.studienarbeit.YourFitnessDiary.DBHelper;
import com.studienarbeit.YourFitnessDiary.R;

import java.text.DecimalFormat;

public class NahrungErstellenActivity extends AppCompatActivity implements View.OnClickListener {

    Button btn_speichern;
    EditText pT_Marke;
    EditText pT_Beschreibung;
    EditText pT_Portionsgroesse;
    EditText pT_Portionen;
    EditText pT_Kcal;
    EditText pT_carbs;
    EditText pT_fette;
    EditText pT_eiweiss;
    private String [] dd_items = {"g", "kg", "ml", "l", "Scheiben", "Tassen", "Stücke", "TL", "EL"};
    private ArrayAdapter<String> adapterItems;
    AutoCompleteTextView dd_Einheiten;

    private String kcalS = "0", carbsS = "0", fetteS = "0", eiweissS = "0";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nahrung_erstellen);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }
        pT_Marke = findViewById(R.id.pT_Marke);
        pT_Beschreibung = findViewById(R.id.pT_Beschreibung);
        pT_Portionsgroesse = findViewById(R.id.pT_Portionsgroesse);
        pT_Portionen = findViewById(R.id.pT_Portionen);
        pT_Kcal = findViewById(R.id.pT_Kcal);
        pT_fette = findViewById(R.id.pT_fette);
        pT_carbs = findViewById(R.id.pT_carbs);
        pT_eiweiss = findViewById(R.id.pT_eiweiss);
        btn_speichern = findViewById(R.id.btn_Speichern);
        btn_speichern.setOnClickListener(this);

        dd_Einheiten = findViewById(R.id.dd_Einheiten);
        adapterItems = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, dd_items);
        dd_Einheiten.setAdapter(adapterItems);
        dd_Einheiten.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String item = adapterView.getItemAtPosition(i).toString();
            }
        });
    }

    // Rechnet Portionsmenge = Portionsgröße * Portionen pro Packung
    // Schneidet Kommazahl ab, wenn ".0"
    // Fügt Portionsmenge mit Einheit zusammen als einzelner String
    private String PortionsmengeRechner() {
        double portionsgroesse = Double.parseDouble(pT_Portionsgroesse.getText().toString());
        double portionenProPackung = Double.parseDouble(pT_Portionen.getText().toString());
        String einheit = dd_Einheiten.getText().toString();
        double portionsmenge_zahl = portionsgroesse * portionenProPackung;
        String portionsmenge_zahlString = String.valueOf(portionsmenge_zahl);

        // Schneidet Kommazahl ab, wenn ".0"
        StringBuilder sb = new StringBuilder(portionsmenge_zahlString);
        String portionsmenge = "";  // Endergebnis
        for (int i = 0; i < portionsmenge_zahlString.length(); i++) {
            if (sb.charAt(i) == '.' && (sb.charAt(i+1) == '0')) {  // Wenn 0er Nachkommastelle gefunden, dann zu int (Nachkommastelle abschneiden)
                int portionsmengeInt = 0;
                portionsmengeInt = (int) portionsmenge_zahl;
                portionsmenge = portionsmengeInt + " " + einheit;  // Fügt Portionsmenge mit Einheit zusammen als einzelner String
            }
            if (sb.charAt(i) == '.' && (sb.charAt(i+1) != '0')) {   // Wenn keine 0er Nachkommastelle gefunden, dann aufrunden
                DecimalFormat portionsmenge_Double = new DecimalFormat(".00");
                portionsmenge_Double.format(portionsmenge_zahl);
                StringBuilder sb2 = new StringBuilder(portionsmenge_zahlString);
                portionsmenge = portionsmenge_Double.format(portionsmenge_zahl) + " " + einheit;
            }
        }
        return portionsmenge;
    }



    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btn_Speichern) {   // die eingegeben Werte werden an addNahrung übergeben
            if (pT_Beschreibung.getText().toString().equals("") || pT_Portionsgroesse.getText().toString().equals("")
                    || pT_Portionen.getText().toString().equals("") || pT_Kcal.getText().toString().equals("")
                    || dd_Einheiten.getText().toString().equals("Einheiten")
                    || (pT_Portionsgroesse.getText().toString().trim().equals("."))
                    || (pT_Portionen.getText().toString().trim().equals("."))
                    || (pT_Kcal.getText().toString().trim().equals("."))) {
                Toast.makeText(this, "Bitte die erforderlichen Felder ausfüllen", Toast.LENGTH_SHORT).show();
            } else {
                DBHelper dbHelper = new DBHelper(NahrungErstellenActivity.this);
                String portionsmenge = PortionsmengeRechner();

                carbsS = pT_carbs.getText().toString();
                fetteS = pT_carbs.getText().toString();
                eiweissS = pT_carbs.getText().toString();

                if (carbsS.equals("") || carbsS.equals(".") || carbsS.equals(" ")) {
                    carbsS = "0";
                }
                if (fetteS.equals("") || fetteS.equals(".") || fetteS.equals(" ")) {
                    fetteS = "0";
                }
                if (eiweissS.equals("") || eiweissS.equals(".") || eiweissS.equals(" ")) {
                    eiweissS = "0";
                }

                dbHelper.addNahrung(pT_Marke.getText().toString().trim(), pT_Beschreibung.getText().toString().trim(),
                        pT_Portionsgroesse.getText().toString().trim(), pT_Portionen.getText().toString().trim(),
                        pT_Kcal.getText().toString().trim(), carbsS, fetteS, eiweissS,
                        dd_Einheiten.getText().toString(), portionsmenge, pT_Kcal.getText().toString().trim(),
                        carbsS, fetteS, eiweissS);
                Intent zuNahrungsliste = new Intent(this, NahrungslisteActivity.class);
                startActivity(zuNahrungsliste);
                this.finish();

                }
            }
        }



    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, NahrungslisteActivity.class);
        startActivity(intent);
        finish();
    }
}