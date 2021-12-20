package com.example.sellit.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sellit.ModelResponse.Series;
import com.example.sellit.R;

import java.util.List;

public class SeriesAdapter extends RecyclerView.Adapter<SeriesAdapter.SeriesViewHolder> {
    private final Context context;
    private final List<Series> series;

    public SeriesAdapter(Context context, List<Series> series) {
        this.context = context;
        this.series = series;
    }

    @NonNull
    @Override
    public SeriesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.series_layout, parent, false);
        return new SeriesAdapter.SeriesViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SeriesViewHolder holder, int position) {
        Series seriesList = series.get(position);
        holder.sName.setText(seriesList.getName());

    }

    @Override
    public int getItemCount() {
        try{

            return series.size();
        }catch (NullPointerException e)
        {
            return 0;
        }
    }

    public class SeriesViewHolder extends RecyclerView.ViewHolder {

        TextView sName;

        public SeriesViewHolder(@NonNull View itemView) {
            super(itemView);
            sName = itemView.findViewById(R.id.seriesName);
        }
    }
}
