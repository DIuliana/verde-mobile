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
import com.verde.ui.model.WebSocketDataViewModel;

import java.util.List;

public class PlantListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<Plant> plants;
    private OnItemClickListener clickListener;
    WebSocketDataViewModel webSocketDataViewModel;
    Fragment fragment;


    public interface OnItemClickListener {
        void onItemClick(Plant plant, View view);
    }

    public PlantListAdapter(Fragment fragment, List<Plant> plants, WebSocketDataViewModel webSocketDataViewModel) {
        this.plants = plants;
        this.clickListener = (OnItemClickListener) fragment;
        this.webSocketDataViewModel = webSocketDataViewModel;
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
        ((ViewHolder) holder).bind(plants.get(position), clickListener, webSocketDataViewModel);
    }

    @Override
    public int getItemCount() {
        return plants.size();
    }


    class ViewHolder extends RecyclerView.ViewHolder {

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
        }

        public void bind(Plant plant, OnItemClickListener clickListener, WebSocketDataViewModel webSocketDataViewModel) {
            TextView displayName = itemView.findViewById(R.id.deviceName);
            TextView displaySpecies = itemView.findViewById(R.id.deviceSpecies);
            TextView displayHumidity = itemView.findViewById(R.id.deviceHumidity);
            displayName.setText(plant.name);
            displaySpecies.setText(plant.species);

            webSocketDataViewModel.getMessagesMap().observe(fragment, stringStringMap -> displayHumidity.setText(stringStringMap.get(plant.potId)));

            itemView.setOnClickListener(v -> clickListener.onItemClick(plant, itemView));
        }


    }


}
