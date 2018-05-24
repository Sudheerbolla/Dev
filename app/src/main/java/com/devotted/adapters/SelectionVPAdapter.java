package com.devotted.adapters;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.devotted.R;
import com.devotted.listeners.IClickListener;
import com.devotted.models.SelectionModel;
import com.devotted.utils.views.CustomTextView;

import java.util.ArrayList;

public class SelectionVPAdapter extends PagerAdapter {

    private ArrayList<SelectionModel> itemsData;
    private Context context;
    private IClickListener iClickListener;

    public SelectionVPAdapter(Context context, ArrayList<SelectionModel> itemsData, IClickListener iClickListener) {
        this.itemsData = itemsData;
        this.iClickListener = iClickListener;
        this.context = context;
    }

    @Override
    public Object instantiateItem(ViewGroup container, final int position) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_selection, null);
        ImageView imgType = view.findViewById(R.id.imgType);
        CustomTextView txtSelect = view.findViewById(R.id.txtSelect);
        CustomTextView txtDescription1 = view.findViewById(R.id.txtDescription1);
        CustomTextView txtDescription2 = view.findViewById(R.id.txtDescription2);
        CustomTextView txtDescription3 = view.findViewById(R.id.txtDescription3);
        SelectionModel selectionModel = itemsData.get(position);
        imgType.setImageResource(selectionModel.icon);
        txtSelect.setText("I Choose " + selectionModel.type);
        txtDescription1.setText(selectionModel.description[0]);
        txtDescription2.setText(selectionModel.description[1]);
        txtDescription3.setText(selectionModel.description[2]);

        if (selectionModel.isSelected) {
            txtSelect.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_tick_mark, 0, 0, 0);
        } else {
            txtSelect.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
        }
        txtSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (iClickListener != null) iClickListener.onClick(v, position);
            }
        });
        txtSelect.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if (iClickListener != null) iClickListener.onLongClick(v, position);
                return false;
            }
        });

        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override
    public int getCount() {
        return itemsData.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return (view == object);
    }

}
