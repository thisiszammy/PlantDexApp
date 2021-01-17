package com.zystems.plantdex.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.zystems.plantdex.R;
import com.zystems.plantdex.models.PlantLocation;

import java.util.List;

public class PlantLocationsAdapter extends RecyclerView.Adapter<PlantLocationsAdapter.PlantLocationViewHolder> {

    private List<PlantLocation> locations;
    private PlantLocationsAdapterCallbacks callbacks;

    @NonNull
    @Override
    public PlantLocationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context ctx = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(ctx);
        View v = inflater.inflate(R.layout.layout_card_plant_location, parent, false);
        return new PlantLocationViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull PlantLocationViewHolder holder, int position) {
        String longitude = "Longitude : " + locations.get(position).getLongitude();
        String latitude = "Latitude : " + locations.get(position).getLatitude();

        holder.lblLocationName.setText(locations.get(position).getLocationName());
        holder.lblLongitude.setText(longitude);
        holder.lblLatitude.setText(latitude);
    }

    @Override
    public int getItemCount() {
        if(locations == null) return 0;
        return locations.size();
    }


    public interface PlantLocationsAdapterCallbacks{
        void removeLocation(PlantLocation location);
        void onClickLocation(PlantLocation location);
    }

    public PlantLocationsAdapter(PlantLocationsAdapterCallbacks callbacks) {
        this.callbacks = callbacks;
    }

    public void setLocations(List<PlantLocation> locations) {
        this.locations = locations;
        notifyDataSetChanged();
    }

    public class PlantLocationViewHolder extends RecyclerView.ViewHolder {

        private TextView lblLatitude, lblLongitude, lblLocationName;
        private ImageButton btnRemove;

        public PlantLocationViewHolder(@NonNull View itemView) {
            super(itemView);
            lblLatitude = (TextView) itemView.findViewById(R.id.lblLatitude);
            lblLongitude = (TextView) itemView.findViewById(R.id.lblLongitude);
            lblLocationName = (TextView) itemView.findViewById(R.id.lblTempName);
            btnRemove = (ImageButton) itemView.findViewById(R.id.btnRemove);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    callbacks.onClickLocation(locations.get(getAdapterPosition()));
                }
            });
            btnRemove.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    callbacks.removeLocation(locations.get(getAdapterPosition()));
                }
            });
        }

    }

}
