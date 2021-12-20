package com.example.sellit.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.sellit.Adapter.SeriesModels;
import com.example.sellit.ModelResponse.SeriesModel;
import com.example.sellit.Network.ApiClient;
import com.example.sellit.Network.ApiInterface;
import com.example.sellit.R;
import com.example.sellit.RecyclerViewExtention.MiddleDividerItemDecoration;
import com.example.sellit.RecyclerViewExtention.RecyclerItemClickListener;

import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ModelFromSeries extends AppCompatActivity {
    TextView text;
    ApiInterface apiInterface;
    private List<SeriesModel> seriesModelsList;
    RecyclerView modelFromSeries;
    ImageView close;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_model_from_series);
        text = findViewById(R.id.seriesName);
        close = findViewById(R.id.back);

        modelFromSeries = findViewById(R.id.modelFromSeries);
        modelFromSeries.setHasFixedSize(true);
        modelFromSeries.addItemDecoration(new MiddleDividerItemDecoration(ModelFromSeries.this, MiddleDividerItemDecoration.ALL));
        modelFromSeries.setLayoutManager(new GridLayoutManager(ModelFromSeries.this, 3));

        getSeriesModels(getString("bid"), getString("cid"), getString("sid"));
        text.setText(getString("name"));


        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                back();
            }
        });

        text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                back();
            }
        });


    }
    public String getString(String key)
    {
        Intent intent  = getIntent();
        HashMap<String, String> map = new HashMap<>();
        map.put("name", intent.getStringExtra("name"));
        map.put("cid", intent.getStringExtra("cid"));
        map.put("bid", intent.getStringExtra("bid"));
        map.put("sid", intent.getStringExtra("sid"));
        Log.d("cid", intent.getStringExtra("cid"));
        Log.d("name", intent.getStringExtra("name"));
        Log.d("bid", intent.getStringExtra("bid"));
        Log.d("sid", intent.getStringExtra("sid"));
        return map.get(key);
    }

    public void getSeriesModels(String bid, String cid, String sid){
        apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        Call<List<SeriesModel>> call = apiInterface.getSeriesModels(bid, cid, sid);
        call.enqueue(new Callback<List<SeriesModel>>() {
            @Override
            public void onResponse(Call<List<SeriesModel>> call, Response<List<SeriesModel>> response) {
                seriesModelsList = response.body();
                modelFromSeries.setAdapter(new SeriesModels(ModelFromSeries.this, seriesModelsList));
                modelFromSeries.addOnItemTouchListener(new RecyclerItemClickListener(ModelFromSeries.this, modelFromSeries, new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        Intent intent = new Intent(ModelFromSeries.this, WebViewActivity.class);
                        intent.putExtra("url", seriesModelsList.get(position).getUrl());
                        sharedPreferences = getSharedPreferences("MySharedPref",MODE_PRIVATE);

// Creating an Editor object to edit(write to the file)
                        SharedPreferences.Editor myPhone = sharedPreferences.edit();

// Storing the key and its value as the data fetched from edittext
                        myPhone.putString("name", seriesModelsList.get(position).getName().toString());

// Once the changes have been made,
// we need to commit to apply those changes made,
// otherwise, it will throw an error
                        myPhone.commit();
                        startActivity(intent);
                        startActivity(intent);

                    }

                    @Override
                    public void onLongItemClick(View view, int position) {

                    }
                }));
            }

            @Override
            public void onFailure(Call<List<SeriesModel>> call, Throwable t) {

            }
        });
    }
    public void back() {
        finish();
    }
}