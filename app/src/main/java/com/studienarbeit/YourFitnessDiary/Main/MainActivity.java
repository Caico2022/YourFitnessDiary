package com.studienarbeit.YourFitnessDiary.Main;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.fitness.Fitness;
import com.google.android.gms.fitness.FitnessOptions;
import com.google.android.gms.fitness.data.Bucket;
import com.google.android.gms.fitness.data.DataPoint;
import com.google.android.gms.fitness.data.DataSet;
import com.google.android.gms.fitness.data.DataType;
import com.google.android.gms.fitness.data.Field;
import com.google.android.gms.fitness.request.DataReadRequest;
import com.studienarbeit.YourFitnessDiary.DBHelper;
import com.studienarbeit.YourFitnessDiary.GetterSetter.GetterSetterNahrungsmittel;
import com.studienarbeit.YourFitnessDiary.GetterSetter.GetterSetterTaetigkeiten;
import com.studienarbeit.YourFitnessDiary.GoogleSchrittzähler.GoogleLoginScreenActivity;
import com.studienarbeit.YourFitnessDiary.GoogleSchrittzähler.GoogleSchrittzaehlerActivity;
import com.studienarbeit.YourFitnessDiary.Adapter.MainAdapter;
import com.studienarbeit.YourFitnessDiary.Nahrungsliste.NahrungslisteActivity;
import com.studienarbeit.YourFitnessDiary.R;
import com.studienarbeit.YourFitnessDiary.Adapter.TaetigkeitenlisteAdapter;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    DBHelper dbHelper = new DBHelper(MainActivity.this);

    private TextView tVmain_carbs, tVmain_fette, tVmain_eiweiss, tVmain_KcalGes, tV_kcalUebrig;
    private Button btn_Nahrunghinzu, btn_verbrKcalHinzu, btn_zuSchrittzaehler;
    private RecyclerView rV_main, rV_TaetigkeitenMain;
    private ArrayList<GetterSetterNahrungsmittel> nahrungList= new ArrayList<>();
    private MainAdapter mainAdapter;
    private TaetigkeitenlisteAdapter taetigkeitenlisteAdapter;
    private ArrayList<GetterSetterTaetigkeiten> taetigkeitenlist = new ArrayList<>();
    private ImageView imgV_fertig, imgV_kcalRechner, imgV_ziel;
    private LinearLayout layout_emptyDataMain;
    private ScrollView scrollview_main;
    private TextView tV_dailyStepsMain, tV_dailyKcalMain, tVmain_kcalverbrauch, tVmain_differenz;
    private ConstraintLayout constraintLayout_circleBarSteps, constraintLayout_circleBarKcal;
    private ProgressBar circleProgressBar_dailyStepsMain, circleProgressBar_dailyKcalMain;
    private FitnessOptions fitnessOptions;
    private static final int UPDATE_INTERVAL_MS = 5000;

    // Für Nährwerterechner
    private String kcalKonsum;
    private String carbs;
    private String fette;
    private String eiweiss;
    private String kcalVerbrauch;
    private String kcaldifferenz;
    double[] kalorienArray, carbsArray, fetteArray, eiweissArray, portionenArray;
    double kcaldifferenzD = 0;
    double uebrigeKcalD = 0;

    // Für passiven Verbrauchsrechner:
    private String idUd="null", geschlechtS="null", gewichtS="null", alterS="null", groesseS="null",
            schritteS="null", kalorienzufuhrS="null", sportS="null";
    double gewichtD, alterD, groesseD, kalorienzufuhrD, sportD, schritteIVerbrauchD, ergebnisD;
    int schritteI;

    private final Handler handlerSteps = new Handler();

    private TextView tV_vortagKonsumKcal, tV_vortagVerbrKcal, tV_vortagDiff, tV_vortagSteps, tV_ziel;

    private String yKonsumKcal = "0", yVerbrauchKcal = "0", yDifferenz = "0", ySchritte = "0";

    private static final String TAG = "creation";
    String ziel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }
        circleProgressBar_dailyStepsMain = findViewById(R.id.circleProgressBar_dailyStepsMain);
        tV_ziel = findViewById(R.id.tV_ziel);
        tV_kcalUebrig = findViewById(R.id.tV_kcalUebrig);
        imgV_ziel = findViewById(R.id.imgV_ziel);
        tV_vortagKonsumKcal = findViewById(R.id.tV_vortagKonsumKcal);
        tV_vortagVerbrKcal = findViewById(R.id.tV_vortagVerbrKcal);
        tV_vortagDiff = findViewById(R.id.tV_vortagDiff);
        tV_vortagSteps = findViewById(R.id.tV_vortagSteps);
        scrollview_main = findViewById(R.id.scrollview_main);
        layout_emptyDataMain = findViewById(R.id.layout_emptyDataMain);
        tVmain_differenz = findViewById(R.id.tVmain_differenz);
        tVmain_carbs = findViewById(R.id.tVmain_carbs);
        tVmain_carbs = findViewById(R.id.tVmain_carbs);
        tVmain_fette = findViewById(R.id.tVmain_fette);
        tVmain_eiweiss = findViewById(R.id.tVmain_eiweiss);
        tVmain_KcalGes = findViewById(R.id.tVmain_KcalGes);
        btn_Nahrunghinzu = findViewById(R.id.btnMain_Nahrunghinzu);
        rV_main = findViewById(R.id.rV_main);
        rV_TaetigkeitenMain = findViewById(R.id.rV_TaetigkeitenMain);
        btn_verbrKcalHinzu = findViewById(R.id.btn_verbrKcalHinzu);
        imgV_fertig = findViewById(R.id.imgV_fertig);
        btn_zuSchrittzaehler = findViewById(R.id.btn_zuSchrittzähler);
        circleProgressBar_dailyStepsMain = findViewById(R.id.circleProgressBar_dailyStepsMain);
        tV_dailyStepsMain = findViewById(R.id.tV_dailyStepsMain);
        constraintLayout_circleBarSteps = findViewById(R.id.constraintLayout_circleBarSteps);
        constraintLayout_circleBarKcal = findViewById(R.id.constraintLayout_circleBarKcal);
        tV_dailyKcalMain = findViewById(R.id.tV_dailyKcalMain);
        circleProgressBar_dailyKcalMain = findViewById(R.id.circleProgressBar_dailyKcalMain);
        imgV_kcalRechner = findViewById(R.id.imgV_kcalRechner);
        tVmain_kcalverbrauch = findViewById(R.id.tVmain_kcalverbrauch);
        imgV_ziel.setOnClickListener(this);
        imgV_fertig.setOnClickListener(this);
        btn_verbrKcalHinzu.setOnClickListener(this);
        btn_Nahrunghinzu.setOnClickListener(this);
        btn_zuSchrittzaehler.setOnClickListener(this);
        constraintLayout_circleBarSteps.setOnClickListener(this);
        constraintLayout_circleBarKcal.setOnClickListener(this);
        imgV_kcalRechner.setOnClickListener(this);

        mainAdapter = new MainAdapter(MainActivity.this, this, nahrungList);
        rV_main.setAdapter(mainAdapter);
        rV_main.setLayoutManager(new LinearLayoutManager(MainActivity.this));
        taetigkeitenlisteAdapter = new TaetigkeitenlisteAdapter(MainActivity.this, this, taetigkeitenlist);
        rV_TaetigkeitenMain.setAdapter(taetigkeitenlisteAdapter);
        rV_TaetigkeitenMain.setLayoutManager(new LinearLayoutManager(MainActivity.this));

        readNaehrwerteAndSet();
        createDummyUser();
        readYesterdayData();
        createAndSafeStartimeStepcounter();

        swipeToDelete();
        swipeToDeleteTaet();
    }


    // Kalorienverbrauch berechnen und anzeigen
    // Bedingungen: dummy-Userdata (onCreate) oder Userdata, Schritte (Display), Kalorienkonsum (onCreate)
    private void kalorienverbrauchsrechner() {
        // Benutzerdaten von Datenbank abfragen
        String maxID3 = dbHelper.getMaxIdUserdata();
        Cursor cursor3 = dbHelper.readUserdataBody(maxID3);
        int s = 0;
        if (cursor3.moveToFirst()) {
            do {
                idUd = cursor3.getString(0); // ID
                geschlechtS = cursor3.getString(1); // geschlecht
                gewichtS = cursor3.getString(2); // gewicht
                alterS = cursor3.getString(3); // alter
                groesseS = cursor3.getString(4); // groesse
                schritteS = cursor3.getString(5); // schritte
                kalorienzufuhrS = cursor3.getString(6); // kalorienzufuhr
                sportS = cursor3.getString(7); // sport
                s++;
            } while (cursor3.moveToNext());
        }
        cursor3.close();

        // .addUserdata fehlgeschlagen, speichert trotzdem die Werte, aber beim Ersten abrufen sind sie null
        if (geschlechtS.equals("null")) {
            geschlechtS = "Männlich";
            gewichtS = "80";
            alterS = "25";
            groesseS = "180";
            schritteS = "0";
            kalorienzufuhrS = "0";
            sportS = "0";
        }
        //  Alle Strings zu Double konvertieren
        gewichtD = Double.parseDouble(gewichtS);
        alterD = Double.parseDouble(alterS);
        groesseD = Double.parseDouble(groesseS);
        schritteI = Integer.parseInt(tV_dailyStepsMain.getText().toString().trim());
        kalorienzufuhrD = Double.parseDouble(kcalKonsum);
        // Aktivitäten von Datenbank abfragen und aufsummieren
        String[] sportA = dbHelper.getAllVerbrKcalMainTaet();
        sportD = 0.0;
        for (String value : sportA) {
            double num = Double.parseDouble(value);
            sportD += num;
        }
        sportS = String.valueOf(sportD);
        sportD = Double.parseDouble(sportS);

        // Formeln zur Berechnung der verbrauchten Kalorien der Schritte und Gesamt
        if (geschlechtS.equals("Männlich")) {
           //schritteIVerbrauchD = ((0.0005 * gewichtD) + (0.0009 * groesseD) - (0.0024 * alterD) + 0.324) / 10;
            ergebnisD = (10 * gewichtD) + (6.25 * groesseD) - (5 * alterD) + 5
                    + 0.04 * schritteI + kalorienzufuhrD / 10 + sportD;
        }
        if (geschlechtS.equals("Weiblich")) {
            //schritteIVerbrauchD = ((0.0004 * gewichtD) + (0.0008 * groesseD) - (0.0023 * alterD) + 0.372)/10;
            ergebnisD = (10 * gewichtD) + (6.25 * groesseD) - (5 * alterD) - 161
                    + 0.04 * schritteI + kalorienzufuhrD/10 + sportD;
            }

        tVmain_kcalverbrauch.setText(String.format(Locale.US, "%.1f", ergebnisD));
    }

    // Kcaldiff, Zieldiff und uebrigeKcal berechnen und anzeigen
    // Bedingungen: Kalorienverbrauch (Display), Zielkaloriendifferenz (Display), Kalorienkonsum (onCreate)
    private void kaloriendifferenzenrechner() {
        // Gesamtkaloriendifferenz berechnen und anzeigen (ist GONE)
        kcalVerbrauch = tVmain_kcalverbrauch.getText().toString().trim();
        if (kcalVerbrauch.length() > 0) {
            kcaldifferenzD = (Double.parseDouble(kcalKonsum) - Double.parseDouble(kcalVerbrauch));
        }
        kcaldifferenzD = Math.round(kcaldifferenzD * 100.0) / 100.0;
        kcaldifferenz = String.valueOf(kcaldifferenzD);
        tVmain_differenz.setText(kcaldifferenz);
        if (tVmain_differenz.getText().toString().equals("0.0")) {
            tVmain_differenz.setText("0");
        } else {
            tVmain_differenz.setText(kcaldifferenz);
        }

        // Übrige Kcal berechnen und setzen (Differenz + Zieldifferenz)
        if (!tVmain_differenz.getText().toString().isEmpty() && !tV_ziel.getText().toString().isEmpty()) {
            String diff = tVmain_differenz.getText().toString().trim();
            String zielDiff = tV_ziel.getText().toString().trim();
            double diffD = Double.parseDouble(diff);
            double zielDiffD = Double.parseDouble(zielDiff);

            // Noch zu essen
            uebrigeKcalD = Double.parseDouble(kcalVerbrauch) + zielDiffD - Double.parseDouble(kcalKonsum);
            uebrigeKcalD = Math.round(uebrigeKcalD * 100) / 100;
            tV_kcalUebrig.setText(String.valueOf(uebrigeKcalD));
        } else {
            tV_kcalUebrig.setText(0);
        }
    }



    // Zeigt die Schrittzahl an bis resetet wird
    @SuppressLint("SetTextI18n")
    private void displayStepsSinceReset(ZonedDateTime startTime) {
        ZonedDateTime endTime = ZonedDateTime.now(ZoneId.systemDefault()).plusSeconds(1);
        Log.i(TAG, "Range Start: " + startTime);
        Log.i(TAG, "Range End: " + endTime);
        DataReadRequest readRequest = new DataReadRequest.Builder()
                .aggregate(DataType.TYPE_STEP_COUNT_DELTA)
                .bucketByTime(1, TimeUnit.DAYS)
                .setTimeRange(startTime.toEpochSecond(), endTime.toEpochSecond(), TimeUnit.SECONDS)
                .build();
        Fitness.getHistoryClient(this, GoogleSignIn.getAccountForExtension(this, fitnessOptions))
                .readData(readRequest)
                .addOnSuccessListener (response -> {
                    int totalSteps = 0;
                    // Jeder Bucket ist 1 Tag und enthält DataSets
                    for (Bucket bucket : response.getBuckets()) {
                        // Jedes DataSet enthält eine Stunde an Daten
                        for (DataSet dataSet : bucket.getDataSets()) {
                            // Jeder DataPoint enthält 15min an Daten
                            for (DataPoint dp : dataSet.getDataPoints()) {
                                // Extrahiert alle Daten vom DataPoint des angegebenen Feldes (TYPE_STEP_COUNT_DELTA)
                                for (Field field : dp.getDataType().getFields()) {
                                    int stepsValue = dp.getValue(field).asInt();
                                    totalSteps += stepsValue;
                                    String stepsString = Integer.toString(stepsValue);
                                    Log.i(TAG, "\tField: " + field.getName() + " Value: " + stepsString);
                                }
                            }
                        }
                    }
                    Log.i(TAG, "Total steps : " + totalSteps);
                    tV_dailyStepsMain.setText(Integer.toString(totalSteps));
                    circleProgressBar_dailyStepsMain.setProgress(totalSteps);
                })
                .addOnFailureListener(e ->
                        Log.e(TAG, "There was an error reading data from Google Fit", e));
    }

/*    private void dumpStepsSinceReset(DataSet dataSet) {
        if (dataSet.isEmpty()) {
            Log.e(TAG, "Dataset ist leer");
            return;
        }
        Log.i(TAG, "Data returned for Data type: " + dataSet.getDataType().getName());
        for (DataPoint dp : dataSet.getDataPoints()) {
            for (Field field : dp.getDataType().getFields()) {
                int stepsValue = dp.getValue(field).asInt(); // Schrittzahl als int-Wert extrahieren
                String stepsString = Integer.toString(stepsValue); // int-Wert in eine Zeichenfolge konvertieren
                Log.i(TAG, "\tField: " + field.getName() + " Value: " + stepsString);
                tV_dailyStepsMain.setText(stepsString);
                circleProgressBar_dailyStepsMain.setProgress(Integer.parseInt(stepsString));
            }
        }
    }*/

/*    // Zeigt die verbrannten Kcal im TextView an vom heutigen Tag
    private void displayDailyKcal() {
        Fitness.getHistoryClient(this, GoogleSignIn.getAccountForExtension(this, fitnessOptions))
                .readDailyTotal(DataType.TYPE_CALORIES_EXPENDED)
                .addOnSuccessListener(dataSet -> {
                    float totalCalories = dataSet.isEmpty()
                            ? 0
                            : dataSet.getDataPoints().get(0).getValue(Field.FIELD_CALORIES).asFloat();

                    tV_dailyKcalMain.setText(String.format(Locale.US, "%.2f", totalCalories));
                    circleProgressBar_dailyKcalMain.setProgress((int) totalCalories);
                })
                .addOnFailureListener(e ->
                        Log.w(TAG, "There was an error reading daily calorie count data", e));

    }*/

    // anonyme, innere Klasse
    private final Runnable runnableSteps = new Runnable() {
        @Override
        public void run() {
            checkFirstStartAndRunDisplaySteps();
            handlerSteps.postDelayed(this, UPDATE_INTERVAL_MS);
        }
    };

    public void checkFirstStartAndRunDisplaySteps() {
        SharedPreferences prefs = MainActivity.this.getSharedPreferences
                ("MyPrefs.StepCounterResetTime", GoogleSchrittzaehlerActivity.MODE_PRIVATE);
        String startTimeS = prefs.getString("stepsResetTime", "FirstStart");

        // Überprüft, ob die Tagesliste schon mal abgeschlossen wurde mit empfangener SP
        if (startTimeS.equals("FirstStart")) {
            // Startzeit empfangen und übergeben
            SharedPreferences prefs2 = MainActivity.this.getSharedPreferences("MyPrefs.MainActivityStepCounterFirstStartTime", MainActivity.MODE_PRIVATE);
            String startTimeFirstStartS = prefs2.getString("stepCounterFirstStartTime", startTimeS);

            ZonedDateTime startTimeFirstStart = ZonedDateTime.parse(startTimeFirstStartS);
            displayStepsSinceReset(startTimeFirstStart);
        } else {
            ZonedDateTime startTime = ZonedDateTime.parse(startTimeS);
            displayStepsSinceReset(startTime);
        }
    }


/*    private final Runnable runnableKcal = new Runnable() {
        @Override
        public void run() {
            //displayDailyKcal();
            handlerKcal.postDelayed(this, UPDATE_INTERVAL_MS);
        }
    };*/

    private void startUpdatingStepCount() {
        handlerSteps.post(runnableSteps);
    }
    private void stopUpdatingStepCount() {
        handlerSteps.removeCallbacks(runnableSteps);
    }
/*    private void startUpdatingKcalCount() {
        handlerKcal.post(runnableKcal);
    }
    private void stopUpdatingKcalCount() {
        handlerKcal.removeCallbacks(runnableKcal);
    }*/

    @Override
    protected void onResume() {
        super.onResume();
        storeDataInArraysMain();
        storeDataInArraysMainTaet();

        fitnessOptions = FitnessOptions.builder()
                .accessActivitySessions(FitnessOptions.ACCESS_READ)
                .addDataType(DataType.TYPE_STEP_COUNT_DELTA, FitnessOptions.ACCESS_READ)
                .build();

        checkIfStepCounterSubscribed();
    }
    // Hello there

    private void checkIfStepCounterSubscribed() {
        // Prüfen, ob Stepcounter abonniert wurde
        SharedPreferences prefsSteps = MainActivity.this.getSharedPreferences("MyPrefs.StepCounterSubscribed", MainActivity.MODE_PRIVATE);
        boolean isAbonniertSteps = prefsSteps.getBoolean("abonniertSteps", false);  // false, wenn kein Wert in "abonniertSteps" gefunden
        if (isAbonniertSteps) {
            constraintLayout_circleBarSteps.setVisibility(View.VISIBLE);
            circleProgressBar_dailyStepsMain.setMax(8000);
            startUpdatingStepCount();  // displayDailySteps() mit run() regelmäßig ausführen
            Log.i(TAG, "SP Stepcounter ist true");
        } else {
            constraintLayout_circleBarSteps.setVisibility(View.GONE);
            Log.i(TAG, "SP Stepcounter ist false");

        }
        /*        SharedPreferences prefs = MainActivity.this.getSharedPreferences("MyPrefs.KcalCounterSubscribed", MainActivity.MODE_PRIVATE);
        boolean isAbonniertKcal = prefs.getBoolean("abonniertKcal", false);
        if (isAbonniertKcal) {
            circleProgressBar_dailyKcalMain.setMax(3000);
            startUpdatingKcalCount();
            Log.i(TAG, "SP Kcalcounter ist true");
        } else {
            Log.i(TAG, "SP Kcalcounter ist false");
        }*/
    }

    @Override
    protected void onPause() {
        super.onPause();
        stopUpdatingStepCount();
        //stopUpdatingKcalCount();
    }


    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.imgV_fertig) {
            // An Konstruktor übergeben von Dialog-Klasse
            String ziel = tV_ziel.getText().toString().trim();
            String differenz = tVmain_differenz.getText().toString().trim();
            AbschliessenPopup abschliessenPopup = new AbschliessenPopup
                    (MainActivity.this, ziel, differenz);
            abschliessenPopup.show();

            abschliessenPopup.setOnCancelListener(new DialogInterface.OnCancelListener() {
                @Override
                public void onCancel(DialogInterface dialog) { }
            });
            abschliessenPopup.setOnDismissListener(new DialogInterface.OnDismissListener() {
                @Override
                public void onDismiss(DialogInterface dialog) {
                    safeDataAndNewTimeAndDelete();
                }
            });
        }
        if (v.getId() == R.id.btnMain_Nahrunghinzu) {
            Intent intent = new Intent(this, NahrungslisteActivity.class);
            startActivity(intent);
            this.finish();
        }
        if (v.getId() == R.id.btn_zuSchrittzähler) {
            Intent intent = new Intent(MainActivity.this, GoogleLoginScreenActivity.class);
            intent.putExtra("schritte", tV_dailyStepsMain.getText().toString().trim());
            startActivity(intent);
            this.finish();
        }
        if (v.getId() == R.id.constraintLayout_circleBarSteps) {
            Intent intent = new Intent(MainActivity.this, GoogleLoginScreenActivity.class);
            intent.putExtra("schritte", tV_dailyStepsMain.getText().toString().trim());
            startActivity(intent);
            this.finish();
        }
/*        if (v.getId() == R.id.constraintLayout_circleBarKcal) {
            Intent intent = new Intent(this, GoogleLoginScreenActivity.class);
            startActivity(intent);
            this.finish();
        }*/
        if (v.getId() == R.id.imgV_ziel) {
            ZielSetzenPopup zielSetzenPopup = new ZielSetzenPopup
                    (MainActivity.this, tV_ziel.getText().toString().trim());
            zielSetzenPopup.show();
            zielSetzenPopup.setOnCancelListener(new DialogInterface.OnCancelListener() {
                @Override
                public void onCancel(DialogInterface dialog) { }
            });
            zielSetzenPopup.setOnDismissListener(new DialogInterface.OnDismissListener() {
                @Override
                public void onDismiss(DialogInterface dialog) {
                    // Variable von MainPopupZielSetzen.class empfangen
                    SharedPreferences prefs = MainActivity.this.getSharedPreferences
                            ("MyPrefs.MainPopupZielSetzen", MainActivity.MODE_PRIVATE);
                    ziel = prefs.getString("schlüsselZiel", "nichts");
                    tV_ziel.setText(ziel);

                    String maxID = dbHelper.getMaxIdUserdata();
                    dbHelper.updateZielUserdata(maxID, ziel);

                    kalorienverbrauchsrechner();
                    kaloriendifferenzenrechner();
                }
            });


        }
        if (v.getId() == R.id.imgV_kcalRechner) {
            String maxID = dbHelper.getMaxIdUserdata();
            dbHelper.updateDailyValues(maxID, tV_dailyStepsMain.getText().toString().trim(),
                    tVmain_KcalGes.getText().toString().trim());
            KcalRechnerPopup mainKcalrechnerPopup = new KcalRechnerPopup(MainActivity.this);
            mainKcalrechnerPopup.show();

            mainKcalrechnerPopup.setOnCancelListener(new DialogInterface.OnCancelListener() {
                @Override
                public void onCancel(DialogInterface dialog) { }
            });
            mainKcalrechnerPopup.setOnDismissListener(new DialogInterface.OnDismissListener() {
                @Override
                public void onDismiss(DialogInterface dialog) {

                    kalorienverbrauchsrechner();
                    kaloriendifferenzenrechner();
                }
            });
        }

        if (v.getId() == R.id.btn_verbrKcalHinzu) {
            VerbrKcalHinzuPopup taetigkeitSchnellHinzufuegen = new VerbrKcalHinzuPopup(MainActivity.this);
            taetigkeitSchnellHinzufuegen.show();

            taetigkeitSchnellHinzufuegen.setOnCancelListener(new DialogInterface.OnCancelListener() {
                @Override
                public void onCancel(DialogInterface dialog) { }
            });
            taetigkeitSchnellHinzufuegen.setOnDismissListener(new DialogInterface.OnDismissListener() {
                @Override
                public void onDismiss(DialogInterface dialog) { }
            });
        }
    }



    private void safeDataAndNewTimeAndDelete() {
        // Heutige Daten speichern
        String konsumKcal = tVmain_KcalGes.getText().toString().trim();
        String verbrauchKcal = tVmain_kcalverbrauch.getText().toString().trim();
        String differenz = tVmain_differenz.getText().toString().trim();
        String schritte = tV_dailyStepsMain.getText().toString().trim();
        dbHelper.addYesterdayData(konsumKcal, verbrauchKcal, differenz, schritte);

        // Startzeit, die in onCreate gespeichert wurde, nicht mehr verwenden
        // Setzen
        SharedPreferences prefs2 = getSharedPreferences("MyPrefs.MainActivityStepCounterFirstStart1", MODE_PRIVATE);
        SharedPreferences.Editor editor2 = prefs2.edit();
        editor2.putBoolean("stepCounterFirstStart", false);  // SP auf false setzen
        editor2.apply();

        // Neue Startzeit für Stepcounter in SP speichern
        LocalDateTime buttonClickTime = LocalDateTime.now();
        ZonedDateTime startTime = ZonedDateTime.of(buttonClickTime, ZoneId.systemDefault());
        String startTimeS = startTime.toString();
        SharedPreferences prefs = getSharedPreferences("MyPrefs.StepCounterResetTime", MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("stepsResetTime", startTimeS);
        editor.apply();
        Log.i(TAG, startTimeS + " startTime click");
        displayStepsSinceReset(startTime);
        tV_dailyStepsMain.setText("0");
        circleProgressBar_dailyStepsMain.setProgress(0);

        // Einträge löschen
        dbHelper.deleteAllDataMain();
        dbHelper.deleteAllDataMainTaet();
        recreate();
        Toast.makeText(MainActivity.this, "Einträge abgeschlossen", Toast.LENGTH_SHORT).show();
    }

    private void storeDataInArraysMain() {
        Cursor cursor = dbHelper.readAllDataMain();
        if (cursor.getCount() == 0) {  // Wenn keine Daten da sind
            //Log.w("creation", "storeDataInArrays_main fehlgeschlagen");
            layout_emptyDataMain.setVisibility(View.VISIBLE);
        } else {
            while (cursor.moveToNext()) {  // Liest alle Daten vom cursor
                nahrungList.add(new GetterSetterNahrungsmittel(cursor.getInt(0),
                        cursor.getString(1),
                        cursor.getString(2),
                        cursor.getString(3),
                        cursor.getString(4),
                        cursor.getString(5),
                        cursor.getString(6),
                        cursor.getString(7),
                        cursor.getString(8),
                        cursor.getString(9),
                        cursor.getString(10),
                        cursor.getInt(11),
                        cursor.getString(12),
                        cursor.getString(13),
                        cursor.getString(14),
                        cursor.getString(15),
                        cursor.getString(16),
                        cursor.getString(17),
                        cursor.getString(18),
                        cursor.getString(19),
                        cursor.getString(20),
                        cursor.getString(21),
                        cursor.getString(22),
                        cursor.getString(23)));
            }
            layout_emptyDataMain.setVisibility(View.GONE);
        }
    }

    private void storeDataInArraysMainTaet() {
        Cursor cursor = dbHelper.readAllDataMainTaet();
        if (cursor.getCount() == 0) {  // Wenn keine Daten da sind
            //Log.w("creation", "storeDataInArrays_mainTaet fehlgeschlagen");
        } else {
            while (cursor.moveToNext()) {  // Liest alle Daten vom cursor
                taetigkeitenlist.add(new GetterSetterTaetigkeiten(cursor.getInt(0),
                        cursor.getString(1),
                        cursor.getString(2),
                        cursor.getString(3),
                        cursor.getString(4),
                        cursor.getInt(5)));
            }
        }

    }


    private void swipeToDelete() {
        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }
            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                String newID = (String) viewHolder.itemView.getTag();
                dbHelper.deleteOneRow_main(newID);
                mainAdapter.removeItem(viewHolder.getBindingAdapterPosition());  // Methode in MainAdapter

                Cursor cursor = dbHelper.readNaehrwerteMain();
                kalorienArray = new double[cursor.getCount()];
                carbsArray = new double[cursor.getCount()];
                fetteArray = new double[cursor.getCount()];
                eiweissArray = new double[cursor.getCount()];
                portionenArray = new double[cursor.getCount()];
                int i = 0;
                if (cursor.moveToFirst()) {
                    do {
                        kalorienArray[i] = Double.parseDouble(cursor.getString(0));
                        carbsArray[i] = Double.parseDouble(cursor.getString(1));
                        fetteArray[i] = Double.parseDouble(cursor.getString(2));
                        eiweissArray[i] = Double.parseDouble(cursor.getString(3));
                        portionenArray[i] = Double.parseDouble(cursor.getString(4));
                        i++;
                    } while (cursor.moveToNext());
                }
                cursor.close();

                kcalKonsum = ProPortionKalorienRechner(kalorienArray);
                carbs = ProPortionCarbsRechner(carbsArray);
                fette = ProPortionFetteRechner(fetteArray);
                eiweiss = ProPortionEiweissRechner(eiweissArray);

                tVmain_KcalGes.setText(kcalKonsum);
                tVmain_carbs.setText(carbs);
                tVmain_fette.setText(fette);
                tVmain_eiweiss.setText(eiweiss);

                kalorienverbrauchsrechner();
                kaloriendifferenzenrechner();
            }
        }).attachToRecyclerView(rV_main);
    }

    private void swipeToDeleteTaet() {
        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }
            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                String newID = (String) viewHolder.itemView.getTag();
                dbHelper.deleteOneRow_mainTaet(newID);
                taetigkeitenlisteAdapter.removeItem(viewHolder.getBindingAdapterPosition());

                kalorienverbrauchsrechner();
                kaloriendifferenzenrechner();
            }
        }).attachToRecyclerView(rV_TaetigkeitenMain);
    }


    public String ProPortionKalorienRechner(double[] kalorienArray) {
        double kalorien_zahl = 0;
        // Summiert alle Werte im kalorienArray auf
        for (double value : kalorienArray) {  // Durchlaufe kalorienArray und speichere bei jedem Durchlauf den Wert in value
            kalorien_zahl += value;
        }
        // Schneidet ggf. ".0" ab oder rundet auf zwei Nachkommastellen
        StringBuilder sb = new StringBuilder(String.valueOf(kalorien_zahl));
        String kalorien = "";  // Endergebnis
        for (int i = 0; i < String.valueOf(kalorien_zahl).length(); i++) {
            if (sb.charAt(i) == '.' && (sb.charAt(i+1) == '0')) {
                int kalorien_int = 0;
                kalorien_int = (int) kalorien_zahl;
                kalorien = String.valueOf(kalorien_int);
            }
            if (sb.charAt(i) == '.' && (sb.charAt(i+1) != '0')) {
                // Auf eine Nachkommastelle runden
                kalorien_zahl = Math.round(kalorien_zahl * 10) / 10.0;
                kalorien = String.valueOf(kalorien_zahl);
            }
        }
        return kalorien;
    }


    public String ProPortionCarbsRechner(double[] carbsArray) {
        double carbs_zahl = 0;
        for (double value : carbsArray) {
            carbs_zahl += value;
        }
        StringBuilder sb = new StringBuilder(String.valueOf(carbs_zahl));
        String carbs = "";  // Endergebnis
        for (int i = 0; i < String.valueOf(carbs_zahl).length(); i++) {
            if (sb.charAt(i) == '.' && (sb.charAt(i+1) == '0')) {
                int carbs_int = 0;
                carbs_int = (int) carbs_zahl;
                carbs = String.valueOf(carbs_int);
            }
            if (sb.charAt(i) == '.' && (sb.charAt(i+1) != '0')) {
                carbs_zahl = Math.round(carbs_zahl * 10) / 10.0;
                carbs = String.valueOf(carbs_zahl);
            }
        }
        return carbs;
    }

    public String ProPortionFetteRechner(double[] fetteArray) {
        double fette_zahl = 0;
        for (double value : fetteArray) {
            fette_zahl += value;
        }
        StringBuilder sb = new StringBuilder(String.valueOf(fette_zahl));
        String fette = "";  // Endergebnis
        for (int i = 0; i < String.valueOf(fette_zahl).length(); i++) {
            if (sb.charAt(i) == '.' && (sb.charAt(i + 1) == '0')) {
                int fette_int = 0;
                fette_int = (int) fette_zahl;
                fette = String.valueOf(fette_int);
            }
            if (sb.charAt(i) == '.' && (sb.charAt(i + 1) != '0')) {
                fette_zahl = Math.round(fette_zahl * 10) / 10.0;
                fette = String.valueOf(fette_zahl);
            }
        }
        return fette;
    }

    public String ProPortionEiweissRechner(double[] eiweissArray) {
        double eiweiss_zahl = 0;
        for (double value : eiweissArray) {
            eiweiss_zahl += value;
        }
        StringBuilder sb = new StringBuilder(String.valueOf(eiweiss_zahl));
        String eiweiss = "";  // Endergebnis
        for (int i = 0; i < String.valueOf(eiweiss_zahl).length(); i++) {
            if (sb.charAt(i) == '.' && (sb.charAt(i + 1) == '0')) {
                int eiweiss_int = 0;
                eiweiss_int = (int) eiweiss_zahl;
                eiweiss = String.valueOf(eiweiss_int);
            }
            if (sb.charAt(i) == '.' && (sb.charAt(i + 1) != '0')) {
                eiweiss_zahl = Math.round(eiweiss_zahl * 10) / 10.0;
                eiweiss = String.valueOf(eiweiss_zahl);
            }
        }
        return eiweiss;
    }

    private void readNaehrwerteAndSet() {
        // Nährwerte und ProPortion ablesen
        Cursor cursor = dbHelper.readNaehrwerteMain();
        kalorienArray = new double[cursor.getCount()];  // getCount() = Länge von size of cursor
        carbsArray = new double[cursor.getCount()];
        fetteArray = new double[cursor.getCount()];
        eiweissArray = new double[cursor.getCount()];
        portionenArray = new double[cursor.getCount()];
        int i = 0;
        if (cursor.moveToFirst()) {
            do {
                kalorienArray[i] = Double.parseDouble(cursor.getString(0));
                carbsArray[i] = Double.parseDouble(cursor.getString(1));
                fetteArray[i] = Double.parseDouble(cursor.getString(2));
                eiweissArray[i] = Double.parseDouble(cursor.getString(3));
                portionenArray[i] = Double.parseDouble(cursor.getString(4));
                i++;
            } while (cursor.moveToNext());
        }
        cursor.close();

        kcalKonsum = ProPortionKalorienRechner(kalorienArray);
        carbs = ProPortionCarbsRechner(carbsArray);
        fette = ProPortionFetteRechner(fetteArray);
        eiweiss = ProPortionEiweissRechner(eiweissArray);

        tVmain_KcalGes.setText(kcalKonsum);
        tVmain_carbs.setText(carbs);
        tVmain_fette.setText(fette);
        tVmain_eiweiss.setText(eiweiss);
    }

    private void createDummyUser() {
        SharedPreferences prefs = MainActivity.this.getSharedPreferences
                ("MyPrefs.MainCustomPopupFirstStart19", Context.MODE_PRIVATE);
        boolean addUserdataLeer = prefs.getBoolean("addUserdataLeer", true);  // true, wenn kein Wert in "addUserdata" gefunden
        if (addUserdataLeer) {
            // Tabelle leer, Dummy Userdata-Zeile anlegen
            Log.e(TAG, "SP addUserdataLeer ist true");
            dbHelper.addUserdata("Männlich", "80", "25", "180",
                    "0", "0", "0", "0",
                    "0", "0", "0", "0", "0",
                    "0", "0", "0", "0");
            SharedPreferences.Editor editor = prefs.edit();
            editor.putBoolean("addUserdataLeer", false);  // SP auf false setzen
            editor.apply();
        } else {
            Log.e(TAG, "SP addUserdataLeer ist false");
        }

        // Zieldifferenz von Datenbank abfragen und setzen
        String maxxID = dbHelper.getMaxIdUserdata();
        String ziel = dbHelper.getZielByID(maxxID);
        if (ziel == null) {
            tV_ziel.setText("0");
        } else {
            tV_ziel.setText(ziel);
        }

        kalorienverbrauchsrechner();
        kaloriendifferenzenrechner();
/*        // Kalorienverbrauch abrufen und anzeigen
        String maxID = dbHelper.getMaxIdUserdata();
        kcalverbrauch = dbHelper.getErgebnisByID(maxID);
        if (kcalverbrauch == null)
        {
            tVmain_kcalverbrauch.setText("0");
        } else {
            tVmain_kcalverbrauch.setText(kcalverbrauch);
        }*/
    }

    private void readYesterdayData() {
        // YesterdayData von Datenbank abfragen und setzen
        String maxIDYd = dbHelper.getMaxIdYesterdayData();
        Cursor cursor2 = dbHelper.readAllYesterdayData();
        int j = 0;
        if (cursor2.moveToFirst()) {
            do {
                yKonsumKcal = (cursor2.getString(1));  // Spalte 0 ist die ID
                yVerbrauchKcal = (cursor2.getString(2));
                yDifferenz = (cursor2.getString(3));
                ySchritte = (cursor2.getString(4));
                j++;
            } while (cursor2.moveToNext());
        }
        cursor2.close();
        tV_vortagKonsumKcal.setText(yKonsumKcal);
        tV_vortagVerbrKcal.setText(yVerbrauchKcal);
        tV_vortagDiff.setText(yDifferenz);
        tV_vortagSteps.setText(ySchritte);
    }



    private void createAndSafeStartimeStepcounter() {
        // Startzeit für Stepcounter festlegen, wenn Einträge noch nie abgeschlossen wurden
        // Empfangen, wenn in 'Einträge abschließen' noch nie was gesetzt wurde, dann standardmäßig true
        SharedPreferences prefs0 = MainActivity.this.getSharedPreferences
                ("MyPrefs.MainActivityStepCounterFirstStart1", Context.MODE_PRIVATE);
        boolean stepCounterFirstStart = prefs0.getBoolean("stepCounterFirstStart", true);  // true, wenn kein Wert in "stepCounterFirstStart" gefunden
        if (stepCounterFirstStart) {
            // aktuelle Startzeit in SP speichern
            LocalDateTime localDateTime = LocalDateTime.now();
            ZonedDateTime startTime = ZonedDateTime.of(localDateTime, ZoneId.systemDefault());
            String startTimeS = startTime.toString();

            SharedPreferences prefs2 = getSharedPreferences("MyPrefs.MainActivityStepCounterFirstStartTime", MODE_PRIVATE);
            SharedPreferences.Editor editor2 = prefs2.edit();
            editor2.putString("stepCounterFirstStartTime", startTimeS);  // SP auf false setzen
            editor2.apply();

            Log.e(TAG, "MainActivityStepCounterFirstStart1 auf true");
        } else {
            // Schlüssel "stepCounterFirstStart" wird in 'Einträge abschließen' auf false gesetzt
            Log.e(TAG, "MainActivityStepCounterFirstStart1 auf false");
        }
    }


    @Override
    public void onBackPressed() { }

}

