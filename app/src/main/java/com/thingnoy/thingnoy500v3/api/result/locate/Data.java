
package com.thingnoy.thingnoy500v3.api.result.locate;

import java.util.List;
import com.google.gson.annotations.SerializedName;

@SuppressWarnings("unused")
public class Data {

    @SerializedName("Alley")
    private List<Locate> mAlley;
    @SerializedName("Road")
    private List<Locate> mRoad;
    @SerializedName("village")
    private List<Locate> mVillage;

    public List<Locate> getmAlley() {
        return mAlley;
    }

    public void setmAlley(List<Locate> mAlley) {
        this.mAlley = mAlley;
    }

    public List<Locate> getmRoad() {
        return mRoad;
    }

    public void setmRoad(List<Locate> mRoad) {
        this.mRoad = mRoad;
    }

    public List<Locate> getmVillage() {
        return mVillage;
    }

    public void setmVillage(List<Locate> mVillage) {
        this.mVillage = mVillage;
    }
}
