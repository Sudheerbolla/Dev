package com.devotted.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.devotted.R;
import com.devotted.activities.MainActivity;
import com.devotted.activities.SearchActivity;
import com.devotted.adapters.ViewPagerAdapter;
import com.devotted.utils.views.CustomTextView;
import com.devotted.utils.views.CustomViewPager;


public class HomeFragment extends BaseFragment implements View.OnClickListener {

    private TabLayout tabLayout;
    private CustomViewPager viewPager;
    private CustomTextView txtSearch;
    private View rootView;
    private MainActivity mainActivity;

    public HomeFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mainActivity = (MainActivity) getActivity();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_home, container, false);
        initComponents();
        return rootView;
    }

    private void initComponents() {
        txtSearch = rootView.findViewById(R.id.txtSearch);
        viewPager = rootView.findViewById(R.id.viewpager);
        setupViewPager(viewPager);
        viewPager.setPagingEnabled(true);
        viewPager.setOffscreenPageLimit(2);

        tabLayout = rootView.findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager, true);

        txtSearch.setOnClickListener(this);

    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getChildFragmentManager());
        adapter.addFrag(CardsFragment.newInstance("Panchang"), "Panchang");
        adapter.addFrag(CardsFragment.newInstance("Mantras"), "Mantras");
        adapter.addFrag(CardsFragment.newInstance("Dharma"), "Dharma");
        adapter.addFrag(CardsFragment.newInstance("Questions"), "Questions");
        adapter.addFrag(InnerFragment.newInstance("Five"), "five");
        adapter.addFrag(InnerFragment.newInstance("Six"), "six");
        adapter.addFrag(InnerFragment.newInstance("Seven"), "seven");
        adapter.addFrag(InnerFragment.newInstance("Eight"), "eight");
        viewPager.setAdapter(adapter);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.txtSearch:
                openSearchScreen();
                break;
            default:
                break;
        }
    }

    private void openSearchScreen() {
        startActivity(new Intent(mainActivity, SearchActivity.class));
    }

}
