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
import com.example.sellit.ModelResponse.Mobile;
import com.example.sellit.R;

import java.util.List;

public class TopMobileAdapter extends RecyclerView.Adapter<TopMobileAdapter.TopMobileAdapterViewHolder>{


    private Context context;
    private List<Mobile> mobiles;

    public TopMobileAdapter(Context context, List<Mobile> mobiles) {
        this.context = context;
        this.mobiles = mobiles;

    }

    @NonNull
    @Override
    public TopMobileAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.mobiles_layout, parent,false);
        return new TopMobileAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TopMobileAdapterViewHolder holder, int position) {
        Mobile mobile = mobiles.get(position);
        holder.phoneText.setText(mobile.getPname());
        Glide.with(context).load(mobile.getFile()).diskCacheStrategy(DiskCacheStrategy.ALL).into(holder.phoneImgae);
    }

    @Override
    public int getItemCount() {
        try{

            return mobiles.size();
        }catch (NullPointerException e)
        {
            return 0;
        }
    }

    public class TopMobileAdapterViewHolder extends RecyclerView.ViewHolder {
        ImageView phoneImgae;
        TextView phoneText;

        public TopMobileAdapterViewHolder(@NonNull View itemView) {
            super(itemView);

            phoneImgae = itemView.findViewById(R.id.phoneImage);
            phoneText = itemView.findViewById(R.id.phoneName);

        }
    }
}
