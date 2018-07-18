package com.devotted.models;

import com.devotted.R;

public class FavouriteTempleModel {

    public boolean isFavourite, isFocused;
    public Integer addressId, image;
    public String templeName, mPincode, city, line1, line2, longitude, latitude, rating, distance;

    public FavouriteTempleModel(int count) {
        if (count == 1) {
            templeName = "Ashtalakshmi Temple, Kothapet, Hyderabad";
            addressId = count;
            mPincode = "500081";
            city = "Hyderabad";
            line1 = "Kothapet, Hyderabad";
            line2 = " Kothapet";
            latitude = "17.4452";
            longitude = "78.3766";
            image = R.drawable.ic_astalakshmi_temple;
            rating = "3";
            distance = "0";
        } else if (count == 2) {
            templeName = "Chilkur Balaji Temple, Hyderabad";
            addressId = count;
            mPincode = "500081";
            city = "Chilkuru";
            line1 = "Hyderabad";
            line2 = " Hitech City";
            latitude = "17.4468";
            longitude = "78.376";
            image = R.drawable.ic_chilkur;
            rating = "3";
            distance = "3";
        } else if (count == 3) {
            templeName = "Karmanghat Hanuman temple, Hyderabad";
            addressId = count;
            mPincode = "500081";
            city = "Hyderabad";
            line1 = "Secunderabad";
            line2 = "Secunderabad";
            latitude = "17.4399";
            longitude = "78.4983";
            image = R.drawable.ic_hanuman;
            rating = "2";
            distance = "0";
        }
    }

}
