package com.studienarbeit.YourFitnessDiary.Main;

import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.studienarbeit.YourFitnessDiary.R;

public class ZielSetzenPopup extends Dialog implements View.OnClickListener {

    private TextView eT_taeglichesZiel;
    private Button btn_zielClose, btn_zielSpeichern;
    private String ziel = "0";

    public ZielSetzenPopup(@NonNull Context context, String ziel) {
        super(context);
        this.ziel = ziel;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_popup_ziel_setzen);

        eT_taeglichesZiel = findViewById(R.id.eT_taeglichesZiel);
        btn_zielClose = findViewById(R.id.btn_zielClose);
        btn_zielSpeichern = findViewById(R.id.btn_zielSpeichern);
        btn_zielClose.setOnClickListener(this);
        btn_zielSpeichern.setOnClickListener(this);

        WindowManager.LayoutParams params = getWindow().getAttributes();
        params.width = WindowManager.LayoutParams.MATCH_PARENT;

        ZielSetzenPopup zielSetzenPopup = new ZielSetzenPopup(getContext(), ziel);
        eT_taeglichesZiel.setText(ziel);
    }



    public void onClick(View v) {
        if (v.getId() == R.id.btn_zielClose) {
            setOnDismissListener(null);
            cancel();
        }
        if (v.getId() == R.id.btn_zielSpeichern) {
            safeZiel();
        }

    }

    private void safeZiel() {
            ziel = eT_taeglichesZiel.getText().toString().trim();
            if (!ziel.equals("") && (!ziel.equals("."))) {
                SharedPreferences prefs = getContext().getSharedPreferences("MyPrefs.MainPopupZielSetzen", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = prefs.edit();
                editor.putString("schlüsselZiel", ziel);  // Variable ziel mit dem Schlüssel 'schlüsselZiel' gespeichert
                editor.apply();
                dismiss();
            } else {
                Toast.makeText(getContext(),"Bitte gib eine gültige Zahl an.", Toast.LENGTH_SHORT).show();
            }
        }


    @Override
    public void onBackPressed() {
        setOnDismissListener(null);
        cancel();
    }
}