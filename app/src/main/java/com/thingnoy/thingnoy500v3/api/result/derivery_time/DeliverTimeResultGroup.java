
package com.thingnoy.thingnoy500v3.api.result.derivery_time;

import java.util.List;
import com.google.gson.annotations.SerializedName;

@SuppressWarnings("unused")
public class DeliverTimeResultGroup {

    @SerializedName("data")
    private List<DataDeliveryTime> mData;
    @SerializedName("success")
    private Boolean mSuccess;

    public List<DataDeliveryTime> getData() {
        return mData;
    }

    public void setData(List<DataDeliveryTime> data) {
        mData = data;
    }

    public Boolean getSuccess() {
        return mSuccess;
    }

    public void setSuccess(Boolean success) {
        mSuccess = success;
    }

}
