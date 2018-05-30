package com.stone.card.library;

import android.database.DataSetObservable;
import android.database.DataSetObserver;
import android.graphics.Rect;
import android.view.View;

public abstract class CardAdapter {

    private final DataSetObservable mDataSetObservable = new DataSetObservable();

    public abstract int getLayoutId();

    public abstract int getCount();

    public abstract void bindView(View view, int index);

    public abstract Object getItem(int index);


    public Rect obtainDraggableArea(View view) {
        return null;
    }

    public void registerDataSetObserver(DataSetObserver observer) {
        mDataSetObservable.registerObserver(observer);
    }

    public void unregisterDataSetObserver(DataSetObserver observer) {
        mDataSetObservable.unregisterObserver(observer);
    }

    public void notifyDataSetChanged() {
        mDataSetObservable.notifyChanged();
    }

}
