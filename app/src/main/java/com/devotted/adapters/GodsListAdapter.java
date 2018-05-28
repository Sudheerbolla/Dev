package com.devotted.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;

import com.devotted.R;
import com.devotted.listeners.IClickListener;
import com.devotted.models.GodModel;
import com.devotted.utils.views.CustomTextView;

import java.util.ArrayList;

public class GodsListAdapter extends RecyclerView.Adapter<GodsListAdapter.ViewHolder> {

    private ArrayList<GodModel> itemsData;
    private Context context;
    private IClickListener iClickListener;

    public GodsListAdapter(Context context, ArrayList<GodModel> itemsData, IClickListener iClickListener) {
        this.itemsData = itemsData;
        this.iClickListener = iClickListener;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View rootView = LayoutInflater.from(context).inflate(R.layout.item_gods, null);
        return new ViewHolder(rootView);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {
        GodModel selectionModel = itemsData.get(position);
        viewHolder.imgGodProfilePic.setImageResource(selectionModel.icon);
        viewHolder.checkBox.setChecked(selectionModel.isSelected);

        viewHolder.checkBox.setOnClickListener(new View.OnClickListener() {
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

        public CustomTextView txtGodName;
        public ImageView imgGodProfilePic;
        public CheckBox checkBox;

        public ViewHolder(View itemLayoutView) {
            super(itemLayoutView);
            txtGodName = itemLayoutView.findViewById(R.id.txtGodName);
            imgGodProfilePic = itemLayoutView.findViewById(R.id.imgGodProfilePic);
            checkBox = itemLayoutView.findViewById(R.id.checkBox);
        }
    }

}
