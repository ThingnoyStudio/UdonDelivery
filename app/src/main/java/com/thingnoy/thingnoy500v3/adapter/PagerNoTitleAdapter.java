package com.thingnoy.thingnoy500v3.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by HBO on 20/2/2561.
 */

public class PagerNoTitleAdapter extends FragmentPagerAdapter {
    private List<Fragment> fragments = new ArrayList<>();
//    private List<String> pageTittle = new ArrayList<>();
    private int myNumberOfPage;

    public PagerNoTitleAdapter(FragmentManager fm, int numberOfPage) {
        super(fm);
        this.myNumberOfPage = numberOfPage;
    }

    public void addFragmentPage(Fragment fragment){
        fragments.add(fragment);
//        pageTittle.add(title);
    }

    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

//    @Override
//    public CharSequence getPageTitle(int position) {
//        return pageTittle.get(position);
//    }

    @Override
    public int getCount() {
        return myNumberOfPage;
    }
}
