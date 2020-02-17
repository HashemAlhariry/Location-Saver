package com.example.locationsaver;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {LocationDetails.class},version = 1,exportSchema = false)
public abstract class AppDatabase extends RoomDatabase
{
    private static final String DATABASE_NAME="Locationsaver";
    private static final Object LOCK = new Object();
    private static AppDatabase sInstance;

    public static AppDatabase getInstance(Context context)
    {
        if(sInstance==null)
        {
            synchronized (LOCK){
                sInstance= Room.databaseBuilder(context.getApplicationContext(),AppDatabase.class,AppDatabase.DATABASE_NAME)
                        .allowMainThreadQueries()
                        .build();
            }
        }
        return sInstance;

    }

    public abstract LocationDao locationDao();


    public static void destroyInstance() {
        sInstance = null;
    }

}

