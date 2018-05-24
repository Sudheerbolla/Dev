package com.devotted.fragments;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.devotted.R;
import com.devotted.models.AddressModelStatic;

public class MapFragment extends Fragment {

    int page_position;
    TextView tvTitle, tvLocation, tvCategory, tvRating, tvOffer, tvDistanceInKm, tvDescription, tvPhone;
    Button btnNavigate, btnBook;
    LinearLayout layoutItem;
    ImageView ivMain;
    private AddressModelStatic dealModel;

    public MapFragment() {

    }

    //    @SuppressLint("ValidFragment")
//    public MapFragment(int position, AddressModel placemodel) {
//        this.page_position = position;
//        this.dealModel = placemodel;
//    }
    @SuppressLint("ValidFragment")
    public MapFragment(int position, AddressModelStatic placemodel) {
        this.page_position = position;
        this.dealModel = placemodel;
    }

    public AddressModelStatic getDealModel() {
        return dealModel;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.map_view_item, container, false);
        try {
            tvTitle = (TextView) rootView.findViewById(R.id.tv_title);
            tvDescription = (TextView) rootView.findViewById(R.id.tv_categories);
            tvLocation = (TextView) rootView.findViewById(R.id.tv_offers);
            tvRating = (TextView) rootView.findViewById(R.id.tv_rating);
            tvDistanceInKm = (TextView) rootView.findViewById(R.id.tv_distanceinkm);

            tvTitle.setText(getDealModel().line1);
            tvDescription.setText(getDealModel().line2);
            tvLocation.setText(getDealModel().city);
            tvDistanceInKm.setText(getDealModel().distance);
            tvRating.setText(getDealModel().rating);

            return rootView;

        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("PlacesMapFragment:" + e.getStackTrace());
        }

    }

}
