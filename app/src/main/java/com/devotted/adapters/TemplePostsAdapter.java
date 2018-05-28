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
import com.devotted.models.TempleModel;
import com.devotted.utils.views.CircleImageView;
import com.devotted.utils.views.CustomTextView;

import java.util.ArrayList;

public class TemplePostsAdapter extends RecyclerView.Adapter<TemplePostsAdapter.ViewHolder> {

    private ArrayList<TempleModel> itemsData;
    private Context context;
    private IClickListener iClickListener;

    public TemplePostsAdapter(Context context, ArrayList<TempleModel> itemsData, IClickListener iClickListener) {
        this.itemsData = itemsData;
        this.iClickListener = iClickListener;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_temples_posts, null));
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {
        viewHolder.txtLike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (iClickListener != null) {
                    iClickListener.onClick(view, position);
                }
            }
        });
        viewHolder.txtLikes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (iClickListener != null) {
                    iClickListener.onClick(view, position);
                }
            }
        });
        viewHolder.txtComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (iClickListener != null) {
                    iClickListener.onClick(view, position);
                }
            }
        });
        viewHolder.txtComments.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (iClickListener != null) {
                    iClickListener.onClick(view, position);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return itemsData.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public CustomTextView txtTempleName, txtTime, txtDescription, txtDescription1, txtLikes, txtComments, txtLike, txtComment, txtShare;
        public CircleImageView imageViewTemple;
        public ImageView imgMore;
        public LinearLayout relBody;

        public ViewHolder(View itemLayoutView) {
            super(itemLayoutView);

            txtTempleName = itemLayoutView.findViewById(R.id.txtTempleName);
            txtTime = itemLayoutView.findViewById(R.id.txtTime);
            txtDescription = itemLayoutView.findViewById(R.id.txtDescription);
            txtDescription1 = itemLayoutView.findViewById(R.id.txtDescription1);
            txtLikes = itemLayoutView.findViewById(R.id.txtLikes);
            txtLike = itemLayoutView.findViewById(R.id.txtLike);
            txtComments = itemLayoutView.findViewById(R.id.txtComments);
            txtComment = itemLayoutView.findViewById(R.id.txtComment);
            txtShare = itemLayoutView.findViewById(R.id.txtShare);

            imageViewTemple = itemLayoutView.findViewById(R.id.imageViewTemple);
            imgMore = itemLayoutView.findViewById(R.id.imgMore);
            relBody = itemLayoutView.findViewById(R.id.relBody);
        }
    }

}
