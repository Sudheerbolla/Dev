package com.devotted.fragments;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.devotted.R;
import com.devotted.activities.SearchActivity;
import com.devotted.adapters.MapAdapter;
import com.devotted.models.TempleModel;
import com.devotted.utils.RuntimePermissionUtils;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MapListFragment extends BaseFragment implements GoogleMap.OnMarkerClickListener, GoogleMap.OnInfoWindowClickListener, GoogleMap.OnMarkerDragListener, OnMapReadyCallback, GoogleMap.OnMapClickListener {


    private static boolean flag = true;
    public ArrayList<Marker> mMarkerList;
    Marker prevMarker;
    String prevVendorName;
    private Map<String, TempleModel> mDealMap = new HashMap<>();
    private List<TempleModel> myDealsList = new ArrayList<>();
    private GoogleMap mMap;
    private ViewPager mViewPager;
    private MapAdapter mAdapter;

    private SearchActivity searchActivity;
    private View rootView;

    private MapView locationsMap;

    public MapListFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        searchActivity = (SearchActivity) getActivity();
        mMarkerList = new ArrayList<>();
        myDealsList = new ArrayList<>();
        mDealMap = new HashMap<>();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_maps_list, container, false);
        initComponents(savedInstanceState);
        return rootView;
    }

    private void initComponents(Bundle savedInstanceState) {
        MapsInitializer.initialize(searchActivity);
        setReferences();
        locationsMap.onCreate(savedInstanceState);
        locationsMap.getMapAsync(this);
        mViewPager.setPadding(16, 0, 16, 0);
        mViewPager.setClipToPadding(false);
        mViewPager.setPageMargin(8);
        mAdapter = new MapAdapter(getChildFragmentManager(), myDealsList, searchActivity);
        mViewPager.setAdapter(mAdapter);
        mAdapter.notifyDataSetChanged();

        mViewPager.setOffscreenPageLimit(3);

        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (flag) {
                    flag = false;
                    final TempleModel temp = myDealsList.get(position);
                    LatLng newLatLng = new LatLng(Double.parseDouble(temp.latitude), Double.parseDouble(temp.longitude));
                    mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(newLatLng, 16));
                    Marker marker = mMarkerList.get(position);
                    if (prevMarker != null) {
                        prevMarker.setIcon(BitmapDescriptorFactory.fromResource(R.drawable.marker_black));
                    }
                    if (!marker.equals(prevMarker)) {
                        marker.setIcon(BitmapDescriptorFactory.fromResource(R.drawable.marker_orange));
                        prevMarker = marker;
                        prevVendorName = myDealsList.get(position).rating;
                    }
                    prevMarker = marker;
                    prevVendorName = myDealsList.get(position).rating;
                    flag = true;
                } else {
                    Log.i("", "" + mMarkerList);
                    Log.i("", "" + position);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }

    private void setReferences() {
        mViewPager = rootView.findViewById(R.id.vp_details);
        locationsMap = rootView.findViewById(R.id.locationsMap);
    }

    @Override
    public void onInfoWindowClick(Marker marker) {

    }

    @Override
    public void onMapClick(LatLng latLng) {
        mViewPager.setVisibility(View.GONE);
    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        if (flag) {
            flag = false;
            mViewPager.setVisibility(View.VISIBLE);
            String aid = marker.getId().substring(1, marker.getId().length());
            final TempleModel temp = myDealsList.get(Integer.parseInt(aid));
            mViewPager.setCurrentItem(Integer.parseInt(aid));

            if (prevMarker != null) {
                prevMarker.setIcon(BitmapDescriptorFactory.fromResource(R.drawable.marker_black));
            }
            if (!marker.equals(prevMarker)) {
                marker.setIcon(BitmapDescriptorFactory.fromResource(R.drawable.marker_orange));
                prevMarker = marker;
                prevVendorName = myDealsList.get(Integer.parseInt(marker.getSnippet())).rating;

            }
            prevMarker = marker;
            prevVendorName = myDealsList.get(Integer.parseInt(marker.getSnippet())).rating;
            mMap.moveCamera(CameraUpdateFactory.newLatLng(marker.getPosition()));
            flag = true;
        }
        return false;
    }

    @Override
    public void onMarkerDragStart(Marker marker) {

    }

    @Override
    public void onMarkerDrag(Marker marker) {

    }

    @Override
    public void onMarkerDragEnd(Marker marker) {

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setOnMarkerClickListener(this);
        mMap.setOnMapClickListener(this);
        if (RuntimePermissionUtils.checkPermission(searchActivity, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            mMap.setMyLocationEnabled(true);
        }

        mMap.getUiSettings().setMyLocationButtonEnabled(true);
        myDealsList = new ArrayList<>();

        for (int i = 1; i < 5; i++) {
            myDealsList.add(new TempleModel(i));
        }

        mAdapter = new MapAdapter(getChildFragmentManager(), myDealsList, searchActivity);
        mViewPager.setAdapter(mAdapter);

        for (int j = 0; j < myDealsList.size(); j++) {
            LatLng newLatLngTemp = new LatLng(Double.parseDouble(myDealsList.get(j).latitude), Double.parseDouble(myDealsList.get(j).longitude));

            MarkerOptions options = new MarkerOptions();
//            IconGenerator iconFactory = new IconGenerator(this);
//            iconFactory.setRotation(0);
//            iconFactory.setBackground(null);
//
//            View view = View.inflate(this, R.layout.map_marker_text, null);
//            TextView tvVendorTitle;
//            tvVendorTitle = view.findViewById(R.id.tv_vendor_title);
//            tvVendorTitle.setText(myDealsList.get(j).rating);
//            iconFactory.setContentView(view);
            options.position(newLatLngTemp);
            options.snippet(String.valueOf(j));

            Marker mapMarker = mMap.addMarker(options);
            mapMarker.setIcon(BitmapDescriptorFactory.fromResource(R.drawable.marker_black));

            mMarkerList.add(mapMarker);
            mDealMap.put(myDealsList.get(j).rating, myDealsList.get(j));

        }
        if (myDealsList.size() > 0) {
            LatLng latlngOne = new LatLng(Double.parseDouble(myDealsList.get(0).latitude), Double.parseDouble(myDealsList.get(0).longitude));
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latlngOne, 16));
        }

        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        if (locationsMap != null) locationsMap.onSaveInstanceState(outState);
    }

    @Override
    public void onPause() {
        super.onPause();
        if (locationsMap != null) locationsMap.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (locationsMap != null) locationsMap.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        if (locationsMap != null) locationsMap.onLowMemory();
    }

    @Override
    public void onResume() {
        super.onResume();
        if (locationsMap != null) locationsMap.onResume();
    }

}
