package com.devotted.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.devotted.R;
import com.devotted.activities.TempleDetailsActivity;
import com.devotted.adapters.CustomImagesPagerAdapter;
import com.devotted.utils.DialogUtils;

import java.util.ArrayList;

public class TempleDetailsFragment extends BaseFragment implements View.OnClickListener {

    private View rootView;
    private TempleDetailsActivity templeDetailsActivity;

    private ImageView imgPrevious, imgNext;
    private ViewPager viewPagerImages;
    private CustomImagesPagerAdapter customImagesPagerAdapter;
    private ArrayList<Integer> imagesArrayList;
    private RelativeLayout relInviteVolunteer;
    private LinearLayout linReviews;

    public TempleDetailsFragment() {
    }

    public static TempleDetailsFragment newInstance() {
        TempleDetailsFragment categoryDetailsFragment = new TempleDetailsFragment();
        categoryDetailsFragment.setArguments(new Bundle());
        return categoryDetailsFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        templeDetailsActivity = (TempleDetailsActivity) getActivity();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_temple_details, container, false);
        initComponents();
        return rootView;
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
        relInviteVolunteer = rootView.findViewById(R.id.relInviteVolunteer);
        viewPagerImages = rootView.findViewById(R.id.viewPagerImages);
        linReviews = rootView.findViewById(R.id.linReviews);

        customImagesPagerAdapter = new CustomImagesPagerAdapter(templeDetailsActivity, imagesArrayList);
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
        relInviteVolunteer.setOnClickListener(this);
        linReviews.setOnClickListener(this);
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
            case R.id.relInviteVolunteer:
                DialogUtils.showInviteVolunteerDialog(templeDetailsActivity, new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                    }
                });
                break;
            case R.id.linReviews:
                templeDetailsActivity.replaceFragment(ReviewsListFragment.newInstance(), true, R.id.templeDetailsFrame);
                break;
            default:
                break;
        }
    }
}
