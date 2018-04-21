package com.thingnoy.thingnoy500v3.dao;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by HBO on 12/2/2561.
 */

public class ResProCollectionDao implements Parcelable {
    @SerializedName("success")   private boolean success;
    @SerializedName("data")         private List<DataResProDao> data;

    public ResProCollectionDao() {
    }

    protected ResProCollectionDao(Parcel in) {
        success = in.readByte() != 0;
        data = in.createTypedArrayList(DataResProDao.CREATOR);
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeByte((byte) (success ? 1 : 0));
        dest.writeTypedList(data);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<ResProCollectionDao> CREATOR = new Creator<ResProCollectionDao>() {
        @Override
        public ResProCollectionDao createFromParcel(Parcel in) {
            return new ResProCollectionDao(in);
        }

        @Override
        public ResProCollectionDao[] newArray(int size) {
            return new ResProCollectionDao[size];
        }
    };

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public List<DataResProDao> getData() {
        return data;
    }

    public void setData(List<DataResProDao> data) {
        this.data = data;
    }
}
