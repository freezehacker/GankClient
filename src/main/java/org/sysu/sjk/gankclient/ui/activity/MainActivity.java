package org.sysu.sjk.gankclient.ui.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.sysu.sjk.gankclient.R;
import org.sysu.sjk.gankclient.base.BaseActivity;
import org.sysu.sjk.gankclient.bean.DailyGank;
import org.sysu.sjk.gankclient.network.GankClient;
import org.sysu.sjk.gankclient.utils.LogUtils;

import java.util.Calendar;

import butterknife.BindView;
import butterknife.OnClick;
import rx.Scheduler;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * Created by sjk on 2016/6/25.
 */
public class MainActivity extends BaseActivity {

    @BindView(R.id.clickme)
    Button btnClickMe;

    @BindView(R.id.show)
    TextView tvShow;

    Subscription subscription;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    protected void onDestroy() {
        if (subscription != null) {
            subscription.unsubscribe();
        }
        super.onDestroy();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void bindViews() {

    }

    @OnClick(R.id.clickme)
    public void clickMe() {
        Calendar calendar = Calendar.getInstance();
        String year = String.valueOf(calendar.get(Calendar.YEAR));
        String month = String.valueOf(calendar.get(Calendar.MONTH) + 1);
        String day = String.valueOf(calendar.get(Calendar.DAY_OF_MONTH) - 1);

        subscription = GankClient.getInstance().getDailyGank(year, month, day)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<DailyGank>() {
                    @Override
                    public void call(DailyGank dailyGank) {
                        if (dailyGank.isError()) {
                            return;
                        }
                        LogUtils.log("成功");
                        if (dailyGank.getCategory() == null || dailyGank.getCategory().size() == 0) {
                            return;
                        }
                        StringBuilder sb = new StringBuilder();
                        for (String s: dailyGank.getCategory()) {
                            sb.append(s);
                            sb.append('\n');
                        }
                        Toast.makeText(MainActivity.this, dailyGank.getCategory().size() + "", Toast.LENGTH_SHORT).show();
                        tvShow.setText(sb.toString());
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        LogUtils.log(throwable.getMessage());
                        btnClickMe.setText("throwable");
                    }
                });
    }
}
