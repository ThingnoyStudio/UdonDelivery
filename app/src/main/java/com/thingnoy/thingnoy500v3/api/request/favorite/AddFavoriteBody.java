
package com.thingnoy.thingnoy500v3.api.request.favorite;

import com.google.gson.annotations.SerializedName;

@SuppressWarnings("unused")
public class AddFavoriteBody {

    @SerializedName("idcustomer")
    private int mIdcustomer;
    @SerializedName("idfood")
    private int mIdfood;

    public int getIdcustomer() {
        return mIdcustomer;
    }

    public void setIdcustomer(int idcustomer) {
        mIdcustomer = idcustomer;
    }

    public int getIdfood() {
        return mIdfood;
    }

    public void setIdfood(int idfood) {
        mIdfood = idfood;
    }

}
