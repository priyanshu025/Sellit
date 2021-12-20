package com.example.sellit.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.sellit.ModelResponse.Series;
import com.example.sellit.ModelResponse.SeriesModel;
import com.example.sellit.R;

import java.util.List;

public class SeriesModels extends RecyclerView.Adapter<SeriesModels.SeriesModelViewHolder> {

    private Context context;
    private List<SeriesModel> seriesModels;

    public SeriesModels(Context context, List<SeriesModel> seriesModels) {
        this.context = context;
        this.seriesModels = seriesModels;
    }

    @NonNull
    @Override
    public SeriesModelViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.model_layout, parent,false);
        return new SeriesModels.SeriesModelViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SeriesModelViewHolder holder, int position) {
    SeriesModel seriesModel = seriesModels.get(position);
    holder.modelName.setText(seriesModel.getName());
        Glide.with(context).load(seriesModel.getFile()).diskCacheStrategy(DiskCacheStrategy.ALL).into(holder.modelImg);

    }

    @Override
    public int getItemCount() {
        try
        {
            return seriesModels.size();
        }
        catch (NullPointerException e)
        {
            return 0;
        }

    }

    public class SeriesModelViewHolder extends RecyclerView.ViewHolder {
         ImageView modelImg;
        TextView modelName;
        public SeriesModelViewHolder(@NonNull View itemView) {
            super(itemView);
            modelImg = itemView.findViewById(R.id.modelImg);
            modelName = itemView.findViewById(R.id.modelName);

        }
    }
}
