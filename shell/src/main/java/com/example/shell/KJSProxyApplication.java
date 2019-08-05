package com.example.shell;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

/**
 * // TODO: 2019/7/9
 * //1：获取dex文件
 * //2：找到源apk的dex
 * //3：解密
 * //4：加载dex
 *
 * @anthor kb_jay
 * create at 2019/7/11 下午7:23
 */
public class KJSProxyApplication extends Application {
    public static KJSProxyApplication mInstance;

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        mInstance = this;
        new JniBridge().reinforce(base);
    }
}
