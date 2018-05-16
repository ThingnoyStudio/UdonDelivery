package com.thingnoy.thingnoy500v3.ui.bottom.favorite.adapter;

import com.thingnoy.thingnoy500v3.adapter.item.BaseItem;
import com.thingnoy.thingnoy500v3.api.result.favorite.DataFavorite;
import com.thingnoy.thingnoy500v3.api.result.favorite.Food;
import com.thingnoy.thingnoy500v3.api.result.favorite.Res;
import com.thingnoy.thingnoy500v3.api.result.foodmenu__with_like.DetailFood;
import com.thingnoy.thingnoy500v3.ui.bottom.favorite.adapter.item.FavoriteFoodItem;
import com.thingnoy.thingnoy500v3.ui.bottom.favorite.adapter.item.RestaurantItem;
import com.thingnoy.thingnoy500v3.ui.moreinfo.foodmenu.adapter.item.DetailFoodItem;

import java.util.ArrayList;
import java.util.List;

public class FavoriteConverter {
    public static List<BaseItem> createFavoriteList(List<DataFavorite> result) {
        List<BaseItem> baseItemList = new ArrayList<>();
        baseItemList.addAll(createResAndFoodList(result));
        return baseItemList;
    }

    private static List<BaseItem> createResAndFoodList(List<DataFavorite> result) {
        List<BaseItem> favoriteList = new ArrayList<>();
        if (result != null && result.size() > 0) {
            for (DataFavorite item : result) {
                favoriteList.add(createRestaurant(item.getRes()));
                favoriteList.addAll(createFavorite(item.getFood(), item.getRes().getIDRestaurant()));
            }
        }
        return favoriteList;
    }

    private static List<FavoriteFoodItem> createFavorite(List<Food> favoritefood, String idRestaurant) {
        List<FavoriteFoodItem> favoriteFoodItems = new ArrayList<>();

        for (Food food : favoritefood) {
            FavoriteFoodItem item = new FavoriteFoodItem();
            item.setCustomerFName(food.getCustomerFName());
            item.setCustomerLName(food.getCustomerLName());
            item.setFoodImg(food.getFoodImg());
            item.setFoodName(food.getFoodName());
            item.setFoodPrice(food.getFoodPrice());

            item.setFoodTypeName(food.getFoodTypeName());
            item.setIDCustomer(food.getIDCustomer());
            item.setIDFavoriteManu(food.getIDFavoriteManu());
            item.setMenuTypeName(food.getFoodTypeName());
            item.setIDFood(food.getIDFood());

            item.setIdRestaurant(idRestaurant);

            item.setIDFoodType(food.getIDFoodType());
            if (food.getDetailfood() != null && food.getDetailfood().size() > 0) {
                item.setDetailFoods(getFoodDetail(food.getDetailfood()));
            } else {
                item.setDetailFoods(new ArrayList<DetailFoodItem>());
            }


            favoriteFoodItems.add(item);
        }
        return favoriteFoodItems;
    }

    private static RestaurantItem createRestaurant(Res res) {
        RestaurantItem restaurantItemList = new RestaurantItem();

        Res restaurant = res;
//        for (Res restaurant : res) {
        RestaurantItem item = new RestaurantItem();
        item.setIDLocation(restaurant.getIDLocation());
        item.setIDRestaurant(restaurant.getIDRestaurant());
        item.setLatlng(restaurant.getLatlng());
        item.setResAddress(restaurant.getResAddress());
        item.setResImg(restaurant.getResImg());

        item.setResLowPrice(restaurant.getResLowPrice());
        item.setResName(restaurant.getResName());
        item.setResTel(restaurant.getResTel());
        item.setResTimeEnd(restaurant.getResTimeEnd());
        item.setResTimeStart(restaurant.getResTimeStart());

        restaurantItemList = item;
//        }

        return restaurantItemList;
    }

    private static List<DetailFoodItem> getFoodDetail(List<DetailFood> detailFoods) {//<< DetailFood NaJa
        List<DetailFoodItem> detailFoodItems = new ArrayList<>();
        for (DetailFood food : detailFoods) {
            DetailFoodItem detailFoodItem = new DetailFoodItem();
            detailFoodItem.setIDFoodDetails(food.getIDFoodDetails());
            detailFoodItem.setFoodDetailName(food.getFoodDetailName());
            detailFoodItem.setFoodDetailsPrice(Double.valueOf(food.getFoodDetailsPrice()));
            detailFoodItems.add(detailFoodItem);
        }
        return detailFoodItems;
    }
}
