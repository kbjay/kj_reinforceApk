package com.example.kb_jay.kj_reinforcegradleplugin;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.example.shell.ZipUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//
//        try {
//            ZipUtils.unZip(getFilesDir().getAbsolutePath() + "/test.apk",
//                    getFilesDir().getAbsolutePath());
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        File file = new File(getApplicationInfo().sourceDir);
//        File filesDir = getFilesDir();
//        File file1 = new File(filesDir, "test.apk");
//        Log.d("test->",file.getAbsolutePath());
//        Log.d("test->",file1.getAbsolutePath());
//        try {
//            FileInputStream fis = new FileInputStream(file);
//            FileOutputStream fos = new FileOutputStream(file1);
//            byte[] buffer = new byte[1024];
//            int len =-1;
//            while((len=fis.read(buffer))>0){
//                fos.write(buffer,0,len);
//            }
//            fis.close();
//            fos.close();
//
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
    }
}
