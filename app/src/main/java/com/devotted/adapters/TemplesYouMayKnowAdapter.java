package com.devotted.adapters;

import android.content.Context;
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

public class TemplesYouMayKnowAdapter extends RecyclerView.Adapter<TemplesYouMayKnowAdapter.ViewHolder> {

    private ArrayList<TempleModel> itemsData;
    private Context context;
    private IClickListener iClickListener;

    public TemplesYouMayKnowAdapter(Context context, ArrayList<TempleModel> itemsData, IClickListener iClickListener) {
        this.itemsData = itemsData;
        this.iClickListener = iClickListener;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_temples_you_may_know, null));
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {
        TempleModel templeModel = itemsData.get(position);
        viewHolder.txtTempleName.setText(templeModel.templeName);
        viewHolder.txtUpdates.setText(templeModel.distance + " Updates");
        viewHolder.txtTempleName.setSelected(true);

        viewHolder.relBody.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (iClickListener != null) iClickListener.onClick(v, position);
            }
        });
        viewHolder.relBody.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if (iClickListener != null) iClickListener.onLongClick(v, position);
                return false;
            }
        });
        RequestOptions options = new RequestOptions()
                .placeholder(R.drawable.temple)
                .fitCenter()
                .error(R.drawable.temple);

        Glide.with(context)
                .load(templeModel.image)
                .apply(options)
                .into(viewHolder.imageViewTemple);
    }

    @Override
    public int getItemCount() {
        return itemsData.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public CustomTextView txtUpdates, txtTempleName;
        public ImageView imageViewTemple;
        private LinearLayout relBody;

        public ViewHolder(View itemLayoutView) {
            super(itemLayoutView);
            txtTempleName = itemLayoutView.findViewById(R.id.txtTempleName);
            txtUpdates = itemLayoutView.findViewById(R.id.txtUpdates);
            imageViewTemple = itemLayoutView.findViewById(R.id.imageViewTemple);
            relBody = itemLayoutView.findViewById(R.id.relBody);
        }
    }

}
