package com.thingnoy.thingnoy500v3.ui.moreinfo.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.thingnoy.thingnoy500v3.R;
import com.thingnoy.thingnoy500v3.ui.moreinfo.foodmenu.adapter.item.DetailFoodItem;

import java.util.ArrayList;
import java.util.List;

public class SpinnerAddOnAdapter extends BaseAdapter {

    private final static String TAG = SpinnerAddOnAdapter.class.getSimpleName();
    private Context context;
    private List<DetailFoodItem> listData;
    private LayoutInflater layoutInflater;

    public SpinnerAddOnAdapter(Context context, List<DetailFoodItem> listData) {
        notifyDataSetInvalidated();
        this.context = context;
        this.listData = listData;
        if (!this.listData.get(0).getIDFoodDetails().equals("-1")) {
            DetailFoodItem foodItem = new DetailFoodItem();
            foodItem.setIDFoodDetails("-1");
            foodItem.setFoodDetailName("เพิ่มเติม");
            foodItem.setFoodDetailsPrice(0.00);
            this.listData.add(0, foodItem);
//            Log.e(TAG,new GetPrettyPrintJson().getJson(this.listData));
        }

        this.layoutInflater = LayoutInflater.from(context);
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return listData.size();
    }

    @Override
    public Object getItem(int position) {
        return listData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        RowHolder holder;
//        PromtHolder promtHolder;

//        if (position == 0) {
//            if (convertView == null) {
//                promtHolder = new PromtHolder();
//                convertView = layoutInflater.inflate(
//                        R.layout.list_spin_add_on_promt, parent, false);
//                promtHolder.addOnName = convertView.findViewById(R.id.tv_add_on_name);
//
//                promtHolder.addOnName.setText("เพิ่มเติม");
//                convertView.setTag(promtHolder);
//            } else {
//                promtHolder = (PromtHolder) convertView.getTag();
//                promtHolder.addOnName.setText("เพิ่มเติม");
//                convertView.setTag(promtHolder);
//            }
//        } else {
        if (convertView == null) {
            holder = new RowHolder();
            convertView = layoutInflater.inflate(
                    R.layout.list_spin_add_on, parent, false);
            holder.addOnName = convertView.findViewById(R.id.tv_add_on_name);
            holder.addOnPrice = convertView.findViewById(R.id.tv_add_on_price);
            holder.containerPrice = convertView.findViewById(R.id.container_price);
            holder.preText = convertView.findViewById(R.id.tv_pre_text);


//                holder.addOnName.setText(listData.get(position).getFoodDetailName());
//                holder.addOnPrice.setText(String.valueOf(listData.get(position).getFoodDetailsPrice()));

//                convertView.setTag(holder);
        } else {
            holder = (RowHolder) convertView.getTag();
//                holder.addOnName.setText(listData.get(position).getFoodDetailName());
//                holder.addOnPrice.setText(String.valueOf(listData.get(position).getFoodDetailsPrice()));
//                convertView.setTag(holder);
        }
//        }
        if (position == 0 && listData.get(position).getIDFoodDetails().equals("-1")) {
            holder.addOnName.setText(listData.get(position).getFoodDetailName());
            holder.containerPrice.setVisibility(View.GONE);
            holder.preText.setVisibility(View.GONE);
        } else {
            holder.containerPrice.setVisibility(View.VISIBLE);
            holder.preText.setVisibility(View.VISIBLE);
            holder.addOnName.setText(listData.get(position).getFoodDetailName());
            holder.addOnPrice.setText(String.valueOf(listData.get(position).getFoodDetailsPrice()));
        }

        convertView.setTag(holder);

        return convertView;
    }

    public class RowHolder {
        TextView addOnName;
        TextView addOnPrice;
        View containerPrice;
        TextView preText;
    }

    public class PromtHolder {
        TextView addOnName;
    }

    public DetailFoodItem getmItem(int position) {
        return getPrivateItems().get(position);
    }

    private List<DetailFoodItem> getPrivateItems() {
        if (listData == null) return new ArrayList<>();
        return listData;
    }
}
