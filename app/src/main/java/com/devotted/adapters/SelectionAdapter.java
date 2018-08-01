package com.devotted.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
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
    private LayoutInflater linf;

    public SelectionAdapter(Context context, ArrayList<SelectionModel> itemsData, IClickListener iClickListener, boolean isUserType) {
        linf = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.itemsData = itemsData;
        this.iClickListener = iClickListener;
        this.context = context;
        this.isUserType = isUserType;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        @SuppressLint("InflateParams") View rootView = linf.inflate(R.layout.item_selection, null);
        RelativeLayout.LayoutParams rlp = new RelativeLayout.LayoutParams(StaticUtils.screen_width - 250, RelativeLayout.LayoutParams.MATCH_PARENT);
        rootView.setLayoutParams(rlp);
        return new ViewHolder(rootView);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, @SuppressLint("RecyclerView") final int position) {
        SelectionModel selectionModel = itemsData.get(position);
        viewHolder.imgType.setImageResource(selectionModel.icon);
        String typeLine = "";
        if (selectionModel.type.equalsIgnoreCase(context.getString(R.string.spiritual))) {
            typeLine = context.getString(R.string.i_follow_spiritual);
//            viewHolder.txtSelect.setLines(2);
        } else if (selectionModel.type.equalsIgnoreCase(context.getString(R.string.religious))) {
            typeLine = context.getString(R.string.i_follow_religious);
//            viewHolder.txtSelect.setLines(2);
        } else if (selectionModel.type.equalsIgnoreCase(context.getString(R.string.devotee))) {
            typeLine = context.getString(R.string.i_am_devotee);
//            viewHolder.txtSelect.setLines(1);
        } else if (selectionModel.type.equalsIgnoreCase(context.getString(R.string.temple_member))) {
            typeLine = context.getString(R.string.i_am_temple_member);
//            viewHolder.txtSelect.setLines(1);
        }
        viewHolder.txtSelect.setText(typeLine);
        viewHolder.linHolder.removeAllViews();
        for (int i = 1; i < selectionModel.description.length; i++) {
            View inflatedLayout = linf.inflate(R.layout.layout_selection_lines, null, false);
            ImageView imageView = inflatedLayout.findViewById(R.id.imgStar);
            CustomTextView customTextView = inflatedLayout.findViewById(R.id.txtLine);
            if (selectionModel.description[i].equalsIgnoreCase(context.getString(R.string.user_type2_line5))) {
                imageView.setVisibility(View.GONE);
                customTextView.setTextSize(17);
            } else {
                imageView.setVisibility(View.VISIBLE);
                imageView.setImageResource(((selectionModel.type.equalsIgnoreCase(context.getString(R.string.religious)) || (selectionModel.type.equalsIgnoreCase(context.getString(R.string.devotee)))) ?
                        R.drawable.ic_lotus : R.drawable.ic_star_s));
                customTextView.setTextSize(16);
            }
            customTextView.setText(selectionModel.description[i]);
            viewHolder.linHolder.addView(inflatedLayout);
        }
        viewHolder.txtHeading.setText(selectionModel.description[0]);
        viewHolder.imgCheckBox.setVisibility(selectionModel.isSelected ? View.VISIBLE : View.INVISIBLE);
        viewHolder.linSelect.setBackgroundResource(selectionModel.isSelected ? R.drawable.gradient_horizontal_plane : R.drawable.btn_background_grey);
        viewHolder.linSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (iClickListener != null) iClickListener.onClick(v, position);
            }
        });
        viewHolder.txtSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (iClickListener != null) iClickListener.onClick(v, position);
            }
        });
        viewHolder.linSelect.setOnLongClickListener(new View.OnLongClickListener() {
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
        public ImageView imgType, imgCheckBox;
        private LinearLayout linHolder;
        public RelativeLayout cardBody, linSelect;

        ViewHolder(View itemLayoutView) {
            super(itemLayoutView);
            linSelect = itemLayoutView.findViewById(R.id.linSelect);
            linHolder = itemLayoutView.findViewById(R.id.linHolder);
            txtSelect = itemLayoutView.findViewById(R.id.txtSelect);
//            txtDescription = itemLayoutView.findViewById(R.id.txtDescription);
            imgType = itemLayoutView.findViewById(R.id.imgType);
            imgCheckBox = itemLayoutView.findViewById(R.id.imgCheckBox);
            cardBody = itemLayoutView.findViewById(R.id.cardBody);
            txtHeading = itemLayoutView.findViewById(R.id.txtHeading);
        }
    }

}
