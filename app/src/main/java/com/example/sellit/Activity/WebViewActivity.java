package com.example.sellit.Activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.graphics.drawable.IconCompat;

import android.annotation.SuppressLint;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.AutoCompleteTextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;
import com.example.sellit.R;

import java.util.ArrayList;

import im.delight.android.webview.AdvancedWebView;

public class WebViewActivity extends AppCompatActivity {

    private static final int TIME_INTERVAL = 2000; // # milliseconds, desired time passed between two back presses.
    private long mBackPressed;
    String url;
    AdvancedWebView mWebView ;
    ProgressDialog progressDoalog;
    private String CHANNEL_ID = "my_channel_01";
    private String name = "my_channel";
    private String Description = "This is my channel";
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wen_view);
        Intent intent = getIntent();
        if(intent.hasExtra("intent_type")){
            Log.e("intent",intent.getStringExtra("intent_type"));
        }
        progressDoalog = new ProgressDialog(WebViewActivity.this);
        progressDoalog.setMax(100);
        progressDoalog.setMessage("Please wait....");
        progressDoalog.setTitle("");
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
        mWebView  = findViewById(R.id.webview);
        mWebView.setMixedContentAllowed(false);

        url=intent.getStringExtra("url");
        mWebView.loadUrl(intent.getStringExtra("url"));
        final Handler handler = new Handler(Looper.getMainLooper());
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                progressDoalog.dismiss();
            }
        }, 1500);


    }

    @Override
    protected void onStart() {
        super.onStart();

    }

    @Override
    public void onBackPressed() {
        if (mBackPressed + TIME_INTERVAL > System.currentTimeMillis())
        {
            super.onBackPressed();
            finish();
        }
        else { Toast.makeText(WebViewActivity.this, "Press again if you want to cancel", Toast.LENGTH_SHORT).show(); }

        mBackPressed = System.currentTimeMillis();
    }

    @Override
    protected void onPause() {
        super.onPause();
        progressDoalog.dismiss();
    }

    @SuppressLint("WrongConstant")
    @Override
    protected void onDestroy() {
        super.onDestroy();

        new Handler().postDelayed(new Runnable(){
            @Override
            public void run() {
                /* Create an Intent that will start the Menu-Activity. */

                int NOTIFICATION_ID = 234;
                NotificationManager notificationManager = (NotificationManager) getSystemService(NotificationManager.class);

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    //int sdk_int=android.os.Build.VERSION.SDK_INT;
                    int importance = NotificationManager.IMPORTANCE_DEFAULT;
                    NotificationChannel mChannel = new NotificationChannel(CHANNEL_ID, name, importance);
                    mChannel.setDescription(Description);
                    mChannel.enableLights(true);
                    mChannel.setLightColor(Color.RED);
                    mChannel.enableVibration(true);
                    mChannel.setVibrationPattern(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400});
                    mChannel.setShowBadge(false);
                    notificationManager.createNotificationChannel(mChannel);
                }
                sharedPreferences =  getSharedPreferences("MySharedPref", MODE_APPEND);

                String s1 = sharedPreferences.getString("name", "");
                Boolean checkIfDirect = sharedPreferences.getBoolean("phoneClick", true);
                String img = sharedPreferences.getString("image", "");
                Log.e("image uri: ", img );
                Log.e("image uri: ", String.valueOf(checkIfDirect));
                /*if (checkIfDirect)
                {
                    sharedPreferences.edit().putBoolean("phoneClick", false);


                    NotificationCompat.Builder builder = new NotificationCompat.Builder(WebViewActivity.this, CHANNEL_ID)
                            .setSmallIcon(R.drawable.icon)
                            .setContentTitle("Sell your " + s1)
                            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                            .setContentText("Don't keep on waiting, sell it right away for ₹10,000!");

                    Intent resultIntent = new Intent(WebViewActivity.this, MainActivity.class);
                    TaskStackBuilder stackBuilder = TaskStackBuilder.create(WebViewActivity.this);
                    stackBuilder.addParentStack(MainActivity.class);
                    stackBuilder.addNextIntent(resultIntent);
                    PendingIntent resultPendingIntent = stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);
                    builder.setContentIntent(resultPendingIntent);
                    NotificationManagerCompat notificationManagerCompat=NotificationManagerCompat.from(WebViewActivity.this);
                    notificationManagerCompat.notify(NOTIFICATION_ID, builder.build());
                }
*/
                Glide.with(getApplicationContext())
                        .asBitmap()
                        .load(img)
                        .into(new CustomTarget<Bitmap>() {
                            @SuppressLint("CommitPrefEdits")
                            @Override
                            public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                                if (checkIfDirect)
                                {
                                    sharedPreferences.edit().putBoolean("phoneClick", false);


                                    NotificationCompat.Builder builder = new NotificationCompat.Builder(WebViewActivity.this, CHANNEL_ID)
                                            .setSmallIcon(IconCompat.createWithBitmap(resource))
                                            .setContentTitle("Sell your " + s1)
                                            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                                            .setContentText("Don't keep on waiting, sell it right away for ₹10,000!");

                                    Intent resultIntent = new Intent(WebViewActivity.this, WebViewActivity.class);
                                    resultIntent.putExtra("url",url);
                                    resultIntent.putExtra("intent_type","pending_intent");
                                   // resultIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);

                                    TaskStackBuilder stackBuilder = TaskStackBuilder.create(WebViewActivity.this);
                                    stackBuilder.addParentStack(MainActivity.class);
                                    stackBuilder.addNextIntent(resultIntent);
                                    PendingIntent resultPendingIntent = stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);
                                    builder.setContentIntent(resultPendingIntent);
                                    builder.setAutoCancel(true);
                                    NotificationManagerCompat notificationManagerCompat=NotificationManagerCompat.from(WebViewActivity.this);
                                    notificationManagerCompat.notify(NOTIFICATION_ID, builder.build());
                                }
                                //Log.e("image uri: ", String.valueOf(checkIfDirect));

                            }

                            @Override
                            public void onLoadCleared(@Nullable Drawable placeholder) {
                            }
                        });

                Log.e("image uri: ", String.valueOf(checkIfDirect));


            }
        }, 2000);


    }

}
