package com.devotted.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.devotted.R;
import com.devotted.activities.TempleDetailsActivity;
import com.devotted.adapters.ReviewsAdapter;
import com.devotted.listeners.IClickListener;
import com.devotted.models.ReviewsItem;
import com.devotted.utils.views.CustomTextView;

import java.util.ArrayList;

public class ReviewsListFragment extends BaseFragment implements IClickListener, View.OnClickListener {

    private View rootView;
    private TempleDetailsActivity templeDetailsActivity;
    private CustomTextView txtAddNewReview;
    private RecyclerView recyclerViewReviews;
    private ReviewsAdapter reviewsAdapter;
    private ArrayList<ReviewsItem> reviewsItemArrayList;

    public ReviewsListFragment() {
    }

    public static ReviewsListFragment newInstance() {
        ReviewsListFragment categoryDetailsFragment = new ReviewsListFragment();
        categoryDetailsFragment.setArguments(new Bundle());
        return categoryDetailsFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        reviewsItemArrayList = new ArrayList<>();
        templeDetailsActivity = (TempleDetailsActivity) getActivity();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_reviews, container, false);
        initComponents();
        return rootView;
    }

    private void initComponents() {
        setReferences();
        setListeners();
    }

    private void setReferences() {
        recyclerViewReviews = rootView.findViewById(R.id.recyclerViewReviews);
        txtAddNewReview = rootView.findViewById(R.id.txtAddNewReview);
        txtAddNewReview.setOnClickListener(this);
        setDummyData();
        setAdapter();
    }

    private void setDummyData() {
        for (int i = 1; i < 5; i++) {
            reviewsItemArrayList.add(new ReviewsItem());
        }
    }

    private void setAdapter() {
        recyclerViewReviews.setLayoutManager(new LinearLayoutManager(templeDetailsActivity));
        reviewsAdapter = new ReviewsAdapter(templeDetailsActivity, reviewsItemArrayList, this);
        recyclerViewReviews.setAdapter(reviewsAdapter);
    }

    private void setListeners() {

    }

    @Override
    public void onClick(View view, int position) {
        switch (view.getId()) {
//            case R.id.imgFavourite:
//                break;
            default:
                break;
        }
    }

    @Override
    public void onLongClick(View view, int position) {
        switch (view.getId()) {
//            case R.id.txtSelect:
//                break;
            default:
                break;
        }
    }

    @Override
    public void onClick(View view) {

    }
}
