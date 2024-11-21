package com.studienarbeit.YourFitnessDiary.Main;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.studienarbeit.YourFitnessDiary.R;

public class AbschliessenPopup extends Dialog implements View.OnClickListener {

    private Button btn_verbrKcalClose, btn_verbrKcalAbschliessen;
    private TextView tV_heutigeDiff;
    private TextView tV_differenzZiel;

    private String ziel, differenz;

    public AbschliessenPopup(@NonNull Context context, String ziel, String differenz) {
        super(context);
        this.ziel = ziel;
        this.differenz = differenz;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_abschliessen_popup);

        btn_verbrKcalClose = findViewById(R.id.btn_verbrKcalClose);
        btn_verbrKcalAbschliessen = findViewById(R.id.btn_verbrKcalAbschliessen);
        tV_heutigeDiff = findViewById(R.id.tV_heutigeDiff);
        tV_differenzZiel = findViewById(R.id.tV_differenzZiel);
        btn_verbrKcalClose.setOnClickListener(this);
        btn_verbrKcalAbschliessen.setOnClickListener(this);

        WindowManager.LayoutParams params = getWindow().getAttributes();
        params.width = WindowManager.LayoutParams.MATCH_PARENT;

        // Auf Konstruktor zugreifen
        AbschliessenPopup abschliessenPopup = new AbschliessenPopup(getContext(), ziel, differenz);

        tV_heutigeDiff.setText(differenz);
        tV_differenzZiel.setText(ziel);

    }


    public void onClick(View v) {
        if (v.getId() == R.id.btn_verbrKcalClose) {
            setOnDismissListener(null);
            cancel();
        }
        if (v.getId() == R.id.btn_verbrKcalAbschliessen) {
            dismiss();
        }
    }

    @Override
    public void onBackPressed() {
        setOnDismissListener(null);
        cancel();
    }
}