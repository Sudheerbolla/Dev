package com.devotted.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.devotted.R;
import com.devotted.activities.MainActivity;
import com.devotted.adapters.CardsPagerAdapter;
import com.devotted.adapters.CardsRecyclerViewAdapter;
import com.devotted.listeners.IClickListener;
import com.devotted.models.CardDataItem;
import com.devotted.utils.views.CustomViewPager;

import java.util.ArrayList;


public class CardsRossDeckFragment extends BaseFragment implements IClickListener, View.OnClickListener {

    private View rootView;
    private MainActivity mainActivity;

    private CardsPagerAdapter cardsPagerAdapter;
    private ArrayList<CardDataItem> dataList;

    private RecyclerView recyclerViewPastUpdates;
    private CardsRecyclerViewAdapter cardsRecyclerViewAdapter;
    private ArrayList<CardDataItem> cardDataItemArrayList;
    private String type;
    private CustomViewPager cardsViewPager;
    private ImageView imgNext, imgPrevious;

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
        cardDataItemArrayList = new ArrayList<>();
        type = getArguments().getString("text");
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_cards_rossdeck, container, false);
        initComponents();
        return rootView;
    }

    private void initComponents() {
        for (int i = 0; i < 6; i++) {
            dataList.add(new CardDataItem());
        }
        cardsPagerAdapter = new CardsPagerAdapter(mainActivity, dataList, this);

        cardsViewPager = rootView.findViewById(R.id.cardsViewPager);
        imgPrevious = rootView.findViewById(R.id.imgPrevious);
        imgNext = rootView.findViewById(R.id.imgNext);

        cardsViewPager.setPagingEnabled(false);

        cardsViewPager.setAdapter(cardsPagerAdapter);
        recyclerViewPastUpdates = rootView.findViewById(R.id.recyclerViewPastUpdates);
        setDummyData();
        setTemplePostsAdapter();

        ViewCompat.setNestedScrollingEnabled(recyclerViewPastUpdates, false);
        imgNext.setOnClickListener(this);
        imgPrevious.setOnClickListener(this);
        cardsViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
//                if (position == dataList.size() - 1) {
//                    cardsViewPager.setCurrentItem();
//                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    private void setDummyData() {
        for (int i = 0; i < 12; i++) {
            CardDataItem cardDataItem = new CardDataItem();
            cardDataItem.isRead = true;
            cardDataItemArrayList.add(cardDataItem);
        }
    }

    private void setTemplePostsAdapter() {
        recyclerViewPastUpdates.setLayoutManager(new LinearLayoutManager(mainActivity));
        cardsRecyclerViewAdapter = new CardsRecyclerViewAdapter(mainActivity, cardDataItemArrayList, this);
        recyclerViewPastUpdates.setAdapter(cardsRecyclerViewAdapter);
    }

    @Override
    public void onClick(View view, int position) {
        switch (view.getId()) {
            case R.id.scrollView:
                imgNext.callOnClick();
                break;
        }

    }

    @Override
    public void onLongClick(View view, int position) {

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.imgNext:
                if (dataList.size() > 0) {
                    updateData();
                    if (cardsViewPager.getCurrentItem() != dataList.size() - 1) {
                        cardsViewPager.setCurrentItem(cardsViewPager.getCurrentItem() + 1, true);
                    }
                    else cardsViewPager.setCurrentItem(0, true);
                }
                break;
            case R.id.imgPrevious:
                if (dataList.size() > 0) {
                    if (cardsViewPager.getCurrentItem() != 0)
                        cardsViewPager.setCurrentItem(cardsViewPager.getCurrentItem() - 1, true);
                    updateData();
                }
                break;
            default:
                break;
        }
    }

    private void updateData() {
        if (dataList.size() > 1) {
            CardDataItem cardDataItem = dataList.get(cardsViewPager.getCurrentItem());
            cardDataItem.isRead = true;
            cardsPagerAdapter.notifyDataSetChanged();
        }
    }

}
