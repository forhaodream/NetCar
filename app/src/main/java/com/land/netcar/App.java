package com.land.netcar;

import android.app.Application;

import com.lzy.okgo.OkGo;
import com.lzy.okgo.cache.CacheEntity;
import com.lzy.okgo.cache.CacheMode;
import com.lzy.okgo.cookie.store.PersistentCookieStore;

import cn.jpush.android.api.JPushInterface;

/**
 * Created by VULCAN on 2018/2/27.
 */

public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        JPushInterface.setDebugMode(true);
        JPushInterface.init(this);
        //okgo配置
        OkGo.init(this);
        OkGo.getInstance().setConnectTimeout(OkGo.DEFAULT_MILLISECONDS)
                .setCacheMode(CacheMode.NO_CACHE)
                .setCacheTime(CacheEntity.CACHE_NEVER_EXPIRE)
                .setRetryCount(3)
                .setCookieStore(new PersistentCookieStore())
                .setCertificates();

    }
}
