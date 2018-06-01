package com.devotted.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.devotted.R;
import com.devotted.listeners.IClickListener;
import com.devotted.models.TempleModel;
import com.devotted.utils.views.CustomTextView;

import java.util.ArrayList;

public class YourFavouriteTemplesAdapter extends RecyclerView.Adapter<YourFavouriteTemplesAdapter.ViewHolder> {

    private ArrayList<TempleModel> itemsData;
    private Context context;
    private IClickListener iClickListener;

    public YourFavouriteTemplesAdapter(Context context, ArrayList<TempleModel> itemsData, IClickListener iClickListener) {
        this.itemsData = itemsData;
        this.iClickListener = iClickListener;
        this.context = context;
    }

    @SuppressLint("InflateParams")
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_temples_you_may_know, null));
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder viewHolder, @SuppressLint("RecyclerView") final int position) {

        TempleModel templeModel = itemsData.get(position);
        viewHolder.txtTempleName.setText(templeModel.templeName);

        viewHolder.txtUpdates.setText(String.format("%s %s", templeModel.distance, context.getString(R.string.updates)));
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

        if (templeModel.isFocused) {
            Animation anim = AnimationUtils.loadAnimation(context, R.anim.scale_in_tv);
            viewHolder.relBody.startAnimation(anim);
            anim.setFillAfter(true);
        } else {
            Animation anim = AnimationUtils.loadAnimation(context, R.anim.scale_out_tv);
            viewHolder.relBody.startAnimation(anim);
            anim.setFillAfter(true);
        }

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

    public class ViewHolder extends RecyclerView.ViewHolder {

        public CustomTextView txtUpdates, txtTempleName;
        public ImageView imageViewTemple;
        private LinearLayout relBody;

        ViewHolder(View itemLayoutView) {
            super(itemLayoutView);
            txtTempleName = itemLayoutView.findViewById(R.id.txtTempleName);
            txtUpdates = itemLayoutView.findViewById(R.id.txtUpdates);
            imageViewTemple = itemLayoutView.findViewById(R.id.imageViewTemple);
            relBody = itemLayoutView.findViewById(R.id.relBody);

        }

    }

}
