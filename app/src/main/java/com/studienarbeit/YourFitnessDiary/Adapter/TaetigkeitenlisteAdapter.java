package com.studienarbeit.YourFitnessDiary.Adapter;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.studienarbeit.YourFitnessDiary.GetterSetter.GetterSetterTaetigkeiten;
import com.studienarbeit.YourFitnessDiary.R;

import java.util.ArrayList;

public class TaetigkeitenlisteAdapter extends RecyclerView.Adapter<TaetigkeitenlisteAdapter.MyViewHolder> {

    private ArrayList<GetterSetterTaetigkeiten> taetigkeitenlist = new ArrayList<>();
    private Context context;
    private Activity activity;

    public TaetigkeitenlisteAdapter(Activity activity, Context context, ArrayList<GetterSetterTaetigkeiten> taetigkeitenlist) {
        this.activity = activity;
        this.context = context;
        this.taetigkeitenlist = taetigkeitenlist;
    }

    @NonNull
    @Override
    public TaetigkeitenlisteAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.recyclerview_taet_main, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TaetigkeitenlisteAdapter.MyViewHolder holder, int position) {
        GetterSetterTaetigkeiten tList = taetigkeitenlist.get(position);
        holder.itemView.setTag(String.valueOf(tList.getNewMainTaetID()));  // für swiped

        Log.e("creation", tList.getTaetBeschreibung());
        if (String.valueOf(tList.getTaetBeschreibung()).length() > 1) {
            holder.tVTaetMain_Beschreibung.setText(String.valueOf(tList.getTaetBeschreibung()));
            holder.tVTaetMain_Beschreibung.setVisibility(View.VISIBLE);
            holder.textView7Komma.setVisibility(View.VISIBLE);
        }
        holder.tVTaetNewMain_VerbrKcal.setText(String.valueOf(tList.getAnzahlKcal()));
    }

    @Override
    public int getItemCount() {
        return taetigkeitenlist.size();
    }

    public void removeItem(int position) {
        taetigkeitenlist.remove(position);
        notifyItemRemoved(position);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tVTaetMain_Beschreibung, tVTaetNewMain_VerbrKcal,
                textView7Komma;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tVTaetMain_Beschreibung = itemView.findViewById(R.id.tVTaetMain_Beschreibung);
            tVTaetNewMain_VerbrKcal = itemView.findViewById(R.id.tVTaetNewMain_VerbrKcal);
            textView7Komma = itemView.findViewById(R.id.textView7Komma);
        }
    }
}

