package com.studienarbeit.YourFitnessDiary.Nahrungsmittel;

import androidx.activity.result.ActivityResultLauncher;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.hardware.camera2.CameraAccessException;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.journeyapps.barcodescanner.CompoundBarcodeView;
import com.journeyapps.barcodescanner.ScanContract;
import com.journeyapps.barcodescanner.ScanOptions;
import com.journeyapps.barcodescanner.camera.CameraManager;
import com.studienarbeit.YourFitnessDiary.Adapter.NahrungslisteAdapter;
import com.studienarbeit.YourFitnessDiary.DBHelper;
import com.studienarbeit.YourFitnessDiary.GetterSetter.GetterSetterNahrungsmittel;
import com.studienarbeit.YourFitnessDiary.Main.MainActivity;
import com.studienarbeit.YourFitnessDiary.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;


public class Nahrungsliste extends AppCompatActivity implements View.OnClickListener {

    NahrungslisteAdapter nahrungslisteAdapter;
    Button btn_eigeneNahrunghinzu, btn_BarcodeScannen;
    EditText pT_Suchleiste;
    RecyclerView rV_Nahrungsliste;
    DBHelper myDB;
    ArrayList<GetterSetterNahrungsmittel> nahrungList = new ArrayList<>();
    ImageView imgV_emptyData;
    TextView tV_KeineEintraege;
    SearchView sV_Suchleiste;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nahrungsliste);

        rV_Nahrungsliste = findViewById(R.id.rV_Nahrungsliste);
        btn_eigeneNahrunghinzu = findViewById(R.id.btn_eigeneNahrunghinzu);
        pT_Suchleiste = findViewById(R.id.pT_Suchleiste);
        imgV_emptyData = findViewById(R.id.imgV_emptyData);
        tV_KeineEintraege = findViewById(R.id.tV_KeineEintraege);
        btn_BarcodeScannen = findViewById(R.id.btn_BarcodeScannen);
        btn_eigeneNahrunghinzu.setOnClickListener(this);
        pT_Suchleiste.setOnClickListener(this);
        btn_BarcodeScannen.setOnClickListener(this);
        sV_Suchleiste = (SearchView) findViewById(R.id.sV_Suchleiste);
        sV_Suchleiste.clearFocus();

        myDB = new DBHelper(Nahrungsliste.this);
        storeDataInArrays();  //Ablesen und an NahrungList.java Konstruktor geben
        nahrungslisteAdapter = new NahrungslisteAdapter(Nahrungsliste.this, this, nahrungList);
        // Ruft CustomAdapter Konstruktor auf
        rV_Nahrungsliste.setAdapter(nahrungslisteAdapter);
        rV_Nahrungsliste.setLayoutManager(new LinearLayoutManager(Nahrungsliste.this));

        SwipeToDelete();
        SearchView();

    }

    void SwipeToDelete() {
        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                String item_id = (String) viewHolder.itemView.getTag();
                DBHelper dbHelper = new DBHelper(Nahrungsliste.this);
                dbHelper.deleteOneRow(item_id);
                recreate();
            }
        }).attachToRecyclerView(rV_Nahrungsliste);
    }

    void SearchView() {
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
    void storeDataInArrays() {
        Cursor cursor = myDB.readAllData();
        if (cursor.getCount() == 0) {  // Wenn keine Daten da sind
            imgV_emptyData.setVisibility(View.VISIBLE);
            tV_KeineEintraege.setVisibility(View.VISIBLE);
        } else {
            while (cursor.moveToNext()) {  // Liest alle Daten vom cursor
                nahrungList.add(new GetterSetterNahrungsmittel(cursor.getInt(0), cursor.getString(1), cursor.getString(2), cursor.getDouble(3), cursor.getDouble(4), cursor.getDouble(5), cursor.getDouble(6), cursor.getDouble(7), cursor.getDouble(8), cursor.getString(9), cursor.getString(10)));
            }
            sortList();
            imgV_emptyData.setVisibility(View.GONE);
            tV_KeineEintraege.setVisibility(View.GONE);
        }
    }

    // RecyclerView sortieren
    public void sortList() {
        nahrungList.sort(new Comparator<GetterSetterNahrungsmittel>() {
            @Override
            public int compare(GetterSetterNahrungsmittel n1, GetterSetterNahrungsmittel n2) {
                //return n1.getBeschreibung().compareToIgnoreCase(n2.getBeschreibung());
                return Integer.compare(n1.getId(), n2.getId());
            }
        });
        Collections.reverse(nahrungList);
    }

    // Zeigt den Papierkorb oben rechts
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.my_menue, menu);
        return super.onCreateOptionsMenu(menu);
    }


    // Triggert OnClickListener für den Papierkorb
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
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }


    private void scanCode() {
        ScanOptions options = new ScanOptions();
        options.setPrompt("Scan a barcode");
        options.setBeepEnabled(true);
        options.setBarcodeImageEnabled(true);
        options.setOrientationLocked(true);
        options.setCaptureActivity(ScannerActivity.class);
        options.setTorchEnabled(true); // schaltet die Taschenlampe während der Kameranutzung ein

        barcodeLauncher.launch(options);
    }

    // Register the launcher and result handler
    ActivityResultLauncher<ScanOptions> barcodeLauncher = registerForActivityResult(new ScanContract(), result -> {
        if (result.getContents() != null) { // Wenn ein Wert im Code enthalten ist
            AlertDialog.Builder builder = new AlertDialog.Builder(Nahrungsliste.this);
            builder.setTitle("Result");
            builder.setMessage(result.getContents());
            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.dismiss();
                }
            }).show();
        } else {
            Toast.makeText(this, "Nichts gescannt", Toast.LENGTH_SHORT).show();
        }
    });

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btn_eigeneNahrunghinzu) {
            Intent zuNahrungErstellen = new Intent(this, NahrungErstellen.class);
            startActivity(zuNahrungErstellen);
            this.finish();
        }
        if (v.getId() == R.id.btn_BarcodeScannen) {
            scanCode();
        }
    }
}

