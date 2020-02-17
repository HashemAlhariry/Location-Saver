package com.example.locationsaver;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;


import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatDialogFragment;

public class Dialog extends AppCompatDialogFragment {
    private EditText editTextlocation;
    private DialogListener dialogListener;
    private double longitude ;
    private double latitude ;
    private AppDatabase mDb;

    public Dialog(double longitude, double latitude, AppDatabase mDb) {
        this.longitude = longitude;
        this.latitude = latitude;
        this.mDb = mDb;
    }

    @Override
    public android.app.Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view  = inflater.inflate(R.layout.layout_dialog,null);


        builder.setView(view).setTitle("Enter Name").setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        }).setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                    String locationName=editTextlocation.getText().toString();
                    dialogListener.applyTexts(locationName);
                    //mDb.locationDao().insertAll(new LocationDetails(locationName,latitude,longitude));
                    MyViewModel vm=new MyViewModel(getContext());
                    vm.insertlocations(new LocationDetails(locationName,latitude,longitude));

            }
        });
        editTextlocation = (EditText)view.findViewById(R.id.edit_location_name);
        return builder.create();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            dialogListener =(DialogListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()+"must implement DialogListener");
        }
    }

    public  interface DialogListener{
        void applyTexts(String locationName);
    }
}
