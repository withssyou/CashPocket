package com.ylbl.cashpocket.ui.center;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.ylbl.cashpocket.R;
import com.ylbl.cashpocket.base.BaseToolBarAty;
import com.ylbl.cashpocket.bean.ResultInfo;
import com.ylbl.cashpocket.net.AppDbCtrl;
import com.ylbl.cashpocket.net.Constants;
import com.ylbl.cashpocket.utils.SpUtils;
import com.ylbl.cashpocket.utils.StringUtils;
import com.ylbl.cashpocket.utils.ToastUtils;
import com.zhy.base.fileprovider.FileProvider7;
import com.zhy.http.okhttp.callback.Callback;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 支付绑定
 * 相册相机取图
 */
public class PayBindAty extends BaseToolBarAty implements View.OnClickListener {
    @BindView(R.id.aty_pay_bind_pay_code_iv)
    ImageView payCode;

    private static final int TAKE_PHOTO = 11;// 拍照
    private static final int CROP_PHOTO = 12;// 裁剪图片
    private static final int LOCAL_CROP = 13;// 本地图库

    private Uri imageUri;// 拍照时的图片uri

    private EditText payPwd;
    private AlertDialog  dialog;
    private Bitmap bitmap;
    @Override
    protected int getLayoutId() {
        return R.layout.aty_pay_bind;
    }

    @Override
    protected String iniTitle() {
        return "提现绑定";
    }

    @Override
    protected void initViewWithBack(boolean setBack) {
        super.initViewWithBack(true);
        if (!TextUtils.isEmpty(SpUtils.getMemberInfo(context).getAlipayCode())){
            payCode.setImageBitmap(StringUtils.stringToBitmap(SpUtils.getMemberInfo(context).getAlipayCode()));
        }
        payCode.setOnClickListener(this);
    }
    @OnClick({R.id.aty_pay_bind_btn} )
    void onClick(){
        if ("0" . equals(SpUtils.getMemberInfo(context).getHasPayPassword())){
            ToastUtils.toastShort(context , "您还未设置支付密码，请到个人设置页面中去设置支付密码");
            return;
        }
        View view = LayoutInflater.from(this).inflate(R.layout.dialog_pay_pwd , null);
        dialog = new AlertDialog.Builder(this)
                .setView(view)
                .create();
        Button cancel = view.findViewById(R.id.dialog_pay_cancel_btn);
        Button confirm = view.findViewById(R.id.dialog_pay_confirm_btn);
        payPwd = view.findViewById(R.id.dialog_pay_pwd_et);

        cancel.setOnClickListener(this);
        confirm.setOnClickListener(this);

        dialog.show();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.dialog_pay_cancel_btn: //取消
                dialog.dismiss();
                break;
            case R.id.aty_pay_bind_pay_code_iv: //添加图片
                choosePicture();
                break;
            case R.id.dialog_pay_confirm_btn: //确认
                if (TextUtils.isEmpty(payPwd.getText().toString().trim())){
                    ToastUtils.toastShort(this , "支付密码不能为空");
                }else {
                    HashMap<String, String> params = new HashMap<>();
                    params.put("url", Constants.CHECK_PAY_PASSWORD);
                    params.put("password" , payPwd.getText().toString().trim());
                    newAsyncTaskExecute(Constants.HTTP_ACTION_2 , params);
                }
                break;
        }
//
//
    }

    @Override
    protected void doInBackgroundTask(int asyncid, Map params, Callback callback) {
//        super.doInBackgroundTask(asyncid, params, callback);
        AppDbCtrl.doServer(asyncid,params ,callback ,context);
    }

    @Override
    protected void onPostExecuteTask(int asyncid, ResultInfo resultInfo) {
        super.onPostExecuteTask(asyncid, resultInfo);
        switch (asyncid){
            case Constants.HTTP_ACTION_1:
                ToastUtils.toastShort(this , "绑定成功");
                break;
            case Constants.HTTP_ACTION_2:
                dialog.dismiss();
                //提交服务器绑定请求
                HashMap<String, String> params = new HashMap<>();
                params.put("url", Constants.PAY_BIND);
                params.put("alipayCode" , StringUtils.BitmaptoBase64(bitmap));
                newAsyncTaskExecute(Constants.HTTP_ACTION_1 , params);
                break;
        }

    }
    /**
     * 相册相机取图
     */
    private void takePhotoOrSelectPicture() {
        CharSequence[] items = {"拍照","图库"};// 裁剪items选项

        // 弹出对话框提示用户拍照或者是通过本地图库选择图片
        new AlertDialog.Builder(this)
                .setItems(items, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which){
                            // 选择了拍照
                            case 0:
                                // 创建文件保存拍照的图片
                                File takePhotoImage = new File(getExternalCacheDir(), "take_photo_image.jpg");
                                try {
                                    // 文件存在，删除文件
                                    if(takePhotoImage.exists()){
                                        takePhotoImage.delete();
                                    }
                                    // 根据路径名自动的创建一个新的空文件
                                    takePhotoImage.createNewFile();
                                }catch (Exception e){
                                    e.printStackTrace();
                                }
                                // 获取图片文件的uri对象
                                if (Build.VERSION.SDK_INT >= 24) {
                                    imageUri = FileProvider7.getUriForFile(context, takePhotoImage);
                                } else {
                                    imageUri = Uri.fromFile(takePhotoImage);
                                }
                                // 创建Intent，用于启动手机的照相机拍照
                                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                                // 指定输出到文件uri中
                                intent.putExtra(MediaStore.EXTRA_OUTPUT,imageUri);
                                // 启动intent开始拍照
                                startActivityForResult(intent, TAKE_PHOTO);
                                break;
                            // 调用系统图库
                            case 1:
                                // 创建Intent，用于打开手机本地图库选择图片
                                Intent intent1 = new Intent(Intent.ACTION_PICK,
                                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                                // 启动intent打开本地图库
                                startActivityForResult(intent1,LOCAL_CROP);
                                break;
                        }
                    }
                }).show();
    }

    /**
     * 调用startActivityForResult方法启动一个intent后，
     * 可以在该方法中拿到返回的数据
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode){
            case TAKE_PHOTO:// 拍照
                if(resultCode == RESULT_OK){
                    try{
                        // 展示拍照后裁剪的图片
                        if(imageUri != null){
                            // 创建BitmapFactory.Options对象
                            BitmapFactory.Options option = new BitmapFactory.Options();
                            // 属性设置，用于压缩bitmap对象
                            option.inSampleSize = 4;
                            option.inPreferredConfig = Bitmap.Config.RGB_565;
                            // 根据文件流解析生成Bitmap对象
                            bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(imageUri), null, option);
                            // 展示图片
                            payCode.setImageBitmap(bitmap);
                        }
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }
                break;
            case LOCAL_CROP:// 系统图库
                if(resultCode == RESULT_OK){
                    Uri uri = data.getData();
                    payCode.setImageURI(uri);
                }
                break;
        }
    }
    public void choosePicture(){
        // 判断SDK是否>=23
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            // 判断是否已有权限
            if (ContextCompat.checkSelfPermission(this,Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                // 申请权限，参数1是当前activity 参数2是我要申请的相关权限（一个String数组）
                //参数3是我定义的requestCode，在onRequestPermissionResult（）要用来识别是否我的返回，
                ActivityCompat.requestPermissions(this,new String[]{
                        Manifest.permission.CAMERA,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.READ_EXTERNAL_STORAGE
                        }, 123);
            }else{
                takePhotoOrSelectPicture();
            }
        }else {
                takePhotoOrSelectPicture();
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
               takePhotoOrSelectPicture();
            }
        }
    }
}
