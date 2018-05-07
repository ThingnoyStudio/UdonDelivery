
package com.thingnoy.thingnoy500v3.api.request.login;

import com.google.gson.annotations.SerializedName;

@SuppressWarnings("unused")
public class LoginBody {

    @SerializedName("iduserface")
    private String mIduserface;
    @SerializedName("pass")
    private String mPass;
    @SerializedName("token")
    private String mToken;
    @SerializedName("username")
    private Object mUsername;

    public String getIduserface() {
        return mIduserface;
    }

    public void setIduserface(String iduserface) {
        mIduserface = iduserface;
    }

    public String getPass() {
        return mPass;
    }

    public void setPass(String pass) {
        mPass = pass;
    }

    public String getToken() {
        return mToken;
    }

    public void setToken(String token) {
        mToken = token;
    }

    public Object getUsername() {
        return mUsername;
    }

    public void setUsername(Object username) {
        mUsername = username;
    }

}
