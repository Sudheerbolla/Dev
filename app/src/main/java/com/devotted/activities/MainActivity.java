package com.devotted.activities;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;

import com.devotted.R;
import com.devotted.fragments.CommentsBottomSheetFragment;
import com.devotted.fragments.HomeFragment;
import com.devotted.fragments.MenuFragment;
import com.devotted.fragments.ShareFragment;
import com.devotted.fragments.UpdatesFragment;
import com.devotted.utils.views.CustomTextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends BaseActivity implements CommentsBottomSheetFragment.OnComments {

    private TabLayout tabLayout;
    private ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        changeStatusBarColorToOrange();
        setContentView(R.layout.activity_main);

        viewPager = findViewById(R.id.viewpager);
        setupViewPager(viewPager);
        viewPager.setOffscreenPageLimit(2);

        tabLayout = findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);

        setupTabIcons();

    }

    private void setupTabIcons() {
        CustomTextView tabOne = (CustomTextView) LayoutInflater.from(this).inflate(R.layout.layout_home_tab_icon, null);
        tabOne.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.home_selector, 0, 0);
        tabLayout.getTabAt(0).setCustomView(tabOne);

        CustomTextView tabTwo = (CustomTextView) LayoutInflater.from(this).inflate(R.layout.layout_home_tab_icon, null);
        tabTwo.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.notifications_selector, 0, 0);
        tabLayout.getTabAt(1).setCustomView(tabTwo);

        CustomTextView tabThree = (CustomTextView) LayoutInflater.from(this).inflate(R.layout.layout_home_tab_icon, null);
        tabThree.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.share_selector, 0, 0);
        tabLayout.getTabAt(2).setCustomView(tabThree);

        CustomTextView tabFive = (CustomTextView) LayoutInflater.from(this).inflate(R.layout.layout_home_tab_icon, null);
        tabFive.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.menu_selector, 0, 0);
        tabLayout.getTabAt(3).setCustomView(tabFive);

    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFrag(new HomeFragment(), getString(R.string.home));
        adapter.addFrag(new UpdatesFragment(), getString(R.string.updates));
        adapter.addFrag(new ShareFragment(), getString(R.string.share));
        adapter.addFrag(new MenuFragment(), getString(R.string.menu));
        viewPager.setAdapter(adapter);
    }

    @Override
    public void onComments() {

    }

    class ViewPagerAdapter extends FragmentStatePagerAdapter {

        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFrag(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return null;
        }

    }


}
