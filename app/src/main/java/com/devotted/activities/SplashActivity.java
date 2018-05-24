package com.devotted.activities;

import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

import com.devotted.BaseApplication;
import com.devotted.R;
import com.devotted.fragments.SplashFragment;
import com.devotted.utils.DialogUtils;
import com.devotted.utils.LocalStorage;
import com.devotted.utils.StaticUtils;

public class SplashActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= 21) {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        }
        setContentView(R.layout.activity_splash);
        checkInternetConnectionAndProceed();
    }

    private void checkInternetConnectionAndProceed() {
        StaticUtils.getHeightAndWidth(this);
        if (StaticUtils.checkInternetConnection(this)) {
            proceedWithFlow();
        } else {
            DialogUtils.showSimpleDialog(this, "", getString(R.string.no_internet_connection), getString(R.string.retry), getString(R.string.close), new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    checkInternetConnectionAndProceed();
                }
            }, new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    finish();
                }
            }, true, false);
        }
    }

    private void proceedWithFlow() {
        if (TextUtils.isEmpty(LocalStorage.getInstance(this).getString(LocalStorage.PREF_LANGUAGE, ""))) {
            DialogUtils.showLanguagePickerDialog(this, new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    BaseApplication.getInstance().changeLanguage(SplashActivity.this, LocalStorage.getInstance(SplashActivity.this).getString(LocalStorage.PREF_LANGUAGE, "en"));
                    recreate();
                }
            });
        } else {
            replaceFragment(SplashFragment.newInstance(), false, R.id.splashContainer);
        }
    }

}
