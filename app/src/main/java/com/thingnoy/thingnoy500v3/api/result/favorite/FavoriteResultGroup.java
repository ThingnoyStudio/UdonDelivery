
package com.thingnoy.thingnoy500v3.api.result.favorite;

import java.util.List;

import com.google.gson.annotations.SerializedName;


@SuppressWarnings("unused")
public class FavoriteResultGroup {

    @SerializedName("data")
    private List<DataFavorite> mData;
    @SerializedName("success")
    private Boolean mSuccess;

    public List<DataFavorite> getData() {
        return mData;
    }

    public void setData(List<DataFavorite> data) {
        mData = data;
    }

    public Boolean getSuccess() {
        return mSuccess;
    }

    public void setSuccess(Boolean success) {
        mSuccess = success;
    }

}
