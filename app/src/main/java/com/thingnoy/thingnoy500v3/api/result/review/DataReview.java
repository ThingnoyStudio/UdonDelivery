package com.thingnoy.thingnoy500v3.api.result.review;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * Created by HBO on 4/4/2561.
 */

public class DataReview implements Parcelable {
    @SerializedName("CustomerFName")
    private String mCustomerFName;
    @SerializedName("CustomerLName")
    private String mCustomerLName;
    @SerializedName("IDCustomer")
    private String mIDCustomer;
    @SerializedName("IDResReview")
    private String mIDResReview;
    @SerializedName("IDRestaurant")
    private String mIDRestaurant;
    @SerializedName("ResComment")
    private String mResComment;
    @SerializedName("ResReviewDate")
    private String mResReviewDate;
    @SerializedName("ResReviewImage")
    private String mResReviewImage;
    @SerializedName("ResReviewScore")
    private String mResReviewScore;

    public DataReview() {
    }


    protected DataReview(Parcel in) {
        mCustomerFName = in.readString();
        mCustomerLName = in.readString();
        mIDCustomer = in.readString();
        mIDResReview = in.readString();
        mIDRestaurant = in.readString();
        mResComment = in.readString();
        mResReviewDate = in.readString();
        mResReviewImage = in.readString();
        mResReviewScore = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mCustomerFName);
        dest.writeString(mCustomerLName);
        dest.writeString(mIDCustomer);
        dest.writeString(mIDResReview);
        dest.writeString(mIDRestaurant);
        dest.writeString(mResComment);
        dest.writeString(mResReviewDate);
        dest.writeString(mResReviewImage);
        dest.writeString(mResReviewScore);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<DataReview> CREATOR = new Creator<DataReview>() {
        @Override
        public DataReview createFromParcel(Parcel in) {
            return new DataReview(in);
        }

        @Override
        public DataReview[] newArray(int size) {
            return new DataReview[size];
        }
    };

    public String getmCustomerFName() {
        return mCustomerFName;
    }

    public void setmCustomerFName(String mCustomerFName) {
        this.mCustomerFName = mCustomerFName;
    }

    public String getmCustomerLName() {
        return mCustomerLName;
    }

    public void setmCustomerLName(String mCustomerLName) {
        this.mCustomerLName = mCustomerLName;
    }

    public String getmIDCustomer() {
        return mIDCustomer;
    }

    public void setmIDCustomer(String mIDCustomer) {
        this.mIDCustomer = mIDCustomer;
    }

    public String getmIDResReview() {
        return mIDResReview;
    }

    public void setmIDResReview(String mIDResReview) {
        this.mIDResReview = mIDResReview;
    }

    public String getmIDRestaurant() {
        return mIDRestaurant;
    }

    public void setmIDRestaurant(String mIDRestaurant) {
        this.mIDRestaurant = mIDRestaurant;
    }

    public String getmResComment() {
        return mResComment;
    }

    public void setmResComment(String mResComment) {
        this.mResComment = mResComment;
    }

    public String getmResReviewDate() {
        return mResReviewDate;
    }

    public void setmResReviewDate(String mResReviewDate) {
        this.mResReviewDate = mResReviewDate;
    }

    public String getmResReviewImage() {
        return mResReviewImage;
    }

    public void setmResReviewImage(String mResReviewImage) {
        this.mResReviewImage = mResReviewImage;
    }

    public String getmResReviewScore() {
        return mResReviewScore;
    }

    public void setmResReviewScore(String mResReviewScore) {
        this.mResReviewScore = mResReviewScore;
    }
}
