package com.example.locationsaver;

    import androidx.lifecycle.LiveData;
    import androidx.lifecycle.MutableLiveData;
    import androidx.room.Dao;
    import androidx.room.Insert;
    import androidx.room.Query;

    import java.util.List;

    @Dao
    public interface LocationDao
    {

        @Query("SELECT * FROM Locations")
        LiveData<List<LocationDetails>> getAllLocations();

        @Insert
        void insertAll(LocationDetails... locationDetails);


    }
