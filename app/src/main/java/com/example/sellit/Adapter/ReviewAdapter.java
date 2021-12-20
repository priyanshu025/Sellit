package com.example.sellit.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sellit.ModelResponse.Reviews;
import com.example.sellit.R;

import java.util.List;

public class ReviewAdapter extends RecyclerView.Adapter<ReviewAdapter.ReviewViewHolder> {

    private Context context;
    private List<Reviews> reviews;

    public ReviewAdapter(Context context, List<Reviews> reviews) {
        this.context = context;
        this.reviews = reviews;
    }

    @NonNull
    @Override
    public ReviewViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.review_layout, parent, false);
        return new ReviewAdapter.ReviewViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ReviewViewHolder holder, int position) {
        Reviews review = reviews.get(position);
        holder.reviewName.setText(review.getName());
        holder.reviewLocation.setText(review.getCity());
        holder.reviewDescription.setText(review.getMessage());
    }

    @Override
    public int getItemCount() {
        try{

            return reviews.size();
        }catch (NullPointerException e)
        {
            return 0;
        }
    }

    public class ReviewViewHolder extends RecyclerView.ViewHolder {
        TextView reviewName;
        TextView reviewLocation;
        TextView reviewDescription;

        public ReviewViewHolder(@NonNull View itemView) {
            super(itemView);
            reviewName = itemView.findViewById(R.id.reviewName);
            reviewLocation = itemView.findViewById(R.id.reviewLocation);
            reviewDescription = itemView.findViewById(R.id.reviewDescription);
        }
    }
}
