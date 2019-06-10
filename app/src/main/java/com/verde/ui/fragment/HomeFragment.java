package com.verde.ui.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.RecyclerView;

import com.example.verde.R;
import com.verde.data.Plant;
import com.verde.ui.model.PlantListViewModel;
import com.verde.ui.model.WebSocketDataViewModel;

import java.util.List;

import static androidx.navigation.Navigation.findNavController;


public class HomeFragment extends Fragment implements PlantListAdapter.OnItemClickListener {


    private PlantListViewModel plantListViewModel;
    private WebSocketDataViewModel webSocketDataViewModel;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        plantListViewModel = ViewModelProviders.of(this).get(PlantListViewModel.class);
        webSocketDataViewModel = ViewModelProviders.of(this).get(WebSocketDataViewModel.class);
        createWebSocket();
    }

    private void createWebSocket() {
        new VerdeWebSocket("Verde_31529819901", webSocketDataViewModel);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(com.example.verde.R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        view.findViewById(R.id.addFab).setOnClickListener(v ->
                findNavController(v).navigate(R.id.action_homeFragment2_to_addNewDevice));

        plantListViewModel.getPlants().observe(this, plants ->
                populatePlantList(plants, view.findViewById(R.id.deviceRecyclerView)));
    }

    private void populatePlantList(List<Plant> plants, View view) {
        ((RecyclerView) view).setAdapter(new PlantListAdapter(this, plants, webSocketDataViewModel));
    }


    @Override
    public void onItemClick(Plant plant, View view) {
        Toast.makeText(getContext(), "Go to details", Toast.LENGTH_SHORT).show();

    }
}
