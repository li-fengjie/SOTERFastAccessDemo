# STOERFastAccessDemo

安卓指纹STOWER(可取到指纹fid索引信息)开发 快速接入演示
=
  1.添加gradle依赖
  2.声明权限
  3.准备密钥
  4.进行指纹认证、
  5.释放

  在安全性要求不高的情况下，可以在几行代码之内快速使用TENCENT SOTER完成指纹授权逻辑编写。
  在使用之前，请确保所使用的测试机在支持机型列表中。
  添加gradle依赖
  在项目的build.gradle中，添加TENCENT SOTER依赖
  dependencies {
      ...
      compile 'com.tencent.soter:soter-wrapper:1.3.10'
      ...
  }
  声明权限
  _
  在 AndroidManifest.xml中添加使用指纹权限
  <uses-permission android:name="android.permission.USE_FINGERPRINT"/>
  初始化
  初始化过程整个应用声明周期内只需要进行一次，用于生成基本配置和检查设备支持情况。你可以选择在Application中进行初始化，或者在使用TENCENT SOTER之前。
  InitializeParam param = new InitializeParam.InitializeParamBuilder()
  .setScenes(0) // 场景值常量，后续使用该常量进行密钥生成或指纹认证
  .build();
  SoterWrapperApi.init(context, // 场景句柄
  new SoterProcessCallback<SoterProcessNoExtResult>() {...}, // 初始化回调
  param);
  准备密钥
  需要在使用指纹认证之前生成相关密钥
  SoterWrapperApi.prepareAuthKey(new SoterProcessCallback<SoterProcessKeyPreparationResult>() {...},false, true, 0, null, null);
  进行指纹认证
  密钥生成完毕之后，可以使用封装接口调用指纹传感器进行认证。
  AuthenticationParam param = new AuthenticationParam.AuthenticationParamBuilder()
                                      .setScene(0)
                                      .setContext(MainActivity.this)
                                      .setFingerprintCanceller(mSoterFingerprintCanceller)
                                      .setPrefilledChallenge("test challenge")
                                      .setSoterFingerprintStateCallback(new SoterFingerprintStateCallback() {...}).build();
  SoterWrapperApi.requestAuthorizeAndSign(new SoterProcessCallback<SoterProcessAuthenticationResult>() {...}, param);
  释放
  当你不再使用TENCENT SOTER时，可以选择释放所有资源，用于停止所有生成、上传任务以及支持状态等。释放之后再次使用时，需要重新进行初始化。 实际上，TENCENT SOTER本身不会占据过多资源，只需要在确认不会再次使用的前提下（如切换账户之前）释放一次即可。
  SoterWrapperApi.release();
