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

public class InternetFailPopup extends Dialog implements View.OnClickListener {

    public InternetFailPopup(@NonNull Context context) {
        super(context);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_internet_fail_popup);

        Button btn_internetFailClose = findViewById(R.id.btn_internetFailClose);
        btn_internetFailClose.setOnClickListener(this);

        getWindow().setDimAmount(1f);
    }

    public void onClick(View v) {
        if (v.getId() == R.id.btn_internetFailClose) {
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

