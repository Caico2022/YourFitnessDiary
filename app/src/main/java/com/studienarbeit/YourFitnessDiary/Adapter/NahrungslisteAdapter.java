package com.studienarbeit.YourFitnessDiary.Adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.studienarbeit.YourFitnessDiary.DBHelper;
import com.studienarbeit.YourFitnessDiary.GetterSetter.GetterSetterNahrungsmittel;
import com.studienarbeit.YourFitnessDiary.Nahrungsliste.NahrungsmittelBearbeitenActivity;
import com.studienarbeit.YourFitnessDiary.Nahrungsliste.NahrungsmittelBearbeitenOFFActivity;
import com.studienarbeit.YourFitnessDiary.R;

import java.util.ArrayList;
import java.util.List;

public class NahrungslisteAdapter extends RecyclerView.Adapter<NahrungslisteAdapter.MyViewHolder> {
    DBHelper dbHelper;
    private Activity activity;
    private Context context;
    private List<GetterSetterNahrungsmittel> nahrungList = new ArrayList<>();

    // Konstruktor
    public NahrungslisteAdapter(Activity activity, Context context, ArrayList<GetterSetterNahrungsmittel> nahrungList) {
        // Wenn die Klasse in Nahrungsliste.java initialisiert wird, sollen alle Arraylists übergeben werden
        this.activity = activity;
        this.context = context;
        this.nahrungList = nahrungList;
        this.nahrungList = new ArrayList<>(nahrungList);
    }

    public void setFilterList(List<GetterSetterNahrungsmittel> filteredList) {
        this.nahrungList = filteredList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {  // Als erstes aufrufen
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.recyclerview_nahrungsliste, parent, false);  // recyclerview_nahrungsliste soll erweitert werden
        return new MyViewHolder(view);
    }

    // Nimmt Daten vom Konstruktor, der die Daten wiederum von Nahrungsliste.java/onCreate hat
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, @SuppressLint("RecyclerView") int position) {
        GetterSetterNahrungsmittel nList = nahrungList.get(position);
        holder.itemView.setTag(String.valueOf(nList.getId()));  // Für OnSwiped bei Nahrungsliste.java
        holder.tV2_id.setText(String.valueOf(nList.getId()));
        holder.tV2_Marke.setText(String.valueOf(nList.getMarke()));
        holder.tV2_Beschreibung.setText(String.valueOf(nList.getBeschreibung()));
        holder.tV2_Kcal.setText(String.valueOf(nList.getKcal100g()));
        holder.tV2_Portionsmenge.setText(String.valueOf(nList.getPortionsmenge()));
        holder.mainLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent;
                if (nList.getBarcode() != null) {
                    // Barcode-Artikel
                    Log.i("creation", "Nahrungsliste Barcode-Artikel");
                    intent = new Intent(context, NahrungsmittelBearbeitenOFFActivity.class);
                    intent.putExtra("id", String.valueOf(nList.getId()));
                    intent.putExtra("marke", String.valueOf(nList.getMarke()));
                    intent.putExtra("beschreibung", String.valueOf(nList.getBeschreibung()));
                    intent.putExtra("portionsgroesse", String.valueOf(nList.getPortionsgroesse()));
                    intent.putExtra("portionen", String.valueOf(nList.getPortionen()));
                    intent.putExtra("kcal", String.valueOf(nList.getKcal()));
                    intent.putExtra("carbs", String.valueOf(nList.getcarbs()));
                    intent.putExtra("fette", String.valueOf(nList.getfette()));
                    intent.putExtra("eiweiss", String.valueOf(nList.geteiweiss()));
                    intent.putExtra("einheit", String.valueOf(nList.getEinheit()));
                    intent.putExtra("portionsmenge", String.valueOf(nList.getPortionsmenge()));
                    intent.putExtra("barcode", String.valueOf(nList.getBarcode()));
                    intent.putExtra("servingSize", String.valueOf(nList.getservingSize()));
                    intent.putExtra("quantitySize", String.valueOf(nList.getquantitySize()));
                    intent.putExtra("kcal100g", String.valueOf(nList.getKcal100g()));
                    intent.putExtra("carbs100g", String.valueOf(nList.getCarbs100g()));
                    intent.putExtra("fette100g", String.valueOf(nList.getFette100g()));
                    intent.putExtra("eiweiss100g", String.valueOf(nList.getEiweiss100g()));
                    intent.putExtra("ausgewSize", String.valueOf(nList.getAusgewSize()));
                } else {
                    // Kein Barcode-Artikel
                    Log.i("creation", "Nahrungsliste KEIN Barcode-Artikel");
                    intent = new Intent(context, NahrungsmittelBearbeitenActivity.class);
                    intent.putExtra("id", String.valueOf(nList.getId()));
                    intent.putExtra("marke", String.valueOf(nList.getMarke()));
                    intent.putExtra("beschreibung", String.valueOf(nList.getBeschreibung()));
                    intent.putExtra("portionsgroesse", String.valueOf(nList.getPortionsgroesse()));
                    intent.putExtra("portionen", String.valueOf(nList.getPortionen()));
                    intent.putExtra("kcal", String.valueOf(nList.getKcal()));
                    intent.putExtra("carbs", String.valueOf(nList.getcarbs()));
                    intent.putExtra("fette", String.valueOf(nList.getfette()));
                    intent.putExtra("eiweiss", String.valueOf(nList.geteiweiss()));
                    intent.putExtra("einheit", String.valueOf(nList.getEinheit()));
                    intent.putExtra("portionsmenge", String.valueOf(nList.getPortionsmenge()));
                    intent.putExtra("kcal100g", String.valueOf(nList.getKcal100g()));
                    intent.putExtra("carbs100g", String.valueOf(nList.getCarbs100g()));
                    intent.putExtra("fette100g", String.valueOf(nList.getFette100g()));
                    intent.putExtra("eiweiss100g", String.valueOf(nList.getEiweiss100g()));


                }
                activity.startActivityForResult(intent, 1);
            }
        });
        holder.imgV_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dbHelper = new DBHelper(context);
                if (nList.getBarcode() != null) {
                    // Barcode-Artikel
                    Log.i("creation", "Nahrungsliste zu main, Barcode-Artikel");
                    dbHelper = new DBHelper(context);
                    dbHelper.addNahrungMainOff(String.valueOf(nList.getId()),
                            String.valueOf(nList.getMarke()),
                            String.valueOf(nList.getBeschreibung()),
                            String.valueOf(nList.getPortionsgroesse()),
                            String.valueOf(nList.getPortionen()),
                            String.valueOf(nList.getKcal()),
                            String.valueOf(nList.getcarbs()),
                            String.valueOf(nList.getfette()),
                            String.valueOf(nList.geteiweiss()),
                            String.valueOf(nList.getEinheit()),
                            String.valueOf(nList.getPortionsmenge()),
                            // Nicht getNewKcal(), da diese Spalte erst in MainBearbeiten befüllt wird
                            String.valueOf(nList.getKcal()),
                            String.valueOf(nList.getcarbs()),
                            String.valueOf(nList.getfette()),
                            String.valueOf(nList.geteiweiss()),
                            String.valueOf(nList.getBarcode()),
                            String.valueOf(nList.getservingSize()),
                            String.valueOf(nList.getquantitySize()),
                            String.valueOf(nList.getKcal100g()),
                            String.valueOf(nList.getCarbs100g()),
                            String.valueOf(nList.getFette100g()),
                            String.valueOf(nList.getEiweiss100g()),
                            String.valueOf(nList.getAusgewSize()));
                } else {
                    // Kein Barcode-Artikel
                    Log.i("creation", "Nahrungsliste zu main, KEIN Barcode-Artikel");
                    dbHelper.addNahrungMain(String.valueOf(nList.getId()),
                            // newKcal werden bei mainBearbeiten aktualisiert
                            String.valueOf(nList.getMarke()),
                            String.valueOf(nList.getBeschreibung()),
                            String.valueOf(nList.getPortionsgroesse()),
                            String.valueOf(nList.getPortionen()),
                            String.valueOf(nList.getKcal()),
                            String.valueOf(nList.getcarbs()),
                            String.valueOf(nList.getfette()),
                            String.valueOf(nList.geteiweiss()),
                            String.valueOf(nList.getEinheit()),
                            String.valueOf(nList.getPortionsmenge()),
                            String.valueOf(nList.getKcal()),
                            String.valueOf(nList.getcarbs()),
                            String.valueOf(nList.getfette()),
                            String.valueOf(nList.geteiweiss()));
                }
            }
        });
    }


    @Override
    public int getItemCount() {
        return nahrungList.size();
    }

    public void removeItem(int position) {
        nahrungList.remove(position);
        notifyItemRemoved(position);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tV2_id, tV2_Beschreibung, tV2_Kcal, tV2_Marke, tV2_Portionsmenge;
        CardView mainLayout;
        ImageView imgV_add;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tV2_id = itemView.findViewById(R.id.tV2_id);
            tV2_Beschreibung = itemView.findViewById(R.id.tV2_Beschreibung);
            tV2_Kcal = itemView.findViewById(R.id.tV2_Kcal);
            tV2_Marke = itemView.findViewById(R.id.tV2_Marke);
            tV2_Portionsmenge = itemView.findViewById(R.id.tV2_Portionsmenge);
            mainLayout = itemView.findViewById(R.id.mainLayout);
            imgV_add = itemView.findViewById(R.id.imgV_add);
        }
    }
}

