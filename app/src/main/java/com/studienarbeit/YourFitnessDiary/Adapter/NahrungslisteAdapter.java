package com.studienarbeit.YourFitnessDiary.Adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.studienarbeit.YourFitnessDiary.Nahrungsmittel.BearbeitenActivity;
import com.studienarbeit.YourFitnessDiary.DBHelper;
import com.studienarbeit.YourFitnessDiary.GetterSetter.GetterSetterNahrungsmittel;
import com.studienarbeit.YourFitnessDiary.R;

import java.util.ArrayList;
import java.util.List;

public class NahrungslisteAdapter extends RecyclerView.Adapter<NahrungslisteAdapter.MyViewHolder> {
    DBHelper myDB;
    Activity activity;
    Animation translate_anim;
    private Context context;
    private List<GetterSetterNahrungsmittel> nahrungList = new ArrayList<>();
    private List<GetterSetterNahrungsmittel> nahrungListAll = new ArrayList<>();
    ArrayList<GetterSetterNahrungsmittel> nahrungListMain = new ArrayList<>();
    ArrayList<String> idsList = new ArrayList<>();


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

    // HIER ÜBERGABE DER DATEN AN DIE RECYCLERVIEW
    // Nimmt Daten vom Konstruktor, der die Daten wiederum von Nahrungsliste.java/onCreate hat
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, @SuppressLint("RecyclerView") int position) {
        GetterSetterNahrungsmittel nList = nahrungList.get(position);
        holder.itemView.setTag(String.valueOf(nList.getId()));  // Für OnSwiped bei Nahrungsliste.java

        holder.tV2_id.setText(String.valueOf(nList.getId()));
        holder.tV2_Marke.setText(String.valueOf(nList.getMarke()));
        holder.tV2_Beschreibung.setText(String.valueOf(nList.getBeschreibung()));
        holder.tV2_Kcal.setText(String.valueOf(nList.getKcal()));
        holder.tV2_Portionsmenge.setText(String.valueOf(nList.getPortionsmenge()));
        holder.mainLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, BearbeitenActivity.class);
                intent.putExtra("id", String.valueOf(nList.getId()));
                intent.putExtra("marke", String.valueOf(nList.getMarke()));
                intent.putExtra("beschreibung", String.valueOf(nList.getBeschreibung()));
                intent.putExtra("portionsgroesse", String.valueOf(nList.getPortionsgroesse()));
                intent.putExtra("portionen", String.valueOf(nList.getPortionen()));
                intent.putExtra("kcal", String.valueOf(nList.getKcal()));
                intent.putExtra("fetteGes", String.valueOf(nList.getFetteGes()));
                intent.putExtra("carbsGes", String.valueOf(nList.getCarbsGes()));
                intent.putExtra("eiweissGes", String.valueOf(nList.getEiweissGes()));
                intent.putExtra("einheit", String.valueOf(nList.getEinheit()));
                intent.putExtra("portionsmenge", String.valueOf(nList.getPortionsmenge()));
                activity.startActivityForResult(intent, 1);
            }
        });
        holder.imgbtn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                myDB = new DBHelper(context);
                DBHelper dbHelper = new DBHelper(context);
                String[] rowData = new String[0];
                myDB.addNahrung_main(String.valueOf(nList.getId()),
                        String.valueOf(nList.getMarke()),
                        String.valueOf(nList.getBeschreibung()),
                        String.valueOf(nList.getPortionsgroesse()),
                        String.valueOf(nList.getPortionen()),
                        String.valueOf(nList.getKcal()),
                        String.valueOf(nList.getCarbsGes()),
                        String.valueOf(nList.getFetteGes()),
                        String.valueOf(nList.getEiweissGes()),
                        String.valueOf(nList.getEinheit()),
                        String.valueOf(nList.getPortionsmenge()),
                        String.valueOf(nList.getKcal()),
                        String.valueOf(nList.getCarbsGes()),
                        String.valueOf(nList.getFetteGes()),
                        String.valueOf(nList.getEiweissGes()));
            }
        });
    }


    @Override
    public int getItemCount() {
        return nahrungList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tV2_id, tV2_Beschreibung, tV2_Kcal, tV2_Marke, tV2_Portionsmenge;
        LinearLayout mainLayout;
        ImageButton imgbtn_add;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tV2_id = itemView.findViewById(R.id.tV2_id);
            tV2_Beschreibung = itemView.findViewById(R.id.tV2_Beschreibung);
            tV2_Kcal = itemView.findViewById(R.id.tV2_Kcal);
            tV2_Marke = itemView.findViewById(R.id.tV2_Marke);
            tV2_Portionsmenge = itemView.findViewById(R.id.tV2_Portionsmenge);
            mainLayout = itemView.findViewById(R.id.mainLayout);
            imgbtn_add = itemView.findViewById(R.id.imgbtn_add);
        }
    }
}

