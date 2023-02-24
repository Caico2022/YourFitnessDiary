package com.studienarbeit.YourFitnessDiary.Nahrungsmittel;

import android.os.Bundle;
import android.util.Log;
import android.widget.ImageButton;

import androidx.appcomat.app.AppCompatActivity;
import com.journeyapps.barcodescanner.ZXingScannerView;

import com.journeyapps.barcodescanner.DecoratedBarcodeView;
import com.studienarbeit.YourFitnessDiary.R;


public class ScannerActivity extends AppCompatActivity implements DecoratedBarcodeView.TorchListener {
    private static final String TAG = ScannerActivity.class.getSimpleName();
    private ZxingScannerView scannerView;
    private ImageButton toggleTorchButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.scanner_layout);

        scannerView = findViewById(R.id.scanner);
        toggleTorchButton = findViewById(R.id.toggle_torch_button);

        // Initialisieren Sie die Taschenlampen-Schaltfläche
        scannerView.setTorchListener(this);
        toggleTorchButton.setOnClickListener(v -> {
            if (scannerView.isTorchOn()) {
                scannerView.setTorchOff();
            } else {
                scannerView.setTorchOn();
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        scannerView.setResultHandler(result -> {
            // Behandeln Sie das Scan-Ergebnis hier
            String contents = result.getText();
            Log.d(TAG, "Scan result: " + contents);
        });
        scannerView.startCamera();
    }

    @Override
    public void onPause() {
        super.onPause();
        scannerView.stopCamera();
    }

    @Override
    public void onTorchOn() {
        toggleTorchButton.setImageResource(R.drawable.ic_flash_on);
    }

    @Override
    public void onTorchOff() {
        toggleTorchButton.setImageResource(R.drawable.ic_flash_off);
    }
}
