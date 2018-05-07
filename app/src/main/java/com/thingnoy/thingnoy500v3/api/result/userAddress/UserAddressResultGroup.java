
package com.thingnoy.thingnoy500v3.api.result.userAddress;

import java.util.List;
import com.google.gson.annotations.SerializedName;

@SuppressWarnings("unused")
public class UserAddressResultGroup {

    @SerializedName("data")
    private List<DataUserAddress> mData;
    @SerializedName("success")
    private Boolean mSuccess;

    public List<DataUserAddress> getData() {
        return mData;
    }

    public void setData(List<DataUserAddress> data) {
        mData = data;
    }

    public Boolean getSuccess() {
        return mSuccess;
    }

    public void setSuccess(Boolean success) {
        mSuccess = success;
    }

}
