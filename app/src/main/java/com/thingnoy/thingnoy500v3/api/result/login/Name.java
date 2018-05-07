
package com.thingnoy.thingnoy500v3.api.result.login;

import com.google.gson.annotations.SerializedName;

@SuppressWarnings("unused")
public class Name {

    @SerializedName("CPasswords")
    private String mCPasswords;
    @SerializedName("CUsername")
    private String mCUsername;
    @SerializedName("CustomerFName")
    private String mCustomerFName;
    @SerializedName("CustomerImage")
    private String mCustomerImage;
    @SerializedName("CustomerLName")
    private String mCustomerLName;
    @SerializedName("CustomerPhone")
    private String mCustomerPhone;
    @SerializedName("CustomerPoint")
    private String mCustomerPoint;
    @SerializedName("email")
    private String mEmail;
    @SerializedName("IDCustomer")
    private String mIDCustomer;
    @SerializedName("iduserface")
    private Object mIduserface;
    @SerializedName("LoginType")
    private String mLoginType;
    @SerializedName("token")
    private Object mToken;

    public String getCPasswords() {
        return mCPasswords;
    }

    public void setCPasswords(String CPasswords) {
        mCPasswords = CPasswords;
    }

    public String getCUsername() {
        return mCUsername;
    }

    public void setCUsername(String CUsername) {
        mCUsername = CUsername;
    }

    public String getCustomerFName() {
        return mCustomerFName;
    }

    public void setCustomerFName(String CustomerFName) {
        mCustomerFName = CustomerFName;
    }

    public String getCustomerImage() {
        return mCustomerImage;
    }

    public void setCustomerImage(String CustomerImage) {
        mCustomerImage = CustomerImage;
    }

    public String getCustomerLName() {
        return mCustomerLName;
    }

    public void setCustomerLName(String CustomerLName) {
        mCustomerLName = CustomerLName;
    }

    public String getCustomerPhone() {
        return mCustomerPhone;
    }

    public void setCustomerPhone(String CustomerPhone) {
        mCustomerPhone = CustomerPhone;
    }

    public String getCustomerPoint() {
        return mCustomerPoint;
    }

    public void setCustomerPoint(String CustomerPoint) {
        mCustomerPoint = CustomerPoint;
    }

    public String getEmail() {
        return mEmail;
    }

    public void setEmail(String email) {
        mEmail = email;
    }

    public String getIDCustomer() {
        return mIDCustomer;
    }

    public void setIDCustomer(String IDCustomer) {
        mIDCustomer = IDCustomer;
    }

    public Object getIduserface() {
        return mIduserface;
    }

    public void setIduserface(Object iduserface) {
        mIduserface = iduserface;
    }

    public String getLoginType() {
        return mLoginType;
    }

    public void setLoginType(String LoginType) {
        mLoginType = LoginType;
    }

    public Object getToken() {
        return mToken;
    }

    public void setToken(Object token) {
        mToken = token;
    }

}
