package com.studienarbeit.YourFitnessDiary.Main;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.studienarbeit.YourFitnessDiary.DBHelper;
import com.studienarbeit.YourFitnessDiary.R;

import java.util.Locale;
import java.util.Objects;

public class MainNahrungBearbeitenOFFActivity extends AppCompatActivity implements View.OnClickListener {
    
    private TextView tV_offTopicBrbMain;
    private TextView pT_offMarkeBrbMain, pT_offKcalBrbMain, pT_offCarbsBrbMain,
            pT_offFetteBrbMain, pT_offEiweissBrbMain;
    private Button btn_offSpeichernBrbMain;
    private EditText pT_offAnzahlPrtBrbMain;

    private String id, marke, beschreibung, portionsgroesse, anzahlPortionen,
            kcal, fette, carbs, eiweiss, einheit, portionsmenge, mainID,
            barcode, servingSize, quantitySize, kcal100g, carbs100g, fette100g, eiweiss100g,
            servingSizeNumber, quantitySizeNumber,
            newKcal, newFette, newCarbs, newEiweiss, ausgewSize;

    private double kcalServing, carbsServing, fetteServing, eiweissServing;

    private String [] dd_items = {"100 g"};
    private String[] dd_items2 = {"100 g", "servingSize"};
    private AutoCompleteTextView dd_EinheitenBrbMain;
    private ArrayAdapter<String> adapterItems;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_bearbeiten_off);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }

        tV_offTopicBrbMain = findViewById(R.id.tV_offTopicBrbMain);
        pT_offAnzahlPrtBrbMain = findViewById(R.id.pT_offAnzahlPrtBrbMain);
        pT_offKcalBrbMain = findViewById(R.id.pT_offKcalBrbMain);
        pT_offCarbsBrbMain = findViewById(R.id.pT_offCarbsBrbMain);
        pT_offFetteBrbMain = findViewById(R.id.pT_offFetteBrbMain);
        pT_offEiweissBrbMain = findViewById(R.id.pT_offEiweissBrbMain);
        pT_offMarkeBrbMain = findViewById(R.id.pT_offMarkeBrbMain);
        btn_offSpeichernBrbMain = findViewById(R.id.btn_offSpeichernBrbMain);
        dd_EinheitenBrbMain = findViewById(R.id.dd_EinheitenBrbMain);
        btn_offSpeichernBrbMain.setOnClickListener(this);

        getAndSetIntentData();
        chooseDropdownMenue();

        // servingSize Nährwerte berechnen
        if (!Objects.equals(servingSize, "null")) {
            servingSizeNumber = servingSize.replaceAll("[^\\d]", "");
            kcalServing = Double.parseDouble(servingSizeNumber)/100 * Double.parseDouble(kcal100g);
            carbsServing = Double.parseDouble(servingSizeNumber)/100 * Double.parseDouble(carbs100g);
            fetteServing = Double.parseDouble(servingSizeNumber)/100 * Double.parseDouble(fette100g);
            eiweissServing = Double.parseDouble(servingSizeNumber)/100 * Double.parseDouble(eiweiss100g);
        }

        TextWatcher100gBrbMain();
        TextWatcherServingBrbMain();
        dropdownOnClickMain();
    }

    private void chooseDropdownMenue() {
        // Dropdown-Menü erstellen
        dd_EinheitenBrbMain.setText(ausgewSize);
        if (Objects.equals(servingSize, "null")) {  // Wenn servingSize leer ist, dann hat es keine ServingSize
            adapterItems = new ArrayAdapter<String>(MainNahrungBearbeitenOFFActivity.this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, dd_items);
            dd_EinheitenBrbMain.setAdapter(adapterItems);
        } else {
            dd_items2[0] = "100 g";
            dd_items2[1] = servingSize;
            adapterItems = new ArrayAdapter<String>(MainNahrungBearbeitenOFFActivity.this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, dd_items2);
            dd_EinheitenBrbMain.setAdapter(adapterItems);
        }
    }


    private void dropdownOnClickMain() {
        dd_EinheitenBrbMain.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                String item = adapterView.getItemAtPosition(position).toString();
                double anzahlPrt = Double.parseDouble(pT_offAnzahlPrtBrbMain.getText().toString());
                if (item.equals("100 g")) {
                    pT_offKcalBrbMain.setText(String.format(Locale.US, "%.2f", anzahlPrt * Double.parseDouble(kcal100g)));
                    pT_offCarbsBrbMain.setText(String.format(Locale.US, "%.2f", anzahlPrt * Double.parseDouble(carbs100g)));
                    pT_offFetteBrbMain.setText(String.format(Locale.US, "%.2f", anzahlPrt * Double.parseDouble(fette100g)));
                    pT_offEiweissBrbMain.setText(String.format(Locale.US, "%.2f", anzahlPrt * Double.parseDouble(eiweiss100g)));
                    TextWatcher100gBrbMain();
                }
                if (item.equals(servingSize)) {
                    pT_offKcalBrbMain.setText(String.format(Locale.US, "%.2f", kcalServing * anzahlPrt));
                    pT_offCarbsBrbMain.setText(String.format(Locale.US, "%.2f", carbsServing * anzahlPrt));
                    pT_offFetteBrbMain.setText(String.format(Locale.US, "%.2f", fetteServing * anzahlPrt));
                    pT_offEiweissBrbMain.setText(String.format(Locale.US, "%.2f", eiweissServing * anzahlPrt));
                    TextWatcherServingBrbMain();
                }
            }
        });
        TextWatcher100gBrbMain();
    }

    private void TextWatcher100gBrbMain() {
        pT_offAnzahlPrtBrbMain.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (dd_EinheitenBrbMain.getText().toString().equals("100 g")) {
                    if (!pT_offAnzahlPrtBrbMain.getText().toString().trim().equals("") &&
                            (!pT_offAnzahlPrtBrbMain.getText().toString().trim().equals("."))) {
                        double anzahlPrt = Double.parseDouble(pT_offAnzahlPrtBrbMain.getText().toString());
                        pT_offKcalBrbMain.setText(String.format(Locale.US, "%.2f", anzahlPrt * Double.parseDouble(kcal100g)));
                        pT_offCarbsBrbMain.setText(String.format(Locale.US, "%.2f", anzahlPrt * Double.parseDouble(carbs100g)));
                        pT_offFetteBrbMain.setText(String.format(Locale.US, "%.2f", anzahlPrt * Double.parseDouble(fette100g)));
                        pT_offEiweissBrbMain.setText(String.format(Locale.US, "%.2f", anzahlPrt * Double.parseDouble(eiweiss100g)));
                    }
                }
            }
            @Override
            public void afterTextChanged(Editable s) { }
        });
    }


    private void TextWatcherServingBrbMain() {
        pT_offAnzahlPrtBrbMain.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (dd_EinheitenBrbMain.getText().toString().equals(servingSize)) {
                    if (!pT_offAnzahlPrtBrbMain.getText().toString().trim().equals("") &&
                            (!pT_offAnzahlPrtBrbMain.getText().toString().trim().equals("."))) {
                        double anzahlPrt = Double.parseDouble(pT_offAnzahlPrtBrbMain.getText().toString());
                        pT_offKcalBrbMain.setText(String.format(Locale.US, "%.2f", kcalServing * anzahlPrt));
                        pT_offCarbsBrbMain.setText(String.format(Locale.US, "%.2f", carbsServing * anzahlPrt));
                        pT_offFetteBrbMain.setText(String.format(Locale.US, "%.2f", fetteServing * anzahlPrt));
                        pT_offEiweissBrbMain.setText(String.format(Locale.US, "%.2f", eiweissServing * anzahlPrt));
                    }
                }
            }
            @Override
            public void afterTextChanged(Editable s) { }
        });
    }


    @SuppressLint("SetTextI18n")
    private void getAndSetIntentData() {
        if (getIntent().hasExtra("id")) {
            // Getten
            id = getIntent().getStringExtra("id");
            marke = getIntent().getStringExtra("marke");
            beschreibung = getIntent().getStringExtra("beschreibung");
            einheit = getIntent().getStringExtra("einheit");
            portionsgroesse = getIntent().getStringExtra("portionsgroesse");
            anzahlPortionen = getIntent().getStringExtra("anzahlPortionen");
            kcal = getIntent().getStringExtra("kcal");
            carbs = getIntent().getStringExtra("carbs");
            fette = getIntent().getStringExtra("fette");
            eiweiss = getIntent().getStringExtra("eiweiss");
            portionsmenge = getIntent().getStringExtra("portionsmenge");
            mainID = getIntent().getStringExtra("mainID");
            newKcal = getIntent().getStringExtra("newKcal");
            newCarbs = getIntent().getStringExtra("newCarbs");
            newFette = getIntent().getStringExtra("newFette");
            newEiweiss = getIntent().getStringExtra("newEiweiss");
            barcode = getIntent().getStringExtra("barcode");
            servingSize = getIntent().getStringExtra("servingSize");
            quantitySize = getIntent().getStringExtra("quantitySize");
            kcal100g = getIntent().getStringExtra("kcal100g");
            carbs100g = getIntent().getStringExtra("carbs100g");
            fette100g = getIntent().getStringExtra("fette100g");
            eiweiss100g = getIntent().getStringExtra("eiweiss100g");
            ausgewSize = getIntent().getStringExtra("ausgewSize");

            // Setten
            tV_offTopicBrbMain.setText("\"" + beschreibung + "\"" + " bearbeiten");
            pT_offMarkeBrbMain.setText(marke);
            pT_offAnzahlPrtBrbMain.setText(anzahlPortionen);
            pT_offKcalBrbMain.setText(newKcal);
            pT_offCarbsBrbMain.setText(newCarbs);
            pT_offFetteBrbMain.setText(newFette);
            pT_offEiweissBrbMain.setText(newEiweiss);

        } else {
            Toast.makeText(this, "Keine Daten in Nahrungsliste", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btn_offSpeichernBrbMain) {
            DBHelper dbHelper = new DBHelper(MainNahrungBearbeitenOFFActivity.this);

            if (!pT_offAnzahlPrtBrbMain.getText().toString().trim().equals("") && !pT_offAnzahlPrtBrbMain.getText().toString().trim().equals(".")) {
                // Teilt ausgewählte Einheit in Zahl und Buchstaben auf
                String portionsgroesse = dd_EinheitenBrbMain.getText().toString().trim();
                String portionsgroesseNumber = portionsgroesse.replaceAll("[^\\d.]+", "");  // Entfernt alle Zeichen
                String portionsgroesseLetters = portionsgroesse.replaceAll("[\\d.]", "").replaceAll("\\s+", ""); // Entfernt alle Zahlen und Leerzeichen

                // Rechnet Portionsmenge (anzahl Portionen * ausgewählte Portionseinheit)
                Double portionsmengeD = Double.parseDouble(portionsgroesseNumber) * Double.parseDouble(pT_offAnzahlPrtBrbMain.getText().toString());
                String portionsmengeString = String.valueOf(portionsmengeD);

                // Schneidet ggf. ".0" ab und fügt in beiden Fällen Portionsmenge mit Einheit zusammen
                if (portionsmengeString.matches("\\d+\\.0")) {
                    int portionsmengeInteger = portionsmengeD.intValue();
                    portionsmenge = portionsmengeInteger + " " + portionsgroesseLetters;
                } else {
                    portionsmenge = portionsmengeD + " " + portionsgroesseLetters;
                }

                dbHelper.updateDataMainOff(portionsgroesseNumber, pT_offAnzahlPrtBrbMain.getText().toString(),
                        portionsgroesseLetters, portionsmenge, mainID, pT_offKcalBrbMain.getText().toString(),
                        pT_offCarbsBrbMain.getText().toString(), pT_offFetteBrbMain.getText().toString(),
                        pT_offEiweissBrbMain.getText().toString(), dd_EinheitenBrbMain.getText().toString());


                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
                this.finish();
            } else {
                Toast.makeText(this, "Bitte gib eine gültige Zahl an.", Toast.LENGTH_SHORT).show();
            }

        }
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}
