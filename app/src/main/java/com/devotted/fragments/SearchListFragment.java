package com.devotted.fragments;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.devotted.R;
import com.devotted.activities.SearchActivity;
import com.devotted.adapters.ViewPagerAdapter;
import com.devotted.utils.Constants;
import com.devotted.utils.StaticUtils;
import com.devotted.utils.views.CustomTextView;
import com.devotted.utils.views.CustomViewPager;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.places.AutocompleteFilter;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocomplete;

public class SearchListFragment extends BaseFragment implements View.OnClickListener {

    private SearchActivity searchActivity;
    private View rootView;
    private TabLayout tabLayout;
    private CustomViewPager viewPager;

    private CustomTextView txtChange, txtLocation;

    public SearchListFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        searchActivity = (SearchActivity) getActivity();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_simple_list, container, false);
        initComponents();
        return rootView;
    }

    private void initComponents() {
        StaticUtils.hideSoftKeyboard(searchActivity);
        txtChange = rootView.findViewById(R.id.txtChange);
        txtLocation = rootView.findViewById(R.id.txtLocation);
        viewPager = rootView.findViewById(R.id.viewpager);
        tabLayout = rootView.findViewById(R.id.tabs);

        setupViewPager(viewPager);
        tabLayout.setupWithViewPager(viewPager, true);

        setupTabIcons();
        txtChange.setOnClickListener(this);
    }

    public void updateLocation(String text) {
        if (!TextUtils.isEmpty(text) && txtLocation != null) txtLocation.setText(text);
    }

    private void setupViewPager(CustomViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getChildFragmentManager());
        adapter.addFrag(TemplesListFragment.newInstance(), getString(R.string.temple));
        adapter.addFrag(PostsFragment.newInstance(getString(R.string.post)), getString(R.string.post));
        adapter.addFrag(PostsFragment.newInstance(getString(R.string.event)), getString(R.string.event));
        viewPager.setAdapter(adapter);

        viewPager.setPagingEnabled(true);
        viewPager.setOffscreenPageLimit(3);
    }

    @SuppressLint("InflateParams")
    private void setupTabIcons() {
        CustomTextView tabOne = (CustomTextView) LayoutInflater.from(searchActivity).inflate(R.layout.layout_home_tab, null);
        tabOne.setText(getString(R.string.temple));
        tabOne.setCompoundDrawablesWithIntrinsicBounds(R.drawable.temple_selector, 0, 0, 0);
        tabLayout.getTabAt(0).setCustomView(tabOne);

        CustomTextView tabTwo = (CustomTextView) LayoutInflater.from(searchActivity).inflate(R.layout.layout_home_tab, null);
        tabTwo.setText(getString(R.string.post));
        tabTwo.setCompoundDrawablesWithIntrinsicBounds(R.drawable.post_selector, 0, 0, 0);
        tabLayout.getTabAt(1).setCustomView(tabTwo);

        CustomTextView tabThree = (CustomTextView) LayoutInflater.from(searchActivity).inflate(R.layout.layout_home_tab, null);
        tabThree.setText(getString(R.string.event));
        tabThree.setCompoundDrawablesWithIntrinsicBounds(R.drawable.event_selector, 0, 0, 0);
        tabLayout.getTabAt(2).setCustomView(tabThree);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.txtChange:
                openSearch();
                break;
            default:
                break;
        }
    }

    private void openSearch() {
        try {
            AutocompleteFilter typeFilter = new AutocompleteFilter.Builder()
                    .setTypeFilter(AutocompleteFilter.TYPE_FILTER_NONE)
                    .setCountry("IN")
                    .build();
            Intent intent = new PlaceAutocomplete.IntentBuilder(PlaceAutocomplete.MODE_OVERLAY)
//                    .setBoundsBias(new LatLngBounds(new LatLng(17.445026, 78.376708), new LatLng(17.514235, 78.379242)))
                    .setFilter(typeFilter)
                    .build(searchActivity);
            startActivityForResult(intent, Constants.PLACE_AUTOCOMPLETE_REQUEST_CODE);
        } catch (GooglePlayServicesRepairableException e) {
            e.printStackTrace();
        } catch (GooglePlayServicesNotAvailableException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Constants.PLACE_AUTOCOMPLETE_REQUEST_CODE) {
            Log.e("Places Api ", "PLACE_AUTOCOMPLETE_REQUEST_CODE");
            switch (resultCode) {
                case SearchActivity.RESULT_OK:
                    Place place = PlaceAutocomplete.getPlace(searchActivity, data);
                    Log.e("Place: ", place.getName().toString());
                    setTextToAddress(place.getName().toString() + ", " + place.getAddress() + ", " + place.getLatLng());
                    break;
                case PlaceAutocomplete.RESULT_ERROR:
                    Status status = PlaceAutocomplete.getStatus(searchActivity, data);
                    // TODO: Handle the error.
                    Log.e("error", status.getStatusMessage());
                    break;
                case SearchActivity.RESULT_CANCELED:
                    // The user canceled the operation.
                    break;
            }
        }
    }

    private void setTextToAddress(String string) {
        updateLocation(string);
    }

}
