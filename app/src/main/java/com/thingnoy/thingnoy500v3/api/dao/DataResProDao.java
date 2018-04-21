package com.thingnoy.thingnoy500v3.dao;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by HBO on 12/2/2561.
 */

public class DataResProDao implements Parcelable {
    @SerializedName("RestaurantName")   private RestaurantNameDao RestaurantNameDao;
    @SerializedName("MenuFood")         private List<MenuFoodDao> MenuFoodDao;
    @SerializedName("Promotion")        private List<PromotionDao> PromotionDao;

    public DataResProDao() {
    }

    protected DataResProDao(Parcel in) {
        RestaurantNameDao = in.readParcelable(com.thingnoy.thingnoy500v3.dao.RestaurantNameDao.class.getClassLoader());
        MenuFoodDao = in.createTypedArrayList(com.thingnoy.thingnoy500v3.dao.MenuFoodDao.CREATOR);
        PromotionDao = in.createTypedArrayList(com.thingnoy.thingnoy500v3.dao.PromotionDao.CREATOR);
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(RestaurantNameDao, flags);
        dest.writeTypedList(MenuFoodDao);
        dest.writeTypedList(PromotionDao);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<DataResProDao> CREATOR = new Creator<DataResProDao>() {
        @Override
        public DataResProDao createFromParcel(Parcel in) {
            return new DataResProDao(in);
        }

        @Override
        public DataResProDao[] newArray(int size) {
            return new DataResProDao[size];
        }
    };

    public com.thingnoy.thingnoy500v3.dao.RestaurantNameDao getRestaurantNameDao() {
        return RestaurantNameDao;
    }

    public void setRestaurantNameDao(com.thingnoy.thingnoy500v3.dao.RestaurantNameDao restaurantNameDao) {
        RestaurantNameDao = restaurantNameDao;
    }

    public List<com.thingnoy.thingnoy500v3.dao.MenuFoodDao> getMenuFoodDao() {
        return MenuFoodDao;
    }

    public void setMenuFoodDao(List<com.thingnoy.thingnoy500v3.dao.MenuFoodDao> menuFoodDao) {
        MenuFoodDao = menuFoodDao;
    }

    public List<com.thingnoy.thingnoy500v3.dao.PromotionDao> getPromotionDao() {
        return PromotionDao;
    }

    public void setPromotionDao(List<com.thingnoy.thingnoy500v3.dao.PromotionDao> promotionDao) {
        PromotionDao = promotionDao;
    }
}
