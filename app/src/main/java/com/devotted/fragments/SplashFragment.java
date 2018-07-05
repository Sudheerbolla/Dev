package com.devotted.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.devotted.R;
import com.devotted.activities.MainActivity;
import com.devotted.activities.SplashActivity;
import com.devotted.utils.LocalStorage;
import com.devotted.utils.views.CustomTextView;

public class SplashFragment extends BaseFragment {

    private View rootView;
    private SplashActivity splashActivity;
    private ImageView imgLogo;
    private LinearLayout img1, img2, img3;
    private CustomTextView txtLogo;

    public SplashFragment() {
    }

    public static SplashFragment newInstance() {
        SplashFragment categoryDetailsFragment = new SplashFragment();
        categoryDetailsFragment.setArguments(new Bundle());
        return categoryDetailsFragment;
    }

    public static SplashFragment newInstance(Bundle bundle) {
        SplashFragment categoryDetailsFragment = new SplashFragment();
        categoryDetailsFragment.setArguments(bundle);
        return categoryDetailsFragment;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_splash, container, false);
        initComponents();
        return rootView;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        splashActivity = (SplashActivity) getActivity();
    }

    private void initComponents() {
        setReferences();
        animateLogos();
    }

    private void animateLogos() {
        Animation uptodown = AnimationUtils.loadAnimation(splashActivity, R.anim.slide_down);
        imgLogo.setAnimation(uptodown);

        Animation downtoup = AnimationUtils.loadAnimation(splashActivity, R.anim.slide_up);
        txtLogo.setAnimation(downtoup);

        boolean isFirstTime = LocalStorage.getInstance(splashActivity).getBoolean(LocalStorage.IS_FIRST_TIME_LAUNCH, true);
        if (isFirstTime) {
            final Runnable img3Runnable = new Runnable() {
                @Override
                public void run() {
                    fadeInXml(img3);
                    img3.setVisibility(View.VISIBLE);
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            navigateToLogin();
                        }
                    }, 1800);
                }
            };
            final Runnable img2Runnable = new Runnable() {
                @Override
                public void run() {
                    fadeInXml(img2);
                    img2.setVisibility(View.VISIBLE);
                    new Handler().postDelayed(img3Runnable, 1000);
                }
            };
            Runnable img1Runnable = new Runnable() {
                @Override
                public void run() {
                    fadeInXml(img1);
                    img1.setVisibility(View.VISIBLE);
                    new Handler().postDelayed(img2Runnable, 1000);
                }
            };

            new Handler().postDelayed(img1Runnable, 1000);
        } else {
            img1.setVisibility(View.VISIBLE);
            img2.setVisibility(View.VISIBLE);
            img3.setVisibility(View.VISIBLE);
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    navigateToLogin();
                }
            }, 2500);
        }
    }

    private void fadeInXml(View yourView) {
        yourView.startAnimation(AnimationUtils.loadAnimation(splashActivity, R.anim.fadein));
    }

    private void setReferences() {
        imgLogo = rootView.findViewById(R.id.imgLogo);
        img1 = rootView.findViewById(R.id.img1);
        img2 = rootView.findViewById(R.id.img2);
        img3 = rootView.findViewById(R.id.img3);
        txtLogo = rootView.findViewById(R.id.txtLogo);
    }

    private void navigateToLogin() {
        if (LocalStorage.getInstance(splashActivity).getBoolean(LocalStorage.IS_FIRST_TIME_LAUNCH, true)) {
            splashActivity.replaceFragment(IntroFragment.newInstance(), false, R.id.splashContainer);
        } else if (LocalStorage.getInstance(splashActivity).getBoolean(LocalStorage.IS_LOGGED_IN_ALREADY, false)) {
            startActivity(new Intent(splashActivity, MainActivity.class));
            splashActivity.finishAffinity();
        } else {
            splashActivity.replaceFragment(LandingFragment.newInstance(new Bundle()), false, R.id.splashContainer);
        }
    }

}
