package com.devotted.activities;

import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.view.WindowManager;

import com.devotted.R;
import com.devotted.utils.FragmentUtils;
import com.devotted.utils.LocaleHelper;
import com.devotted.utils.StaticUtils;

import java.util.Locale;

public class BaseActivity extends AppCompatActivity {

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        changeStatusBarColor();
    }

    public void changeStatusBarColor() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
        }
    }

    public void changeStatusBarColorToOrange() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(getResources().getColor(R.color.colorPrimaryDark));
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onResumeFragments() {
        super.onResumeFragments();
    }

    public void replaceFragment(Fragment fragment, boolean needToAddToBackStack) {
        StaticUtils.hideSoftKeyboard(this);
        String tag = fragment.getClass().getSimpleName();
        final FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        setCustomAnimation(fragmentTransaction, false);
        if (needToAddToBackStack)
            fragmentTransaction.replace(R.id.splashContainer, fragment, tag).addToBackStack(tag).commitAllowingStateLoss();
        else
            fragmentTransaction.replace(R.id.splashContainer, fragment, tag).commitAllowingStateLoss();
    }

    public void replaceFragment(Fragment fragment) {
        StaticUtils.hideSoftKeyboard(this);
        String tag = fragment.getClass().getSimpleName();
        final FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        setCustomAnimation(fragmentTransaction, false);
        fragmentTransaction.replace(R.id.splashContainer, fragment, tag).addToBackStack(tag).commitAllowingStateLoss();
    }

    public void clearAndReplaceFragment(Fragment fragment) {
        clearBackStack();
        StaticUtils.hideSoftKeyboard(this);
        String tag = fragment.getClass().getSimpleName();
        final FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        setCustomAnimation(fragmentTransaction, false);
        fragmentTransaction.replace(R.id.splashContainer, fragment, tag).addToBackStack(tag).commitAllowingStateLoss();
    }

    public void replaceFragment(Fragment fragment, boolean needToAddToBackStack, int containerId) {
        StaticUtils.hideSoftKeyboard(this);
        String tag = fragment.getClass().getSimpleName();
        final FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();

        setCustomAnimation(fragmentTransaction, false);
        if (needToAddToBackStack)
            fragmentTransaction.replace(containerId, fragment, tag).addToBackStack(tag).commitAllowingStateLoss();
        else
            fragmentTransaction.replace(containerId, fragment, tag).commitAllowingStateLoss();
    }

    public void replaceFragmentWithOutAnimation(Fragment fragment, boolean needToAddToBackStack, int containerId) {
        StaticUtils.hideSoftKeyboard(this);
        String tag = fragment.getClass().getSimpleName();
        final FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        if (needToAddToBackStack)
            fragmentTransaction.replace(containerId, fragment, tag).addToBackStack(tag).commitAllowingStateLoss();
        else
            fragmentTransaction.replace(containerId, fragment, tag).commitAllowingStateLoss();
    }

    public void replaceFragment(Fragment fragment, int containerId) {
        StaticUtils.hideSoftKeyboard(this);
        String tag = fragment.getClass().getSimpleName();
        final FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        setCustomAnimation(fragmentTransaction, false);
        fragmentTransaction.replace(containerId, fragment, tag).addToBackStack(tag).commitAllowingStateLoss();
    }

    public void clearBackStack() {
        FragmentManager fragment = getSupportFragmentManager();
        fragment.popBackStackImmediate(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
    }

    public void clearBackStackCompletely() {
        FragmentUtils.sDisableFragmentAnimations = true;
        FragmentManager fragment = getSupportFragmentManager();
        fragment.popBackStackImmediate(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
        FragmentUtils.sDisableFragmentAnimations = false;
    }

    public void popBackStack() {
        StaticUtils.hideSoftKeyboard(this);
        FragmentManager fragment = getSupportFragmentManager();
        setCustomAnimation(fragment.beginTransaction(), true);
        fragment.popBackStackImmediate();
    }

    private static void setCustomAnimation(FragmentTransaction ft, boolean reverseAnimation) {
        if (!reverseAnimation) {
            ft.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left, R.anim.enter_from_left, R.anim.exit_to_right);
        } else {
            ft.setCustomAnimations(R.anim.enter_from_left, R.anim.exit_to_right, R.anim.enter_from_right, R.anim.exit_to_left);
        }
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(LocaleHelper.onAttach(newBase));
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        // refresh your views here
//        lblLang.setText(R.string.langselection);
        super.onConfigurationChanged(newConfig);
//        getBaseContext().getResources().updateConfiguration(newConfig, getBaseContext().getResources().getDisplayMetrics());
//        setContentView(R.layout.main);
// Checks the active language
        if (newConfig.locale == Locale.ENGLISH) {
            StaticUtils.showToast(this, getString(R.string.english));
        } else if (newConfig.locale == new Locale("tl")) {
            StaticUtils.showToast(this, getString(R.string.telugu));
        } else if (newConfig.locale == new Locale("hi")) {
            StaticUtils.showToast(this, getString(R.string.hindi));
        }
    }
}
