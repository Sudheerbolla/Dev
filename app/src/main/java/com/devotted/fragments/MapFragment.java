package com.devotted.fragments;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.devotted.R;
import com.devotted.activities.SearchActivity;
import com.devotted.activities.TempleDetailsActivity;
import com.devotted.models.TempleModel;
import com.devotted.utils.views.CustomTextView;

public class MapFragment extends BaseFragment implements View.OnClickListener {

    private ImageView imageViewTemple, imgFavourite;
    private CustomTextView txtTempleName, txtTempleAddress, txtRating, txtTempleDistance;
    int page_position;
    private LinearLayout ll_map_item;
    private TempleModel dealModel;
    private CardView cardViewTemple;
    private SearchActivity searchActivity;

    public MapFragment() {

    }

    @SuppressLint("ValidFragment")
    public MapFragment(int position, TempleModel placemodel) {
        this.page_position = position;
        this.dealModel = placemodel;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        searchActivity = (SearchActivity) getActivity();
    }

    public TempleModel getDealModel() {
        return dealModel;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.item_map_view_slider, container, false);
        try {
            txtTempleName = rootView.findViewById(R.id.txtTempleName);
            txtTempleAddress = rootView.findViewById(R.id.txtTempleAddress);
            cardViewTemple = rootView.findViewById(R.id.cardViewTemple);
            txtRating = rootView.findViewById(R.id.txtRating);
            imgFavourite = rootView.findViewById(R.id.imgFavourite);
            ll_map_item = rootView.findViewById(R.id.ll_map_item);
            txtTempleDistance = rootView.findViewById(R.id.txtTempleDistance);
            imageViewTemple = rootView.findViewById(R.id.imageViewTemple);

            ll_map_item.setOnClickListener(this);

            txtTempleName.setText(getDealModel().templeName);
            txtTempleAddress.setText(String.format("%s, %s, %s", getDealModel().line1, getDealModel().line2, getDealModel().city));
            txtRating.setText(getDealModel().rating);
            txtTempleDistance.setText(getDealModel().distance);
            RequestOptions options = new RequestOptions()
                    .placeholder(R.drawable.temple)
                    .fitCenter()
                    .error(R.drawable.temple);

            Glide.with(this)
                    .load(getDealModel().image)
                    .apply(options)
                    .into(imageViewTemple);
            imgFavourite.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    imgFavourite.setSelected(!imgFavourite.isSelected());
                }
            });
            cardViewTemple.setOnClickListener(this);
            return rootView;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("PlacesMapFragment:" + e.getStackTrace());
        }

    }

    @Override
    public void onClick(View view) {
        startActivity(new Intent(searchActivity, TempleDetailsActivity.class));
    }
}
