package com.speak.voice.translate.AdsUtils.PreferencesManager;

import android.content.Context;

import com.speak.voice.translate.AdsUtils.FirebaseADHandlers.AdsJsonPOJO;
import com.speak.voice.translate.AdsUtils.Utils.Constants;
import com.google.gson.Gson;

public class AppPreferencesManger {
    private AppPreferences appPreference;

    public AppPreferencesManger(Context context) {
        appPreference = AppPreferences.getAppPreferences(context);
    }

    public void setAdsModel(AdsJsonPOJO adsJsonPOJO) {
        appPreference.putString(Constants.ADSJSON, new Gson().toJson(adsJsonPOJO));
    }
    public String getAdsModel() {
        return appPreference.getString(Constants.ADSJSON,"");
    }

}
