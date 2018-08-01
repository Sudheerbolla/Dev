package com.devotted.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.devotted.R;
import com.devotted.listeners.IClickListener;
import com.devotted.models.TempleModel;
import com.devotted.utils.views.CustomTextView;

import java.util.ArrayList;

public class GroupsListAdapter extends RecyclerView.Adapter<GroupsListAdapter.ViewHolder> {

    private ArrayList<TempleModel> itemsData;
    private Context context;
    private IClickListener iClickListener;

    public GroupsListAdapter(Context context, ArrayList<TempleModel> itemsData, IClickListener iClickListener) {
        this.itemsData = itemsData;
        this.iClickListener = iClickListener;
        this.context = context;
    }

    @SuppressLint("InflateParams")
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_groups, null));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, @SuppressLint("RecyclerView") final int position) {
        TempleModel templeModel = itemsData.get(position);
        switch (position) {
            case 0:
                viewHolder.txtGroupName.setText(context.getString(R.string.swami_jaganath_temple_volunteer_group));
                break;
            case 1:
                viewHolder.txtGroupName.setText(R.string.chilukuru_balaji_temple_volunteer_group);
                break;
            case 2:
                viewHolder.txtGroupName.setText(R.string.hanuman_temple_volunteer_group);
                break;
        }

        viewHolder.cardViewGroups.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (iClickListener != null) iClickListener.onClick(v, position);
            }
        });

        viewHolder.cardViewGroups.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if (iClickListener != null) iClickListener.onLongClick(v, position);
                return false;
            }
        });

        viewHolder.ll_groups_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (iClickListener != null) iClickListener.onClick(v, position);
            }
        });

        RequestOptions options = new RequestOptions()
                .placeholder(R.drawable.temple)
                .fitCenter()
                .error(R.drawable.temple);

        Glide.with(context)
                .load(R.drawable.temple1)
                .apply(options)
                .into(viewHolder.imageViewGroup);
    }

    @Override
    public int getItemCount() {
        return itemsData.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public CustomTextView txtVolunteers, txtGroupName;
        public ImageView imageViewGroup;
        public LinearLayout ll_groups_item;
        public CardView cardViewGroups;

        ViewHolder(View itemLayoutView) {
            super(itemLayoutView);
            txtGroupName = itemLayoutView.findViewById(R.id.txtGroupName);
            txtVolunteers = itemLayoutView.findViewById(R.id.txtVolunteers);
            imageViewGroup = itemLayoutView.findViewById(R.id.imageViewGroup);
            ll_groups_item = itemLayoutView.findViewById(R.id.ll_groups_item);
            cardViewGroups = itemLayoutView.findViewById(R.id.cardViewGroups);
        }
    }

}
