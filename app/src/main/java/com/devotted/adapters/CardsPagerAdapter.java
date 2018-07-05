package com.devotted.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.ScrollView;

import com.devotted.R;
import com.devotted.listeners.IClickListener;
import com.devotted.models.CardDataItem;

import java.util.ArrayList;

public class CardsPagerAdapter extends PagerAdapter {

    private Context mContext;
    private ArrayList<CardDataItem> resArrayList;
    private IClickListener iClickListener;

    public CardsPagerAdapter(Context context, ArrayList<CardDataItem> resArrayList, IClickListener iClickListener) {
        mContext = context;
        this.iClickListener = iClickListener;
        this.resArrayList = resArrayList;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup collection, final int position) {

        final ScrollView layout;
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        layout = (ScrollView) inflater.inflate(R.layout.item_card, collection, false);

        RelativeLayout relColoredBackground = layout.findViewById(R.id.relColoredBackground);
//        TextView txtReadLogo = layout.findViewById(R.id.txtReadLogo);
        CardDataItem cardDataItem = resArrayList.get(position);
        if (cardDataItem.isRead) {
            relColoredBackground.setBackground(ContextCompat.getDrawable(mContext, R.drawable.btn_background));
//            txtReadLogo.setVisibility(View.VISIBLE);
        } else {
            relColoredBackground.setBackground(ContextCompat.getDrawable(mContext, R.drawable.btn_background_h));
//            txtReadLogo.setVisibility(View.GONE);
        }
        layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (iClickListener != null) iClickListener.onClick(view, position);
            }
        });
        collection.addView(layout);
        return layout;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup collection, int position, @NonNull Object view) {
        collection.removeView((View) view);
    }

    @Override
    public int getCount() {
        return resArrayList.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

}