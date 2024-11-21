package com.studienarbeit.YourFitnessDiary.Nahrungsliste;

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


public class NahrungsmittelBearbeitenOFFActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String tag = "creation";
    private TextView tV_offTopicBrb, pT_offKcalBrb, pT_offCarbsBrb, pT_offFetteBrb, pT_offEiweissBrb, pT_offMarkeBrb;
    private EditText pT_offAnzahlPrtBrb;
    private Button btn_offSpeichernBrb;

    private String id, marke, beschreibung, portionsgroesse, portionen,
            kcal, fette, carbs, eiweiss, einheit, portionsmenge,
            barcode, servingSize, quantitySize, kcal100g, carbs100g, fette100g, eiweiss100g,
            servingSizeNumber, ausgewSize;

    double kcalServing, carbsServing, fetteServing, eiweissServing;

    private String[] dd_items = {"100 g"};
    private String[] dd_items2 = {"100 g", "servingSize"};
    private AutoCompleteTextView dd_EinheitenBrb;
    private ArrayAdapter<String> adapterItems;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_offbearbeiten);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }

        tV_offTopicBrb = findViewById(R.id.tV_offTopicBrb);
        pT_offAnzahlPrtBrb = findViewById(R.id.pT_offAnzahlPrtBrb);
        pT_offKcalBrb = findViewById(R.id.pT_offKcalBrb);
        pT_offCarbsBrb = findViewById(R.id.pT_offCarbsBrb);
        pT_offFetteBrb = findViewById(R.id.pT_offFetteBrb);
        pT_offEiweissBrb = findViewById(R.id.pT_offEiweissBrb);
        pT_offMarkeBrb = findViewById(R.id.pT_offMarkeBrb);
        btn_offSpeichernBrb = findViewById(R.id.btn_offSpeichernBrb);
        dd_EinheitenBrb = findViewById(R.id.dd_EinheitenBrb);
        btn_offSpeichernBrb.setOnClickListener(this);

        getAndSetIntentData();
        chooseDropdownMenue();


        TextWatcher100gBrb();
        TextWatcherServingBrb();
        dropdownOnClick();
    }

    private void chooseDropdownMenue() {
        if (!Objects.equals(servingSize, "null")) {
            servingSizeNumber = servingSize.replaceAll("[^\\d]", "");
            kcalServing = Double.parseDouble(servingSizeNumber)/100 * Double.parseDouble(kcal100g);
            carbsServing = Double.parseDouble(servingSizeNumber)/100 * Double.parseDouble(carbs100g);
            fetteServing = Double.parseDouble(servingSizeNumber)/100 * Double.parseDouble(fette100g);
            eiweissServing = Double.parseDouble(servingSizeNumber)/100 * Double.parseDouble(eiweiss100g);
        }

        dd_EinheitenBrb.setText(ausgewSize);
        if (Objects.equals(servingSize, "null")) {  // Wenn servingSize leer ist, dann hat es keine ServingSize
            adapterItems = new ArrayAdapter<String>(NahrungsmittelBearbeitenOFFActivity.this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, dd_items);
            dd_EinheitenBrb.setAdapter(adapterItems);
        } else {
            dd_items2[0] = "100 g";
            dd_items2[1] = servingSize;
            adapterItems = new ArrayAdapter<String>(NahrungsmittelBearbeitenOFFActivity.this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, dd_items2);
            dd_EinheitenBrb.setAdapter(adapterItems);
        }
    }




    private void dropdownOnClick() {
        dd_EinheitenBrb.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                String item = adapterView.getItemAtPosition(position).toString();
                double anzahlPrt = Double.parseDouble(pT_offAnzahlPrtBrb.getText().toString());
                if (item.equals("100 g")) {
                    pT_offKcalBrb.setText(String.format(Locale.US, "%.2f", anzahlPrt * Double.parseDouble(kcal100g)));
                    pT_offCarbsBrb.setText(String.format(Locale.US, "%.2f", anzahlPrt * Double.parseDouble(carbs100g)));
                    pT_offFetteBrb.setText(String.format(Locale.US, "%.2f", anzahlPrt * Double.parseDouble(fette100g)));
                    pT_offEiweissBrb.setText(String.format(Locale.US, "%.2f", anzahlPrt * Double.parseDouble(eiweiss100g)));
                    TextWatcher100gBrb();
                }
                if (item.equals(servingSize)) {
                    pT_offKcalBrb.setText(String.format(Locale.US, "%.2f", kcalServing * anzahlPrt));
                    pT_offCarbsBrb.setText(String.format(Locale.US, "%.2f", carbsServing * anzahlPrt));
                    pT_offFetteBrb.setText(String.format(Locale.US, "%.2f", fetteServing * anzahlPrt));
                    pT_offEiweissBrb.setText(String.format(Locale.US, "%.2f", eiweissServing * anzahlPrt));
                    TextWatcherServingBrb();
                }
            }
        });
        TextWatcher100gBrb();
    }

    private void TextWatcher100gBrb() {
        pT_offAnzahlPrtBrb.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (dd_EinheitenBrb.getText().toString().equals("100 g")) {
                    if (!pT_offAnzahlPrtBrb.getText().toString().trim().equals("") &&
                            (!pT_offAnzahlPrtBrb.getText().toString().trim().equals("."))) {
                        double anzahlPrt = Double.parseDouble(pT_offAnzahlPrtBrb.getText().toString());
                        pT_offKcalBrb.setText(String.format(Locale.US, "%.2f", anzahlPrt * Double.parseDouble(kcal100g)));
                        pT_offCarbsBrb.setText(String.format(Locale.US, "%.2f", anzahlPrt * Double.parseDouble(carbs100g)));
                        pT_offFetteBrb.setText(String.format(Locale.US, "%.2f", anzahlPrt * Double.parseDouble(fette100g)));
                        pT_offEiweissBrb.setText(String.format(Locale.US, "%.2f", anzahlPrt * Double.parseDouble(eiweiss100g)));
                    }
                }
            }
            @Override
            public void afterTextChanged(Editable s) { }
        });
    }


    private void TextWatcherServingBrb() {
        pT_offAnzahlPrtBrb.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (dd_EinheitenBrb.getText().toString().equals(servingSize)) {
                    if (!pT_offAnzahlPrtBrb.getText().toString().trim().equals("") &&
                            (!pT_offAnzahlPrtBrb.getText().toString().trim().equals("."))) {
                        double anzahlPrt = Double.parseDouble(pT_offAnzahlPrtBrb.getText().toString());
                        pT_offKcalBrb.setText(String.format(Locale.US, "%.2f", kcalServing * anzahlPrt));
                        pT_offCarbsBrb.setText(String.format(Locale.US, "%.2f", carbsServing * anzahlPrt));
                        pT_offFetteBrb.setText(String.format(Locale.US, "%.2f", fetteServing * anzahlPrt));
                        pT_offEiweissBrb.setText(String.format(Locale.US, "%.2f", eiweissServing * anzahlPrt));
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
            portionen = getIntent().getStringExtra("portionen");
            kcal = getIntent().getStringExtra("kcal");
            carbs = getIntent().getStringExtra("carbs");
            fette = getIntent().getStringExtra("fette");
            eiweiss = getIntent().getStringExtra("eiweiss");
            portionsmenge = getIntent().getStringExtra("portionsmenge");
            barcode = getIntent().getStringExtra("barcode");
            servingSize = getIntent().getStringExtra("servingSize");
            quantitySize = getIntent().getStringExtra("quantitySize");
            kcal100g = getIntent().getStringExtra("kcal100g");
            carbs100g = getIntent().getStringExtra("carbs100g");
            fette100g = getIntent().getStringExtra("fette100g");
            eiweiss100g = getIntent().getStringExtra("eiweiss100g");
            ausgewSize = getIntent().getStringExtra("ausgewSize");

            // Setten
            tV_offTopicBrb.setText("\"" + beschreibung + "\"" + " bearbeiten");
            pT_offMarkeBrb.setText(marke);
            pT_offAnzahlPrtBrb.setText(portionen);
            pT_offKcalBrb.setText(kcal);
            pT_offCarbsBrb.setText(carbs);
            pT_offFetteBrb.setText(fette);
            pT_offEiweissBrb.setText(eiweiss);

        } else {
            Toast.makeText(this, "Keine Daten in Nahrungsliste", Toast.LENGTH_SHORT).show();
        }
    }


    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btn_offSpeichernBrb) {
            DBHelper dbHelper = new DBHelper(NahrungsmittelBearbeitenOFFActivity.this);

            if (!pT_offAnzahlPrtBrb.getText().toString().trim().equals("") && !pT_offAnzahlPrtBrb.getText().toString().trim().equals(".")) {
                // Teilt ausgewählte Einheit in Zahl und Buchstaben auf
                String portionsgroesse = dd_EinheitenBrb.getText().toString().trim();
                String portionsgroesseNumber = portionsgroesse.replaceAll("[^\\d.]+", "");  // Entfernt alle Zeichen
                String portionsgroesseLetters = portionsgroesse.replaceAll("[\\d.]", "").replaceAll("\\s+", ""); // Entfernt alle Zahlen und Leerzeichen

                // Rechnet Portionsmenge (anzahl Portionen * ausgewählte Portionseinheit)
                Double portionsmengeD = Double.parseDouble(portionsgroesseNumber) * Double.parseDouble(pT_offAnzahlPrtBrb.getText().toString());

                // Schneidet ggf. ".0" ab und fügt in beiden Fällen Portionsmenge mit Einheit zusammen
                if (String.valueOf(portionsmengeD).matches("\\d+\\.0")) {
                    int portionsmengeInteger = portionsmengeD.intValue();
                    portionsmenge = portionsmengeInteger + " " + portionsgroesseLetters;
                } else {
                    portionsmenge = portionsmengeD + " " + portionsgroesseLetters;
                }

                dbHelper.updateDataOff(id, portionsgroesseNumber, pT_offAnzahlPrtBrb.getText().toString(),
                        pT_offKcalBrb.getText().toString(), pT_offCarbsBrb.getText().toString(),
                        pT_offFetteBrb.getText().toString(), pT_offEiweissBrb.getText().toString(),
                        portionsgroesseLetters, portionsmenge, dd_EinheitenBrb.getText().toString());

                Intent intent = new Intent(this, NahrungslisteActivity.class);
                startActivity(intent);
                this.finish();
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