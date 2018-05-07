package com.thingnoy.thingnoy500v3.adapter;


import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import java.util.ArrayList;

public class StringSpinnerAdapter extends ArrayAdapter<String>{
    private ArrayList<String> list;

    public StringSpinnerAdapter(@NonNull Context context, int resource) {
        super(context, resource);
    }

    public void setList(ArrayList<String> list) {
        this.list = list;
    }

}
