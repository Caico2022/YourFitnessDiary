package com.studienarbeit.YourFitnessDiary.Adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.studienarbeit.YourFitnessDiary.GetterSetter.GetterSetterNahrungsmittel;
import com.studienarbeit.YourFitnessDiary.Main.MainNahrungBearbeitenActivity;
import com.studienarbeit.YourFitnessDiary.Main.MainNahrungBearbeitenOFFActivity;
import com.studienarbeit.YourFitnessDiary.R;

import java.util.ArrayList;

public class MainAdapter extends RecyclerView.Adapter<MainAdapter.MyViewHolder> {
    private ArrayList<GetterSetterNahrungsmittel> nahrungList = new ArrayList<>();
    private Context context;
    private Activity activity;

    public MainAdapter(Activity activity, Context context, ArrayList<GetterSetterNahrungsmittel> nahrungList) {
        this.activity = activity;
        this.context = context;
        this.nahrungList = nahrungList;
    }

    @NonNull
    @Override
    public MainAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.recyclerview_main, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MainAdapter.MyViewHolder holder, int position) {
        GetterSetterNahrungsmittel nList = nahrungList.get(position);
        holder.itemView.setTag(String.valueOf(nList.getMainID()));  // für swiped

        holder.tV2main_Beschreibung.setText(String.valueOf(nList.getBeschreibung()));
        holder.tV2main_Portionsmenge.setText(String.valueOf(nList.getPortionsmenge()));
        holder.tV2main_Kcal.setText(String.valueOf(nList.getNewKcal()));
        holder.tV2main_Carbs.setText(String.valueOf(nList.getNewCarbs()));
        holder.tV2main_Fette.setText(String.valueOf(nList.getNewFette()));
        holder.tV2main_Eiweiss.setText(String.valueOf(nList.getNewEiweiss()));
        holder.mainLayoutMainActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent;
                if (nList.getBarcode() != null) {
                    // Barcode-Artikel
                    Log.i("creation", "Main Barcode-Artikel");
                    intent = new Intent(context, MainNahrungBearbeitenOFFActivity.class);
                    intent.putExtra("id", String.valueOf(nList.getId()));
                    intent.putExtra("marke", String.valueOf(nList.getMarke()));
                    intent.putExtra("beschreibung", String.valueOf(nList.getBeschreibung()));
                    intent.putExtra("portionsgroesse", String.valueOf(nList.getPortionsgroesse()));
                    intent.putExtra("anzahlPortionen", String.valueOf(nList.getPortionen()));
                    intent.putExtra("kcal", String.valueOf(nList.getKcal()));
                    intent.putExtra("carbs", String.valueOf(nList.getcarbs()));
                    intent.putExtra("fette", String.valueOf(nList.getfette()));
                    intent.putExtra("eiweiss", String.valueOf(nList.geteiweiss()));
                    intent.putExtra("einheit", String.valueOf(nList.getEinheit()));
                    intent.putExtra("portionsmenge", String.valueOf(nList.getPortionsmenge()));
                    intent.putExtra("mainID", String.valueOf(nList.getMainID()));
                    intent.putExtra("newKcal", String.valueOf(nList.getNewKcal()));
                    intent.putExtra("newCarbs", String.valueOf(nList.getNewCarbs()));
                    intent.putExtra("newFette", String.valueOf(nList.getNewFette()));
                    intent.putExtra("newEiweiss", String.valueOf(nList.getNewEiweiss()));
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
                    Log.i("creation", "Main KEIN Barcode-Artikel");
                    intent = new Intent(context, MainNahrungBearbeitenActivity.class);
                    intent.putExtra("id", String.valueOf(nList.getId()));
                    intent.putExtra("marke", String.valueOf(nList.getMarke()));
                    intent.putExtra("beschreibung", String.valueOf(nList.getBeschreibung()));
                    intent.putExtra("portionsgroesse", String.valueOf(nList.getPortionsgroesse()));
                    intent.putExtra("portionen", String.valueOf(nList.getPortionen()));
                    intent.putExtra("kcal", String.valueOf(nList.getKcal()));
                    intent.putExtra("fette", String.valueOf(nList.getfette()));
                    intent.putExtra("carbs", String.valueOf(nList.getcarbs()));
                    intent.putExtra("eiweiss", String.valueOf(nList.geteiweiss()));
                    intent.putExtra("einheit", String.valueOf(nList.getEinheit()));
                    intent.putExtra("portionsmenge", String.valueOf(nList.getPortionsmenge()));
                    intent.putExtra("mainID", String.valueOf(nList.getMainID()));
                    intent.putExtra("newKcal", String.valueOf(nList.getNewKcal()));
                    intent.putExtra("newFette", String.valueOf(nList.getNewFette()));
                    intent.putExtra("newCarbs", String.valueOf(nList.getNewCarbs()));
                    intent.putExtra("newEiweiss", String.valueOf(nList.getNewEiweiss()));
                }
                activity.startActivityForResult(intent, 1);
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
        TextView tV2main_Beschreibung, tV2main_Kcal, tV2main_Portionsmenge,
                tV2main_Carbs, tV2main_Fette, tV2main_Eiweiss;
        CardView mainLayoutMainActivity;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tV2main_Beschreibung = itemView.findViewById(R.id.tV2main_Beschreibung);
            tV2main_Kcal = itemView.findViewById(R.id.tV2main_Kcal);
            tV2main_Portionsmenge = itemView.findViewById(R.id.tV2main_Portionsmenge);
            tV2main_Carbs = itemView.findViewById(R.id.tV2main_Carbs);
            tV2main_Fette = itemView.findViewById(R.id.tV2main_Fette);
            tV2main_Eiweiss = itemView.findViewById(R.id.tV2main_Eiweiss);
            mainLayoutMainActivity = itemView.findViewById(R.id.mainLayoutMainActivity);
        }
    }
}
