package com.devotted.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.devotted.R;
import com.devotted.activities.TempleGroupsActivity;
import com.devotted.adapters.GroupsListAdapter;
import com.devotted.listeners.IClickListener;
import com.devotted.models.TempleModel;

import java.util.ArrayList;

public class GroupsListFragment extends BaseFragment implements IClickListener {

    private View rootView;
    private TempleGroupsActivity templeGroupsActivity;
    private RecyclerView recyclerViewTemples;
    private GroupsListAdapter groupsListAdapter;
    private ArrayList<TempleModel> templeModelArrayList;

    public GroupsListFragment() {
    }

    public static GroupsListFragment newInstance() {
        GroupsListFragment categoryDetailsFragment = new GroupsListFragment();
        categoryDetailsFragment.setArguments(new Bundle());
        return categoryDetailsFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        templeGroupsActivity = (TempleGroupsActivity) getActivity();
        templeModelArrayList = new ArrayList<>();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_temples_list, container, false);
        initComponents();
        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        if (templeModelArrayList != null) {
            templeModelArrayList.clear();
            setDummyData();
        }
        templeGroupsActivity.setTopBarText(getString(R.string.my_temple_groups));
    }

    private void initComponents() {
        setReferences();
        setListeners();
    }

    private void setReferences() {
        recyclerViewTemples = rootView.findViewById(R.id.recyclerViewTemples);
        setAdapter();
    }

    private void setDummyData() {
        for (int i = 0; i < 3; i++) {
            templeModelArrayList.add(new TempleModel(templeGroupsActivity,i));
        }
        if (groupsListAdapter != null) groupsListAdapter.notifyDataSetChanged();
    }

    private void setAdapter() {
        recyclerViewTemples.setLayoutManager(new LinearLayoutManager(templeGroupsActivity));
        groupsListAdapter = new GroupsListAdapter(templeGroupsActivity, templeModelArrayList, this);
        recyclerViewTemples.setAdapter(groupsListAdapter);
    }

    private void setListeners() {

    }

    @Override
    public void onClick(View view, int position) {
        templeGroupsActivity.replaceFragment(GroupDetailsFragment.newInstance(), true, R.id.groupsContainer);
    }

    @Override
    public void onLongClick(View view, int position) {
//        switch (view.getId()) {
//            case R.id.txtSelect:
//                break;
//        }
    }

}
