package com.verde.ui.fragment.components;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.verde.R;
import com.verde.data.Plant;
import com.verde.data.VerdeSocketProvider;
import com.verde.data.VerdeWebSocket;

import java.util.List;

public class PlantListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<Plant> plants;
    private OnItemClickListener clickListener;
    VerdeSocketProvider verdeSocketProvider;
    Fragment fragment;


    public interface OnItemClickListener {
        void onItemClick(Plant plant, View view);
    }

    public PlantListAdapter(Fragment fragment, List<Plant> plants, VerdeSocketProvider verdeSocketProvider) {
        this.plants = plants;
        this.clickListener = (OnItemClickListener) fragment;
        this.verdeSocketProvider = verdeSocketProvider;
        this.fragment = fragment;
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
        ((ViewHolder) holder).bind(plants.get(position), clickListener, verdeSocketProvider);
    }

    @Override
    public int getItemCount() {
        return plants.size();
    }


    class ViewHolder extends RecyclerView.ViewHolder {

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
        }

        public void bind(Plant plant, OnItemClickListener clickListener, VerdeSocketProvider verdeSocketProvider) {
            TextView displayName = itemView.findViewById(R.id.deviceName);
            TextView displaySpecies = itemView.findViewById(R.id.deviceSpecies);
            TextView displayHumidity = itemView.findViewById(R.id.deviceHumidity);
            displayName.setText(plant.name);
            displaySpecies.setText(plant.species);

            VerdeWebSocket socketByPotId = verdeSocketProvider.getSocketByPotId(plant.potId);
            socketByPotId.getMessage().observe(fragment, message -> displayHumidity.setText(message));

            itemView.setOnClickListener(v -> clickListener.onItemClick(plant, itemView));
        }


    }


}
