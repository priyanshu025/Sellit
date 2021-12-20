package com.example.sellit.Adapter;

import android.content.Context;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.sellit.ModelResponse.Model;
import com.example.sellit.R;

import org.w3c.dom.Text;

import java.util.List;

public class ModelAdapter extends RecyclerView.Adapter<ModelAdapter.ModelViewHolder> {

    private Context context;
    private List<Model> models;

    public ModelAdapter(Context context, List<Model> models) {
        this.context = context;
        this.models = models;
    }

    @NonNull
    @Override
    public ModelViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.model_layout, parent, false);
        return new ModelAdapter.ModelViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ModelViewHolder holder, int position) {
    Model model = models.get(position);
    holder.modelName.setText(models.get(position).getName());
    Glide.with(context).load(model.getFile()).diskCacheStrategy(DiskCacheStrategy.ALL).into(holder.modelImg);
    }

    @Override
    public int getItemCount() {
        try{

            return models.size();
        }catch (NullPointerException e)
        {
            return 0;
        }

    }

    public class ModelViewHolder extends RecyclerView.ViewHolder {
        ImageView modelImg;
        TextView modelName;

        public ModelViewHolder(@NonNull View itemView) {
            super(itemView);
            modelImg = itemView.findViewById(R.id.modelImg);
            modelName = itemView.findViewById(R.id.modelName);
        }
    }
}
