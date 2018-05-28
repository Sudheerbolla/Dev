package com.devotted.fragments;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetDialog;
import android.support.design.widget.BottomSheetDialogFragment;
import android.view.View;
import android.widget.RelativeLayout;

import com.devotted.BaseApplication;
import com.devotted.R;
import com.devotted.activities.SearchActivity;
import com.devotted.activities.SelectGodsActivity;
import com.devotted.utils.views.CustomTextView;

public class FilterBottomSheetFragment extends BottomSheetDialogFragment implements View.OnClickListener {

    private SearchActivity searchActivity;
    private BaseApplication baseApplication;
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    BottomSheetDialog dialog;
    private View rootView;
    private int checkedId;
    private RelativeLayout relByGod;
    private CustomTextView txtReset, txtSearch;

    private OnFilterOptionSelected mListener;

    public FilterBottomSheetFragment() {
        // Required empty public constructor
    }

    public static FilterBottomSheetFragment newInstance(String param1, String param2) {
        FilterBottomSheetFragment fragment = new FilterBottomSheetFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public static FilterBottomSheetFragment newInstance(int param1) {
        FilterBottomSheetFragment fragment = new FilterBottomSheetFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_PARAM1, param1);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            checkedId = getArguments().getInt(ARG_PARAM1);
        }
        searchActivity = (SearchActivity) getActivity();
        baseApplication = (BaseApplication) searchActivity.getApplication();
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        dialog = (BottomSheetDialog) super.onCreateDialog(savedInstanceState);
        try {
            if (rootView == null) {
                rootView = View.inflate(getContext(), R.layout.fragment_bottom_sheet_filter, null);
            }
            dialog.setContentView(rootView);
            initComponents();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return dialog;
    }

    private void initComponents() {
        txtReset = rootView.findViewById(R.id.txtReset);
        txtSearch = rootView.findViewById(R.id.txtSearch);
        relByGod = rootView.findViewById(R.id.relByGod);
        relByGod.setOnClickListener(this);
        txtSearch.setOnClickListener(this);
        txtReset.setOnClickListener(this);
    }

    public void onButtonPressed(int position) {
        if (mListener != null) {
            mListener.filterCallback(position);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFilterOptionSelected) {
            mListener = (OnFilterOptionSelected) context;
        } else {
            throw new RuntimeException(context.toString() + " must implement OnFilterOptionSelected");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.relByGod:
                searchActivity.startActivity(new Intent(searchActivity, SelectGodsActivity.class));
                break;
            default:
                dismiss();
                break;
        }
    }

    public interface OnFilterOptionSelected {
        void filterCallback(int checkedId);
    }

}
