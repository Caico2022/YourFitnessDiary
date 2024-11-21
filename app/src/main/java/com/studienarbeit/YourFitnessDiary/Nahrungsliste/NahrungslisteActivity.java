package com.studienarbeit.YourFitnessDiary.Nahrungsliste;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.activity.result.ActivityResultLauncher;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.journeyapps.barcodescanner.ScanContract;
import com.journeyapps.barcodescanner.ScanOptions;
import com.studienarbeit.YourFitnessDiary.Adapter.NahrungslisteAdapter;
import com.studienarbeit.YourFitnessDiary.DBHelper;
import com.studienarbeit.YourFitnessDiary.GetterSetter.GetterSetterNahrungsmittel;
import com.studienarbeit.YourFitnessDiary.Main.MainActivity;
import com.studienarbeit.YourFitnessDiary.OFFBarcodescanner.OpenFoodFactsActivity;
import com.studienarbeit.YourFitnessDiary.OFFBarcodescanner.ScannerActivity;
import com.studienarbeit.YourFitnessDiary.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;


public class NahrungslisteActivity extends AppCompatActivity implements View.OnClickListener {

    private NahrungslisteAdapter nahrungslisteAdapter;
    private Button btn_eigeneNahrunghinzu, btn_BarcodeScannen, zuOFF;
    private EditText pT_Suchleiste;
    private RecyclerView rV_Nahrungsliste;
    private DBHelper dbHelper;
    private ArrayList<GetterSetterNahrungsmittel> nahrungList = new ArrayList<>();
    private SearchView sV_Suchleiste;

    private static final int CAMERA_PERMISSION_REQUEST_CODE = 100;
    private boolean cameraPermissionGranted = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nahrungsliste);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }

        ConstraintLayout layout = findViewById(R.id.ConstraintLayout);
        layout.setBackgroundResource(R.drawable.bg_general);

        rV_Nahrungsliste = findViewById(R.id.rV_Nahrungsliste);
        btn_eigeneNahrunghinzu = findViewById(R.id.btn_eigeneNahrunghinzu);
        btn_BarcodeScannen = findViewById(R.id.btn_BarcodeScannen);
        zuOFF = findViewById(R.id.zuOFF);
        zuOFF.setOnClickListener(this);
        btn_eigeneNahrunghinzu.setOnClickListener(this);
        btn_BarcodeScannen.setOnClickListener(this);
        sV_Suchleiste = findViewById(R.id.sV_Suchleiste);
        sV_Suchleiste.clearFocus();


        dbHelper = new DBHelper(NahrungslisteActivity.this);
        storeDataInArrays();  //Ablesen und an GetterSetterNahrungsmittel.java Konstruktor geben
        nahrungslisteAdapter = new NahrungslisteAdapter(NahrungslisteActivity.this, this, nahrungList);
        // Ruft CustomAdapter Konstruktor auf
        rV_Nahrungsliste.setAdapter(nahrungslisteAdapter);
        rV_Nahrungsliste.setLayoutManager(new LinearLayoutManager(NahrungslisteActivity.this));

        swipeToDelete();
        searchView();

    }

    private void swipeToDelete() {
        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                String item_id = (String) viewHolder.itemView.getTag();
                DBHelper dbHelper = new DBHelper(NahrungslisteActivity.this);
                dbHelper.deleteOneRow(item_id);
                nahrungslisteAdapter.removeItem(viewHolder.getBindingAdapterPosition());
            }
        }).attachToRecyclerView(rV_Nahrungsliste);
    }

    private void searchView() {
        sV_Suchleiste = findViewById(R.id.sV_Suchleiste);
        sV_Suchleiste.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                filterList(newText);
                return true;
            }
        });
    }

    // Suchfilter
    private void filterList(String text) {
        List<GetterSetterNahrungsmittel> filteredList = new ArrayList<>();
        for (GetterSetterNahrungsmittel nahrungList1 : nahrungList) {  // Bis zum letzten item der nahrungList
            if (nahrungList1.getBeschreibung().toLowerCase().contains(text.toLowerCase())) {
                filteredList.add(nahrungList1); // nahrungList1 = gefilterte Liste
            }
        }
        if (filteredList.isEmpty()) {
            //Toast.makeText(this, "Keine Einträge gefunden", Toast.LENGTH_SHORT).show();
        } else {
            nahrungslisteAdapter.setFilterList(filteredList);
        }
    }

    // Daten von Methode readAllData (DBHelper) in cursor speichern
    // An NahrungList.java Konstruktor geben
    private void storeDataInArrays() {
        Cursor cursor = dbHelper.readAllData();
        if (cursor.getCount() == 0) {  // Wenn keine Daten da sind
        } else {
            while (cursor.moveToNext()) {  // Liest alle Daten vom cursor
                nahrungList.add(new GetterSetterNahrungsmittel(cursor.getInt(0),
                        cursor.getString(1),
                        cursor.getString(2),
                        cursor.getString(3),
                        cursor.getString(4),
                        cursor.getString(5),
                        cursor.getString(6),
                        cursor.getString(7),
                        cursor.getString(8),
                        cursor.getString(9),
                        cursor.getString(10),
                        cursor.getString(11),
                        cursor.getString(12),
                        cursor.getString(13),
                        cursor.getString(14),
                        cursor.getString(15),
                        cursor.getString(16),
                        cursor.getString(17),
                        cursor.getString(18)));
            }
            sortList();
        }
    }

    // RecyclerView sortieren
    private void sortList() {
        nahrungList.sort(new Comparator<GetterSetterNahrungsmittel>() {
            @Override
            public int compare(GetterSetterNahrungsmittel n1, GetterSetterNahrungsmittel n2) {
                //return n1.getBeschreibung().compareToIgnoreCase(n2.getBeschreibung());
                return Integer.compare(n1.getId(), n2.getId());
            }
        });
        Collections.reverse(nahrungList);
    }

/*    // Zeigt den Papierkorb oben rechts
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.my_menue, menu);
        return super.onCreateOptionsMenu(menu);
    }*/


/*    // Triggert OnClickListener für den Papierkorb
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.delete_all) {  // delete_all ist die id vom Papierkorb (siehe my_menue)
            confirmDialog();
        }
        return super.onOptionsItemSelected(item);
    }

    void confirmDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Alles löschen?");
        builder.setMessage("Sind Sie sicher, dass Sie alle Einträge löschen möchten?");
        builder.setPositiveButton("Ja", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                DBHelper dbHelper = new DBHelper(Nahrungsliste.this);
                dbHelper.deleteAllData();
                // Von Nahrungsliste.java zu Nahrungsliste.java, besser als recreate()
                Intent intent = new Intent(Nahrungsliste.this, Nahrungsliste.class);
                startActivity(intent);
                finish();
                Toast.makeText(Nahrungsliste.this, "Liste gelöscht", Toast.LENGTH_SHORT).show();
            }
        });
        builder.setNegativeButton("Nein", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        builder.create().show();
    }*/


    // ScanOptions Objekt wird erstellt, konfiguriert den Scanner, der durch barcodeLauncher.launch(options) aufgerufen wird.
    // barcodeLauncher verwendet die Parameter von ScanOptions.
    private void scanCode() {
        ScanOptions options = new ScanOptions();
        options.setPrompt("Scanne einen Barcode");
        options.setBeepEnabled(false);
        options.setBarcodeImageEnabled(true);
        options.setOrientationLocked(true);
        options.setCaptureActivity(ScannerActivity.class);
        options.setTorchEnabled(true);
        barcodeLauncher.launch(options);
    }

    ActivityResultLauncher<ScanOptions> barcodeLauncher = registerForActivityResult(new ScanContract(), result -> {
        if (result.getContents() != null) {
            String barcode = result.getContents();
            Intent intent = new Intent(NahrungslisteActivity.this, OpenFoodFactsActivity.class);
            intent.putExtra("barcode", barcode);
            startActivity(intent);
            this.finish();
        }
    });
    // Lambda-Ausdruck wird erst ausgeführt, wenn das Ergebnis des Scans zurückgegeben wird






    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btn_eigeneNahrunghinzu) {
            Intent intent = new Intent(this, NahrungErstellenActivity.class);
            startActivity(intent);
            this.finish();
        }
        if (v.getId() == R.id.btn_BarcodeScannen) {
            if (ContextCompat.checkSelfPermission(NahrungslisteActivity.this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
                // Die Kameraberechtigung wurde bereits erteilt
                cameraPermissionGranted = true;
                scanCode();
            } else {
                // Der Benutzer hat die Kameraberechtigung noch nicht erteilt
                cameraPermissionGranted = false;
                ActivityCompat.requestPermissions(NahrungslisteActivity.this, new String[] {Manifest.permission.CAMERA}, CAMERA_PERMISSION_REQUEST_CODE);
            }
        }
        if (v.getId() == R.id.zuOFF) {
            Intent intent = new Intent(this, OpenFoodFactsActivity.class);
            startActivity(intent);
            this.finish();
        }
    }


    @SuppressLint("MissingSuperCall")
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == CAMERA_PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Der Benutzer hat die Kameraberechtigung erteilt
                cameraPermissionGranted = true;
                scanCode();
            } else {
                // Der Benutzer hat die Kameraberechtigung abgelehnt
                cameraPermissionGranted = false;
                showPermissionDeniedDialog();
            }
        }
    }

    private void showPermissionDeniedDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Kameraberechtigung erforderlich");
        builder.setMessage("Die Kameraberechtigung ist erforderlich, um Barcodes scannen zu können. Bitte erteilen Sie die Berechtigung in den App-Einstellungen.");
        builder.setPositiveButton("App-Einstellungen", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                // Zu den App-Einstellungen verlinken (Uri : Uniform Resource Identifier, eindeutige Identifikation für Bilder, Websiten, Einstellungen etc.)
                Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                Uri uri = Uri.fromParts("package", getPackageName(), null);
                intent.setData(uri);
                startActivity(intent);
            }
        });
        builder.setNegativeButton("Abbrechen", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }



    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }

}

