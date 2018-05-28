package com.devotted.fragments;

import android.graphics.Rect;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.devotted.R;
import com.devotted.activities.MainActivity;
import com.devotted.models.CardDataItem;
import com.devotted.utils.views.CustomTextView;
import com.stone.card.library.CardAdapter;
import com.stone.card.library.CardSlidePanel;

import java.util.ArrayList;

public class CardsFragment extends BaseFragment {
    private CardSlidePanel.CardSwitchListener cardSwitchListener;
    private ArrayList<CardDataItem> dataList;
    private CustomTextView txtReload;
    private View rootView;
    private MainActivity mainActivity;

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
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_cards, container, false);
        initComponents();
        return rootView;
    }

    private void initComponents() {
        txtReload = rootView.findViewById(R.id.txtReload);

        final CardSlidePanel slidePanel = rootView.findViewById(R.id.image_slide_panel);

        // 1. 左右滑动监听
        cardSwitchListener = new CardSlidePanel.CardSwitchListener() {

            @Override
            public void onShow(int index) {
//                Log.d("Card", "正在显示-" + dataList.get(index).userName);
            }

            @Override
            public void onCardVanish(int index, int type) {
//                Log.d("Card", "正在消失-" + dataList.get(index).userName + " 消失type=" + type);
            }
        };

        slidePanel.setCardSwitchListener(cardSwitchListener);


        // 2. 绑定Adapter
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

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                prepareDataList();
                slidePanel.getAdapter().notifyDataSetChanged();
            }
        }, 500);

        txtReload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                appendDataList();
                slidePanel.getAdapter().notifyDataSetChanged();
            }
        });
    }

    private void prepareDataList() {
        for (int i = 0; i < 6; i++) {
            CardDataItem dataItem = new CardDataItem();
            dataList.add(dataItem);
        }
    }

    private void appendDataList() {
        for (int i = 0; i < 6; i++) {
            CardDataItem dataItem = new CardDataItem();
            dataList.add(dataItem);
        }
    }

    class ViewHolder {

        ImageView imageView;
        View maskView;
        TextView userNameTv;
        TextView imageNumTv;
        TextView likeNumTv;

        public ViewHolder(View view) {
//            imageView = (ImageView) view.findViewById(R.id.card_image_view);
//            maskView = view.findViewById(R.id.maskView);
//            userNameTv = (TextView) view.findViewById(R.id.card_user_name);
//            imageNumTv = (TextView) view.findViewById(R.id.card_pic_num);
//            likeNumTv = (TextView) view.findViewById(R.id.card_like);
        }

        public void bindData(CardDataItem itemData) {
//            Glide.with(MainActivity.this).load(itemData.imagePath).into(imageView);
//            userNameTv.setText(itemData.userName);
//            imageNumTv.setText(itemData.imageNum + "");
//            likeNumTv.setText(itemData.likeNum + "");
        }
    }

}
