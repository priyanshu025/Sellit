package com.example.sellit.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.service.controls.actions.ModeAction;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sellit.Adapter.SeriesAdapter;
import com.example.sellit.ModelResponse.Series;
import com.example.sellit.RecyclerViewExtention.MiddleDividerItemDecoration;
import com.example.sellit.RecyclerViewExtention.RecyclerItemClickListener;
import com.example.sellit.Adapter.ReviewAdapter;
import com.example.sellit.Adapter.TopBrandAdapter;
import com.example.sellit.Adapter.TopMobileAdapter;
import com.example.sellit.Adapter.TotalBrandsAdapter;
import com.example.sellit.ModelResponse.Brand;
import com.example.sellit.ModelResponse.CommonBrand;
import com.example.sellit.ModelResponse.Mobile;
import com.example.sellit.ModelResponse.Reviews;
import com.example.sellit.Network.ApiClient;
import com.example.sellit.Network.ApiInterface;
import com.example.sellit.R;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.jaredrummler.android.device.DeviceName;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SellingActivity extends AppCompatActivity {

    RecyclerView sellingRecycler, mobileRecycler, brandRecycler, reviewRecycler;
    ApiInterface apiInterface;
    TextView txtname, topSelling;
    RelativeLayout mobile, brand, seriesLayout;
    List<Mobile> mobileList;
    List<Brand> brandList;
    List<CommonBrand> commonBrandList;
    private SharedPreferences sharedPreferences;
    ProgressDialog progressDoalog;
    private ShimmerFrameLayout mFrameLayout, mFrameLayout1, mFrameLayout2, mFrameLayout3;
    ImageView back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selling);
        txtname = findViewById(R.id.txt_name);
        mobile = findViewById(R.id.sellingMobileLayout);
        brand = findViewById(R.id.sellingBrandLayout);
        back = findViewById(R.id.back);
        topSelling = findViewById(R.id.topSelling);


        //shimmerLayout
        mFrameLayout1 = findViewById(R.id.shimmerLayout1);
        mFrameLayout2 = findViewById(R.id.shimmerLayout2);
        mFrameLayout3 = findViewById(R.id.shimmerLayout3);

        mFrameLayout = findViewById(R.id.shimmerLayout);


        Intent intent = getIntent();
        String key = "";
        if(intent.getStringExtra("url").toString().equals("phone"))
        {
            txtname.setText("Sell Your Mobile");
            topSelling.setText("Top Selling Mobiles");
            key = "1";

            try {
                getMobile("1");
                getBrands("1");
                getCommonBrand("1");
                getReview();
            } catch (NullPointerException e)
            {
                Toast.makeText(this, "Check your internet connection or try again later", Toast.LENGTH_SHORT).show();
            }

        }
        else if(intent.getStringExtra("url").toString().equals("watch"))
        {
            txtname.setText("Sell Your Watch");
            topSelling.setText("Top Selling Watch");
            key = "2";

            try {
                getMobile("2");
                getBrands("2");
                getCommonBrand("2");
                getReview();
            }catch (NullPointerException e)
            {
                Toast.makeText(this, "Check your internet connection or try again later", Toast.LENGTH_SHORT).show();
            }

        }
        else if(intent.getStringExtra("url").toString().equals("tablet"))
        {
            key = "3";
            txtname.setText("Sell Your Tablet");
            topSelling.setText("Top Selling Tablet");


            try {
                getCommonBrand("3");
                getReview();
                getMobile("3");
                getBrands("3");

            }
            catch (NullPointerException e)
            {
                Toast.makeText(this, "Check your internet connection or try again later", Toast.LENGTH_SHORT).show();
                mobile.setVisibility(View.GONE);
                brand.setVisibility(View.GONE);

            }

        }
        else if(intent.getStringExtra("url").toString().equals("earbud"))
        {
            txtname.setText("Sell Your Earbuds");
            topSelling.setText("Top Selling Earbuds");
            key = "4";

            try {
                getMobile("4");
                getBrands("4");
                getCommonBrand("4");
                getReview();
            }
            catch (NullPointerException e)
            {
                Toast.makeText(this, "Check your internet connection or try again later", Toast.LENGTH_SHORT).show();
            }

        }

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });



        //recyclerView

        sellingRecycler = findViewById(R.id.sellingRecycler);
        sellingRecycler.setHasFixedSize(true);

        sellingRecycler.addItemDecoration(new MiddleDividerItemDecoration(SellingActivity.this, MiddleDividerItemDecoration.ALL));
        sellingRecycler.setLayoutManager(new GridLayoutManager(SellingActivity.this, 3));
        mobileRecycler = findViewById(R.id.phoneRecyclerSell);
        brandRecycler = findViewById(R.id.brandRecyclerSell);
        reviewRecycler = findViewById(R.id.reviewRecyclerSell);
        reviewRecycler.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        mobileRecycler.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        brandRecycler.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));

    }




    public void getReview(){
        apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        Call<List<Reviews>> call = apiInterface.getReviews();
        call.enqueue(new Callback<List<Reviews>>() {
            @Override
            public void onResponse(Call<List<Reviews>> call, Response<List<Reviews>> response) {
                List<Reviews> list = response.body();
                try {
                    reviewRecycler.setAdapter(new ReviewAdapter(SellingActivity.this, list));
                    mFrameLayout3.startShimmer();
                    mFrameLayout3.setVisibility(View.GONE);
                    reviewRecycler.setVisibility(View.VISIBLE);
                } catch (NullPointerException e)
                {
                    Toast.makeText(SellingActivity.this, "Not showing", Toast.LENGTH_SHORT).show();
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
                    mobileRecycler.setAdapter(new TopMobileAdapter(SellingActivity.this, mobileList));
                    mFrameLayout2.startShimmer();
                    mFrameLayout2.setVisibility(View.GONE);
                    mobileRecycler.setVisibility(View.VISIBLE);
                } catch (NullPointerException e)
                {
                    Toast.makeText(SellingActivity.this, "Not showing", Toast.LENGTH_SHORT).show();
                }

                mobileRecycler.addOnItemTouchListener(new RecyclerItemClickListener(SellingActivity.this, mobileRecycler, new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        progressDoalog = new ProgressDialog(SellingActivity.this);
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
                        String url = mobileList.get(position).getUrl();
                        Intent intent = new Intent(SellingActivity.this, WebViewActivity.class);
                        intent.putExtra("url", url);
                        startActivity(intent);
                        sharedPreferences = getSharedPreferences("MySharedPref",MODE_PRIVATE);

// Creating an Editor object to edit(write to the file)
                        SharedPreferences.Editor myPhone = sharedPreferences.edit();
                        myPhone.putString("image", mobileList.get(position).getFile());
                        myPhone.putBoolean("phoneClick", true);

// Storing the key and its value as the data fetched from edittext
                        myPhone.putString("name", mobileList.get(position).getPname().toString());

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
                try {

                    brandRecycler.setAdapter(new TopBrandAdapter(SellingActivity.this, brandList));
                    mFrameLayout1.startShimmer();
                    mFrameLayout1.setVisibility(View.GONE);
                    brandRecycler.setVisibility(View.VISIBLE);
                } catch (NullPointerException e)
                {
                    Toast.makeText(SellingActivity.this, "Not showing", Toast.LENGTH_SHORT).show();
                }
                brandRecycler.addOnItemTouchListener(new RecyclerItemClickListener(SellingActivity.this, brandRecycler, new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {

                        Intent intent = new Intent(SellingActivity.this, ModelActivity.class);
                        intent.putExtra("key", key);
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

    @Override
    protected void onResume() {
        mFrameLayout.startShimmer();
        super.onResume();
         mFrameLayout1.startShimmer();
        mFrameLayout2.startShimmer();
        mFrameLayout3.startShimmer();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mFrameLayout.stopShimmer();
         mFrameLayout1.stopShimmer();
        mFrameLayout2.stopShimmer();
        mFrameLayout3.stopShimmer();
    }

    public void getCommonBrand(String key)
    {
        apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        Call<List<CommonBrand>> call = apiInterface.getCommonBrands(key);
        call.enqueue(new Callback<List<CommonBrand>>() {
            @Override
            public void onResponse(Call<List<CommonBrand>> call, Response<List<CommonBrand>> response) {
                 commonBrandList = response.body();
                 try{

                     sellingRecycler.setAdapter(new TotalBrandsAdapter(SellingActivity.this, commonBrandList));
                     mFrameLayout.startShimmer();
                     mFrameLayout.setVisibility(View.GONE);
                     sellingRecycler.setVisibility(View.VISIBLE);
                 } catch (NullPointerException e)
                 {
                     Toast.makeText(SellingActivity.this, "Not showing", Toast.LENGTH_SHORT).show();
                 }
                sellingRecycler.addOnItemTouchListener(new RecyclerItemClickListener(SellingActivity.this, sellingRecycler, new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        String url = commonBrandList.get(position).getUrl();
                        String bid = url.substring(url.indexOf("=")+1, url.length());
                        Intent intent = new Intent(SellingActivity.this, ModelActivity.class);
                        intent.putExtra("bid", bid);
                        intent.putExtra("key", key);
                        startActivity(intent);
                    }

                    @Override
                    public void onLongItemClick(View view, int position) {

                    }
                }));
            }

            @Override
            public void onFailure(Call<List<CommonBrand>> call, Throwable t) {

            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}