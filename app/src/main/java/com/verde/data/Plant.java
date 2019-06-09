package com.verde.data;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Plant {

    @PrimaryKey(autoGenerate = true)
    @NonNull
    public int id;
    public String potId;
    public String name;
    public String species;
    public double humidity = 60;


    public Plant(String potId, String name, String species) {
        this.potId = potId;
        this.name = name;
        this.humidity = humidity;
        this.species = species;
    }
}
