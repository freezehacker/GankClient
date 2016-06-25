package org.sysu.sjk.gankclient;

import android.app.Application;
import android.content.Context;

/**
 * Created by sjk on 2016/6/24.
 */
public class GankApplication extends Application {

    private static Context mContext;

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = this;
        //...
    }

    public static Context getGlobalContext() {
        return mContext;
    }
}
