package com.devotted.activities;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.devotted.R;
import com.devotted.adapters.GodsListAdapter;
import com.devotted.listeners.IClickListener;
import com.devotted.models.GodModel;
import com.devotted.utils.StaticUtils;

import java.util.ArrayList;

public class SelectGodsActivity extends BaseActivity implements View.OnClickListener, IClickListener {

    private ImageView imgBack, imgSearch;
    private GodsListAdapter godsListAdapter;
    private ArrayList<GodModel> godModelArrayList;
    private RecyclerView recyclerViewGods;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        changeStatusBarColorToOrange();
        setContentView(R.layout.activity_select_god);
        initComponents();
    }

    private void initComponents() {
        godModelArrayList = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            godModelArrayList.add(new GodModel("Lord Krishna"));
        }
        setReferences();
    }

    private void setReferences() {
        imgBack = findViewById(R.id.imgBack);
        imgSearch = findViewById(R.id.imgSearch);
        recyclerViewGods = findViewById(R.id.recyclerViewGods);

        recyclerViewGods.setLayoutManager(new LinearLayoutManager(this));
        godsListAdapter = new GodsListAdapter(this, godModelArrayList, this);
        recyclerViewGods.setAdapter(godsListAdapter);

        setListeners();
    }

    private void setListeners() {
        imgBack.setOnClickListener(this);
        imgSearch.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.imgBack:
                onBackPressed();
                break;
            case R.id.imgShare:
                StaticUtils.showToast(SelectGodsActivity.this, "Module Under Development");
                break;
            default:
                break;
        }
    }

    @Override
    public void onClick(View view, int position) {
        switch (view.getId()) {
            case R.id.checkBox:
                godModelArrayList.get(position).isSelected = !godModelArrayList.get(position).isSelected;
                break;
            default:
                break;
        }
    }

    @Override
    public void onLongClick(View view, int position) {

    }

}
