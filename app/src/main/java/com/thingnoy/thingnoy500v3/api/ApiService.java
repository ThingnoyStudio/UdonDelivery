package com.thingnoy.thingnoy500v3.api;

import com.thingnoy.thingnoy500v3.api.result.foodMenu.FoodMenuResultGroupO;
import com.thingnoy.thingnoy500v3.api.request.AddReviewBody;
import com.thingnoy.thingnoy500v3.api.result.foodMenu.fds.FoodMenuResultGroup;
import com.thingnoy.thingnoy500v3.api.result.promotion.PromotionResultGroup;
import com.thingnoy.thingnoy500v3.api.result.restaurant.RestaurantResultGroup;
import com.thingnoy.thingnoy500v3.api.result.review.AddReviewResult;
import com.thingnoy.thingnoy500v3.api.result.review.ReviewResultGroup;
import com.thingnoy.thingnoy500v3.api.dao.PhotoItemCollectionDao;
import com.thingnoy.thingnoy500v3.api.dao.ResProCollectionDao;


import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Query;

import static com.thingnoy.thingnoy500v3.api.UdonFoodURL.URL_GET_FOOD_MENU;
import static com.thingnoy.thingnoy500v3.api.UdonFoodURL.URL_GET_FOOD_MENU2;
import static com.thingnoy.thingnoy500v3.api.UdonFoodURL.URL_GET_PROMOTION;
import static com.thingnoy.thingnoy500v3.api.UdonFoodURL.URL_GET_RESTAURANT;
import static com.thingnoy.thingnoy500v3.api.UdonFoodURL.URL_GET_REVIEW;
import static com.thingnoy.thingnoy500v3.api.UdonFoodURL.URL_ADD_REVIEW;

/**
 * Created by HBO on 16/9/2560.
 */

public interface ApiService {
    /* ============ old api ============ */
    @GET("list")
    Call<PhotoItemCollectionDao> loadPhotolist();

    @GET("apitest/listtest")
    Call<ResProCollectionDao> loadResProlist();

    /* ============ api ============ */

    @Headers("Content-Type: application/json")
    @POST(URL_ADD_REVIEW)
    Call<AddReviewResult> addReview(@Body AddReviewBody body);

    @GET(URL_GET_REVIEW)
    Call<ReviewResultGroup> getReview(@Query("id") int id);

    @GET(URL_GET_FOOD_MENU)
    Call<FoodMenuResultGroupO> getFoodMenuByIdO(@Query("id") int id);

    @GET(URL_GET_PROMOTION)
    Call<PromotionResultGroup> getPromotion();

    @GET(URL_GET_RESTAURANT)
    Call<RestaurantResultGroup> getRestaurant();

    @GET(URL_GET_FOOD_MENU2)
    Call<FoodMenuResultGroup> getFoodMenuById(@Query("id") int id);

}
