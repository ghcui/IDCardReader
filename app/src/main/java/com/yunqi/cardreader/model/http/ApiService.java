package com.yunqi.cardreader.model.http;
import com.yunqi.cardreader.model.bean.User;
import com.yunqi.cardreader.model.request.ChangePwdRequest;
import com.yunqi.cardreader.model.request.ClientInfoAddRequest;
import com.yunqi.cardreader.model.response.BaseHttpRsp;
import com.yunqi.cardreader.model.response.CommonHttpRsp;


import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import rx.Observable;

/**
 * @author ghcui
 * @time 2017/1/11
 */
public interface ApiService {

    //测试环境
    String BASE_URL = "http://60.174.196.102:3080/api/";
    /**
     * 登录接口
     */
    @FormUrlEncoded
    @POST("users/login")
    Observable<CommonHttpRsp<User>> doLogin(@Field("account") String account, @Field("password") String password);
    /**
     * 提交用户信息
     *
     * @param request
     * @return
     */
    @POST("action/add")
    Observable<BaseHttpRsp> submitInfo(@Body ClientInfoAddRequest request);
    /**
     * 修改密码
     *
     * @param request
     * @return
     */
    @POST("action/add")
    Observable<BaseHttpRsp> changePwd(@Body ChangePwdRequest request);

}
