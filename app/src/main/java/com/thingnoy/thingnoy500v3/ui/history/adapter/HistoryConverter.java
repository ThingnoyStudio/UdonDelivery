package com.thingnoy.thingnoy500v3.ui.history.adapter;

import com.thingnoy.thingnoy500v3.adapter.item.BaseItem;
import com.thingnoy.thingnoy500v3.api.result.history.HistoryResult;
import com.thingnoy.thingnoy500v3.api.result.history.HistoryResultGroup;
import com.thingnoy.thingnoy500v3.ui.history.adapter.item.HistoryItem;
import com.thingnoy.thingnoy500v3.ui.history.adapter.item.HistoryItemGroup;

import java.util.ArrayList;
import java.util.List;

public class HistoryConverter {

    public static HistoryItemGroup createHistoryItemFromResult(
            HistoryResultGroup resultGroup){
        HistoryItemGroup itemGroup = new HistoryItemGroup();
        itemGroup.setHistoryList(createHistoryItemFromResult(resultGroup.getData()));
        return itemGroup;
    }

    private static List<BaseItem> createHistoryItemFromResult(
            List<HistoryResult> resultList) {
        List<BaseItem> items = new ArrayList<>();
        for (HistoryResult history : resultList){
            HistoryItem item = new HistoryItem()
                    .setOrder(history.getOrder())
                    .setFoodDetails(history.getFood());
            items.add(item);
        }
        return items;
    }
}
