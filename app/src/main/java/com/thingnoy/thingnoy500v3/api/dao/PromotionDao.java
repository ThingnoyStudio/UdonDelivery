package com.thingnoy.thingnoy500v3.api.dao;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * Created by HBO on 12/2/2561.
 */

public class PromotionDao implements Parcelable {
    @SerializedName("ResPromotionEnd")      private String ResPromotionEnd;
    @SerializedName("ResPromotionStart")    private String ResPromotionStart;
    @SerializedName("ResPromotionName")     private String ResPromotionName;

    public PromotionDao() {
    }

    protected PromotionDao(Parcel in) {
        ResPromotionEnd = in.readString();
        ResPromotionStart = in.readString();
        ResPromotionName = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(ResPromotionEnd);
        dest.writeString(ResPromotionStart);
        dest.writeString(ResPromotionName);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<PromotionDao> CREATOR = new Creator<PromotionDao>() {
        @Override
        public PromotionDao createFromParcel(Parcel in) {
            return new PromotionDao(in);
        }

        @Override
        public PromotionDao[] newArray(int size) {
            return new PromotionDao[size];
        }
    };

    public String getResPromotionEnd() {
        return ResPromotionEnd;
    }

    public void setResPromotionEnd(String resPromotionEnd) {
        ResPromotionEnd = resPromotionEnd;
    }

    public String getResPromotionStart() {
        return ResPromotionStart;
    }

    public void setResPromotionStart(String resPromotionStart) {
        ResPromotionStart = resPromotionStart;
    }

    public String getResPromotionName() {
        return ResPromotionName;
    }

    public void setResPromotionName(String resPromotionName) {
        ResPromotionName = resPromotionName;
    }

    public static Creator<PromotionDao> getCREATOR() {
        return CREATOR;
    }
}
