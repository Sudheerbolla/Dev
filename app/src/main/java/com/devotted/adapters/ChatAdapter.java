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
import com.devotted.models.ReviewsItem;
import com.devotted.utils.views.CustomTextView;

import java.util.ArrayList;

public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.ViewHolder> {

    private ArrayList<ReviewsItem> itemsData;
    private Context context;
    private IClickListener iClickListener;
    private final static int INCOMING = 0, OUTGOING = 1;

    public ChatAdapter(Context context, ArrayList<ReviewsItem> itemsData, IClickListener iClickListener) {
        this.itemsData = itemsData;
        this.iClickListener = iClickListener;
        this.context = context;
    }

    @SuppressLint("InflateParams")
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_chat, null));
    }

    @Override
    public int getItemViewType(int position) {
        if (position % 2 == 0) {
            return INCOMING;
        } else return OUTGOING;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, final int position) {
        HideAllViews(viewHolder);
        setVisibilitiesForMessageType(viewHolder, getItemViewType(position));
    }

    private void setVisibilitiesForMessageType(ViewHolder viewHolder, int itemViewType) {
        if (itemViewType == INCOMING) {
            viewHolder.relInComing.setVisibility(View.VISIBLE);
        } else {
            viewHolder.relOutGoing.setVisibility(View.VISIBLE);
        }
    }

    private void HideAllViews(ViewHolder viewHolder) {
        viewHolder.relOutGoing.setVisibility(View.GONE);
        viewHolder.relInComing.setVisibility(View.GONE);
    }

    @Override
    public int getItemCount() {
        return itemsData.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public CustomTextView txtTimeInComing, txtTimeOutGoing, txtUserNameInComing, txtUserNameOutGoing, txtMessageInComing, txtMessageOutGoing;
        public ImageView imageViewUserInComing, imageViewUserOutGoing;
        public LinearLayout relBody;
        public RelativeLayout relInComing, relOutGoing, relUserDetailsInComing, relUserDetailsOutGoing;

        public ViewHolder(View itemLayoutView) {
            super(itemLayoutView);

            txtTimeInComing = itemLayoutView.findViewById(R.id.txtTimeInComing);
            txtTimeOutGoing = itemLayoutView.findViewById(R.id.txtTimeOutGoing);

            txtUserNameInComing = itemLayoutView.findViewById(R.id.txtUserNameInComing);
            txtUserNameOutGoing = itemLayoutView.findViewById(R.id.txtUserNameOutGoing);

            txtMessageInComing = itemLayoutView.findViewById(R.id.txtMessageInComing);
            txtMessageOutGoing = itemLayoutView.findViewById(R.id.txtMessageOutGoing);

            imageViewUserOutGoing = itemLayoutView.findViewById(R.id.imageViewUserOutGoing);
            imageViewUserInComing = itemLayoutView.findViewById(R.id.imageViewUserInComing);

            relInComing = itemLayoutView.findViewById(R.id.relInComing);
            relOutGoing = itemLayoutView.findViewById(R.id.relOutGoing);

            relUserDetailsInComing = itemLayoutView.findViewById(R.id.relUserDetailsInComing);
            relUserDetailsOutGoing = itemLayoutView.findViewById(R.id.relUserDetailsOutGoing);

            relBody = itemLayoutView.findViewById(R.id.relBody);

        }
    }

}
