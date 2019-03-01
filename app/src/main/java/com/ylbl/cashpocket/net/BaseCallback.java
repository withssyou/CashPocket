package com.ylbl.cashpocket.net;

import com.ylbl.cashpocket.bean.ResultInfo;
import com.ylbl.cashpocket.utils.FastJsonUtils;
import com.zhy.http.okhttp.callback.Callback;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import okhttp3.Call;
import okhttp3.Response;

public abstract class BaseCallback extends Callback<ResultInfo> {
    @Override
    public ResultInfo parseNetworkResponse(Response response, int id) throws Exception {

        ResultInfo resultInfo = FastJsonUtils.toBean(response.body().string(), ResultInfo.class);
//        if(resultInfo !=null){
//            resultInfo.setJsonData(sb.toString());
//        }
        return resultInfo;
    }
}
