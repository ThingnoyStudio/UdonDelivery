package com.thingnoy.thingnoy500v3.api.dao;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by HBO on 5/3/2561.
 */

public class NameAndImageDao implements Parcelable  {
    private int resId;
    private String resName;
    private String resImage;
    private boolean deliveryFee;

    public NameAndImageDao() {
    }

    protected NameAndImageDao(Parcel in) {
        resId = in.readInt();
        resName = in.readString();
        resImage = in.readString();
        deliveryFee = in.readByte() != 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(resId);
        dest.writeString(resName);
        dest.writeString(resImage);
        dest.writeByte((byte) (deliveryFee ? 1 : 0));
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<NameAndImageDao> CREATOR = new Creator<NameAndImageDao>() {
        @Override
        public NameAndImageDao createFromParcel(Parcel in) {
            return new NameAndImageDao(in);
        }

        @Override
        public NameAndImageDao[] newArray(int size) {
            return new NameAndImageDao[size];
        }
    };

    public int getResId() {
        return resId;
    }

    public void setResId(int resId) {
        this.resId = resId;
    }

    public String getResName() {
        return resName;
    }

    public void setResName(String resName) {
        this.resName = resName;
    }

    public String getResImage() {
        return resImage;
    }

    public void setResImage(String resImage) {
        this.resImage = resImage;
    }

    public boolean isDeliveryFee() {
        return deliveryFee;
    }

    public void setDeliveryFee(boolean deliveryFee) {
        this.deliveryFee = deliveryFee;
    }

    @Override
    public String toString() {
        return "NameAndImageDao{" +
                "resId=" + resId +
                ", resName='" + resName + '\'' +
                ", resImage='" + resImage + '\'' +
                ", deliveryFee=" + deliveryFee +
                '}';
    }
}
