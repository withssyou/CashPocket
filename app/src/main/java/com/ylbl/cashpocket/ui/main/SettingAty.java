package com.ylbl.cashpocket.ui.main;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ylbl.cashpocket.R;
import com.ylbl.cashpocket.base.BaseToolBarAty;
import com.ylbl.cashpocket.utils.DataCleanManager;
import com.ylbl.cashpocket.utils.SpUtils;
import com.ylbl.cashpocket.utils.ToastUtils;
import com.ylbl.cashpocket.utils.VersionUtils;

import java.io.File;

import butterknife.BindView;
import butterknife.OnClick;

public class SettingAty extends BaseToolBarAty{
    @BindView(R.id.aty_setting_cache_tv)
    TextView cache;
    @BindView(R.id.aty_setting_version_tv)
    TextView version;
    @BindView(R.id.aty_setting_clear_ll)
    LinearLayout clear;
//    private PackageManager pm;
    @Override
    protected int getLayoutId() {
        return R.layout.aty_setting;
    }

    @Override
    protected String iniTitle() {
        return "系统设置";
    }

    @Override
    protected void initViewWithBack(boolean setBack) {
        super.initViewWithBack(true);
        //获得应用内部缓存(/data/data/com.example.androidclearcache/cache)
        try {
            String cacheSize = DataCleanManager.getTotalCacheSize(context);
            cache.setText(cacheSize);
        } catch (Exception e) {
            e.printStackTrace();
        }
        version.setText(VersionUtils.getLocalVersionName(context));
    }
    @OnClick({R.id.aty_setting_version_iv,R.id.aty_setting_exit_btn,R.id.aty_setting_cache})
    void onClick (View view){
        switch (view.getId()){
            case R.id.aty_setting_cache:
                clear.setVisibility(View.VISIBLE);
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try{
                            DataCleanManager.clearAllCache(context);
                        }catch (Exception e){

                        }
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                clear.setVisibility(View.GONE);
                                try {
                                    String cacheSize = DataCleanManager.getTotalCacheSize(context);
                                    cache.setText(cacheSize);
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        });
                    }
                }).start();
                break;
            case R.id.aty_setting_version_iv:

                break;
            case R.id.aty_setting_exit_btn:
                SpUtils.quitLogin(this);
                Intent intent = new Intent(context , LoginAty.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                break;
        }
    }
}
