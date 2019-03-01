package com.ylbl.cashpocket.ui.nouse;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.ylbl.cashpocket.R;
import com.ylbl.cashpocket.adapter.TeamLevelAdapter;
import com.ylbl.cashpocket.api.OnItemClickListener;
import com.ylbl.cashpocket.base.BaseToolBarAty;
import com.ylbl.cashpocket.bean.ResultInfo;
import com.ylbl.cashpocket.bean.TeamInfo;
import com.ylbl.cashpocket.bean.TeamLevel;
import com.ylbl.cashpocket.net.AppDbCtrl;
import com.ylbl.cashpocket.net.Constants;
import com.ylbl.cashpocket.utils.FastJsonUtils;
import com.ylbl.cashpocket.utils.StatusBarUtil;
import com.zhy.http.okhttp.callback.Callback;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;

public class TeamAty extends BaseToolBarAty {
//    @BindView(R.id.aty_team_invite_man_tv)
//    TextView inviteMan;
//    @BindView(R.id.aty_team_num_tv)
//    TextView teamNum;
//    @BindView(R.id.aty_team_income_tv)
//    TextView teamMoney;
//    @BindView(R.id.aty_team_level_rv)
//    RecyclerView teamLevel;
//    private TeamLevelAdapter adapter;
//    private TeamInfo teamInfo;
    @Override
    protected int getLayoutId() {
        return R.layout.aty_team;
    }

    @Override
    protected String iniTitle() {
        return "我的团队";
    }

    @Override
    protected void initViewWithBack(boolean setBack) {
        super.initViewWithBack(true);
//        teamLevel.setLayoutManager(new LinearLayoutManager(this));
//        adapter  = new TeamLevelAdapter(this ,this);
//        teamLevel.setAdapter(adapter);
//        Map<String ,String> params = new HashMap<>();
//        params.put("url" , Constants.MY_TEAM_INFO);
//        newAsyncTaskExecute(Constants.HTTP_ACTION_1 , params);
    }

//    @Override
//    protected void doInBackgroundTask(int asyncid, Map params, Callback callback) {
//        super.doInBackgroundTask(asyncid, params, callback);
//        AppDbCtrl.getServer(asyncid , params ,callback ,context);
//    }
//
//    @Override
//    protected void onPostExecuteTask(int asyncid, ResultInfo resultInfo) {
//        super.onPostExecuteTask(asyncid, resultInfo);
//        switch (asyncid){
//            case Constants.HTTP_ACTION_1:
//                teamInfo = FastJsonUtils.toBean(resultInfo.getData().toString() , TeamInfo.class);
//                if (teamInfo != null){
//                    inviteMan.setText("我的推荐人"+teamInfo.getParentName()+"("+teamInfo.getParentMobile()+")");
//                    int tempNum = 0;
//                    double tempMoney = 0;
//                    teamNum.setText(tempNum+ "");
//                    teamMoney.setText(tempMoney +"");
//                }
//
//                break;
//        }
//    }
//
//    @Override
//    protected void onStart() {
//        super.onStart();
//        StatusBarUtil.setWindowStatusBarColor(this,R.color.colorRed);
//    }

//    @OnClick({R.id.aty_team_invite_1,R.id.aty_team_invite_2,R.id.aty_team_invite_3})
//    void onClick(View view){
//        switch (view.getId()){
//            case R.id.aty_team_invite_1:
//                Intent intent = new Intent(this , TeamSecondAty.class);
////                intent.putExtra()
//                startActivity(intent);
//                break;
//            case R.id.aty_team_invite_2:
//                break;
//            case R.id.aty_team_invite_3:
//                break;
//        }
//    }

//    @Override
//    public void onItemClickListener(int i) {
//        TeamLevel teamLevel = teamInfo.getTeam().get(i);
//        Intent intent = new Intent(this , TeamSecondAty.class);
//        intent.putExtra("teamlevel" , teamLevel);
//        startActivity(intent);
//    }
}
