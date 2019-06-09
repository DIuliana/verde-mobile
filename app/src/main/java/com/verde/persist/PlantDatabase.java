package com.verde.persist;

import android.app.Application;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.verde.data.Plant;

@Database(entities = {Plant.class}, version = 1)
public abstract class PlantDatabase extends RoomDatabase {

    private static PlantDatabase INSTANCE;

    public abstract PlantDao productDao();

    public static PlantDatabase getInstance(Application application) {
        if (INSTANCE == null) {
            synchronized (PlantDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE =
                            Room.databaseBuilder(application.getApplicationContext(),
                                    PlantDatabase.class,
                                    "plant_database.db").allowMainThreadQueries().build();
                }
            }
        }
        return INSTANCE;
    }

}
