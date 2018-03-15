package com.example.lifen.stoerdemo1.app;

import android.app.Application;
import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.Toast;

import com.example.lifen.stoerdemo1.model.Constants;
import com.example.lifen.stoerdemo1.model.PrefUtils;
import com.tencent.soter.wrapper.SoterWrapperApi;
import com.tencent.soter.wrapper.wrap_callback.SoterProcessCallback;
import com.tencent.soter.wrapper.wrap_callback.SoterProcessNoExtResult;
import com.tencent.soter.wrapper.wrap_core.SoterProcessErrCode;
import com.tencent.soter.wrapper.wrap_task.InitializeParam;

/**
 * Created by LiFen on 2018/3/14.
 * 程序入口 配置文件中配置
 */

public class App extends Application {
    private static final String TAG = "App";
    @Override
    public void onCreate() {
        super.onCreate();
        initSoterSupport();
    }

    private void initSoterSupport() {
        InitializeParam param = new InitializeParam.InitializeParamBuilder()
                .setScenes(Constants.SCENE_VALUE) // 场景值常量，后续使用该常量进行密钥生成或指纹认证
                .build();
        SoterWrapperApi.init(getApplicationContext(), // 场景句柄
                new SoterProcessCallback<SoterProcessNoExtResult>() {

                    @Override
                    public void onResult(@NonNull SoterProcessNoExtResult result) {
                        Log.i(TAG, "onResult: " + result.toString());
                        if(result.errCode != SoterProcessErrCode.ERR_SOTER_NOT_SUPPORTED){
                            PrefUtils.setBoolean(getApplicationContext(),Constants.KEY_SOTER_SUPPORTED,true);
                            Toast.makeText(getApplicationContext(),"支持",Toast.LENGTH_SHORT).show();
                        }
                    }
                }, // 初始化回调
                param);

    }
}
