
package com.thingnoy.thingnoy500v3.api.result.deliverypro;

import java.util.List;
import com.google.gson.annotations.SerializedName;

@SuppressWarnings("unused")
public class DeliveryProResultGroup {

    @SerializedName("data")
    private List<DataDeliveryPro> mData;
    @SerializedName("success")
    private Boolean mSuccess;

    public List<DataDeliveryPro> getData() {
        return mData;
    }

    public void setData(List<DataDeliveryPro> data) {
        mData = data;
    }

    public Boolean getSuccess() {
        return mSuccess;
    }

    public void setSuccess(Boolean success) {
        mSuccess = success;
    }

}
