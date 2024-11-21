package com.studienarbeit.YourFitnessDiary.Main;

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

public class MainNahrungBearbeitenActivity extends AppCompatActivity implements View.OnClickListener {

    TextView pTbrbMain_Beschreibung, pTbrbMain_Marke, pTbrbMain_Kcal, pTbrbMain_carbs,
            pTbrbMain_fette, pTbrbMain_eiweiss, pTbrbMain_Einheiten, pTBrbMain_Portionsgroesse;
    EditText pTbrbMain_Portionen;

    Button btnBrbMain_aktualisieren;
    String id, marke, beschreibung, portionsgroesse, portionen, kcal, carbs, fette,
            eiweiss, einheit, portionsmenge, mainID, newKcal, newCarbs, newFette, newEiweiss;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_bearbeiten);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }

        pTbrbMain_Marke = findViewById(R.id.pTbrbMain_Marke);
        pTbrbMain_Beschreibung = findViewById(R.id.pTbrbMain_Beschreibung);
        //pTbrbMain_Einheiten = findViewById(R.id.pTbrbMain_Einheiten);
        pTBrbMain_Portionsgroesse = findViewById(R.id.pTBrbMain_Portionsgroesse);
        pTbrbMain_Portionen = findViewById(R.id.pTbrbMain_Portionen);
        pTbrbMain_Kcal = findViewById(R.id.pTbrbMain_Kcal);
        pTbrbMain_carbs = findViewById(R.id.pTbrbMain_carbs);
        pTbrbMain_fette = findViewById(R.id.pTbrbMain_fette);
        pTbrbMain_eiweiss = findViewById(R.id.pTbrbMain_eiweiss);
        btnBrbMain_aktualisieren = findViewById(R.id.btnBrbMain_aktualisieren);
        btnBrbMain_aktualisieren.setOnClickListener(this);

        getAndSetIntentData();
        TextWatcher();
    }

    private void TextWatcher() {
        pTbrbMain_Portionen.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) { }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (!pTbrbMain_Portionen.getText().toString().trim().equals("") &&
                        (!pTbrbMain_Portionen.getText().toString().trim().equals("."))) {
                    double PrtProPackung = Double.parseDouble(pTbrbMain_Portionen.getText().toString().trim());
                    double newKcal = Double.parseDouble(kcal) * PrtProPackung;
                    pTbrbMain_Kcal.setText(String.format(Locale.US, "%.2f", newKcal));
                    double newCarbs = Double.parseDouble(carbs) * PrtProPackung;
                    pTbrbMain_carbs.setText(String.format(Locale.US, "%.2f", newCarbs));
                    double newFette = Double.parseDouble(fette) * PrtProPackung;
                    pTbrbMain_fette.setText(String.format(Locale.US, "%.2f", newFette));
                    double newEiweiss = Double.parseDouble(eiweiss) * PrtProPackung;
                    pTbrbMain_eiweiss.setText(String.format(Locale.US, "%.2f", newEiweiss));
                }
            }
            @Override
            public void afterTextChanged(Editable editable) { }
        });
    }


    @SuppressLint("SetTextI18n")
    private void getAndSetIntentData() {
        if (getIntent().hasExtra("mainID")) {
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
            mainID = getIntent().getStringExtra("mainID");
            newKcal = getIntent().getStringExtra("newKcal");
            newFette = getIntent().getStringExtra("newFette");
            newCarbs = getIntent().getStringExtra("newCarbs");
            newEiweiss = getIntent().getStringExtra("newEiweiss");


            pTbrbMain_Beschreibung.setText("\"" + beschreibung + "\"" + " bearbeiten");
            String prtgrEinh = portionsgroesse + " " + einheit;
            pTbrbMain_Marke.setText(marke);
            pTBrbMain_Portionsgroesse.setText(prtgrEinh);
            pTbrbMain_Portionen.setText(portionen);
            pTbrbMain_Kcal.setText(newKcal);
            pTbrbMain_fette.setText(newFette);
            pTbrbMain_carbs.setText(newCarbs);
            pTbrbMain_eiweiss.setText(newEiweiss);

        } else {
            Toast.makeText(this, "Keine Daten in Main", Toast.LENGTH_SHORT).show();
        }
    }


    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btnBrbMain_aktualisieren) {
            safeData();
        }
    }

    private void safeData() {
        DBHelper dbHelper = new DBHelper(MainNahrungBearbeitenActivity.this);
        if (!pTbrbMain_Portionen.getText().toString().trim().equals("") && !pTbrbMain_Portionen.getText().toString().trim().equals(".")) {
            // Rechnet Portionsmenge (anzahl Portionen * ausgewählte Portionseinheit)
            Double portionsmengeD = Double.parseDouble(pTbrbMain_Portionen.getText().toString()) *
                    Double.parseDouble(portionsgroesse);

            // Schneidet ggf. ".0" ab und fügt in beiden Fällen Portionsmenge mit Einheit zusammen
            if (String.valueOf(portionsmengeD).matches("\\d+\\.0")) {
                int portionsmengeInteger = portionsmengeD.intValue();
                portionsmenge = portionsmengeInteger + " " + einheit;
            } else {
                portionsmenge = portionsmengeD + " " + einheit;
            }

            portionen = pTbrbMain_Portionen.getText().toString().trim();
            newKcal = pTbrbMain_Kcal.getText().toString().trim();
            newCarbs = pTbrbMain_carbs.getText().toString().trim();
            newFette = pTbrbMain_fette.getText().toString().trim();
            newEiweiss = pTbrbMain_eiweiss.getText().toString().trim();
            dbHelper.updateDataMain(mainID, portionen, portionsmenge,
                    newKcal, newCarbs, newFette, newEiweiss);

            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            this.finish();
        } else {
            Toast.makeText(this, "Bitte gib eine gültige Zahl an.", Toast.LENGTH_SHORT).show();
        }
    }



    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}