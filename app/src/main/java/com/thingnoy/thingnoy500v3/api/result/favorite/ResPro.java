
package com.thingnoy.thingnoy500v3.api.result.favorite;


import com.google.gson.annotations.SerializedName;

@SuppressWarnings("unused")
public class ResPro {
    @SerializedName("IDResPromotion")
    private String mIDResPromotion;
    @SerializedName("ResPromotionName")
    private String mResPromotionName;

    public String getmIDResPromotion() {
        return mIDResPromotion;
    }

    public void setmIDResPromotion(String mIDResPromotion) {
        this.mIDResPromotion = mIDResPromotion;
    }

    public String getmResPromotionName() {
        return mResPromotionName;
    }

    public void setmResPromotionName(String mResPromotionName) {
        this.mResPromotionName = mResPromotionName;
    }
}
