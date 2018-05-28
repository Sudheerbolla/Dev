package com.devotted.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;

import com.devotted.R;

import java.util.List;

public class CardsDeckAdapter extends ArrayAdapter<Pair<String, Integer>> {

    private final int mResource;

    public CardsDeckAdapter(@NonNull Context context, @NonNull List<Pair<String, Integer>> objects) {
        super(context, R.layout.item_card, objects);
        mResource = R.layout.item_card;
    }


    @NonNull
    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {
        final LinearLayout layout;
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            layout = (LinearLayout) inflater.inflate(mResource, parent, false);
        } else
            layout = (LinearLayout) convertView;

        Pair item = getItem(position);
        if (item != null) {
//            layout.setBackgroundColor((Integer) item.second);
//            ((TextView) layout.findViewById(R.id.item_text)).setText((String) item.first);
        }
        return layout;
    }

}
