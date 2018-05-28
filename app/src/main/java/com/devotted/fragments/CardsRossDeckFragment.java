/*
package com.devotted.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
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
import com.devotted.utils.StaticUtils;

import java.util.ArrayList;
import java.util.List;


public class CardsRossDeckFragment extends BaseFragment implements FlingChiefListener.Actions*/
/*, FlingChiefListener.Proximity *//*
{

    private View rootView;
    private MainActivity mainActivity;

    private final static int DELAY = 1000;
    private List<Pair<String, Integer>> mItems;
    private CardsDeckAdapter mAdapter;
    private int[] mColors;
    private int mCount = 0;

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
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_cards_rossdeck, container, false);
        initComponents();
        return rootView;
    }

    private void initComponents() {
        mColors = getResources().getIntArray(R.array.cardsBackgroundColors);
        mItems = new ArrayList<>();

        mItems.add(newItem());
        mItems.add(newItem());
        mItems.add(newItem());
        mItems.add(newItem());
        mItems.add(newItem());
        mAdapter = new CardsDeckAdapter(mainActivity, mItems);

        RossDeckView mDeckLayout = rootView.findViewById(R.id.decklayout);
        mDeckLayout.setAdapter(mAdapter);
        mDeckLayout.setActionsListener(this);
//        mDeckLayout.setProximityListener(this);
        mDeckLayout.setDirections(new FlingChief.Direction[]{FlingChief.Direction.BOTTOM, FlingChief.Direction.TOP});
    }

    private Pair<String, Integer> newItem() {
        Pair<String, Integer> res = new Pair<>("Card" + Integer.toString(mCount), mColors[mCount]);
        mCount = (mCount >= mColors.length - 1) ? 0 : mCount + 1;
        return res;
    }

    @Override
    public boolean onDismiss(@NonNull FlingChief.Direction direction, @NonNull View view) {
//        StaticUtils.showToast(mainActivity, "Swiped to direction" + direction);
        return true;
//        return false;
    }

    @Override
    public boolean onDismissed(@NonNull View view) {
        if (mItems.size() > 1) {
            mItems.remove(0);
            mAdapter.notifyDataSetChanged();
        } else {
//            navigate to next tab
            StaticUtils.showToast(mainActivity, "reached last and will move to next tab");
        }
        return true;
        //        return false;
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
        StaticUtils.showToast(mainActivity, "Tapped ");
        return true;
    }

    @Override
    public boolean onDoubleTapped() {
        StaticUtils.showToast(mainActivity, "Tapped ");
        return true;
    }

//    @Override
//    public void onProximityUpdate(@NonNull float[] proximities, @NonNull View view) {
//
//    }
}
*/
