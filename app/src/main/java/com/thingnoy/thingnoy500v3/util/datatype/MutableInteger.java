package com.thingnoy.thingnoy500v3.util.datatype;

import android.os.Bundle;

/**
 * Created by HBO on 17/4/2560.
 */

public class MutableInteger {

    private int value;

    public MutableInteger(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public Bundle onSaveInstanceState(){
        Bundle bundle = new Bundle();
        bundle.putInt("value",value);
        return bundle;
    }

    public void onRestoreInstanceState(Bundle savedInstanceState){
        value = savedInstanceState.getInt("value");
    }
}
