package com.devotted.adapters;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.devotted.R;

import java.util.ArrayList;

public class CustomPagerAdapter extends PagerAdapter {

    private Context mContext;
    private ArrayList<Integer> resArrayList;

    public CustomPagerAdapter(Context context, ArrayList<Integer> resArrayList) {
        mContext = context;
        this.resArrayList = resArrayList;
    }

    @Override
    public Object instantiateItem(ViewGroup collection, int position) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        ImageView imageView = (ImageView) inflater.inflate(R.layout.layout_image, collection, false);
        imageView.setImageResource(resArrayList.get(position));
        collection.addView(imageView);
        return imageView;
    }

    @Override
    public void destroyItem(ViewGroup collection, int position, Object view) {
        collection.removeView((View) view);
    }

    @Override
    public int getCount() {
        return resArrayList.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

}