package com.studienarbeit.YourFitnessDiary.Tätigkeiten;

import androidx.annotation.NonNull;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.studienarbeit.YourFitnessDiary.DBHelper;
import com.studienarbeit.YourFitnessDiary.Main.MainActivity;
import com.studienarbeit.YourFitnessDiary.R;

public class VerbrKcalSchnellHinzu extends Dialog implements View.OnClickListener {

    public VerbrKcalSchnellHinzu(@NonNull Context context) {
        super(context);
    }

    Button btn_TaetSchnellZurueck, btn_TaetSchnellSpeichern;
    EditText pT_WvKcalVerbrannt;
    String id = "0";
    String beschreibung = "0";
    String laenge = "0";
    String verbrKcal = "0";
    String startzeit = "0";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_taetigkeit_schnell_hinzufuegen);

        btn_TaetSchnellZurueck = findViewById(R.id.btn_TaetSchnellZurueck);
        btn_TaetSchnellSpeichern = findViewById(R.id.btn_TaetSchnellSpeichern);
        pT_WvKcalVerbrannt = findViewById(R.id.pT_WvKcalVerbrannt);
        btn_TaetSchnellZurueck.setOnClickListener(this);
        btn_TaetSchnellSpeichern.setOnClickListener(this);
    }

    public void onClick(View v) {
        if (v.getId() == R.id.btn_TaetSchnellZurueck) {
            dismiss();
        }
        if (v.getId() == R.id.btn_TaetSchnellSpeichern) {
            DBHelper myDB = new DBHelper(VerbrKcalSchnellHinzu.this.getContext());
            verbrKcal = pT_WvKcalVerbrannt.getText().toString().trim();
            myDB.addNahrung_mainTaet(id, beschreibung, laenge, verbrKcal, startzeit);

            Context context = getContext();
            Intent intent = new Intent(context, MainActivity.class);
            context.startActivity(intent);
            dismiss();
        }
    }
}

