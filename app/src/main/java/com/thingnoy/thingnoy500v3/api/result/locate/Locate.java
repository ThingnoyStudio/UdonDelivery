
package com.thingnoy.thingnoy500v3.api.result.locate;

import com.google.gson.annotations.SerializedName;

@SuppressWarnings("unused")
public class Locate {

    @SerializedName("IDLocation")
    private String mIDLocation;
    @SerializedName("IDLocationType")
    private String mIDLocationType;
    @SerializedName("letlng")
    private String mLetlng;
    @SerializedName("LocationName")
    private String mLocationName;

    public String getIDLocation() {
        return mIDLocation;
    }

    public void setIDLocation(String IDLocation) {
        mIDLocation = IDLocation;
    }

    public String getIDLocationType() {
        return mIDLocationType;
    }

    public void setIDLocationType(String IDLocationType) {
        mIDLocationType = IDLocationType;
    }

    public String getLetlng() {
        return mLetlng;
    }

    public void setLetlng(String letlng) {
        mLetlng = letlng;
    }

    public String getLocationName() {
        return mLocationName;
    }

    public void setLocationName(String LocationName) {
        mLocationName = LocationName;
    }

}
