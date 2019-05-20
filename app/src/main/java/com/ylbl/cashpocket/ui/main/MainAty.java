package com.ylbl.cashpocket.ui.main;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.PowerManager;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.ylbl.cashpocket.R;
import com.ylbl.cashpocket.base.BaseToolBarAty;
import com.ylbl.cashpocket.bean.ConfigInfo;
import com.ylbl.cashpocket.bean.MemberConfigInfo;
import com.ylbl.cashpocket.bean.MemberInfo;
import com.ylbl.cashpocket.bean.VersionInfo;
import com.ylbl.cashpocket.bean.ResultInfo;
import com.ylbl.cashpocket.fmg.DianFmg;
import com.ylbl.cashpocket.fmg.FoundFmg;
import com.ylbl.cashpocket.fmg.MyFmg;
import com.ylbl.cashpocket.fmg.NewsFmg;
import com.ylbl.cashpocket.net.AppDbCtrl;
import com.ylbl.cashpocket.net.Constants;
import com.ylbl.cashpocket.ui.redenvelops.PublishAty;
import com.ylbl.cashpocket.utils.FastJsonUtils;
import com.ylbl.cashpocket.utils.ImageCacheUtils;
import com.ylbl.cashpocket.utils.SpUtils;
import com.ylbl.cashpocket.utils.StatusBarUtil;
import com.ylbl.cashpocket.utils.StringUtils;
import com.ylbl.cashpocket.utils.ToastUtils;
import com.ylbl.cashpocket.utils.VersionUtils;
import com.zhy.base.fileprovider.FileProvider7;
import com.zhy.http.okhttp.callback.Callback;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

/**
 *  主页面
 *
 */
public class MainAty extends BaseToolBarAty implements View.OnClickListener{
    @BindView(R.id.main_dian)
    RelativeLayout dian;
    @BindView(R.id.main_dian_icon)
    ImageView dianIcon;
    @BindView(R.id.main_dian_txt)
    TextView dianTxt;
    @BindView(R.id.main_found_icon)
    ImageView foundIcon;
    @BindView(R.id.main_found_txt)
    TextView foundTxt;
    @BindView(R.id.main_news_icon)
    ImageView newsIcon;
    @BindView(R.id.main_news_txt)
    TextView newsTxt;
    @BindView(R.id.main_my_icon)
    ImageView myIcon;
    @BindView(R.id.main_my_txt)
    TextView myTxt;

    private Fragment dianFmg, foundFmg, myFmg, newsFmg;
    private FragmentManager supportFragmentManager;
    private FragmentTransaction fragmentTransaction;
    private AlertDialog dialog;
    private AlertDialog upDateDialog;
    private AlertDialog upDating;
    private ConfigInfo configInfo;

    private ProgressBar pBar;
    private TextView pText;
    private static final String DOWNLOAD_NAME = "diandianhui.apk";

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main_aty;
    }

    @Override
    protected void initViewWithBack(boolean setBack) {
        super.initViewWithBack(setBack);
        isShowLoad = true;
            //判断是否登录
        if (SpUtils.checkLogin(this) ) {
            //弹出对话框
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            View view = LayoutInflater.from(this).inflate(R.layout.dialog_islogin, null);
            builder.setView(view);
            builder.setCancelable(true);
            TextView login= view.findViewById(R.id.dialog_login_tv);
            TextView register= view.findViewById(R.id.dialog_register_tv);
            login.setOnClickListener(this);
            register.setOnClickListener(this);
            //取消或确定按钮监听事件处理
            dialog = builder.create();
            dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
            dialog.setCanceledOnTouchOutside(false);
            dialog.setCancelable(false);
            dialog.show();
        }else {
            //获取个人信息
            Map<String ,String > params = new HashMap<>();
            params.put("url" , Constants.MEMBER_INFO);
            newAsyncTaskExecute(Constants.HTTP_ACTION_2 , params);

            supportFragmentManager = getSupportFragmentManager();
            fragmentTransaction = supportFragmentManager.beginTransaction();
            dianFmg = new DianFmg();
            fragmentTransaction.add(R.id.main_fl, dianFmg).commit();
        }
    }

//    /**
//     * 页面初始化
//     */
//    @Override
//    protected void initView() {
////        supportFragmentManager = getSupportFragmentManager();
////        fragmentTransaction = supportFragmentManager.beginTransaction();
////        dianFmg = new DianFmg();
////        fragmentTransaction.add(R.id.main_fl, dianFmg).commit();
//
//    }

    /**
     * fragment切换时隐藏
     */
    private void hideFrag() {
        FragmentTransaction fragmentTransaction = supportFragmentManager.beginTransaction();
        if (dianFmg != null && dianFmg.isAdded()) {
            fragmentTransaction.hide(dianFmg);
        }
        if (foundFmg != null && foundFmg.isAdded()) {
            fragmentTransaction.hide(foundFmg);
        }
        if (newsFmg != null && newsFmg.isAdded()) {
            fragmentTransaction.hide(newsFmg);
        }
        if (myFmg != null && myFmg.isAdded()) {
            fragmentTransaction.hide(myFmg);
        }
        fragmentTransaction.commit();
    }

    @OnClick({R.id.main_dian, R.id.main_found, R.id.main_publish, R.id.main_news, R.id.main_my})
    public void onTab(View v) {
        hideFrag();
        FragmentTransaction fragmentTransaction = supportFragmentManager.beginTransaction();
        switch (v.getId()) {
            case R.id.main_dian://点点汇
                fragmentTransaction.show(dianFmg).commit();
                setCheck(dianIcon, dianTxt, R.mipmap.dian);
                setNormal(new ImageView[]{foundIcon, newsIcon, myIcon}, new TextView[]{foundTxt, newsTxt, myTxt},
                        new int[]{R.mipmap.found_nomal, R.mipmap.news_nomal, R.mipmap.my_normal});
                StatusBarUtil.setWindowStatusBarColor(this, R.color.colorBlack);
                break;
            case R.id.main_found: //发现
                if (foundFmg == null) {
                    foundFmg = new FoundFmg();
                    fragmentTransaction.add(R.id.main_fl, foundFmg).commit();
                } else {
                    fragmentTransaction.show(foundFmg).commit();
                }

                setCheck(foundIcon, foundTxt, R.mipmap.found);
                setNormal(new ImageView[]{dianIcon, newsIcon, myIcon}, new TextView[]{dianTxt, newsTxt, myTxt},
                        new int[]{R.mipmap.dian_nomal, R.mipmap.news_nomal, R.mipmap.my_normal});
                StatusBarUtil.setWindowStatusBarColor(this, R.color.colorBlue);
                break;
            case R.id.main_publish: //红包发布页面
                startActivityForResult(new Intent(this, PublishAty.class), 0x10);
                break;
            case R.id.main_news: //新闻
                if (newsFmg == null) {
                    newsFmg = new NewsFmg();
                    fragmentTransaction.add(R.id.main_fl, newsFmg).commit();
                } else {
                    fragmentTransaction.show(newsFmg).commit();
                }
                setCheck(newsIcon, newsTxt, R.mipmap.news);
                setNormal(new ImageView[]{dianIcon, foundIcon, myIcon}, new TextView[]{dianTxt, foundTxt, myTxt},
                        new int[]{R.mipmap.dian_nomal, R.mipmap.found_nomal, R.mipmap.my_normal});
                StatusBarUtil.setWindowStatusBarColor(this, R.color.colorBlack);
                break;
            case R.id.main_my:
                if (myFmg == null) {
                    myFmg = new MyFmg();
                    fragmentTransaction.add(R.id.main_fl, myFmg).commit();
                } else {
                    fragmentTransaction.show(myFmg).commit();
                }
                setCheck(myIcon, myTxt, R.mipmap.my);
                setNormal(new ImageView[]{dianIcon, foundIcon, newsIcon}, new TextView[]{dianTxt, foundTxt, newsTxt},
                        new int[]{R.mipmap.dian_nomal, R.mipmap.found_nomal, R.mipmap.news_nomal});
                StatusBarUtil.setWindowStatusBarColor(this, R.color.colorRedTitle);
                break;
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    /**
     * 设置正常
     *
     * @param iv
     * @param tv
     * @param res
     */
    private void setNormal(ImageView[] iv, TextView[] tv, int[] res) {
        tv[0].setTextColor(getResources().getColor(R.color.colorFontNormal));
        iv[0].setImageResource(res[0]);
        tv[1].setTextColor(getResources().getColor(R.color.colorFontNormal));
        iv[1].setImageResource(res[1]);
        tv[2].setTextColor(getResources().getColor(R.color.colorFontNormal));
        iv[2].setImageResource(res[2]);
    }

    /**
     * 设置选中
     *
     * @param iv
     * @param tv
     * @param res
     */
    private void setCheck(ImageView iv, TextView tv, int res) {
        tv.setTextColor(getResources().getColor(R.color.colorFont));
        iv.setImageResource(res);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 124) {
            update();//再次执行安装流程，包含权限判等
            return;
        }
        switch (resultCode){
            case 0x16:
                dian.performClick();
                break;
            case 0x14:
                dialog.dismiss();
                //获取个人信息
                Map<String ,String > params = new HashMap<>();
                params.put("url" , Constants.MEMBER_INFO);
                newAsyncTaskExecute(Constants.HTTP_ACTION_2 , params);

                supportFragmentManager = getSupportFragmentManager();
                fragmentTransaction = supportFragmentManager.beginTransaction();
                dianFmg = new DianFmg();
                fragmentTransaction.add(R.id.main_fl, dianFmg).commit();
                dian.performClick();
                break;
        }

    }

    @Override
    protected void doInBackgroundTask(int asyncid, Map params, Callback callback) {
        switch (asyncid){
            case Constants.HTTP_ACTION_1:
                AppDbCtrl.doUpdate(asyncid, params, callback);
                break;
            case Constants.HTTP_ACTION_2:
                AppDbCtrl.getServer(asyncid ,params ,callback ,context);
                break;
        }


    }

    @Override
    protected void onPostExecuteTask(int asyncid, ResultInfo resultInfo) {
        super.onPostExecuteTask(asyncid, resultInfo);
        switch (asyncid) {
            case Constants.HTTP_ACTION_2:
                MemberConfigInfo memberConfigInfo = FastJsonUtils.toBean(resultInfo.getData().toString() , MemberConfigInfo.class);
                if (memberConfigInfo != null) {
                    MemberInfo memberInfo = memberConfigInfo.getMemberInfo();
                    configInfo = memberConfigInfo.getSysconfig();
                    //缓存个人信息
                    SpUtils.saveMemberInfo(context , memberInfo);
                    SpUtils.saveConfigInfo(context ,configInfo);

                    if ((Float.parseFloat(configInfo.getAndroidVersion()) - Float.parseFloat(VersionUtils.getLocalVersionName(context))) > 0.000001 ){
                        //更新
                        View view = LayoutInflater.from(context).inflate(R.layout.dialog_is_up_date, null);
                        upDateDialog = new AlertDialog.Builder(context)
                                .setView(view)
                                .create();
                        TextView cancel = view.findViewById(R.id.dialog_update_cancel);
                        TextView confirm = view.findViewById(R.id.dialog_update_confirm);
                        TextView newVersion = view.findViewById(R.id.dialog_version_code_tv);
                        upDateDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                        //检查是否强制更新
                        if ("1".equals(configInfo.getAndroidAutoUpdate())){
                            cancel.setVisibility(View.GONE);
                            upDateDialog.setCanceledOnTouchOutside(false);
                            upDateDialog.setOnKeyListener(new DialogInterface.OnKeyListener() {
                                @Override
                                public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                                    if(keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount()==0)
                                    {
                                        upDateDialog.dismiss();
                                        android.os.Process.killProcess(android.os.Process.myPid());
                                    }
                                    return false;
                                }
                            });
                        }
                        newVersion.setText(configInfo.getAndroidVersion());
                        cancel.setOnClickListener(this);
                        confirm.setOnClickListener(this);
                        upDateDialog.show();
                        WindowManager.LayoutParams params = upDateDialog.getWindow().getAttributes();
                        params.width = 800;
                        upDateDialog.getWindow().setAttributes(params);
                    }
                }

                break;
        }
    }
    //R.id.dialog_login_tv, R.id.dialog_register_tv, R.id.dialog_update_cancel, R.id.dialog_update_confirm
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.dialog_login_tv:
                startActivityForResult(new Intent(this, LoginAty.class),0x12);
                break;
            case R.id.dialog_register_tv:
                startActivityForResult(new Intent(this,RegisterAty.class) ,0x12);
                break;
            case R.id.dialog_update_cancel: //不更新
                upDateDialog.dismiss();
                break;
            case R.id.dialog_update_confirm: //更新
                upDateDialog.dismiss();
                View view = LayoutInflater.from(this).inflate(R.layout.dialog_up_date, null);
                upDating = new AlertDialog.Builder(this)
                        .setView(view)
                        .create();
                //绑定view
                TextView newVersion = view.findViewById(R.id.dialog_update_version);
                TextView currentVersion = view.findViewById(R.id.dialog_update_new_version_tv);
                TextView remark= view.findViewById(R.id.dialog_update_current_version_tv);
//                TextView date= view.findViewById(R.id.dialog_update_new_date_tv);
                pBar = view.findViewById(R.id.dialog_update_pb);
                pText = view.findViewById(R.id.dialog_update_tv);

                newVersion.setText("正在更新"+configInfo.getAndroidVersion()+"版本");
                currentVersion.setText("当前版本："+VersionUtils.getLocalVersionName(context));
                remark.setText(configInfo.getAndroidRemark());
                pBar.setProgress(0);
                pText.setText("0%");
                upDating.show();
                downLoadApk();
                break;
        }
    }

    /**
     * 下载应用
     *
     * @author Administrator
     */
    class DownloadTask extends AsyncTask<String, Integer, String> {
        private Context context;
        private PowerManager.WakeLock mWakeLock;

        public DownloadTask(Context context) {
            this.context = context;
        }

        @Override
        protected String doInBackground(String... sUrl) {
            InputStream input = null;
            OutputStream output = null;
            HttpURLConnection connection = null;
            File file = null;
            try {
                URL url = new URL(sUrl[0]);
                connection = (HttpURLConnection) url.openConnection();
                connection.connect();

                if (connection.getResponseCode() != HttpURLConnection.HTTP_OK) {
                    return "Server returned HTTP "
                            + connection.getResponseCode() + " "
                            + connection.getResponseMessage();
                }

                int fileLength = connection.getContentLength();
                if (Environment.getExternalStorageState().equals(
                        Environment.MEDIA_MOUNTED)) {
                    file = new File(Environment.getExternalStorageDirectory(),
                            DOWNLOAD_NAME);
                    if (!file.exists()) {
                        // 判断父文件夹是否存在
                        if (!file.getParentFile().exists()) {
                            file.getParentFile().mkdirs();
                        }
                    }
                } else {
                    Toast.makeText(context, "sd卡未挂载",
                            Toast.LENGTH_LONG).show();
                }
                input = connection.getInputStream();
                output = new FileOutputStream(file);
                byte data[] = new byte[4096];
                long total = 0;
                int count;
                while ((count = input.read(data)) != -1) {
                    // allow canceling with back button
                    if (isCancelled()) {
                        input.close();
                        return null;
                    }
                    total += count;
                    // publishing the progress....
                    if (fileLength > 0) // only if total length is known
                        publishProgress((int) (total * 100 / fileLength));
                    output.write(data, 0, count);

                }
            } catch (Exception e) {
                return e.toString();

            } finally {
                try {
                    if (output != null)
                        output.close();
                    if (input != null)
                        input.close();
                } catch (IOException ignored) {
                }
                if (connection != null)
                    connection.disconnect();
            }
            return null;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            PowerManager pm = (PowerManager) context
                    .getSystemService(Context.POWER_SERVICE);
            mWakeLock = pm.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK,
                    getClass().getName());
            mWakeLock.acquire();
        }

        @Override
        protected void onProgressUpdate(Integer... progress) {
            super.onProgressUpdate(progress);
//             展示进度条
            pBar.setIndeterminate(false);
            pBar.setMax(100);
            pBar.setProgress(progress[0]);
            pText.setText(progress[0]+"%");
        }

        @Override
        protected void onPostExecute(String result) {
            upDating.dismiss();
            mWakeLock.release();
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                if (getPackageManager().canRequestPackageInstalls()){
                    //有权限 直接安装
                    update();
                }else {
                    Intent intent = new Intent(Settings.ACTION_MANAGE_UNKNOWN_APP_SOURCES);
                    startActivityForResult(intent, 124);
                }
            }else {
                update();
            }
        }
    }

    private static final int REQUEST_CODE_PERMISSION_SD = 101;
    private static final int REQUEST_CODE_SETTING = 300;

    private void update() {
        File file = new File(Environment.getExternalStorageDirectory(),
                DOWNLOAD_NAME);
        Intent intent = new Intent(Intent.ACTION_VIEW);
        // 仅需改变这一行
        FileProvider7.setIntentDataAndType(context,
                intent, "application/vnd.android.package-archive", file, true);
        startActivity(intent);
    }

    public void downLoadApk(){
        // 判断SDK是否>=23
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            // 判断是否已有权限
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                // 申请权限，参数1是当前activity 参数2是我要申请的相关权限（一个String数组）
                //参数3是我定义的requestCode，在onRequestPermissionResult（）要用来识别是否我的返回，
                ActivityCompat.requestPermissions(this,new String[]{
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.READ_EXTERNAL_STORAGE
                }, 123);
            }else{
                //定时任务下载
                new DownloadTask(this).execute(configInfo.getAndroidLink());
            }
        }else {
            //定时任务下载
            new DownloadTask(this).execute(configInfo.getAndroidLink());
        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        // requestCode识别，找到我自己定义的requestCode
        if (requestCode==123) {
            boolean grantedAll = true;
            for (int rangtResult:grantResults) {
//                判断用户是否给予权限
                if (rangtResult != PackageManager.PERMISSION_GRANTED){
                    grantedAll=false;
                    break;
                }
            }
            if (grantedAll){
                //定时任务下载
                new DownloadTask(this).execute(configInfo.getAndroidLink());
            }
        }
    }
}