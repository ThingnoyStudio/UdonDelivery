
package com.thingnoy.thingnoy500v3.api.result.history;

import java.util.List;
import com.google.gson.annotations.SerializedName;

@SuppressWarnings("unused")
public class HistoryResultGroup {

    @SerializedName("data")
    private List<HistoryResult> mData;
    @SerializedName("success")
    private Boolean mSuccess;

    public List<HistoryResult> getData() {
        return mData;
    }

    public void setData(List<HistoryResult> data) {
        mData = data;
    }

    public Boolean getSuccess() {
        return mSuccess;
    }

    public void setSuccess(Boolean success) {
        mSuccess = success;
    }

}
