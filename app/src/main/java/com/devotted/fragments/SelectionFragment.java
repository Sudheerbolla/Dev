package com.devotted.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.devotted.R;
import com.devotted.activities.MainActivity;
import com.devotted.activities.SplashActivity;
import com.devotted.adapters.SelectionAdapter;
import com.devotted.listeners.IClickListener;
import com.devotted.models.SelectionModel;
import com.devotted.utils.LocalStorage;
import com.devotted.utils.views.CustomTextView;

import java.util.ArrayList;

public class SelectionFragment extends BaseFragment implements View.OnClickListener, IClickListener {

    private View rootView;
    private SplashActivity splashActivity;
    private CustomTextView txtProceed, txtHeading, txtUserName;
    private RecyclerView recyclerViewSelection;
    private SelectionAdapter selectionAdapter;
    private ArrayList<SelectionModel> selectionModelArrayList;
    private boolean isUserType;
    private int selectedPosition = 0;

    public SelectionFragment() {
    }

    public static SelectionFragment newInstance(boolean isUserType) {
        SelectionFragment categoryDetailsFragment = new SelectionFragment();
        Bundle bundle = new Bundle();
        bundle.putBoolean("isUserType", isUserType);
        categoryDetailsFragment.setArguments(bundle);
        return categoryDetailsFragment;
    }

    public static SelectionFragment newInstance() {
        SelectionFragment categoryDetailsFragment = new SelectionFragment();
        categoryDetailsFragment.setArguments(new Bundle());
        return categoryDetailsFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        splashActivity = (SplashActivity) getActivity();
        selectionModelArrayList = new ArrayList<>();
        isUserType = getArguments().getBoolean("isUserType");
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_selection, container, false);
        initComponents();
        return rootView;
    }

    private void initComponents() {
        setReferences();
        setListeners();
    }

    private void setReferences() {
        txtProceed = rootView.findViewById(R.id.txtProceed);
        txtHeading = rootView.findViewById(R.id.txtHeading);
        txtUserName = rootView.findViewById(R.id.txtUserName);
        recyclerViewSelection = rootView.findViewById(R.id.recyclerViewSelection);
        setDummyData();
        checkForValidation();
        setAdapter();
    }

    private void setDummyData() {
        txtUserName.setText(getString(R.string.namaste_user) + " " + LocalStorage.getInstance(splashActivity).getString(LocalStorage.PREF_USER_NAME, ""));
        selectionModelArrayList.clear();
        if (isUserType) {
            txtHeading.setText("");
            // need to change
            SelectionModel spiritualModel = new SelectionModel(getString(R.string.devotee),
                    new String[]{getString(R.string.services_i_receive),
                            getString(R.string.user_type1_line1),
                            getString(R.string.user_type1_line2),
                            getString(R.string.user_type1_line3),
                            getString(R.string.user_type1_line4)
                    },
                    R.drawable.ic_lotus_position);
            SelectionModel religiousModel = new SelectionModel(getString(R.string.temple_member),
                    new String[]{getString(R.string.services_we_provide),
                            getString(R.string.user_type2_line1),
                            getString(R.string.user_type2_line2),
                            getString(R.string.user_type2_line3),
                            getString(R.string.user_type2_line4),
                            getString(R.string.user_type2_line5)
                    }, R.drawable.ic_indian_tm);
            selectionModelArrayList.add(spiritualModel);
            selectionModelArrayList.add(religiousModel);
        } else {
            SelectionModel spiritualModel = new SelectionModel(getString(R.string.spiritual), new String[]{
                    getString(R.string.spiritual_features),
                    getString(R.string.interest_type1_line1),
                    getString(R.string.interest_type1_line2),
                    getString(R.string.interest_type1_line3),
                    getString(R.string.interest_type1_line4)
            }, R.drawable.ic_spiritual);
            SelectionModel religiousModel = new SelectionModel(getString(R.string.religious), new String[]{
                    getString(R.string.religious_features),
                    getString(R.string.interest_type2_line1),
                    getString(R.string.interest_type2_line2),
                    getString(R.string.interest_type2_line3),
                    getString(R.string.interest_type2_line4),
                    getString(R.string.interest_type2_line5)
            }, R.drawable.ic_religious);
            selectionModelArrayList.add(spiritualModel);
            selectionModelArrayList.add(religiousModel);
        }
    }

    private void setAdapter() {
        recyclerViewSelection.setLayoutManager(new LinearLayoutManager(splashActivity, LinearLayoutManager.HORIZONTAL, false));
        selectionAdapter = new SelectionAdapter(splashActivity, selectionModelArrayList, this, isUserType);
        recyclerViewSelection.setAdapter(selectionAdapter);
    }

    private void setListeners() {
        txtProceed.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.txtProceed:
                navigateToHomeScreen();
                break;
            default:
                break;
        }
    }

    private void navigateToHomeScreen() {
        if (isUserType) {
            LocalStorage.getInstance(splashActivity).putBoolean(LocalStorage.IS_LOGGED_IN_ALREADY, true);
            LocalStorage.getInstance(splashActivity).putBoolean(LocalStorage.IS_PREFERENCE_SELECTED, true);
            startActivity(new Intent(splashActivity, MainActivity.class));
            splashActivity.finishAffinity();
        } else {
            boolean isOnlySpiritual = true;
            for (int i = 0; i < selectionModelArrayList.size(); i++) {
                if (selectionModelArrayList.get(i).isSelected) {
                    if (selectionModelArrayList.get(i).type.equalsIgnoreCase(getString(R.string.religious))) {
                        isOnlySpiritual = false;
                    }
                }
            }
            if (isOnlySpiritual) {
                LocalStorage.getInstance(splashActivity).putBoolean(LocalStorage.IS_LOGGED_IN_ALREADY, true);
                startActivity(new Intent(splashActivity, MainActivity.class));
                splashActivity.finishAffinity();
            } else {
                splashActivity.replaceFragment(SelectionFragment.newInstance(true));
            }
        }
    }

    @Override
    public void onClick(View view, int position) {
        switch (view.getId()) {
            case R.id.txtSelect:
            case R.id.linSelect:
                selectedPosition = position;
                if (isUserType) {
                    for (int i = 0; i < selectionModelArrayList.size(); i++) {
                        if (i != position) {
                            SelectionModel selectionModel = selectionModelArrayList.get(i);
                            selectionModel.isSelected = false;
                        }
                    }
                    selectionModelArrayList.get(position).isSelected = !selectionModelArrayList.get(position).isSelected;
                    selectionAdapter.notifyDataSetChanged();
                } else {
                    selectionModelArrayList.get(position).isSelected = !selectionModelArrayList.get(position).isSelected;
                    selectionAdapter.notifyItemChanged(position);
                }
                checkForValidation();
                break;
        }
    }

    private void checkForValidation() {
        boolean isValid = false;
        for (SelectionModel selectionModel : selectionModelArrayList) {
            if (selectionModel.isSelected) {
                isValid = true;
                break;
            }
        }
        if (isValid) {
            txtProceed.setEnabled(true);
            txtProceed.setClickable(true);
            txtProceed.setAlpha(1.0f);
        } else {
            txtProceed.setEnabled(false);
            txtProceed.setClickable(false);
            txtProceed.setAlpha(0.5f);
        }
    }

    @Override
    public void onLongClick(View view, int position) {
        switch (view.getId()) {
            case R.id.txtSelect:
                break;
        }
    }

}
