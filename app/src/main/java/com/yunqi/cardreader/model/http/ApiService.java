package com.yunqi.cardreader.model.http;
import com.yunqi.cardreader.model.bean.Room;
import com.yunqi.cardreader.model.bean.User;
import com.yunqi.cardreader.model.request.ChangePwdRequest;
import com.yunqi.cardreader.model.request.CheckOutRequest;
import com.yunqi.cardreader.model.request.ClientInfoAddRequest;
import com.yunqi.cardreader.model.response.BaseHttpRsp;
import com.yunqi.cardreader.model.response.CommonHttpRsp;


import java.util.List;

import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;
import rx.Observable;

/**
 * @author ghcui
 * @time 2017/1/11
 */
public interface ApiService {

    //测试环境
    String BASE_URL = "http://121.40.117.145:8080/index.php/api/";
    /**
     * 登录接口
     */
    @FormUrlEncoded
    @POST("app_user/login")
    Observable<CommonHttpRsp<User>> doLogin(@Field("account") String account, @Field("password") String password);
    /**
     * 提交用户信息
     *
     * @param request
     * @return
     */
    @POST("room_orders/addOrder")
    Observable<BaseHttpRsp> submitInfo(@Body ClientInfoAddRequest request);
    /**
     * 修改密码
     *
     * @param request
     * @return
     */
    @POST("action/add")
    Observable<BaseHttpRsp> changePwd(@Body ChangePwdRequest request);
    /**
     * 退房
     *
     * @param request
     * @return
     */
    @POST("action/add")
    Observable<BaseHttpRsp> checkOut(@Body CheckOutRequest request);

    /**
     * 房间列表查询
     */
    @GET("room/query")
    Observable<CommonHttpRsp<List<Room>>> getRoomList(@Query("uid") String uid,@Query("police_station_id") String police_station_id,@Query("page") int page, @Query("size") int size);

}
