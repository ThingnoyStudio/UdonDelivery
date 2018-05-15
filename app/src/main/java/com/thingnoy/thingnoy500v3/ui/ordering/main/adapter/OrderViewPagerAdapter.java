package com.thingnoy.thingnoy500v3.ui.ordering.main.adapter;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.thingnoy.thingnoy500v3.ui.ordering.address.AddressFragment;
import com.thingnoy.thingnoy500v3.ui.ordering.deliverytime.DeliveryTimeFragment;
import com.thingnoy.thingnoy500v3.ui.ordering.payment.PaymentFragment;

public class OrderViewPagerAdapter extends FragmentPagerAdapter {
    public OrderViewPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0: return AddressFragment.newInstance();
            case 1: return DeliveryTimeFragment.newInstance();
            case 2: return PaymentFragment.newInstance(0.00);
            default: return null;
        }
    }

    @Override
    public int getCount() {
        return 3;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        CharSequence[] pageTitle = {"เลือกที่อยู่","เลือกเวลาจัดส่ง","ช่องทางการชำระเงิน"};
        return pageTitle[position];
    }
}
