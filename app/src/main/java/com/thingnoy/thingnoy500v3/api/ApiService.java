package com.thingnoy.thingnoy500v3.api;

import com.thingnoy.thingnoy500v3.api.request.AddNewOrderBody;
import com.thingnoy.thingnoy500v3.api.request.favorite.AddFavoriteBody;
import com.thingnoy.thingnoy500v3.api.request.login.LoginBody;
import com.thingnoy.thingnoy500v3.api.request.register.RegisterBody;
import com.thingnoy.thingnoy500v3.api.result.deliverypro.DeliveryProResultGroup;
import com.thingnoy.thingnoy500v3.api.result.derivery_time.DeliverTimeResultGroup;
import com.thingnoy.thingnoy500v3.api.result.favorite.FavoriteResultGroup;
import com.thingnoy.thingnoy500v3.api.result.foodMenu.FoodMenuResultGroupO;
import com.thingnoy.thingnoy500v3.api.request.AddReviewBody;
import com.thingnoy.thingnoy500v3.api.result.foodMenu.fds.FoodMenuResultGroup;
import com.thingnoy.thingnoy500v3.api.result.history.HistoryResultGroup;
import com.thingnoy.thingnoy500v3.api.result.login.LoginResultGroup;
import com.thingnoy.thingnoy500v3.api.result.new_restaurant.NewRestaurantResultGroup;
import com.thingnoy.thingnoy500v3.api.result.profile.ProfileResultGroup;
import com.thingnoy.thingnoy500v3.api.result.promotion.PromotionResultGroup;
import com.thingnoy.thingnoy500v3.api.result.restaurant.RestaurantResultGroup;
import com.thingnoy.thingnoy500v3.api.result.review.AddReviewResult;
import com.thingnoy.thingnoy500v3.api.result.review.ReviewResultGroup;
import com.thingnoy.thingnoy500v3.api.dao.PhotoItemCollectionDao;
import com.thingnoy.thingnoy500v3.api.dao.ResProCollectionDao;
import com.thingnoy.thingnoy500v3.api.result.status.StatusResult;
import com.thingnoy.thingnoy500v3.api.result.userAddress.UserAddressResultGroup;


import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Query;

import static com.thingnoy.thingnoy500v3.api.UdonFoodURL.URL_ADD_FAVORITE;
import static com.thingnoy.thingnoy500v3.api.UdonFoodURL.URL_ADD_NEWORDER;
import static com.thingnoy.thingnoy500v3.api.UdonFoodURL.URL_ADD_USER;
import static com.thingnoy.thingnoy500v3.api.UdonFoodURL.URL_DEL_FAVORITE;
import static com.thingnoy.thingnoy500v3.api.UdonFoodURL.URL_GET_DELIVERY_PRO;
import static com.thingnoy.thingnoy500v3.api.UdonFoodURL.URL_GET_DELIVERY_TIME;
import static com.thingnoy.thingnoy500v3.api.UdonFoodURL.URL_GET_FAVORITE;
import static com.thingnoy.thingnoy500v3.api.UdonFoodURL.URL_GET_FOOD_MENU;
import static com.thingnoy.thingnoy500v3.api.UdonFoodURL.URL_GET_FOOD_MENU2;
import static com.thingnoy.thingnoy500v3.api.UdonFoodURL.URL_GET_HISTORY;
import static com.thingnoy.thingnoy500v3.api.UdonFoodURL.URL_GET_NEW_RESTAURANT;
import static com.thingnoy.thingnoy500v3.api.UdonFoodURL.URL_GET_PROFIlE;
import static com.thingnoy.thingnoy500v3.api.UdonFoodURL.URL_GET_PROMOTION;
import static com.thingnoy.thingnoy500v3.api.UdonFoodURL.URL_GET_RESTAURANT;
import static com.thingnoy.thingnoy500v3.api.UdonFoodURL.URL_GET_REVIEW;
import static com.thingnoy.thingnoy500v3.api.UdonFoodURL.URL_ADD_REVIEW;
import static com.thingnoy.thingnoy500v3.api.UdonFoodURL.URL_GET_USER_ADDRESS;
import static com.thingnoy.thingnoy500v3.api.UdonFoodURL.URL_LOGIN;

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

    @GET(URL_GET_NEW_RESTAURANT)
    Call<NewRestaurantResultGroup> getRestaurant();

    @GET(URL_GET_FOOD_MENU2)
    Call<FoodMenuResultGroup> getFoodMenuById(@Query("id") int id);

    @GET(URL_GET_USER_ADDRESS)
    Call<UserAddressResultGroup> getUserAddress(@Query("id") int id);

    @GET(URL_GET_DELIVERY_TIME)
    Call<DeliverTimeResultGroup> getDeliveryTime();

    @GET(URL_GET_HISTORY)
    Call<HistoryResultGroup> getHistory(@Query("id") int id);

    @Headers("Content-Type: application/json")
    @POST(URL_ADD_NEWORDER)
    Call<StatusResult> addNewOrder(@Body AddNewOrderBody body);

    @GET(URL_GET_PROFIlE)
    Call<ProfileResultGroup> getProfile(@Query("id") int id);

    @Headers("Content-Type: application/json")
    @POST(URL_ADD_FAVORITE)
    Call<StatusResult> addFavoriteMenu(@Body AddFavoriteBody body);

    @Headers("Content-Type: application/json")
    @POST(URL_ADD_USER)
    Call<StatusResult> addUser(@Body RegisterBody body);

    @Headers("Content-Type: application/json")
    @POST(URL_LOGIN)
    Call<LoginResultGroup> login(@Body LoginBody body);

    @GET(URL_GET_DELIVERY_PRO)
    Call<DeliveryProResultGroup> getDeliveryPro();

    @DELETE(URL_DEL_FAVORITE)
    Call<StatusResult> delFavorite(@Query("idf") int idFood,
                                   @Query("idc") int idUser);

    @GET(URL_GET_FAVORITE)
    Call<FavoriteResultGroup> getFavorite(@Query("id") int idFood);


}
