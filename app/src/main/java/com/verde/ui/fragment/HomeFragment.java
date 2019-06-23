package com.verde.ui.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.RecyclerView;

import com.example.verde.R;
import com.verde.data.Plant;
import com.verde.data.VerdeSocketProvider;
import com.verde.ui.fragment.components.PlantListAdapter;
import com.verde.ui.model.PlantIdViewModel;
import com.verde.ui.model.PlantListViewModel;
import com.verde.ui.model.PlantViewModel;

import java.util.List;

import static androidx.navigation.Navigation.findNavController;


public class HomeFragment extends Fragment implements PlantListAdapter.OnItemClickListener {


    private PlantListViewModel plantListViewModel;
    private PlantIdViewModel plantIdViewModel;
    private PlantViewModel plantViewModel;
    private VerdeSocketProvider verdeSocketProvider;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        plantListViewModel = ViewModelProviders.of(this).get(PlantListViewModel.class);
        verdeSocketProvider = ViewModelProviders.of(getActivity()).get(VerdeSocketProvider.class);
        plantIdViewModel = ViewModelProviders.of(getActivity()).get(PlantIdViewModel.class);
        plantViewModel = ViewModelProviders.of(getActivity()).get(PlantViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(com.example.verde.R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //redirect at button click
        view.findViewById(R.id.addFab).setOnClickListener(v ->
                findNavController(v).navigate(R.id.action_homeFragment2_to_addNewDevice));

        //populate the plant list
        plantListViewModel.getPlants().observe(this, plants -> {
            populatePlantList(plants, view.findViewById(R.id.deviceRecyclerView));
            createWebSockets(plants);
        });

    }

    private void createWebSockets(List<Plant> plants) {
        plants.forEach(plant -> createWebSocketIfItDoesNotExist(plant));
    }

    private void createWebSocketIfItDoesNotExist(Plant plant) {

        if (verdeSocketProvider.getSocketByPotId(plant.potId) == null) {
            verdeSocketProvider.createWebSocket(plant.potId);
        }
    }

    private void populatePlantList(List<Plant> plants, View view) {
        ((RecyclerView) view).setAdapter(new PlantListAdapter(this, plants, verdeSocketProvider));
    }


    @Override
    public void onItemClick(Plant plant, View view) {

        plantIdViewModel.setPotId(plant.potId);
        plantViewModel.setPlantName(plant.name);
        findNavController(view).navigate(R.id.action_homeFragment_to_plantDetailsFragment);

    }
}
