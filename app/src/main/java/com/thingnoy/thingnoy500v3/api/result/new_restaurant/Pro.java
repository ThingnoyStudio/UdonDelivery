
package com.thingnoy.thingnoy500v3.api.result.new_restaurant;

import com.google.gson.annotations.SerializedName;

@SuppressWarnings("unused")
public class Pro {

    @SerializedName("IDResPromotion")
    private String mIDResPromotion;
    @SerializedName("ResPromotionName")
    private String mResPromotionName;

    public String getIDResPromotion() {
        return mIDResPromotion;
    }

    public void setIDResPromotion(String IDResPromotion) {
        mIDResPromotion = IDResPromotion;
    }

    public String getResPromotionName() {
        return mResPromotionName;
    }

    public void setResPromotionName(String ResPromotionName) {
        mResPromotionName = ResPromotionName;
    }

}
