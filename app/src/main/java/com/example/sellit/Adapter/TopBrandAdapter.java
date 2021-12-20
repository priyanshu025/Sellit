package com.example.sellit.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.sellit.ModelResponse.Brand;
import com.example.sellit.R;

import java.util.List;

public class TopBrandAdapter extends RecyclerView.Adapter<TopBrandAdapter.TopBrandAdapterViewHolder> {

    private Context context;
    private List<Brand> brands;

    public TopBrandAdapter(Context context, List<Brand> brands) {
        this.context = context;
        this.brands = brands;
    }

    @NonNull
    @Override
    public TopBrandAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.brand_layout, parent, false);
        return new TopBrandAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TopBrandAdapterViewHolder holder, int position) {
        Brand brand = brands.get(position);

        Glide.with(context).load(brand.getFile()).diskCacheStrategy(DiskCacheStrategy.ALL).into(holder.brandImg);
    }

    @Override
    public int getItemCount() {
        try{

            return brands.size();
        }catch (NullPointerException e)
        {
            return 0;
        }

    }

    public class TopBrandAdapterViewHolder extends RecyclerView.ViewHolder {
        ImageView brandImg;

        public TopBrandAdapterViewHolder(@NonNull View itemView) {
            super(itemView);
            brandImg = itemView.findViewById(R.id.brandImg);
        }
    }

}
