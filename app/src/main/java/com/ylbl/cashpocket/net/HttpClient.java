package com.ylbl.cashpocket.net;

import android.content.Context;
import android.text.TextUtils;

import com.ylbl.cashpocket.ui.App;
import com.ylbl.cashpocket.utils.SpUtils;
import com.ylbl.cashpocket.utils.ToastUtils;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.Callback;

import java.util.Map;

import static com.ylbl.cashpocket.net.Constants.BASE_URL;

public class HttpClient {
    /**
     * 登录服务
     *
     * @param asyncid
     * @param params
     * @param callback
     */
    public static void login(int asyncid, Map params, Callback callback) {
        OkHttpUtils
                .post()
                .url(BASE_URL +params.get("url"))
                .id(asyncid)
                .params(params)
                .build()
                .execute(callback);
    }
    /**
     * 请求服务
     *
     * @param asyncid
     * @param params
     * @param callback
     */
    public static void doPost(int asyncid, Map params, Callback callback , Context context) {
        String token = SpUtils.getToken(context);
        if (TextUtils.isEmpty(token)){
            ToastUtils.toastShort(context , "登录过期，请重新登录");
            return;
        }
        OkHttpUtils
                .post()
                .url(BASE_URL + params.get("url"))
                .id(asyncid)
                .params(params)
                .addHeader("Authorization" ,token )
                .build()
                .execute(callback);

    }

    /**
     * get请求
     * @param asyncid
     * @param params
     * @param callback
     */
    public static void doGet(int asyncid, Map<String ,String> params, Callback callback , Context context) {
        String token = SpUtils.getToken(context);
        if (TextUtils.isEmpty(token)){
            ToastUtils.toastShort(context , "登录过期，请重新登录");
            return;
        }
        OkHttpUtils
                .get()
                .url(BASE_URL + params.get("url"))
                .id(asyncid)
                .addHeader("Authorization" , token)
                .params(params)
                .build()
                .execute(callback);
    }
//    /**
//     * get请求
//     * @param asyncid
//     * @param params
//     * @param callback
//     */
//    public static void doPageGet(int asyncid, Map<String ,String> params, Callback callback , Context context) {
//        String token = SpUtils.getToken(context);
//        if (TextUtils.isEmpty(token)){
//            ToastUtils.toastShort(context , "登录过期，请重新登录");
//            return;
//        }
//        OkHttpUtils
//                .get()
//                .url(BASE_URL + Constants.CASH_LOG)
//                .id(asyncid)
////                .addHeader("Authorization" , token)
//                .addParams("pageNum" , "1")
//                .addParams("pageSize" , "10")
//                .build()
//                .execute(callback);
//    }
    /**
     *  检查版本更新
     * @param asyncid
     * @param callback
     */
    public static void upDate(int asyncid ,Map object , Callback callback){
        OkHttpUtils.get()
                .url("")
                .build()
                .execute(callback);
    }
}
