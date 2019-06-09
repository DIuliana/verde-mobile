package com.verde.ui.fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.verde.R;
import com.verde.data.Plant;

import java.util.List;

public class PlantListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<Plant> plants;
    private OnItemClickListener clickListener;


    interface OnItemClickListener {
        void onItemClick(Plant plant, View view);
    }

    public PlantListAdapter(List<Plant> plants, OnItemClickListener clickListener) {
        this.plants = plants;
        this.clickListener = clickListener;
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.device, parent, false);
        return new ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ((ViewHolder) holder).bind(plants.get(position), clickListener);
    }

    @Override
    public int getItemCount() {
        return plants.size();
    }


    class ViewHolder extends RecyclerView.ViewHolder {

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
        }

        public void bind(Plant plant, OnItemClickListener clickListener) {
            TextView displayName = itemView.findViewById(R.id.deviceName);
            TextView displaySpecies = itemView.findViewById(R.id.deviceSpecies);
            TextView displayHumidity = itemView.findViewById(R.id.deviceHumidity);
            displayName.setText(plant.name);
            displaySpecies.setText(plant.species);
            displayHumidity.setText(String.valueOf(plant.humidity));

            itemView.setOnClickListener(v -> clickListener.onItemClick(plant, itemView));
        }


    }


}
