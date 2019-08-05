package com.example.shell;

import android.text.TextUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

/**
 * @anthor kb_jay
 * create at 2019-07-18 23:39
 */
public class ApkUtils {
    /**
     * 获取所有dex
     *
     * @param source
     * @param dest
     */
    public static void moveDex(String source, String dest) {
        if (TextUtils.isEmpty(source) || TextUtils.isEmpty(dest)) {
            throw new RuntimeException("param == null");
        }

        if (!source.endsWith(".apk")) {
            throw new RuntimeException("error source");
        }

        try {
            ZipFile sourceApk = new ZipFile(source);
            Enumeration<? extends ZipEntry> entries = sourceApk.entries();
            while (entries.hasMoreElements()) {
                ZipEntry entry = entries.nextElement();
                File itemFile = new File(dest, entry.getName());
                if (!entry.isDirectory() && entry.getName().endsWith(".dex")) {
                    File parent = itemFile.getParentFile();
                    parent.mkdirs();
                    InputStream is = sourceApk.getInputStream(entry);
                    OutputStream fos = new FileOutputStream(itemFile);
                    byte[] b = new byte[2048];
                    int len = -1;
                    while ((len = is.read(b)) > 0) {
                        fos.write(b, 0, len);
                    }
                    is.close();
                    fos.close();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
