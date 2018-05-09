
package com.thingnoy.thingnoy500v3.api.result.favorite;

import java.util.List;
import com.google.gson.annotations.SerializedName;

@SuppressWarnings("unused")
public class DataFavorite {

    @SerializedName("favoritefood")
    private List<Favoritefood> mFavoritefood;
    @SerializedName("res")
    private List<Res> mRes;

    public List<Favoritefood> getFavoritefood() {
        return mFavoritefood;
    }

    public void setFavoritefood(List<Favoritefood> favoritefood) {
        mFavoritefood = favoritefood;
    }

    public List<Res> getRes() {
        return mRes;
    }

    public void setRes(List<Res> res) {
        mRes = res;
    }

}
