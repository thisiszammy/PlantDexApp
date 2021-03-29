package com.zystems.plantdex.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.zystems.plantdex.BuildConfig;
import com.zystems.plantdex.R;
import com.zystems.plantdex.models.Plant;

import java.util.List;

public class SearchPlantAdapter extends RecyclerView.Adapter<SearchPlantAdapter.PlantViewHolder> {
    private List<Plant> plants;
    private SearchPlantAdapterCallbacks callbacks;

    public interface SearchPlantAdapterCallbacks{
        void onClick(Plant plant);
    }

    public SearchPlantAdapter(SearchPlantAdapterCallbacks callbacks) {
        this.callbacks = callbacks;
    }

    public void setPlants(List<Plant> plants) {
        this.plants = plants;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public PlantViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View v = inflater.inflate(R.layout.layout_card_plant, parent, false);
        return new PlantViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull PlantViewHolder holder, int position) {
        String scientificName = plants.get(position).getScientificName();
        String commonName = "\""+plants.get(position).getCommonName()+"\"";
        String plantImageString = plants.get(position).getPlantImage();

        SpannableString spannableString = new SpannableString(scientificName);
        spannableString.setSpan(new UnderlineSpan(), 0, spannableString.length(), 0);

        holder.txtScientificName.setText(spannableString);
        holder.txtCommonName.setText(commonName);
        holder.txtPercentConfidence.setText("");

        if(plantImageString != null){
            byte[] plantImage = Base64.decode(plantImageString, Base64.DEFAULT);
            Bitmap bmp = BitmapFactory.decodeByteArray(plantImage, 0, plantImage.length);
            holder.imgPlant.setImageBitmap(bmp);
        }

    }

    @Override
    public int getItemCount() {
        if(plants == null) return 0;
        return plants.size();
    }

    class PlantViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private ImageView imgPlant;
        private TextView txtScientificName, txtCommonName, txtPercentConfidence;

        public PlantViewHolder(@NonNull View itemView) {
            super(itemView);
            imgPlant = (ImageView) itemView.findViewById(R.id.imgPlant);
            txtScientificName = (TextView) itemView.findViewById(R.id.txtScientificName);
            txtCommonName = (TextView) itemView.findViewById(R.id.txtCommonName);
            txtPercentConfidence = (TextView) itemView.findViewById(R.id.txtPercentConfidence);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            callbacks.onClick(plants.get(getAdapterPosition()));
        }
    }
}
