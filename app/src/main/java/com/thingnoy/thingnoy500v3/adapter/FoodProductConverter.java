package com.thingnoy.thingnoy500v3.adapter;

import com.thingnoy.thingnoy500v3.adapter.dao.FoodProductCollectionDao;
import com.thingnoy.thingnoy500v3.adapter.dao.Normalmenu;
import com.thingnoy.thingnoy500v3.adapter.dao.RecommendedMenu;
import com.thingnoy.thingnoy500v3.adapter.item.BaseOrderFoodItem;
import com.thingnoy.thingnoy500v3.adapter.item.EmptyItem;
import com.thingnoy.thingnoy500v3.adapter.item.OrderItem;
import com.thingnoy.thingnoy500v3.adapter.item.SectionItem;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by HBO on 23/2/2561.
 */

public class FoodProductConverter {
    public static List<BaseOrderFoodItem> createSectionandOrder(FoodProductCollectionDao dao,
                                                                String recommendedMenuTitle,
                                                                String normalMenuTitle,
                                                                String currency) {
        List<BaseOrderFoodItem> baseOrderFoodItemList = new ArrayList<>();
        baseOrderFoodItemList.addAll(getRecommendedMenu(dao.getmData().getmRecommendedMenu(), recommendedMenuTitle, currency));
        baseOrderFoodItemList.add(createEmpty());
        baseOrderFoodItemList.addAll(getNormalMenu(dao.getmData().getmNormalmenu(), normalMenuTitle, currency));
        return baseOrderFoodItemList;
    }

    private static List<BaseOrderFoodItem> getNormalMenu(List<Normalmenu> normalmenus, String normalMenuTitle, String currency) {
        List<BaseOrderFoodItem> normalMenuList = new ArrayList<>();
        if (normalmenus != null && normalmenus.size() > 0) {
            normalMenuList.add(createSection(normalMenuTitle));
            for (Normalmenu nMenu :
                    normalmenus) {
                String mFoodImg = nMenu.getmFoodImg();
                String mFoodName = nMenu.getmFoodName();
                String mFoodPrice = nMenu.getmFoodPrice() + currency;
                String mFoodTypeName = nMenu.getmFoodTypeName();
                String mIDFood = nMenu.getmIDFood() + "";
                normalMenuList.add(createOrder(mFoodImg, mFoodName, mFoodPrice, mFoodTypeName, mIDFood));
            }
        }
        return normalMenuList;
    }

    private static List<BaseOrderFoodItem> getRecommendedMenu(List<RecommendedMenu> recommendedMenus, String recommendedMenuTitle, String currency) {
        List<BaseOrderFoodItem> recMenuList = new ArrayList<>();
        if (recommendedMenus != null && recommendedMenus.size() > 0) {
            recMenuList.add(createSection(recommendedMenuTitle));
            for (RecommendedMenu rcMenu :
                    recommendedMenus) {
                String mFoodImg = rcMenu.getmFoodImg();
                String mFoodName = rcMenu.getmFoodName();
                String mFoodPrice = rcMenu.getmFoodPrice() + currency;
                String mFoodTypeName = rcMenu.getmFoodTypeName();
                String mIDFood = rcMenu.getmIDFood() + "";
                recMenuList.add(createOrder(mFoodImg, mFoodName, mFoodPrice, mFoodTypeName, mIDFood));
            }
        }
        return recMenuList;
    }

    public static EmptyItem createEmpty() {
        return new EmptyItem();
    }

    private static SectionItem createSection(String title) {
        SectionItem sectionItem = new SectionItem();
        sectionItem.setSectionName(title);
        return sectionItem;
    }

    private static OrderItem createOrder(String mFoodImg, String mFoodName, String mFoodPrice, String mFoodTypeName, String mIDFood) {
        OrderItem orderItems = new OrderItem();
        orderItems.setmFoodImg(mFoodImg);
        orderItems.setmFoodName(mFoodName);
        orderItems.setmFoodPrice(mFoodPrice);
        orderItems.setmFoodTypeName(mFoodTypeName);
        orderItems.setmIDFood(mIDFood);
        return orderItems;
    }
}
