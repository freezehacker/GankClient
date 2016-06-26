package org.sysu.sjk.gankclient;

/**
 * Created by sjk on 2016/6/25.
 */
public class Constant {

    public static final String TYPE = "gank_type";

    // 数据类型： 福利 | Android | iOS | 休息视频 | 拓展资源 | 前端 | all
    public static final String TYPE_ANDROID = "Android";            // 0
    public static final String TYPE_IOS = "iOS";                    // 1
    public static final String TYPE_FRONT_END = "前端";             // 2
    public static final String TYPE_RECOMMEND = "瞎推荐";           // 3
    public static final String TYPE_EXTEND = "拓展资源";            // 4
    public static final String TYPE_PICTURE = "福利";
    public static final String TYPE_VIDEO = "休息视频";
    public static final String[] TYPES = new String[]{"Android", "iOS", "前端", "瞎推荐", "拓展资源"};

    public static final String URL = "gank_url";
    public static final String TITLE = "gank_title";
}
