package com.studienarbeit.YourFitnessDiary.Main;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.studienarbeit.YourFitnessDiary.Adapter.MainAdapter;
import com.studienarbeit.YourFitnessDiary.Adapter.TaetigkeitenMainAdapter;
import com.studienarbeit.YourFitnessDiary.DBHelper;
import com.studienarbeit.YourFitnessDiary.GetterSetter.GetterSetterNahrungsmittel;
import com.studienarbeit.YourFitnessDiary.GetterSetter.GetterSetterTaetigkeiten;
import com.studienarbeit.YourFitnessDiary.Nahrungsmittel.Nahrungsliste;
import com.studienarbeit.YourFitnessDiary.R;
import com.studienarbeit.YourFitnessDiary.Spezialfunktionen.Schrittzaehler;
import com.studienarbeit.YourFitnessDiary.Tätigkeiten.Taetigkeitenliste;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Objects;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    TextView tVmain_CarbsGes, tVmain_FetteGes, tVmain_EiweissGes, tVmain_KcalGes;
    Button btn_Nahrunghinzu, btnMain_fertig, btn_Schrittzaehler, btn_TaetigkeitHinzu;
    RecyclerView rV_main, rV_TaetigkeitenMain;
    DBHelper myDB;
    ArrayList<GetterSetterNahrungsmittel> nahrungList = new ArrayList<>();
    MainAdapter mainAdapter;
    TaetigkeitenMainAdapter taetigkeitenMainAdapter;
    SharedPreferences preferences;
    SharedPreferences.Editor editor;
    ArrayList<GetterSetterTaetigkeiten> taetigkeitenlist = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        DBHelper dbHelper = new DBHelper(MainActivity.this);

        Objects.requireNonNull(getSupportActionBar()).hide();

        tVmain_CarbsGes = findViewById(R.id.tVmain_CarbsGes);
        tVmain_FetteGes = findViewById(R.id.tVmain_FetteGes);
        tVmain_EiweissGes = findViewById(R.id.tVmain_EiweissGes);
        tVmain_KcalGes = findViewById(R.id.tVmain_KcalGes);
        btn_Nahrunghinzu = findViewById(R.id.btnMain_Nahrunghinzu);
        rV_main = findViewById(R.id.rV_main);
        rV_TaetigkeitenMain = findViewById(R.id.rV_TaetigkeitenMain);
        btnMain_fertig = findViewById(R.id.btnMain_fertig);
        btn_Schrittzaehler = findViewById(R.id.btn_Schrittzaehler);
        btn_TaetigkeitHinzu = findViewById(R.id.btn_TaetigkeitHinzu);
        btn_TaetigkeitHinzu.setOnClickListener(this);
        btn_Nahrunghinzu.setOnClickListener(this);
        btnMain_fertig.setOnClickListener(this);
        btn_Schrittzaehler.setOnClickListener(this);

        myDB = new DBHelper(MainActivity.this);

        storeDataInArrays_main();  //Ablesen und an NahrungList.java Konstruktor geben
        storeDataInArrays_mainTaet();

        mainAdapter = new MainAdapter(MainActivity.this, this, nahrungList);
        rV_main.setAdapter(mainAdapter);
        rV_main.setLayoutManager(new LinearLayoutManager(MainActivity.this));
        taetigkeitenMainAdapter = new TaetigkeitenMainAdapter(MainActivity.this, this, taetigkeitenlist);
        rV_TaetigkeitenMain.setAdapter(taetigkeitenMainAdapter);
        rV_TaetigkeitenMain.setLayoutManager(new LinearLayoutManager(MainActivity.this));

        SwipeToDelete();
        SwipeToDeleteTaet();

        // Nährwerte und ProPortion ablesen
        Cursor cursor_naehrwerte_main = myDB.readNaehrwerte_main();
        double[] kalorienArray = new double[cursor_naehrwerte_main.getCount()];  // getCount() = Länge von size of cursor
        double[] carbsArray = new double[cursor_naehrwerte_main.getCount()];
        double[] fetteArray = new double[cursor_naehrwerte_main.getCount()];
        double[] proteineArray = new double[cursor_naehrwerte_main.getCount()];
        double[] portionenArray = new double[cursor_naehrwerte_main.getCount()];
        int i = 0;
        if (cursor_naehrwerte_main.moveToFirst()) {
            do {
                kalorienArray[i] = Double.parseDouble(cursor_naehrwerte_main.getString(0));
                carbsArray[i] = Double.parseDouble(cursor_naehrwerte_main.getString(1));
                fetteArray[i] = Double.parseDouble(cursor_naehrwerte_main.getString(2));
                proteineArray[i] = Double.parseDouble(cursor_naehrwerte_main.getString(3));
                portionenArray[i] = Double.parseDouble(cursor_naehrwerte_main.getString(4));
                i++;
            } while (cursor_naehrwerte_main.moveToNext());
        }
        cursor_naehrwerte_main.close();


        String kalorien = ProPortionKalorienRechner(kalorienArray);
        String carbs = ProPortionCarbsRechner(carbsArray);
        String fette = ProPortionFetteRechner(fetteArray);
        String eiweiss = ProPortionEiweissRechner(proteineArray);

        tVmain_KcalGes.setText(kalorien);
        tVmain_CarbsGes.setText(carbs);
        tVmain_FetteGes.setText(fette);
        tVmain_EiweissGes.setText(eiweiss);
    }

    public void storeDataInArrays_main() {
        Cursor cursor_main = myDB.readAllData_main();
        if (cursor_main.getCount() == 0) {  // Wenn keine Daten da sind
            Log.d("MainActivity", "storeDataInArrays_main fehlgeschlagen");
        } else {
            while (cursor_main.moveToNext()) {  // Liest alle Daten vom cursor
                nahrungList.add(new GetterSetterNahrungsmittel(cursor_main.getInt(0),
                        cursor_main.getString(1),
                        cursor_main.getString(2),
                        cursor_main.getDouble(3),
                        cursor_main.getDouble(4),
                        cursor_main.getDouble(5),
                        cursor_main.getDouble(6),
                        cursor_main.getDouble(7),
                        cursor_main.getDouble(8),
                        cursor_main.getString(9),
                        cursor_main.getString(10),
                        cursor_main.getInt(11),
                        cursor_main.getDouble(12),
                        cursor_main.getDouble(13),
                        cursor_main.getDouble(14),
                        cursor_main.getDouble(15)));
            }
        }
    }

    public void storeDataInArrays_mainTaet() {
        Cursor cursor = myDB.readAllData_mainTaet();
        if (cursor.getCount() == 0) {  // Wenn keine Daten da sind
            Log.d("MainActivity", "storeDataInArrays_main fehlgeschlagen");
        } else {
            while (cursor.moveToNext()) {  // Liest alle Daten vom cursor
                taetigkeitenlist.add(new GetterSetterTaetigkeiten(cursor.getInt(0),
                        cursor.getString(2),
                        cursor.getString(3),
                        cursor.getString(4),
                        cursor.getString(4),
                        cursor.getInt(5)));
            }
        }
    }

    public void SwipeToDelete() {
        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }
            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                String newID = (String) viewHolder.itemView.getTag();
                DBHelper dbHelper = new DBHelper(MainActivity.this);
                dbHelper.deleteOneRow_main(newID);
                recreate();
            }
        }).attachToRecyclerView(rV_main);
    }

    public void SwipeToDeleteTaet() {
        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }
            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                String newID = (String) viewHolder.itemView.getTag();
                DBHelper dbHelper = new DBHelper(MainActivity.this);
                dbHelper.deleteOneRow_mainTaet(newID);
                recreate();
            }
        }).attachToRecyclerView(rV_TaetigkeitenMain);
    }

    void confirmDialog_main() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Einträge für heute abschließen?");
        builder.setPositiveButton("Ja", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                DBHelper dbHelper = new DBHelper(MainActivity.this);
                dbHelper.deleteAllData_main();
                // Von Nahrungsliste.java zu Nahrungsliste.java, besser als recreate()
                Intent intent = new Intent(MainActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
                Toast.makeText(MainActivity.this, "Liste gelöscht", Toast.LENGTH_SHORT).show();
            }
        });
        builder.setNegativeButton("Nein", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        builder.create().show();
    }

    public String ProPortionKalorienRechner(double[] kalorienArray) {

        double kalorien_zahl = 0;
        // Summiert alle Werte im kalorienArray auf
        for (double value : kalorienArray) {  // Durchlaufe kalorienArray und speichere bei jedem Durchlauf den Wert in value
            kalorien_zahl += value;
        }
        // Schneidet ggf. ".0" ab oder rundet auf zwei Nachkommastellen
        StringBuilder sb = new StringBuilder(String.valueOf(kalorien_zahl));
        String kalorien = "";  // Endergebnis
        for (int i = 0; i < String.valueOf(kalorien_zahl).length(); i++) {
            if (sb.charAt(i) == '.' && (sb.charAt(i+1) == '0')) {
                int kalorien_int = 0;
                kalorien_int = (int) kalorien_zahl;
                kalorien = String.valueOf(kalorien_int);
            }
            if (sb.charAt(i) == '.' && (sb.charAt(i+1) != '0')) {
                DecimalFormat kalorien_double = new DecimalFormat(".00");
                kalorien_double.format(kalorien_zahl);
                kalorien = kalorien_double.format(kalorien_zahl);
            }
        }
        return kalorien;
    }

    public String ProPortionCarbsRechner(double[] carbsArray) {
        double carbs_zahl = 0;
        for (double value : carbsArray) {
            carbs_zahl += value;
        }
        StringBuilder sb = new StringBuilder(String.valueOf(carbs_zahl));
        String carbs = "";  // Endergebnis
        for (int i = 0; i < String.valueOf(carbs_zahl).length(); i++) {
            if (sb.charAt(i) == '.' && (sb.charAt(i+1) == '0')) {
                int carbs_int = 0;
                carbs_int = (int) carbs_zahl;
                carbs = String.valueOf(carbs_int);
            }
            if (sb.charAt(i) == '.' && (sb.charAt(i+1) != '0')) {
                DecimalFormat carbs_double = new DecimalFormat(".00");
                carbs_double.format(carbs_zahl);
                carbs = carbs_double.format(carbs_zahl);
            }
        }
        return carbs;
    }

    public String ProPortionFetteRechner(double[] fetteArray) {
        double fette_zahl = 0;
        for (double value : fetteArray) {
            fette_zahl += value;
        }
        StringBuilder sb = new StringBuilder(String.valueOf(fette_zahl));
        String fette = "";  // Endergebnis
        for (int i = 0; i < String.valueOf(fette_zahl).length(); i++) {
            if (sb.charAt(i) == '.' && (sb.charAt(i + 1) == '0')) {
                int fette_int = 0;
                fette_int = (int) fette_zahl;
                fette = String.valueOf(fette_int);
            }
            if (sb.charAt(i) == '.' && (sb.charAt(i + 1) != '0')) {
                DecimalFormat fette_double = new DecimalFormat(".00");
                fette_double.format(fette_zahl);
                fette = fette_double.format(fette_zahl);
            }
        }
        return fette;
    }

    public String ProPortionEiweissRechner(double[] eiweissArray) {
        double eiweiss_zahl = 0;
        for (double value : eiweissArray) {
            eiweiss_zahl += value;
        }
        StringBuilder sb = new StringBuilder(String.valueOf(eiweiss_zahl));
        String eiweiss = "";  // Endergebnis
        for (int i = 0; i < String.valueOf(eiweiss_zahl).length(); i++) {
            if (sb.charAt(i) == '.' && (sb.charAt(i + 1) == '0')) {
                int eiweiss_int = 0;
                eiweiss_int = (int) eiweiss_zahl;
                eiweiss = String.valueOf(eiweiss_int);
            }
            if (sb.charAt(i) == '.' && (sb.charAt(i + 1) != '0')) {
                DecimalFormat eiweiss_double = new DecimalFormat(".00");
                eiweiss_double.format(eiweiss_zahl);
                eiweiss = eiweiss_double.format(eiweiss_zahl);
            }
        }
        return eiweiss;
    }

    @Override
    public void onBackPressed() { }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btnMain_Nahrunghinzu) {
            Intent zuNahrungHinzuMenue = new Intent(this, Nahrungsliste.class);
            startActivity(zuNahrungHinzuMenue);
            this.finish();
        }
        if (v.getId() == R.id.btnMain_fertig) {
            confirmDialog_main();
        }
        if (v.getId() == R.id.btn_Schrittzaehler) {
            Intent zuSchrittzaehler = new Intent(this, Schrittzaehler.class);
            startActivity(zuSchrittzaehler);
            this.finish();
        }
        if (v.getId() == R.id.btn_TaetigkeitHinzu) {
            Intent zuTaetigkeitenliste = new Intent(this, Taetigkeitenliste.class);
            startActivity(zuTaetigkeitenliste);
            this.finish();
        }
    }

}

