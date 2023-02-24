package com.studienarbeit.YourFitnessDiary.Adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.studienarbeit.YourFitnessDiary.GetterSetter.GetterSetterTaetigkeiten;
import com.studienarbeit.YourFitnessDiary.R;

import java.util.ArrayList;

public class TaetigkeitenMainAdapter extends RecyclerView.Adapter<TaetigkeitenMainAdapter.MyViewHolder> {

    ArrayList<GetterSetterTaetigkeiten> taetigkeitenlist = new ArrayList<>();
    private Context taetmaincontext;
    Activity taetactivity;

    public TaetigkeitenMainAdapter(Activity taetactivity, Context taetcontext, ArrayList<GetterSetterTaetigkeiten> taetigkeitenlist) {
        this.taetactivity = taetactivity;
        this.taetmaincontext = taetcontext;
        this.taetigkeitenlist = taetigkeitenlist;
    }

    @NonNull
    @Override
    public TaetigkeitenMainAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(taetmaincontext);
        View view = inflater.inflate(R.layout.recyclerview_taet_main, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TaetigkeitenMainAdapter.MyViewHolder holder, int position) {
        GetterSetterTaetigkeiten tList = taetigkeitenlist.get(position);
        holder.itemView.setTag(String.valueOf(tList.getNewMainTaetID()));  // für swiped

        if (String.valueOf(tList.getTaetBeschreibung()).equals("0")) {
            holder.tVTaetMain_Beschreibung.setVisibility(View.GONE);
        } else {
            holder.tVTaetMain_Beschreibung.setText(String.valueOf(tList.getTaetBeschreibung()));
        }

        if (String.valueOf(tList.getLaenge()).equals("0")) {
            holder.TaetMain_LinLayoutUnten.setVisibility(View.GONE);
            holder.LinearLayoutHorizontal.setForegroundGravity(View.TEXT_ALIGNMENT_CENTER);

        } else {
            holder.tVTaetMain_Laenge.setText(String.valueOf(tList.getTaetBeschreibung()));
            holder.tVTaetMain_Startzeit.setText(String.valueOf(tList.getStartzeit()));
        }

        holder.tVTaetNewMain_VerbrKcal.setText(String.valueOf(tList.getVerbrKcal()));
    }

    @Override
    public int getItemCount() {
        return taetigkeitenlist.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tVTaetMain_Beschreibung, tVTaetNewMain_VerbrKcal, tVTaetNewMain_ID,
                tVTaetMain_Laenge, tVTaetMain_Komma, tVTaetMain_Startzeit,
                tVTaetMain_LaengeTopic, tVTaetMain_StartzeitTopic;
        View TaetMain_LinLayoutUnten, LinearLayoutHorizontal;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tVTaetMain_Beschreibung = itemView.findViewById(R.id.tVTaetMain_Beschreibung);
            tVTaetNewMain_VerbrKcal = itemView.findViewById(R.id.tVTaetNewMain_VerbrKcal);
            tVTaetNewMain_ID = itemView.findViewById(R.id.tVTaetNewMain_ID);
            tVTaetMain_Laenge = itemView.findViewById(R.id.tVTaetMain_Laenge);
            tVTaetMain_Startzeit = itemView.findViewById(R.id.tVTaetMain_Startzeit);
            tVTaetMain_Komma = itemView.findViewById(R.id.tVTaetMain_Komma);
            tVTaetMain_LaengeTopic = itemView.findViewById(R.id.tVTaetMain_LaengeTopic);
            tVTaetMain_StartzeitTopic = itemView.findViewById(R.id.tVTaetMain_StartzeitTopic);
            TaetMain_LinLayoutUnten = itemView.findViewById(R.id.TaetMain_LinLayoutUnten);
            LinearLayoutHorizontal = itemView.findViewById(R.id.LinearLayoutHorizontal);
        }
    }
}

