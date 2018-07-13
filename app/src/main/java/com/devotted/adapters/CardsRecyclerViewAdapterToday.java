package com.devotted.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.NonNull;
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
        holder.txtDescription.setCompoundDrawablesWithIntrinsicBounds(0, 0, cardDataItem.isRead ? R.drawable.ic_read_orange : 0, 0);
        holder.txtDescription.setText(cardDataItem.content);
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

        public CustomTextView txtDescription;
        public LinearLayout linCard;

        ViewHolder(View itemLayoutView) {
            super(itemLayoutView);
            linCard = itemLayoutView.findViewById(R.id.linCard);
            txtDescription = itemLayoutView.findViewById(R.id.txtDescription);
        }
    }


}
