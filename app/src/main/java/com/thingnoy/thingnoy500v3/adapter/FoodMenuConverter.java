package com.thingnoy.thingnoy500v3.adapter;

import com.thingnoy.thingnoy500v3.adapter.item.BaseItem;
import com.thingnoy.thingnoy500v3.adapter.item.DetailFoodItem;
import com.thingnoy.thingnoy500v3.adapter.item.EmptyItem;
import com.thingnoy.thingnoy500v3.adapter.item.FoodProductItem;
import com.thingnoy.thingnoy500v3.adapter.item.SectionItem;
import com.thingnoy.thingnoy500v3.api.result.foodMenu.fds.DetailFood;
import com.thingnoy.thingnoy500v3.api.result.foodMenu.fds.FoodMenuResultGroup;
import com.thingnoy.thingnoy500v3.api.result.foodMenu.fds.Normalmenu;
import com.thingnoy.thingnoy500v3.api.result.foodMenu.fds.RecommendedMenu;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by HBO on 23/2/2561.
 */

public class FoodMenuConverter {
    public static List<BaseItem> createSectionAndOrder(FoodMenuResultGroup dao,
                                                       String recommendedMenuTitle,
                                                       String normalMenuTitle) {
        List<BaseItem> baseItemList = new ArrayList<>();
        baseItemList.addAll(getRecommendedMenu(dao.getData().getRecommendedMenu(), recommendedMenuTitle));
        baseItemList.add(createEmpty());
        baseItemList.addAll(getNormalMenu(dao.getData().getNormalmenu(), normalMenuTitle));
        return baseItemList;
    }

    private static List<BaseItem> getNormalMenu(List<Normalmenu> normalmenus, String normalMenuTitle) {
        List<BaseItem> normalMenuList = new ArrayList<>();
        if (normalmenus != null && normalmenus.size() > 0) {
            normalMenuList.add(createSection(normalMenuTitle));
            for (Normalmenu nMenu : normalmenus) {
                String mFoodImg = nMenu.getMenu().getmFoodImg();
                String mFoodName = nMenu.getMenu().getmFoodName();
                double mFoodPrice = nMenu.getMenu().getmFoodPrice();
                String mFoodTypeName = nMenu.getMenu().getmFoodTypeName();
                String mIDFood = nMenu.getMenu().getmIDFood() + "";

                if (nMenu.getMenu().getmDetailFood() != null && nMenu.getMenu().getmDetailFood().size() > 0) {
                    normalMenuList.add(createOrder(mFoodImg, mFoodName, mFoodPrice, mFoodTypeName, mIDFood, getFoodDetail(nMenu.getMenu().getmDetailFood())));
                } else {
                    normalMenuList.add(createOrder(mFoodImg, mFoodName, mFoodPrice, mFoodTypeName, mIDFood, new ArrayList<DetailFoodItem>()));
                }
            }
        }
        return normalMenuList;
    }

    private static List<BaseItem> getRecommendedMenu(List<RecommendedMenu> recommendedMenus, String recommendedMenuTitle) {
        List<BaseItem> recMenuList = new ArrayList<>();
        if (recommendedMenus != null && recommendedMenus.size() > 0) {
            recMenuList.add(createSection(recommendedMenuTitle));
            for (RecommendedMenu rcMenu : recommendedMenus) {
                String mFoodImg = rcMenu.getMenu().getmFoodImg();
                String mFoodName = rcMenu.getMenu().getmFoodName();
                double mFoodPrice = rcMenu.getMenu().getmFoodPrice();
                String mFoodTypeName = rcMenu.getMenu().getmFoodTypeName();
                String mIDFood = rcMenu.getMenu().getmIDFood() + "";

                if (rcMenu.getMenu().getmDetailFood() != null && rcMenu.getMenu().getmDetailFood().size() > 0) {
                    recMenuList.add(createOrder(mFoodImg, mFoodName, mFoodPrice, mFoodTypeName, mIDFood, getFoodDetail(rcMenu.getMenu().getmDetailFood())));
                } else {
                    recMenuList.add(createOrder(mFoodImg, mFoodName, mFoodPrice, mFoodTypeName, mIDFood, new ArrayList<DetailFoodItem>()));
                }
            }
        }
        return recMenuList;
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

    public static EmptyItem createEmpty() {
        return new EmptyItem();
    }

    private static SectionItem createSection(String title) {
        SectionItem sectionItem = new SectionItem();
        sectionItem.setSectionName(title);
        return sectionItem;
    }

    private static FoodProductItem createOrder(String mFoodImg, String mFoodName, double mFoodPrice, String mFoodTypeName, String mIDFood, List<DetailFoodItem> detailFoods) {
        FoodProductItem foodProductItems = new FoodProductItem();
        foodProductItems.setmFoodImg(mFoodImg);
        foodProductItems.setmFoodName(mFoodName);
        foodProductItems.setPrice(mFoodPrice);
        foodProductItems.setmFoodTypeName(mFoodTypeName);
        foodProductItems.setmIDFood(mIDFood);

        if (detailFoods.size() > 0) {
            foodProductItems.setDetailFoods(detailFoods);
        } else {
            foodProductItems.setDetailFoods(new ArrayList<DetailFoodItem>());
        }
        return foodProductItems;
    }
}
