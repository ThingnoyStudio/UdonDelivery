
package com.thingnoy.thingnoy500v3.api.result.profile;

import java.util.List;
import com.google.gson.annotations.SerializedName;

@SuppressWarnings("unused")
public class Datum {

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
