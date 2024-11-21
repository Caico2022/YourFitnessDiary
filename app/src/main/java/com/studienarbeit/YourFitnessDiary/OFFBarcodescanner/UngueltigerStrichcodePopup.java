package com.studienarbeit.YourFitnessDiary.OFFBarcodescanner;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;

import com.studienarbeit.YourFitnessDiary.Nahrungsliste.NahrungslisteActivity;
import com.studienarbeit.YourFitnessDiary.R;

public class UngueltigerStrichcodePopup extends Dialog implements View.OnClickListener {

    public UngueltigerStrichcodePopup(@NonNull Context context) {
        super(context);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ungueltiger_strichcode_pop_up);

        Button btn_ungueltigerStrichcodePopupZurueck = findViewById(R.id.btn_ungueltigerStrichcodePopupZurueck);
        btn_ungueltigerStrichcodePopupZurueck.setOnClickListener(this);

        getWindow().setDimAmount(1f);
    }

    public void onClick(View v) {
        if (v.getId() == R.id.btn_ungueltigerStrichcodePopupZurueck) {
            Intent intent = new Intent(getContext(), NahrungslisteActivity.class);
            getContext().startActivity(intent);
        }
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(getContext(), NahrungslisteActivity.class);
        getContext().startActivity(intent);
    }
}

