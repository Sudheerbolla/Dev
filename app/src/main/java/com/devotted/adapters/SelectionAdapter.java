package com.devotted.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
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
        if (isUserType) {
            viewHolder.txtSelect.setText(String.format("%s%s", context.getString(R.string.i_am_a), selectionModel.type));
        } else {
            viewHolder.txtSelect.setText(context.getString(R.string.i_choose) + selectionModel.type);
        }
        viewHolder.txtDescription1.setText(selectionModel.description[0]);
        viewHolder.txtDescription2.setText(selectionModel.description[1]);
        viewHolder.txtDescription3.setText(selectionModel.description[2]);

        if (selectionModel.isSelected) {
            viewHolder.txtSelect.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_tick_mark, 0, 0, 0);
        } else {
            viewHolder.txtSelect.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
        }
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

        public CustomTextView txtSelect, txtDescription1, txtDescription2, txtDescription3;
        public ImageView imgType;
        public RelativeLayout cardBody;

        ViewHolder(View itemLayoutView) {
            super(itemLayoutView);
            txtSelect = itemLayoutView.findViewById(R.id.txtSelect);
            txtDescription1 = itemLayoutView.findViewById(R.id.txtDescription1);
            txtDescription2 = itemLayoutView.findViewById(R.id.txtDescription2);
            txtDescription3 = itemLayoutView.findViewById(R.id.txtDescription3);
            imgType = itemLayoutView.findViewById(R.id.imgType);
            cardBody = itemLayoutView.findViewById(R.id.cardBody);
        }
    }

}
