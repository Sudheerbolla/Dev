package com.devotted.models;

public class AddressModelStatic {

    public Integer addressId;
    public String mPincode;
    public String city;
    public String line1;
    public String line2;
    public String longitude;
    public String latitude;
    public String addressType;
    public Boolean _default;
    public String image;
    public String rating;
    public String distance;

    public AddressModelStatic(int count) {
        if (count == 1) {
            addressId = count;
            mPincode = "500081";
            city = "Hyderabad";
            line1 = "Cyber Pearl Driveway, Phase 2";
            line2 = " HITEC City";
            latitude = "17.4452";
            longitude = "78.3766";
            addressType = "";
            image = "";
            rating = "3";
            distance = "10";
        } else if (count == 2) {
            addressId = count;
            mPincode = "500081";
            city = "Hyderabad";
            line1 = "Cyber Gateways";
            line2 = " Hitech City";
            latitude = "17.4468";
            longitude = "78.376";
            addressType = "";
            image = "";
            rating = "3";
            distance = "10";
        } else if (count == 3) {
            addressId = count;
            mPincode = "500081";
            city = "Hyderabad";
            line1 = "Cyber Pearl Driveway, Phase 2";
            line2 = " HITEC City";
            latitude = "17.4452";
            longitude = "78.3766";
            addressType = "";
            image = "";
            rating = "3";
            distance = "10";
        } else if (count == 4) {
            addressId = count;
            mPincode = "500081";
            city = "Hyderabad";
            line1 = "Cyber Gateways";
            line2 = " Hitech City";
            latitude = "17.4468";
            longitude = "78.376";
            addressType = "";
            image = "";
            rating = "3";
            distance = "10";
        }
    }

}
