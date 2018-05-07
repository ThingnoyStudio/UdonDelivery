
package com.thingnoy.thingnoy500v3.api.result.deliverypro;

import com.google.gson.annotations.SerializedName;

@SuppressWarnings("unused")
public class DataDeliveryPro {

    @SerializedName("DeliveryProEnd")
    private String mDeliveryProEnd;
    @SerializedName("DeliveryProName")
    private String mDeliveryProName;
    @SerializedName("DeliveryProPiont")
    private String mDeliveryProPiont;
    @SerializedName("DeliveryProPrice")
    private String mDeliveryProPrice;
    @SerializedName("DeliveryProStart")
    private String mDeliveryProStart;
    @SerializedName("IDDeliveryPro")
    private String mIDDeliveryPro;

    public String getDeliveryProEnd() {
        return mDeliveryProEnd;
    }

    public void setDeliveryProEnd(String DeliveryProEnd) {
        mDeliveryProEnd = DeliveryProEnd;
    }

    public String getDeliveryProName() {
        return mDeliveryProName;
    }

    public void setDeliveryProName(String DeliveryProName) {
        mDeliveryProName = DeliveryProName;
    }

    public String getDeliveryProPiont() {
        return mDeliveryProPiont;
    }

    public void setDeliveryProPiont(String DeliveryProPiont) {
        mDeliveryProPiont = DeliveryProPiont;
    }

    public String getDeliveryProPrice() {
        return mDeliveryProPrice;
    }

    public void setDeliveryProPrice(String DeliveryProPrice) {
        mDeliveryProPrice = DeliveryProPrice;
    }

    public String getDeliveryProStart() {
        return mDeliveryProStart;
    }

    public void setDeliveryProStart(String DeliveryProStart) {
        mDeliveryProStart = DeliveryProStart;
    }

    public String getIDDeliveryPro() {
        return mIDDeliveryPro;
    }

    public void setIDDeliveryPro(String IDDeliveryPro) {
        mIDDeliveryPro = IDDeliveryPro;
    }

}
