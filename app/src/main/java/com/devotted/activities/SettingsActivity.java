package com.devotted.activities;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioGroup;

import com.devotted.BaseApplication;
import com.devotted.R;
import com.devotted.utils.DialogUtils;
import com.devotted.utils.LocalStorage;
import com.devotted.utils.StaticUtils;
import com.devotted.utils.views.CustomEditText;
import com.devotted.utils.views.CustomTextView;

public class SettingsActivity extends BaseActivity implements View.OnClickListener {

    private ImageView imgBack;
    private LinearLayout linLanguage, linPassword;
    private CustomEditText edtOldPassword, edtNewPassword, edtConfirmPassword;
    private CustomTextView txtChangeLanguage, txtChangePassword, txtProceed, txtConfirmChangePassword;
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

        linPassword = findViewById(R.id.linPassword);
        edtOldPassword = findViewById(R.id.edtOldPassword);
        edtNewPassword = findViewById(R.id.edtNewPassword);
        edtConfirmPassword = findViewById(R.id.edtConfirmPassword);
        txtConfirmChangePassword = findViewById(R.id.txtConfirmChangePassword);

        setListeners();

        switch (language) {
            case "tl":
                rgLanguages.check(R.id.rbTelugu);
                break;
            case "en":
                rgLanguages.check(R.id.rbEnglish);
                break;
            case "hi":
                rgLanguages.check(R.id.rbHindi);
                break;
            default:
                rgLanguages.check(R.id.rbEnglish);
                break;
        }
    }

    String language = LocalStorage.getInstance(SettingsActivity.this).getString(LocalStorage.PREF_LANGUAGE, "en");

    private void setListeners() {
        imgBack.setOnClickListener(this);
        txtChangePassword.setOnClickListener(this);
        txtChangeLanguage.setOnClickListener(this);
        txtProceed.setOnClickListener(this);
        txtConfirmChangePassword.setOnClickListener(this);
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
                linPassword.setVisibility(linPassword.getVisibility() == View.VISIBLE ? View.GONE : View.VISIBLE);
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
            case R.id.txtConfirmChangePassword:
                String message = checkValidations();
                if (TextUtils.isEmpty(message)) {
                    StaticUtils.showToast(this, getString(R.string.password_changed_successfully));
                } else {
                    StaticUtils.showToast(this, message);
                }
                break;
            default:
                break;
        }
    }

    private String checkValidations() {
        String oldPassword = edtOldPassword.getText().toString().trim();
        String newPassword = edtNewPassword.getText().toString().trim();
        String confirmPassword = edtConfirmPassword.getText().toString().trim();
        if (TextUtils.isEmpty(oldPassword)) {
            edtOldPassword.requestFocus();
            return getString(R.string.please_enter_your_old_password);
        }
//        if (oldPassword.length()<6||!oldPassword.equalsIgnoreCase(LocalStorage.getInstance(this).getString(LocalStorage.PREF_PASSWORD, ""))) {
//            edtOldPassword.requestFocus();
//            return "Old password is wrong";
//        }
        if (oldPassword.length() < 6) {
            edtOldPassword.requestFocus();
            return getString(R.string.old_password_is_wrong);
        }
        if (oldPassword.equalsIgnoreCase(newPassword)) {
            edtNewPassword.requestFocus();
            return getString(R.string.old_password_and_new_password_cannot_be_same);
        }
        if (TextUtils.isEmpty(newPassword)) {
            edtNewPassword.requestFocus();
            return getString(R.string.please_enter_new_password);
        }
        if (newPassword.length() < 6) {
            edtNewPassword.requestFocus();
            return getString(R.string.please_enter_a_valid_password);
        }
        if (TextUtils.isEmpty(confirmPassword)) {
            edtConfirmPassword.requestFocus();
            return getString(R.string.please_enter_confirm_password);
        }
        if (confirmPassword.length() < 6) {
            edtNewPassword.requestFocus();
            return getString(R.string.please_enter_a_valid_password);
        }
        if (!newPassword.equalsIgnoreCase(confirmPassword)) {
            edtNewPassword.requestFocus();
            return getString(R.string.new_password_and_confirm_password_should_match);
        }
        return "";
    }

}
