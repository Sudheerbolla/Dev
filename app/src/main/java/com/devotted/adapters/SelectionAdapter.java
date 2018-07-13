package com.devotted.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.devotted.R;
import com.devotted.listeners.IClickListener;
import com.devotted.models.SelectionModel;
import com.devotted.utils.StaticUtils;
import com.devotted.utils.views.CustomTextView;

import java.util.ArrayList;

public class SelectionAdapter extends RecyclerView.Adapter<SelectionAdapter.ViewHolder> {

    private ArrayList<SelectionModel> itemsData;
    private Context context;
    private IClickListener iClickListener;
    private boolean isUserType;

    public SelectionAdapter(Context context, ArrayList<SelectionModel> itemsData, IClickListener iClickListener, boolean isUserType) {
        this.itemsData = itemsData;
        this.iClickListener = iClickListener;
        this.context = context;
        this.isUserType = isUserType;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        @SuppressLint("InflateParams") View rootView = LayoutInflater.from(context).inflate(R.layout.item_selection, null);
        RelativeLayout.LayoutParams rlp = new RelativeLayout.LayoutParams(StaticUtils.screen_width - 250, RelativeLayout.LayoutParams.MATCH_PARENT);
        rootView.setLayoutParams(rlp);
        return new ViewHolder(rootView);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, @SuppressLint("RecyclerView") final int position) {
        SelectionModel selectionModel = itemsData.get(position);
        viewHolder.imgType.setImageResource(selectionModel.icon);
        viewHolder.txtSelect.setText((isUserType ? context.getString(R.string.i_am_a) : context.getString(R.string.i_choose)) + " " + selectionModel.type);
        viewHolder.linHolder.removeAllViews();
        for (int i = 1; i < selectionModel.description.length; i++) {
            CustomTextView customTextView = new CustomTextView(context);
            customTextView.setTextSize(16);
            customTextView.setPadding(5, 5, 5, 5);
//            customTextView.setCompoundDrawablesWithIntrinsicBounds(isUserType ? (selectionModel.type.equalsIgnoreCase(context.getString(R.string.devotee)) ? R.drawable.ic_star_d : R.drawable.ic_small_flower) :
//                    (selectionModel.type.equalsIgnoreCase(context.getString(R.string.religious)) ? R.drawable.ic_lotus : R.drawable.ic_star_s), 0, 0, 0);
            customTextView.setCompoundDrawablesWithIntrinsicBounds(((selectionModel.type.equalsIgnoreCase(context.getString(R.string.religious)) || (selectionModel.type.equalsIgnoreCase(context.getString(R.string.devotee)))) ?
                    R.drawable.ic_lotus : R.drawable.ic_star_s), 0, 0, 0);
            customTextView.setCompoundDrawablePadding(StaticUtils.pxFromDp(context, 8));
            customTextView.setGravity(Gravity.CENTER_VERTICAL);
            customTextView.setText(selectionModel.description[i]);
            viewHolder.linHolder.addView(customTextView);
        }
        viewHolder.txtHeading.setText(selectionModel.description[0]);
//        viewHolder.txtHeading.setCompoundDrawablesWithIntrinsicBounds(isUserType ? (selectionModel.type.equalsIgnoreCase(context.getString(R.string.devotee)) ? R.drawable.ic_star_d : R.drawable.ic_small_flower) :
//                (selectionModel.type.equalsIgnoreCase(context.getString(R.string.religious)) ? R.drawable.ic_lotus : R.drawable.ic_star_s), 0, 0, 0);
        viewHolder.txtSelect.setCompoundDrawablesWithIntrinsicBounds(selectionModel.isSelected ? R.drawable.ic_tick_mark : 0, 0, 0, 0);
        viewHolder.txtSelect.setBackgroundResource(selectionModel.isSelected ? R.drawable.gradient_horizontal_plane : R.drawable.btn_background_grey);
        viewHolder.txtSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (iClickListener != null) iClickListener.onClick(v, position);
            }
        });
        viewHolder.txtSelect.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if (iClickListener != null) iClickListener.onLongClick(v, position);
                return false;
            }
        });
    }

    @Override
    public int getItemCount() {
        return itemsData.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public CustomTextView txtSelect, txtHeading;
        public ImageView imgType;
        private LinearLayout linHolder;
        public RelativeLayout cardBody;

        ViewHolder(View itemLayoutView) {
            super(itemLayoutView);
            linHolder = itemLayoutView.findViewById(R.id.linHolder);
            txtSelect = itemLayoutView.findViewById(R.id.txtSelect);
//            txtDescription = itemLayoutView.findViewById(R.id.txtDescription);
            imgType = itemLayoutView.findViewById(R.id.imgType);
            cardBody = itemLayoutView.findViewById(R.id.cardBody);
            txtHeading = itemLayoutView.findViewById(R.id.txtHeading);
        }
    }

}
