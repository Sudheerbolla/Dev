package com.devotted.fragments;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.devotted.R;
import com.devotted.activities.SplashActivity;
import com.devotted.listeners.ISearchClickListener;
import com.devotted.receivers.SmsReceiver;
import com.devotted.utils.Constants;
import com.devotted.utils.RuntimePermissionUtils;
import com.devotted.utils.StaticUtils;
import com.devotted.utils.views.CustomEditText;
import com.devotted.utils.views.CustomTextView;

public class VerificationCodeFragment extends BaseFragment implements View.OnClickListener {

    private View rootView;
    private SplashActivity splashActivity;
    private CustomTextView txtConfirmOtp, txtBack, txtResendOtp;
    private CustomEditText edtOTP;

    public VerificationCodeFragment() {
    }

    public static VerificationCodeFragment newInstance(Bundle bundle) {
        VerificationCodeFragment categoryDetailsFragment = new VerificationCodeFragment();
        categoryDetailsFragment.setArguments(bundle);
        return categoryDetailsFragment;
    }

    public static VerificationCodeFragment newInstance() {
        VerificationCodeFragment categoryDetailsFragment = new VerificationCodeFragment();
        categoryDetailsFragment.setArguments(new Bundle());
        return categoryDetailsFragment;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_verification_code, container, false);
        setSmsListener();
        return rootView;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        splashActivity = (SplashActivity) getActivity();
    }

    private void setSmsListener() {
        initComponents();
        if (RuntimePermissionUtils.checkPermission(getActivity(), Manifest.permission.RECEIVE_SMS) != PackageManager.PERMISSION_GRANTED) {
            requestForPermission(Manifest.permission.RECEIVE_SMS, Constants.READ_SMS_CODE);
        } else {
            setSmSBindListener();
        }
    }

    private void setSmSBindListener() {
        SmsReceiver.bindListener(new ISearchClickListener() {
            @Override
            public void onClick(String messageText) {
                String otp;
                try {
                    otp = StaticUtils.parseCode(messageText);
                } catch (Exception e) {
                    e.printStackTrace();
                    otp = null;
                }
                if (otp != null) fillOtpinEdittexts(otp);
            }
        });
    }

    private void fillOtpinEdittexts(String verificationCode) {
        if (!TextUtils.isEmpty(verificationCode)) {
            edtOTP.setText(verificationCode);
        } else {
            StaticUtils.showToast(splashActivity, getString(R.string.failed_to_fetch_otp_please_enter_manually));
        }
    }

    private void initComponents() {
        setReferences();
        checkForEnablingConfirmButton("");
        setListeners();
    }

    private void setReferences() {
        edtOTP = rootView.findViewById(R.id.edtOTP);
        txtBack = rootView.findViewById(R.id.txtBack);
        txtConfirmOtp = rootView.findViewById(R.id.txtConfirmOtp);
        txtResendOtp = rootView.findViewById(R.id.txtResendOtp);
    }

    private void setListeners() {
        txtResendOtp.setOnClickListener(this);
        txtConfirmOtp.setOnClickListener(this);
        txtBack.setOnClickListener(this);
        edtOTP.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                checkForEnablingConfirmButton(charSequence.toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    private void checkForEnablingConfirmButton(String string) {
        if (!TextUtils.isEmpty(string) && string.length() == 4) {
            txtConfirmOtp.setEnabled(true);
            txtConfirmOtp.setClickable(true);
            txtConfirmOtp.setAlpha(1.0f);
        } else {
            txtConfirmOtp.setEnabled(false);
            txtConfirmOtp.setClickable(false);
            txtConfirmOtp.setAlpha(0.5f);
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.txtResendOtp:
                StaticUtils.showToast(splashActivity, getString(R.string.otp_resent_successfully));
                break;
            case R.id.txtConfirmOtp:
                String message = checkValidations();
                if (TextUtils.isEmpty(message)) {
                    navigateToHomeScreen();
                } else {
                    StaticUtils.showToast(splashActivity, message);
                }
                break;
            case R.id.txtBack:
//                splashActivity.replaceFragment(LandingFragment.newInstance());
                splashActivity.clearBackStackCompletely();
//                splashActivity.popBackStack();
                break;
            default:
                break;
        }
    }

    private void navigateToHomeScreen() {
        StaticUtils.showToast(splashActivity, getString(R.string.otp_verification_successfull));
        splashActivity.replaceFragment(SelectionFragment.newInstance(false));
    }

    private String checkValidations() {
        String message = "";
        String password = edtOTP.getText().toString().trim();
        if (TextUtils.isEmpty(password)) {
            edtOTP.requestFocus();
            return getString(R.string.please_enter_otp_sent_to_your_registered_mobile_number);
        }
        if (password.length() != 4) {
            edtOTP.requestFocus();
            return getString(R.string.please_enter_a_valid_otp);
        }
        return message;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case Constants.READ_SMS_CODE:
                if (StaticUtils.isAllPermissionsGranted(grantResults)) {
                    setSmSBindListener();
                } else {
                    StaticUtils.showToast(splashActivity, getString(R.string.please_enter_otp_manually_since_you_have_denied_permission_for_reading_sms));
                }
                break;
            default:
                break;
        }
    }


}
