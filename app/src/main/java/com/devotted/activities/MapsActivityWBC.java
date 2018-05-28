/*
package com.devotted.activities;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.devotted.R;
import com.devotted.adapters.MapAdapter;
import com.devotted.models.AddressModelStatic;
import com.devotted.utils.Constants;
import com.devotted.utils.RuntimePermissionUtils;
import com.devotted.utils.StaticUtils;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.maps.android.ui.IconGenerator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MapsActivityWBC extends AppCompatActivity implements GoogleMap.OnMarkerClickListener, GoogleMap.OnInfoWindowClickListener, GoogleMap.OnMarkerDragListener, OnMapReadyCallback, GoogleMap.OnMapClickListener {

    private static boolean flag = true;
    public ArrayList<Marker> mMarkerList = new ArrayList<>();
    Marker prevMarker;
    String prevVendorName;
    boolean doubleBackToExitPressedOnce = false;
    private Map<String, AddressModelStatic> mDealMap = new HashMap<>();
    private List<AddressModelStatic> myDealsList = new ArrayList<>();
    private GoogleMap mMap;

    private ViewPager mViewPager;
    private MapAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        checkPermissions();
    }

    private void checkPermissions() {
        if (RuntimePermissionUtils.checkPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            RuntimePermissionUtils.requestForPermission(this, Manifest.permission.ACCESS_FINE_LOCATION, Constants.LOCATION_PERMISSION);
        } else {
            initComponents();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (StaticUtils.isAllPermissionsGranted(grantResults)) {
            initComponents();
        } else {
            StaticUtils.showToast(this, "Permission Manditory to access Maps");
            checkPermissions();
        }
    }

    private void initComponents() {
        mMarkerList = new ArrayList<>();
        myDealsList = new ArrayList<>();
        mDealMap = new HashMap<>();

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        mViewPager = findViewById(R.id.vp_details);
        mViewPager.setPadding(16, 0, 16, 0);
        mViewPager.setClipToPadding(false);
        mViewPager.setPageMargin(8);

        mAdapter = new MapAdapter(getSupportFragmentManager(), myDealsList, this);
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
                    final AddressModelStatic temp = myDealsList.get(position);
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
            //final AddressModel temp = myDealsList.get(Integer.parseInt(marker.getSnippet()));
            // mViewPager.setCurrentItem(Integer.parseInt(marker.getSnippet()));
            final AddressModelStatic temp = myDealsList.get(Integer.parseInt(aid));
            mViewPager.setCurrentItem(Integer.parseInt(aid));

            if (prevMarker != null) {
                //Set prevMarker back to default color
                IconGenerator iconFactory = new IconGenerator(this);
                iconFactory.setRotation(0);
                iconFactory.setBackground(null);
                View view = View.inflate(this, R.layout.map_marker_text, null);
                TextView tvVendorTitle;

                tvVendorTitle = view.findViewById(R.id.tv_vendor_title);
                tvVendorTitle.setText(prevVendorName);
                tvVendorTitle.setBackground(getResources().getDrawable(R.mipmap.map_pin_white));
                tvVendorTitle.setTextColor(Color.parseColor("#0097a9"));

                iconFactory.setContentView(view);

                prevMarker.setIcon(BitmapDescriptorFactory.fromBitmap(iconFactory.makeIcon(temp.rating)));

            }

            //leave Marker default color if re-click current Marker
            if (!marker.equals(prevMarker)) {

                IconGenerator iconFactory = new IconGenerator(this);
                iconFactory.setRotation(0);
                iconFactory.setBackground(null);
                View view = View.inflate(this, R.layout.map_marker_text, null);
                TextView tvVendorTitle;

                tvVendorTitle = view.findViewById(R.id.tv_vendor_title);
                tvVendorTitle.setText(myDealsList.get(Integer.parseInt(marker.getSnippet())).rating);


                tvVendorTitle.setBackground(getResources().getDrawable(R.mipmap.map_pin_green));
                tvVendorTitle.setCompoundDrawablesWithIntrinsicBounds(getResources().getDrawable(R.drawable.star_icon_white), null, null, null);
                tvVendorTitle.setTextColor(Color.parseColor("#FFFFFF"));
                iconFactory.setContentView(view);
                //
                marker.setIcon(BitmapDescriptorFactory.fromBitmap(iconFactory.makeIcon(temp.rating)));
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
        myDealsList = new ArrayList<>();

        for (int i = 1; i < 5; i++) {
            myDealsList.add(new AddressModelStatic(i));
        }

        mAdapter = new MapAdapter(getSupportFragmentManager(), myDealsList, this);
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
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
        }
        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show();
        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce = false;
            }
        }, 2000);
    }

}
*/
