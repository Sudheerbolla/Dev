package com.devotted.adapters;

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
import com.devotted.models.CardDataItem;
import com.devotted.utils.StaticUtils;
import com.devotted.utils.views.CustomTextView;

import java.util.ArrayList;

public class CardsRecyclerViewAdapter extends RecyclerView.Adapter<CardsRecyclerViewAdapter.ViewHolder> {

    private ArrayList<CardDataItem> itemsData;
    private Context context;
    private IClickListener iClickListener;

    public CardsRecyclerViewAdapter(Context context, ArrayList<CardDataItem> itemsData, IClickListener iClickListener) {
        this.itemsData = itemsData;
        this.iClickListener = iClickListener;
        this.context = context;
    }

    @NonNull
    @Override
    public CardsRecyclerViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new CardsRecyclerViewAdapter.ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_card, null));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        CardDataItem cardDataItem = itemsData.get(position);
        if (position == 0) {
            holder.txtDate.setVisibility(View.VISIBLE);
            holder.txtDate.setText("Yesterday - " + StaticUtils.getpreviousDatesString(-1));
        } else if (position == 3) {
            holder.txtDate.setVisibility(View.VISIBLE);
            holder.txtDate.setText("" + StaticUtils.getpreviousDatesString(-2));
        } else if (position == 7) {
            holder.txtDate.setVisibility(View.VISIBLE);
            holder.txtDate.setText("Older");
        } else holder.txtDate.setVisibility(View.GONE);

        if (cardDataItem.isRead) {
            holder.relColoredBackground.setBackground(ContextCompat.getDrawable(context, R.drawable.btn_background));
            holder.txtReadLogo.setVisibility(View.VISIBLE);
        } else {
            holder.relColoredBackground.setBackground(ContextCompat.getDrawable(context, R.drawable.btn_background_h));
            holder.txtReadLogo.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return itemsData.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        private RelativeLayout relColoredBackground;
        public CustomTextView txtReadLogo, txtDate;
        public LinearLayout linCard;

        ViewHolder(View itemLayoutView) {
            super(itemLayoutView);
            relColoredBackground = itemLayoutView.findViewById(R.id.relColoredBackground);
            linCard = itemLayoutView.findViewById(R.id.linCard);
            txtReadLogo = itemLayoutView.findViewById(R.id.txtReadLogo);
            txtDate = itemLayoutView.findViewById(R.id.txtDate);
        }
    }


}
