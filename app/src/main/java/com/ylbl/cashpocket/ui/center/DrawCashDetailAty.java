package com.ylbl.cashpocket.ui.center;


import com.ylbl.cashpocket.R;
import com.ylbl.cashpocket.adapter.DepositRecordAdapter;
import com.ylbl.cashpocket.base.BaseSwipeRefreshAty;
import com.ylbl.cashpocket.bean.DepositRecordInfo;
import com.ylbl.cashpocket.bean.ResultInfo;
import com.ylbl.cashpocket.net.AppDbCtrl;
import com.ylbl.cashpocket.net.Constants;
import com.ylbl.cashpocket.utils.FastJsonUtils;
import com.zhy.http.okhttp.callback.Callback;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 *  钱包明细
 */
public class DrawCashDetailAty extends BaseSwipeRefreshAty {


    @Override
    protected int getLayoutId() {
        return R.layout.aty_deposit_record;
    }

    @Override
    protected String iniTitle() {
        return "钱包明细";
    }

    @Override
    protected void setMonitor() {
        super.setMonitor();
        goodList = new ArrayList();
        adapter = new DepositRecordAdapter(this , goodList);
        easyRecyclerView.setAdapter(adapter);
    }

    @Override
    protected void initViewWithBack(boolean setBack) {
        super.initViewWithBack(true);
        opreate();
    }

    private void opreate(){
        Map<String ,String> params = new HashMap<>();
        params.put("url" , Constants.CASH_LOG);
        params.put("pageNum" , page+"");
        params.put("pageSize" , "10");
        newAsyncTaskExecute(Constants.HTTP_ACTION_1 , params);
    }

    @Override
    protected void doInBackgroundTask(int asyncid, Map params, Callback callback) {
        AppDbCtrl.getServer(asyncid , params ,callback ,this);
    }

    @Override
    protected void onPostExecuteTask(int asyncid, ResultInfo resultInfo) {
        super.onPostExecuteTask(asyncid, resultInfo);
        goodList = FastJsonUtils.toList(resultInfo.getData().toString() , DepositRecordInfo.class);
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

    }


    @Override
    public void onRefresh() {
        page = 1;
        opreate();
    }

    @Override
    public void onLoadMore() {
        page++;
        opreate();

    }
}
