package com.studienarbeit.YourFitnessDiary.GoogleSchrittzähler;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.studienarbeit.YourFitnessDiary.Main.MainActivity;
import com.studienarbeit.YourFitnessDiary.R;

public class GoogleLoginScreenActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String tag = "creation";
    private static final String test = "test";

    private GoogleSignInAccount googleSignInAccount;  // Repräsentiert den Acc des Benutzer, Zugriff auf Namen und Email, Identifikation
    private GoogleSignInOptions googleSignInOptions;  // Festlegen, welche Berechtigungen die App anfordert
    private GoogleSignInClient googleSignInClient;  // Anmeldevorgang starten, Verbindung zum Google-Server, Zugriffstoken und ID-Token,
                                            // Zugriff auf Namen und Email, Benutzer abmelden und Konto wechseln
    /* Zugriffstoken : Wird vom Google-Server ausgestellt, um auf geschützte Ressourcen zuzugreifen (durch OAuth geschützt),
                       Zugriff auf Google-Dienste (Google-Drive, Google Calendar) */
    // ID-Token: JSON-Web-Token, Wird vom Google-Server ausgestellt, enthält Infos über Benutzer (ID, Name, Email)

    private TextView tV_googleFitEinleitung;
    private EditText multiEditText_Einleitung;
    private  ImageView imgV_googleLogin;
    private ActivityResultLauncher<Intent> activityResultLauncher;  // deklarieren Sie die Variable als Klassenvariable

    private String schritte = "0";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_google_schrittz_login_screen);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }

        tV_googleFitEinleitung = findViewById(R.id.tV_googleFitEinleitung);
        String text = "Um den Google Fit Schrittzähler zu benutzen, musst du dich mit deinem Google-Konto anmelden.\n\n"
                + "Deine gesammelten Schritte werden aufgezeichnet, welche du dann in deiner Tagesliste sehen kannst.\n\n"
                + "Beachte bitte, dass es sich nur um Schätzungen handelt.";
        tV_googleFitEinleitung.setText(text);

        imgV_googleLogin = findViewById(R.id.imgV_googleLogin);
        imgV_googleLogin.setOnClickListener(this);

/*        googleSignInOptions = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .requestScopes(Fitness.SCOPE_ACTIVITY_READ_WRITE)
                .build();*/

        googleSignInOptions = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        googleSignInClient = GoogleSignIn.getClient(this, googleSignInOptions);

        schritte = getIntent().getStringExtra("schritte");

        safeLogin();
        setupActivityResultLauncher();
    }


    private void safeLogin() {
        // Login speichern
        googleSignInAccount = GoogleSignIn.getLastSignedInAccount(this);
        if (googleSignInAccount != null) {
            Intent intent = new Intent(GoogleLoginScreenActivity.this, GoogleSchrittzaehlerActivity.class);
            intent.putExtra("schritte", schritte);
            startActivity(intent);
            finish();
        }
    }

    private void setupActivityResultLauncher() {
        Log.w("creation", "3");
        activityResultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        Log.w("creation", "4");
                        if (result.getResultCode() == Activity.RESULT_OK) {
                            Log.w("creation", "5");
                            Intent data = result.getData();
                            Log.w("creation", "6");
                            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
                            Log.w("creation", "7");
                            try {
                                Log.w("creation", "8");
                                task.getResult(ApiException.class);
                                Log.w("creation", "9");
                                Intent intent = new Intent(GoogleLoginScreenActivity.this, GoogleSchrittzaehlerActivity.class);
                                startActivity(intent);
                                finish();
                            } catch (ApiException e) {
                                Log.w(tag, String.valueOf(e));
                                Toast.makeText(GoogleLoginScreenActivity.this, "Etwas ist schief gelaufen", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                });
    }


    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.imgV_googleLogin) {
            Intent intent = googleSignInClient.getSignInIntent();
            Log.w("creation", "1");
            activityResultLauncher.launch(intent);
            Log.w("creation", "2");
        }
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}
