package com.devotted.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.devotted.R;
import com.devotted.activities.SplashActivity;
import com.devotted.adapters.SelectionVPAdapter;
import com.devotted.listeners.IClickListener;
import com.devotted.models.SelectionModel;
import com.devotted.utils.LocalStorage;
import com.devotted.utils.StaticUtils;
import com.devotted.utils.carousels.coverflow.CoverFlow;
import com.devotted.utils.carousels.coverflow.core.PagerContainer;
import com.devotted.utils.views.CustomTextView;

import java.util.ArrayList;

public class SelectionVPFragment extends BaseFragment implements IClickListener, View.OnClickListener {

    private View rootView;
    private SplashActivity splashActivity;
    private CustomTextView txtProceed;
    private ArrayList<SelectionModel> selectionModelArrayList;

    public SelectionVPFragment() {
    }

    public static SelectionVPFragment newInstance() {
        SelectionVPFragment categoryDetailsFragment = new SelectionVPFragment();
        categoryDetailsFragment.setArguments(new Bundle());
        return categoryDetailsFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        splashActivity = (SplashActivity) getActivity();
        selectionModelArrayList = new ArrayList<>();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_selection_vp, container, false);
        initComponents();
        return rootView;
    }

    SelectionVPAdapter selectionVPAdapter;

    private void initComponents() {
        setDummyData();
        txtProceed = rootView.findViewById(R.id.txtProceed);
        checkForValidation();
        PagerContainer container = rootView.findViewById(R.id.pager_container);
        ViewPager pager = container.getViewPager();
        selectionVPAdapter = new SelectionVPAdapter(splashActivity, selectionModelArrayList, this);
        pager.setAdapter(selectionVPAdapter);
        pager.setClipChildren(false);
        pager.setOffscreenPageLimit(5);

        new CoverFlow.Builder()
                .with(pager)
                .scale(0.3f)
                .pagerMargin(getResources().getDimensionPixelSize(R.dimen.pager_margin))
                .spaceSize(0f)
                .build();
        setListeners();
    }

    private void setDummyData() {
        SelectionModel spiritualModel = new SelectionModel("Spiritual", new String[]{"Provide the update of God / Temple", "Manage Temple's Activities, Events, etc.. Online ", "Help improving temple through Groups devotees, etc..."}, R.drawable.ic_spirtitual);
        SelectionModel religiousModel = new SelectionModel("Religious", new String[]{"Provide the update of God / Temple", "Manage Temple's Activities, Events, etc.. Online ", "Help improving temple through Groups devotees, etc..."}, R.drawable.ic_religious);
        selectionModelArrayList.add(spiritualModel);
        selectionModelArrayList.add(religiousModel);
    }

    private void setListeners() {
        txtProceed.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.txtProceed:
                LocalStorage.getInstance(splashActivity).putBoolean(LocalStorage.IS_PREFERENCE_SELECTED, true);
                navigateToHomeScreen();
                break;
            default:
                break;
        }
    }

    private void navigateToHomeScreen() {
        StaticUtils.showToast(splashActivity, getString(R.string.otp_verification_successfull));
    }

    @Override
    public void onClick(View view, int position) {
        switch (view.getId()) {
            case R.id.txtSelect:
                selectionModelArrayList.get(position).isSelected = !selectionModelArrayList.get(position).isSelected;
                selectionVPAdapter.notifyDataSetChanged();
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

    }

}
