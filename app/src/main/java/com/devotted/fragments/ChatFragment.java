package com.devotted.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.devotted.R;
import com.devotted.activities.TempleGroupsActivity;
import com.devotted.adapters.ChatAdapter;
import com.devotted.listeners.IClickListener;
import com.devotted.models.ReviewsItem;
import com.devotted.utils.StaticUtils;
import com.devotted.utils.views.CustomEditText;

import java.util.ArrayList;

public class ChatFragment extends BaseFragment implements View.OnClickListener, IClickListener {

    private View rootView;
    private RecyclerView recyclerViewChat;
    private ChatAdapter chatAdapter;
    private ArrayList<ReviewsItem> commentsArrayList;
    private TempleGroupsActivity templeGroupsActivity;
    private CustomEditText edtComment;
    private ImageView imgSend;

    public ChatFragment() {
        // Required empty public constructor
    }

    public static ChatFragment newInstance() {
        ChatFragment fragment = new ChatFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        commentsArrayList = new ArrayList<>();
        templeGroupsActivity = (TempleGroupsActivity) getActivity();
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = View.inflate(getContext(), R.layout.fragment_chat, null);
        initComponents();
        return rootView;
    }

    private void initComponents() {
        recyclerViewChat = rootView.findViewById(R.id.recyclerViewChat);

        edtComment = rootView.findViewById(R.id.edtComment);
        imgSend = rootView.findViewById(R.id.imgSend);
        imgSend.setOnClickListener(this);
        setDummyData();
        setAdapter();
    }

    private void setDummyData() {
        for (int i = 1; i < 25; i++) {
            commentsArrayList.add(new ReviewsItem());
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        templeGroupsActivity.setTopBarText(getString(R.string.discussion));
    }

    private void setAdapter() {
        recyclerViewChat.setLayoutManager(new LinearLayoutManager(templeGroupsActivity));
        chatAdapter = new ChatAdapter(templeGroupsActivity, commentsArrayList, this);
        recyclerViewChat.setAdapter(chatAdapter);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.imgSend:
                StaticUtils.showToast(templeGroupsActivity, getString(R.string.sent_message_successfully));
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

}
