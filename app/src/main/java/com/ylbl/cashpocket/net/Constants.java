package com.ylbl.cashpocket.net;

public class Constants {
    //网络请求
    public final static int HTTP_ACTION_1 = 101;
    public final static int HTTP_ACTION_2 = 102;
    public final static int HTTP_ACTION_3 = 103;
    public final static int HTTP_ACTION_4 = 104;
    public final static int HTTP_ACTION_5 = 105;
    public final static int HTTP_ACTION_6 = 106;

    //base url
//    public final static String BASE_URL = "http://yunlbl.3322.org:58080";
    public final static String BASE_URL = "http://rpk-api.zhuayixia.cn";


    //规则
    public final static String URL_RULES = "http://front-ddh.yunlbl.cn/redpacketruleintro"; //红包规则
    public final static String URL_SERVE = "http://front-ddh.yunlbl.cn/agreement";          //服务协议

    //--------------------------model url------------------------------
    //消息
    public final static String NEWS = "/api/noticeList";
    //用户
    public final static String USER_LOGIN = "/api/login";
    public final static String USER_REGISGER = "/api/register";
    public final static String USER_VERCODE = "/api/sendVercode";
    public final static String CHECK_PAY_PASSWORD = "/api/checkPayPassword";
    //红包
    public final static String NEAR_RED_ENVENLOPES = "/api/nearRedEnvelopes";//附近红包列表
    public final static String OPEN_RED_ENVENLOPES = "/api/openRedEnvelopes";//打开红包
    public final static String RED_ENVELOPS_DETAIL = "/api/redEnvelopesDetail";//红包记录
    public final static String OUT_RED_ENVELOPS = "/api/outRedEnvelopes"; //发红包
    public final static String ADVERTISER_LIST = "/api/advertiserList"; //广告主列表
    public final static String ADVERTISER_BUY = "/api/advertiserBuy"; //广告主购买订单

    //个人中心
    public final static String WITH_DRAW = "/api/withdraw"; //提现
    public final static String MY_TEAM_INFO = "/api/myteamInfo"; //我的团队
    public final static String MY_REDENVELOPES = "/api/myRedEnvelopes"; //红包记录
    public final static String PAY_BIND = "/api/bindingAlipayCode"; //支付绑定
    public final static String MY_ADVERTISER = "/api/myAdvertiser"; //广告主
    public final static String WITH_DRAW_LIST = "/api/withdrawList"; //提现记录
    public final static String JIFEN_LOG = "/api/integralLog"; //积分记录
    public final static String MEMBER_INFO = "/api/memberInfo"; //会员配置信息
    public final static String MODIFY_PWD = "/api/modifyPass"; //修改密码
    public final static String MODIFY_MEMINFO = "/api/modifyMeminfo"; //修改会员信息
    public final static String EXTENSION = "/api/extension"; //推广信息
    public final static String CASH_LOG = "/api/cashLog"; //钱包明细

    //留言
    public final static String COMMTNT_LIST = "/api/commentList"; //留言记录
    public final static String ADD_COMMENT = "/api/addComment"; //发表留言


}
