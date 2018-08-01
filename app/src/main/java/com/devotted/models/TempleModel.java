package com.devotted.models;

import android.content.Context;

import com.devotted.R;

public class TempleModel {

    public boolean isFavourite, isFocused;
    public Integer addressId, image;
    public String templeName, mPincode, city, line1, line2, longitude, latitude, rating, distance;

    public TempleModel(Context context, int count) {
        if (count == 1) {
            templeName = context.getString(R.string.sai_baba_temple);
            addressId = count;
            mPincode = "500081";
            city = "Hyderabad";
            line1 = "Cyber Pearl Driveway, Phase 2";
            line2 = " HITEC City";
            latitude = "17.4452";
            longitude = "78.3766";
            image = R.drawable.temple1;
            rating = "3";
            distance = "1";
        } else if (count == 2) {
            templeName = context.getString(R.string.sri_venkateswara_swamy_temple);
            addressId = count;
            mPincode = "500081";
            city = "Hyderabad";
            line1 = "Cyber Gateways";
            line2 = " Hitech City";
            latitude = "17.4468";
            longitude = "78.376";
            image = R.drawable.temple2;
            rating = "3";
            distance = "2";
        } else if (count == 3) {
            templeName = context.getString(R.string.sri_krishna_bagavan_swamy_temple);
            addressId = count;
            mPincode = "500081";
            city = "Hyderabad";
            line1 = "Secunderabad";
            line2 = "Secunderabad";
            latitude = "17.4399";
            longitude = "78.4983";
            image = R.drawable.temple3;
            rating = "2";
            distance = "12";
        } else if (count == 4) {
            templeName = context.getString(R.string.lord_shiva_temple);
            addressId = count;
            mPincode = "500081";
            city = "Hyderabad";
            line1 = "IIIT Hyderabad";
            line2 = " Gachibowli";
            latitude = "17.4454";
            longitude = "78.3482";
            image = R.drawable.temple4;
            rating = "4";
            distance = "7";
        }
    }

}
