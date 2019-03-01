package com.ylbl.cashpocket.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;

import com.ylbl.cashpocket.bean.ConfigInfo;
import com.ylbl.cashpocket.bean.MemberInfo;
import com.ylbl.cashpocket.bean.User;

public class SpUtils {
    /**
     * 保存登陆状态
     * @param context
     * @param user
     */
    public static void saveLogin(Context context , User user){
        SharedPreferences sharedPreferences = context.getSharedPreferences("isLogin",Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("name" , user.getName());
        editor.putString("pwd" , user.getPwd());
        editor.putString("token" , user.getToken());
        editor.putString("phone" , user.getPhone());
        editor.commit();
    }
    /**
     * 保存个人信息
     * @param context
     */
    public static void saveUserInfo(Context context , String name , String phone){
        SharedPreferences sharedPreferences = context.getSharedPreferences("isLogin",Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("name" ,name);
        editor.putString("phone" ,phone);
        editor.commit();
    }

    /**
     *  判断是否登陆
     * @param context
     * @return
     */
    public static boolean checkLogin(Context context ){
        SharedPreferences sharedPreferences = context.getSharedPreferences("isLogin",Context.MODE_PRIVATE);
        String token = sharedPreferences.getString("token" , null);
        return TextUtils.isEmpty(token);
    }

    /**
     *  退出登录
     * @param context
     */
    public static void quitLogin(Context context ){
        SharedPreferences sharedPreferences = context.getSharedPreferences("isLogin",Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("name" , null);
        editor.putString("pwd" ,null);
        editor.putString("token" , null);
        editor.commit();
    }

    public static String getToken(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("isLogin",Context.MODE_PRIVATE);
        String token = sharedPreferences.getString("token" , null);
        return token;
    }
    public static String getPhone(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("isLogin",Context.MODE_PRIVATE);
        String phone = sharedPreferences.getString("phone" , null);
        return phone;
    }
    public static String getName(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("isLogin",Context.MODE_PRIVATE);
        String name = sharedPreferences.getString("name" , null);
        return name;
    }

    /**
     * 缓存个人信息
     * @param memberInfo
     */
    public static void saveMemberInfo(Context context , MemberInfo memberInfo) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("menberInfo",Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putLong("id" ,memberInfo.getId());
        editor.putString("mobilePhone" ,memberInfo.getMobilePhone());
        editor.putString("headImg" ,memberInfo.getHeadImg());
        editor.putString("gender" ,memberInfo.getGender());
        editor.putString("birthday" ,memberInfo.getBirthday());
        editor.putString("industry" ,memberInfo.getIndustry());
        editor.putString("province" ,memberInfo.getProvince());
        editor.putString("city" ,memberInfo.getCity());
        editor.putString("registerTime" ,memberInfo.getRegisterTime());
        editor.putString("realName" ,memberInfo.getRealName());
        editor.putString("cardNo" ,memberInfo.getCardNo());
        editor.putString("name" ,memberInfo.getName());
        editor.putString("alipayCode" ,memberInfo.getAlipayCode());
        editor.putInt("state" ,memberInfo.getState());
        editor.putString("cashNum" ,memberInfo.getCashNum());
        editor.putString("profit" ,memberInfo.getProfit());
        editor.putInt("levelCode" ,memberInfo.getLevelCode());
        editor.putString("hasPayPassword" ,memberInfo.getHasPayPassword());
        editor.putString("rpkPoolCount" ,memberInfo.getRpkPoolCount());
        editor.commit();
    }

    /**
     *  获取个人信息
     * @param context
     * @return
     */
    public static MemberInfo getMemberInfo(Context context){
        MemberInfo memberInfo = new MemberInfo();
        SharedPreferences sharedPreferences = context.getSharedPreferences("menberInfo",Context.MODE_PRIVATE);
        if (sharedPreferences != null) {
            memberInfo.setId(sharedPreferences.getLong("id", -1));
            memberInfo.setMobilePhone(sharedPreferences.getString("mobilePhone", ""));
            memberInfo.setHeadImg(sharedPreferences.getString("headImg", ""));
            memberInfo.setGender(sharedPreferences.getString("gender", ""));
            memberInfo.setBirthday(sharedPreferences.getString("birthday", ""));
            memberInfo.setIndustry(sharedPreferences.getString("industry", ""));
            memberInfo.setProvince(sharedPreferences.getString("province", ""));
            memberInfo.setCity(sharedPreferences.getString("city", ""));
            memberInfo.setRegisterTime(sharedPreferences.getString("registerTime", ""));
            memberInfo.setRealName(sharedPreferences.getString("realName", ""));
            memberInfo.setCardNo(sharedPreferences.getString("cardNo", ""));
            memberInfo.setName(sharedPreferences.getString("name", ""));
            memberInfo.setAlipayCode(sharedPreferences.getString("alipayCode", ""));
            memberInfo.setState(sharedPreferences.getInt("state", -1));
            memberInfo.setCashNum(sharedPreferences.getString("cashNum", ""));
            memberInfo.setProfit(sharedPreferences.getString("profit", ""));
            memberInfo.setLevelCode(sharedPreferences.getInt("levelCode", -1));
            memberInfo.setHasPayPassword(sharedPreferences.getString("hasPayPassword", ""));
            memberInfo.setRpkPoolCount(sharedPreferences.getString("rpkPoolCount", ""));
        }
        return memberInfo;
    }
    /**
     * 缓存系统信息
     * @param configInfo
     */
    public static void saveConfigInfo(Context context ,ConfigInfo configInfo) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("configInfo",Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("androidVersion" , configInfo.getAndroidVersion());
        editor.putString("androidRemark" , configInfo.getAndroidRemark());
        editor.putString("androidLink" , configInfo.getAndroidLink());
        editor.putString("withdrawalType" , configInfo.getWithdrawalType());
        editor.putString("withdrawalRate" , configInfo.getWithdrawalRate());
        editor.putString("withdrawalSwitch" , configInfo.getWithdrawalSwitch());
        editor.commit();
    }
    public static ConfigInfo getConfigInfo(Context context){
        ConfigInfo configInfo = new ConfigInfo();
        SharedPreferences sharedPreferences = context.getSharedPreferences("configInfo",Context.MODE_PRIVATE);
        if (sharedPreferences != null ){
            configInfo.setAndroidVersion(sharedPreferences.getString("androidVersion" , ""));
            configInfo.setAndroidRemark(sharedPreferences.getString("androidRemark" , ""));
            configInfo.setAndroidLink(sharedPreferences.getString("androidLink" , ""));
            configInfo.setWithdrawalType(sharedPreferences.getString("withdrawalType" , ""));
            configInfo.setWithdrawalRate(sharedPreferences.getString("withdrawalRate" , ""));
            configInfo.setWithdrawalSwitch(sharedPreferences.getString("withdrawalSwitch" , ""));
        }
        return configInfo;
    }
}
