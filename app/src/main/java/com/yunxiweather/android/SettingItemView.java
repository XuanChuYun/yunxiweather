package com.yunxiweather.android;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * Created by Xuan on 2017/8/30.
 */

public class SettingItemView extends RelativeLayout {

    private CheckBox cb_box;
    private static final String tag = "SettingItemView";
    private static final String NAMESPACE = "http://schemas.android.com/apk/res/com.yunxiweather.android";
    private String mTitle;

    public SettingItemView(Context context) {
        this(context, null);
    }

    public SettingItemView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SettingItemView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        //xml转换成view，将设置界面的一个条目装换成view对象,直接添加到SettingItemView对应的view中
        View.inflate(context, R.layout.setting_item_view, this);
        //自定义组合控件中的标题描述
        TextView tv_title = (TextView) findViewById(R.id.tv_title);
        cb_box = (CheckBox) findViewById(R.id.cb_box);
        //获取自定义以及原生属性的操作
        initAttrs(attrs);
        //获取布局文件中定义的字符串，赋值给自定义组合控件的标题
        tv_title.setText(mTitle);
    }

    private void initAttrs(AttributeSet attrs) {
       /* Log.i(tag, "attrs.getAttributeCount() = ***************" + attrs.getAttributeCount());
        for (int i = 0; i < attrs.getAttributeCount(); i++) {
            Log.i(tag,"name = **********"+attrs.getAttributeName(i));
            Log.i(tag,"value = ***********"+attrs.getAttributeValue(i));
            Log.i(tag,"=============================================");
        }*/
        mTitle = attrs.getAttributeValue(NAMESPACE, "title");
        Log.i(tag, mTitle);
    }

    /**
     * 判断是否开启的方法
     *
     * @return true开启（CheckBox返回true）；false（CheckBox返回false）
     */
    public boolean isCheck() {
        return cb_box.isChecked();
    }

    public void setCheck(boolean isCheck) {
        cb_box.setChecked(isCheck);
    }
}
