package com.ylbl.cashpocket.ui.news;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.widget.EditText;

import com.ylbl.cashpocket.R;
import com.ylbl.cashpocket.adapter.CommentAdapter;
import com.ylbl.cashpocket.base.BaseSwipeRefreshAty;
import com.ylbl.cashpocket.base.BaseToolBarAty;
import com.ylbl.cashpocket.bean.CommentInfo;
import com.ylbl.cashpocket.bean.ResultInfo;
import com.ylbl.cashpocket.net.AppDbCtrl;
import com.ylbl.cashpocket.net.Constants;
import com.ylbl.cashpocket.utils.DateUtils;
import com.ylbl.cashpocket.utils.FastJsonUtils;
import com.ylbl.cashpocket.utils.ToastUtils;
import com.zhy.http.okhttp.callback.Callback;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

public class HelpCommentAty extends BaseSwipeRefreshAty implements SwipeRefreshLayout.OnRefreshListener {
    @BindView(R.id.aty_comment_et)
    EditText content;
    @Override
    protected int getLayoutId() {
        return R.layout.aty_comment_help;
    }

    @Override
    protected String iniTitle() {
        return "客户留言";
    }

    @Override
    protected void setMonitor() {
        super.setMonitor();
        goodList = new ArrayList();
        adapter = new CommentAdapter(context ,goodList);
        easyRecyclerView.setAdapter(adapter);
    }

    @Override
    protected void initViewWithBack(boolean setBack) {
        super.initViewWithBack(true);
        onRefresh();
    }
    private void operate(){
        Map<String ,String> params = new HashMap<>();
        params.put("url" , Constants.COMMTNT_LIST);
        params.put("pageNum" , page+"");
        params.put("pageSize" , pageSize);
        newAsyncTaskExecute(Constants.HTTP_ACTION_1 , params);
    }

    @Override
    protected void doInBackgroundTask(int asyncid, Map params, Callback callback) {
        switch (asyncid){
            case Constants.HTTP_ACTION_1:
                AppDbCtrl.getServer(asyncid , params ,callback ,context);
                break;
            case Constants.HTTP_ACTION_2:
                AppDbCtrl.doServer(asyncid , params ,callback ,context);
                break;
        }
    }

    @Override
    protected void onPostExecuteTask(int asyncid, ResultInfo resultInfo) {
        super.onPostExecuteTask(asyncid, resultInfo);
        switch (asyncid){
            case Constants.HTTP_ACTION_1:
                goodList = FastJsonUtils.toList(resultInfo.getData().toString() , CommentInfo.class);
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
            case Constants.HTTP_ACTION_2:
                ToastUtils.toastShort(context , "留言成功");
                onRefresh();
                break;
        }
    }

    @Override
    public void onRefresh() {
            page =1;
            operate();
    }

    @Override
    public void onLoadMore() {
        page++;
        operate();
    }

    @OnClick(R.id.aty_comment_confirm_btn)
    void onClick(){
        String con = content.getText().toString();
        if (TextUtils.isEmpty(con)){
            ToastUtils.toastShort(context , "输入内容不能为空");
        }else {
            Map<String ,String> params = new HashMap<>();
            params.put("url" , Constants.ADD_COMMENT);
            params.put("content" , con);
            newAsyncTaskExecute(Constants.HTTP_ACTION_2 , params);
            content.setText("");
        }
    }
}
