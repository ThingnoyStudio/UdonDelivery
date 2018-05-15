package com.thingnoy.thingnoy500v3.ui.moreinfo.adapter.holder;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.thingnoy.thingnoy500v3.R;
import com.thingnoy.thingnoy500v3.ui.moreinfo.adapter.CartAdapter.TextChangeListener;
import com.thingnoy.thingnoy500v3.ui.moreinfo.adapter.SpinnerAddOnAdapter;
import com.thingnoy.thingnoy500v3.ui.moreinfo.foodmenu.adapter.item.DetailFoodItem;
import com.thingnoy.thingnoy500v3.ui.moreinfo.foodmenu.adapter.item.FoodProductItem;
import com.thingnoy.thingnoy500v3.manager.http.bus.Contextor;
import com.thingnoy.thingnoy500v3.util.GetPrettyPrintJson;
import com.thingnoy.thingnoy500v3.util.StringUtils;
import com.thingnoy.thingnoy500v3.util.ViewAnimation;

public class CartHolder extends RecyclerView.ViewHolder {

    //region Variable
    private final static String TAG = CartHolder.class.getSimpleName();
    private EditText edtReason;
    private Context context;
    private Spinner spnAddOn;
    private ImageButton btnDescription;
    private View lytExpandDescription;
    private TextView tvName;
    private TextView tvPrice;
    private TextView tvAmount;
    private ImageView btnDecrease;
    private ImageView btnIncrease;
    private ImageView btnDelete;
    SpinnerAddOnAdapter addOnAdapter;
    private OnClickCartHolderListener listener;
    public TextChangeListener textChangeListener;
    //endregion

    public CartHolder(View itemView,
                      Context context,
                      TextChangeListener textChangeListener) {
        super(itemView);
        this.context = context;
        this.textChangeListener = textChangeListener;
        tvName = itemView.findViewById(R.id.tv_food_name);
        tvPrice = itemView.findViewById(R.id.tv_food_price);
        tvAmount = itemView.findViewById(R.id.tv_beer_amount);
        btnDecrease = itemView.findViewById(R.id.btn_decrease);
        btnIncrease = itemView.findViewById(R.id.btn_increase);
        btnDelete = itemView.findViewById(R.id.btn_delete_item);

        spnAddOn = itemView.findViewById(R.id.spn_add_on);
        edtReason = itemView.findViewById(R.id.edt_reason);

        btnDescription = itemView.findViewById(R.id.bt_toggle_description);
        lytExpandDescription = itemView.findViewById(R.id.lyt_expand_description);

        btnDecrease.setOnClickListener(onClickDecrease());
        btnIncrease.setOnClickListener(onClickIncrease());
        btnDelete.setOnClickListener(onClickDelete());
        btnDescription.setOnClickListener(onClickDescription());

        edtReason.addTextChangedListener(textChangeListener);
        spnAddOn.setOnItemSelectedListener(onSpinnerSelected());
    }

    public void setOnClickCartHolderListener(
            OnClickCartHolderListener listener) {
        this.listener = listener;
    }

    @SuppressLint("SetTextI18n")
    public void onBind(FoodProductItem item, int position) {
        tvName.setText(item.getmFoodName());
        tvPrice.setText(StringUtils.getCommaPriceWithBaht(Contextor.getInstance().getContext(), item.getPrice()));
        tvAmount.setText("" + item.getAmount());
        edtReason.setText(item.getReason());


        if (item.getDetailFoods() != null && item.getDetailFoods().size() > 0) {
            Log.e(TAG, "new AddOnAdapter (size = " + item.getDetailFoods().size() + ") : " + new GetPrettyPrintJson().getJson(item.getDetailFoods()));
            addOnAdapter = new SpinnerAddOnAdapter(context, item.getDetailFoods());
            spnAddOn.setAdapter(addOnAdapter);
            int ii = 0;
            if (item.getAddOn() != null) {
                ii = item.getAddOn().getSelectedIndex();
            }
            spnAddOn.setVisibility(View.VISIBLE);
            spnAddOn.setSelection(ii);
        } else {
            spnAddOn.setVisibility(View.GONE);
        }

    }

    public interface OnClickCartHolderListener {
        void onClickIncrease(CartHolder view, int position);

        void onClickDecrease(CartHolder view, int position);

        void onClickDelete(CartHolder view, int position);

        void onSelectedAddOn(CartHolder view, int position, DetailFoodItem detailFoodItem);

//        void onTextChangedReason(CartHolder view, int position, String reason);
    }

    private void toggleSection(View bt, final View lyt) {
        boolean show = toggleArrow(bt);
        if (show) {
            ViewAnimation.expand(lyt, new ViewAnimation.AnimListener() {
                @Override
                public void onFinish() {
//                    Tools.nestedScrollTo(nested_scroll_view, lyt);
                }
            });
        } else {
            ViewAnimation.collapse(lyt);
        }
    }

    private boolean toggleArrow(View view) {
        if (view.getRotation() == 0) {
            view.animate().setDuration(200).rotation(180);
            return true;
        } else {
            view.animate().setDuration(200).rotation(0);
            return false;
        }
    }

//    public CartHolder(ViewGroup viewGroup ){
//        super( viewGroup, R.layout.holder_cart );
//    }

//    private void setupView() {
//
//    }

    /*
     *Event Handle
     */
    @NonNull
    private View.OnClickListener onClickDecrease() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.onClickDecrease(CartHolder.this, getAdapterPosition());
                }
            }
        };
    }

    @NonNull
    private View.OnClickListener onClickIncrease() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.onClickIncrease(CartHolder.this, getAdapterPosition());
                }
            }
        };
    }

    @NonNull
    private View.OnClickListener onClickDelete() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.onClickDelete(CartHolder.this, getAdapterPosition());
                }
            }
        };
    }

    private View.OnClickListener onClickDescription() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toggleSection(v, lytExpandDescription);
            }
        };
    }

    private AdapterView.OnItemSelectedListener onSpinnerSelected() {
        return new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (listener != null) {
//                    if (position != 0){
                    if (addOnAdapter.getmItem(position) == null) {
                        listener.onSelectedAddOn(CartHolder.this, position, new DetailFoodItem());
                    } else {
                        DetailFoodItem detailFoodItem = new DetailFoodItem();
                        detailFoodItem.setFoodDetailName(addOnAdapter.getmItem(position).getFoodDetailName());
                        detailFoodItem.setFoodDetailsPrice(addOnAdapter.getmItem(position).getFoodDetailsPrice());
                        detailFoodItem.setIDFoodDetails(addOnAdapter.getmItem(position).getIDFoodDetails());
                        detailFoodItem.setSelectedIndex(position);
                        listener.onSelectedAddOn(CartHolder.this, position, detailFoodItem);
                    }

                    Log.e(TAG, "AddOn selected");
//                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        };
    }


}
