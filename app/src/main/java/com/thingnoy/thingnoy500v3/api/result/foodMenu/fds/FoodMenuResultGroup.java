
package com.thingnoy.thingnoy500v3.api.result.foodMenu.fds;

import com.google.gson.annotations.SerializedName;


@SuppressWarnings("unused")
public class FoodMenuResultGroup {

    @SerializedName("data")
    private Data mData;
    @SerializedName("success")
    private Boolean mSuccess;

    public Data getData() {
        return mData;
    }

    public void setData(Data data) {
        mData = data;
    }

    public Boolean getSuccess() {
        return mSuccess;
    }

    public void setSuccess(Boolean success) {
        mSuccess = success;
    }

    @Override
    public String toString() {
        return "FoodMenuResultGroup{" +
                "mData=" + mData +
                ", mSuccess=" + mSuccess +
                '}';
    }
}
