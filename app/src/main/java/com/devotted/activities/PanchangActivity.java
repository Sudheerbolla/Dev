package com.devotted.activities;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.ImageView;

import com.devotted.R;
import com.devotted.utils.ShareUtils;
import com.devotted.utils.StaticUtils;
import com.devotted.utils.views.CustomTextView;

import java.util.Calendar;

public class PanchangActivity extends BaseActivity implements View.OnClickListener, DatePickerDialog.OnDateSetListener {

    private ImageView imgBack, imgShare;
    private CustomTextView txtDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        changeStatusBarColorToOrange();
        setContentView(R.layout.activity_panchang);
        initComponents();
    }

    private void initComponents() {
        setReferences();
    }

    DatePickerDialog datePickerDialog;

    private void setReferences() {
        Calendar calendar = Calendar.getInstance();
        datePickerDialog = new DatePickerDialog(this, this, calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH) + 1, calendar.get(Calendar.DATE));
        imgBack = findViewById(R.id.imgBack);
        imgShare = findViewById(R.id.imgShare);
        txtDate = findViewById(R.id.txtDate);

        txtDate.setText(StaticUtils.getDisplayDate(calendar));
        setListeners();
    }

    private void setListeners() {
        imgBack.setOnClickListener(this);
        imgShare.setOnClickListener(this);
        txtDate.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.imgBack:
                onBackPressed();
                break;
            case R.id.imgShare:
                ShareUtils.shareViaIntent(this, getString(R.string.temple_details), getString(R.string.sharing_temple_details));
                break;
            case R.id.txtDate:
                datePickerDialog.show();
                break;
            default:
                break;
        }
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

    }
}
