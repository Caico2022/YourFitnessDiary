package com.studienarbeit.YourFitnessDiary.Nahrungsliste;

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

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.studienarbeit.YourFitnessDiary.DBHelper;
import com.studienarbeit.YourFitnessDiary.R;

import java.util.Locale;

public class NahrungsmittelBearbeitenActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = "creation";

    private TextView pTbrb_Marke, pTbrb_Beschreibung, pTBrb_Portionsgroesse, pTbrb_Kcal,pTbrb_fette, pTbrb_carbs, pTbrb_eiweiss;
    private EditText pTbrb_Portionen;
    private Button btnBrb_aktualisieren;
    private TextView tVbrb_EintragBearbeiten;

    private String id, marke, beschreibung, portionsgroesse, portionen, kcal, carbs, fette,
            eiweiss, einheit, portionsmenge, newKcal, newCarbs, newFette, newEiweiss;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bearbeiten);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }

        pTbrb_Marke = findViewById(R.id.pTbrb_Marke);
        pTBrb_Portionsgroesse = findViewById(R.id.pTBrb_Portionsgroesse);
        pTbrb_Portionen = findViewById(R.id.pTbrb_Portionen);
        pTbrb_Kcal = findViewById(R.id.pTbrb_Kcal);
        pTbrb_fette = findViewById(R.id.pTbrb_fette);
        pTbrb_carbs = findViewById(R.id.pTbrb_carbs);
        pTbrb_eiweiss = findViewById(R.id.pTbrb_eiweiss);
        tVbrb_EintragBearbeiten = findViewById(R.id.tVbrb_EintragBearbeiten);
        btnBrb_aktualisieren = findViewById(R.id.btnBrb_aktualisieren);
        btnBrb_aktualisieren.setOnClickListener(this);

/*        // vor ArrayAdapter<String> ..., damit noch die anderen Einheiten ausgewählt werden können
        getAndSetIntentData();

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, dd_items);
        ddBrb_Einheiten.setAdapter(adapter);*/

        getAndSetIntentData();
        TextWatcher();
    }


    private void TextWatcher() {
        pTbrb_Portionen.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) { }
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (!pTbrb_Portionen.getText().toString().trim().equals("") &&
                        (!pTbrb_Portionen.getText().toString().trim().equals("."))) {
                    // Rechnet mit originalen Nährwerten
                    double PrtProPackung = Double.parseDouble(pTbrb_Portionen.getText().toString().trim());
                    double newKcal = Double.parseDouble(kcal) * PrtProPackung;
                    pTbrb_Kcal.setText(String.format(Locale.US, "%.2f", newKcal));
                    double newCarbs = Double.parseDouble(carbs) * PrtProPackung;
                    pTbrb_carbs.setText(String.format(Locale.US, "%.2f", newCarbs));
                    double newFette = Double.parseDouble(fette) * PrtProPackung;
                    pTbrb_fette.setText(String.format(Locale.US, "%.2f", newFette));
                    double newEiweiss = Double.parseDouble(eiweiss) * PrtProPackung;
                    pTbrb_eiweiss.setText(String.format(Locale.US, "%.2f", newEiweiss));
                }
            }
            @Override
            public void afterTextChanged(Editable editable) { }
        });
    }


    @SuppressLint("SetTextI18n")
    private void getAndSetIntentData() {
        if (getIntent().hasExtra("id")) {
            id = getIntent().getStringExtra("id");
            marke = getIntent().getStringExtra("marke");
            beschreibung = getIntent().getStringExtra("beschreibung");
            portionsgroesse = getIntent().getStringExtra("portionsgroesse");
            portionen = getIntent().getStringExtra("portionen");
            kcal = getIntent().getStringExtra("kcal");
            carbs = getIntent().getStringExtra("carbs");
            fette = getIntent().getStringExtra("fette");
            eiweiss = getIntent().getStringExtra("eiweiss");
            einheit = getIntent().getStringExtra("einheit");
            portionsmenge = getIntent().getStringExtra("portionsmenge");
            newKcal = getIntent().getStringExtra("kcal100g");  // originalen Nährwerte
            newCarbs = getIntent().getStringExtra("carbs100g");
            newFette = getIntent().getStringExtra("fette100g");
            newEiweiss = getIntent().getStringExtra("eiweiss100g");

            tVbrb_EintragBearbeiten.setText("\"" + beschreibung + "\"" + " bearbeiten");
            pTbrb_Marke.setText(marke);
            String prtgrEinh = portionsgroesse + " " + einheit;
            pTBrb_Portionsgroesse.setText(prtgrEinh);
            pTbrb_Portionen.setText(portionen);
            pTbrb_Kcal.setText(newKcal);
            pTbrb_carbs.setText(newCarbs);
            pTbrb_fette.setText(newFette);
            pTbrb_eiweiss.setText(newEiweiss);

        } else {
            Toast.makeText(this, "Keine Daten in Nahrungsliste", Toast.LENGTH_SHORT).show();
        }
    }


/*    public String PortionsmengeRechnerBrb() {
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
    }*/

/*    void confirmDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Löschen von " + beschreibung);
        builder.setMessage("Sind Sie sicher, dass Sie " +  beschreibung + " löschen möchten?");
        builder.setPositiveButton("Ja", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                DBHelper dbHelper = new DBHelper(BearbeitenActivity.this);
                dbHelper.deleteOneRow(id);  // Wichtig: Getter von getAndSetIntentData()
                finish();
            }
        });
        builder.setNegativeButton("Nein", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        builder.create().show();
    }*/



    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btnBrb_aktualisieren) {
            DBHelper dbHelper = new DBHelper(NahrungsmittelBearbeitenActivity.this);

            if (!pTbrb_Portionen.getText().toString().trim().equals("") && !pTbrb_Portionen.getText().toString().trim().equals(".")) {

                // Rechnet Portionsmenge (anzahl Portionen * ausgewählte Portionseinheit)
                Double portionsmengeD = Double.parseDouble(pTbrb_Portionen.getText().toString()) *
                        Double.parseDouble(portionsgroesse);

                // Schneidet ggf. ".0" ab und fügt in beiden Fällen Portionsmenge mit Einheit zusammen
                if (String.valueOf(portionsmengeD).matches("\\d+\\.0")) {
                    int portionsmengeInteger = portionsmengeD.intValue();
                    portionsmenge = portionsmengeInteger + " " + einheit;
                } else {
                    portionsmenge = portionsmengeD + " " + einheit;
                }

                //String portionsmenge = PortionsmengeRechnerBrb();
                marke = pTbrb_Marke.getText().toString().trim();
                //beschreibung = pTbrb_Beschreibung.getText().toString().trim();
                //portionsgroesse = pTBrb_Portionsgroesse.getText().toString().trim();
                //einheit = ddBrb_Einheiten.getText().toString().trim();
                portionen = pTbrb_Portionen.getText().toString().trim();
                newKcal = pTbrb_Kcal.getText().toString().trim();
                newCarbs = pTbrb_carbs.getText().toString().trim();
                newFette = pTbrb_fette.getText().toString().trim();
                newEiweiss = pTbrb_eiweiss.getText().toString().trim();

                beschreibung = getIntent().getStringExtra("beschreibung");
                dbHelper.updateData(id, marke, beschreibung, portionsgroesse, portionen,
                        kcal, carbs, fette, eiweiss, einheit, portionsmenge,
                        newKcal, newCarbs, newFette, newEiweiss);
                Intent intent = new Intent(NahrungsmittelBearbeitenActivity.this, NahrungslisteActivity.class);
                startActivity(intent);
            } else {
                Toast.makeText(this, "Bitte gib eine gültige Zahl an.", Toast.LENGTH_SHORT).show();
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