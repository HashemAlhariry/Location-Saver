package com.example.locationsaver;

import android.content.Context;
import android.os.AsyncTask;

import java.util.List;
import java.util.concurrent.ExecutionException;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class MyViewModel extends ViewModel {
    private MutableLiveData<List<LocationDetails>> locations;
    private Context context;
    AppDatabase db;

    public MyViewModel(Context context) {
        this.context = context;
    }

    public LiveData<List<LocationDetails>> getLocations() {
        if (locations == null) {
            locations = new MutableLiveData<List<LocationDetails>>();
            try {
                return loadLocations();
            } catch (ExecutionException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return null;
    }
    private LiveData<List<LocationDetails>> loadLocations() throws ExecutionException, InterruptedException {
        db = AppDatabase.getInstance(context);
        return new GetLocationsAsyncTask().execute().get();
    }
    public void insertlocations(LocationDetails ld){
        db=AppDatabase.getInstance(context);
        new InsertLocationAsyncTask().execute(ld);

    }
    private class GetLocationsAsyncTask extends AsyncTask<Void, Void,LiveData<List<LocationDetails>>>
    {
        @Override
        protected LiveData<List<LocationDetails>> doInBackground(Void... url) {
            return db.locationDao().getAllLocations();
        }
    }
    private class InsertLocationAsyncTask extends AsyncTask<LocationDetails, Void,Integer>
    {
        @Override
        protected Integer doInBackground(LocationDetails... details) {
             db.locationDao().insertAll(details);
            return 0;
        }
    }

}

