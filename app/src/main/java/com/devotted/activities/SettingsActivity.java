package com.devotted.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioGroup;

import com.devotted.BaseApplication;
import com.devotted.R;
import com.devotted.utils.DialogUtils;
import com.devotted.utils.LocalStorage;
import com.devotted.utils.views.CustomTextView;

public class SettingsActivity extends BaseActivity implements View.OnClickListener {

    private ImageView imgBack;
    private LinearLayout linLanguage;
    private CustomTextView txtChangeLanguage, txtChangePassword, txtProceed;
    private RadioGroup rgLanguages;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        changeStatusBarColorToOrange();
        setContentView(R.layout.activity_settings);
        initComponents();
    }

    private void initComponents() {
        setReferences();
    }

    private void setReferences() {
        imgBack = findViewById(R.id.imgBack);
        linLanguage = findViewById(R.id.linLanguage);
        txtChangeLanguage = findViewById(R.id.txtChangeLanguage);
        txtChangePassword = findViewById(R.id.txtChangePassword);
        rgLanguages = findViewById(R.id.rgLanguages);
        txtProceed = findViewById(R.id.txtProceed);
        setListeners();
    }

    String language = LocalStorage.getInstance(SettingsActivity.this).getString(LocalStorage.PREF_LANGUAGE, "en");

    private void setListeners() {
        imgBack.setOnClickListener(this);
        txtChangePassword.setOnClickListener(this);
        txtChangeLanguage.setOnClickListener(this);
        txtProceed.setOnClickListener(this);
        rgLanguages.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                switch (i) {
                    case R.id.rbEnglish:
                        language = "en";
                        txtProceed.setText("Proceed");
                        break;
                    case R.id.rbTelugu:
                        language = "tl";
                        txtProceed.setText("ముందుకు");
                        break;
                    case R.id.rbHindi:
                        language = "hi";
                        txtProceed.setText("बढ़ना");
                        break;
                    default:
                        language = "en";
                        txtProceed.setText("Proceed");
                        break;
                }
            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.imgBack:
                onBackPressed();
                break;
            case R.id.txtChangeLanguage:
                linLanguage.setVisibility(linLanguage.getVisibility() == View.VISIBLE ? View.GONE : View.VISIBLE);
                break;
            case R.id.txtChangePassword:
                break;
            case R.id.txtProceed:
                DialogUtils.showSimpleDialog(SettingsActivity.this, getString(R.string.change_language),
                        getString(R.string.please_restart_the_application_for_the_new_language_to_be_reflected), getString(R.string.restart), getString(R.string.cancel), new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                LocalStorage.getInstance(SettingsActivity.this).putString(LocalStorage.PREF_LANGUAGE, language);
                                BaseApplication.getInstance().changeLanguage(SettingsActivity.this,
                                        LocalStorage.getInstance(SettingsActivity.this).getString(LocalStorage.PREF_LANGUAGE, "en"));
                                Intent i = getPackageManager().getLaunchIntentForPackage(getBaseContext().getPackageName());
                                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                startActivity(i);
                            }
                        }, null, false, false);
                break;
            default:
                break;
        }
    }

}
