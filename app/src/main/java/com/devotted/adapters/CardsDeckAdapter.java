package com.devotted.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.devotted.R;
import com.devotted.models.CardDataItem;

import java.util.List;

public class CardsDeckAdapter extends ArrayAdapter<Pair<String, CardDataItem>> {

    private final int mResource;
    private Context context;

    public CardsDeckAdapter(@NonNull Context context, @NonNull List<Pair<String, CardDataItem>> objects) {
        super(context, R.layout.item_card, objects);
        this.context = context;
        mResource = R.layout.item_card;
    }


    @NonNull
    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {
        final ScrollView layout;
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            layout = (ScrollView) inflater.inflate(mResource, parent, false);
        } else
            layout = (ScrollView) convertView;

        Pair item = getItem(position);

        RelativeLayout relColoredBackground = layout.findViewById(R.id.relColoredBackground);
        TextView txtReadLogo = layout.findViewById(R.id.txtReadLogo);
        CardDataItem cardDataItem = (CardDataItem) item.second;
        if (cardDataItem.isRead) {
            relColoredBackground.setBackground(ContextCompat.getDrawable(context, R.drawable.btn_background));
            txtReadLogo.setVisibility(View.VISIBLE);
        } else {
            relColoredBackground.setBackground(ContextCompat.getDrawable(context, R.drawable.btn_background_h));
            txtReadLogo.setVisibility(View.GONE);
        }
        return layout;
    }

}
