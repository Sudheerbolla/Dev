package com.devotted.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.devotted.R;
import com.devotted.utils.views.CustomTextView;


public class InnerFragment extends BaseFragment {

    public InnerFragment() {
    }

    public static InnerFragment newInstance(String text) {
        InnerFragment categoryDetailsFragment = new InnerFragment();
        Bundle bundle=new Bundle();
        bundle.putString("text",text);
        categoryDetailsFragment.setArguments(bundle);
        return categoryDetailsFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView=inflater.inflate(R.layout.fragment_inner, container, false);
        ((CustomTextView)rootView.findViewById(R.id.txtText)).setText(getArguments().getString("text"));

        return rootView;
    }

}
