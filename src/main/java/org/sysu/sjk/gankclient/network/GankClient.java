package org.sysu.sjk.gankclient.network;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.sysu.sjk.gankclient.bean.DailyGank;
import org.sysu.sjk.gankclient.bean.TypeGank;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;

/**
 * 单例模式
 * Created by sjk on 2016/6/25.
 */
public class GankClient {

    public static final String BASE_URL = "http://gank.io/api/";
    public static final int CONNECT_TIMEOUT = 10;

    private Retrofit mRetrofit;
    private GankService mGankService;

    /**
     * 构造器私有
     * 只调用一次，进行Retrofit的配置工作
     */
    private GankClient() {
        OkHttpClient.Builder okHttpClientBuilder = new OkHttpClient.Builder();
        okHttpClientBuilder.connectTimeout(CONNECT_TIMEOUT, TimeUnit.SECONDS);  // 设置5秒的连接时长

        Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();

        mRetrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(okHttpClientBuilder.build())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
        mGankService = mRetrofit.create(GankService.class);
    }

    /**
     * 据说是最好的单例写法哦~
     */
    private static class SingletonHolder {
        private static final GankClient INSTANCE = new GankClient();
    }

    /**
     * 给业务程序员的接口，来调用Retrofit的服务
     * @return
     */
    public static GankClient getInstance() {
        return SingletonHolder.INSTANCE;
    }

    /**
     * 把Service那里的接口转移到Retrofit Client这里来调用
     * @param year
     * @param month
     * @param day
     * @return  DailyGank(每日干货)的Observable序列
     */
    public Observable<DailyGank> getDailyGank(String year, String month, String day) {
        return mGankService.getDailyGank(year, month, day);
    }

    /**
     * more API Service below
     */
    public Observable<TypeGank> getTypeGank(String type, int page) {
        return mGankService.getTypeGank(type, page);
    }
}
