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

import com.devotted.R;
import com.devotted.listeners.IClickListener;
import com.devotted.models.ReviewsItem;
import com.devotted.utils.views.CustomTextView;

import java.util.ArrayList;

public class CommentsAdapter extends RecyclerView.Adapter<CommentsAdapter.ViewHolder> {

    private ArrayList<ReviewsItem> itemsData;
    private Context context;
    private IClickListener iClickListener;

    public CommentsAdapter(Context context, ArrayList<ReviewsItem> itemsData, IClickListener iClickListener) {
        this.itemsData = itemsData;
        this.iClickListener = iClickListener;
        this.context = context;
    }

    @SuppressLint("InflateParams")
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_comments, null));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, final int position) {
    }

    @Override
    public int getItemCount() {
        return itemsData.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public CustomTextView txtTime, txtUserName, txtDescription;
        public ImageView imageViewUser;
        public LinearLayout relBody;

        public ViewHolder(View itemLayoutView) {
            super(itemLayoutView);
            txtUserName = itemLayoutView.findViewById(R.id.txtUserName);
            txtTime = itemLayoutView.findViewById(R.id.txtTime);
            txtDescription = itemLayoutView.findViewById(R.id.txtDescription);

            imageViewUser = itemLayoutView.findViewById(R.id.imageViewUser);
            relBody = itemLayoutView.findViewById(R.id.relBody);
        }
    }

}
