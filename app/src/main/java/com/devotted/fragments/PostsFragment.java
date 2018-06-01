package com.devotted.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.devotted.R;
import com.devotted.activities.SearchActivity;
import com.devotted.adapters.TemplePostsAdapter;
import com.devotted.listeners.IClickListener;
import com.devotted.models.TempleModel;

import java.util.ArrayList;


public class PostsFragment extends BaseFragment implements IClickListener {

    private TemplePostsAdapter templePostsAdapter;
    private RecyclerView recyclerViewPosts;
    private View rootView;
    private SearchActivity searchActivity;
    private ArrayList<TempleModel> templePostsArrayList;
    private CommentsBottomSheetFragment commentsBottomSheetFragment;

    public PostsFragment() {
    }

    public static PostsFragment newInstance(String text) {
        PostsFragment categoryDetailsFragment = new PostsFragment();
        Bundle bundle = new Bundle();
        bundle.putString("text", text);
        categoryDetailsFragment.setArguments(bundle);
        return categoryDetailsFragment;
    }

    private void setDummyData() {
        for (int i = 0; i < 10; i++) {
            templePostsArrayList.add(new TempleModel(1));
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        searchActivity = (SearchActivity) getActivity();
        templePostsArrayList = new ArrayList<>();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_posts, container, false);
        initComponents();
        return rootView;
    }

    private void initComponents() {
        recyclerViewPosts = rootView.findViewById(R.id.recyclerViewPosts);
        setDummyData();
        setTemplePostsAdapter();
        ViewCompat.setNestedScrollingEnabled(recyclerViewPosts, false);
    }

    private void setTemplePostsAdapter() {
        recyclerViewPosts.setLayoutManager(new LinearLayoutManager(searchActivity));
        templePostsAdapter = new TemplePostsAdapter(searchActivity, templePostsArrayList, this);
        recyclerViewPosts.setAdapter(templePostsAdapter);
    }


    @Override
    public void onClick(View view, int position) {
        switch (view.getId()) {
            case R.id.txtComment:
            case R.id.txtComments:
                showCommentsScreen();
                break;
            case R.id.txtLike:
            case R.id.txtLikes:
                break;
            default:
                break;
        }
    }

    private void showCommentsScreen() {
        commentsBottomSheetFragment = new CommentsBottomSheetFragment();
        commentsBottomSheetFragment.show(searchActivity.getSupportFragmentManager(), commentsBottomSheetFragment.getTag());
    }

    @Override
    public void onLongClick(View view, int position) {

    }
}
