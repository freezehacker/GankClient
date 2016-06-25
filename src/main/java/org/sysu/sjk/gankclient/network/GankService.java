package org.sysu.sjk.gankclient.network;

import org.sysu.sjk.gankclient.bean.DailyGank;
import org.sysu.sjk.gankclient.bean.GankDetail;

import retrofit2.http.GET;
import retrofit2.http.Path;
import rx.Observable;

/**
 * Created by sjk on 2016/6/25.
 */
public interface GankService {

    //http://gank.io/api/day/2015/08/06
    @GET("day/{year}/{month}/{day}")
    Observable<DailyGank> getDailyGank(@Path("year") String year, @Path("month") String month, @Path("day") String day);
}
