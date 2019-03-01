package com.ylbl.cashpocket.utils;

import android.graphics.Bitmap;
import android.util.LruCache;

import java.util.Map;

/**
 * 图片缓存的工具类
 */
public class ImageCacheUtils {
    private static ImageCacheUtils instance;
    private LruCache<String, Bitmap> mLruCache;
    //获取手机最大内存,单位kb
    int maxMemory = (int) (Runtime.getRuntime().maxMemory() / 1024);
    //一般都将1/8设为LruCache的最大缓存
    int cacheSize = maxMemory / 8;

    private ImageCacheUtils() {
        mLruCache = new LruCache<String,Bitmap>(cacheSize){
            @Override
            protected int sizeOf (String key, Bitmap value){
                return value.getByteCount() / 1024;
            }

        };
    }
    public static ImageCacheUtils getInstance(){
        if (instance == null){
            synchronized (ImageCacheUtils.class){
                if (instance == null){
                    instance = new ImageCacheUtils();
                }
            }
        }
        return instance;
    }

    //加入缓存
    public  void putBitmap (Map<String , Bitmap> map){
        for (Map.Entry<String, Bitmap> entry : map.entrySet()) {
            mLruCache.put(entry.getKey() ,entry.getValue());
        }
    }

    //从缓存中读取
    public  Bitmap getBitmap (String key){
        return mLruCache.get(key);
    }
}
