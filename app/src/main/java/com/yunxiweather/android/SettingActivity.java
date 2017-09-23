package com.yunxiweather.android;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.WindowManager;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yunxiweather.android.util.ConstanValue;
import com.yunxiweather.android.util.SpUtils;

/**
 * Created by Xuan on 2017/9/23.
 */

public class SettingActivity extends AppCompatActivity {

    private TextView updateTimeText;
    private RelativeLayout rlBg2;
    private RadioButton oneButton;
    private RadioButton twoButton;
    private RadioButton threeButton;
    private RadioButton fourButton;
    private RadioGroup radioGroup;
    public static String FLAG = "6小时";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_setting);
        initToolBar();
        initUpdate();
        initRadio();
    }

    private void initToolBar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.setup1_toolbar);
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
        updateTimeText = (TextView) findViewById(R.id.update_time_text);
        radioGroup = (RadioGroup) findViewById(R.id.radio_group);
        oneButton = (RadioButton) findViewById(R.id.one_button);
        twoButton = (RadioButton) findViewById(R.id.two_button);
        threeButton = (RadioButton) findViewById(R.id.three_button);
        fourButton = (RadioButton) findViewById(R.id.four_button);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == oneButton.getId()) {
                    updateTimeText.setText(oneButton.getText().toString());
                    FLAG = oneButton.getText().toString();
                } else if (checkedId == twoButton.getId()) {
                    updateTimeText.setText(twoButton.getText().toString());
                    FLAG = twoButton.getText().toString();
                    SpUtils.putString(getApplicationContext(), ConstanValue.FLAG, FLAG);
                } else if (checkedId == threeButton.getId()) {
                    updateTimeText.setText(threeButton.getText().toString());
                    FLAG = threeButton.getText().toString();
//                    SpUtils.putString(getApplicationContext(), ConstanValue.FLAG, threeButton.getText().toString());
                } else if (checkedId == fourButton.getId()) {
                    updateTimeText.setText(fourButton.getText().toString());
                    FLAG = fourButton.getText().toString();
//                    SpUtils.putString(getApplicationContext(), ConstanValue.FLAG, fourButton.getText().toString());
                }
            }
        });
        switch (FLAG) {
            case "45分钟":
                oneButton.setChecked(true);
                break;
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
