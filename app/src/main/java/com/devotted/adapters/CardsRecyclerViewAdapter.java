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
import com.devotted.utils.views.CustomTextView;

import java.util.ArrayList;

public class CardsRecyclerViewAdapter extends RecyclerView.Adapter<CardsRecyclerViewAdapter.ViewHolder> {

    private ArrayList<CardDataItemNew> itemsData;
    private Context context;
    private IClickListener iClickListener;

    public CardsRecyclerViewAdapter(Context context, ArrayList<CardDataItemNew> itemsData, IClickListener iClickListener) {
        this.itemsData = itemsData;
        this.iClickListener = iClickListener;
        this.context = context;
    }

    @SuppressLint("InflateParams")
    @NonNull
    @Override
    public CardsRecyclerViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new CardsRecyclerViewAdapter.ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_card, null));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        CardDataItemNew cardDataItem = itemsData.get(position);

        holder.txtDate.setVisibility(View.VISIBLE);
        holder.txtDate.setText(cardDataItem.weekDay);

        if (cardDataItem.isRead) {
//            holder.relColoredBackground.setBackground(ContextCompat.getDrawable(context, R.drawable.btn_background_grey));
//            holder.txtReadLogo.setVisibility(View.VISIBLE);
            holder.txtDescription.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_read, 0, 0, 0);
        } else {
//            holder.relColoredBackground.setBackground(ContextCompat.getDrawable(context, R.drawable.btn_background_h));
//            holder.txtReadLogo.setVisibility(View.GONE);
            holder.txtDescription.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
        }
        holder.txtDescription.setText(cardDataItem.content);
//        holder.txtQuoteMeaning.setText(cardDataItem.content);
    }

    @Override
    public int getItemCount() {
        return itemsData.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        private RelativeLayout relColoredBackground;
        public CustomTextView /*txtReadLogo, */txtDate, txtDescription;
        //        public ReadMoreTextView txtDescription;
        public LinearLayout linCard;

        ViewHolder(View itemLayoutView) {
            super(itemLayoutView);
            relColoredBackground = itemLayoutView.findViewById(R.id.relColoredBackground);
            linCard = itemLayoutView.findViewById(R.id.linCard);
//            txtReadLogo = itemLayoutView.findViewById(R.id.txtReadLogo);
            txtDate = itemLayoutView.findViewById(R.id.txtDate);
            txtDescription = itemLayoutView.findViewById(R.id.txtDescription);
//            txtQuote = itemLayoutView.findViewById(R.id.txtQuote);
//            txtQuoteMeaning = itemLayoutView.findViewById(R.id.txtQuoteMeaning);
        }
    }


}
