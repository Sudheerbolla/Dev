package com.devotted.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.devotted.R;
import com.devotted.activities.MainActivity;
import com.devotted.activities.NewPostActivity;
import com.devotted.adapters.TemplePostsAdapter;
import com.devotted.adapters.TemplesYouMayKnowAdapter;
import com.devotted.listeners.IClickListener;
import com.devotted.models.TempleModel;
import com.devotted.utils.views.CustomTextView;

import java.util.ArrayList;


public class UpdatesFragment extends BaseFragment implements IClickListener, View.OnClickListener {
    private TemplesYouMayKnowAdapter templesYouMayKnowAdapter;
    private TemplePostsAdapter templePostsAdapter;
    private RecyclerView recyclerViewTemples, recyclerViewUpdates;
    private View rootView;
    private MainActivity mainActivity;
    private CustomTextView txtWriteAnUpdate;
    private ArrayList<TempleModel> templeModelArrayList;
    private ArrayList<TempleModel> templePostsArrayList;

    public UpdatesFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mainActivity = (MainActivity) getActivity();
        templePostsArrayList = new ArrayList<>();
        templeModelArrayList = new ArrayList<>();
    }

    private void setDummyData() {
        for (int i = 1; i < 5; i++) {
            templeModelArrayList.add(new TempleModel(i));
            templePostsArrayList.add(new TempleModel(i));
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_updates, container, false);
        initComponents();
        return rootView;
    }

    private void initComponents() {
        recyclerViewTemples = rootView.findViewById(R.id.recyclerViewTemples);
        recyclerViewUpdates = rootView.findViewById(R.id.recyclerViewUpdates);
        txtWriteAnUpdate = rootView.findViewById(R.id.txtWriteAnUpdate);
        setDummyData();
        setTemplesYouMayKnowAdapter();
        setTemplePostsAdapter();
//        recyclerViewUpdates.setNestedScrollingEnabled(false);
        ViewCompat.setNestedScrollingEnabled(recyclerViewUpdates, false);
        txtWriteAnUpdate.setOnClickListener(this);
    }

    private void setTemplePostsAdapter() {
        recyclerViewUpdates.setLayoutManager(new LinearLayoutManager(mainActivity));
        templePostsAdapter = new TemplePostsAdapter(mainActivity, templePostsArrayList, this);
        recyclerViewUpdates.setAdapter(templePostsAdapter);
    }

    private void setTemplesYouMayKnowAdapter() {
        recyclerViewTemples.setLayoutManager(new LinearLayoutManager(mainActivity, LinearLayoutManager.HORIZONTAL, false));
        templesYouMayKnowAdapter = new TemplesYouMayKnowAdapter(mainActivity, templeModelArrayList, this);
        recyclerViewTemples.setAdapter(templesYouMayKnowAdapter);
    }

    @Override
    public void onClick(View view, int position) {

    }

    @Override
    public void onLongClick(View view, int position) {

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.txtWriteAnUpdate:
                startActivity(new Intent(mainActivity, NewPostActivity.class));
                break;
            default:
                break;
        }
    }
}
