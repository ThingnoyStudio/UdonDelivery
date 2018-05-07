
package com.thingnoy.thingnoy500v3.api.result.status;

import com.google.gson.annotations.SerializedName;


@SuppressWarnings("unused")
public class StatusResult {

    @SerializedName("data")
    private String mData;
    @SerializedName("status")
    private Boolean mStatus;

    public String getData() {
        return mData;
    }

    public void setData(String data) {
        mData = data;
    }

    public Boolean getStatus() {
        return mStatus;
    }

    public void setStatus(Boolean status) {
        mStatus = status;
    }

}
