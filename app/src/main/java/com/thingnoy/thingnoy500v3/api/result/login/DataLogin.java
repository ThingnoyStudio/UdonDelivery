
package com.thingnoy.thingnoy500v3.api.result.login;

import java.util.List;
import com.google.gson.annotations.SerializedName;
import com.thingnoy.thingnoy500v3.api.result.profile.Address;

@SuppressWarnings("unused")
public class DataLogin {

    @SerializedName("address")
    private List<Address> mAddress;
    @SerializedName("name")
    private Name mName;

    public List<Address> getAddress() {
        return mAddress;
    }

    public void setAddress(List<Address> address) {
        mAddress = address;
    }

    public Name getName() {
        return mName;
    }

    public void setName(Name name) {
        mName = name;
    }

}
