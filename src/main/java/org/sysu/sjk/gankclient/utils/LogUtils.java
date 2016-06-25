package org.sysu.sjk.gankclient.utils;

import android.util.Log;

/**
 * Created by sjk on 2016/6/25.
 */
public class LogUtils {

    public static final String TAG = "JK";

    public static void log(String sentence) {
        if (null == sentence) {
            throw new IllegalArgumentException("打印的字符串为null");
        }
        //Log.d(TAG, sentence); // fu*k..我的Log系列坏了
        System.out.println(TAG + ": " + sentence);
    }
}
