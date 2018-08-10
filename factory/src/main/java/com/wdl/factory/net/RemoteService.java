package com.wdl.factory.net;

import com.wdl.factory.model.api.account.LoginModel;
import com.wdl.factory.model.api.account.RegisterModel;
import com.wdl.factory.model.api.account.RspModel;
import com.wdl.factory.model.api.pi.PiModel;
import com.wdl.factory.model.card.Notice;
import com.wdl.factory.model.card.Pi;
import com.wdl.factory.model.card.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

/**
 * 项目名：  MonitoringOfForest
 * 包名：    com.wdl.factory.net
 * 创建者：   wdl
 * 创建时间： 2018/8/3 18:40
 * 描述：    网络请求的所有接口、api
 */
@SuppressWarnings("unused")
public interface RemoteService {
    /**
     * 获取短信验证码
     *
     * @param model 用户名
     * @return RspModel<String>
     */
    @POST("sendSmsCode")
    Call<RspModel<String>> getSmsCode(@Body User model);

    /**
     * 用户注册
     *
     * @param model RegisterModel
     * @return RspModel<String>
     */
    @POST("userReg")
    Call<RspModel<String>> register(@Body RegisterModel model);

    /**
     * 用户登陆
     *
     * @param model LoginModel
     * @return RspModel<User>
     */
    @POST("user/select")
    Call<RspModel<User>> login(@Body LoginModel model);


    /**
     * 获取用户信息
     *
     * @param id uId
     * @return RspModel<User>
     */
    @GET("user/getUserInfo/{uId}")
    Call<RspModel<User>> getUserInfo(@Path("uId") int id);

    /*---------------------------------------------------------------------------------------------------------*/

    /**
     * 查询所有公告
     *
     * @return RspModel<List<Notice>>
     */
    @POST("notice/selectAll")
    Call<RspModel<List<Notice>>> notice();

    /*---------------------------------------------------------------------------------------------------------*/

    /**
     * 查看已绑定设备列表
     *
     * @param user User
     * @return RspModel<List<Pi>>
     */
    @POST("pi/select")
    Call<RspModel<List<Pi>>> selectAllPi(@Body User user);

    /**
     * 绑定设备
     *
     * @param model PiModel
     * @return RspModel<Pi>
     */
    @POST("pi/add")
    Call<RspModel<Pi>> addPi(@Body PiModel model);

    /**
     * 控制开关
     *
     * @param model PiModel
     * @return RspModel<String>
     */
    @POST("pi/changeSwitchState")
    Call<RspModel<String>> changedState(@Body PiModel model);


    /**
     * 更新设备信息,uId
     *
     * @param model PiModel
     * @return RspModel<Pi>
     */
    @POST("pi/update")
    Call<RspModel<Pi>> update(@Body PiModel model);


}