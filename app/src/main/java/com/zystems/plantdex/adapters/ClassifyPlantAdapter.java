package com.zystems.plantdex.adapters;

import android.content.Context;
import android.graphics.Typeface;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.zystems.plantdex.R;
import com.zystems.plantdex.models.PlantClassificationResult;

import java.util.List;

public class ClassifyPlantAdapter extends RecyclerView.Adapter<ClassifyPlantAdapter.PlantViewHolder> {

    private List<PlantClassificationResult> plantClassificationResultList;
    private ClassifyPlantAdapterCallbacks callbacks;

    public ClassifyPlantAdapter(ClassifyPlantAdapterCallbacks callbacks) {
        this.callbacks = callbacks;
    }

    public interface ClassifyPlantAdapterCallbacks{
        void onClick(PlantClassificationResult plantClassificationResult);
    }

    public void setPlantClassificationResultList(List<PlantClassificationResult> plantClassificationResultList) {
        this.plantClassificationResultList = plantClassificationResultList;
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
        String scientificName = plantClassificationResultList.get(position).getPlant().getScientificName();
        String commonName = "\""+plantClassificationResultList.get(position).getPlant().getCommonName()+"\"";
        String percent = "Confidence : " + String.format("%.2f",(plantClassificationResultList.get(position).getPercentConfidence() * 100)) + ""  + "%";

        SpannableString spannableString = new SpannableString(scientificName);
        spannableString.setSpan(new UnderlineSpan(), 0, spannableString.length(), 0);

        holder.txtScientificName.setText(spannableString);
        holder.txtCommonName.setText(commonName);
        holder.txtPercentConfidence.setText(percent);
        holder.txtPercentConfidence.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14f);
        holder.txtPercentConfidence.setTypeface(null, Typeface.BOLD);
    }

    @Override
    public int getItemCount() {
        if(plantClassificationResultList == null) return 0;
        return plantClassificationResultList.size();
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
            callbacks.onClick(plantClassificationResultList.get(getAdapterPosition()));
        }
    }
}
