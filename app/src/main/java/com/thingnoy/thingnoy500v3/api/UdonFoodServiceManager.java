package com.thingnoy.thingnoy500v3.api;

import android.support.annotation.NonNull;
import android.util.Log;

import com.thingnoy.thingnoy500v3.adapter.item.FoodProductItemGroup;
import com.thingnoy.thingnoy500v3.api.result.foodMenu.FoodMenuResultGroupO;
import com.thingnoy.thingnoy500v3.api.result.foodMenu.fds.FoodMenuResultGroup;
import com.thingnoy.thingnoy500v3.api.result.promotion.PromotionResultGroup;
import com.thingnoy.thingnoy500v3.api.result.restaurant.RestaurantResultGroup;
import com.thingnoy.thingnoy500v3.api.request.AddReviewBody;
import com.thingnoy.thingnoy500v3.api.result.review.AddReviewResult;
import com.thingnoy.thingnoy500v3.api.result.review.ReviewResultGroup;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.thingnoy.thingnoy500v3.api.UdonFoodURL.BASE_URL;

/**
 * Created by HBO on 4/4/2561.
 */

public class UdonFoodServiceManager {
//    public static final String SUCCESS = "SUCCESS";

    private static UdonFoodServiceManager instance;
    private static ApiService api;
    private Call<AddReviewResult> callAddReview;

    private UdonFoodServiceManager() {
    }

    public static UdonFoodServiceManager getInstance(){
        if( instance == null ){
            instance = new UdonFoodServiceManager();
        }
        return instance;
    }

    public static void setApi(ApiService mockApi) {
        api = mockApi;
    }

    public interface UdonFoodManagerCallback<T> {
        void onSuccess(T result);

        void onFailure(Throwable t);
    }


    /*============================ Request ==============================*/

    //region request Get Restaurant
    public void requestRestaurant(final UdonFoodManagerCallback<RestaurantResultGroup> callback) {
        requestRestaurantCall().enqueue(new Callback<RestaurantResultGroup>() {
            @Override
            public void onResponse(@NonNull Call<RestaurantResultGroup> call, @NonNull Response<RestaurantResultGroup> response) {
                if (callback != null) {
                    if (restaurantChecker(response)) {
                        callback.onSuccess(response.body());
                    } else {
                        callback.onFailure(new Throwable("Response invalid."));
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<RestaurantResultGroup> call, @NonNull Throwable t) {
                if (!call.isCanceled()) {
                    if (callback != null) {
                        callback.onFailure(t);
                    }
                }
            }
        });

    }

    public Call<RestaurantResultGroup> requestRestaurantCall() {
        return UdonService.newInstance(BASE_URL)
                .getApi(api)
                .getRestaurant();
    }

    private boolean restaurantChecker(Response<RestaurantResultGroup> response) {
        if (response.isSuccessful()) {
            RestaurantResultGroup result = response.body();
//            return SUCCESS.equals(result.getmSuccess()) && result.getmData() != null;
            assert result != null;
            if (result.getmSuccess() != null)
                if (result.getmSuccess()) return true;
            return false;
        }
        return false;
    }
    //endregion

    //region request Get Promotion
    public void requestPromotion(final UdonFoodManagerCallback<PromotionResultGroup> callback) {
        requestPromotionCall().enqueue(new Callback<PromotionResultGroup>() {
            @Override
            public void onResponse(@NonNull Call<PromotionResultGroup> call, @NonNull Response<PromotionResultGroup> response) {
                if (callback != null) {
                    if (promotionChecker(response)) {
                        callback.onSuccess(response.body());
                    } else {
                        callback.onFailure(new Throwable("Response invalid."));
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<PromotionResultGroup> call, @NonNull Throwable t) {
                if (!call.isCanceled()) {
                    if (callback != null) {
                        callback.onFailure(t);
                    }
                }
            }
        });

    }

    public Call<PromotionResultGroup> requestPromotionCall() {
        return UdonService.newInstance(BASE_URL)
                .getApi(api)
                .getPromotion();
    }

    private boolean promotionChecker(Response<PromotionResultGroup> response) {
        if (response.isSuccessful()) {
            PromotionResultGroup result = response.body();
            assert result != null;
            if (result.getmSuccess() != null)
                if (result.getmSuccess()) return true;
            return false;
        }
        return false;
    }
    //endregion

    //region request Get FoodMenu O
    public void requestFoodMenuO(int id, final UdonFoodManagerCallback<FoodMenuResultGroupO> callback) {
        requestFoodMenuCallO(id).enqueue(new Callback<FoodMenuResultGroupO>() {
            @Override
            public void onResponse(@NonNull Call<FoodMenuResultGroupO> call, @NonNull Response<FoodMenuResultGroupO> response) {
                if (callback != null) {
                    if (foodMenuCheckerO(response)) {
                        callback.onSuccess(response.body());
                    } else {
                        callback.onFailure(new Throwable("Response invalid."));
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<FoodMenuResultGroupO> call, @NonNull Throwable t) {
                if (!call.isCanceled()) {
                    if (callback != null) {
                        callback.onFailure(t);
                    }
                }
            }
        });
    }

    public Call<FoodMenuResultGroupO> requestFoodMenuCallO(int id) {
        return UdonService.newInstance(BASE_URL)
                .getApi(api)
                .getFoodMenuByIdO(id);
    }

    private boolean foodMenuCheckerO(Response<FoodMenuResultGroupO> response) {
        if (response.isSuccessful()) {
            FoodMenuResultGroupO result = response.body();
            assert result != null;
            if (result.getmSuccess() != null)
                if (result.getmSuccess()) return true;
            return false;
        }
        return false;
    }
    //endregion

    //region request Get Review
    public void requestGetReview(int id, final UdonFoodManagerCallback<ReviewResultGroup> callback) {
        requestGetReviewCall(id).enqueue(new Callback<ReviewResultGroup>() {
            @Override
            public void onResponse(@NonNull Call<ReviewResultGroup> call, @NonNull Response<ReviewResultGroup> response) {
                if (callback != null) {
                    if (getReviewChecker(response)) {
                        callback.onSuccess(response.body());
                    } else {
                        callback.onFailure(new Throwable("Response invalid."));
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<ReviewResultGroup> call, @NonNull Throwable t) {
                if (!call.isCanceled()) {
                    if (callback != null) {
                        callback.onFailure(t);
                    }
                }
            }
        });
    }

    public Call<ReviewResultGroup> requestGetReviewCall(int id) {
        return UdonService.newInstance(BASE_URL)
                .getApi(api)
                .getReview(id);
    }

    private boolean getReviewChecker(Response<ReviewResultGroup> response) {
        if (response.isSuccessful()) {
            ReviewResultGroup result = response.body();
            assert result != null;
            if (result.getmSuccess() != null)
                if (result.getmSuccess()) return true;
            return false;
        }
        return false;
    }
    //endregion

    //region request Add Review
    public void AddReview(AddReviewBody body, final UdonFoodManagerCallback<AddReviewResult> callback) {
        callAddReview = requestAddReview(body);
        callAddReview.enqueue(new Callback<AddReviewResult>() {
            @Override
            public void onResponse(@NonNull Call<AddReviewResult> call, @NonNull Response<AddReviewResult> response) {
                if (callback != null) {
                    if (addReviewChecker(response)) {
                        callback.onSuccess(response.body());
                    } else {
                        callback.onFailure(new Throwable("Response invalid."));
                    }
                }
                callAddReview = null;
            }

            @Override
            public void onFailure(@NonNull Call<AddReviewResult> call, @NonNull Throwable t) {
                if (callback != null) {
                    callback.onFailure(t);
                }
                callAddReview = null;
            }
        });
    }

    public Call<AddReviewResult> requestAddReview(AddReviewBody body) {
        return UdonService.newInstance(BASE_URL)
                .getApi(api)
                .addReview(body);
    }

    private boolean addReviewChecker(Response<AddReviewResult> response) {
        if (response.isSuccessful()) {
            AddReviewResult result = response.body();
            assert result != null;
            if (result.getStatus() != null)
                if (result.getStatus()) return true;
            return false;
        }
        return false;
    }

    public void cancelAddReview() {
        if (callAddReview != null) {
            callAddReview.cancel();
        }
    }
    //endregion

    //region request Get FoodMenu
    public void requestFoodMenu(int id, final UdonFoodManagerCallback<FoodMenuResultGroup> callback) {
        requestFoodMenuCall(id).enqueue(new Callback<FoodMenuResultGroup>() {
            @Override
            public void onResponse(@NonNull Call<FoodMenuResultGroup> call, @NonNull Response<FoodMenuResultGroup> response) {
                if (callback != null) {
                    if (foodMenuChecker(response)) {
                        callback.onSuccess(response.body());
                    } else {
                        callback.onFailure(new Throwable("Response invalid."));
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<FoodMenuResultGroup> call, @NonNull Throwable t) {
                if (!call.isCanceled()) {
                    if (callback != null) {
                        callback.onFailure(t);
                    }
                }
            }
        });
    }

    public Call<FoodMenuResultGroup> requestFoodMenuCall(int id) {
        return UdonService.newInstance(BASE_URL)
                .getApi(api)
                .getFoodMenuById(id);
    }

    private boolean foodMenuChecker(Response<FoodMenuResultGroup> response) {
        if (response.isSuccessful()) {
            FoodMenuResultGroup result = response.body();
            assert result != null;
            if (result.getSuccess() != null)
                if (result.getSuccess()) return true;
            return false;
        }
        return false;
    }
    //endregion

}
