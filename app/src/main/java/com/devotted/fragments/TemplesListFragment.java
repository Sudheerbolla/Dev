package com.devotted.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.devotted.R;
import com.devotted.activities.SearchActivity;
import com.devotted.activities.TempleDetailsActivity;
import com.devotted.adapters.TemplesListAdapter;
import com.devotted.listeners.IClickListener;
import com.devotted.models.TempleModel;

import java.util.ArrayList;

public class TemplesListFragment extends BaseFragment implements IClickListener {

    private View rootView;
    private SearchActivity searchActivity;
    private RecyclerView recyclerViewTemples;
    private TemplesListAdapter templesListAdapter;
    private ArrayList<TempleModel> templeModelArrayList;

    public TemplesListFragment() {
    }

    public static TemplesListFragment newInstance() {
        TemplesListFragment categoryDetailsFragment = new TemplesListFragment();
        categoryDetailsFragment.setArguments(new Bundle());
        return categoryDetailsFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        searchActivity = (SearchActivity) getActivity();
        templeModelArrayList = new ArrayList<>();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_temples_list, container, false);
        initComponents();
        return rootView;
    }

    private void initComponents() {
        setReferences();
        setListeners();
    }

    private void setReferences() {
        recyclerViewTemples = rootView.findViewById(R.id.recyclerViewTemples);
        setDummyData();
        setAdapter();
    }

    private void setDummyData() {
        for (int i = 1; i < 5; i++) {
            templeModelArrayList.add(new TempleModel(i));
        }
    }

    private void setAdapter() {
        recyclerViewTemples.setLayoutManager(new LinearLayoutManager(searchActivity));
        templesListAdapter = new TemplesListAdapter(searchActivity, templeModelArrayList, this);
        recyclerViewTemples.setAdapter(templesListAdapter);
    }

    private void setListeners() {

    }

    private void navigateToTempleDetailsScreen() {
    }

    @Override
    public void onClick(View view, int position) {
        switch (view.getId()) {
            case R.id.imgFavourite:
                templeModelArrayList.get(position).isFavourite = !templeModelArrayList.get(position).isFavourite;
                templesListAdapter.notifyItemChanged(position);
                break;
            case R.id.cardViewTemple:
                startActivity(new Intent(searchActivity, TempleDetailsActivity.class));
//                StaticUtils.showToast(searchActivity, "Navigate To Temple Details under development");
                break;
            default:
                break;
        }
    }

    @Override
    public void onLongClick(View view, int position) {
        switch (view.getId()) {
            case R.id.txtSelect:
                break;
        }
    }

}
