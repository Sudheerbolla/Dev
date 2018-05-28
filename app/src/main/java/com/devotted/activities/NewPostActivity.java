package com.devotted.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.devotted.R;
import com.devotted.utils.StaticUtils;
import com.devotted.utils.views.CustomTextView;

public class NewPostActivity extends BaseActivity implements View.OnClickListener {

    private ImageView imgBack;
    private CustomTextView txtPost;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        changeStatusBarColorToOrange();
        setContentView(R.layout.activity_new_post);
        initComponents();
    }

    private void initComponents() {
        setReferences();
    }

    private void setReferences() {
        imgBack = findViewById(R.id.imgBack);
        txtPost = findViewById(R.id.txtPost);
        setListeners();
    }

    private void setListeners() {
        imgBack.setOnClickListener(this);
        txtPost.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.imgBack:
                onBackPressed();
                break;
            case R.id.txtPost:
                StaticUtils.showToast(this, "Posted Successfully");
                finish();
                break;
            default:
                break;
        }
    }


}
