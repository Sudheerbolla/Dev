package com.devotted.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.Spinner;

import com.devotted.R;
import com.devotted.activities.MainActivity;
import com.devotted.activities.SplashActivity;
import com.devotted.utils.DialogUtils;
import com.devotted.utils.LocalStorage;
import com.devotted.utils.StaticUtils;
import com.devotted.utils.views.CustomEditText;
import com.devotted.utils.views.CustomTextView;

public class LoginFragment extends BaseFragment implements View.OnClickListener, CompoundButton.OnCheckedChangeListener {

    private View rootView;
    private SplashActivity splashActivity;
    private CustomTextView txtForgotPassword, txtLogin;
    private CheckBox cbRememberMe;
    private CustomEditText edtPassword, edtEmailAddress, edtMobileNumber;
    private RadioButton radioEmail, radioMobile;
    private LinearLayout linEmail, linMobile;
    private Spinner spinnerCountry;
    private String[] countryCodesArray;
    private ImageView imgBack;

    public LoginFragment() {
    }

    public static LoginFragment newInstance() {
        LoginFragment categoryDetailsFragment = new LoginFragment();
        categoryDetailsFragment.setArguments(new Bundle());
        return categoryDetailsFragment;
    }

    public static LoginFragment newInstance(Bundle bundle) {
        LoginFragment categoryDetailsFragment = new LoginFragment();
        categoryDetailsFragment.setArguments(bundle);
        return categoryDetailsFragment;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_login, container, false);
        initComponents();
        return rootView;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        splashActivity = (SplashActivity) getActivity();
    }

    private void initComponents() {
        countryCodesArray = new String[]{"+91", "+1"};
        setReferences();
        setAdapter();
        setListeners();
    }

    private void setAdapter() {
        ArrayAdapter spinnerAdapter = new ArrayAdapter(splashActivity, android.R.layout.simple_spinner_item, countryCodesArray);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCountry.setAdapter(spinnerAdapter);
    }

    private void setReferences() {
        txtForgotPassword = rootView.findViewById(R.id.txtForgotPassword);
        cbRememberMe = rootView.findViewById(R.id.cbRememberMe);
        edtEmailAddress = rootView.findViewById(R.id.edtEmailAddress);
        edtMobileNumber = rootView.findViewById(R.id.edtMobileNumber);
        edtPassword = rootView.findViewById(R.id.edtPassword);
        txtLogin = rootView.findViewById(R.id.txtLogin);
        radioEmail = rootView.findViewById(R.id.radioEmail);
        radioMobile = rootView.findViewById(R.id.radioMobile);
        linEmail = rootView.findViewById(R.id.linEmail);
        linMobile = rootView.findViewById(R.id.linMobile);
        spinnerCountry = rootView.findViewById(R.id.spinnerCountry);
        imgBack = rootView.findViewById(R.id.imgBack);
    }

    private void setListeners() {
        txtLogin.setOnClickListener(this);
        txtForgotPassword.setOnClickListener(this);
        radioEmail.setOnCheckedChangeListener(this);
        radioMobile.setOnCheckedChangeListener(this);
        imgBack.setOnClickListener(this);
        showEmail(true);
        spinnerCountry.setSelection(0, true);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.txtLogin:
                String message = checkValidations();
                if (!TextUtils.isEmpty(message)) {
                    StaticUtils.showToast(splashActivity, message);
                } else {
                    StaticUtils.showToast(splashActivity, getString(R.string.login_successful));
                    LocalStorage.getInstance(splashActivity).putBoolean(LocalStorage.IS_LOGGED_IN_ALREADY, true);
                    startActivity(new Intent(splashActivity, MainActivity.class));
                    splashActivity.finishAffinity();
                }
                break;
            case R.id.txtForgotPassword:
                showDialog();
                break;
            case R.id.imgBack:
                splashActivity.popBackStack();
                break;
            default:
                break;
        }
    }

    private String checkValidations() {
        String message = "";
        if (linEmail.getVisibility() == View.VISIBLE) {
            String emailId = edtEmailAddress.getText().toString().trim();
            if (TextUtils.isEmpty(emailId)) {
                edtEmailAddress.requestFocus();
                return getString(R.string.please_enter_email_address);
            }
            if (!StaticUtils.isValidEmail(emailId)) {
                edtEmailAddress.requestFocus();
                return getString(R.string.please_enter_a_valid_email_address);
            }
        } else if (linMobile.getVisibility() == View.VISIBLE) {
            String mobileNumber = edtMobileNumber.getText().toString().trim();
            if (TextUtils.isEmpty(mobileNumber)) {
                edtMobileNumber.requestFocus();
                return getString(R.string.please_enter_mobile_number);
            }
            if (mobileNumber.length() < 9) {
                edtMobileNumber.requestFocus();
                return getString(R.string.please_enter_a_valid_mobile_number);
            }
        }
        String password = edtPassword.getText().toString().trim();
        if (TextUtils.isEmpty(password)) {
            edtPassword.requestFocus();
            return getString(R.string.please_enter_password);
        }
        if (password.length() < 6) {
            edtPassword.requestFocus();
            return getString(R.string.please_enter_a_valid_password);
        }
        return message;
    }

    private void showDialog() {
        DialogUtils.showForgotPasswordDialog(splashActivity, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String emailAddress = (String) view.getTag();
                StaticUtils.showToast(splashActivity, "Entered email address is : " + emailAddress);
            }
        });
    }

    private void navigateToMapScreen() {
//        startActivity(new Intent(splashActivity, MapsActivity.class));
//        splashActivity.finishAffinity();
    }

    private void showEmail(boolean showEmail) {
        if (showEmail) {
            linEmail.setVisibility(View.VISIBLE);
            linMobile.setVisibility(View.GONE);
        } else {
            linEmail.setVisibility(View.GONE);
            linMobile.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
        if (compoundButton.getId() == R.id.radioEmail) {
            showEmail(false);
        } else if (compoundButton.getId() == R.id.radioMobile) {
            showEmail(true);
        }
    }

}
