package com.yunxiweather.android;

import android.app.NotificationManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v4.app.NotificationCompat;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.yunxiweather.android.service.Notification;

/**
 * Created by Xuan on 2017/9/25.
 */

public class AboutActivity extends AppCompatActivity implements View.OnClickListener{

    private RelativeLayout rlAb1;
    private RelativeLayout rlAb2;
    private RelativeLayout rlAb3;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        initToolBar();
        initData();
    }

    private void initToolBar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.activity_about_toolbar);
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

    private void initData() {
        rlAb1 = (RelativeLayout) findViewById(R.id.rl_ab1);
        rlAb2 = (RelativeLayout) findViewById(R.id.rl_ab2);
        rlAb3 = (RelativeLayout) findViewById(R.id.rl_ab3);
        rlAb1.setOnClickListener(this);
        rlAb2.setOnClickListener(this);
        rlAb3.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.rl_ab1:
                NotificationManager manager1 = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
                android.app.Notification notification1 = new NotificationCompat.Builder(this)
                        .setContentTitle("欢迎关注新浪微博：")
                        .setContentText("1518849189@qq.com")
                        .setDefaults(android.support.v7.app.NotificationCompat.DEFAULT_SOUND)
                        .setSmallIcon(R.drawable.ic_launcher1)
                        .build();
                manager1.notify(2,notification1);
                break;
            case R.id.rl_ab2:
                Toast.makeText(this, "特别感谢郭林老师、玉麒麟老师、大少、罗黾", Toast.LENGTH_LONG).show();
                break;
            case R.id.rl_ab3:
                NotificationManager manager2 = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
                android.app.Notification notification2 = new NotificationCompat.Builder(this)
                        .setContentTitle("反馈QQ邮箱：")
                        .setContentText("1518849189@qqq.com")
                        .setDefaults(android.support.v7.app.NotificationCompat.DEFAULT_SOUND)
                        .setSmallIcon(R.drawable.ic_launcher1)
                        .build();
                manager2.notify(3,notification2);
                break;
        }
    }
}
