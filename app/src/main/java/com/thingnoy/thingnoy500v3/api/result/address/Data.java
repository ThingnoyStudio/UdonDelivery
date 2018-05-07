
package com.thingnoy.thingnoy500v3.api.result.address;

import java.util.List;

import com.google.gson.annotations.SerializedName;


@SuppressWarnings("unused")
public class Data {

    @SerializedName("Alley")
    private List<com.thingnoy.thingnoy500v3.api.result.address.Alley> mAlley;
    @SerializedName("Road")
    private List<com.thingnoy.thingnoy500v3.api.result.address.Road> mRoad;
    @SerializedName("village")
    private List<Village> mVillage;

    public List<com.thingnoy.thingnoy500v3.api.result.address.Alley> getAlley() {
        return mAlley;
    }

    public void setAlley(List<com.thingnoy.thingnoy500v3.api.result.address.Alley> Alley) {
        mAlley = Alley;
    }

    public List<com.thingnoy.thingnoy500v3.api.result.address.Road> getRoad() {
        return mRoad;
    }

    public void setRoad(List<com.thingnoy.thingnoy500v3.api.result.address.Road> Road) {
        mRoad = Road;
    }

    public List<Village> getVillage() {
        return mVillage;
    }

    public void setVillage(List<Village> village) {
        mVillage = village;
    }

}
