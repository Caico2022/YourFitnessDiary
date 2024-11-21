package com.studienarbeit.YourFitnessDiary.OFFBarcodescanner;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.studienarbeit.YourFitnessDiary.DBHelper;
import com.studienarbeit.YourFitnessDiary.Main.MainActivity;
import com.studienarbeit.YourFitnessDiary.Nahrungsliste.NahrungslisteActivity;
import com.studienarbeit.YourFitnessDiary.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map;

public class OpenFoodFactsActivity extends AppCompatActivity implements View.OnClickListener {

    public final String TAG = "creation";
    public final String TEST = "test";

    private TextView tV_offTopic, pT_offKcal, pT_offCarbs, pT_offFette, pT_offEiweiss, pT_offMarke;
    private EditText pT_offAnzahlPrt;
    private Button btn_offAufzeichnen;

    private String barcode, brands, productName, serving = "null", quantity = "null",
            barcodeAbfrage, servingSize = "null", quantitySize = "null", portionsmenge;
    private Double servingD, quantityD;
    private String[][] nutriments100g, nutrimentsServing;
    private String[] nutri100g, nutriServing;
    private double[] nutri100gD, nutriServingD, nutriQuantity;

    private String[] dd_items100g = {"100 g"};
    private String[] dd_items100gUndServing = {"100 g", "serving"};
    private AutoCompleteTextView dd_Einheiten;
    private ArrayAdapter<String> adapterItems;

    private LinearLayout layout_ladescreen, layout_off;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_open_food_facts);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }

        tV_offTopic = findViewById(R.id.tV_offTopic);
        pT_offAnzahlPrt = findViewById(R.id.pT_offAnzahlPrt);
        pT_offKcal = findViewById(R.id.pT_offKcal);
        pT_offCarbs = findViewById(R.id.pT_offCarbs);
        pT_offFette = findViewById(R.id.pT_offFette);
        pT_offEiweiss = findViewById(R.id.pT_offEiweiss);
        pT_offMarke = findViewById(R.id.pT_offMarke);
        btn_offAufzeichnen = findViewById(R.id.btn_offAufzeichnen);
        dd_Einheiten = findViewById(R.id.dd_Einheiten);
        btn_offAufzeichnen.setOnClickListener(this);
        Log.w("creation", "21");

        // Ladescreen anzeigen
        layout_ladescreen = findViewById(R.id.layout_ladescreen);
        layout_off = findViewById(R.id.layout_off);

        openFoodFactsJSONRequest(new OpenFoodFactsCallback() {
            @Override
            public void onSuccess(JSONObject response) {
                layout_ladescreen.setVisibility(View.GONE);
                layout_off.setVisibility(View.VISIBLE);

                // nutri100g = (carbs, kcal, fett, eiweiss)
                TextWatcher100g();

                // Wenn ein Item bei der AutoCompleteTextView geklickt wird
                dd_Einheiten.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                        String item = adapterView.getItemAtPosition(position).toString();
                        double anzahlPrt = Double.parseDouble(pT_offAnzahlPrt.getText().toString());
                        if (item.equals("100 g")) {
                            pT_offKcal.setText(String.format(Locale.US, "%.2f", anzahlPrt * nutri100gD[1]));
                            pT_offCarbs.setText(String.format(Locale.US, "%.2f", anzahlPrt * nutri100gD[0]));
                            pT_offFette.setText(String.format(Locale.US, "%.2f", anzahlPrt * nutri100gD[2]));
                            pT_offEiweiss.setText(String.format(Locale.US, "%.2f", anzahlPrt * nutri100gD[3]));
                            TextWatcher100g();
                        }
                        if (item.equals(serving)) {
                            pT_offKcal.setText(String.format(Locale.US, "%.2f", nutriServingD[1] * anzahlPrt));
                            pT_offCarbs.setText(String.format(Locale.US, "%.2f", nutriServingD[0] * anzahlPrt));
                            pT_offFette.setText(String.format(Locale.US, "%.2f", nutriServingD[2] * anzahlPrt));
                            pT_offEiweiss.setText(String.format(Locale.US, "%.2f", nutriServingD[3] * anzahlPrt));
                            TextWatcherServing();
                        }
                    }
                });
            }

            @Override
            public void onError(VolleyError error) {
            }
        });

    }

    private void TextWatcher100g() {
        pT_offAnzahlPrt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String selectedItem = dd_Einheiten.getText().toString();
                if (selectedItem.equals("100 g")) {
                    if (!pT_offAnzahlPrt.getText().toString().trim().equals("") && (!pT_offAnzahlPrt.getText().toString().trim().equals("."))) {
                        double anzahlPrt = Double.parseDouble(pT_offAnzahlPrt.getText().toString().trim());
                        pT_offKcal.setText(String.format(Locale.US, "%.2f", anzahlPrt * nutri100gD[1]));
                        pT_offCarbs.setText(String.format(Locale.US, "%.2f", anzahlPrt * nutri100gD[0]));
                        pT_offFette.setText(String.format(Locale.US, "%.2f", anzahlPrt * nutri100gD[2]));
                        pT_offEiweiss.setText(String.format(Locale.US, "%.2f", anzahlPrt * nutri100gD[3]));
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });
    }


    public interface OpenFoodFactsCallback {
        void onSuccess(JSONObject response);
        void onError(VolleyError error);
    }


    private void TextWatcherServing() {
        pT_offAnzahlPrt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String selectedItem = dd_Einheiten.getText().toString();
                if (selectedItem.equals(serving)) {
                    if (!pT_offAnzahlPrt.getText().toString().trim().equals("") && (!pT_offAnzahlPrt.getText().toString().trim().equals("."))) {
                        double anzahlPrt = Double.parseDouble(pT_offAnzahlPrt.getText().toString().trim());
                        pT_offKcal.setText(String.format(Locale.US, "%.2f", nutriServingD[1] * anzahlPrt));
                        pT_offCarbs.setText(String.format(Locale.US, "%.2f", nutriServingD[0] * anzahlPrt));
                        pT_offFette.setText(String.format(Locale.US, "%.2f", nutriServingD[2] * anzahlPrt));
                        pT_offEiweiss.setText(String.format(Locale.US, "%.2f", nutriServingD[3] * anzahlPrt));
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });
    }


    private void openFoodFactsJSONRequest(OpenFoodFactsCallback callback) {
        //barcode = "8712429350100";

        /*https://jsonformatter.org/json-viewer
        https://world.openfoodfacts.org/api/v2/product/*/

        /* API-Anfrage, um Daten vom Server abzurufen (GET), senden (POST), aktualisieren (PUT), löschen (DELETE)
        json = JavaScript Object Notation, strukturiertes Format, um Daten zwischen Server und Client auszutauschen */
                /* StringRequest ist eine Klasse in der Bib 'Volley', sendet HTTP-Anfrage und gibt String als Antwort zurück
        new Response.Listener<String>() = Erste anonyme innere Klasse, um die Antwort vom Server auf die
                                          HTTP-Anfrage zu verarbeiten, empfangene Antwort kann hier verarbeitet werden */
        Intent intent = getIntent();
        barcode = intent.getStringExtra("barcode");
        String url = "https://world.openfoodfacts.org/api/v2/product/" + barcode + ".json";
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    barcodeAbfrage = response.getString("code");
                    JSONObject productObject = response.getJSONObject("product");
                    nutriments100g = new String[4][2];
                    nutrimentsServing = new String[4][2];
                    if (productObject.has("brands")) {
                        brands = productObject.getString("brands");
                    } else {
                        brands = "Keine Marke gefunden";
                    }

                    try {
                        productName = productObject.getString("product_name_de");
                    } catch (JSONException exceptionProductName0) {
                        try {
                            productName = productObject.getString("product_name");
                        } catch (JSONException exceptionProductName1) {
                            try {
                                productName = productObject.getString("product_name_en");
                            } catch (JSONException exceptionProductName2) {
                                try {
                                    productName = productObject.getString("product_name_fr");
                                } catch (JSONException exceptionProductName3) {
                                    try {
                                        productName = productObject.getString("product_name_it");
                                    } catch (JSONException exceptionProductName5) {
                                        productName = "Keine Produktbeschreibung gefunden";
                                    }
                                }
                            }
                        }
                    }
                    try {
                        serving = productObject.getString("serving_size");
                    } catch (JSONException exceptionServing) {
                        serving = "null";
                    }

                    //**************************** NUTRIMENTS ****************************
                    JSONObject nutriments = productObject.getJSONObject("nutriments");

                    // Prüfen, ob es nutriments100g und nutrimentsServing gibt
                    boolean nutriments100gBool = nutriments.has("energy-kcal_100g");
                    boolean nutrimentsServingBool = nutriments.has("energy-kcal_serving");

                    // Wenn es nutriments100g gibt, aber KEINE nutrimentsServing
                    if (nutriments100gBool && !nutrimentsServingBool) {
                        Log.i(TAG, "servingSize nutriments NICHT gefunden");
                        Iterator<String> iterator = nutriments.keys();
                        int i = 0;
                        while (iterator.hasNext()) {  // Alle Schlüssel iterieren
                            String key = iterator.next();
                            if (key.equals("energy-kcal_100g") || key.equals("carbohydrates_100g") || key.equals("fat_100g") || key.equals("proteins_100g")) {
                                String value = nutriments.getString(key);
                                nutriments100g[i][0] = key;
                                nutriments100g[i][1] = value;
                                i++;
                            }
                        }
                        serving = "null";
                        adapterItems = new ArrayAdapter<String>(OpenFoodFactsActivity.this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, dd_items100g);
                        dd_Einheiten.setAdapter(adapterItems);
                    }


                    // Wenn es nutriments100g UND nutrimentsServing gibt
                    if (nutriments100gBool && nutrimentsServingBool) {
                        Log.i(TAG, "servingSize nutriments gefunden");

                                /* keys() = gibt Iterator zurück, der die Schlüssel der JSON-Objekte in der
                                Reihenfolge durchläuft, in der sie im JSON-Objekt definiert sind */
                        Iterator<String> iterator = nutriments.keys();
                        int i = 0;
                        int j = 0;
                        while (iterator.hasNext()) {  // Alle Schlüssel iterieren
                            String key = iterator.next();
                            if (key.equals("energy-kcal_100g") || key.equals("carbohydrates_100g") || key.equals("fat_100g") || key.equals("proteins_100g")) {
                                String value = nutriments.getString(key);
                                nutriments100g[i][0] = key;  // i = key an Arrayposition 0
                                nutriments100g[i][1] = value;  // i = value an Arrayposition 1
                                i++;
                            }
                            if (key.equals("energy-kcal_serving") || key.equals("carbohydrates_serving") || key.equals("fat_serving") || key.equals("proteins_serving")) {
                                String value = nutriments.getString(key);
                                nutrimentsServing[j][0] = key;  // i = key an Arrayposition 0
                                nutrimentsServing[j][1] = value;  // i = value an Arrayposition 1
                                j++;
                            }
                        }
                        dd_items100gUndServing[1] = serving;
                        adapterItems = new ArrayAdapter<String>(OpenFoodFactsActivity.this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, dd_items100gUndServing);
                        dd_Einheiten.setAdapter(adapterItems);
                    }

                    // Wenn es keine nutriments100g gibt
                    if (!nutriments100gBool) {
                        // Jeder Position "0" zuweisen, damit getAndSetIntentData_off() es zu double konvertieren kann
                        for (String[] strings : nutriments100g) {
                            Arrays.fill(strings, "0");
                        }

                        serving = "null";
                        adapterItems = new ArrayAdapter<String>(OpenFoodFactsActivity.this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, dd_items100g);
                        dd_Einheiten.setAdapter(adapterItems);
                    }

                    nutrimentsOrganizer(brands, productName, serving, nutriments100g, nutrimentsServing);

                } catch (JSONException error) {  // Wenn kein Strichcode zur Anfrage gefunden wurde
                    Log.e(TAG, "JSONException : " + error);
                    error.printStackTrace();
                    UngueltigerStrichcodePopup ungueltigerStrichcodePopup = new UngueltigerStrichcodePopup(OpenFoodFactsActivity.this);
                    ungueltigerStrichcodePopup.show();
                }
                callback.onSuccess(response);
            }  // onResponse
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {  // Bei Problemen mit dem Internet oder Server
                Log.e(TAG, "VolleyError : " + error);
                error.printStackTrace();
                InternetFailPopup internetFailPopup = new InternetFailPopup(OpenFoodFactsActivity.this);
                internetFailPopup.show();
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = super.getHeaders();
                if (headers == null || headers.equals(Collections.emptyMap())) {
                    headers = new HashMap<>();
                }
                // Schlüssel = "User-Agent", Wert = "YourFitnessDiary (Study work) - Android - Version 1.0"
                headers.put("User-Agent", "YourFitnessDiary (Study work) - Android - Version 1.0");
                return headers;
            }
        };
        RequestQueue queue = Volley.newRequestQueue(this);
        queue.add(jsonObjectRequest);
        // 'Volley' = Bib von Google, vereinfacht das Senden von HTTP-Anfragen
        // HTTP-Anfrage wird der RequestQueue (Anfrage-Warteschlange) mit 'add' hinzugefügt

        // 1) stringRequest wird erstellt und an RequestQueue (Warteschlange) angehängt
        // 2) queue.add(stringRequest); sendet HTTP-Anfrage an Server (RequestQueue prüft Warteschlange regelmäßig)
        // 3) Callback-Methode 'onResponse' oder 'onErrorResponse' wird aufgerufen

    }


    private void nutrimentsOrganizer(String brands, String product_name, String serving, String[][] nutriments100g, String[][] nutrimentsServing) {

        // Schlüsselnamen von nutri100g entfernen
        nutri100g = new String[4];
        for (int i = 0; i < nutriments100g.length; i++) {
            nutri100g[i] = nutriments100g[i][1];
        }

        // Falls nutriServing leer, dann kürzen, sonst Schlüsselnamen entfernen
        nutriServing = new String[4];
        if (nutrimentsServing[0][0] == null) {
            for (int i = 0; i < nutrimentsServing.length; i++) {
                nutriServing[i] = null;
            }
        } else {
            for (int i = 0; i < nutrimentsServing.length; i++) {
                nutriServing[i] = nutrimentsServing[i][1];
            }
        }


        setData(brands, product_name, serving, nutri100g, nutriServing, nutriQuantity);

    }

    private void setData(String brands, String product_name, String serving, String[] nutri100g, String[] nutriServing, double[] nutriQuantity) {
        // nutri100g = (carbs, kcal, fett, eiweiss)
        pT_offMarke.setText(brands);
        tV_offTopic.setText(product_name);
        pT_offKcal.setText(nutri100g[1]);
        pT_offCarbs.setText(nutri100g[0]);
        pT_offFette.setText(nutri100g[2]);
        pT_offEiweiss.setText(nutri100g[3]);

        // nutri100g und nutriServing von String[] zu double[]
        nutri100gD = new double[nutri100g.length];
        for (int i = 0; i < nutri100g.length; i++) {
            nutri100gD[i] = Double.parseDouble(nutri100g[i]);
        }
        if (nutriServing[0] != null) {
            nutriServingD = new double[nutriServing.length];
            for (int i = 0; i < nutriServing.length; i++) {
                nutriServingD[i] = Double.parseDouble(nutriServing[i]);
            }
        }

    }


    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btn_offAufzeichnen) {
            DBHelper dbHelper = new DBHelper(OpenFoodFactsActivity.this);
            if (!pT_offAnzahlPrt.getText().toString().trim().equals("") && !pT_offAnzahlPrt.getText().toString().trim().equals(".")) {

                // Teilt ausgewählte Einheit in Zahl und Buchstaben auf
                String portionsgroesse = dd_Einheiten.getText().toString().trim();
                String portionsgroesseNumber = portionsgroesse.replaceAll("[^\\d.]+", "");  // Entfernt alle Zeichen
                String portionsgroesseLetters = portionsgroesse.replaceAll("[\\d.]", "").replaceAll("\\s+", ""); // Entfernt alle Zahlen und Leerzeichen


                // Rechnet Portionsmenge (anzahl Portionen * ausgewählte Portionseinheit)
                Double portionsmengeD = Double.parseDouble(portionsgroesseNumber) * Double.parseDouble(pT_offAnzahlPrt.getText().toString());
                String portionsmengeString = String.valueOf(portionsmengeD);

                // Schneidet ggf. ".0" ab und fügt in beiden Fällen Portionsmenge mit Einheit zusammen
                if (portionsmengeString.matches("\\d+\\.0")) {
                    int portionsmengeInteger = portionsmengeD.intValue();
                    portionsmenge = portionsmengeInteger + " " + portionsgroesseLetters;
                } else {
                    portionsmenge = portionsmengeD + " " + portionsgroesseLetters;
                }


                servingSize = serving;


                if (dbHelper.checkIfBarcodeExists(barcodeAbfrage) == 1) {  // Artikel bereits in der Nahrungsliste
                    Toast.makeText(this, "Der Artikel befindet sich bereits in deiner " + "Nahrungsliste, wurde aber zur Tagesliste hinzugefügt", Toast.LENGTH_SHORT).show();
                    // Sucht mit übergebenem Barcode die ID dazu raus
                    String ID = String.valueOf(dbHelper.getIDFromBarcode(barcodeAbfrage));
                    dbHelper.addNahrungMainOff(ID, pT_offMarke.getText().toString().trim(), tV_offTopic.getText().toString().trim(), portionsgroesseNumber, pT_offAnzahlPrt.getText().toString().trim(), pT_offKcal.getText().toString().trim(), pT_offCarbs.getText().toString().trim(), pT_offFette.getText().toString().trim(), pT_offEiweiss.getText().toString().trim(), portionsgroesseLetters, portionsmenge, pT_offKcal.getText().toString().trim(), pT_offCarbs.getText().toString().trim(), pT_offFette.getText().toString().trim(), pT_offEiweiss.getText().toString().trim(), barcodeAbfrage, servingSize, quantitySize, nutri100g[1], nutri100g[0], nutri100g[2], nutri100g[3], dd_Einheiten.getText().toString().trim());

                    Intent intent = new Intent(this, MainActivity.class);
                    startActivity(intent);
                    this.finish();

                } else {  // Artikel noch nicht in der Nahrungsliste
                    Log.e(TAG, dd_Einheiten.getText().toString().trim());
                    dbHelper.addNahrungOff(pT_offMarke.getText().toString(), tV_offTopic.getText().toString().trim(), portionsgroesseNumber, pT_offAnzahlPrt.getText().toString(), pT_offKcal.getText().toString(), pT_offCarbs.getText().toString(), pT_offFette.getText().toString(), pT_offEiweiss.getText().toString(), portionsgroesseLetters, portionsmenge, barcodeAbfrage, servingSize, quantitySize, nutri100g[1], nutri100g[0], nutri100g[2], nutri100g[3], dd_Einheiten.getText().toString().trim());

                    String ID = dbHelper.getMaxIdNahrungsliste();
                    dbHelper.addNahrungMainOff(ID, pT_offMarke.getText().toString().trim(), tV_offTopic.getText().toString().trim(), portionsgroesseNumber, pT_offAnzahlPrt.getText().toString().trim(), pT_offKcal.getText().toString().trim(), pT_offCarbs.getText().toString().trim(), pT_offFette.getText().toString().trim(), pT_offEiweiss.getText().toString().trim(), portionsgroesseLetters, portionsmenge, pT_offKcal.getText().toString().trim(), pT_offCarbs.getText().toString().trim(), pT_offFette.getText().toString().trim(), pT_offEiweiss.getText().toString().trim(), barcodeAbfrage, servingSize, quantitySize, nutri100g[1], nutri100g[0], nutri100g[2], nutri100g[3], dd_Einheiten.getText().toString().trim());

                    Toast.makeText(this, "Der Artikel wurde der Nahrungsliste " + "und der Tagesliste hinzugefügt", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(this, MainActivity.class);
                    startActivity(intent);
                    this.finish();
                }
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