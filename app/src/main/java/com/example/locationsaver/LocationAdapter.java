package com.example.locationsaver;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class LocationAdapter extends RecyclerView.Adapter<LocationAdapter.ViewHolder>
{

    List<LocationDetails> locations;
    private Context mContext;

    public LocationAdapter(List<LocationDetails> locations, Context mContext) {
        this.locations = locations;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.location_details, parent, false);
        ViewHolder holder = new ViewHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position)
    {
        holder.location_name.setText(locations.get(position).getName());
        holder.location_details_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Create a Uri from an intent string. Use the result to create an Intent.
                Uri gmmIntentUri = Uri.parse("google.streetview:cbll="+locations.get(position).getLatitude()+","+locations.get(position).getLongitude());

                // Create an Intent from gmmIntentUri. Set the action to ACTION_VIEW
                Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                // Make the Intent explicit by setting the Google Maps package
                mapIntent.setPackage("com.google.android.apps.maps");

                // Attempt to start an activity that can handle the Intent
                mContext.startActivity(mapIntent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return locations.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder
    {

        TextView location_name;
        LinearLayout location_details_layout;

        public ViewHolder(@NonNull View itemView)
        {
            super(itemView);
            location_name = itemView.findViewById(R.id.location_name);
            location_details_layout=itemView.findViewById(R.id.location_details_layout);

        }
    }
}
