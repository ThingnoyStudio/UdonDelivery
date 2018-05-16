
package com.thingnoy.thingnoy500v3.api.request.orderstate;

import com.google.gson.annotations.SerializedName;

@SuppressWarnings("unused")
public class OrderStateBody {

    @SerializedName("idorder")
    private String mIdorder;
    @SerializedName("status")
    private String mStatus;

    public String getIdorder() {
        return mIdorder;
    }

    public void setIdorder(String idorder) {
        mIdorder = idorder;
    }

    public String getStatus() {
        return mStatus;
    }

    public void setStatus(String status) {
        mStatus = status;
    }

}
