package com.ylbl.cashpocket.utils;

import android.text.TextUtils;

import java.util.HashMap;
import java.util.Map;

public class MapUtil {

    /**
     * 返回格式 {
     * username :"123",
     * password :"123",
     * }
     *
     * @param paramsKey
     * @param params
     * @return
     */
    public static Object toMap(Object[] paramsKey, Object[] params) {
        Map<Object, Object> options = new HashMap<>();
        if (paramsKey != null && paramsKey.length > 0) {
            for (int i = 0; i < paramsKey.length; i++) {
                options.put(paramsKey[i], params[i]);
            }
        }
        return options;
    }

    /**
     * 转json
     * @param paramsKey
     * @param params
     * @return
     */
    public static Object toJson(Object[] paramsKey, Object[] params) {
        Map<Object, Object> options = new HashMap<>();
        if (paramsKey != null && paramsKey.length > 0) {
            for (int i = 0; i < paramsKey.length; i++) {
                options.put(paramsKey[i], params[i]);
            }
        }
        Object o = toQuot(FastJsonUtils.map2Json(options));
        return o;
    }

    /**
     * 转json params为空，不添加
     * @param paramsKey
     * @param params
     * @return
     */
    public static Object toJsonMap(Object[] paramsKey, Object[] params) {
        Map<Object, Object> options = new HashMap<>();
        if (paramsKey != null && paramsKey.length > 0) {
            for (int i = 0; i < paramsKey.length; i++) {
                if(!TextUtils.isEmpty((String)params[i])){
                    options.put(paramsKey[i], params[i]);
                }

            }
        }

        return toQuot(FastJsonUtils.map2Json(options));
    }


    /**
     * \“ 转 &quot;
     * @param josn
     * @return
     */
    public static Object toQuot(String josn) {
        return josn.replace("\"","&quot;");
    }




}
