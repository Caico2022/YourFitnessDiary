package com.studienarbeit.YourFitnessDiary.Adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.studienarbeit.YourFitnessDiary.GetterSetter.GetterSetterNahrungsmittel;
import com.studienarbeit.YourFitnessDiary.Main.MainBearbeitenActivity;
import com.studienarbeit.YourFitnessDiary.R;

import java.util.ArrayList;

public class MainAdapter extends RecyclerView.Adapter<MainAdapter.MyViewHolder> {
    ArrayList<GetterSetterNahrungsmittel> nahrungList = new ArrayList<>();
    private Context maincontext;
    Activity mainactivity;

    public MainAdapter(Activity mainactivity, Context maincontext, ArrayList<GetterSetterNahrungsmittel> nahrungList) {
        this.mainactivity = mainactivity;
        this.maincontext = maincontext;
        this.nahrungList = nahrungList;
    }

    @NonNull
    @Override
    public MainAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(maincontext);
        View view = inflater.inflate(R.layout.recyclerview_main, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MainAdapter.MyViewHolder holder, int position) {
        GetterSetterNahrungsmittel nList = nahrungList.get(position);
        holder.itemView.setTag(String.valueOf(nList.getMainID()));  // für swiped

        holder.tV2main_id.setText(String.valueOf(nList.getMarke()));
        holder.tV2main_Marke.setText(String.valueOf(nList.getMarke()));
        holder.tV2main_Beschreibung.setText(String.valueOf(nList.getBeschreibung()));
        holder.tV2main_Portionsmenge.setText(String.valueOf(nList.getPortionsmenge()));
        holder.tV2main_Kcal.setText(String.valueOf(nList.getNewKcal()));
        holder.tV2main_Carbs.setText(String.valueOf(nList.getNewCarbs()));
        holder.tV2main_Fette.setText(String.valueOf(nList.getNewFette()));
        holder.tV2main_Eiweiss.setText(String.valueOf(nList.getNewEiweiss()));
        holder.mainLayoutMainActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(maincontext, MainBearbeitenActivity.class);
                intent.putExtra("id", String.valueOf(nList.getId()));
                intent.putExtra("marke", String.valueOf(nList.getMarke()));
                intent.putExtra("beschreibung", String.valueOf(nList.getBeschreibung()));
                intent.putExtra("portionsgroesse", String.valueOf(nList.getPortionsgroesse()));
                intent.putExtra("portionen", String.valueOf(nList.getPortionen()));
                intent.putExtra("kcal", String.valueOf(nList.getNewKcal()));
                intent.putExtra("fetteGes", String.valueOf(nList.getNewFette()));
                intent.putExtra("carbsGes", String.valueOf(nList.getNewCarbs()));
                intent.putExtra("eiweissGes", String.valueOf(nList.getNewEiweiss()));
                intent.putExtra("einheit", String.valueOf(nList.getEinheit()));
                intent.putExtra("portionsmenge", String.valueOf(nList.getPortionsmenge()));
                intent.putExtra("mainID", String.valueOf(nList.getMainID()));
                mainactivity.startActivityForResult(intent, 2);
            }
        });
    }

    @Override
    public int getItemCount() {
        return nahrungList.size();
    }



    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tV2main_Beschreibung, tV2main_id, tV2main_Kcal, tV2main_Portionsmenge,
                tV2main_Marke, tV2main_Carbs, tV2main_Fette, tV2main_Eiweiss,
                tVTaetNewMain_VerbrKcalTopic;
        LinearLayout mainLayoutMainActivity;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tV2main_id = itemView.findViewById(R.id.tV2main_id);
            tV2main_Marke = itemView.findViewById(R.id.tV2main_Marke);
            tV2main_Beschreibung = itemView.findViewById(R.id.tV2main_Beschreibung);
            tV2main_Kcal = itemView.findViewById(R.id.tV2main_Kcal);
            tV2main_Portionsmenge = itemView.findViewById(R.id.tV2main_Portionsmenge);
            tV2main_Carbs = itemView.findViewById(R.id.tV2main_Carbs);
            tV2main_Fette = itemView.findViewById(R.id.tV2main_Fette);
            tV2main_Eiweiss = itemView.findViewById(R.id.tV2main_Eiweiss);
            tVTaetNewMain_VerbrKcalTopic = itemView.findViewById(R.id.tVTaetNewMain_VerbrKcalTopic);
            mainLayoutMainActivity = itemView.findViewById(R.id.mainLayoutMainActivity);
        }
    }
}
