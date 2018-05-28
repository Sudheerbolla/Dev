package com.devotted.models;

import com.devotted.R;
import com.google.gson.JsonObject;

public class GodModel {

    public String name;
    public boolean isSelected;
    public int icon;

    public GodModel(String name) {
        this.name = name;
//        this.icon = icon;
        this.icon = R.drawable.krishna_icon;
        this.isSelected = false;
    }

    public GodModel(JsonObject jsonObject) {

    }
}
