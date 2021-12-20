package com.example.sellit.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.sellit.Adapter.SearchAdapter;
import com.example.sellit.Network.ApiClient;
import com.example.sellit.Network.ApiInterface;
import com.example.sellit.ModelResponse.Response;
import com.example.sellit.ModelResponse.User;
import com.example.sellit.R;
import com.example.sellit.RecyclerViewExtention.RecyclerItemClickListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;

public class SearchActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private List<User> user;
    private SearchAdapter adapter ;
    private ApiInterface apiInterface;
    AutoCompleteTextView autoCompleteTextView;
    ImageView imageView;
    ProgressDialog progressDialog;
    SharedPreferences sharedPreferences;
    ArrayAdapter<String> arrayAdapter;
    List<String> names;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        //fetch("");
        autoCompleteTextView = (AutoCompleteTextView) findViewById(R.id.searchIt);
        autoCompleteTextView.requestFocus();
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        imageView = findViewById(R.id.back);
        user=new ArrayList<User>();
        adapter = new SearchAdapter(SearchActivity.this, user);
        recyclerView.setAdapter(adapter);

            imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                autoCompleteTextView.clearFocus();
                finish();
            }
        });

        autoCompleteTextView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//                adapter.notifyDataSetChanged();

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                autoCompleteTextView.setThreshold(1);
                fetch(s.toString());
                if (s.toString().equals("") && user != null)
                {
                    user.clear();
                    adapter.notifyDataSetChanged();
                }
               /* if (user != null)
                {
                   adapter.notifyDataSetChanged();

                }*/


            }

            @Override
            public void afterTextChanged(Editable s) {
                autoCompleteTextView.setThreshold(1);
                //fetch(s.toString());
                if (s.toString().equals(""))
                {
                    user.clear();
                     // names.clear();
                    adapter.notifyDataSetChanged();
                    //arrayAdapter.notifyDataSetChanged();

                }
                //adapter.notifyDataSetChanged();
                /*if (user != null)
                {
                    adapter.notifyDataSetChanged();

                }*/

            }
        });


    }
    public void fetch( String key) {
        apiInterface = ApiClient.getApiClient().create(ApiInterface.class);

        Call<Response> call = apiInterface.getUsers(key);
        call.enqueue(new Callback<Response>() {
            @Override
            public void onResponse(Call<Response> call, retrofit2.Response<Response> response) {
                if(user!=null)
                    user.clear();
                //user=response.body().getResponse();
                if(response.body().getStatus().equals("1")){
                    //recyclerView.setVisibility(View.VISIBLE);
                    user.addAll(response.body().getResponse());
                    autoCompleteTextView.setThreshold(1);
                    autoCompleteTextView.requestFocus();
                    adapter.notifyDataSetChanged();
                   /* recyclerView.setAdapter(adapter);*/
                   /*
                    names=new ArrayList<String>();
                    for(int i=0;i<user.size();i++){
                        names.add(user.get(i).getName());
                    }
                    arrayAdapter=new ArrayAdapter<String>(SearchActivity.this, android.R.layout.simple_list_item_1,names);
                    autoCompleteTextView.setAdapter(arrayAdapter);
                    */
                    recyclerView.addOnItemTouchListener(
                            new RecyclerItemClickListener(SearchActivity.this, recyclerView ,new RecyclerItemClickListener.OnItemClickListener() {
                                @Override public void onItemClick(View view, int position) {

                                    progressDialog = new ProgressDialog(SearchActivity.this);
                                    progressDialog.setMax(100);
                                    progressDialog.setMessage("Please wait....");
                                    progressDialog.setTitle("Searching..");
                                    progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                                    progressDialog.show();
                                    new Thread(new Runnable() {
                                        @Override
                                        public void run() {
                                            try {
                                                while (progressDialog.getProgress() <= progressDialog
                                                        .getMax()) {
                                                    Thread.sleep(200);
                                                    //handle.sendMessage(handle.obtainMessage());
                                                    if (progressDialog.getProgress() == progressDialog
                                                            .getMax()) {
                                                        progressDialog.dismiss();
                                                    }
                                                }
                                            } catch (Exception e) {
                                                e.printStackTrace();
                                            }
                                        }
                                    }).start();

                                    String url = adapter.getUrl(position);
                                    sharedPreferences=getSharedPreferences("MySharedPref",MODE_PRIVATE);
                                    sharedPreferences.edit().putString("name",user.get(position).getName()).apply();
                                    //Uri uri = Uri.parse(url);

                                    Intent intent = new Intent(SearchActivity.this, WebViewActivity.class);
                                    intent.putExtra("url", url);
                                    startActivity(intent);
                                    finish();
                                }

                                @Override public void onLongItemClick(View view, int position) {
                                    // do whatever

                                }
                            })
                    );

                }
                else {
                    recyclerView.clearFocus();
                    //recyclerView.setVisibility(View.GONE);
                    Toast.makeText(SearchActivity.this, "No such device found!", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<Response> call, Throwable t) {
                Log.e("fetch","not working->" + t.getMessage());
            }
        });

    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        MenuInflater inflater = getMenuInflater();
//        inflater.inflate(R.menu.menu, menu);
//
//        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
//        SearchView searchView = (SearchView) menu.findItem(R.id.search).getActionView();
//        searchView.setSearchableInfo(
//                searchManager.getSearchableInfo(getComponentName()));
//        searchView.setIconifiedByDefault(false);
//        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
//            @Override
//            public boolean onQueryTextSubmit(String query) {
//                fetch( query);
//                return false;
//            }
//
//            @Override
//            public boolean onQueryTextChange(String newText) {
//                fetch(newText);
//                return false;
//            }
//        });
//        return true;
//    }

}