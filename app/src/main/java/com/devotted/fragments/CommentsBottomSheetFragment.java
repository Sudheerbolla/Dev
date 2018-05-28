package com.devotted.fragments;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetDialog;
import android.support.design.widget.BottomSheetDialogFragment;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.devotted.R;
import com.devotted.activities.SearchActivity;
import com.devotted.adapters.CommentsAdapter;
import com.devotted.listeners.IClickListener;
import com.devotted.models.ReviewsItem;
import com.devotted.utils.StaticUtils;
import com.devotted.utils.views.CustomEditText;
import com.devotted.utils.views.CustomTextView;

import java.util.ArrayList;

public class CommentsBottomSheetFragment extends BottomSheetDialogFragment implements View.OnClickListener, IClickListener {

    private SearchActivity searchActivity;
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    BottomSheetDialog dialog;
    private View rootView;
    private RecyclerView recyclerViewComments;
    private CommentsAdapter commentsAdapter;
    private ArrayList<ReviewsItem> commentsArrayList;
    private CustomEditText edtComment;
    private CustomTextView txtSend;
    private ImageView imgClose;

    private OnComments mListener;

    public CommentsBottomSheetFragment() {
        // Required empty public constructor
    }

    public static CommentsBottomSheetFragment newInstance(String param1, String param2) {
        CommentsBottomSheetFragment fragment = new CommentsBottomSheetFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public static CommentsBottomSheetFragment newInstance(int param1) {
        CommentsBottomSheetFragment fragment = new CommentsBottomSheetFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_PARAM1, param1);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        commentsArrayList = new ArrayList<>();
        searchActivity = (SearchActivity) getActivity();
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        dialog = (BottomSheetDialog) super.onCreateDialog(savedInstanceState);
        try {
            if (rootView == null) {
                rootView = View.inflate(getContext(), R.layout.fragment_bottom_sheet_comments, null);
            }
            dialog.setContentView(rootView);
            initComponents();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return dialog;
    }

    private void initComponents() {
        recyclerViewComments = rootView.findViewById(R.id.recyclerViewComments);

        edtComment = rootView.findViewById(R.id.edtComment);
        txtSend = rootView.findViewById(R.id.txtSend);
        imgClose = rootView.findViewById(R.id.imgClose);
        imgClose.setOnClickListener(this);
        txtSend.setOnClickListener(this);
        setDummyData();
        setAdapter();
        ViewCompat.setNestedScrollingEnabled(recyclerViewComments, true);
    }

    private void setDummyData() {
        for (int i = 1; i < 10; i++) {
            commentsArrayList.add(new ReviewsItem());
        }
    }

    private void setAdapter() {
        recyclerViewComments.setLayoutManager(new LinearLayoutManager(searchActivity));
        commentsAdapter = new CommentsAdapter(searchActivity, commentsArrayList, this);
        recyclerViewComments.setAdapter(commentsAdapter);
    }

    public void onButtonPressed(int position) {
        if (mListener != null) {
            mListener.onComments();
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnComments) {
            mListener = (OnComments) context;
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
            case R.id.imgClose:
                dismiss();
                break;
            case R.id.txtSend:
                StaticUtils.showToast(searchActivity, "Sent Comment Successfully");
                dismiss();
                break;
            default:
//                dismiss();
                break;
        }
    }

    @Override
    public void onClick(View view, int position) {

    }

    @Override
    public void onLongClick(View view, int position) {

    }

    public interface OnComments {
        void onComments();
    }

}
