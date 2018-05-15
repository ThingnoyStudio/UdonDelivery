package com.thingnoy.thingnoy500v3.ui.bottom.favorite.adapter.item;

import android.os.Parcel;
import android.os.Parcelable;

import com.thingnoy.thingnoy500v3.adapter.item.BaseItem;

import java.util.List;

public class FavoriteGroup implements Parcelable {
    private List<BaseItem> favorite;

    public FavoriteGroup() {
    }

    protected FavoriteGroup(Parcel in) {
        favorite = in.createTypedArrayList(BaseItem.CREATOR);
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeTypedList(favorite);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<FavoriteGroup> CREATOR = new Creator<FavoriteGroup>() {
        @Override
        public FavoriteGroup createFromParcel(Parcel in) {
            return new FavoriteGroup(in);
        }

        @Override
        public FavoriteGroup[] newArray(int size) {
            return new FavoriteGroup[size];
        }
    };

    public List<BaseItem> getFavorite() {
        return favorite;
    }

    public void setFavorite(List<BaseItem> favorite) {
        this.favorite = favorite;
    }
}
