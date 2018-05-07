
package com.thingnoy.thingnoy500v3.api.result.new_restaurant;

import java.util.List;
import com.google.gson.annotations.SerializedName;


@SuppressWarnings("unused")
public class NewRestaurantResultGroup {

    @SerializedName("data")
    private List<DataRestaurant> mData;
    @SerializedName("success")
    private Boolean mSuccess;

    public List<DataRestaurant> getData() {
        return mData;
    }

    public void setData(List<DataRestaurant> data) {
        mData = data;
    }

    public Boolean getSuccess() {
        return mSuccess;
    }

    public void setSuccess(Boolean success) {
        mSuccess = success;
    }

}
