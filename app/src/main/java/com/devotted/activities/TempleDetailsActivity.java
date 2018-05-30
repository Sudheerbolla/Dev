package com.devotted.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.devotted.R;
import com.devotted.fragments.TempleDetailsFragment;
import com.devotted.utils.DialogUtils;
import com.devotted.utils.LocalStorage;
import com.devotted.utils.ShareUtils;

public class TempleDetailsActivity extends BaseActivity implements View.OnClickListener {

    private ImageView imgBack, imgShare;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        changeStatusBarColorToOrange();
        setContentView(R.layout.activity_temple_details);
        initComponents();
    }

    private void initComponents() {
        setReferences();
        replaceFragment(TempleDetailsFragment.newInstance(), false, R.id.templeDetailsFrame);
        if (!LocalStorage.getInstance(this).getBoolean(LocalStorage.PREF_IS_I_BELONG_HERE_DONE, true)) {
            DialogUtils.showIBelongHereDialog(this, new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    LocalStorage.getInstance(TempleDetailsActivity.this).putBoolean(LocalStorage.PREF_IS_I_BELONG_HERE_DONE, true);
                }
            });
        }
    }

    private void setReferences() {
        imgBack = findViewById(R.id.imgBack);
        imgShare = findViewById(R.id.imgShare);
        setListeners();
    }

    private void setListeners() {
        imgBack.setOnClickListener(this);
        imgShare.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.imgBack:
                onBackPressed();
                break;
            case R.id.imgShare:
                ShareUtils.shareViaIntent(this, getString(R.string.temple_details), getString(R.string.sharing_temple_details));
                break;
            default:
                break;
        }
    }

}
