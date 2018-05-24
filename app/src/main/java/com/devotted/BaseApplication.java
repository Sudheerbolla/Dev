package com.devotted;

import android.content.Context;
import android.support.multidex.MultiDexApplication;

import com.devotted.utils.LocalStorage;
import com.devotted.utils.LocaleHelper;

public class BaseApplication extends MultiDexApplication {

    private static BaseApplication mInstance;

    public static synchronized BaseApplication getInstance() {
        if (mInstance == null) mInstance = new BaseApplication();
        return mInstance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        changeLanguage(getApplicationContext(), LocalStorage.getInstance(getApplicationContext()).getString(LocalStorage.PREF_LANGUAGE, "en"));
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(LocaleHelper.onAttach(base, "en"));
    }

    public Context changeLanguage(Context context, String languageCode) {
        return LocaleHelper.setLocale(context, languageCode);
    }
}
