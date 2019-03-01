package com.ylbl.cashpocket.ui.redenvelops;

import android.Manifest;
import android.app.ActivityManager;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.ylbl.cashpocket.R;
import com.ylbl.cashpocket.adapter.GvAdapter;
import com.ylbl.cashpocket.base.BaseToolBarAty;
import com.ylbl.cashpocket.bean.Order;
import com.ylbl.cashpocket.bean.OrderInfo;
import com.ylbl.cashpocket.bean.ResultInfo;
import com.ylbl.cashpocket.net.AppDbCtrl;
import com.ylbl.cashpocket.net.Constants;
import com.ylbl.cashpocket.ui.center.ModifyInfoAty;
import com.ylbl.cashpocket.utils.FastJsonUtils;
import com.ylbl.cashpocket.utils.SpUtils;
import com.ylbl.cashpocket.utils.StringUtils;
import com.ylbl.cashpocket.utils.ToastUtils;
import com.zhy.base.fileprovider.FileProvider7;
import com.zhy.http.okhttp.callback.Callback;
import com.zzti.fengyongge.imagepicker.PhotoSelectorActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 红包发布
 */
public class PublishAty extends BaseToolBarAty implements View.OnClickListener {
    @BindView(R.id.aty_publish_content_et)
    EditText content;
    @BindView(R.id.aty_publish_limit_tv)
    TextView limit;
    @BindView(R.id.aty_publish_pocket_num_et)
    EditText pubNum;
//    @BindView(R.id.aty_publish_pocket_money_et)
//    EditText pubMoney;
    @BindView(R.id.aty_publish_link_title_et)
    EditText pubTitle;
    @BindView(R.id.aty_publish_link_url_et)
    EditText pubUrl;
    @BindView(R.id.aty_publish_publish_btn)
    Button confirmBtn;
    @BindView(R.id.aty_publish_gv)
    GridView gridView;

    List<Bitmap> images = new ArrayList<>();

    private static final int TAKE_PHOTO = 11;// 拍照
    private static final int CROP_PHOTO = 12;// 裁剪图片
    private static final int LOCAL_CROP = 13;// 本地图库

    private Uri imageUri;// 拍照时的图片uri
    private AlertDialog payPwdDialog  , dialog;
    private EditText payPwd;
    private GvAdapter adapter;
    private String payType = "1";
    @Override
    protected int getLayoutId() {
        return R.layout.aty_publish;
    }

    @Override
    protected String iniTitle() {
        return "红包发布";
    }

    @Override
    protected void initViewWithBack(boolean setBack) {
        super.initViewWithBack(setBack);
        setResult(0x16);
        content.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (count < 200){
                    limit.setText(count+"/200");
                }else {
                    content.setCursorVisible(false);
                    content.setFocusable(false);
                    content.setFocusableInTouchMode(false);
                }
            }
            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        adapter = new GvAdapter(context , images);
        gridView.setAdapter(adapter);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position==parent.getChildCount()-1){
                    if (position==9){//不能点击了
                    }else{
                        choosePicture();
                    }
                }else{//可以加点预览功能。

                }
            }
        });
    }

    @OnClick({R.id.aty_publish_publish_btn /*, R.id.aty_publish_area_tv ,*/})
    void click(View view){
        switch (view.getId()){
            case R.id.aty_publish_publish_btn: //红包发布
                if (TextUtils.isEmpty(content.getText().toString())){
                    ToastUtils.toastShort(context , "发布内容不能为空");
                    return;
                }
                if (TextUtils.isEmpty(pubNum.getText().toString()) ){
                    ToastUtils.toastShort(context , "金额不能为空");
                    return;
                }
                if (Integer.parseInt(pubNum.getText().toString()) % 10 != 0 ){
                    ToastUtils.toastShort(context , "金额数必须是10或10的倍数");
                    return;
                }
                if (TextUtils.isEmpty(pubTitle.getText().toString())){
                    ToastUtils.toastShort(context , "标题不能为空");
                    return;
                }
                if (images.size() <= 0) {
                    ToastUtils.toastShort(context , "图片不能为空");
                    return;
                }

                View v = LayoutInflater.from(this).inflate(R.layout.dialog_pay, null);
                dialog = new AlertDialog.Builder(this)
                        .setView(v)
                        .create();
                RadioGroup radioGroup = v.findViewById(R.id.rg);
                radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(RadioGroup group, int checkedId) {
                        switch (checkedId){
                            case R.id.dialog_pay_dian_rb:
                                payType = "1";
                                break;
                            case R.id.dialog_pay_ali_rb:
                                payType = "2";
                                break;
//                            case R.id.dialog_pay_wechat_rb:
//                                dialog.dismiss();
//                                showPayDialog("dian");
//                                break;
                        }
                    }
                });
                TextView totalMonty = v.findViewById(R.id.dialog_pay_money_show_tv);
                TextView dianRestMoney = v.findViewById(R.id.dialog_dian_money_rest_tv);
                Button payType = v.findViewById(R.id.dialog_publish_type_btn);
                //需要支付的金额
                totalMonty.setText(pubNum.getText().toString().trim());
                //点点余额
                dianRestMoney.setText(StringUtils.doubleToString(Double.parseDouble(SpUtils.getMemberInfo(context).getCashNum())));
                payType.setOnClickListener(this);
                dialog.show();
                break;
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.dialog_pay_cancel_btn:
                payPwdDialog.dismiss();
                break;
            case R.id.dialog_pay_confirm_btn:

                if (TextUtils.isEmpty(payPwd.getText().toString().trim())){
                    ToastUtils.toastShort(this , "请输入支付密码");
                }else {
                    Map<String ,String> params = new HashMap<>();
                    params.put("url" , Constants.CHECK_PAY_PASSWORD);
                    params.put("password" , payPwd.getText().toString().trim());
                    newAsyncTaskExecute(Constants.HTTP_ACTION_1 , params);
                }
                break;
            case R.id.dialog_publish_type_btn://选择支付方式
                dialog.dismiss();
                if ("1".equals(payType)){
                    if ("0".equals(SpUtils.getMemberInfo(context).getHasPayPassword())){
                        ToastUtils.toastShort(context , "您尚未设置支付密码，请前去设置");
                        startActivity(new Intent(context , ModifyInfoAty.class));
                    }else {
                        showPayDialog();
                    }
                }else {
                    publish();
                }
                break;
        }
    }
    /**
     * 校验支付密码
     */
    private void showPayDialog() {
        View view = LayoutInflater.from(this).inflate(R.layout.dialog_pay_pwd, null);
        payPwdDialog = new AlertDialog.Builder(this)
                .setView(view)
                .create();
        Button cancel = view.findViewById(R.id.dialog_pay_cancel_btn);
        Button confirm = view.findViewById(R.id.dialog_pay_confirm_btn);
        payPwd = view.findViewById(R.id.dialog_pay_pwd_et);

        cancel.setOnClickListener(this);
        confirm.setOnClickListener(this);

        payPwdDialog.show();
    }

    private void publish(){
            Map<String ,String> params = new HashMap<>();
            params.put("url" , Constants.OUT_RED_ENVELOPS);
            params.put("redpacketMoney" , pubNum.getText().toString().trim());
            params.put("payType" , payType);
            params.put("position" , "4");
            params.put("content" , content.getText().toString().trim());
            params.put("linkTitle" , pubTitle.getText().toString().trim());

            if (!TextUtils.isEmpty(pubUrl.getText().toString())){
                params.put("linkAddress" , pubUrl.getText().toString().trim());
            }
            params.put("images", FastJsonUtils.toJSON(StringUtils.BitmaptoBase64(images)));
            newAsyncTaskExecute(Constants.HTTP_ACTION_2 , params);

    }

    @Override
    protected void doInBackgroundTask(int asyncid, Map params, Callback callback) {
//        super.doInBackgroundTask(asyncid, params, callback);
        AppDbCtrl.doServer(asyncid , params ,callback , context);
    }

    @Override
    protected void onPostExecuteTask(int asyncid, ResultInfo resultInfo) {
        super.onPostExecuteTask(asyncid, resultInfo);
        switch (asyncid){
            case Constants.HTTP_ACTION_1: //校验
                //密码校验成功
                payPwdDialog.dismiss();
                publish();
                break;
            case Constants.HTTP_ACTION_2://发布
                //发布成功 获取到订单号 支付
                OrderInfo info = FastJsonUtils.toBean(resultInfo.getData().toString() , OrderInfo.class);
                if (info != null){
                    switch (info.getContent_type().toString()) {
                        case "text":
                            if (!TextUtils.isEmpty(info.getOrderStr())) {
                                String str = info.getOrderStr();
                                finish();
                                Intent i = new Intent(context , ShowCodeAty.class);
                                startActivity(i);
                                try {
                                    JSONObject object = new JSONObject(str);
                                    String zCode = (String) object.get("actionUrl");
                                    Intent intent = new Intent(Intent.ACTION_VIEW);
                                    intent.setData(Uri.parse(zCode));
                                    startActivity(intent); //启动浏览器
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                            break;
                        case "json":
                            if (!TextUtils.isEmpty(info.getOrderStr())) {
                                String str = info.getOrderStr();
                                try {
                                    JSONObject object = new JSONObject(str);
                                    JSONObject jsonObject = (JSONObject) object.get("data");
                                    String url = (String) jsonObject.get("qrcode");
                                    Intent intent = new Intent();
                                    intent.setData(Uri.parse(url));//Url 就是你要打开的网址
                                    intent.setAction(Intent.ACTION_VIEW);
                                    startActivity(intent); //启动浏览器
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                            break;
                        }
                }
                if (info != null && TextUtils.isEmpty(info.getOrderStr())){
                        //零钱支付成功
                        startActivity(new Intent( this , PublishDoneAty.class));
                        finish();
                }

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
                                Intent intent1 = new Intent(context, PhotoSelectorActivity.class);
                                intent1.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                                intent1.putExtra("limit", 9 );//number是选择图片的数量
                                startActivityForResult(intent1, 0);

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
                            option.inSampleSize = 2;
                            option.inPreferredConfig = Bitmap.Config.RGB_565;
                            // 根据文件流解析生成Bitmap对象
                           Bitmap bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(imageUri), null, option);
                            // 展示图片
                            images.add(bitmap);
                            setGridViewHeight(gridView);
                            adapter.notifyDataSetChanged();

                        }
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }
                break;
            case 0:
                if (data != null) {
                    List<String> paths = (List<String>) data.getExtras().getSerializable("photos");//path是选择拍照或者图片的地址数组
                    // 创建BitmapFactory.Options对象
                    BitmapFactory.Options option = new BitmapFactory.Options();
                    // 属性设置，用于压缩bitmap对象
                    option.inSampleSize = 2;
                    option.inPreferredConfig = Bitmap.Config.RGB_565;
                    Bitmap bitmap = null;
                    for (String path: paths) {
                        bitmap = BitmapFactory.decodeFile(path , option);
                        if (images.size() >= 9){
                            ToastUtils.toastShort(context , "最多添加9张图片");
                            break;
                        }
                        images.add(bitmap);
                    }
                    setGridViewHeight(gridView);
                    adapter.notifyDataSetChanged();
                }
                break;
        }
    }

    public void choosePicture(){
        // 判断SDK是否>=23
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            // 判断是否已有权限
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
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
    public void setGridViewHeight(GridView gridview) {
        // 获取gridview的adapter
        ListAdapter listAdapter = gridview.getAdapter();
        if (listAdapter == null) {
            return;
        }
        // 固定列宽，有多少列
        int numColumns= gridview.getNumColumns(); //5
        int totalHeight = 0;
        // 计算每一列的高度之和
        for (int i = 0; i < listAdapter.getCount(); i += numColumns) {
            // 获取gridview的每一个item
            View listItem = listAdapter.getView(i, null, gridview);
            listItem.measure(0, 0);
            // 获取item的高度和
            totalHeight += listItem.getMeasuredHeight();
        }

        // 获取gridview的布局参数
        ViewGroup.LayoutParams params = gridview.getLayoutParams();
        // 设置高度
        params.height = totalHeight;
        // 设置参数
        gridview.setLayoutParams(params);
    }
}
