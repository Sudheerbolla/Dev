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
            viewHolder.txtSelect.setText(context.getString(R.string.i_am_a) + " " + selectionModel.type);
        } else {
            viewHolder.txtSelect.setText(context.getString(R.string.i_choose) + " " + selectionModel.type);
        }
        String description = "";
        for (int i = 0; i < selectionModel.description.length; i++) {
            description += "\n\n * " + selectionModel.description[i];
        }
        viewHolder.txtDescription.setText(description);

        viewHolder.txtSelect.setCompoundDrawablesWithIntrinsicBounds(selectionModel.isSelected ? R.drawable.ic_tick_mark : 0, 0, 0, 0);
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

        public CustomTextView txtSelect, txtDescription;
        public ImageView imgType;
        public RelativeLayout cardBody;

        ViewHolder(View itemLayoutView) {
            super(itemLayoutView);
            txtSelect = itemLayoutView.findViewById(R.id.txtSelect);
            txtDescription = itemLayoutView.findViewById(R.id.txtDescription);
            imgType = itemLayoutView.findViewById(R.id.imgType);
            cardBody = itemLayoutView.findViewById(R.id.cardBody);
        }
    }

}
