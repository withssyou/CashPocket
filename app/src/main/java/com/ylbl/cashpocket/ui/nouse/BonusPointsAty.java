package com.ylbl.cashpocket.ui.nouse;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.ylbl.cashpocket.R;
import com.ylbl.cashpocket.adapter.IntegralAdapter;
import com.ylbl.cashpocket.base.BaseHttpAty;
import com.ylbl.cashpocket.bean.IntegralInfo;
import com.ylbl.cashpocket.bean.ResultInfo;
import com.ylbl.cashpocket.net.AppDbCtrl;
import com.ylbl.cashpocket.net.Constants;
import com.ylbl.cashpocket.utils.FastJsonUtils;
import com.ylbl.cashpocket.utils.StatusBarUtil;
import com.zhy.http.okhttp.callback.Callback;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 积分
 */
public class BonusPointsAty extends BaseHttpAty implements SwipeRefreshLayout.OnRefreshListener {
    @BindView(R.id.aty_bonus_total_point_tv)
    TextView totalPoints;
    @BindView(R.id.aty_bonus_today_point_tv)
    TextView todayPoints;
    @BindView(R.id.aty_bonus_points_srl)
    SwipeRefreshLayout refreshLayout;
    @BindView(R.id.aty_bonus_points_rv)
    RecyclerView recyclerView;

    private IntegralAdapter adapter ;
    private List<IntegralInfo> datas ;
    private int page = 1;
    private boolean hasMore = true;
    @Override
    protected int getLayoutId() {
        return R.layout.aty_bonus_point;
    }

    @Override
    protected void initViewWithBack(boolean setBack) {
        super.initViewWithBack(setBack);
        refreshLayout.setOnRefreshListener(this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new IntegralAdapter(this);
        recyclerView.setAdapter(adapter);
        operate();
    }

    @Override
    protected void doInBackgroundTask(int asyncid, Map params, Callback callback) {
        super.doInBackgroundTask(asyncid, params, callback);
        AppDbCtrl.getServer(asyncid, params ,callback ,context);
    }

    @Override
    protected void onPostExecuteTask(int asyncid, ResultInfo resultInfo) {
        super.onPostExecuteTask(asyncid, resultInfo);
            totalPoints.setText(resultInfo.getTotal()+"");
            datas = FastJsonUtils.toList(resultInfo.getData().toString() , IntegralInfo.class);
            if (datas == null || datas.size() <= 0) {
                datas = new ArrayList<>();
                if (page == 1) {
                    adapter.clear();
                    adapter.addAll(datas);
                } else {
                    page--;
                    hasMore = false;
                }
            } else {
                if (page == 1) {
                    adapter.clear();
                    adapter.addAll(datas);
                } else {
                    adapter.addAll(datas);
                }
            }
    }

    @OnClick({R.id.aty_points_back_iv,R.id.aty_points_help_iv})
    void onClick(View view){
        switch (view.getId()){
            case R.id.aty_points_back_iv:
                finish();
                break;
            case R.id.aty_points_help_iv:
                break;
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        StatusBarUtil.setWindowStatusBarColor(this , R.color.colorBarYello_j);
    }

    @Override
    public void onRefresh() {
        if (hasMore){
            page++;
            operate();
        }
    }

    private void operate() {
        Map<String ,String > params = new HashMap<>();
        params.put("url" , Constants.JIFEN_LOG);
        params.put("pageNum" , page+"");
        params.put("pageSize" , "60");
//        params.put("logType" , "1");
        newAsyncTaskExecute(Constants.HTTP_ACTION_1 , params);
    }
}
