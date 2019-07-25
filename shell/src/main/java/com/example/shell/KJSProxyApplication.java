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
        mInstance=this;
        new JniBridge().reinforce(base);
//        File apkFile = new File(getApplicationInfo().sourceDir);
//
//        Log.d(TAG, "apkFile->" + apkFile.getAbsolutePath());
//        File unzipApk = getDir("unzipApk", MODE_PRIVATE);
//
//        File app = new File(unzipApk, "app");
//
//        Log.d(TAG, "app->" + app.getAbsolutePath());
//
//        if (!app.exists()) {
//            try {
//                ZipUtils.unZip(apkFile.getAbsolutePath(), app.getAbsolutePath());
//            } catch (Exception e) {
//                Log.d(TAG, "unzip failed->" + e.getMessage());
//                e.printStackTrace();
//            }
//            File[] files = app.listFiles();
//            Log.d(TAG, "files len->" + files.length + "");
//            for (File file : files) {
//                String name = file.getName();
//                if ("classes.dex".equals(name)) {
//                    Log.d(TAG, "将classes.dex转为二进制");
//                    byte[] bytes = FileUtils.getFileToByte(file);
//                    byte[] lengthByte = new byte[4];
//                    System.arraycopy(bytes, bytes.length - 4, lengthByte, 0, 4);
//                    int len = ConvertUtils.byteArrayToInt(lengthByte);
//                    Log.d(TAG, "获取源dex的长度：" + len);
//                    byte[] realMainDex = new byte[len];
//                    System.arraycopy(bytes, bytes.length - 4 - len, realMainDex, 0, len);
//                    Log.d(TAG, "解密源dex");
//                    byte[] decrypt = new DESUtils("1234").decrypt(realMainDex);
//                    Log.d(TAG, "解密源dex的长度：" + decrypt.length);
//                    if (decrypt != null) {
//                        Log.d(TAG, "解密源dex成功，将源dex写入file");
//                        try {
//                            FileOutputStream fos = new FileOutputStream(file);
//                            fos.write(decrypt);
//                            fos.flush();
//                            fos.close();
//                        } catch (FileNotFoundException e) {
//                            e.printStackTrace();
//                        } catch (IOException e) {
//                            e.printStackTrace();
//                        }
//                    }
//                } else if (name.endsWith(".dex")) {
//                    Log.d(TAG, "解密源dex(非classed.dex)");
//                    byte[] bytes = FileUtils.getFileToByte(file);
//                    byte[] decrypt = new DESUtils("1234").decrypt(bytes);
//                    if (decrypt != null) {
//                        try {
//                            FileOutputStream fos = new FileOutputStream(file);
//                            fos.write(decrypt);
//                            fos.flush();
//                            fos.close();
//                        } catch (FileNotFoundException e) {
//                            e.printStackTrace();
//                        } catch (IOException e) {
//                            e.printStackTrace();
//                        }
//                    }
//                }
//            }
//        }
//        //mutidex 方式加载
//        List<File> dexFiles = new ArrayList<>();
//        for (File file : app.listFiles()) {
//            if (file.getName().endsWith(".dex")) {
//                dexFiles.add(file);
//            }
//        }
//        Log.d(TAG, "获取dex文件的个数" + dexFiles.size());
//        try {
//            Log.d(TAG, "使用mutidex中的v19加载dex");
//            V19Utils.install(getClassLoader(), dexFiles, app);
//            Log.d(TAG, "加载成功");
//        } catch (IllegalAccessException e) {
//            Log.d(TAG, "加载dex失败" + e.getMessage());
//            e.printStackTrace();
//        } catch (NoSuchFieldException e) {
//            Log.d(TAG, "加载dex失败" + e.getMessage());
//            e.printStackTrace();
//        } catch (InvocationTargetException e) {
//            Log.d(TAG, "加载dex失败" + e.getMessage());
//            e.printStackTrace();
//        } catch (NoSuchMethodException e) {
//            Log.d(TAG, "加载dex失败" + e.getMessage());
//            e.printStackTrace();
//        }
//        Toast.makeText(base, "KJSProxyApplication", Toast.LENGTH_SHORT).show();
    }
}
