package com.verde.ui.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.verde.R;

import static androidx.navigation.Navigation.findNavController;


public class HomeFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(com.example.verde.R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        // Navigate to add people
         view.findViewById(R.id.addFab).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                findNavController(v).navigate(R.id.action_homeFragment2_to_addNewDevice);
            }
        });

    }
}
