package com.thingnoy.thingnoy500v3.adapter.item;

import com.thingnoy.thingnoy500v3.util.FoodProductType;

/**
 * Created by HBO on 23/2/2561.
 */

public class SectionItem extends BaseOrderFoodItem {

    private String sectionName;

    public SectionItem() {
        super(FoodProductType.TYPE_SECTION);
    }

    public String getSectionName() {
        return sectionName;
    }

    public void setSectionName(String sectionName) {
        this.sectionName = sectionName;
    }
}
