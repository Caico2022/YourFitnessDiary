package com.studienarbeit.YourFitnessDiary.Main;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;

import com.studienarbeit.YourFitnessDiary.DBHelper;
import com.studienarbeit.YourFitnessDiary.R;

public class VerbrKcalHinzuPopup extends Dialog implements View.OnClickListener {

    public VerbrKcalHinzuPopup(@NonNull Context context) {
        super(context);
    }

    private Button btn_verbrKcalClose, btn_verbrKcalSpeichern;
    private EditText pT_verbrKcalBeschr, pT_verbrKcalAnzahl;
    private String id = "0";
    private String beschreibung = "0";
    private String laenge = "0";
    private String anzahl = "0";
    private String startzeit = "0";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verbr_kcal_hinzu_popup);

        pT_verbrKcalBeschr = findViewById(R.id.pT_verbrKcalBeschr);
        pT_verbrKcalAnzahl = findViewById(R.id.pT_verbrKcalAnzahl);
        btn_verbrKcalClose = findViewById(R.id.btn_verbrKcalClose);
        btn_verbrKcalSpeichern = findViewById(R.id.btn_verbrKcalAbschliessen);
        btn_verbrKcalClose.setOnClickListener(this);
        btn_verbrKcalSpeichern.setOnClickListener(this);
    }


    public void onClick(View v) {
        if (v.getId() == R.id.btn_verbrKcalClose) {
            setOnDismissListener(null);
            cancel();
        }
        if (v.getId() == R.id.btn_verbrKcalAbschliessen) {
            safeData();
            Intent intent = new Intent(getContext(), MainActivity.class);
            getContext().startActivity(intent);
            dismiss();
        }
    }

    private void safeData() {
        DBHelper dbHelper = new DBHelper(VerbrKcalHinzuPopup.this.getContext());
        beschreibung = pT_verbrKcalBeschr.getText().toString().trim();
        anzahl = pT_verbrKcalAnzahl.getText().toString().trim();
        dbHelper.addNahrungMainTaet(id, beschreibung, laenge, anzahl, startzeit);
    }

    @Override
    public void onBackPressed() {
        setOnDismissListener(null);
        cancel();
    }
}

