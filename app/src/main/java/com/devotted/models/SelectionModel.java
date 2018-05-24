package com.devotted.models;

import com.google.gson.JsonObject;

public class SelectionModel {

    public String type, description[];
    public boolean isSelected;
    public int icon;

    public SelectionModel(String type, String[] description, int icon) {
        this.type = type;
        this.icon = icon;
        this.description = description;
        this.isSelected = false;
    }

    public SelectionModel(JsonObject jsonObject) {

    }
}
