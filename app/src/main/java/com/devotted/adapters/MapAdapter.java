package com.devotted.adapters;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.LayoutInflater;

import com.devotted.fragments.MapFragment;
import com.devotted.models.TempleModel;

import java.util.List;

public class MapAdapter extends FragmentPagerAdapter {
    LayoutInflater layoutInflater;
    List<TempleModel> deals;
    Context mContext;

    public MapAdapter(FragmentManager fm, List<TempleModel> deals, Context context) {
        super(fm);
        layoutInflater = LayoutInflater.from(context);
        this.deals = deals;
        this.mContext = context;
    }

    @Override
    public Fragment getItem(int position) {
        return new MapFragment(position, deals.get(position));
    }

    @Override
    public int getCount() {
        return this.deals.size();
    }

    @Override
    public float getPageWidth(int position) {
        return 0.85f;
    }
}

