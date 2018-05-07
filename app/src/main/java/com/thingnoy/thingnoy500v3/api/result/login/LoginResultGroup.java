
package com.thingnoy.thingnoy500v3.api.result.login;

import java.util.List;
import com.google.gson.annotations.SerializedName;

@SuppressWarnings("unused")
public class LoginResultGroup {

    @SerializedName("data")
    private List<DataLogin> mData;
    @SerializedName("success")
    private Boolean mSuccess;

    public List<DataLogin> getData() {
        return mData;
    }

    public void setData(List<DataLogin> data) {
        mData = data;
    }

    public Boolean getSuccess() {
        return mSuccess;
    }

    public void setSuccess(Boolean success) {
        mSuccess = success;
    }

}
