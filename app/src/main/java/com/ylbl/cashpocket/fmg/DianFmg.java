package com.ylbl.cashpocket.fmg;

import android.Manifest;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.amap.api.fence.GeoFence;
import com.amap.api.fence.GeoFenceClient;
import com.amap.api.fence.GeoFenceListener;
import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.location.DPoint;
import com.amap.api.maps.AMap;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.MapView;
import com.amap.api.maps.UiSettings;
import com.amap.api.maps.model.BitmapDescriptor;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.CircleOptions;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.Marker;
import com.amap.api.maps.model.MarkerOptions;
import com.amap.api.maps.model.MyLocationStyle;
import com.mylhyl.acp.Acp;
import com.mylhyl.acp.AcpListener;
import com.mylhyl.acp.AcpOptions;
import com.squareup.picasso.Picasso;
import com.ylbl.cashpocket.R;
import com.ylbl.cashpocket.base.BaseToolbarFmg;
import com.ylbl.cashpocket.bean.Goods;
import com.ylbl.cashpocket.bean.GpsLocation;
import com.ylbl.cashpocket.bean.RedPacket;
import com.ylbl.cashpocket.bean.ResultInfo;
import com.ylbl.cashpocket.net.AppDbCtrl;
import com.ylbl.cashpocket.net.Constants;
import com.ylbl.cashpocket.ui.center.InviteAty;
import com.ylbl.cashpocket.ui.news.RulesAty;
import com.ylbl.cashpocket.ui.redenvelops.PocketDetailsAty;
import com.ylbl.cashpocket.utils.FastJsonUtils;
import com.ylbl.cashpocket.utils.LocationUtils;
import com.ylbl.cashpocket.utils.SpUtils;
import com.ylbl.cashpocket.utils.ToastUtils;
import com.zhy.http.okhttp.callback.Callback;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

public class DianFmg extends BaseToolbarFmg implements View.OnClickListener ,AMap.OnMarkerClickListener {
    @BindView(R.id.frag_home_mv)
    MapView mapView;
    @BindView(R.id.frag_dian_invite_iv)
    ImageView inviteBtn;

    private AMap aMap;
    public AMapLocationClient mLocationClient;
    public AMapLocationClientOption mLocationOption;
    private GeoFenceClient mGeoFenceClient;
    //服务器上的红包数据
    private List<Goods> datas;
    private AlertDialog dialog;
    private AlertDialog lordDialog;
    private double latitude;
    private double longtude;
    private int goodId; //用来记录打开红包的id
    private Marker marker;
    //声明定位回调监听器
    public AMapLocationListener mLocationListener = new AMapLocationListener() {
        @Override
        public void onLocationChanged(AMapLocation aMapLocation) {
            latitude = aMapLocation.getLatitude();
            longtude = aMapLocation.getLongitude();
            // 设置当前地图显示为当前位置
            aMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(latitude, longtude), 15));
            setupLocationStyle();
            //展示围栏
            circle(latitude, longtude);
//            showWeiLan(latitude, longtude);
            //获取红包数据
            Map<String ,String > params = new HashMap<>();
            params.put("lon" , longtude+"");
            params.put("lat" , latitude+"");
            params.put("url" , Constants.NEAR_RED_ENVENLOPES);
            newAsyncTaskExecute(Constants.HTTP_ACTION_1 , params);

        }
    };
    /**
     * 领红包对话框
     * @param goods
     */
    private void showDialog(final Goods goods) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        View view = LayoutInflater.from(context).inflate(R.layout.dialog_get_pocket , null);
        builder.setView(view);
        CircleImageView kaiIcon = view.findViewById(R.id.dian_kai_icon_civ);
        ImageView kai = view.findViewById(R.id.dian_kai_kai_iv);
        TextView kaiTitle = view.findViewById(R.id.dian_kai_title_tv);
        TextView kaiContent = view.findViewById(R.id.dian_kai_content_tv);
        kaiTitle.setText(goods.getMemName());
        kaiContent.setText(goods.getLinkTitle());
        if (!TextUtils.isEmpty(goods.getHeadImage())){
            Picasso.with(context).load(goods.getHeadImage()).error(R.mipmap.icon_default).into(kaiIcon);
        }else {
            kaiIcon.setImageResource(R.mipmap.icon_default);
        }

        kai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                goodId = goods.getId();
                Map<String ,String> params = new HashMap<>();
                params.put("url" ,Constants.OPEN_RED_ENVENLOPES);
                params.put("id" , goodId+"");
                newAsyncTaskExecute(Constants.HTTP_ACTION_2 , params);
            }
        });
        dialog = builder.create();
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        dialog.show();
        WindowManager.LayoutParams params = dialog.getWindow().getAttributes();
        params.height = 1500;
        dialog.getWindow().setAttributes(params);

    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_home;
    }

    @Override
    protected String iniTitle() {
        return "点点";
    }

    @Override
    protected void initViewWithBack(boolean setBack, Bundle savedInstanceState) {
        super.initViewWithBack(setBack, savedInstanceState);
        initview(savedInstanceState);
        requestPermission();
    }
    /**
     * 地图初始化
     */
    private void initview(Bundle savedInstanceState) {
        mapView.onCreate(savedInstanceState);// 此方法必须重写
        aMap = mapView.getMap();
        //隐藏logo
        UiSettings uiSettings =  aMap.getUiSettings();
        uiSettings.setLogoBottomMargin(-100);
        uiSettings.setZoomControlsEnabled(false);
    }

    @Override
    public void onStart() {
        super.onStart();
        if (latitude != 0.0  && longtude != 0.0){
            //获取红包数据
            Map<String ,String > params = new HashMap<>();
            params.put("lon" , longtude+"");
            params.put("lat" , latitude+"");
            params.put("url" , Constants.NEAR_RED_ENVENLOPES);
            newAsyncTaskExecute(Constants.HTTP_ACTION_1 , params);
        }
    }

    /**
     * 开启定位
     */
    private void startLocation() {
        //初始化定位
        mLocationClient = new AMapLocationClient(getActivity());
        //设置定位回调监听
        mLocationClient.setLocationListener(mLocationListener);
        //初始化AMapLocationClientOption对象
        mLocationOption = new AMapLocationClientOption();
        mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
        mLocationOption.setOnceLocation(true);
        mLocationOption.setOnceLocationLatest(false);
        //设置是否返回地址信息（默认返回地址信息）
        mLocationOption.setNeedAddress(true);
        //单位是毫秒，默认30000毫秒，建议超时时间不要低于8000毫秒。
        mLocationOption.setHttpTimeOut(20000);
        //给定位客户端对象设置定位参数
        mLocationClient.setLocationOption(mLocationOption);
        //启动定位
        mLocationClient.startLocation();
    }
    /**
     * 设置自定义定位蓝点
     *
     */
    private void setupLocationStyle(){
        MyLocationStyle myLocationStyle;
        //初始化定位蓝点样式类
        myLocationStyle = new MyLocationStyle();
        //连续定位、且将视角移动到地图中心点，定位点依照设备方向旋转，并且会跟随设备移动。（1秒1次定位）如果不设置myLocationType，默认也会执行此种模式。
//        myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_LOCATION_ROTATE);
        //设置连续定位模式下的定位间隔，只在连续定位模式下生效，单次定位模式下不会生效。单位为毫秒。
        myLocationStyle.interval(2000);
        //设置定位蓝点的Style
        aMap.setMyLocationStyle(myLocationStyle);
        //设置默认定位按钮是否显示，非必需设置。
        aMap.getUiSettings().setMyLocationButtonEnabled(true);
        // 设置为true表示启动显示定位蓝点，false表示隐藏定位蓝点并不进行定位，默认是false。
        aMap.setMyLocationEnabled(true);
    }

    //绘制面
    public void circle(double v1, double v2) {
        LatLng latLng = new LatLng(v1, v2);
        aMap.addCircle(new CircleOptions().
                center(latLng).
                radius(1000).
                strokeColor(getResources().getColor(R.color.colorRed)).
                fillColor(getResources().getColor(R.color.colorAccentLight)).
                strokeWidth(5));


    }
    /**
     * 运行时权限检查
     */
    private void requestPermission() {
        Acp.getInstance(getActivity()).request(new AcpOptions.Builder()
                        .setPermissions(Manifest.permission.ACCESS_COARSE_LOCATION,
                                Manifest.permission.ACCESS_FINE_LOCATION
                        )
                        .build(),
                new AcpListener() {
                    @Override
                    public void onGranted() {
                        startLocation();
                    }

                    @Override
                    public void onDenied(List<String> permissions) {
                        Toast.makeText(getActivity() , "权限不够，应用可能会出现问" ,Toast.LENGTH_LONG).show();
                    }
                });
    }
    @OnClick({R.id.frag_dian_invite_iv , R.id.frag_dian_share_iv, R.id.fmg_dian_help_btn})
    void click(View view){
        switch (view.getId()){
            case R.id.frag_dian_invite_iv:
                startActivity(new Intent(context , InviteAty.class));
                break;
            case R.id.frag_dian_share_iv:
                ToastUtils.toastShort(getContext() , "暂未开放");

                break;
            case R.id.fmg_dian_help_btn:
                Intent intent = new Intent(context , RulesAty.class);
                intent.putExtra("url" , Constants.URL_RULES);
                startActivity(intent);
            break;
        }
    }

    @Override
    protected void doInBackgroundTask(int asyncid, Map params, Callback callback) {
        AppDbCtrl.getServer(asyncid , params ,callback ,getContext());
    }
    @Override
    protected void onPostExecuteTask(int asyncid, ResultInfo resultInfo) {
        super.onPostExecuteTask(asyncid, resultInfo);
        switch (asyncid){
            case Constants.HTTP_ACTION_1:
                datas = FastJsonUtils.toList(resultInfo.getData().toString() ,Goods.class);
                List<Marker> markers = aMap.getMapScreenMarkers();
                //移除之前的marker标记
                if (markers!= null && markers.size() >0){
                    for (Marker markerInfo:markers) {
                        markerInfo.setVisible(false);
                    }
                }

                //重新添加marker信息
                if (datas != null && datas.size() > 0){
                    //添加maker
                    MarkerOptions markerOptions = new MarkerOptions();
                        for (int i = 0 ;i <datas.size() ; i++){
                        //获取随机坐标
                        GpsLocation location = LocationUtils.GetRandomLocation(latitude , longtude , 1000);
                        markerOptions.position(new LatLng(location.getLat() , location.getLon()));
                        markerOptions.visible(true);
                        BitmapDescriptor bitmapDescriptor = null;
                        switch (datas.get(i).getType()){
                            case 1://现金红包
                                bitmapDescriptor = BitmapDescriptorFactory.fromBitmap(BitmapFactory.decodeResource(getResources(), R.mipmap.dian_hongbao));
                                break;
                            case 2://积分红包
                                bitmapDescriptor = BitmapDescriptorFactory.fromBitmap(BitmapFactory.decodeResource(getResources(), R.mipmap.red_jifen));
                                break;
                            case 3: //佣金红包
                                bitmapDescriptor = BitmapDescriptorFactory.fromBitmap(BitmapFactory.decodeResource(getResources(), R.mipmap.red_yj));
                                break;
                        }
                        markerOptions.icon(bitmapDescriptor);
                        Marker marker = aMap.addMarker(markerOptions);
                        marker.setObject(datas.get(i));

                    }
                    aMap.setOnMarkerClickListener(this);
                }
                break;
            case Constants.HTTP_ACTION_2:
                RedPacket redpacketId = FastJsonUtils.toBean(resultInfo.getData().toString() , RedPacket.class);
                Goods goods = (Goods) marker.getObject();
                if (goods.getId() == goodId){
                    marker.setVisible(false);
                }
                //页面跳转
                Intent intent = new Intent(context , PocketDetailsAty.class);
                intent.putExtra("goodId" , redpacketId.getId());
                startActivity(intent);
                break;
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.dialog_become_lord_confirm_btn:
                break;
            case R.id.dialog_become_lord_cancel_btn:
                lordDialog.dismiss();
                break;
        }
    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        this.marker = marker;
        //弹出领红包的对话框
        showDialog((Goods) marker.getObject());
        return false;
    }
}
