package com.ylbl.cashpocket.ui.center;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.CountDownTimer;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import com.bigkoo.pickerview.builder.OptionsPickerBuilder;
import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.listener.OnOptionsSelectListener;
import com.bigkoo.pickerview.listener.OnTimeSelectListener;
import com.bigkoo.pickerview.view.OptionsPickerView;
import com.bigkoo.pickerview.view.TimePickerView;
import com.squareup.picasso.Picasso;
import com.ylbl.cashpocket.R;
import com.ylbl.cashpocket.base.BaseToolBarAty;
import com.ylbl.cashpocket.bean.MemberInfo;
import com.ylbl.cashpocket.bean.ShengBean;
import com.ylbl.cashpocket.bean.ResultInfo;
import com.ylbl.cashpocket.net.AppDbCtrl;
import com.ylbl.cashpocket.net.Constants;
import com.ylbl.cashpocket.utils.FastJsonUtils;
import com.ylbl.cashpocket.utils.GetJsonDataUtil;
import com.ylbl.cashpocket.utils.ImageCacheUtils;
import com.ylbl.cashpocket.utils.SpUtils;
import com.ylbl.cashpocket.utils.StringUtils;
import com.ylbl.cashpocket.utils.ToastUtils;
import com.zhy.base.fileprovider.FileProvider7;
import com.zhy.http.okhttp.callback.Callback;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

public class ModifyInfoAty extends BaseToolBarAty implements View.OnClickListener {
    @BindView(R.id.aty_modify_icon_civ)
    CircleImageView icon;
    @BindView(R.id.aty_modify_phone_et)
    EditText phone;
    @BindView(R.id.aty_modify_name_et)
    EditText name;
    @BindView(R.id.aty_modify_gender_tv)
    TextView gender;
    @BindView(R.id.aty_modify_birthday_tv)
    TextView birthday;
    @BindView(R.id.aty_modify_address_tv)
    TextView address;
    @BindView(R.id.aty_modify_job_tv)
    TextView jobs;

    private static final int TAKE_PHOTO = 11;// 拍照
    private static final int CROP_PHOTO = 12;// 裁剪图片
    private static final int LOCAL_CROP = 13;// 本地图库

    //  省
    private List<ShengBean> options1Items = new ArrayList<>();
    //  市
    private ArrayList<ArrayList<String>> options2Items = new ArrayList<>();
    //  区
    private ArrayList<ArrayList<ArrayList<String>>> options3Items = new ArrayList<>();

    private Uri imageUri;// 拍照时的图片uri
    private Bitmap bitmap ;//头像
    private AlertDialog  modifyDialog ,modifyPayDialog ,genderDialog ;
    private EditText newPwd1 ,newPwd2 , codeEt ,payNewPwd1 ,payNewPwd2 , payCodeEt;
    private RadioButton radioButton;
    private RadioGroup radioGroup;
    private String province , city;
    private MemberInfo memberInfo;
    private Button codeBtn;
    @Override
    protected int getLayoutId() {
        return R.layout.aty_modify;
    }

    @Override
    protected String iniTitle() {
        return "修改资料";
    }

    @Override
    protected void initViewWithBack(boolean setBack) {
        super.initViewWithBack(true);
        memberInfo = SpUtils.getMemberInfo(context);
//        //加载历史信息
        Intent intent = getIntent();
        phone.setText(TextUtils.isEmpty(intent.getStringExtra("phone")) ? "" : intent.getStringExtra("phone"));
        name.setText(TextUtils.isEmpty(intent.getStringExtra("name")) ? "" : intent.getStringExtra("name"));
        gender.setText(TextUtils.isEmpty(intent.getStringExtra("gender")) ? "" : intent.getStringExtra("gender"));
        birthday.setText(TextUtils.isEmpty(intent.getStringExtra("birthday")) ? "" : intent.getStringExtra("birthday"));
        address.setText(TextUtils.isEmpty(intent.getStringExtra("address")) ? "" : intent.getStringExtra("address"));
        jobs.setText(TextUtils.isEmpty(intent.getStringExtra("jobs")) ? "" : intent.getStringExtra("jobs"));
        //加载头像图片
        if (TextUtils.isEmpty(intent.getStringExtra("headImg"))){
            icon.setImageResource(R.mipmap.icon_default);
        }else {
            Picasso.with(context).load(intent.getStringExtra("headImg")).error(R.mipmap.icon_default).into(icon);
        }
    }

    @OnClick({R.id.aty_modify_icon_civ  , R.id.aty_modify_pwd_ll ,R.id.aty_modify_pay_pwd_ll,
              R.id.aty_modify_gender_ll , R.id.aty_modify_birthday_ll ,R.id.aty_modify_address_ll,
              R.id.aty_modify_job_ll, R.id.tv_title_save })
    void click(View view){
        switch (view.getId()){
            case R.id.aty_modify_icon_civ:
                choosePicture();
                break;
            case R.id.aty_modify_pwd_ll://修改登录密码
                modifyLoginDialog();
                break;
            case R.id.aty_modify_pay_pwd_ll: //修改支付密码
                modifyPayDialog();
                break;
            case R.id.aty_modify_gender_ll://性别
                genderDialog();
                break;
            case R.id.aty_modify_birthday_ll://出生日期
                birthDialog();
                break;
            case R.id.aty_modify_address_ll://地址
                addressDialog();
                break;
            case R.id.aty_modify_job_ll://行业
                vocationDialog();
                break;
            case R.id.tv_title_save://保存
                Map<String ,String> params = new HashMap<>();
                params.put("url" , Constants.MODIFY_MEMINFO);
                if (bitmap != null){
                    params.put("headImage" ,StringUtils.BitmaptoBase64(bitmap));
                    Log.i("TAG" ,StringUtils.BitmaptoBase64(bitmap));
                }
                params.put("name" ,name.getText().toString().trim() );
                if (!"请选择".equals(gender.getText().toString())){
                    params.put("gender" ,gender.getText().toString().trim() );
                }
                if (!TextUtils.isEmpty(birthday.getText().toString().trim())){
                    params.put("birthday" ,birthday.getText().toString().trim() );
                }
                if (!TextUtils.isEmpty(jobs.getText().toString().trim())){
                    params.put("industry" ,jobs.getText().toString().trim() );
                }
                if (!TextUtils.isEmpty(province)){
                    params.put("province" ,province );
                }
                if (!TextUtils.isEmpty(city)){
                    params.put("city" ,city );
                }
                newAsyncTaskExecute(Constants.HTTP_ACTION_4 , params);
                break;
        }
    }

    /**
     * 行业选择器弹出框
     *
     */
    private void vocationDialog() {
        final CharSequence[] items = {"计算机/互联网/通信/电子",
                "会计/金融/银行/保险",
                "贸易/消费/制造/营运",
                "制药/医疗",
                "广告/媒体",
                "房地产/建筑",
                "专业服务/教育/培训",
                "服务业", "物流/运输",
                "能源/原材料",
                "政府/非营利组织/其他"};// 裁剪items选项

        // 弹出对话框提示用户拍照或者是通过本地图库选择图片
        new AlertDialog.Builder(this)
                .setItems(items, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        jobs.setText(items[which].toString());
                    }
                }).show();
    }

    /**
     * 选择地址弹出框
     */
    private void addressDialog() {
        parseData();
        showPickerView();
    }

    /**
     * 选择生日弹出框
     */
    private void birthDialog() {
        TimePickerView pvTime = new TimePickerBuilder(this, new OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {
                birthday.setText(new SimpleDateFormat("yyyy-MM-dd").format(date));
            }
        })
                .setType(new boolean[]{true, true, true, false, false, false})// 默认全部显示
                .setCancelText("取消")//取消按钮文字
                .setSubmitText("确定")//确认按钮文字
//                .setContentSize(18)//滚轮文字大小
//                .setTitleSize(20)//标题文字大小
//                //.setTitleText("Title")//标题文字
//                .setOutSideCancelable(true)//点击屏幕，点在控件外部范围时，是否取消显示
//                .isCyclic(true)//是否循环滚动
//                //.setTitleColor(Color.BLACK)//标题文字颜色
//                .setSubmitColor(Color.BLUE)//确定按钮文字颜色
//                .setCancelColor(Color.BLUE)//取消按钮文字颜色
//                //.setTitleBgColor(0xFF666666)//标题背景颜色 Night mode
//                .setBgColor(0xFF333333)//滚轮背景颜色 Night mode
////                .setDate(selectedDate)// 如果不设置的话，默认是系统时间*/
////                .setRangDate(startDate,endDate)//起始终止年月日设定
//                //.setLabel("年","月","日","时","分","秒")//默认设置为年月日时分秒
                .isCenterLabel(false) //是否只显示中间选中项的label文字，false则每项item全部都带有label。
                //.isDialog(true)//是否显示为对话框样式
                .build();

        pvTime.show();

    }

    /**
     * 性别的弹出框
     */
    private void genderDialog() {
        final View view = LayoutInflater.from(this).inflate(R.layout.dialog_modify_gender , null);
        genderDialog = new AlertDialog.Builder(this)
                .setView(view)
                .setCancelable(false)
                .create();
        radioGroup = view.findViewById(R.id.dialog_modify_gender_bg);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                radioButton = view.findViewById(radioGroup.getCheckedRadioButtonId());
                gender.setText(radioButton.getText().toString());
            }
        });
        Button  confirm =  view.findViewById(R.id.dialog_modify_gender_confirm_btn);
        Button  cancel =  view.findViewById(R.id.dialog_modify_gender_cancel_btn);

        cancel.setOnClickListener(this);
        confirm.setOnClickListener(this);
        genderDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        genderDialog.show();
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.dialog_modify_login_pwd_code_btn: //获取验证码
            case R.id.dialog_modify_pay_pwd_code_btn:   //获取验证码
                    Map<String ,String> param = new HashMap<>();
                    param.put("url" , Constants.USER_VERCODE);
                    param.put("mobilePhone" , memberInfo.getMobilePhone());
                    newAsyncTaskExecute(Constants.HTTP_ACTION_1 , param);
                //计数器
                new CountDownTimer(60000, 1000) {
                    @Override
                    public void onTick(long millisUntilFinished) {
                        codeBtn.setEnabled(false);
                        codeBtn.setText(millisUntilFinished / 1000 + "");
                    }
                    @Override
                    public void onFinish() {
                        codeBtn.setEnabled(true);
                        codeBtn.setText("获取验证码");
                    }
                }.start();

                break;
            case R.id.dialog_modify_login_pwd_cancel_btn:
                modifyDialog.dismiss();
                break;
            case R.id.dialog_modify_pay_pwd_cancel_btn:
                modifyPayDialog.dismiss();
                break;
            case R.id.dialog_modify_gender_cancel_btn:
                genderDialog.dismiss();
                break;
            case R.id.dialog_modify_login_pwd_confirm_btn:
                if (TextUtils.isEmpty(newPwd1.getText().toString())){
                    ToastUtils.toastShort(this , "请输入密码");
                }else if (TextUtils.isEmpty(newPwd2.getText().toString())){
                    ToastUtils.toastShort(this , "请确认密码");
                }else if (TextUtils.isEmpty(codeEt.getText().toString())){
                    ToastUtils.toastShort(this , "请输入验证码");
                }else {
                    Map<String ,String> params = new HashMap<>();
                    params.put("url" , Constants.MODIFY_PWD);
                    params.put("verCode" ,codeEt.getText().toString().trim() );
                    params.put("newPass" , newPwd1.getText().toString().trim());
                    params.put("type" , "login");
                    newAsyncTaskExecute(Constants.HTTP_ACTION_2 , params);
                }
                break;
            case R.id.dialog_modify_pay_pwd_confirm_btn:
                if (TextUtils.isEmpty(payNewPwd1.getText().toString())){
                    ToastUtils.toastShort(this , "请输入密码");
                }else if (TextUtils.isEmpty(payNewPwd2.getText().toString())){
                    ToastUtils.toastShort(this , "请确认密码");
                }else if (TextUtils.isEmpty(payCodeEt.getText().toString())){
                    ToastUtils.toastShort(this , "请输入验证码");
                }else {
                    Map<String ,String> params = new HashMap<>();
                    params.put("url" , Constants.MODIFY_PWD);
                    params.put("verCode" ,payCodeEt.getText().toString().trim() );
                    params.put("newPass" , payNewPwd1.getText().toString().trim());
                    params.put("type" , "pay");
                    newAsyncTaskExecute(Constants.HTTP_ACTION_3 , params);
                }
                break;
            case R.id.dialog_modify_gender_confirm_btn:
//                String checkedGender = radioButton.getText().toString();
//                gender.setText(checkedGender);
                genderDialog.dismiss();
                break;
        }
    }

    /**
     * 修改支付密码
     */
    private void modifyPayDialog() {
        View view = LayoutInflater.from(this).inflate(R.layout.dialog_modify_pay_pwd , null);
        modifyPayDialog = new AlertDialog.Builder(this)
                .setView(view)
                .setCancelable(false)
                .create();
        payNewPwd1 = view.findViewById(R.id.dialog_modify_pay_new_pwd1_et);
        payNewPwd2 = view.findViewById(R.id.dialog_modify_pay_new_pwd2_et);
        payCodeEt = view.findViewById(R.id.dialog_modify_pay_pwd_code_et);
        codeBtn =  view.findViewById(R.id.dialog_modify_pay_pwd_code_btn);
        Button  cancel =  view.findViewById(R.id.dialog_modify_pay_pwd_cancel_btn);
        Button  confirm =  view.findViewById(R.id.dialog_modify_pay_pwd_confirm_btn);

        codeBtn.setOnClickListener(this);
        cancel.setOnClickListener(this);
        confirm.setOnClickListener(this);
        modifyPayDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        modifyPayDialog.show();
    }
    /**
     * 修改登录密码
     */
    private void modifyLoginDialog() {
        View view = LayoutInflater.from(this).inflate(R.layout.dialog_modify_login_pwd , null);
        modifyDialog = new AlertDialog.Builder(this)
                .setView(view)
                .setCancelable(false)
                .create();
        newPwd1 = view.findViewById(R.id.dialog_modify_login_new_pwd1_et);
        newPwd2 = view.findViewById(R.id.dialog_modify_login_new_pwd2_et);
        codeEt = view.findViewById(R.id.dialog_modify_login_pwd_code_et);
        codeBtn =  view.findViewById(R.id.dialog_modify_login_pwd_code_btn);
        Button  cancel =  view.findViewById(R.id.dialog_modify_login_pwd_cancel_btn);
        Button  confirm =  view.findViewById(R.id.dialog_modify_login_pwd_confirm_btn);

        codeBtn.setOnClickListener(this);
        cancel.setOnClickListener(this);
        confirm.setOnClickListener(this);
        modifyDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        modifyDialog.show();
    }

    @Override
    protected void doInBackgroundTask(int asyncid, Map params, Callback callback) {
//        super.doInBackgroundTask(asyncid, params, callback);
          AppDbCtrl.doServer(asyncid , params ,callback ,context);
    }

    @Override
    protected void onPostExecuteTask(int asyncid, ResultInfo resultInfo) {
        super.onPostExecuteTask(asyncid, resultInfo);
        switch (asyncid){
            case Constants.HTTP_ACTION_1:
//                ToastUtils.toastShort(this , "个人信息修改成功");
                break;
            case Constants.HTTP_ACTION_2://修改登录密码
                ToastUtils.toastShort(this , "登录密码修改成功");
                modifyDialog.dismiss();
                break;
            case Constants.HTTP_ACTION_3://修改支付密码
                memberInfo.setHasPayPassword("1");
                SpUtils.saveMemberInfo(context , memberInfo);
                modifyPayDialog.dismiss();
                break;
            case Constants.HTTP_ACTION_4://修改个人资料密码
                ToastUtils.toastShort(this , "个人资料修改成功");
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

    private void parseData() {
        String jsonStr = new GetJsonDataUtil().getJson(this, "province.json");//获取assets目录下的json文件数据
        List<ShengBean> shengList = FastJsonUtils.toList(jsonStr ,ShengBean.class);
//     把解析后的数据组装成想要的list
        options1Items = shengList;
//     遍历省
        for (int i = 0; i < shengList.size(); i++) {
//         存放城市
            ArrayList<String> cityList = new ArrayList<>();
//         存放区
            ArrayList<ArrayList<String>> province_AreaList = new ArrayList<>();
//         遍历市
            for (int c = 0; c < shengList.get(i).city.size(); c++) {
//        拿到城市名称
                String cityName = shengList.get(i).city.get(c).name;
                cityList.add(cityName);

                ArrayList<String> city_AreaList = new ArrayList<>();//该城市的所有地区列表
                if (shengList.get(i).city.get(c).area == null || shengList.get(i).city.get(c).area.size() == 0) {
                    city_AreaList.add("");
                } else {
                    city_AreaList.addAll(shengList.get(i).city.get(c).area);
                }
                province_AreaList.add(city_AreaList);
            }
            /**
             * 添加城市数据
             */
            options2Items.add(cityList);
            /**
             * 添加地区数据
             */
            options3Items.add(province_AreaList);
        }
    }
    private void showPickerView() {// 弹出选择器

        OptionsPickerView pvOptions = new OptionsPickerBuilder(this, new OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int options2, int options3, View v) {
                //返回的分别是三个级别的选中位置
                String tx = options1Items.get(options1).name +
                        options2Items.get(options1).get(options2) +
                        options3Items.get(options1).get(options2).get(options3);
                //保存地址信息
                province = options1Items.get(options1).name;
                city =  options2Items.get(options1).get(options2);
                //展示
                address.setText(tx);

            }
        })
                .setTitleText("城市选择")
                .setDividerColor(Color.BLACK)
                .setTextColorCenter(Color.BLACK) //设置选中项文字颜色
                .setContentTextSize(20)
                .build();
        pvOptions.setPicker(options1Items, options2Items, options3Items);//三级选择器
        pvOptions.show();
    }
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
                            icon.setImageBitmap(bitmap);
                        }
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }
                break;
            case LOCAL_CROP:// 系统图库
                if(resultCode == RESULT_OK){
                    Uri uri = data.getData();
                    icon.setImageURI(uri);
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
}
