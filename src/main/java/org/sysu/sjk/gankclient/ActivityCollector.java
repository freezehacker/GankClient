package org.sysu.sjk.gankclient;

import org.sysu.sjk.gankclient.base.BaseActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sjk on 2016/6/24.
 * 虽然可以管理Activity，但要考虑内存泄漏
 */
public class ActivityCollector {

    private static List<BaseActivity> activities = new ArrayList<>();

    public static void addActivity(BaseActivity activity) {
        activities.add(activity);
    }

    public static void removeActivity(BaseActivity activity) {
        activities.remove(activity);
    }

    public static void removeAll() {
        for (BaseActivity activity : activities) {
            if (!activity.isFinishing()) {
                activity.finish();
            }
        }
    }
}
