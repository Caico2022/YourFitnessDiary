package com.studienarbeit.YourFitnessDiary.Tätigkeiten;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.studienarbeit.YourFitnessDiary.Main.MainActivity;
import com.studienarbeit.YourFitnessDiary.R;

public class Taetigkeitenliste extends AppCompatActivity implements View.OnClickListener {

    Button btn_eigenTaetHinzu,btn_TaetHinzuSchnell;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_taetigkeitenliste);

        btn_eigenTaetHinzu = findViewById(R.id.btn_eigenTaetHinzu);
        btn_TaetHinzuSchnell = findViewById(R.id.btn_TaetHinzuSchnell);
        btn_eigenTaetHinzu.setOnClickListener(this);
        btn_TaetHinzuSchnell.setOnClickListener(this);

    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    public void onClick(View v) {
        if (v.getId() == R.id.btn_eigenTaetHinzu) {
            Intent zuTaetigkeitErstellen = new Intent(this, TaetigkeitErstellen.class);
            startActivity(zuTaetigkeitErstellen);
            this.finish();
        }
        if (v.getId() == R.id.btn_TaetHinzuSchnell) {
            VerbrKcalSchnellHinzu taetigkeitSchnellHinzufuegen = new VerbrKcalSchnellHinzu(Taetigkeitenliste.this);
            taetigkeitSchnellHinzufuegen.show();
        }
    }
}