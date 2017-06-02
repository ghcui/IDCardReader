package com.yunqi.cardreader.model.http;
import com.yunqi.cardreader.model.bean.ClientInfo;
import com.yunqi.cardreader.model.bean.Notice;
import com.yunqi.cardreader.model.bean.Room;
import com.yunqi.cardreader.model.bean.User;
import com.yunqi.cardreader.model.request.ChangePwdRequest;
import com.yunqi.cardreader.model.request.ChangeRoomRequest;
import com.yunqi.cardreader.model.request.CheckOutRequest;
import com.yunqi.cardreader.model.response.BaseHttpRsp;
import com.yunqi.cardreader.model.response.CommonHttpRsp;


import java.util.List;
import java.util.Map;

import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PartMap;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;
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
    Observable<BaseHttpRsp> submitInfo(@Body ClientInfo request);
    /**
     * 修改密码
     *
     * @param request
     * @return
     */
    @POST("app_user/resetPassword")
    Observable<BaseHttpRsp> changePwd(@Body ChangePwdRequest request);
    /**
     * 退房
     *
     * @param request
     * @return
     */
    @POST("room_orders/updateOrder")
    Observable<BaseHttpRsp> checkOut(@Body CheckOutRequest request);
    /**
     * 换房
     *
     * @param request
     * @return
     */
    @POST("room_orders/changeRoom")
    Observable<BaseHttpRsp> changeRoom(@Body ChangeRoomRequest request);

    /**
     * 房间列表查询
     */
    @GET("room/query")
    Observable<CommonHttpRsp<List<Room>>> getRoomList(@Query("uid") String uid, @Query("police_station_id") String police_station_id, @Query("keyword") String keyword, @Query("type") int type, @Query("page") int page, @Query("size") int size);

    /**
     * 查询在住客户
     */
    @GET("room/get_custom")
    Observable<CommonHttpRsp<List<ClientInfo>>> getCustomByRoomCode(@Query("room_code") String room_code);
    /**
     * 查询已发数据的数目
     */
    @GET("room_orders/getOrderCount")
    Observable<CommonHttpRsp<String>> getSendedCount(@Query("uid") String user_id);

    /**
     * 最新通知列表查询
     */
    @GET("message/query")
    Observable<CommonHttpRsp<List<Notice>>> getNotices(@Query("page") int page, @Query("size") int size);
    /**
     * 上传文件
     *
     * @return
     */
    @Multipart
    @POST("upload")
    Observable<CommonHttpRsp<BaseHttpRsp>> uploader(@PartMap Map<String, RequestBody> file);
}
