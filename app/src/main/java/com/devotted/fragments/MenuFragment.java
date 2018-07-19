package com.devotted.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.devotted.R;
import com.devotted.activities.MainActivity;
import com.devotted.activities.SettingsActivity;
import com.devotted.activities.SplashActivity;
import com.devotted.utils.DialogUtils;
import com.devotted.utils.LocalStorage;
import com.devotted.utils.views.CustomTextView;

public class MenuFragment extends BaseFragment implements View.OnClickListener {

    private MainActivity mainActivity;
    private View rootView;
    private CustomTextView txtSettings, txtWriteToUs, txtHelp, txtProfile, txtName, txtLogout;

    public MenuFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mainActivity = (MainActivity) getActivity();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_menu, container, false);
        initComponents();
        return rootView;
    }

    private void initComponents() {
        txtProfile = rootView.findViewById(R.id.txtProfile);
        txtLogout = rootView.findViewById(R.id.txtLogout);
        txtHelp = rootView.findViewById(R.id.txtHelp);
        txtWriteToUs = rootView.findViewById(R.id.txtWriteToUs);
        txtSettings = rootView.findViewById(R.id.txtSettings);
        txtName = rootView.findViewById(R.id.txtName);

        setListeners();
        txtName.setText(LocalStorage.getInstance(mainActivity).getString(LocalStorage.PREF_USER_NAME, ""));
    }

    private void setListeners() {
        txtSettings.setOnClickListener(this);
        txtHelp.setOnClickListener(this);
        txtProfile.setOnClickListener(this);
        txtLogout.setOnClickListener(this);
        txtWriteToUs.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.txtSettings:
                mainActivity.startActivity(new Intent(mainActivity, SettingsActivity.class));
                break;
            case R.id.txtProfile:
                break;
            case R.id.txtHelp:
                break;
            case R.id.txtWriteToUs:
                break;
            case R.id.txtLogout:
                DialogUtils.showSimpleDialog(mainActivity, "", "Are you sure you want to Logout?", "Logout", "Cancel", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        LocalStorage.getInstance(mainActivity).clearLocalStorage();
                        startActivity(new Intent(mainActivity, SplashActivity.class));
                        mainActivity.finishAffinity();
                    }
                }, null, false, false);
                break;
            default:
                break;
        }
    }
}
