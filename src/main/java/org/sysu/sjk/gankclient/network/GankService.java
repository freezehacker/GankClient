package org.sysu.sjk.gankclient.network;

import org.sysu.sjk.gankclient.Config;
import org.sysu.sjk.gankclient.bean.DailyGank;
import org.sysu.sjk.gankclient.bean.DateList;
import org.sysu.sjk.gankclient.bean.GankDetail;
import org.sysu.sjk.gankclient.bean.TypeGank;

import retrofit2.http.GET;
import retrofit2.http.Path;
import rx.Observable;

/**
 * Created by sjk on 2016/6/25.
 */
public interface GankService {

    /**
     * 获取有干货的那些日期
     * @return
     */
    @GET("day/history")
    Observable<DateList> getDatesWithGank();


    /**
     * http://gank.io/api/day/2015/08/06
     * 获取某一天的干货序列
     * 注意，发起请求之前，最好事先先请求一下getDatesWithGank来获取有干货的那些日期保存起来，因为不是每天都有干货
     * @param year
     * @param month
     * @param day
     * @return  某一天的干货序列
     */
    @GET("day/{year}/{month}/{day}")
    Observable<DailyGank> getDailyGank(@Path("year") String year,
                                       @Path("month") String month,
                                       @Path("day") String day);


    /**
     * http://gank.io/api/data/Android/10/1
     * 获取某个类型的干货
     * page_size参数在Config类中，暂时定义成每页15
     * @param type  所有可能的取值定义在Constant类中
     * @param page  某一页
     * @return  某个主题下的干货序列，比如Android
     */
    @GET("data/{type}/" + Config.PAGE_SIZE + "/{page}")
    Observable<TypeGank> getTypeGank(@Path("type") String type,
                                     @Path("page") int page);
}
