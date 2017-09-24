package com.yunxiweather.android;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.yunxiweather.android.gson.Weather;
import com.yunxiweather.android.service.Notification;
import com.yunxiweather.android.util.Utility;

/**
 * Created by Xuan on 2017/9/24.
 */
public class NotifiSettingActivity extends AppCompatActivity{

    public static String NTFCT_FLAG = "1";
    private RadioButton notifiStyle1;
    private RadioButton notifiStyle2;
    private RadioButton notifiStyle3;
    private RadioGroup notifiRadioGroup;
    private Weather weather;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notifi_setting);
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        String weatherString = preferences.getString("weather", null);
        weather = Utility.handleWeatherResponse(weatherString);
        initToolBar();
        initNotifiStyle();
    }

    private void initNotifiStyle() {
        notifiRadioGroup = (RadioGroup) findViewById(R.id.notifi_radio_group);
        notifiStyle1 = (RadioButton) findViewById(R.id.notifi_style1);
        notifiStyle2 = (RadioButton) findViewById(R.id.notifi_style2);
        notifiStyle3 = (RadioButton) findViewById(R.id.notifi_style3);
        notifiRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == notifiStyle1.getId()) {
                    NTFCT_FLAG = notifiStyle1.getText().toString();
                } else if (checkedId == notifiStyle2.getId()) {
                    NTFCT_FLAG = notifiStyle2.getText().toString();
                } else if (checkedId == notifiStyle3.getId()) {
                    NTFCT_FLAG = notifiStyle3.getText().toString();
                }
                Intent serviceIntent = new Intent(getApplicationContext(), Notification.class);
                stopService(serviceIntent);
                startService(serviceIntent);
            }
        });

        switch (NTFCT_FLAG) {
            case "1":
                notifiStyle1.setChecked(true);
                break;
            case "2":
                notifiStyle2.setChecked(true);
                break;
            case "3":
                notifiStyle3.setChecked(true);
                break;
            default:
        }
    }

    private void initToolBar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.notification_setting_toolbar);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
