package com.thingnoy.thingnoy500v3.api.dao;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by HBO on 16/9/2560.
 */

public class PhotoItemCollectionDao implements Parcelable {
    @SerializedName("success")
    @Expose
    private boolean success;
    @SerializedName("data")
    @Expose
    private List<PhotoItemDao> data;

    public PhotoItemCollectionDao() {
    }


    protected PhotoItemCollectionDao(Parcel in) {
        data = in.createTypedArrayList(PhotoItemDao.CREATOR);
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeTypedList(data);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<PhotoItemCollectionDao> CREATOR = new Creator<PhotoItemCollectionDao>() {
        @Override
        public PhotoItemCollectionDao createFromParcel(Parcel in) {
            return new PhotoItemCollectionDao(in);
        }

        @Override
        public PhotoItemCollectionDao[] newArray(int size) {
            return new PhotoItemCollectionDao[size];
        }
    };

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public List<PhotoItemDao> getData() {
        return data;
    }

    public void setData(List<PhotoItemDao> data) {
        this.data = data;
    }
}
