package com.ylbl.cashpocket.fmg;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;

import com.ylbl.cashpocket.R;
import com.ylbl.cashpocket.adapter.AdLordAdapter;
import com.ylbl.cashpocket.adapter.DrawCashAdapter;
import com.ylbl.cashpocket.adapter.DrawCashDoingAdapter;
import com.ylbl.cashpocket.base.BaseHttpFmg;
import com.ylbl.cashpocket.base.BaseSwipeRefreshFmg;
import com.ylbl.cashpocket.base.BaseToolbarFmg;
import com.ylbl.cashpocket.bean.AdLordInfo;
import com.ylbl.cashpocket.bean.DrawCashInfo;
import com.ylbl.cashpocket.bean.ResultInfo;
import com.ylbl.cashpocket.net.AppDbCtrl;
import com.ylbl.cashpocket.net.Constants;
import com.ylbl.cashpocket.utils.FastJsonUtils;
import com.zhy.http.okhttp.callback.Callback;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;

/**
 *
 */
public class DoingFmg extends BaseSwipeRefreshFmg implements SwipeRefreshLayout.OnRefreshListener {

    @Override
    protected int getLayoutId() {
        return R.layout.fmg_doing;
    }

    @Override
    protected void setMonitor() {
        super.setMonitor();
        goodList = new ArrayList<>();
        adapter = new DrawCashDoingAdapter(context ,goodList);
        easyRecyclerView.setAdapter(adapter);
    }

    @Override
    protected void initViewWithBack(boolean setBack) {
        super.initViewWithBack(setBack);
    }

    @Override
    public void onStart() {
        super.onStart();
        operate();
    }

    @Override
    public void onRefresh() {
        page = 1;
        operate();
    }

    @Override
    public void onLoadMore() {
        page++;
        operate();
    }

    private void operate() {
        Map<String ,String> params = new HashMap<>();
        params.put("url" , Constants.WITH_DRAW_LIST);
        params.put("type" , "1");
        params.put("pageNum" , page+"");
        params.put("pageSize" , pageSize);
        newAsyncTaskExecute(Constants.HTTP_ACTION_1 , params);
    }

    @Override
    protected void doInBackgroundTask(int asyncid, Map params, Callback callback) {
        AppDbCtrl.getServer(asyncid , params ,callback , getContext());
    }

    @Override
    protected void onPostExecuteTask(int asyncid, ResultInfo resultInfo) {
        super.onPostExecuteTask(asyncid, resultInfo);
        switch (asyncid){
            case Constants.HTTP_ACTION_1:
                goodList = FastJsonUtils.toList(resultInfo.getData().toString() , DrawCashInfo.class);
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
