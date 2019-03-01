package com.ylbl.cashpocket.ui;

import android.app.Application;
import android.content.Context;
import com.ylbl.cashpocket.bean.User;
import com.zhy.http.okhttp.OkHttpUtils;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;

public class App extends Application {
//   public static MemberInfo memberInfo;
//   public static ConfigInfo configInfo;
    protected static User user;
    public Context context;
    @Override
    public void onCreate() {
        super.onCreate();
        initOkHttpClient();
    }

    private void initOkHttpClient() {
      OkHttpClient okHttpClient = new OkHttpClient.Builder()
              .connectTimeout(10000L, TimeUnit.MILLISECONDS) //链接超时
              .readTimeout(10000L,TimeUnit.MILLISECONDS) //读取超时
              .build(); //其他配置

     OkHttpUtils.initClient(okHttpClient);
    }

    public static User getUser() {
        return user;
    }

    public static void setUser(User user) {
        App.user = user;
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }
}
