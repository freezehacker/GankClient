package org.sysu.sjk.gankclient.utils;

import android.graphics.Color;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;

import org.sysu.sjk.gankclient.bean.Gank;

/**
 * Created by sjk on 2016/6/26.
 */
public class SpannableUtils {

    /**
     * RecyclerView中item（显示一条gank的标题和作者）的文字样式
     * @param gank
     * @return
     */
    public static SpannableString getGankStr(Gank gank) {
        String str = new String(gank.getDesc() + " @" + gank.getWho());
        SpannableString ret = new SpannableString(str);
        // 改变后半部分的大小：0.8倍
        ret.setSpan(new RelativeSizeSpan(0.8f), gank.getDesc().length(), str.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        // 改变后半部分的颜色：灰色
        ret.setSpan(new ForegroundColorSpan(Color.GRAY), gank.getDesc().length(), str.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        return ret;
    }
}
