package com.yunxiweather.android.gson;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Xuan on 2017/9/22.
 */

public class Now {
    @SerializedName("tmp")
    public String temperature;

    @SerializedName("cond")
    public More more;

    public class More {
        @SerializedName("txt")
        public String info;
    }
}
