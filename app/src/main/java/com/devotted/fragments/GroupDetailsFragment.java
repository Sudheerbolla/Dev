package com.devotted.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.devotted.R;
import com.devotted.activities.TempleGroupsActivity;
import com.devotted.adapters.CustomImagesPagerAdapter;
import com.devotted.utils.views.CustomTextView;

import java.util.ArrayList;

public class GroupDetailsFragment extends BaseFragment implements View.OnClickListener {

    private View rootView;
    private TempleGroupsActivity templeGroupsActivity;

    private ImageView imgPrevious, imgNext;
    private ViewPager viewPagerImages;
    private CustomImagesPagerAdapter customImagesPagerAdapter;
    private ArrayList<Integer> imagesArrayList;
    private CustomTextView txtViewDiscussion;

    public GroupDetailsFragment() {
    }

    public static GroupDetailsFragment newInstance() {
        GroupDetailsFragment categoryDetailsFragment = new GroupDetailsFragment();
        categoryDetailsFragment.setArguments(new Bundle());
        return categoryDetailsFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        templeGroupsActivity = (TempleGroupsActivity) getActivity();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_group_details, container, false);
        initComponents();
        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        templeGroupsActivity.setTopBarText(getString(R.string.group_details));
    }

    private void initComponents() {

        imagesArrayList = new ArrayList<>();

        imagesArrayList.add(R.drawable.temple1);
        imagesArrayList.add(R.drawable.temple2);
        imagesArrayList.add(R.drawable.temple3);
        imagesArrayList.add(R.drawable.temple4);

        setReferences();
        setListeners();
    }


    private void setReferences() {
        imgPrevious = rootView.findViewById(R.id.imgPrevious);
        imgNext = rootView.findViewById(R.id.imgNext);
        txtViewDiscussion = rootView.findViewById(R.id.txtViewDiscussion);
        viewPagerImages = rootView.findViewById(R.id.viewPagerImages);

        customImagesPagerAdapter = new CustomImagesPagerAdapter(templeGroupsActivity, imagesArrayList);
        viewPagerImages.setAdapter(customImagesPagerAdapter);

        imgNext.setVisibility(View.VISIBLE);
        imgPrevious.setVisibility(View.GONE);

        viewPagerImages.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (position == 0) {
                    imgNext.setVisibility(View.VISIBLE);
                    imgPrevious.setVisibility(View.GONE);
                } else if (position == imagesArrayList.size() - 1) {
                    imgNext.setVisibility(View.GONE);
                    imgPrevious.setVisibility(View.VISIBLE);
                } else {
                    imgNext.setVisibility(View.VISIBLE);
                    imgPrevious.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }

    private void setListeners() {
        imgNext.setOnClickListener(this);
        imgPrevious.setOnClickListener(this);
        txtViewDiscussion.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.imgPrevious:
                viewPagerImages.setCurrentItem(viewPagerImages.getCurrentItem() - 1, true);
                break;
            case R.id.imgNext:
                viewPagerImages.setCurrentItem(viewPagerImages.getCurrentItem() + 1, true);
                break;
            case R.id.txtViewDiscussion:
                templeGroupsActivity.replaceFragment(ChatFragment.newInstance(), true, R.id.groupsContainer);
                break;
            default:
                break;
        }
    }
}
