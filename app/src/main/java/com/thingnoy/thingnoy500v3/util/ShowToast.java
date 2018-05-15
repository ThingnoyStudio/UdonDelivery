package com.thingnoy.thingnoy500v3.util;

import android.widget.Toast;

import com.thingnoy.thingnoy500v3.manager.http.bus.Contextor;

public class ShowToast {
    public void shortToast(String text){
        Toast.makeText(Contextor.getInstance().getContext(),text,Toast.LENGTH_SHORT).show();
    }
    public void longToast(String text){
        Toast.makeText(Contextor.getInstance().getContext(),text,Toast.LENGTH_LONG).show();
    }

}
