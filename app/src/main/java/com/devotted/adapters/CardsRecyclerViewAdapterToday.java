package com.devotted.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.devotted.R;
import com.devotted.listeners.IClickListener;
import com.devotted.models.CardDataItemNew;
import com.devotted.utils.StaticUtils;
import com.devotted.utils.views.CustomTextView;
import com.devotted.utils.views.ReadMoreTextView;

import java.util.ArrayList;

public class CardsRecyclerViewAdapterToday extends RecyclerView.Adapter<CardsRecyclerViewAdapterToday.ViewHolder> {

    private ArrayList<CardDataItemNew> itemsData;
    private Context context;
    private IClickListener iClickListener;

    public CardsRecyclerViewAdapterToday(Context context, ArrayList<CardDataItemNew> itemsData, IClickListener iClickListener) {
        this.itemsData = itemsData;
        this.iClickListener = iClickListener;
        this.context = context;
    }

    @SuppressLint("InflateParams")
    @NonNull
    @Override
    public CardsRecyclerViewAdapterToday.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        @SuppressLint("InflateParams") View rootView = LayoutInflater.from(context).inflate(R.layout.item_today_card, null);
        RelativeLayout.LayoutParams rlp = new RelativeLayout.LayoutParams(StaticUtils.screen_width - 90, RelativeLayout.LayoutParams.WRAP_CONTENT);
        rootView.setLayoutParams(rlp);
        return new CardsRecyclerViewAdapterToday.ViewHolder(rootView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") final int position) {
        CardDataItemNew cardDataItem = itemsData.get(position);
        if (cardDataItem.isRead) {
            holder.relColoredBackground.setBackground(ContextCompat.getDrawable(context, R.drawable.btn_background_grey));
            holder.txtReadLogo.setVisibility(View.VISIBLE);
        } else {
            holder.relColoredBackground.setBackground(ContextCompat.getDrawable(context, R.drawable.btn_background_h));
            holder.txtReadLogo.setVisibility(View.GONE);
        }

        holder.txtDescription.setText(cardDataItem.content);
//        holder.txtQuoteMeaning.setText(cardDataItem.content);
//        holder.txtDescription.getIsExpandedOnce();
        holder.txtDescription.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (iClickListener != null) iClickListener.onClick(view, position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return itemsData.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        private RelativeLayout relColoredBackground;
        public CustomTextView txtReadLogo;
        public ReadMoreTextView txtDescription;
        public LinearLayout linCard;

        ViewHolder(View itemLayoutView) {
            super(itemLayoutView);
            relColoredBackground = itemLayoutView.findViewById(R.id.relColoredBackground);
            linCard = itemLayoutView.findViewById(R.id.linCard);
            txtReadLogo = itemLayoutView.findViewById(R.id.txtReadLogo);
            txtDescription = itemLayoutView.findViewById(R.id.txtDescription);
//            txtQuote = itemLayoutView.findViewById(R.id.txtQuote);
//            txtQuoteMeaning = itemLayoutView.findViewById(R.id.txtQuoteMeaning);
        }
    }


}
