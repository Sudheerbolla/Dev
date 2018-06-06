package com.devotted.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.devotted.R;
import com.devotted.fragments.GroupsListFragment;
import com.devotted.utils.views.CustomTextView;

public class TempleGroupsActivity extends BaseActivity implements View.OnClickListener {

    private ImageView imgBack;
    private CustomTextView txtGroupsHeading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        changeStatusBarColorToOrange();
        setContentView(R.layout.activity_groups);
        initComponents();
    }

    private void initComponents() {
        setReferences();
        replaceFragmentWithOutAnimation(GroupsListFragment.newInstance(), false, R.id.groupsContainer);
    }

    private void setReferences() {
        imgBack = findViewById(R.id.imgBack);
        txtGroupsHeading = findViewById(R.id.txtGroupsHeading);

        setListeners();
    }

    public void setTopBarText(String heading) {
        txtGroupsHeading.setHint(heading);
    }

    private void setListeners() {
        imgBack.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.imgBack:
                onBackPressed();
                break;
            default:
                break;
        }
    }

}
