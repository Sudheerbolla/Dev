package com.devotted.fragments;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetDialog;
import android.support.design.widget.BottomSheetDialogFragment;
import android.view.View;
import android.widget.RadioGroup;

import com.devotted.BaseApplication;
import com.devotted.R;
import com.devotted.activities.SearchActivity;

public class SortBottomSheetFragment extends BottomSheetDialogFragment {

    private SearchActivity searchActivity;
    private BaseApplication baseApplication;
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    BottomSheetDialog dialog;
    private View rootView;
    private int checkedId;
    private RadioGroup rgSortBy;

    private OnSortOptionSelected mListener;

    public SortBottomSheetFragment() {
        // Required empty public constructor
    }

    public static SortBottomSheetFragment newInstance(String param1, String param2) {
        SortBottomSheetFragment fragment = new SortBottomSheetFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public static SortBottomSheetFragment newInstance(int param1) {
        SortBottomSheetFragment fragment = new SortBottomSheetFragment();
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
                rootView = View.inflate(getContext(), R.layout.fragment_bottom_sheet_sort, null);
            }
            dialog.setContentView(rootView);
            initComponents();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return dialog;
    }

    private void initComponents() {
        rgSortBy = rootView.findViewById(R.id.rg_sort_by);
        if (checkedId != -1) {
            rgSortBy.check(checkedId);
        }
        rgSortBy.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int checkedId) {
                onButtonPressed(checkedId);
            }
        });

    }

    public void onButtonPressed(int position) {
        if (mListener != null) {
            mListener.sortOptionSelected(position);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnSortOptionSelected) {
            mListener = (OnSortOptionSelected) context;
        } else {
            throw new RuntimeException(context.toString() + " must implement OnFilterOptionSelected");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnSortOptionSelected {
        void sortOptionSelected(int checkedId);
    }

}
