package com.ylbl.cashpocket.utils;

import android.app.Activity;

import com.ylbl.cashpocket.view.CommonLoadingDialog;


/**
 * 进度条封装，切记在应用退出时需调用releaseSrc()进行资源释放
 *
 * @author BillowLau
 */
public class CommonProgressUtil {
    /**
     * 后台加载提示内容
     */
    private static String defalutProgMsg;
    /**
     * 缓存上一个Activity
     */
    private static CommonLoadingDialog loadingDialog;
    /**
     * 缓存上一个Activity
     */
    private static Activity tmpActivity;

    /**
     * 显示进度条
     *
     */
    public static void showProgcessBar(Activity activity) {
        if (tmpActivity == null || !tmpActivity.equals(activity)) {
            loadingDialog = new CommonLoadingDialog(activity);
        }
        tmpActivity = activity;
        if (!loadingDialog.isShowing())
            loadingDialog.show();
    }
    /**
     * 显示进度条
     *
     * @param isOperable 在显示进度条时UI是否可操作
     */
    public static void showProgcessBar(Activity activity, boolean isOperable,
                                       String progMsg) {
        if (tmpActivity == null || !tmpActivity.equals(activity)) {
            loadingDialog = new CommonLoadingDialog(activity);
        }
        tmpActivity = activity;
        loadingDialog.setTitle(progMsg);

        if (!loadingDialog.isShowing())
            loadingDialog.show();
    }

    /**
     * 显示进度条
     * isOperable 在显示进度条时UI是否可操作
     */
    public static void closeProgcessBar() {
        if (null != loadingDialog)
            loadingDialog.dismiss();
    }

}
