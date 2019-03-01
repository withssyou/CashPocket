package com.ylbl.cashpocket.ui.center;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.ylbl.cashpocket.R;
import com.ylbl.cashpocket.adapter.TeamNumberAdapter;
import com.ylbl.cashpocket.base.BaseToolBarAty;
import com.ylbl.cashpocket.bean.ResultInfo;
import com.ylbl.cashpocket.bean.TeamInfo;
import com.ylbl.cashpocket.bean.Teamer;
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

/**
 * 团队详情
 */
public class TeamSecondAty extends BaseToolBarAty {
    @BindView(R.id.aty_team_invite_man_tv)
    TextView inviteMan;
    @BindView(R.id.aty_team_num_tv)
    TextView teamNum;
    @BindView(R.id.aty_team_income_tv)
    TextView teamMoney;
    @BindView(R.id.aty_team_second_my_num_tv)
    TextView inviteNum;
    @BindView(R.id.aty_team_s_people_rv)
    RecyclerView rv;


    private TeamInfo teamInfo;
    private TeamNumberAdapter adapter;
    private List<Teamer> datas;
    @Override
    protected int getLayoutId() {
        return R.layout.aty_team_second;
    }

    @Override
    protected String iniTitle() {
        return "我的团队";
    }

    @Override
    protected void initViewWithBack(boolean setBack) {
        super.initViewWithBack(true);
        datas = new ArrayList<>();
        rv.setLayoutManager(new LinearLayoutManager(this));
        adapter = new TeamNumberAdapter(this , datas);
        rv.setAdapter(adapter);
        operate();
    }

    /**
     * 获取数据
     */
    private void operate(){
        Map<String ,String> params = new HashMap<>();
        params.put("url" , Constants.MY_TEAM_INFO);
        newAsyncTaskExecute(Constants.HTTP_ACTION_1 , params);
    }
    @Override
    protected void doInBackgroundTask(int asyncid, Map params, Callback callback) {
//        super.doInBackgroundTask(asyncid, params, callback);
        AppDbCtrl.getServer(asyncid , params ,callback , context);
    }

    @Override
    protected void onPostExecuteTask(int asyncid, ResultInfo resultInfo) {
        super.onPostExecuteTask(asyncid, resultInfo);
        switch (asyncid){
            case Constants.HTTP_ACTION_1:
                teamInfo = FastJsonUtils.toBean(resultInfo.getData().toString() , TeamInfo.class);
                if (teamInfo != null){
                    teamNum .setText(teamInfo.getTeam().getTotalNum());
                    teamMoney .setText(teamInfo.getTeam().getTotalMoney());
                    inviteMan.setText("我的推荐人："+teamInfo.getParentName()+"("+teamInfo.getParentMobile()+")");
                    inviteNum.setText("直接推荐人人数"+"("+teamInfo.getChildren().size()+")");
                    adapter.addAll(teamInfo.getChildren());
                }
                break;
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        StatusBarUtil.setWindowStatusBarColor(this , R.color.colorRed);
    }

}
