package com.ylbl.cashpocket.ui.center;

import android.annotation.SuppressLint;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.ylbl.cashpocket.R;
import com.ylbl.cashpocket.adapter.PocketRecordAdapter;
import com.ylbl.cashpocket.base.BaseSwipeRefreshAty;
import com.ylbl.cashpocket.base.BaseToolBarAty;
import com.ylbl.cashpocket.bean.PocketRecordInfo;
import com.ylbl.cashpocket.bean.ResultInfo;
import com.ylbl.cashpocket.net.AppDbCtrl;
import com.ylbl.cashpocket.net.Constants;
import com.ylbl.cashpocket.utils.FastJsonUtils;
import com.ylbl.cashpocket.utils.StringUtils;
import com.zhy.http.okhttp.callback.Callback;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 红包记录
 */
public class PocketRecordAty extends BaseSwipeRefreshAty {
    @BindView(R.id.aty_deposit_record_all_money_tv)
    TextView allMoney;
    @BindView(R.id.aty_pocket_record_pool_money_tv)
    TextView poolMoney;

    @Override
    protected int getLayoutId() {
        return R.layout.aty_pocket_record;
    }

    @Override
    protected String iniTitle() {
        return "红包记录";
    }

    @Override
    protected void setMonitor() {
        super.setMonitor();
        goodList = new ArrayList();
        adapter = new PocketRecordAdapter(context , goodList);
        easyRecyclerView.setAdapter(adapter);
    }

    @Override
    protected void initViewWithBack(boolean setBack) {
        super.initViewWithBack(true);
       getRecords();

    }
    @Override
    public void onRefresh() {
        page=1;
        getRecords();
    }

    @Override
    public void onLoadMore() {
        page++;
        getRecords();
    }

    private void getRecords() {
        Map<String ,String> params = new HashMap<>();
        params.put("url" , Constants.MY_REDENVELOPES);
        params.put("logType" , "2");
        params.put("pageNum" ,page+"");
        params.put("pageSize",pageSize);
        newAsyncTaskExecute(Constants.HTTP_ACTION_1 , params);
    }

    @Override
    protected void doInBackgroundTask(int asyncid, Map params, Callback callback) {
        AppDbCtrl.getServer(asyncid , params ,callback,context);
    }

    @SuppressLint("SetTextI18n")
    @Override
    protected void onPostExecuteTask(int asyncid, ResultInfo resultInfo) {
        super.onPostExecuteTask(asyncid, resultInfo);
        switch (asyncid){
            case Constants.HTTP_ACTION_1:
                allMoney.setText("￥"+ StringUtils.doubleToString(Double.parseDouble(resultInfo.getTotalMoney())));
                poolMoney.setText("￥"+ StringUtils.doubleToString(Double.parseDouble(resultInfo.getPoolMoney())));
                goodList = FastJsonUtils.toList(resultInfo.getData().toString() , PocketRecordInfo.class);
                if (goodList == null || goodList.size() <= 0) {
                    goodList = new ArrayList<>();
                    if (page == 1) {
                        adapter.clear();
                        adapter.addAll(goodList);
                    } else {
                        page--;
                        adapter.stopMore();
                    }
                } else {
                    if (page == 1) {
                        adapter.clear();
                        adapter.addAll(goodList);
                    } else {
                        adapter.addAll(goodList);
                    }
                }
                break;
        }
    }
}
