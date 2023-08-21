package com.speak.voice.translate.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.speak.voice.translate.AdsUtils.FirebaseADHandlers.AdUtils;
import com.speak.voice.translate.AdsUtils.FirebaseADHandlers.AdsJsonPOJO;
import com.speak.voice.translate.AdsUtils.FirebaseADHandlers.FirebaseUtils;
import com.speak.voice.translate.AdsUtils.Interfaces.AppInterfaces;
import com.speak.voice.translate.AdsUtils.PreferencesManager.AppPreferencesManger;
import com.speak.voice.translate.AdsUtils.Utils.Constants;
import com.speak.voice.translate.R;
import com.speak.voice.translate.base.BaseActivity;
import com.google.firebase.messaging.FirebaseMessaging;
import com.speak.voice.translate.utils.Utils;

import butterknife.BindView;

public class SplashActivity extends BaseActivity {
    @BindView(R.id.mIvLogo)
    ImageView mIvLogo;
    @BindView(R.id.mIvTitle)
    TextView mIvTitle;
    public static SplashActivity splashActivity;

    public static SplashActivity getInstance() {
        return splashActivity;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_splash;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        splashActivity = this;

        final AppPreferencesManger appPreferencesManger = new AppPreferencesManger(this);

        FirebaseMessaging.getInstance().subscribeToTopic(Constants.ADSJSON);

        Constants.adsJsonPOJO = Utils.getAdsData(appPreferencesManger.getAdsModel());

        if (Constants.adsJsonPOJO != null && !Utils.isEmptyStr(Constants.adsJsonPOJO.getParameters().getApp_open_ad().getDefaultValue().getValue())) {
            Constants.adsJsonPOJO = Utils.getAdsData(appPreferencesManger.getAdsModel());
            Constants.hitCounter = Integer.parseInt(Constants.adsJsonPOJO.getParameters().getApp_open_ad().getDefaultValue().getHits());
            AdUtils.showAppOpenAd(splashActivity, new AppInterfaces.AppOpenADInterface() {
                @Override
                public void appOpenAdState(boolean state_load) {
//                    rltest.setVisibility(View.VISIBLE);
                    mStartAct();
                }
            });

        } else {
            FirebaseUtils.initiateAndStoreFirebaseRemoteConfig(splashActivity, new AppInterfaces.AdDataInterface() {
                @Override
                public void getAdData(AdsJsonPOJO adsJsonPOJO) {
                    //Need to call this only once per
                    appPreferencesManger.setAdsModel(adsJsonPOJO);
                    Constants.adsJsonPOJO = adsJsonPOJO;
                    Constants.hitCounter = Integer.parseInt(Constants.adsJsonPOJO.getParameters().getApp_open_ad().getDefaultValue().getHits());
                    AdUtils.showAppOpenAd(splashActivity, new AppInterfaces.AppOpenADInterface() {
                        @Override
                        public void appOpenAdState(boolean state_load) {
//                            rltest.setVisibility(View.VISIBLE);
                            mStartAct();
                        }
                    });
                }
            });

        }
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    private void mStartAct() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (Utils.isNetworkAvailable(SplashActivity.this)) {
                    startActivity(new Intent(SplashActivity.this, IntroActivity.class));
                    finish();
                } else {
                    Toast.makeText(SplashActivity.this, "Turn On Internet..!", Toast.LENGTH_SHORT).show();
                }
            }
        }, 1000);

    }
}