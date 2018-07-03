package com.devotted.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.devotted.R;
import com.devotted.activities.MainActivity;
import com.devotted.adapters.CardsRecyclerViewAdapter;
import com.devotted.adapters.CardsRecyclerViewAdapterToday;
import com.devotted.listeners.IClickListener;
import com.devotted.models.CardDataItemNew;
import com.devotted.utils.StaticUtils;
import com.devotted.utils.views.CustomTextView;

import java.util.ArrayList;
import java.util.HashMap;


public class CardsRossDeckFragment extends BaseFragment implements IClickListener, View.OnClickListener {

    private View rootView;
    private MainActivity mainActivity;

    private ArrayList<CardDataItemNew> dataList;

    private RecyclerView recyclerViewPastUpdates, recyclerViewTodayUpdates;
    private CardsRecyclerViewAdapter cardsRecyclerViewAdapter;
    private CardsRecyclerViewAdapterToday cardsRecyclerViewAdapterToday;
    private ArrayList<CardDataItemNew> cardDataItemNewArrayList, todayArrayList;
    private String category;
    private CustomTextView txtToday;
    private HashMap<String, String> hashMapOfCategory;
    private String dayOfTheWeek;

    public CardsRossDeckFragment() {
    }

    public static CardsRossDeckFragment newInstance(String text) {
        CardsRossDeckFragment categoryDetailsFragment = new CardsRossDeckFragment();
        Bundle bundle = new Bundle();
        bundle.putString("category", text);
        categoryDetailsFragment.setArguments(bundle);
        return categoryDetailsFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mainActivity = (MainActivity) getActivity();
        dataList = new ArrayList<>();
        cardDataItemNewArrayList = new ArrayList<>();
        todayArrayList = new ArrayList<>();
        category = getArguments().getString("category");
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_cards_rossdeck, container, false);
        initComponents();
        return rootView;
    }

    private void initComponents() {
        for (int i = 0; i < 7; i++) {
            dataList.add(new CardDataItemNew());
        }
        recyclerViewTodayUpdates = rootView.findViewById(R.id.recyclerViewTodayUpdates);
        recyclerViewPastUpdates = rootView.findViewById(R.id.recyclerViewPastUpdates);
        txtToday = rootView.findViewById(R.id.txtToday);
        String today = StaticUtils.getpreviousDatesString(0);
        txtToday.setText("Today: " + today);
        dayOfTheWeek = StaticUtils.getTodayInWeek(today);
        setDummyData();
        setTemplePostsAdapter();

        ViewCompat.setNestedScrollingEnabled(recyclerViewPastUpdates, false);
    }

    private CardDataItemNew getTodaysCard(CardDataItemNew cardDataItemToday) {
        cardDataItemToday.content = hashMapOfCategory.get(cardDataItemToday.weekDay);
//        switch (dayOfTheWeek) {
//            case "Monday":
//                hashMapOfCategory.get("Monday");
//                break;
//            case "Tuesday":
//                break;
//            case "Wednesday":
//                break;
//            case "Thursday":
//                break;
//            case "Friday":
//                break;
//            case "Saturday":
//                break;
//            case "Sunday":
//                break;
//        }

        return cardDataItemToday;
    }

    private void setDummyData() {

        hashMapOfCategory = new HashMap<>();

        switch (category) {
            case "Mantras":
                hashMapOfCategory.put("Monday", getString(R.string.spiritual_monday_mantra));
                hashMapOfCategory.put("Tuesday", getString(R.string.spiritual_tuesday_mantra));
                hashMapOfCategory.put("Wednesday", getString(R.string.spiritual_wednesday_mantra));
                hashMapOfCategory.put("Thursday", getString(R.string.spiritual_thursday_mantra));
                hashMapOfCategory.put("Friday", getString(R.string.spiritual_friday_mantra));
                hashMapOfCategory.put("Saturday", getString(R.string.spiritual_saturday_mantra));
                hashMapOfCategory.put("Sunday", getString(R.string.spiritual_sunday_mantra));
                break;
            case "Prayer":
                hashMapOfCategory.put("Monday", getString(R.string.religious_monday_prayer));
                hashMapOfCategory.put("Tuesday", getString(R.string.religious_tuesday_prayer));
                hashMapOfCategory.put("Wednesday", getString(R.string.religious_wednesday_prayer));
                hashMapOfCategory.put("Thursday", getString(R.string.religious_thursday_prayer));
                hashMapOfCategory.put("Friday", getString(R.string.religious_friday_prayer));
                hashMapOfCategory.put("Saturday", getString(R.string.religious_saturday_prayer));
                hashMapOfCategory.put("Sunday", getString(R.string.religious_sunday_prayer));
                break;
            case "Dharma":
                hashMapOfCategory.put("Monday", getString(R.string.religious_monday_dharma));
                hashMapOfCategory.put("Tuesday", getString(R.string.religious_tuesday_dharma));
                hashMapOfCategory.put("Wednesday", getString(R.string.religious_wednesday_dharma));
                hashMapOfCategory.put("Thursday", getString(R.string.religious_thursday_dharma));
                hashMapOfCategory.put("Friday", getString(R.string.religious_friday_dharma));
                hashMapOfCategory.put("Saturday", getString(R.string.religious_saturday_dharma));
                hashMapOfCategory.put("Sunday", getString(R.string.religious_sunday_dharma));
                break;
            case "Health":
                hashMapOfCategory.put("Monday", getString(R.string.wellness_health_content_monday));
                hashMapOfCategory.put("Tuesday", getString(R.string.wellness_health_content_tuesday));
                hashMapOfCategory.put("Wednesday", getString(R.string.wellness_health_content_wednesday));
                hashMapOfCategory.put("Thursday", getString(R.string.wellness_health_content_thursday));
                hashMapOfCategory.put("Friday", getString(R.string.wellness_health_content_friday));
                hashMapOfCategory.put("Saturday", getString(R.string.wellness_health_content_saturday));
                hashMapOfCategory.put("Sunday", getString(R.string.wellness_health_content_sunday));
                break;
        }

        CardDataItemNew cardDataItemToday = new CardDataItemNew();
        cardDataItemToday.category = category;
        cardDataItemToday.isRead = false;
        cardDataItemToday.heading = category;
        cardDataItemToday.weekDay = dayOfTheWeek;
        cardDataItemToday = getTodaysCard(cardDataItemToday);
        todayArrayList.add(cardDataItemToday);

        Log.e("cat : ", cardDataItemToday.weekDay + " " + cardDataItemToday.category);

        for (int i = 0; i < 6; i++) {
            CardDataItemNew cardDataItem1 = new CardDataItemNew();
            cardDataItem1.category = category;
            cardDataItem1.isRead = true;
            cardDataItem1.heading = category;
            String today = StaticUtils.getpreviousDatesString(0 - (i + 1));
            cardDataItem1.weekDay = StaticUtils.getTodayInWeek(today);
            Log.e("cat : ", cardDataItem1.weekDay + " " + cardDataItem1.category);
            cardDataItem1 = getTodaysCard(cardDataItem1);
            cardDataItem1.weekDay = today;
            cardDataItemNewArrayList.add(cardDataItem1);
        }
        Log.e("cat : ", cardDataItemToday.weekDay + " " + cardDataItemToday.category);
    }

    private void setTemplePostsAdapter() {
        recyclerViewTodayUpdates.setLayoutManager(new LinearLayoutManager(mainActivity, LinearLayoutManager.HORIZONTAL, false));
        cardsRecyclerViewAdapterToday = new CardsRecyclerViewAdapterToday(mainActivity, todayArrayList, this);
        recyclerViewTodayUpdates.setAdapter(cardsRecyclerViewAdapterToday);

        recyclerViewPastUpdates.setLayoutManager(new LinearLayoutManager(mainActivity));
        cardsRecyclerViewAdapter = new CardsRecyclerViewAdapter(mainActivity, cardDataItemNewArrayList, this);
        recyclerViewPastUpdates.setAdapter(cardsRecyclerViewAdapter);
    }

    @Override
    public void onClick(View view, final int position) {
        switch (view.getId()) {
            case R.id.linCard:
            case R.id.txtDescription:
//                DialogUtils.showSimpleDialog(mainActivity, todayArrayList.get(position).content, new View.OnClickListener() {
//                    @Override
//                    public void onClick(View view) {
//                        todayArrayList.get(position).isRead = true;
//                    }
//                }, null, true);
                todayArrayList.get(position).isRead = true;
                cardsRecyclerViewAdapterToday.notifyDataSetChanged();
                break;
        }
    }

    @Override
    public void onLongClick(View view, int position) {

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
//            case R.id.imgNext:
//                if (dataList.size() > 0) {
//                    updateData();
//                    if (cardsViewPager.getCurrentItem() != dataList.size() - 1) {
//                        cardsViewPager.setCurrentItem(cardsViewPager.getCurrentItem() + 1, true);
//                    } else cardsViewPager.setCurrentItem(0, true);
//                }
//                break;
//            case R.id.imgPrevious:
//                if (dataList.size() > 0) {
//                    if (cardsViewPager.getCurrentItem() != 0)
//                        cardsViewPager.setCurrentItem(cardsViewPager.getCurrentItem() - 1, true);
//                    updateData();
//                }
//                break;
            default:
                break;
        }
    }

}
