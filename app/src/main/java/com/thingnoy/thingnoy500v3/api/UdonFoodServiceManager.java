package com.thingnoy.thingnoy500v3.api;

import android.support.annotation.NonNull;
import android.util.Log;

import com.thingnoy.thingnoy500v3.api.request.AddNewOrderBody;
import com.thingnoy.thingnoy500v3.api.request.favorite.AddFavoriteBody;
import com.thingnoy.thingnoy500v3.api.request.login.LoginBody;
import com.thingnoy.thingnoy500v3.api.request.register.RegisterBody;
import com.thingnoy.thingnoy500v3.api.result.deliverypro.DeliveryProResultGroup;
import com.thingnoy.thingnoy500v3.api.result.derivery_time.DeliverTimeResultGroup;
import com.thingnoy.thingnoy500v3.api.result.favorite.FavoriteResultGroup;
import com.thingnoy.thingnoy500v3.api.result.foodMenu.FoodMenuResultGroupO;
import com.thingnoy.thingnoy500v3.api.result.foodMenu.fds.FoodMenuResultGroup;
import com.thingnoy.thingnoy500v3.api.result.history.HistoryResultGroup;
import com.thingnoy.thingnoy500v3.api.result.login.LoginResultGroup;
import com.thingnoy.thingnoy500v3.api.result.new_restaurant.NewRestaurantResultGroup;
import com.thingnoy.thingnoy500v3.api.result.profile.ProfileResultGroup;
import com.thingnoy.thingnoy500v3.api.result.promotion.PromotionResultGroup;
import com.thingnoy.thingnoy500v3.api.result.restaurant.RestaurantResultGroup;
import com.thingnoy.thingnoy500v3.api.request.AddReviewBody;
import com.thingnoy.thingnoy500v3.api.result.review.AddReviewResult;
import com.thingnoy.thingnoy500v3.api.result.review.ReviewResultGroup;
import com.thingnoy.thingnoy500v3.api.result.status.StatusResult;
import com.thingnoy.thingnoy500v3.api.result.userAddress.UserAddressResultGroup;

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
    private Call<StatusResult> callAddNewOrder;
    private Call<StatusResult> callAddFavorite;
    private Call<StatusResult> callAddUser;
    private Call<LoginResultGroup> callLogin;
    private Call<StatusResult> callDelFavorite;


    private UdonFoodServiceManager() {
    }

    public static UdonFoodServiceManager getInstance() {
        if (instance == null) {
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
    public void requestRestaurant(final UdonFoodManagerCallback<NewRestaurantResultGroup> callback) {
        requestRestaurantCall().enqueue(new Callback<NewRestaurantResultGroup>() {
            @Override
            public void onResponse(@NonNull Call<NewRestaurantResultGroup> call, @NonNull Response<NewRestaurantResultGroup> response) {
                if (callback != null) {
                    if (restaurantChecker(response)) {
                        callback.onSuccess(response.body());
                    } else {
                        callback.onFailure(new Throwable("Response invalid."));
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<NewRestaurantResultGroup> call, @NonNull Throwable t) {
                if (!call.isCanceled()) {
                    if (callback != null) {
                        callback.onFailure(t);
                    }
                }
            }
        });

    }

    private Call<NewRestaurantResultGroup> requestRestaurantCall() {
        return UdonService.newInstance(BASE_URL)
                .getApi(api)
                .getRestaurant();
    }

    private boolean restaurantChecker(Response<NewRestaurantResultGroup> response) {
        if (response.isSuccessful()) {
            NewRestaurantResultGroup result = response.body();
//            return SUCCESS.equals(result.getmSuccess()) && result.getmData() != null;
            assert result != null;
            if (result.getSuccess() != null)
                return result.getSuccess();
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

    //region request Get UserAddress
    public void requestUserAddress(int id, final UdonFoodManagerCallback<UserAddressResultGroup> callback) {
        requestUserAddressCall(id).enqueue(new Callback<UserAddressResultGroup>() {
            @Override
            public void onResponse(@NonNull Call<UserAddressResultGroup> call, @NonNull Response<UserAddressResultGroup> response) {
                if (callback != null) {
                    if (userAddressChecker(response)) {
                        callback.onSuccess(response.body());
                    } else {
                        callback.onFailure(new Throwable("Response invalid."));
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<UserAddressResultGroup> call, @NonNull Throwable t) {
                if (!call.isCanceled()) {
                    if (callback != null) {
                        callback.onFailure(t);
                    }
                }
            }
        });
    }

    public Call<UserAddressResultGroup> requestUserAddressCall(int id) {
        return UdonService.newInstance(BASE_URL)
                .getApi(api)
                .getUserAddress(id);
    }

    private boolean userAddressChecker(Response<UserAddressResultGroup> response) {
        if (response.isSuccessful()) {
            UserAddressResultGroup result = response.body();
            assert result != null;
            if (result.getSuccess() != null)
                return result.getSuccess();
            return false;
        }
        return false;
    }
    //endregion

    //region request Get DeliveryTime
    public void requestDeliveryTime(final UdonFoodManagerCallback<DeliverTimeResultGroup> callback) {
        requestDeliveryTimeCall().enqueue(new Callback<DeliverTimeResultGroup>() {
            @Override
            public void onResponse(@NonNull Call<DeliverTimeResultGroup> call, @NonNull Response<DeliverTimeResultGroup> response) {
                if (callback != null) {
                    if (deliveryTimeChecker(response)) {
                        callback.onSuccess(response.body());
                    } else {
                        callback.onFailure(new Throwable("Response invalid."));
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<DeliverTimeResultGroup> call, @NonNull Throwable t) {
                if (!call.isCanceled()) {
                    if (callback != null) {
                        callback.onFailure(t);
                    }
                }
            }
        });
    }

    public Call<DeliverTimeResultGroup> requestDeliveryTimeCall() {
        return UdonService.newInstance(BASE_URL)
                .getApi(api)
                .getDeliveryTime();
    }

    private boolean deliveryTimeChecker(Response<DeliverTimeResultGroup> response) {
        if (response.isSuccessful()) {
            DeliverTimeResultGroup result = response.body();
            assert result != null;
            if (result.getSuccess() != null)
                return result.getSuccess();
            return false;
        }
        return false;
    }
    //endregion

    //region request Add Order
    public void AddOrder(AddNewOrderBody body, final UdonFoodManagerCallback<StatusResult> callback) {
        callAddNewOrder = requestAddNewOrder(body);
        callAddNewOrder.enqueue(new Callback<StatusResult>() {
            @Override
            public void onResponse(@NonNull Call<StatusResult> call, @NonNull Response<StatusResult> response) {
                if (callback != null) {
                    if (addOrderChecker(response)) {
                        callback.onSuccess(response.body());
                    } else {
                        callback.onFailure(new Throwable("Response invalid."));
                    }
                }
                callAddNewOrder = null;
            }

            @Override
            public void onFailure(@NonNull Call<StatusResult> call, @NonNull Throwable t) {
                if (callback != null) {
                    callback.onFailure(t);
                }
                callAddNewOrder = null;
            }
        });
    }

    private Call<StatusResult> requestAddNewOrder(AddNewOrderBody body) {
        return UdonService.newInstance(BASE_URL)
                .getApi(api)
                .addNewOrder(body);
    }

    private boolean addOrderChecker(Response<StatusResult> response) {
        if (response.isSuccessful()) {
            StatusResult result = response.body();
            assert result != null;
            if (result.getStatus() != null) {
                return result.getStatus();
            }
        }
        return false;
    }

    public void cancelAddOrder() {
        if (callAddNewOrder != null) {
            callAddNewOrder.cancel();
        }
    }
    //endregion

    //region request Get History
    public void requestHistory(int id, final UdonFoodManagerCallback<HistoryResultGroup> callback) {
        requestHistoryCall(id).enqueue(new Callback<HistoryResultGroup>() {
            @Override
            public void onResponse(@NonNull Call<HistoryResultGroup> call, @NonNull Response<HistoryResultGroup> response) {
                if (callback != null) {
                    if (historyChecker(response)) {
                        callback.onSuccess(response.body());
                    } else {
                        callback.onFailure(new Throwable("Response invalid."));
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<HistoryResultGroup> call, @NonNull Throwable t) {
                if (!call.isCanceled()) {
                    if (callback != null) {
                        callback.onFailure(t);
                    }
                }
            }
        });
    }

    private Call<HistoryResultGroup> requestHistoryCall(int id) {
        return UdonService.newInstance(BASE_URL)
                .getApi(api)
                .getHistory(id);
    }

    private boolean historyChecker(Response<HistoryResultGroup> response) {
        if (response.isSuccessful()) {
            HistoryResultGroup result = response.body();
            assert result != null;
            if (result.getSuccess() != null)
                return result.getSuccess();
            return false;
        }
        return false;
    }
    //endregion

    //region request Get Profile
    public void requestProfile(int id, final UdonFoodManagerCallback<ProfileResultGroup> callback) {
        requestProfileCall(id).enqueue(new Callback<ProfileResultGroup>() {
            @Override
            public void onResponse(@NonNull Call<ProfileResultGroup> call, @NonNull Response<ProfileResultGroup> response) {
                if (callback != null) {
                    if (profileChecker(response)) {
                        callback.onSuccess(response.body());
                    } else {
                        callback.onFailure(new Throwable("Response invalid."));
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<ProfileResultGroup> call, @NonNull Throwable t) {
                if (!call.isCanceled()) {
                    if (callback != null) {
                        callback.onFailure(t);
                    }
                }
            }
        });
    }

    private Call<ProfileResultGroup> requestProfileCall(int id) {
        return UdonService.newInstance(BASE_URL)
                .getApi(api)
                .getProfile(id);
    }

    private boolean profileChecker(Response<ProfileResultGroup> response) {
        if (response.isSuccessful()) {
            ProfileResultGroup result = response.body();
            assert result != null;
            if (result.getSuccess() != null)
                return result.getSuccess();
            return false;
        }
        return false;
    }
    //endregion

    //region request Add Favorite
    public void requestAddFavorite(AddFavoriteBody body, final UdonFoodManagerCallback<StatusResult> callback) {
        callAddFavorite = requestAddFavorite(body);
        callAddFavorite.enqueue(new Callback<StatusResult>() {
            @Override
            public void onResponse(@NonNull Call<StatusResult> call, @NonNull Response<StatusResult> response) {
                if (callback != null) {
                    if (addFavoriteChecker(response)) {
                        callback.onSuccess(response.body());
                    } else {
                        callback.onFailure(new Throwable("Response invalid."));
                    }
                }
                callAddFavorite = null;
            }

            @Override
            public void onFailure(@NonNull Call<StatusResult> call, @NonNull Throwable t) {
                if (callback != null) {
                    callback.onFailure(t);
                }
                callAddFavorite = null;
            }
        });
    }

    private Call<StatusResult> requestAddFavorite(AddFavoriteBody body) {
        return UdonService.newInstance(BASE_URL)
                .getApi(api)
                .addFavoriteMenu(body);
    }

    private boolean addFavoriteChecker(Response<StatusResult> response) {
        if (response.isSuccessful()) {
            StatusResult result = response.body();
            assert result != null;
            if (result.getStatus() != null) {
                return result.getStatus();
            }
        }
        return false;
    }

    public void cancelAddFavorite() {
        if (callAddFavorite != null) {
            callAddFavorite.cancel();
        }
    }

    //endregion

    //region request Add User
    public void requestAddUser(RegisterBody body, final UdonFoodManagerCallback<StatusResult> callback) {
        callAddUser = requestAddUser(body);
        callAddUser.enqueue(new Callback<StatusResult>() {
            @Override
            public void onResponse(@NonNull Call<StatusResult> call, @NonNull Response<StatusResult> response) {
                if (callback != null) {
                    if (addUserChecker(response)) {
                        callback.onSuccess(response.body());
                    } else {
                        if (response.body() != null) {
                            callback.onFailure(new Throwable(response.body().getData()));
                        }
                    }
                }
                callAddUser = null;
            }

            @Override
            public void onFailure(@NonNull Call<StatusResult> call, @NonNull Throwable t) {
                if (callback != null) {
                    callback.onFailure(t);
                }
                callAddUser = null;
            }
        });
    }

    private Call<StatusResult> requestAddUser(RegisterBody body) {
        return UdonService.newInstance(BASE_URL)
                .getApi(api)
                .addUser(body);
    }

    private boolean addUserChecker(Response<StatusResult> response) {
        if (response.isSuccessful()) {
            StatusResult result = response.body();
            assert result != null;
            if (result.getStatus() != null) {
                return result.getStatus();
            }
        }
        return false;
    }

    public void cancelAddUser() {
        if (callAddUser != null) {
            callAddUser.cancel();
        }
    }
    //endregion

    //region request Login
    public void requestLogin(LoginBody body, final UdonFoodManagerCallback<LoginResultGroup> callback) {
        callLogin = requestLogin(body);
        callLogin.enqueue(new Callback<LoginResultGroup>() {
            @Override
            public void onResponse(@NonNull Call<LoginResultGroup> call, @NonNull Response<LoginResultGroup> response) {
                if (callback != null) {
                    if (loginChecker(response)) {
                        callback.onSuccess(response.body());

                    } else {
                        if (response.body() != null) {
                            callback.onFailure(new Throwable(response.body().getData().toString()));
                        }
                    }
                }
                callLogin = null;
            }

            @Override
            public void onFailure(@NonNull Call<LoginResultGroup> call, @NonNull Throwable t) {
                if (callback != null) {
                    callback.onFailure(new Throwable("มีบางอย่างผิดพลาดขณะเข้าสู่ระบบ"));//Todo: fix error message
                }
                callLogin = null;
            }
        });
    }

    private Call<LoginResultGroup> requestLogin(LoginBody body) {
        return UdonService.newInstance(BASE_URL)
                .getApi(api)
                .login(body);
    }

    private boolean loginChecker(Response<LoginResultGroup> response) {
        if (response.isSuccessful()) {
            LoginResultGroup result = response.body();
            assert result != null;
            if (result.getSuccess() != null) {
                return result.getSuccess();
            }
        }
        return false;
    }

    public void cancelLogin() {
        if (callLogin != null) {
            callLogin.cancel();
        }
    }
    //endregion

    //region request Get Delivery Promotion
    public void requestDeliveryPro(final UdonFoodManagerCallback<DeliveryProResultGroup> callback) {
        requestDeliveryProCall().enqueue(new Callback<DeliveryProResultGroup>() {
            @Override
            public void onResponse(@NonNull Call<DeliveryProResultGroup> call, @NonNull Response<DeliveryProResultGroup> response) {
                if (callback != null) {
                    if (deliveryProChecker(response)) {
                        callback.onSuccess(response.body());
                    } else {
                        callback.onFailure(new Throwable("Response invalid."));
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<DeliveryProResultGroup> call, @NonNull Throwable t) {
                if (!call.isCanceled()) {
                    if (callback != null) {
                        callback.onFailure(t);
                    }
                }
            }
        });

    }

    private Call<DeliveryProResultGroup> requestDeliveryProCall() {
        return UdonService.newInstance(BASE_URL)
                .getApi(api)
                .getDeliveryPro();
    }

    private boolean deliveryProChecker(Response<DeliveryProResultGroup> response) {
        if (response.isSuccessful()) {
            DeliveryProResultGroup result = response.body();
            assert result != null;
            if (result.getSuccess() != null)
                return result.getSuccess();
            return false;
        }
        return false;
    }
    //endregion

    //region request Del Favorite
    public void requestDelFavorite(int idFood, int idUser, final UdonFoodManagerCallback<StatusResult> callback) {
        callDelFavorite = requestDelFavorite(idFood, idUser);
        callDelFavorite.enqueue(new Callback<StatusResult>() {
            @Override
            public void onResponse(@NonNull Call<StatusResult> call, @NonNull Response<StatusResult> response) {
                if (callback != null) {
                    if (delFavoriteChecker(response)) {
                        callback.onSuccess(response.body());
                    } else {
                        if (response.body() != null) {
                            callback.onFailure(new Throwable(response.body().getData()));
                        }
                    }
                }
                callDelFavorite = null;
            }

            @Override
            public void onFailure(@NonNull Call<StatusResult> call, @NonNull Throwable t) {
                if (callback != null) {
                    callback.onFailure(t);
                }
                callDelFavorite = null;
            }
        });
    }

    private Call<StatusResult> requestDelFavorite(int idFood, int idUser) {
        return UdonService.newInstance(BASE_URL)
                .getApi(api)
                .delFavorite(idFood, idUser);
    }

    private boolean delFavoriteChecker(Response<StatusResult> response) {
        if (response.isSuccessful()) {
            StatusResult result = response.body();
            assert result != null;
            if (result.getStatus() != null) {
                return result.getStatus();
            }
        }
        return false;
    }

    public void cancelDelFavorite() {
        if (callDelFavorite != null) {
            callDelFavorite.cancel();
        }
    }
    //endregion

    //region request Get Favorite
    public void requestGetFavorite(int id, final UdonFoodManagerCallback<FavoriteResultGroup> callback) {
        requestGetFavoriteCall(id).enqueue(new Callback<FavoriteResultGroup>() {
            @Override
            public void onResponse(@NonNull Call<FavoriteResultGroup> call, @NonNull Response<FavoriteResultGroup> response) {
                if (callback != null) {
                    if (getFavoriteChecker(response)) {
                        callback.onSuccess(response.body());
                    } else {
                        callback.onFailure(new Throwable("Response invalid."));
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<FavoriteResultGroup> call, @NonNull Throwable t) {
                if (!call.isCanceled()) {
                    if (callback != null) {
                        callback.onFailure(t);
                    }
                }
            }
        });

    }

    private Call<FavoriteResultGroup> requestGetFavoriteCall(int id) {
        return UdonService.newInstance(BASE_URL)
                .getApi(api)
                .getFavorite(id);
    }

    private boolean getFavoriteChecker(Response<FavoriteResultGroup> response) {
        if (response.isSuccessful()) {
            FavoriteResultGroup result = response.body();
            assert result != null;
            if (result.getSuccess() != null)
                return result.getSuccess();
            return false;
        }
        return false;
    }
    //endregion
}
