
package com.thingnoy.thingnoy500v3.api.request.register;

import com.google.gson.annotations.SerializedName;

public class RegisterBody {
    public RegisterBody() {
    }
    @SerializedName("fname")
    private String mFname;
    @SerializedName("iduserface")
    private String mIduserface;
    @SerializedName("img")
    private String mImg;
    @SerializedName("lname")
    private String mLname;
    @SerializedName("password")
    private String mPassword;
    @SerializedName("phone")
    private String mPhone;
    @SerializedName("token")
    private String mToken;
    @SerializedName("username")
    private String mUsername;

    public String getFname() {
        return mFname;
    }

    public void setFname(String fname) {
        mFname = fname;
    }

    public String getIduserface() {
        return mIduserface;
    }

    public void setIduserface(String iduserface) {
        mIduserface = iduserface;
    }

    public String getImg() {
        return mImg;
    }

    public void setImg(String img) {
        mImg = img;
    }

    public String getLname() {
        return mLname;
    }

    public void setLname(String lname) {
        mLname = lname;
    }

    public String getPassword() {
        return mPassword;
    }

    public void setPassword(String password) {
        mPassword = password;
    }

    public String getPhone() {
        return mPhone;
    }

    public void setPhone(String phone) {
        mPhone = phone;
    }

    public String getToken() {
        return mToken;
    }

    public void setToken(String token) {
        mToken = token;
    }

    public String getUsername() {
        return mUsername;
    }

    public void setUsername(String username) {
        mUsername = username;
    }
}
