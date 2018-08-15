package com.wdl.factory;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.raizlabs.android.dbflow.config.FlowConfig;
import com.raizlabs.android.dbflow.config.FlowManager;
import com.wdl.common.app.Application;
import com.wdl.factory.data.data.feedback.FeedbackCenter;
import com.wdl.factory.data.data.feedback.FeedbackDispatch;
import com.wdl.factory.data.data.pi.PiCenter;
import com.wdl.factory.data.data.pi.PiDispatcher;
import com.wdl.factory.data.data.user.UserCenter;
import com.wdl.factory.model.card.Feedback;
import com.wdl.factory.persistence.Account;
import com.wdl.factory.utils.DBFlowExclusionStrategies;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import com.wdl.factory.data.DataSource;

/**
 * 项目名：  MonitoringOfForest
 * 包名：    com.wdl.factory
 * 创建者：   wdl
 * 创建时间： 2018/8/2 13:23
 * 描述：    工厂类
 */
@SuppressWarnings("unused")
public class Factory {
    //单例模式
    private static final Factory instance;
    private final Executor executor;
    private final Gson gson;

    static {
        instance = new Factory();
    }

    private Factory() {
        //初始化一个4个线程的线程池
        executor = Executors.newFixedThreadPool(4);
        gson = new GsonBuilder()
                .setDateFormat("yyyy-MM-dd HH:mm:ss")
                //设置过滤器,数据库级别的model不进行json转换
                .setExclusionStrategies(new DBFlowExclusionStrategies())
                .create();
    }

    /**
     * 异步上传
     *
     * @param runnable Runnable
     */
    public static void runOnAsy(Runnable runnable) {
        instance.executor.execute(runnable);
    }

    /**
     * 获取全局Application
     *
     * @return Application
     */
    public static Application application() {
        return Application.getInstance();
    }

    /**
     * 获取全局gson
     *
     * @return Gson
     */
    public static Gson getGson() {
        return instance.gson;
    }


    /**
     * 将返回的code进行解析
     *
     * @param str      String
     * @param callback FailedCallback
     */
    public static void decodeRspCode(String str, DataSource.FailedCallback callback) {
        switch (str) {
            case "电话号码已被注册，如忘记密码请找回!":
                decodeRspCoded(R.string.data_rsp_error_parameters_exist_account, callback);
                break;
            case "验证码错误!":
                decodeRspCoded(R.string.data_rsp_error_verification_code, callback);
                break;
            case "密码验证错误，请重新输入密码。":
                decodeRspCoded(R.string.data_rsp_account_invalid_password, callback);
                break;
            default:
                decodeRspCoded(R.string.data_rsp_error_unknown, callback);
                break;
        }
    }

    private static void decodeRspCoded(final int resId, DataSource.FailedCallback callback) {
        if (callback != null)
            callback.onNotAvailable(resId);
    }

    /**
     * Factory app初始化
     */
    public static void setUp() {
        //初始化数据库
        FlowManager.init(new FlowConfig
                .Builder(application())
                .openDatabasesOnInit(true)
                .build());
        //持久化数据的初始化
        Account.load(application());
    }

    /**
     * 处理推送来的消息
     *
     * @param message 消息
     */
    public static void dispatchMessage(String message) {
    }

    /**
     * 获取设备中心,规范
     *
     * @return 设备中心实现类
     */
    public static PiCenter getPiCenter() {
        return PiDispatcher.getPiCenter();
    }

    /**
     * 获取反馈中心,规范
     *
     * @return 实现类
     */
    public static FeedbackCenter getFeedbackCenter() {
        return FeedbackDispatch.getFeedbackCenter();
    }

    /**
     * 获取用户中心,规范
     *
     * @return 用户中心实现类
     */
    public static UserCenter getUserCenter() {
        return null;
    }
}


