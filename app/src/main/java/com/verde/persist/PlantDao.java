package com.verde.persist;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.verde.data.Plant;

import java.util.List;

@Dao
public interface PlantDao {


    @Query("SELECT * FROM Plant")
    LiveData<List<Plant>> getAll();


    @Query("SELECT * FROM Plant WHERE potId = :id")
    LiveData<Plant> find(String id);


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Plant plant);


}
