package com.studienarbeit.YourFitnessDiary.GoogleSchrittzähler;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.fitness.Fitness;
import com.google.android.gms.fitness.FitnessOptions;
import com.google.android.gms.fitness.data.DataType;
import com.studienarbeit.YourFitnessDiary.Main.MainActivity;
import com.studienarbeit.YourFitnessDiary.R;

public class GoogleSchrittzaehlerActivity extends AppCompatActivity implements View.OnClickListener {

    private Button btn_googleSchrittzLogout;
    private TextView tV_gSchrittzEmail, tV_gSchrittzName, tV_gSchrittzSteps;
    private ProgressBar circleProgressBar_gSchrittzDailySteps, circleProgressBar_gSchrittzDailyKcal;
    private TextView tV_gSchrittzDailyKcal, tV_gSchrittzYesterdaySteps;
    private ConstraintLayout constraintLayout_Steps, constraintLayout_gSchrittzCircleBarSteps,
            constraintLayout_gSchrittzCircleBarKcal;

    private static final String TAG = "creation";
    private static final String TEST = "TEST";

    private GoogleSignInOptions googleSignInOptions;
    private GoogleSignInClient googleSignInClient;
    private FitnessOptions fitnessOptions;

    String schritte;

    @SuppressLint({"StaticFieldLeak", "ClickableViewAccessibility"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_google_schrittzaehler);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }

        tV_gSchrittzEmail = findViewById(R.id.tV_googleSchrittzEmail);
        tV_gSchrittzName = findViewById(R.id.tV_googleSchrittzName);
        tV_gSchrittzSteps = findViewById(R.id.tV_gSchrittzSteps);
        tV_gSchrittzDailyKcal = findViewById(R.id.tV_gSchrittzDailyKcal);
        btn_googleSchrittzLogout = findViewById(R.id.btn_googleSchrittzLogout);
        circleProgressBar_gSchrittzDailySteps = findViewById(R.id.circleProgressBar_gSchrittzDailySteps);
        circleProgressBar_gSchrittzDailyKcal = findViewById(R.id.circleProgressBar_gSchrittzDailyKcal);
        constraintLayout_Steps = findViewById(R.id.constraintLayout_Steps);
        constraintLayout_gSchrittzCircleBarSteps = findViewById(R.id.constraintLayout_gSchrittzCircleBarSteps);
        constraintLayout_gSchrittzCircleBarKcal = findViewById(R.id.constraintLayout_gSchrittzCircleBarKcal);
        tV_gSchrittzYesterdaySteps = findViewById(R.id.tV_gSchrittzYesterdaySteps);
        circleProgressBar_gSchrittzDailySteps.setMax(10000);
        constraintLayout_gSchrittzCircleBarKcal.setOnClickListener(this);
        constraintLayout_gSchrittzCircleBarSteps.setOnClickListener(this);
        btn_googleSchrittzLogout.setOnClickListener(this);

        getSchritteFromIntent();
        showUser();
        initializeAndSubscribe();

/*        SharedPreferences prefsSteps = getSharedPreferences("MyPrefs.userdataDialog", MODE_PRIVATE);
        // Wenn "value" noch nicht in prefsSteps gespeichert wurde, dann ist es automatisch false
        initialized = prefsSteps.getBoolean("value", false);
        if (!initialized) {
            // Status von "value" auf true setzen
            SharedPreferences.Editor editor = prefsSteps.edit();
            editor.putBoolean("value", true);
            editor.apply();
        }*/

/*        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);
        if (account != null) {
            Log.e(TAG, "angemeldet");
        } else {
            Log.e(TAG, "nicht angemeldet");
        }

        account = GoogleSignIn.getLastSignedInAccount(this);
        if (GoogleSignIn.hasPermissions(account, Fitness.SCOPE_ACTIVITY_READ_WRITE)) {
            Log.e(TAG, "Rechte SCOPE_ACTIVITY_READ_WRITE");
        } else {
            Log.e(TAG, "keine Rechte SCOPE_ACTIVITY_READ_WRITE");
        }

        account = GoogleSignIn.getLastSignedInAccount(this);
        if (GoogleSignIn.hasPermissions(account, Fitness.SCOPE_BODY_READ_WRITE)) {
            Log.e(TAG, "Rechte SCOPE_BODY_READ_WRITE");
        } else {
            Log.e(TAG, "keine Rechte SCOPE_BODY_READ_WRITE");
        }

        account = GoogleSignIn.getLastSignedInAccount(this);
        if (GoogleSignIn.hasPermissions(account, Fitness.SCOPE_NUTRITION_READ_WRITE)) {
            Log.e(TAG, "Rechte SCOPE_NUTRITION_READ_WRITE");
        } else {
            Log.e(TAG, "keine Rechte SCOPE_NUTRITION_READ_WRITE");
        }*/

/*        // Kalorienzähler subscriben
        SharedPreferences prefsKcal = getSharedPreferences("MyPrefs.KcalCounterSubscribed", MODE_PRIVATE);
        Fitness.getRecordingClient(this, GoogleSignIn.getAccountForExtension(this, fitnessOptions))
                .subscribe(DataType.TYPE_CALORIES_EXPENDED)
                .addOnSuccessListener(unused -> {
                    Log.i(TAG, "Successfully subscribed to TYPE_CALORIES_EXPENDED");
                    SharedPreferences.Editor editor = prefsKcal.edit();
                    editor.putBoolean("abonniertKcal", true);
                    editor.apply();
                    Log.i(TAG, "SP Kcalcounter auf true");
                })
                .addOnFailureListener( e -> {
                    Log.w(TAG, "There was a problem subscribing TYPE_CALORIES_EXPENDED", e);
                    SharedPreferences.Editor editor = prefsKcal.edit();
                    editor.putBoolean("abonniertKcal", false);
                    editor.apply();
                    Log.i(TAG, "SP Kcalcounter auf false");
                });*/

/*        // ****************************** Steps vom gestrigen Tag ausgeben ****************************** //
        LocalDate yesterday = LocalDate.now().minusDays(1); // gestriger Tag
        ZonedDateTime endTime = yesterday.atTime(LocalTime.MAX).atZone(ZoneId.systemDefault());
        ZonedDateTime startTime = endTime.minusDays(1);
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
                    int totalSteps = 0; // Summe der Schritte für den gestrigen Tag
                    for (Bucket bucket : response.getBuckets()) {
                        for (DataSet dataSet : bucket.getDataSets()) {
                            for (DataPoint dp : dataSet.getDataPoints()) {
                                totalSteps += dp.getValue(Field.FIELD_STEPS).asInt();
                            }
                            dumpDataSetYesterdaySteps(dataSet);
                        }
                    }
                    Log.i(TAG, "Total steps for yesterday: " + totalSteps);
                })
                .addOnFailureListener(e ->
                        Log.w(TAG, "There was an error reading data from Google Fit", e));*/

    }

    private void initializeAndSubscribe() {
        // Initialisieren
/*        googleSignInOptions = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestScopes(Fitness.SCOPE_ACTIVITY_READ_WRITE)
                .build();*/
        googleSignInOptions = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .build();
        googleSignInClient = GoogleSignIn.getClient(this, googleSignInOptions);
        // Alle Berechtigungen festlegen, die gebraucht werden
/*        fitnessOptions = FitnessOptions.builder()
                .accessActivitySessions(FitnessOptions.ACCESS_READ)
                .addDataType(DataType.TYPE_STEP_COUNT_DELTA, FitnessOptions.ACCESS_READ)
                .build();*/
        fitnessOptions = FitnessOptions.builder()
                .addDataType(DataType.TYPE_STEP_COUNT_DELTA, FitnessOptions.ACCESS_READ)
                .build();

        // Schrittzähler subscriben
        SharedPreferences prefsSteps = getSharedPreferences("MyPrefs.StepCounterSubscribed", MODE_PRIVATE);
        Fitness.getRecordingClient(this, GoogleSignIn.getAccountForExtension(this, fitnessOptions))
                .subscribe(DataType.TYPE_STEP_COUNT_DELTA)
                .addOnSuccessListener(unused -> {
                    Log.i(TAG, "Successfully subscribed to TYPE_STEP_COUNT_DELTA");
                    SharedPreferences.Editor editor = prefsSteps.edit();
                    editor.putBoolean("abonniertSteps", true);  // SP auf true setzen
                    editor.apply();
                    Log.i(TAG, "SP Stepcounter auf true");
                })
                .addOnFailureListener( e -> {
                    Log.w(TAG, "There was a problem subscribing TYPE_STEP_COUNT_DELTA", e);
                    SharedPreferences.Editor editor = prefsSteps.edit();
                    editor.putBoolean("abonniertSteps", false);  // SP auf false setzen
                    editor.apply();
                    Log.i(TAG, "SP Stepcounter auf false");
                });
    }


    /*private void dumpDataSetYesterdaySteps(DataSet dataSet) {
        if (dataSet.isEmpty()) {
            Log.w(TAG, "DataSet is empty or contains no valid data points");
            return;
        }
        Log.i(TAG, "Data returned for Data type: " + dataSet.getDataType().getName());
        for (DataPoint dp : dataSet.getDataPoints()) {
            Log.i(TAG, "Data point:");
            Log.i(TAG, "\tType: " + dp.getDataType().getName());
            Log.i(TAG, "\tStart: " + getStartTimeString(dp));
            Log.i(TAG, "\tEnd: " + getEndTimeString(dp));
            for (Field field : dp.getDataType().getFields()) {
                Log.i(TAG, "\tField: " + field.getName() + " Value: " + dp.getValue(field));
            }
        }
    }*/

/*    private void dumpDataSetYesterdaySteps(DataSet dataSet) {
        if (dataSet.isEmpty()) {
            Log.w(TAG, "Dataset ist leer");
            return;
        }
        Log.i(TAG, "Data returned for Data type: " + dataSet.getDataType().getName());
        for (DataPoint dp : dataSet.getDataPoints()) {
            for (Field field : dp.getDataType().getFields()) {
                int stepsValue = dp.getValue(field).asInt(); // Schrittzahl als int-Wert extrahieren
                String stepsString = Integer.toString(stepsValue); // int-Wert in eine Zeichenfolge konvertieren
                Log.i(TAG, "\tField: " + field.getName() + " Value: " + stepsString);
                tV_gSchrittzYesterdaySteps.setText(stepsString); // Zeichenfolge im TextView setzen
            }
        }
    }*/

/*    // Zeigt die Schrittzahl an bis resetet wird
    @SuppressLint("SetTextI18n")
    private void displayStepsSinceReset(ZonedDateTime startTime) {
        ZonedDateTime endTime = ZonedDateTime.now(ZoneId.systemDefault()).plusSeconds(1);

        Log.e(TAG, "Range Start: " + startTime);
        Log.e(TAG, "Range End: " + endTime);
        DataReadRequest readRequest = new DataReadRequest.Builder()
                .aggregate(DataType.TYPE_STEP_COUNT_DELTA)
                .bucketByTime(1, TimeUnit.DAYS)
                .setTimeRange(startTime.toEpochSecond(), endTime.toEpochSecond(), TimeUnit.SECONDS)
                .build();
        Fitness.getHistoryClient(this, GoogleSignIn.getAccountForExtension(this, fitnessOptions))
                .readData(readRequest)
                .addOnSuccessListener (response -> {
                    int totalSteps = 0; // Summe der Schritte für den gestrigen Tag
                    for (Bucket bucket : response.getBuckets()) {
                        for (DataSet dataSet : bucket.getDataSets()) {
                            for (DataPoint dp : dataSet.getDataPoints()) {
                                totalSteps += dp.getValue(Field.FIELD_STEPS).asInt();
                            }
                            dumpStepsSinceReset(dataSet);
                        }
                    }
                    Log.e(TAG, "Total steps : " + totalSteps);
                })
                .addOnFailureListener(e ->
                        Log.e(TAG, "There was an error reading data from Google Fit", e));

    }*/

/*    private void dumpStepsSinceReset(DataSet dataSet) {
        if (dataSet.isEmpty()) {
            Log.e(TAG, "Dataset ist leer");
            return;
        }
        Log.e(TAG, "Data returned for Data type: " + dataSet.getDataType().getName());
        for (DataPoint dp : dataSet.getDataPoints()) {
            for (Field field : dp.getDataType().getFields()) {
                int stepsValue = dp.getValue(field).asInt(); // Schrittzahl als int-Wert extrahieren
                String stepsString = Integer.toString(stepsValue); // int-Wert in eine Zeichenfolge konvertieren
                Log.e(TAG, "\tField: " + field.getName() + " Value: " + stepsString);
                tV_gSchrittzSteps.setText(stepsString);
                tV_gSchrittzSteps.setText(stepsString);
            }
        }
    }*/

    /*    // Zeigt die Schrittzahl im TextView an vom heutigen Tag
    @SuppressLint("SetTextI18n")
    private void displayDailySteps() {
        Fitness.getHistoryClient(this, GoogleSignIn.getAccountForExtension(this, fitnessOptions))
                .readDailyTotal(DataType.TYPE_STEP_COUNT_DELTA)
                .addOnSuccessListener(dataSet -> {
                    int totalSteps = 0;
                    // Summierte alle Datenpunkte auf (Zeiträume, in denen der User Schritte gesammelt hat)
                    for (DataPoint dp : dataSet.getDataPoints()) {
                        for(Field field : dp.getDataType().getFields()) {
                            totalSteps += dp.getValue(field).asInt();
                        }
                    }
                    tV_gSchrittzDailySteps.setText(String.valueOf(totalSteps));
                    circleProgressBar_gSchrittzDailySteps.setProgress(totalSteps);
                })
                .addOnFailureListener(e ->
                        Log.w(TAG, "There was an error reading daily step count data", e));
    }*/

/*    // Zeigt die verbrannten Kcal im TextView an vom heutigen Tag
    private void displayDailyKcal() {
        Fitness.getHistoryClient(this, GoogleSignIn.getAccountForExtension(this, fitnessOptions))
                .readDailyTotal(DataType.TYPE_CALORIES_EXPENDED)
                .addOnSuccessListener(dataSet -> {
                    float totalCalories = dataSet.isEmpty()
                            ? 0
                            : dataSet.getDataPoints().get(0).getValue(Field.FIELD_CALORIES).asFloat();

                    // Anzeige der verbrannten Kalorien in der TextView
                    TextView kalorienTextView = findViewById(R.id.tV_gSchrittzDailyKcal);
                    kalorienTextView.setText(String.format(Locale.US, "%.2f", totalCalories));
                    circleProgressBar_gSchrittzDailyKcal.setProgress((int) totalCalories);
                })
                .addOnFailureListener(e ->
                        Log.w(TAG, "There was an error reading daily calorie count data", e));

    }*/

/*    // anonyme innere Klasse, führt Methode run() aus, die wiederum in bestimmten Zeitintervallen vom handler ausgeführt wird
    private final Runnable runnable = new Runnable() {
        @Override
        public void run() {
            SharedPreferences prefs = GoogleSchrittzaehler.this.getSharedPreferences
                    ("MyPrefs.StepCounterResetTime", GoogleSchrittzaehler.MODE_PRIVATE);
            String startTimeS = prefs.getString("stepsResetTime", String.valueOf(ZonedDateTime.now(ZoneId.systemDefault()).minusSeconds(1)));
            ZonedDateTime startTime = ZonedDateTime.parse(startTimeS);
            Log.w(TAG, startTimeS + " startTime run");
            displayStepsSinceReset(startTime);
            handler.postDelayed(this, UPDATE_INTERVAL_MS);
        }
    };

    // Ruft die Methode post() des handlers auf, um die Klasse 'Runnable' zu starten
    private void startUpdatingStepCount() {
        handler.post(runnable);
    }

    // Ruft die Methode removeCallbacks() des handlers auf, um die Klasse 'Runnable' zu stoppen
    private void stopUpdatingStepCount() {
        handler.removeCallbacks(runnable);
    }

    // Wird beim Start aufgerufen, auch wenn die Aktivität nach pausieren oder stoppen wieder aufgenommen wird (onCreate nicht)
    @Override
    protected void onResume() {
        super.onResume();
        startUpdatingStepCount();
    }

    @Override
    protected void onPause() {
        super.onPause();
        stopUpdatingStepCount();
    }*/

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btn_googleSchrittzLogout) {
            logoutDialog();
        }
/*        if (v.getId() == R.id.btn_reset) {
            LocalDateTime buttonClickTime = LocalDateTime.now();
            ZonedDateTime startTime = ZonedDateTime.of(buttonClickTime, ZoneId.systemDefault());

            String startTimeS = startTime.toString();

            SharedPreferences prefs = getSharedPreferences("MyPrefs.StepCounterResetTime", MODE_PRIVATE);
            SharedPreferences.Editor editor = prefs.edit();
            editor.putString("stepsResetTime", startTimeS);
            editor.apply();
            Log.w(TAG, startTimeS + " startTime click");

            displayStepsSinceReset(startTime);
        }*/
    }


    // AlertDialog : Von Google Fit-Diensten trennen und ausloggen
    private void logoutDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Möchtest du dich wirklich ausloggen?");
        builder.setMessage("Deine Verbindung zu den Google Fit Diensten wird " +
                "getrennt und du kannst deine Schritte nicht mehr verfolgen.");
        builder.setPositiveButton("Verbindung trennen", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                SharedPreferences prefsSteps = getSharedPreferences("MyPrefs.StepCounterSubscribed", MODE_PRIVATE);
                SharedPreferences.Editor editor = prefsSteps.edit();
                editor.putBoolean("abonniertSteps", false);  // SP auf false setzen
                editor.apply();
                Log.i(TAG, "SP Stepcounter auf false");

/*                SharedPreferences prefsKcal = getSharedPreferences("MyPrefs.KcalCounterSubscribed", MODE_PRIVATE);
                SharedPreferences.Editor editor2 = prefsKcal.edit();
                editor2.putBoolean("abonniertKcal", false);
                editor2.apply();
                Log.i(TAG, "SP Kcalcounter auf false");*/

                dialog.dismiss();
                Fitness.getConfigClient(GoogleSchrittzaehlerActivity.this, GoogleSignIn.getAccountForExtension(GoogleSchrittzaehlerActivity.this, fitnessOptions))
                        .disableFit()
                        .addOnSuccessListener(unused -> Log.i(TAG, "Disabled Google Fit"))
                        .addOnFailureListener(e -> Log.w(TAG, "There was an error disabling Google Fit", e));
                googleSignInClient.signOut().addOnCompleteListener(GoogleSchrittzaehlerActivity.this, task -> {
                    Intent intent = new Intent(GoogleSchrittzaehlerActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                });
            }
        });
        builder.setNegativeButton("Abbrechen", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.create().show();
    }

    private void getSchritteFromIntent() {
        schritte = getIntent().getStringExtra("schritte");
        Log.e(TAG, schritte + " SCHRITTE");
        if (schritte != null) {
            tV_gSchrittzSteps.setText(schritte);
            circleProgressBar_gSchrittzDailySteps.setProgress(Integer.parseInt(schritte));
        }
    }

    private void showUser() {
        // Name und Email des Google-Kontos anzeigen
        GoogleSignInAccount googleSignInAccount = GoogleSignIn.getLastSignedInAccount(this);
        if (googleSignInAccount != null) {
            tV_gSchrittzName.setText(googleSignInAccount.getDisplayName());
            tV_gSchrittzEmail.setText(googleSignInAccount.getEmail());
        }
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }


}