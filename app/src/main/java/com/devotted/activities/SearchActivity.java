package com.devotted.activities;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.ImageView;

import com.devotted.R;
import com.devotted.fragments.BaseFragment;
import com.devotted.fragments.CommentsBottomSheetFragment;
import com.devotted.fragments.FilterBottomSheetFragment;
import com.devotted.fragments.MapListFragment;
import com.devotted.fragments.SearchListFragment;
import com.devotted.fragments.SortBottomSheetFragment;
import com.devotted.utils.Constants;
import com.devotted.utils.RuntimePermissionUtils;
import com.devotted.utils.StaticUtils;
import com.devotted.utils.views.CustomEditText;
import com.devotted.utils.views.CustomTextView;

public class SearchActivity extends BaseActivity implements View.OnClickListener, LocationListener,
        SortBottomSheetFragment.OnSortOptionSelected, FilterBottomSheetFragment.OnFilterOptionSelected, CommentsBottomSheetFragment.OnComments {

    private ImageView imgBack, imgSwitch;
    private CustomEditText edtSearch;
    private CustomTextView txtSort, txtFilter, txtFavourites;
    private String currentFragment = "";
    public static Location mLastLocation;
    public LocationManager mLocationManager;
    private boolean isLocationAlreadyFetched = false;
    private int checkedSortId = -1;
    private SortBottomSheetFragment sortBySortBottomSheetFragment;
    private FilterBottomSheetFragment filterSortBottomSheetFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        changeStatusBarColorToOrange();
        setContentView(R.layout.activity_search);
        checkPermissions();
        initComponents();
    }

    private void initComponents() {
        mLocationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        setReferences();

        currentFragment = MapListFragment.class.getSimpleName();
        replaceFragmentWithOutAnimation(new SearchListFragment(), false, R.id.searchContainer);
    }

    private void changeFragment(BaseFragment fragment) {
        replaceFragment(fragment, false, R.id.searchContainer);
        if (currentFragment.equalsIgnoreCase(MapListFragment.class.getSimpleName())) {
            imgSwitch.setImageResource(R.drawable.ic_map);
        } else {
            imgSwitch.setImageResource(R.drawable.ic_topbar_menu);
        }
        currentFragment = fragment.getClass().getSimpleName();
    }

    private void setReferences() {
        txtSort = findViewById(R.id.txtSort);
        txtFilter = findViewById(R.id.txtFilter);
        txtFavourites = findViewById(R.id.txtFavourites);

        edtSearch = findViewById(R.id.edtSearch);

        imgBack = findViewById(R.id.imgBack);
        imgSwitch = findViewById(R.id.imgSwitch);

        setListeners();
    }

    private void setListeners() {
        imgBack.setOnClickListener(this);
        txtFavourites.setOnClickListener(this);
        txtSort.setOnClickListener(this);
        txtFilter.setOnClickListener(this);
        imgSwitch.setOnClickListener(this);
        edtSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        StaticUtils.hideSoftKeyboard(this);
    }

    private void showSortBottomSheet() {
        sortBySortBottomSheetFragment = SortBottomSheetFragment.newInstance(checkedSortId);
        sortBySortBottomSheetFragment.show(getSupportFragmentManager(), sortBySortBottomSheetFragment.getTag());
    }

    private void showFilterBottomSheet() {
        filterSortBottomSheetFragment = new FilterBottomSheetFragment();
        filterSortBottomSheetFragment.show(getSupportFragmentManager(), filterSortBottomSheetFragment.getTag());
    }

    private void checkPermissions() {
        if (RuntimePermissionUtils.checkPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            RuntimePermissionUtils.requestForPermission(this, Manifest.permission.ACCESS_FINE_LOCATION, Constants.LOCATION_PERMISSION);
        } else {
            getCurrentLocation();
        }
    }

    @SuppressLint("MissingPermission")
    private void getCurrentLocation() {
        if (mLocationManager == null)
            mLocationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        boolean isGPSEnabled = mLocationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        boolean isNetworkEnabled = mLocationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
        if (!(isGPSEnabled || isNetworkEnabled))
            StaticUtils.showToast(SearchActivity.this, getString(R.string.error_fetching_location_from_the_provider));
        else {
            if (isNetworkEnabled) {
                mLocationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 60000, 10, this);
                mLastLocation = mLocationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
            }

            if (isGPSEnabled) {
                mLocationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 60000, 10, this);
                mLastLocation = mLocationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            }
        }
        if (mLastLocation != null && !isLocationAlreadyFetched) {
            setTextToAddress();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (StaticUtils.isAllPermissionsGranted(grantResults)) {
            getCurrentLocation();
        } else {
            StaticUtils.showToast(this, getString(R.string.location_permission_mandatory_to_access_your_location));
            checkPermissions();
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.imgBack:
                onBackPressed();
                break;
            case R.id.imgSwitch:
                changeFragment(currentFragment.equalsIgnoreCase(MapListFragment.class.getSimpleName()) ? new SearchListFragment() : new MapListFragment());
                break;
            case R.id.txtFilter:
                showFilterBottomSheet();
                break;
            case R.id.txtSort:
                showSortBottomSheet();
                break;
            case R.id.txtFavourites:
                break;
            default:
                break;
        }
    }

    @Override
    public void onLocationChanged(Location location) {
        mLastLocation = location;
        if (mLastLocation != null && !isLocationAlreadyFetched) {
            setTextToAddress();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        mLocationManager.removeUpdates(this);
    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {

    }

    @Override
    public void onProviderEnabled(String s) {

    }

    @Override
    public void onProviderDisabled(String s) {

    }

    @Override
    public void sortOptionSelected(int checkedId) {
        if (sortBySortBottomSheetFragment != null) {
            switch (checkedId) {
                case R.id.rbPopularity:
                    StaticUtils.showToast(this, getString(R.string.you_selected_popularity));
                    break;
                case R.id.rbAlphabetical:
                    StaticUtils.showToast(this, getString(R.string.you_selected_alphabetical));
                    break;
                case R.id.rbDistance:
                    StaticUtils.showToast(this, getString(R.string.you_selected_distance));
                    break;
                case R.id.rbRatings:
                    StaticUtils.showToast(this, getString(R.string.you_selected_ratings));
                    break;
                default:
                    break;
            }
            checkedSortId = checkedId;
            sortBySortBottomSheetFragment.dismiss();
        }
    }

    @Override
    public void filterCallback(int checkedId) {

    }

    private void setTextToAddress() {
        BaseFragment fragment = (BaseFragment) getSupportFragmentManager().findFragmentById(R.id.searchContainer);
        if (fragment instanceof SearchListFragment) {
            SearchListFragment searchListFragment = (SearchListFragment) fragment;
            searchListFragment.updateLocation(StaticUtils.getAddress(this, mLastLocation.getLatitude(), mLastLocation.getLongitude()));
            isLocationAlreadyFetched = true;
        }
    }

    @Override
    public void onComments() {

    }

}
