package com.devotted.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.db.rossdeckview.FlingChief;
import com.db.rossdeckview.FlingChiefListener;
import com.db.rossdeckview.RossDeckView;
import com.devotted.R;
import com.devotted.activities.MainActivity;
import com.devotted.adapters.CardsDeckAdapter;
import com.devotted.adapters.TemplePostsAdapter;
import com.devotted.listeners.IClickListener;
import com.devotted.models.CardDataItem;
import com.devotted.models.TempleModel;

import java.util.ArrayList;
import java.util.List;


public class CardsRossDeckFragment extends BaseFragment implements FlingChiefListener.Actions, IClickListener {

    private View rootView;
    private MainActivity mainActivity;

    private final static int DELAY = 1000;
    private CardsDeckAdapter mAdapter;
    private List<Pair<String, CardDataItem>> dataList, originalData;

    private RecyclerView recyclerViewPastUpdates;
    private TemplePostsAdapter templePostsAdapter;
    private ArrayList<TempleModel> templePostsArrayList;

    public CardsRossDeckFragment() {
    }

    public static CardsRossDeckFragment newInstance(String text) {
        CardsRossDeckFragment categoryDetailsFragment = new CardsRossDeckFragment();
        Bundle bundle = new Bundle();
        bundle.putString("text", text);
        categoryDetailsFragment.setArguments(bundle);
        return categoryDetailsFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mainActivity = (MainActivity) getActivity();
        dataList = new ArrayList<>();
        originalData = new ArrayList<>();
        templePostsArrayList = new ArrayList<>();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_cards_rossdeck, container, false);
        initComponents();
        return rootView;
    }

    private void initComponents() {
        for (int i = 0; i < 6; i++) {
            dataList.add(newItem(i));
        }
        originalData.addAll(dataList);

        mAdapter = new CardsDeckAdapter(mainActivity, dataList);

        RossDeckView mDeckLayout = rootView.findViewById(R.id.decklayout);
        mDeckLayout.setAdapter(mAdapter);
        mDeckLayout.setActionsListener(this);

        recyclerViewPastUpdates = rootView.findViewById(R.id.recyclerViewPastUpdates);
        setDummyData();
        setTemplePostsAdapter();

        ViewCompat.setNestedScrollingEnabled(recyclerViewPastUpdates, false);

    }

    private void setDummyData() {
        for (int i = 0; i < 10; i++) {
            templePostsArrayList.add(new TempleModel(1));
        }
    }

    private void setTemplePostsAdapter() {
        recyclerViewPastUpdates.setLayoutManager(new LinearLayoutManager(mainActivity));
        templePostsAdapter = new TemplePostsAdapter(mainActivity, templePostsArrayList, this);
        recyclerViewPastUpdates.setAdapter(templePostsAdapter);
    }

    private Pair<String, CardDataItem> newItem(int position) {
        return new Pair<>("Card At " + position, new CardDataItem());
    }

    @Override
    public boolean onDismiss(@NonNull FlingChief.Direction direction, @NonNull View view) {
        return true;
    }

    @Override
    public boolean onDismissed(@NonNull View view) {
        Pair<String, CardDataItem> cardDataItem = dataList.get(0);

        if (dataList.size() > 1) {
            dataList.remove(0);
            cardDataItem.second.isRead = true;
            dataList.add(dataList.size() - 1, cardDataItem);
            //            mItems.get(0)
            mAdapter.notifyDataSetChanged();
//        } else {
//            navigate to next tab
//            StaticUtils.showToast(mainActivity, "reached last and will move to next tab");
        }
        return true;
    }

    @Override
    public boolean onReturn(@NonNull View view) {
        return true;
    }

    @Override
    public boolean onReturned(@NonNull View view) {
        return true;
    }

    @Override
    public boolean onTapped() {
//        StaticUtils.showToast(mainActivity, "Tapped ");
        return true;
    }

    @Override
    public boolean onDoubleTapped() {
//        StaticUtils.showToast(mainActivity, "Tapped ");
        return true;
    }

    @Override
    public void onClick(View view, int position) {

    }

    @Override
    public void onLongClick(View view, int position) {

    }
}
