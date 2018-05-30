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

    private LayoutInflater layoutInflater;
    private List<TempleModel> templeModelList;
    private Context mContext;

    public MapAdapter(FragmentManager fm, List<TempleModel> templeModelList, Context context) {
        super(fm);
        layoutInflater = LayoutInflater.from(context);
        this.templeModelList = templeModelList;
        this.mContext = context;
    }

    @Override
    public Fragment getItem(int position) {
        return new MapFragment(position, templeModelList.get(position));
    }

    @Override
    public int getCount() {
        return this.templeModelList.size();
    }

    @Override
    public float getPageWidth(int position) {
        return 0.85f;
    }
}

