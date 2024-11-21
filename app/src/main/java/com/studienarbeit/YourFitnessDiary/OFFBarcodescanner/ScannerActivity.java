package com.studienarbeit.YourFitnessDiary.OFFBarcodescanner;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.journeyapps.barcodescanner.CaptureActivity;
import com.journeyapps.barcodescanner.DecoratedBarcodeView;
import com.studienarbeit.YourFitnessDiary.Nahrungsliste.NahrungslisteActivity;
import com.studienarbeit.YourFitnessDiary.R;

public class ScannerActivity extends CaptureActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EditText eT_Strichcode = findViewById(R.id.eT_Strichcode);
        Button btn_strichcodeDone = findViewById(R.id.btn_strichcodeDone);
        Button btn_flash_off = findViewById(R.id.btn_flash_off);
        Button btn_flash_on = findViewById(R.id.btn_flash_on);
        btn_flash_off.setOnClickListener(this);
        btn_flash_on.setOnClickListener(this);
        btn_strichcodeDone.setOnClickListener(this);
    }


    // Scanner-Oberfläche initialisieren
    @Override
    protected DecoratedBarcodeView initializeContent() {
        setContentView(R.layout.scanner_overlay);
        return findViewById(R.id.zxing_barcode_scanner);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btn_flash_on) {
            v.setVisibility(View.GONE);
            DecoratedBarcodeView barcodeView = findViewById(R.id.zxing_barcode_scanner);
            barcodeView.setTorchOff();
            findViewById(R.id.btn_flash_off).setVisibility(View.VISIBLE);
        }
        if (v.getId() == R.id.btn_flash_off) {
            v.setVisibility(View.GONE);
            DecoratedBarcodeView barcodeView = findViewById(R.id.zxing_barcode_scanner);
            barcodeView.setTorchOn();
            findViewById(R.id.btn_flash_on).setVisibility(View.VISIBLE);
        }
        if (v.getId() == R.id.btn_strichcodeDone) {
            EditText eT_Strichcode = findViewById(R.id.eT_Strichcode);
            if (eT_Strichcode.getText().toString().length() > 0) {
                String barcode = eT_Strichcode.getText().toString().trim();
                Log.w("creation", barcode + " ScannerActivity Intent");
                Intent intent = new Intent(ScannerActivity.this, OpenFoodFactsActivity.class);
                intent.putExtra("barcode", barcode);
                startActivity(intent);
                this.finish();
            } else {
                Toast.makeText(this, "Bitte einen Strichcode eingeben", Toast.LENGTH_SHORT).show();
            }

        }
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, NahrungslisteActivity.class);
        startActivity(intent);
        finish();
    }
}








