package com.yunxiweather.android.service;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.BitmapFactory;
import android.os.IBinder;
import android.preference.PreferenceManager;
import android.support.v4.app.TaskStackBuilder;
import android.support.v4.app.NotificationCompat;
import android.widget.Toast;

import com.yunxiweather.android.R;
import com.yunxiweather.android.WeatherActivity;
import com.yunxiweather.android.gson.Weather;
import com.yunxiweather.android.util.HttpUtil;
import com.yunxiweather.android.util.Utility;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class Notification extends Service {
    private String mWeatherId;

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        mWeatherId = intent.getStringExtra("mWeatherId");
        requestWeather(mWeatherId);
        return super.onStartCommand(intent, flags, startId);
    }

    private void requestWeather(String weatherId) {
        String weatherUrl = "http://guolin.tech/api/weather?cityid=" + weatherId + "&key=0ebd95b22d994fffab87610eb21121c8";
        HttpUtil.sendOkHttpRequest(weatherUrl, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Toast.makeText(getApplicationContext(), "获取天气信息失败", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String responseText = response.body().string();
                final Weather weather = Utility.handleWeatherResponse(responseText);
                if (weather != null && "ok".equals(weather.status)) {
                    SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(Notification.this).edit();
                    editor.putString("weather", responseText);
                    editor.apply();
                    showNotification(weather);
                } else {
                    Toast.makeText(getApplicationContext(), "获取天气信息失败", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void showNotification(Weather weather) {
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this)
                .setContentTitle(weather.basic.cityName + " " + weather.now.more.info + " " + weather.now.temperature + "℃")
                .setContentText("AQI：" + weather.aqi.city.aqi + "  &  " + "PM：" + weather.aqi.city.pm25);
        String icon = weather.now.more.info;
        switch (icon) {
            case "晴":
                mBuilder.setSmallIcon(R.drawable.weather_icon_1);
                mBuilder.setLargeIcon(BitmapFactory.decodeResource(getResources(), R.drawable.weather_icon_1));
                break;
            case "多云":
                mBuilder.setSmallIcon(R.drawable.weather_icon_2);
                mBuilder.setLargeIcon(BitmapFactory.decodeResource(getResources(), R.drawable.weather_icon_2));
                break;
            case "阴":
                mBuilder.setSmallIcon(R.drawable.weather_icon_3);
                mBuilder.setLargeIcon(BitmapFactory.decodeResource(getResources(), R.drawable.weather_icon_3));
                break;
            case "阵雨":
                mBuilder.setSmallIcon(R.drawable.weather_icon_4);
                mBuilder.setLargeIcon(BitmapFactory.decodeResource(getResources(), R.drawable.weather_icon_4));
                break;
            case "雷阵雨":
                mBuilder.setSmallIcon(R.drawable.weather_icon_5);
                mBuilder.setLargeIcon(BitmapFactory.decodeResource(getResources(), R.drawable.weather_icon_5));
                break;
            case "雷阵雨伴有冰雹":
                mBuilder.setSmallIcon(R.drawable.weather_icon_6);
                mBuilder.setLargeIcon(BitmapFactory.decodeResource(getResources(), R.drawable.weather_icon_6));
                break;
            case "雨加雪":
                mBuilder.setSmallIcon(R.drawable.weather_icon_7);
                mBuilder.setLargeIcon(BitmapFactory.decodeResource(getResources(), R.drawable.weather_icon_7));
                break;
            case "沙尘暴":
                mBuilder.setSmallIcon(R.drawable.weather_icon_8);
                mBuilder.setLargeIcon(BitmapFactory.decodeResource(getResources(), R.drawable.weather_icon_8));
                break;
            case "小雨":
                mBuilder.setSmallIcon(R.drawable.weather_icon_9);
                mBuilder.setLargeIcon(BitmapFactory.decodeResource(getResources(), R.drawable.weather_icon_9));
                break;
            case "中雨":
                mBuilder.setSmallIcon(R.drawable.weather_icon_10);
                mBuilder.setLargeIcon(BitmapFactory.decodeResource(getResources(), R.drawable.weather_icon_10));
                break;
            case "大雨":
                mBuilder.setSmallIcon(R.drawable.weather_icon_11);
                mBuilder.setLargeIcon(BitmapFactory.decodeResource(getResources(), R.drawable.weather_icon_11));
                break;
            case "雾":
                mBuilder.setSmallIcon(R.drawable.weather_icon_12);
                mBuilder.setLargeIcon(BitmapFactory.decodeResource(getResources(), R.drawable.weather_icon_12));
                break;
            case "阵雪":
                mBuilder.setSmallIcon(R.drawable.weather_icon_13);
                mBuilder.setLargeIcon(BitmapFactory.decodeResource(getResources(), R.drawable.weather_icon_13));
                break;
            case "小雪":
                mBuilder.setSmallIcon(R.drawable.weather_icon_14);
                mBuilder.setLargeIcon(BitmapFactory.decodeResource(getResources(), R.drawable.weather_icon_14));
                break;
            case "中雪":
                mBuilder.setSmallIcon(R.drawable.weather_icon_15);
                mBuilder.setLargeIcon(BitmapFactory.decodeResource(getResources(), R.drawable.weather_icon_15));
                break;
            case "大雪":
                mBuilder.setSmallIcon(R.drawable.weather_icon_16);
                mBuilder.setLargeIcon(BitmapFactory.decodeResource(getResources(), R.drawable.weather_icon_16));
                break;
            default:
                mBuilder.setSmallIcon(R.drawable.ic_launcher);
                mBuilder.setLargeIcon(BitmapFactory.decodeResource(getResources(), R.drawable.weather_icon_17));
                break;
        }
        Intent resultIntent = new Intent(this, WeatherActivity.class);
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
        stackBuilder.addParentStack(WeatherActivity.class);
        stackBuilder.addNextIntent(resultIntent);
        PendingIntent pi = stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);
        mBuilder.setContentIntent(pi);
        NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        android.app.Notification notification = mBuilder.build();
        manager.notify(1, notification);
        startForeground(1, notification);
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

}
