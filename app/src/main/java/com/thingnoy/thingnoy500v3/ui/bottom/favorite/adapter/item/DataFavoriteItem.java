
package com.thingnoy.thingnoy500v3.ui.bottom.favorite.adapter.item;

import android.os.Parcel;
import android.os.Parcelable;

import com.thingnoy.thingnoy500v3.adapter.item.BaseItem;

import java.util.List;

import static com.thingnoy.thingnoy500v3.util.FoodProductType.TYPE_DATA_FAVORITE;

@SuppressWarnings("unused")
public class DataFavoriteItem extends BaseItem implements Parcelable{
    private List<FavoriteFoodItem> mFavoriteFood;
    private List<RestaurantItem> mRes;

    public DataFavoriteItem() {
        super(TYPE_DATA_FAVORITE);
    }

    protected DataFavoriteItem(Parcel in) {
        mFavoriteFood = in.createTypedArrayList(FavoriteFoodItem.CREATOR);
        mRes = in.createTypedArrayList(RestaurantItem.CREATOR);
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeTypedList(mFavoriteFood);
        dest.writeTypedList(mRes);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<DataFavoriteItem> CREATOR = new Creator<DataFavoriteItem>() {
        @Override
        public DataFavoriteItem createFromParcel(Parcel in) {
            return new DataFavoriteItem(in);
        }

        @Override
        public DataFavoriteItem[] newArray(int size) {
            return new DataFavoriteItem[size];
        }
    };

    public List<FavoriteFoodItem> getmFavoriteFood() {
        return mFavoriteFood;
    }

    public void setmFavoriteFood(List<FavoriteFoodItem> mFavoriteFood) {
        this.mFavoriteFood = mFavoriteFood;
    }

    public List<RestaurantItem> getmRes() {
        return mRes;
    }

    public void setmRes(List<RestaurantItem> mRes) {
        this.mRes = mRes;
    }
}
