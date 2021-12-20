package com.example.sellit.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sellit.Adapter.ModelAdapter;
import com.example.sellit.Adapter.ReviewAdapter;
import com.example.sellit.Adapter.SeriesAdapter;
import com.example.sellit.Adapter.SeriesModels;
import com.example.sellit.Adapter.TopBrandAdapter;
import com.example.sellit.Adapter.TopMobileAdapter;
import com.example.sellit.ModelResponse.Brand;
import com.example.sellit.ModelResponse.Mobile;
import com.example.sellit.ModelResponse.Model;
import com.example.sellit.ModelResponse.Reviews;
import com.example.sellit.ModelResponse.Series;
import com.example.sellit.ModelResponse.SeriesModel;
import com.example.sellit.Network.ApiClient;
import com.example.sellit.Network.ApiInterface;
import com.example.sellit.R;
import com.example.sellit.RecyclerViewExtention.MiddleDividerItemDecoration;
import com.example.sellit.RecyclerViewExtention.RecyclerItemClickListener;
import com.facebook.shimmer.ShimmerFrameLayout;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ModelActivity extends AppCompatActivity {

    RecyclerView modelRecycler, mobileRecycler, reviewRecycler, brandRecycler, seriesRecycler;
    ApiInterface apiInterface;
    private List<Model> modelList;
    private List<Mobile> mobileList;
    private List<Brand> brandList;
    private List<Series> seriesList;
    private List<SeriesModel> seriesModelsList;
    private SharedPreferences sharedPreferences;
    RelativeLayout seriesLayout;
    TextView txt1, txt2;
    ProgressDialog  progressDoalog;
    ImageView back;
    ShimmerFrameLayout mFrameLayout4;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_model);

        txt1 = findViewById(R.id.txt_name);
        txt2 = findViewById(R.id.txt_second_name);
        back = findViewById(R.id.back);
        seriesLayout = findViewById(R.id.seriesRL);

        //Shimmer layout
        mFrameLayout4 = findViewById(R.id.shimmerLayout4);

        //recycler

        modelRecycler = findViewById(R.id.modelRecycler);
        mobileRecycler = findViewById(R.id.phoneRecyclerSell);
        brandRecycler = findViewById(R.id.brandRecyclerSell);
        reviewRecycler = findViewById(R.id.reviewRecyclerSell);
        seriesRecycler = findViewById(R.id.seriesRecycler);

        seriesRecycler.setHasFixedSize(true);
        seriesRecycler.addItemDecoration(new MiddleDividerItemDecoration(ModelActivity.this, MiddleDividerItemDecoration.ALL));
        seriesRecycler.setLayoutManager(new GridLayoutManager(ModelActivity.this, 3));

        modelRecycler.addItemDecoration(new MiddleDividerItemDecoration(ModelActivity.this, MiddleDividerItemDecoration.ALL));
        modelRecycler.setLayoutManager(new GridLayoutManager(this, 3));

        reviewRecycler.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        mobileRecycler.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        brandRecycler.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));

        //intentCalling

        Intent intent = getIntent();
        String bid = intent.getStringExtra("bid");
        String key = intent.getStringExtra("key");
        //String cid=intent.getStringExtra("cid");
        /*String mobBid = intent.getStringExtra("ModelBid");
       String mobCid = intent.getStringExtra("ModelCid");
       String mobSid = intent.getStringExtra("ModelSid");*/

        switch (key) {
            case "1":
                txt1.setText("Sell Old Mobile");
                txt2.setText("Select Mobile");
                break;
            case "2":
                txt1.setText("Sell Old Watch");
                txt2.setText("Select Watch");
                break;
            case "3":
                txt1.setText("Sell Old Tablet");
                txt2.setText("Select Tablet");
                break;
            case "4":
                txt1.setText("Sell Old Earbud");
                txt2.setText("Select Earbud");
                break;
        }
        Log.d("intent bid", bid);
        Log.d("intent key", key);
        try {


                //func call
                getSeries(bid);
                getMobile(key);
                getBrands(key);
                getReview();
                getModels(bid);
                //getSeriesModels(mobBid,mobCid,mobSid);


            } catch (NullPointerException e)
            {
                Toast.makeText(this, "Check your internet connection or try again later", Toast.LENGTH_SHORT).show();
            }



        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    @Override
    protected void onResume() {

        super.onResume();
        mFrameLayout4.startShimmer();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mFrameLayout4.stopShimmer();
    }
    //function not working
    public void getSeries(String key){
        apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        Call<List<Series>> call = apiInterface.getSeries(key);
        call.enqueue(new Callback<List<Series>>() {
            @Override
            public void onResponse(Call<List<Series>> call, Response<List<Series>> response) {

                try {
                    if (!response.body().toString().equals("null"))
                    {
                        mFrameLayout4.startShimmer();
                        seriesList = response.body();
                        seriesRecycler.setAdapter(new SeriesAdapter(ModelActivity.this, seriesList));
                        mFrameLayout4.setVisibility(View.VISIBLE);
                        mFrameLayout4.setVisibility(View.GONE);
                        mFrameLayout4.stopShimmer();
                        seriesRecycler.addOnItemTouchListener(new RecyclerItemClickListener(ModelActivity.this, seriesRecycler, new RecyclerItemClickListener.OnItemClickListener() {
                            @Override
                            public void onItemClick(View view, int position) {

                                    Intent intent = new Intent(ModelActivity.this, ModelFromSeries.class);
                                    intent.putExtra("cid", seriesList.get(position).getCatid().toString());
                                    intent.putExtra("name", seriesList.get(position).getName());
                                    intent.putExtra("bid", seriesList.get(position).getBid().toString());
                                    intent.putExtra("sid", seriesList.get(position).getSeriesid().toString());
                                    startActivity(intent);

                            }

                            @Override
                            public void onLongItemClick(View view, int position) {

                            }
                        }));

                                Log.d("name: ", seriesList.get(1).getName());
                        Log.d("name: ", seriesList.get(1).getSeriesid());
                        Log.d("name: ", seriesList.get(1).getBid());
                        Log.d("name: ", seriesList.get(1).getCatid());


                    }
                    else
                    {
                        seriesRecycler.setVisibility(View.GONE);
                        seriesLayout.setVisibility(View.GONE);
                        mFrameLayout4.setVisibility(View.GONE);
                        mFrameLayout4.stopShimmer();
                    }
                }
                catch (NullPointerException e)
                {
                    seriesRecycler.setVisibility(View.GONE);
                    seriesLayout.setVisibility(View.GONE);
                    mFrameLayout4.setVisibility(View.GONE);
                    mFrameLayout4.stopShimmer();
                }

            }

            @Override
            public void onFailure(Call<List<Series>> call, Throwable t) {

                Log.d("series Resp: ", t.getLocalizedMessage());
                seriesRecycler.setVisibility(View.GONE);
                seriesLayout.setVisibility(View.GONE);
                mFrameLayout4.setVisibility(View.GONE);
                mFrameLayout4.stopShimmer();
            }
        });
    }

    public void getSeriesModels(String bid, String cid, String sid){
        apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        Call<List<SeriesModel>> call = apiInterface.getSeriesModels(bid, cid, sid);
        call.enqueue(new Callback<List<SeriesModel>>() {
            @Override
            public void onResponse(Call<List<SeriesModel>> call, Response<List<SeriesModel>> response) {
                seriesModelsList = response.body();
                modelRecycler.setAdapter(new SeriesModels(ModelActivity.this, seriesModelsList));
                mobileRecycler.addOnItemTouchListener(new RecyclerItemClickListener(ModelActivity.this, mobileRecycler, new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        modelRecycler.setAdapter(new SeriesModels(ModelActivity.this, seriesModelsList));
                        // Storing data into SharedPreferences
                        sharedPreferences = getSharedPreferences("MySharedPref",MODE_PRIVATE);

// Creating an Editor object to edit(write to the file)
                        SharedPreferences.Editor myPhone = sharedPreferences.edit();
                        myPhone.putString("image", seriesModelsList.get(position).getFile());

// Storing the key and its value as the data fetched from edittext
                        myPhone.putString("name", seriesModelsList.get(position).getName().toString());

// Once the changes have been made,
// we need to commit to apply those changes made,
// otherwise, it will throw an error
                        myPhone.commit();

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

    public void getModels(String key){
        apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        Call<List<Model>> call = apiInterface.getModels(key);
        call.enqueue(new Callback<List<Model>>() {
            @Override
            public void onResponse(Call<List<Model>> call, Response<List<Model>> response) {
                modelList = response.body();
                try {
                    modelRecycler.setAdapter(new ModelAdapter(ModelActivity.this, modelList));

                } catch (NullPointerException e)
                {
                    Toast.makeText(ModelActivity.this, "Not showing", Toast.LENGTH_SHORT).show();
                }
                modelRecycler.addOnItemTouchListener(new RecyclerItemClickListener(ModelActivity.this, modelRecycler, new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        progressDoalog = new ProgressDialog(ModelActivity.this);
                        progressDoalog.setMax(100);
                        progressDoalog.setMessage("Please wait....");
                        progressDoalog.setTitle("Searching..");
                        progressDoalog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                        progressDoalog.show();
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    while (progressDoalog.getProgress() <= progressDoalog
                                            .getMax()) {
                                        Thread.sleep(200);
                                        //handle.sendMessage(handle.obtainMessage());
                                        if (progressDoalog.getProgress() == progressDoalog
                                                .getMax()) {
                                            progressDoalog.dismiss();
                                        }
                                    }
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        }).start();
                        String url =  modelList.get(position).getUrl();
                        Intent intent = new Intent(ModelActivity.this, WebViewActivity.class);
                        intent.putExtra("url", url);
                        sharedPreferences = getSharedPreferences("MySharedPref",MODE_PRIVATE);

// Creating an Editor object to edit(write to the file)
                        SharedPreferences.Editor myPhone = sharedPreferences.edit();

// Storing the key and its value as the data fetched from edittext
                        myPhone.putString("name", modelList.get(position).getName().toString());

// Once the changes have been made,
// we need to commit to apply those changes made,
// otherwise, it will throw an error
                        myPhone.commit();
                        startActivity(intent);
                        progressDoalog.dismiss();
                    }

                    @Override
                    public void onLongItemClick(View view, int position) {

                    }
                }));
            }

            @Override
            public void onFailure(Call<List<Model>> call, Throwable t) {

            }
        });
    }
    public void getReview(){
        apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        Call<List<Reviews>> call = apiInterface.getReviews();
        call.enqueue(new Callback<List<Reviews>>() {
            @Override
            public void onResponse(Call<List<Reviews>> call, Response<List<Reviews>> response) {
                List<Reviews> list = response.body();
                try {

                    reviewRecycler.setAdapter(new ReviewAdapter(ModelActivity.this, list));
                } catch (NullPointerException e)
                {
                    Toast.makeText(ModelActivity.this, "Not showing", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Reviews>> call, Throwable t) {

            }
        });
    }


    public void getMobile(String key){
        apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        Call<List<Mobile>> call = apiInterface.getMobiles(key);
        call.enqueue(new Callback<List<Mobile>>() {
            @Override
            public void onResponse(Call<List<Mobile>> call, Response<List<Mobile>> response) {
                mobileList = response.body();
                try {
                    mobileRecycler.setAdapter(new TopMobileAdapter(ModelActivity.this, mobileList));
                } catch (NullPointerException e)
                {
                    Toast.makeText(ModelActivity.this, "Not showing", Toast.LENGTH_SHORT).show();
                }

                mobileRecycler.addOnItemTouchListener(new RecyclerItemClickListener(ModelActivity.this, mobileRecycler, new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {

                        String url = mobileList.get(position).getUrl();
                        Intent intent = new Intent(ModelActivity.this, WebViewActivity.class);
                        intent.putExtra("url", url);
                        sharedPreferences = getSharedPreferences("MySharedPref",MODE_PRIVATE);

// Creating an Editor object to edit(write to the file)
                        SharedPreferences.Editor myPhone = sharedPreferences.edit();
                        myPhone.putString("image", seriesModelsList.get(position).getFile());

// Storing the key and its value as the data fetched from edittext
                        myPhone.putString("name", seriesModelsList.get(position).getName().toString());

// Once the changes have been made,
// we need to commit to apply those changes made,
// otherwise, it will throw an error
                        myPhone.commit();
                        startActivity(intent);
                    }

                    @Override
                    public void onLongItemClick(View view, int position) {

                    }
                }));



            }

            @Override
            public void onFailure(Call<List<Mobile>> call, Throwable t) {

            }
        });

    }
    public void getBrands(String key){
        apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        Call<List<Brand>> call = apiInterface.getBrands(key);
        call.enqueue(new Callback<List<Brand>>() {
            @Override
            public void onResponse(Call<List<Brand>> call, Response<List<Brand>> response) {
                brandList = response.body();
                brandRecycler.setAdapter(new TopBrandAdapter(ModelActivity.this, brandList));
                brandRecycler.addOnItemTouchListener(new RecyclerItemClickListener(ModelActivity.this, brandRecycler, new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {

                        Intent intent = new Intent(ModelActivity.this, ModelActivity.class);
                        intent.putExtra("key", "1");
                        intent.putExtra("bid", brandList.get(position).getId());

                        startActivity(intent);
                    }

                    @Override
                    public void onLongItemClick(View view, int position) {

                    }
                }));
            }

            @Override
            public void onFailure(Call<List<Brand>> call, Throwable t) {

            }
        });

    }


}