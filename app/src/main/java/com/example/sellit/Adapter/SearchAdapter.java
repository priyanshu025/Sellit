package com.example.sellit.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sellit.ModelResponse.User;
import com.example.sellit.R;

import java.util.ArrayList;
import java.util.List;

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.MyViewHolder>{

    private Context context;
    private List<User> users = new ArrayList<User>();
    public SearchAdapter(Context context, List<User> users) {
        this.context = context;
        this.users = users;
    }


    @Override
    public MyViewHolder onCreateViewHolder( ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.search_item, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.name.setText(users.get(position).getName());
        holder.url.setText(users.get(position).getUrl());
    }

    public String getUrl(int pos)
    {
        return users.get(pos).getUrl();
    }





    @Override
    public int getItemCount() {
        try{

            return users.size();
        }catch (NullPointerException e)
        {
            return 0;
        }

    }

public static class MyViewHolder extends RecyclerView.ViewHolder {
    TextView name;
    TextView url;
    public MyViewHolder(@NonNull View itemView) {
        super(itemView);
        name = itemView.findViewById(R.id.name);
        url = itemView.findViewById(R.id.url);
    }
}
}
