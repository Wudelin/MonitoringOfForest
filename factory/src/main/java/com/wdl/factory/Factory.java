package com.wdl.factory;

import com.wdl.common.common.app.Application;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

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

    static {
        instance = new Factory();
    }

    private Factory() {
        //初始化一个4个线程的线程池
        executor = Executors.newFixedThreadPool(4);
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
}