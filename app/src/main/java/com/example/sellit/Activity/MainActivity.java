package com.example.sellit.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.app.NotificationCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.sellit.ModelResponse.HighestPrice;
import com.example.sellit.ModelResponse.Model;
import com.example.sellit.ModelResponse.User;
import com.example.sellit.RecyclerViewExtention.RecyclerItemClickListener;
import com.example.sellit.Adapter.ReviewAdapter;
import com.example.sellit.ModelResponse.Reviews;
import com.example.sellit.Adapter.TopBrandAdapter;
import com.example.sellit.Adapter.TopMobileAdapter;
import com.example.sellit.ModelResponse.Brand;
import com.example.sellit.ModelResponse.Mobile;
import com.example.sellit.Network.ApiClient;
import com.example.sellit.Network.ApiInterface;
import com.example.sellit.R;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.jaredrummler.android.device.DeviceName;
import com.jaredrummler.materialspinner.MaterialSpinner;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

   TextView search, phoneName,current_price;
   RecyclerView mobileRecycler, brandRecycler, reviewRecycler;
   ApiInterface apiInterface;
   CardView phone, tablet, watch, eearbud, phoneOffer, tabletOffer, watchOffer, eearbudOffer;
   ProgressDialog progressDoalog;
   LinearLayout user_mobile;
   ImageView current_device;
   String current_value;
    private ShimmerFrameLayout mFrameLayout1, mFrameLayout2, mFrameLayout3;
    private static final int TIME_INTERVAL = 2000; // # milliseconds, desired time passed between two back presses.
    private long mBackPressed;
    private SharedPreferences sharedPreferences;
    private Boolean phoneClick = false;
    private String CHANNEL_ID = "my_channel_01";
    private CharSequence name = "my_channel";
    private String Description = "This is my channel";
    private List<User> users;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();


        phone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(MainActivity.this, SellingActivity.class);
                intent.putExtra("url", "phone");
                startActivity(intent);
            }
        });
        tablet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(MainActivity.this, SellingActivity.class);
                intent.putExtra("url", "tablet");
                startActivity(intent);

            }
        });
        watch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(MainActivity.this, SellingActivity.class);
                intent.putExtra("url", "watch");
                startActivity(intent);

            }
        });
        eearbud.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(MainActivity.this, SellingActivity.class);
                intent.putExtra("url", "earbud");
                startActivity(intent);

            }
        });

        phoneOffer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(MainActivity.this, SellingActivity.class);
                intent.putExtra("url", "phone");
                startActivity(intent);

            }
        }); tabletOffer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(MainActivity.this, SellingActivity.class);
                intent.putExtra("url", "tablet");
                startActivity(intent);

            }
        }); watchOffer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(MainActivity.this, SellingActivity.class);
                intent.putExtra("url", "watch");
                startActivity(intent);

            }
        }); eearbudOffer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(MainActivity.this, SellingActivity.class);
                intent.putExtra("url", "earbud");
                startActivity(intent);

            }
        });

        //Layouts for recyclerview
        reviewRecycler.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        mobileRecycler.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        brandRecycler.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));

        String deviceName = DeviceName.getDeviceName();
        int end = 0;
        try {
            end = deviceName.indexOf("(");
            phoneName.setText(Build.MANUFACTURER.toUpperCase()+" "+deviceName.substring(0, end));

        }catch (Exception e)
        {
            phoneName.setText(Build.MANUFACTURER+" "+deviceName);

        }
       fetch_data(deviceName);
        //current_price.setText(current_value);

            user_mobile.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    progressDoalog = new ProgressDialog(MainActivity.this);
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
                    sharedPreferences = getSharedPreferences("MySharedPref", MODE_PRIVATE);
                    sharedPreferences.edit().putString("name", deviceName).apply();
                    if(users.size()>0) {
                        String url = users.get(0).getUrl();                        Intent intent = new Intent(MainActivity.this, WebViewActivity.class);
                        intent.putExtra("url", url);
                        startActivity(intent);
                        progressDoalog.dismiss();
                    }

                }
            });
        //api Calls

        try {
            getMobile("1");
            getBrands("1");
            getReview();
        }
        catch (NullPointerException e) {
            Toast.makeText(this, "Check your internet connection or try again later", Toast.LENGTH_SHORT).show();
        }

        search = (TextView) findViewById(R.id.searching);
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { open(); }});
    }
    public void init() {

        //cardView clickListener
        phone = findViewById(R.id.phoneCard);
        tablet = findViewById(R.id.tabletCard);
        watch = findViewById(R.id.watchCard);
        eearbud = findViewById(R.id.earbudCard);
        phoneOffer = findViewById(R.id.phoneOffer);
        tabletOffer = findViewById(R.id.tabletOffer);
        watchOffer = findViewById(R.id.watchOffer);
        eearbudOffer = findViewById(R.id.earbudOffer);
        user_mobile=findViewById(R.id.user_mobile);

        phoneName = findViewById(R.id.presentPhone);


        //shimmerLayout
        mFrameLayout1 = findViewById(R.id.shimmerLayout1);
        mFrameLayout2 = findViewById(R.id.shimmerLayout2);
        mFrameLayout3 = findViewById(R.id.shimmerLayout3);

        //recyclerView
        mobileRecycler = findViewById(R.id.phoneRecycler);
        brandRecycler = findViewById(R.id.brandRecycler);
        reviewRecycler = findViewById(R.id.reviewRecycler);
        current_device=findViewById(R.id.device_image);
        current_price=findViewById(R.id.presentPhonePrice);
    }

    @Override
    protected void onResume() {
        mFrameLayout1.startShimmer();
        mFrameLayout2.startShimmer();
        mFrameLayout3.startShimmer();
        super.onResume();
    }

    @SuppressLint("WrongConstant")
    @Override
    protected void onPause() {
        super.onPause();
        mFrameLayout1.stopShimmer();
        mFrameLayout2.stopShimmer();
        mFrameLayout3.stopShimmer();
        // Retrieving the value using its keys the file name
// must be same in both saving and retrieving the data
        sharedPreferences =  getSharedPreferences("MySharedPref", MODE_APPEND);

// The value will be default as empty string because for
// the very first time when the app is opened, there is nothing to show
        String s1 = sharedPreferences.getString("name", "");


// We can then use the data
        System.out.println("testing phone name: "+ s1);
        Log.d("testing phone name", "onDestroy: ");
    }

    public void open() {
        Intent intent =  new Intent(MainActivity.this, SearchActivity.class);
        startActivity(intent);
    }

    public void getReview(){
        apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        Call<List<Reviews>> call = apiInterface.getReviews();
        call.enqueue(new Callback<List<Reviews>>() {
            @Override
            public void onResponse(Call<List<Reviews>> call, Response<List<Reviews>> response) {
                List<Reviews> list = response.body();
                try {
                    reviewRecycler.setAdapter(new ReviewAdapter(MainActivity.this, list));
                    mFrameLayout3.startShimmer();
                    mFrameLayout3.setVisibility(View.GONE);
                    reviewRecycler.setVisibility(View.VISIBLE);
                } catch (NullPointerException e)
                {
                    Toast.makeText(MainActivity.this, "Not showing", Toast.LENGTH_SHORT).show();
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
                List<Mobile> list = response.body();
                    try {
                        mobileRecycler.setAdapter(new TopMobileAdapter(MainActivity.this, list));
                        mFrameLayout2.startShimmer();
                        mFrameLayout2.setVisibility(View.GONE);
                        mobileRecycler.setVisibility(View.VISIBLE);
                    } catch (NullPointerException e)
                    {
                        Toast.makeText(MainActivity.this, "Not showing", Toast.LENGTH_SHORT).show();
                    }

                    mobileRecycler.addOnItemTouchListener(new RecyclerItemClickListener(MainActivity.this, mobileRecycler, new RecyclerItemClickListener.OnItemClickListener() {
                        @Override
                        public void onItemClick(View view, int position) {
                            progressDoalog = new ProgressDialog(MainActivity.this);
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
                           String url = list.get(position).getUrl();
                            Intent intent = new Intent(MainActivity.this, WebViewActivity.class);
                            intent.putExtra("url", url);
                            // Storing data into SharedPreferences
                            sharedPreferences = getSharedPreferences("MySharedPref",MODE_PRIVATE);

// Creating an Editor object to edit(write to the file)
                            SharedPreferences.Editor myPhone = sharedPreferences.edit();
                            myPhone.putString("image", list.get(position).getFile());
                            myPhone.putBoolean("phoneClick", true);

// Storing the key and its value as the data fetched from edittext
                            myPhone.putString("name", list.get(position).getPname().toString());

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
                List<Brand> list = response.body();
                try {

                    brandRecycler.setAdapter(new TopBrandAdapter(MainActivity.this, list));
                    mFrameLayout1.startShimmer();
                    mFrameLayout1.setVisibility(View.GONE);
                    brandRecycler.setVisibility(View.VISIBLE);
                } catch (NullPointerException e)
                {
                    Toast.makeText(MainActivity.this, "Not showing", Toast.LENGTH_SHORT).show();
                }
                brandRecycler.addOnItemTouchListener(new RecyclerItemClickListener(MainActivity.this, brandRecycler, new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        Intent intent = new Intent(MainActivity.this, ModelActivity.class);
                        intent.putExtra("key", key);
                        intent.putExtra("bid", list.get(position).getId());

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
    public void onBackPressed() {
        if (mBackPressed + TIME_INTERVAL > System.currentTimeMillis())
        {
            super.onBackPressed();
            return;
        }
        else { Toast.makeText(MainActivity.this, "Tap back button in order to exit", Toast.LENGTH_SHORT).show(); }

        mBackPressed = System.currentTimeMillis();
    }
    public void fetch_data(String key){
        apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        Call<com.example.sellit.ModelResponse.Response> call = apiInterface.getUsers(key);
        call.enqueue(new Callback<com.example.sellit.ModelResponse.Response>() {
            @Override
            public void onResponse(Call<com.example.sellit.ModelResponse.Response> call, Response<com.example.sellit.ModelResponse.Response> response) {
                users=response.body().getResponse();
               // Toast.makeText(MainActivity.this, "it works "+ users.toString(), Toast.LENGTH_SHORT).show();
                Glide.with(MainActivity.this).load(users.get(0).getImageUrl()).into(current_device);
                fetch_price(users.get(0).getId());


            }

            @Override
            public void onFailure(Call<com.example.sellit.ModelResponse.Response> call, Throwable t) {

            }
        });

    }
    public void fetch_price(String mid){
        apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        Call<HighestPrice> call=apiInterface.getHighPrice(mid);
        call.enqueue(new Callback<HighestPrice>() {
            @Override
            public void onResponse(Call<HighestPrice> call, Response<HighestPrice> response) {
                current_value=response.body().getValue();
                //Toast.makeText(MainActivity.this, "Current Price " + current_value, Toast.LENGTH_SHORT).show();
                current_price.setText("Current Value: "+"₹" +current_value);
            }

            @Override
            public void onFailure(Call<HighestPrice> call, Throwable t) {
                    Log.e("error_in_price",t.getMessage());
            }
        });
    }


}