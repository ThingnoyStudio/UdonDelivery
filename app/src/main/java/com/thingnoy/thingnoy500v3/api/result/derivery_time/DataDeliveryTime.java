
package com.thingnoy.thingnoy500v3.api.result.derivery_time;

import com.google.gson.annotations.SerializedName;

@SuppressWarnings("unused")
public class DataDeliveryTime {

    @SerializedName("DeliveryTime")
    private String mDeliveryTime;
    @SerializedName("IDDeliveryTime")
    private int mIDDeliveryTime;

    public String getDeliveryTime() {
        return mDeliveryTime;
    }

    public void setDeliveryTime(String DeliveryTime) {
        mDeliveryTime = DeliveryTime;
    }

    public int getIDDeliveryTime() {
        return mIDDeliveryTime;
    }

    public void setIDDeliveryTime(int IDDeliveryTime) {
        mIDDeliveryTime = IDDeliveryTime;
    }

}
