package com.devotted.adapters;

import android.content.Context;
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

public class ReviewsAdapter extends RecyclerView.Adapter<ReviewsAdapter.ViewHolder> {

    private ArrayList<ReviewsItem> itemsData;
    private Context context;
    private IClickListener iClickListener;

    public ReviewsAdapter(Context context, ArrayList<ReviewsItem> itemsData, IClickListener iClickListener) {
        this.itemsData = itemsData;
        this.iClickListener = iClickListener;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_reviews, null));
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {
    }

    @Override
    public int getItemCount() {
        return itemsData.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public CustomTextView txtTime, txtUserName, txtDescription, txtRemoveReview, txtDisLikes, txtLikes;
        public ImageView imageViewUser;
        public LinearLayout relBody;

        public ViewHolder(View itemLayoutView) {
            super(itemLayoutView);
            txtUserName = itemLayoutView.findViewById(R.id.txtUserName);
            txtTime = itemLayoutView.findViewById(R.id.txtTime);
            txtDescription = itemLayoutView.findViewById(R.id.txtDescription);
            txtRemoveReview = itemLayoutView.findViewById(R.id.txtRemoveReview);
            txtDisLikes = itemLayoutView.findViewById(R.id.txtDisLikes);
            txtLikes = itemLayoutView.findViewById(R.id.txtLikes);

            imageViewUser = itemLayoutView.findViewById(R.id.imageViewUser);
            relBody = itemLayoutView.findViewById(R.id.relBody);
        }
    }

}
