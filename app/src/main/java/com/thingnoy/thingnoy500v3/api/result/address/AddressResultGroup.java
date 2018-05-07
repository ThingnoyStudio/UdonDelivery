
package com.thingnoy.thingnoy500v3.api.result.address;

import com.google.gson.annotations.SerializedName;


@SuppressWarnings("unused")
public class AddressResultGroup {

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

}
