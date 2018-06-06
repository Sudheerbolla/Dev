package com.devotted.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.devotted.R;
import com.devotted.activities.MainActivity;
import com.devotted.activities.SearchActivity;
import com.devotted.activities.TempleGroupsActivity;
import com.devotted.adapters.ViewPagerAdapter;
import com.devotted.utils.LocalStorage;
import com.devotted.utils.views.CustomTextView;
import com.devotted.utils.views.CustomViewPager;


public class HomeFragment extends BaseFragment implements View.OnClickListener {

    private TabLayout tabLayout;
    private CustomViewPager viewPager;
    private CustomTextView txtSearch, txtGroups;
    private RelativeLayout relTemple;
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
        txtGroups = rootView.findViewById(R.id.txtGroups);
        viewPager = rootView.findViewById(R.id.viewpager);
        relTemple = rootView.findViewById(R.id.relTemple);

        setupViewPager(viewPager);
        viewPager.setPagingEnabled(true);
        viewPager.setOffscreenPageLimit(2);

        tabLayout = rootView.findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager, true);

        txtSearch.setOnClickListener(this);
        txtGroups.setOnClickListener(this);
        if (LocalStorage.getInstance(mainActivity).getBoolean(LocalStorage.IS_GUEST_USER, false)) {
            relTemple.setVisibility(View.GONE);
        } else {
            relTemple.setVisibility(View.VISIBLE);
        }
    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getChildFragmentManager());
//        adapter.addFrag(CardsFragment.newInstance("Mantras"), "Mantras");
//        adapter.addFrag(CardsFragment.newInstance("Dharma"), "Dharma");
//        adapter.addFrag(CardsFragment.newInstance("Questions"), "Questions");
        adapter.addFrag(CardsRossDeckFragment.newInstance(getString(R.string.mantras)), getString(R.string.mantras));
        adapter.addFrag(CardsRossDeckFragment.newInstance(getString(R.string.dharma)), getString(R.string.dharma));
        adapter.addFrag(CardsRossDeckFragment.newInstance(getString(R.string.questions)), getString(R.string.questions));
//        adapter.addFrag(CardsRossDeckFragment.newInstance("Five"), "five");
        viewPager.setAdapter(adapter);

//        ViewCompat.setNestedScrollingEnabled(viewPager, true);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.txtSearch:
                openSearchScreen();
                break;
            case R.id.txtGroups:
                openGroupsScreen();
                break;
            default:
                break;
        }
    }

    private void openGroupsScreen() {
        startActivity(new Intent(mainActivity, TempleGroupsActivity.class));
    }

    private void openSearchScreen() {
        startActivity(new Intent(mainActivity, SearchActivity.class));
    }

}
