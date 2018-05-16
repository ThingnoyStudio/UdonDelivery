package com.thingnoy.thingnoy500v3.ui.moreinfo.foodmenu.adapter;

import com.thingnoy.thingnoy500v3.adapter.item.BaseItem;

import com.thingnoy.thingnoy500v3.api.result.foodmenu__with_like.DetailFood;
import com.thingnoy.thingnoy500v3.api.result.foodmenu__with_like.NewFoodMenuResultGroup;
import com.thingnoy.thingnoy500v3.api.result.foodmenu__with_like.Normalmenu;
import com.thingnoy.thingnoy500v3.api.result.foodmenu__with_like.RecommendedMenu;
import com.thingnoy.thingnoy500v3.ui.moreinfo.foodmenu.adapter.item.DetailFoodItem;
import com.thingnoy.thingnoy500v3.ui.moreinfo.foodmenu.adapter.item.EmptyItem;
import com.thingnoy.thingnoy500v3.ui.moreinfo.foodmenu.adapter.item.FoodProductItem;
import com.thingnoy.thingnoy500v3.ui.moreinfo.foodmenu.adapter.item.SectionItem;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by HBO on 23/2/2561.
 */

public class NewFoodMenuConverter {
    public static List<BaseItem> createSectionAndOrder(NewFoodMenuResultGroup dao,
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
                String mFoodImg = nMenu.getMenu().getFoodImg();
                String mFoodName = nMenu.getMenu().getFoodName();
                double mFoodPrice = Double.parseDouble(nMenu.getMenu().getFoodPrice());
                String mFoodTypeName = nMenu.getMenu().getFoodTypeName();
                String mIDFood = nMenu.getMenu().getIDFood() + "";
                boolean mLikeState = nMenu.getMenu().getStatusFoodFavorite();

                if (nMenu.getMenu().getDetailFood() != null && nMenu.getMenu().getDetailFood().size() > 0) {
                    normalMenuList.add(createOrder(mFoodImg, mFoodName, mFoodPrice, mFoodTypeName, mIDFood, getFoodDetail(nMenu.getMenu().getDetailFood()), mLikeState));
                } else {
                    normalMenuList.add(createOrder(mFoodImg, mFoodName, mFoodPrice, mFoodTypeName, mIDFood, new ArrayList<DetailFoodItem>(), mLikeState));
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
                String mFoodImg = rcMenu.getMenu().getFoodImg();
                String mFoodName = rcMenu.getMenu().getFoodName();
                double mFoodPrice = Double.parseDouble(rcMenu.getMenu().getFoodPrice());
                String mFoodTypeName = rcMenu.getMenu().getFoodTypeName();
                String mIDFood = rcMenu.getMenu().getIDFood() + "";
                boolean mLikeState = rcMenu.getMenu().getStatusFoodFavorite();


                if (rcMenu.getMenu().getDetailFood() != null && rcMenu.getMenu().getDetailFood().size() > 0) {
                    recMenuList.add(createOrder(mFoodImg, mFoodName, mFoodPrice, mFoodTypeName, mIDFood, getFoodDetail(rcMenu.getMenu().getDetailFood()), mLikeState));
                } else {
                    recMenuList.add(createOrder(mFoodImg, mFoodName, mFoodPrice, mFoodTypeName, mIDFood, new ArrayList<DetailFoodItem>(), mLikeState));
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

    private static FoodProductItem createOrder(String mFoodImg, String mFoodName, double mFoodPrice, String mFoodTypeName, String mIDFood, List<DetailFoodItem> detailFoods, boolean likeState) {
        FoodProductItem foodProductItems = new FoodProductItem();
        foodProductItems.setmFoodImg(mFoodImg);
        foodProductItems.setmFoodName(mFoodName);
        foodProductItems.setPrice(mFoodPrice);
        foodProductItems.setmFoodTypeName(mFoodTypeName);
        foodProductItems.setmIDFood(mIDFood);
        foodProductItems.setLike(likeState);

        if (detailFoods.size() > 0) {
            foodProductItems.setDetailFoods(detailFoods);
        } else {
            foodProductItems.setDetailFoods(new ArrayList<DetailFoodItem>());
        }
        return foodProductItems;
    }
}
