package com.thingnoy.thingnoy500v3.ui.bottom.history.adapter.item;

import android.os.Parcel;
import android.os.Parcelable;

import com.thingnoy.thingnoy500v3.adapter.item.BaseItem;

import java.util.ArrayList;
import java.util.List;

public class HistoryItemGroup implements Parcelable{
    private List<HistoryItem> historyList;

    public HistoryItemGroup() {
    }

    protected HistoryItemGroup(Parcel in) {
        historyList = in.createTypedArrayList(HistoryItem.CREATOR);
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeTypedList(historyList);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<HistoryItemGroup> CREATOR = new Creator<HistoryItemGroup>() {
        @Override
        public HistoryItemGroup createFromParcel(Parcel in) {
            return new HistoryItemGroup(in);
        }

        @Override
        public HistoryItemGroup[] newArray(int size) {
            return new HistoryItemGroup[size];
        }
    };

    public List<HistoryItem> getHistoryList() {
        return historyList;
    }

    public List<BaseItem> getBaseItems(){
        List<BaseItem> baseItems = new ArrayList<>(  );
        for( HistoryItem history : historyList){
            baseItems.add(history);
        }
        return baseItems;
    }

    public void setHistoryList(List<BaseItem> historyList) {
        List<HistoryItem> items = new ArrayList<>();
        for (BaseItem history : historyList){
            if (history instanceof HistoryItem){
                items.add( (HistoryItem) history);
            }
        }
        this.historyList = items;
    }
}
