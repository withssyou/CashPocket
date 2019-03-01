package com.ylbl.cashpocket.base;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.ylbl.cashpocket.ui.App;
import com.ylbl.cashpocket.utils.ToastUtils;

import java.util.List;

import butterknife.ButterKnife;

public abstract class BaseAty extends AppCompatActivity {
    protected Activity context;
    /**
     * set layout of this activity
     *
     * @return the id of layout
     */
    protected abstract int getLayoutId();

    /**
     * @return 返回此页面标题，如果返回null则使用应用名
     */
    protected String iniTitle() {
        return null;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            setContentView(getLayoutId());
            ButterKnife.bind(this);
            initBundle(savedInstanceState);
            context = this;
            initInject();
            setMonitor();
            initToolBar();
            fullScreen(this);
            initViewWithBack(false);
            initView();
        } catch (Exception e) {
           ToastUtils.toastShort(context, e.getMessage());
        }

    }

    protected  void setMonitor(){}

    protected void initView() {
    }

    protected void initBundle(Bundle savedInstanceState) {
    }

    protected void initInject() {
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
    }
    protected void initToolBar() {
    }
    /**
     * 初始化界面 设置返回
     */
    protected void initViewWithBack(boolean setBack) {

    }

    protected void fullScreen(Activity activity) {
    }
    /**
     * 判断某一个类是否存在任务栈里面
     * @return
     */
    public boolean isExsitMianActivity(Class<?> cls){
        Intent intent = new Intent(this, cls);
        ComponentName cmpName = intent.resolveActivity(getPackageManager());
        boolean flag = false;
        if (cmpName != null) { // 说明系统中存在这个activity
            ActivityManager am = (ActivityManager) getSystemService(ACTIVITY_SERVICE);
            List<ActivityManager.RunningTaskInfo> taskInfoList = am.getRunningTasks(10);
            for (ActivityManager.RunningTaskInfo taskInfo : taskInfoList) {
                if (taskInfo.baseActivity.equals(cmpName)) { // 说明它已经启动了
                    flag = true;
                    break;  //跳出循环，优化效率
                }
            }
        }
        return flag;
    }
}
