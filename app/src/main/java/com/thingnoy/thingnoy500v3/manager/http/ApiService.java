package com.thingnoy.thingnoy500v3.manager.http;

import com.thingnoy.thingnoy500v3.adapter.dao.FoodProductCollectionDao;
import com.thingnoy.thingnoy500v3.dao.PhotoItemCollectionDao;
import com.thingnoy.thingnoy500v3.dao.ResProCollectionDao;
import com.thingnoy.thingnoy500v3.dao.ResReviewBody;
import com.thingnoy.thingnoy500v3.dao.ReturnReviewInsertDao;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by HBO on 16/9/2560.
 */

public interface ApiService {

    @GET("list")
    Call<PhotoItemCollectionDao> loadPhotolist();

    @GET("apitest/listtest")
    Call<ResProCollectionDao> loadResProlist();

    @Headers("Content-Type: application/json")
    @POST("apigetreviewres/insertresreview")
    Call<ReturnReviewInsertDao> insertResreview(@Body ResReviewBody body);

    @GET("apitestfood2/listtestfood2")
    Call<FoodProductCollectionDao> getFoodById(@Query("id") int id);

}
