package com.example.sellit.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sellit.ModelResponse.CommonBrand;
import com.example.sellit.R;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import java.util.List;

public class TotalBrandsAdapter extends RecyclerView.Adapter<TotalBrandsAdapter.TotalBrandsViewHolder> {

    private Context context;
    private List<CommonBrand> brands;

    public TotalBrandsAdapter(Context context, List<CommonBrand> list) {
        this.context = context;
        this.brands = list;
    }

    @NonNull
    @Override
    public TotalBrandsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.selling_brands_layout, parent,false);
        return new TotalBrandsAdapter.TotalBrandsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TotalBrandsViewHolder holder, int position) {
        CommonBrand brand = brands.get(position);
        Glide.with(context).load(brand.getFile()).diskCacheStrategy(DiskCacheStrategy.ALL).into(holder.img);
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

    public class TotalBrandsViewHolder extends RecyclerView.ViewHolder {
        ImageView img;

        public TotalBrandsViewHolder(@NonNull View itemView) {
            super(itemView);
            img = itemView.findViewById(R.id.imageView);
        }
    }
}
