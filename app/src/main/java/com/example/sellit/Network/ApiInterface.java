package com.example.sellit.Network;

import com.example.sellit.Adapter.SeriesModels;
import com.example.sellit.ModelResponse.CommonBrand;
import com.example.sellit.ModelResponse.HighestPrice;
import com.example.sellit.ModelResponse.Model;
import com.example.sellit.ModelResponse.Reviews;
import com.example.sellit.ModelResponse.Brand;
import com.example.sellit.ModelResponse.Mobile;
import com.example.sellit.ModelResponse.Response;
import com.example.sellit.ModelResponse.Series;
import com.example.sellit.ModelResponse.SeriesModel;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface ApiInterface {

    @FormUrlEncoded
    @POST("search.php")
    Call<Response> getUsers(@Field("search") String name);

    @FormUrlEncoded
    @POST("topmobbrand.php")
    Call<List<Brand>> getBrands(@Field("cid") String name
    );

    @FormUrlEncoded
    @POST("commonbrands.php")
    Call<List<CommonBrand>> getCommonBrands(@Field("cid") String name
    );

    @FormUrlEncoded
    @POST("topmobile.php")
    Call<List<Mobile>> getMobiles(@Field("cid") String name);

    @FormUrlEncoded
    @POST("sesries.php")
    Call<List<Series>> getSeries(@Field("bid") String name
    );

    @FormUrlEncoded
    @POST("seriesmodels.php")
    Call<List<SeriesModel>> getSeriesModels(@Field("bid") String bid, @Field("cid") String cid, @Field("sid") String sid
    );

    @FormUrlEncoded
    @POST("commonmodels.php")
    Call<List<Model>> getModels(@Field("bid") String name
    );


    @GET("reviews.php")
    Call<List<Reviews>> getReviews();

    @FormUrlEncoded
    @POST("producthighprice.php")
    Call<HighestPrice> getHighPrice(@Field("mid") String id);

}
