package com.ylbl.cashpocket.fmg;

import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;

import com.ylbl.cashpocket.R;
import com.ylbl.cashpocket.adapter.NewsAdapter;
import com.ylbl.cashpocket.api.OnItemClickListener;
import com.ylbl.cashpocket.base.BaseRecyclerArrayAdapter;
import com.ylbl.cashpocket.base.BaseSwipeRefreshAty;
import com.ylbl.cashpocket.base.BaseSwipeRefreshFmg;
import com.ylbl.cashpocket.base.BaseToolbarFmg;
import com.ylbl.cashpocket.bean.NewsInfo;
import com.ylbl.cashpocket.bean.ResultInfo;
import com.ylbl.cashpocket.net.AppDbCtrl;
import com.ylbl.cashpocket.net.Constants;
import com.ylbl.cashpocket.ui.news.NewsDetailsAty;
import com.ylbl.cashpocket.utils.FastJsonUtils;
import com.ylbl.cashpocket.utils.ToastUtils;
import com.zhy.http.okhttp.callback.Callback;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;

public class NewsFmg extends BaseSwipeRefreshFmg implements BaseRecyclerArrayAdapter.OnClickListener {

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_news;
    }

    @Override
    protected String iniTitle() {
        return "消息";
    }

    @Override
    protected void setMonitor() {
        super.setMonitor();
        goodList = new ArrayList<>();
        adapter = new NewsAdapter(context , goodList ,this);
        easyRecyclerView.setAdapter(adapter);
    }

    @Override
    protected void initViewWithBack(boolean setBack) {
        super.initViewWithBack(setBack);
        getNews();
    }

    /**
     * 获取消息
     */
    private void getNews(){
        Map<String ,String> params = new HashMap<>();
        params.put("url" , Constants.NEWS);
        params.put("pageNum" ,page+"");
        params.put("pageSize",10+"");
        //网络请求获取数据
        newAsyncTaskExecute(Constants.HTTP_ACTION_1 , params);
    }
    @Override
    protected void doInBackgroundTask(int asyncid, Map params, Callback callback) {
        AppDbCtrl.getServer(asyncid , params ,callback ,context);
    }

    @Override
    protected void onPostExecuteTask(int asyncid, ResultInfo resultInfo) {
        super.onPostExecuteTask(asyncid, resultInfo);
        goodList = FastJsonUtils.toList(resultInfo.getData().toString() , NewsInfo.class);
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
        getNews();
    }

    @Override
    public void onLoadMore() {
        page++;
        getNews();
    }

    @Override
    public void onRecyclerArrayClick(View view, Object data, int position) {
        NewsInfo info = (NewsInfo) data;
        if (TextUtils.isEmpty(info.getLink())){
            ToastUtils.toastShort(context , "该消息没有详情信息");
        }else {
            Intent intent = new Intent(context , NewsDetailsAty.class);
            intent.putExtra("link" ,info.getLink());
            startActivity(intent);
        }

    }
}

