package com.ylbl.cashpocket.fmg;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.ylbl.cashpocket.R;
import com.ylbl.cashpocket.adapter.ReceivedAdapter;
import com.ylbl.cashpocket.base.BaseHttpFmg;
import com.ylbl.cashpocket.bean.PocketRecordInfo;
import com.ylbl.cashpocket.bean.RecordsInfo;
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
 * 已抢到
 */
public class ReceivedFmg extends BaseHttpFmg implements  SwipeRefreshLayout.OnRefreshListener  {
    @BindView(R.id.fragment_receive_money_tv)
    TextView receiveAllMoney;
    @BindView(R.id.fragment_receive_rv)
    RecyclerView recyclerView;
    private ReceivedAdapter adapter;
    private List<PocketRecordInfo> datas;

    private int page = 1;
    private boolean hasMore = true;
    @Override
    protected int getLayoutId() {
        return R.layout.fragment_received;
    }

    @Override
    protected void initViewWithBack(boolean setBack) {
        super.initViewWithBack(setBack);
        datas = new ArrayList<>();
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerView.addItemDecoration(new DividerItemDecoration(context, DividerItemDecoration.VERTICAL));
        adapter = new ReceivedAdapter(context);
        recyclerView.setAdapter(adapter);
        adapter.setData(datas);
        getRecords();
    }

    @Override
    public void onRefresh() {
        if (hasMore){
            page++;
            getRecords();
        }
    }

    private void getRecords() {
        Map<String ,String> params = new HashMap<>();
        params.put("url" , Constants.MY_REDENVELOPES);
        params.put("type" , "1");
        params.put("pageNum" ,page+"");
        params.put("pageSize","60");
        newAsyncTaskExecute(Constants.HTTP_ACTION_1 , params);
    }

    @Override
    protected void doInBackgroundTask(int asyncid, Map params, Callback callback) {
        super.doInBackgroundTask(asyncid, params, callback);
        AppDbCtrl.getServer(asyncid , params ,callback , getContext());
    }

    @Override
    protected void onPostExecuteTask(int asyncid, ResultInfo resultInfo) {
        super.onPostExecuteTask(asyncid, resultInfo);
        switch (asyncid){
            case Constants.HTTP_ACTION_1:
                receiveAllMoney.setText(resultInfo.getTotalMoney()+"");
                datas = FastJsonUtils.toList(resultInfo.getData().toString() , PocketRecordInfo.class);
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
//                adapter.setData(datas);
                break;
        }
    }
}
