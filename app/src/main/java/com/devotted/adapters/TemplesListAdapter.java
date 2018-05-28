package com.devotted.adapters;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.devotted.R;
import com.devotted.listeners.IClickListener;
import com.devotted.models.TempleModel;
import com.devotted.utils.views.CustomTextView;

import java.util.ArrayList;

public class TemplesListAdapter extends RecyclerView.Adapter<TemplesListAdapter.ViewHolder> {

    private ArrayList<TempleModel> itemsData;
    private Context context;
    private IClickListener iClickListener;

    public TemplesListAdapter(Context context, ArrayList<TempleModel> itemsData, IClickListener iClickListener) {
        this.itemsData = itemsData;
        this.iClickListener = iClickListener;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_simple_list_temple, null));
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {
        TempleModel templeModel = itemsData.get(position);
        viewHolder.txtTempleName.setText(templeModel.templeName);
        viewHolder.txtTempleAddress.setText(templeModel.line1 + ", " + templeModel.line2 + ", " + templeModel.city);
        viewHolder.txtRating.setText(templeModel.rating);
        viewHolder.txtTempleDistance.setText(templeModel.distance);
        viewHolder.imgFavourite.setSelected(templeModel.isFavourite);

        viewHolder.imgFavourite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (iClickListener != null) iClickListener.onClick(v, position);
            }
        });
        viewHolder.cardViewTemple.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (iClickListener != null) iClickListener.onClick(v, position);
            }
        });
        viewHolder.cardViewTemple.setOnLongClickListener(new View.OnLongClickListener() {
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

        public CustomTextView txtTempleAddress, txtRating, txtTempleDistance, txtTempleName;
        public ImageView imageViewTemple, imgFavourite;
        public CardView cardViewTemple;

        public ViewHolder(View itemLayoutView) {
            super(itemLayoutView);
            txtTempleName = itemLayoutView.findViewById(R.id.txtTempleName);
            txtTempleAddress = itemLayoutView.findViewById(R.id.txtTempleAddress);
            txtRating = itemLayoutView.findViewById(R.id.txtRating);
            txtTempleDistance = itemLayoutView.findViewById(R.id.txtTempleDistance);
            imageViewTemple = itemLayoutView.findViewById(R.id.imageViewTemple);
            imgFavourite = itemLayoutView.findViewById(R.id.imgFavourite);
            cardViewTemple = itemLayoutView.findViewById(R.id.cardViewTemple);
        }
    }

}

/*  txtTempleName = rootView.findViewById(R.id.txtTempleName);
            txtTempleAddress = rootView.findViewById(R.id.txtTempleAddress);
            txtRating = rootView.findViewById(R.id.txtRating);
            txtTempleDistance = rootView.findViewById(R.id.txtTempleDistance);
            imageViewTemple = rootView.findViewById(R.id.imageViewTemple);
            txtTempleName.setText(getDealModel().templeName);
            txtTempleAddress.setText(getDealModel().line1 + ", " + getDealModel().line2 + ", " + getDealModel().city);
            txtRating.setText(getDealModel().rating);
            txtTempleDistance.setText(getDealModel().distance);
            txtTempleName.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    final int DRAWABLE_RIGHT = 2;
//                    if (event.getAction() == MotionEvent.ACTION_UP) {
                    if (event.getRawX() >= (txtTempleName.getRight() - txtTempleName.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())) {
                        txtTempleName.setSelected(!txtTempleName.isSelected());
//                            if(txtTempleName.isSelected()){
//
//                            }
                        return true;
                    }
//                    }
                    return false;
                }
            });*/