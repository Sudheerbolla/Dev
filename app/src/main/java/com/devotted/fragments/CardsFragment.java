package com.devotted.fragments;

import android.graphics.Rect;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.devotted.R;
import com.devotted.activities.MainActivity;
import com.devotted.adapters.TemplePostsAdapter;
import com.devotted.listeners.IClickListener;
import com.devotted.models.CardDataItem;
import com.devotted.models.TempleModel;
import com.devotted.utils.StaticUtils;
import com.devotted.utils.views.CustomTextView;
import com.stone.card.library.CardAdapter;
import com.stone.card.library.CardSlidePanel;

import java.util.ArrayList;

public class CardsFragment extends BaseFragment implements IClickListener {

    private CardSlidePanel.CardSwitchListener cardSwitchListener;
    private ArrayList<CardDataItem> dataList/*, originalData*/;
    private CustomTextView txtReload;
    private View rootView;
    private RecyclerView recyclerViewPastUpdates;
    private MainActivity mainActivity;
    private TemplePostsAdapter templePostsAdapter;
    private ArrayList<TempleModel> templePostsArrayList;

    public CardsFragment() {
    }

    public static CardsFragment newInstance(String text) {
        CardsFragment categoryDetailsFragment = new CardsFragment();
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
//        originalData = new ArrayList<>();
        templePostsArrayList = new ArrayList<>();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_cards, container, false);
        initComponents();
        return rootView;
    }

    private void initComponents() {
        txtReload = rootView.findViewById(R.id.txtReload);
        recyclerViewPastUpdates = rootView.findViewById(R.id.recyclerViewPastUpdates);
        setDummyData();
        setTemplePostsAdapter();

        final CardSlidePanel slidePanel = rootView.findViewById(R.id.image_slide_panel);

        cardSwitchListener = new CardSlidePanel.CardSwitchListener() {

            @Override
            public void onShow(int index) {
//                Log.d("Card", "正在显示-" + dataList.get(index).userName);
//                dataList.get(index).isRead = true;
            }

            @Override
            public void onCardVanish(int index, int type) {
                StaticUtils.showToast(mainActivity, "removed item at " + index + " New size " + dataList.size());
//                Log.d("Card", "正在消失-" + dataList.get(index).userName + " 消失type=" + type);
//                CardItemView cardItemView=dataList.get(index);
                if (index == dataList.size() - 1) {
                    ArrayList<CardDataItem> arrayList = new ArrayList<>(dataList);
                    dataList.clear();
                    dataList.addAll(arrayList);
                    slidePanel.getAdapter().notifyDataSetChanged();
                }
//                CardDataItem cardDataItem = dataList.get(index);
//                cardDataItem.isRead = true;
//                dataList.remove(index);
//                dataList.add(dataList.size() - 1, cardDataItem);
////                if (index == dataList.size() - 1) {
////                    dataList.addAll(dataList);
//                slidePanel.getAdapter().notifyDataSetChanged();
//                }
                Log.e("swipe : ", "index : " + index + ", type : " + type + ", size" + dataList.size());

            }
        };

        slidePanel.setCardSwitchListener(cardSwitchListener);

        slidePanel.setAdapter(new CardAdapter() {
            @Override
            public int getLayoutId() {
                return R.layout.item_card;
            }

            @Override
            public int getCount() {
                return dataList.size();
            }

            @Override
            public void bindView(View view, int index) {
                Object tag = view.getTag();
                ViewHolder viewHolder;
                if (null != tag) {
                    viewHolder = (ViewHolder) tag;
                } else {
                    viewHolder = new ViewHolder(view);
                    view.setTag(viewHolder);
                }

                viewHolder.bindData(dataList.get(index));
            }

            @Override
            public Object getItem(int index) {
                return dataList.get(index);
            }

            @Override
            public Rect obtainDraggableArea(View view) {
                View contentView = view.findViewById(R.id.cardViewTemple);
                View topLayout = view.findViewById(R.id.linTop);
                View bottomLayout = view.findViewById(R.id.linBottom);
                int left = view.getLeft() + contentView.getPaddingLeft() + topLayout.getPaddingLeft();
                int right = view.getRight() - contentView.getPaddingRight() - topLayout.getPaddingRight();
                int top = view.getTop() + contentView.getPaddingTop() + topLayout.getPaddingTop();
                int bottom = view.getBottom() - contentView.getPaddingBottom() - bottomLayout.getPaddingBottom();
                return new Rect(left, top, right, bottom);
            }
        });

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                prepareDataList();
                slidePanel.getAdapter().notifyDataSetChanged();
            }
        }, 500);

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

    private void prepareDataList() {
        for (int i = 0; i < 6; i++) {
            CardDataItem dataItem = new CardDataItem();
            dataList.add(dataItem);
//            originalData.addAll(dataList);
        }
    }

    private void appendDataList() {
        for (int i = 0; i < 6; i++) {
            CardDataItem dataItem = new CardDataItem();
            dataList.add(dataItem);
        }
    }

    @Override
    public void onClick(View view, int position) {

    }

    @Override
    public void onLongClick(View view, int position) {

    }

    class ViewHolder {

        RelativeLayout relColoredBackground;
        TextView txtReadLogo;

        public ViewHolder(View view) {
            relColoredBackground = view.findViewById(R.id.relColoredBackground);
            txtReadLogo = view.findViewById(R.id.txtReadLogo);
        }

        public void bindData(CardDataItem itemData) {
            if (itemData.isRead) {
                relColoredBackground.setBackground(ContextCompat.getDrawable(mainActivity, R.drawable.btn_background));
                txtReadLogo.setVisibility(View.VISIBLE);
            } else {
                relColoredBackground.setBackground(ContextCompat.getDrawable(mainActivity, R.drawable.btn_background_h));
                txtReadLogo.setVisibility(View.GONE);
            }
//            itemData.isRead = true;
        }
    }

}
