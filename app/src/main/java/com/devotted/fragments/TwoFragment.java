package com.devotted.fragments;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.devotted.R;
import com.devotted.adapters.ViewPagerAdapter;
import com.devotted.utils.views.CustomViewPager;


public class TwoFragment extends BaseFragment {

    private TabLayout tabLayout;
    private CustomViewPager viewPager;

    public TwoFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_two, container, false);

        viewPager = rootView.findViewById(R.id.viewpager);
        setupViewPager(viewPager);
        viewPager.setPagingEnabled(true);
        viewPager.setOffscreenPageLimit(2);

        tabLayout = rootView.findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager, true);

        return rootView;
    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getChildFragmentManager());
        adapter.addFrag(InnerFragment.newInstance("One"), "ONE");
        adapter.addFrag(InnerFragment.newInstance("Two"), "Two");
        adapter.addFrag(InnerFragment.newInstance("Three"), "THREE");
        adapter.addFrag(InnerFragment.newInstance("Four"), "Four");
        adapter.addFrag(InnerFragment.newInstance("Five"), "five");
        adapter.addFrag(InnerFragment.newInstance("Six"), "six");
        adapter.addFrag(InnerFragment.newInstance("Seven"), "seven");
        adapter.addFrag(InnerFragment.newInstance("Eight"), "eight");
        viewPager.setAdapter(adapter);
    }

}
