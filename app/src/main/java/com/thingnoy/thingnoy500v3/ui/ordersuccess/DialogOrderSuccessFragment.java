package com.thingnoy.thingnoy500v3.ui.ordersuccess;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.DialogFragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.TextView;

import com.thingnoy.thingnoy500v3.R;
import com.thingnoy.thingnoy500v3.adapter.item.FoodProductItem;
import com.thingnoy.thingnoy500v3.api.request.AddNewOrderBody;
import com.thingnoy.thingnoy500v3.ui.ordersuccess.adapter.OrderSuccessAdapter;
import com.thingnoy.thingnoy500v3.util.GetPrettyPrintJson;

import java.util.List;

public class DialogOrderSuccessFragment extends DialogFragment implements DialogInterface.OnDismissListener {

    private final static String TAG = DialogOrderSuccessFragment.class.getSimpleName();

    private View root_view;
    private AddNewOrderBody dao;
    private TextView tvDate, tvTime, tvAddress, tvDeliveryTime, tvTotal, tvStatus, tvPayType, tvPay;
    private RecyclerView rcOrder;
    private List<FoodProductItem> foodproductItem;

    public DialogOrderSuccessFragment() {
        super();
    }

    @SuppressWarnings("unused")
    public static DialogOrderSuccessFragment newInstance(AddNewOrderBody dao) {//รับ dao
        DialogOrderSuccessFragment fragment = new DialogOrderSuccessFragment();
        Bundle args = new Bundle();

        Log.e(TAG,"dao: "+new GetPrettyPrintJson().getJson(dao));

        //รับ dao
        args.putParcelable("dao", dao);

        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        assert getArguments() != null;
        dao = getArguments().getParcelable("dao");

        root_view = inflater.inflate(R.layout.dialog_order_success, container, false);

        bindView(root_view);
        setupView();
        return root_view;
    }

    private void bindView(View root_view) {
        ((FloatingActionButton) root_view.findViewById(R.id.fab)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        tvDate = root_view.findViewById(R.id.tv_chk_out_date);
        tvTime = root_view.findViewById(R.id.tv_chk_out_time);
        tvAddress = root_view.findViewById(R.id.tv_chk_out_address);
        tvDeliveryTime = root_view.findViewById(R.id.tv_chk_out_delivery_time);
        rcOrder = root_view.findViewById(R.id.rc_chk_out_order);
        tvTotal = root_view.findViewById(R.id.tv_chk_out_total);
        tvStatus = root_view.findViewById(R.id.tv_chk_out_status);
        tvPayType = root_view.findViewById(R.id.tv_chk_out_pay_type);
        tvPay = root_view.findViewById(R.id.tv_chk_out_pay);

    }

    @SuppressLint("SetTextI18n")
    private void setupView() {
        tvDate.setText(dao.getmOrderDate());
        tvTime.setVisibility(View.GONE);
//        tvTime.setText(dao.getmOrderDate());// todo: time
        tvAddress.setText(dao.getAddress().getmAddressName());
        tvDeliveryTime.setText(""+dao.getmDeliveryTime());
        tvTotal.setText("" + dao.getmTotalPrice()+"฿");
        tvStatus.setText("สถานะ รอการยืนยัน");

        if (dao.getmPaymentType() == 1 ){
            tvPayType.setText("ชำระเงินปลายทาง");//todo: int
        }

        tvPay.setText("จำนวนเงินที่เตรียมจ่าย: " + dao.getmPay()+"฿");

        foodproductItem = dao.getmItems();

        OrderSuccessAdapter adapter = new OrderSuccessAdapter(foodproductItem);
        rcOrder.setHasFixedSize(true);
        rcOrder.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        rcOrder.setAdapter(adapter);
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog = super.onCreateDialog(savedInstanceState);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        return dialog;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void dismiss() {
        super.dismiss();
        onDismiss(new DialogInterface() {
            @Override
            public void cancel() {

            }

            @Override
            public void dismiss() {
//                Log.e("d", "ondismiss");
            }
        });
    }

    @Override
    public void onDismiss(DialogInterface dialog) {
//        Log.e("boy","onDismiss");
        super.onDismiss(dialog);
        final Activity activity = getActivity();
        if (activity instanceof DialogInterface.OnDismissListener) {
            ((DialogInterface.OnDismissListener) activity).onDismiss(dialog);
        }
    }
}
