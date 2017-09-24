package com.yunxiweather.android;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.yunxiweather.android.service.Notification;
import com.yunxiweather.android.util.ConstanValue;
import com.yunxiweather.android.util.SpUtils;

/**
 * Created by Xuan on 2017/9/23.
 */

public class SettingActivity extends AppCompatActivity {

    private RelativeLayout rlBg2;
    private RelativeLayout rlBg4;
    private RadioButton twoButton;
    private RadioButton threeButton;
    private RadioButton fourButton;
    private RadioGroup radioGroup;
    public static String FLAG = "6小时";
    private TextView textNotification;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        initToolBar();
        initUpdate();
        initRadio();
        initNotification();
        initNotificationSetting();
    }

    private void initNotification() {
        rlBg4 = (RelativeLayout) findViewById(R.id.rl_bg4);
        final SettingItemView notifiSwitch = (SettingItemView) findViewById(R.id.notifi_switch);
        //获取已有的开关状态，用作显示
        boolean open_notifi = SpUtils.getBoolean(this, ConstanValue.OPEN_NOTIFICATION, false);
        //是否选中，根据上一次存储的结果去做决定
        notifiSwitch.setCheck(open_notifi);
        notifiSwitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //如果之前是选中的，点击过后，变成未选中
                //如果之前的未选择的，点击过后，变成选中
                //获取之前的选择状态
                boolean isCheck = notifiSwitch.isCheck();
                //将原有状态取反，等同于上述两步操作
                notifiSwitch.setCheck(!isCheck);
                //将取反后的状态存储到相应的sp中
                SpUtils.putBoolean(getApplicationContext(), ConstanValue.OPEN_NOTIFICATION, !isCheck);
                if (!isCheck){
                    Intent serviceIntent = new Intent(getApplicationContext(), Notification.class);
                    startService(serviceIntent);
                }else {
                    Intent serviceIntent = new Intent(getApplicationContext(), Notification.class);
                    stopService(serviceIntent);
                }
                if (SpUtils.getBoolean(getApplicationContext(), ConstanValue.OPEN_NOTIFICATION, false)) {
                    rlBg4.setVisibility(View.VISIBLE);
                } else {
                    rlBg4.setVisibility(View.GONE);
                }
            }
        });
        if (SpUtils.getBoolean(this, ConstanValue.OPEN_NOTIFICATION, false)) {
            rlBg4.setVisibility(View.VISIBLE);
        } else {
            rlBg4.setVisibility(View.GONE);
        }
    }

    private void initNotificationSetting() {
        textNotification = (TextView) findViewById(R.id.text_notification);
        textNotification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), NotifiSettingActivity.class);
                startActivity(intent);
            }
        });
    }

    private void initToolBar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.activity_setting_toolbar);
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

    private void initRadio() {
        radioGroup = (RadioGroup) findViewById(R.id.radio_group);
        twoButton = (RadioButton) findViewById(R.id.two_button);
        threeButton = (RadioButton) findViewById(R.id.three_button);
        fourButton = (RadioButton) findViewById(R.id.four_button);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == twoButton.getId()) {
                    FLAG = twoButton.getText().toString();
                } else if (checkedId == threeButton.getId()) {
                    FLAG = threeButton.getText().toString();
                } else if (checkedId == fourButton.getId()) {
                    FLAG = fourButton.getText().toString();
                }
            }
        });
        switch (FLAG) {
            case "6小时":
                twoButton.setChecked(true);
                break;
            case "12小时":
                threeButton.setChecked(true);
                break;
            case "24小时":
                fourButton.setChecked(true);
                break;
            default:
        }
    }

    private void initUpdate() {
        rlBg2 = (RelativeLayout) findViewById(R.id.rl_bg2);
        final SettingItemView siv_update = (SettingItemView) findViewById(R.id.setting_update);
        //获取已有的开关状态，用作显示
        boolean open_update = SpUtils.getBoolean(this, ConstanValue.OPEN_UPDATE, false);
        //是否选中，根据上一次存储的结果去做决定
        siv_update.setCheck(open_update);
        siv_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //如果之前是选中的，点击过后，变成未选中
                //如果之前的未选择的，点击过后，变成选中
                //获取之前的选择状态
                boolean isCheck = siv_update.isCheck();
                //将原有状态取反，等同于上述两步操作
                siv_update.setCheck(!isCheck);
                //将取反后的状态存储到相应的sp中
                SpUtils.putBoolean(getApplicationContext(), ConstanValue.OPEN_UPDATE, !isCheck);
                if (SpUtils.getBoolean(getApplicationContext(), ConstanValue.OPEN_UPDATE, false)) {
                    rlBg2.setVisibility(View.VISIBLE);
                } else {
                    rlBg2.setVisibility(View.GONE);
                }
            }
        });
        if (SpUtils.getBoolean(this, ConstanValue.OPEN_UPDATE, false)) {
            rlBg2.setVisibility(View.VISIBLE);
        } else {
            rlBg2.setVisibility(View.GONE);
        }
    }
}
