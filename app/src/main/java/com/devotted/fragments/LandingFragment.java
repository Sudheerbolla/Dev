package com.devotted.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.devotted.R;
import com.devotted.activities.MainActivity;
import com.devotted.activities.SplashActivity;
import com.devotted.utils.LocalStorage;
import com.devotted.utils.StaticUtils;
import com.devotted.utils.views.CustomTextView;

public class LandingFragment extends BaseFragment implements View.OnClickListener {

    private View rootView;
    private SplashActivity splashActivity;
    private CustomTextView txtLogin, txtRegister, txtGuestUser;
    private ImageView imgFb, imgGoogle;

    public LandingFragment() {
    }

    public static LandingFragment newInstance() {
        LandingFragment categoryDetailsFragment = new LandingFragment();
        categoryDetailsFragment.setArguments(new Bundle());
        return categoryDetailsFragment;
    }

    public static LandingFragment newInstance(Bundle bundle) {
        LandingFragment categoryDetailsFragment = new LandingFragment();
        categoryDetailsFragment.setArguments(bundle);
        return categoryDetailsFragment;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_landing, container, false);
        initComponents();
        return rootView;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        splashActivity = (SplashActivity) getActivity();
        splashActivity.changeStatusBarColorToOrange();
    }

    private void initComponents() {
        setReferences();
        setListeners();
    }

    private void setReferences() {
        txtLogin = rootView.findViewById(R.id.txtLogin);
        txtRegister = rootView.findViewById(R.id.txtRegister);
        txtGuestUser = rootView.findViewById(R.id.txtGuestUser);
        imgFb = rootView.findViewById(R.id.imgFb);
        imgGoogle = rootView.findViewById(R.id.imgGoogle);

    }

    private void setListeners() {
        txtLogin.setOnClickListener(this);
        txtRegister.setOnClickListener(this);
        txtGuestUser.setOnClickListener(this);
        imgFb.setOnClickListener(this);
        imgGoogle.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.txtLogin:
                splashActivity.replaceFragment(LoginFragment.newInstance(), true, R.id.splashContainer);
                break;
            case R.id.txtRegister:
//                splashActivity.replaceFragment(RegistrationFragment.newInstance(), true, R.id.splashContainer);
                splashActivity.replaceFragment(SelectionFragment.newInstance(false), true, R.id.splashContainer);
                break;
            case R.id.txtGuestUser:
                LocalStorage.getInstance(splashActivity).putBoolean(LocalStorage.IS_GUEST_USER, true);
                startActivity(new Intent(splashActivity, MainActivity.class));
                splashActivity.finishAffinity();
                break;
            case R.id.imgFb:
                StaticUtils.showToast(splashActivity, getString(R.string.module_under_development));
                break;
            case R.id.imgGoogle:
                StaticUtils.showToast(splashActivity, getString(R.string.module_under_development));
                break;
            default:
                break;
        }
    }

}
