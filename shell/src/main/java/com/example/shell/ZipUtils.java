package com.example.shell;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipOutputStream;

public class ZipUtils {

    /**
     * 使用GBK编码可以避免压缩中文文件名乱码
     */
    private static final String CHINESE_CHARSET = "GBK";

    /**
     * 文件读取缓冲区大小
     */
    private static final int CACHE_SIZE = 1024;

    /**
     * <p>
     * 压缩文件
     * </p>
     *
     * @param sourceFolder 压缩文件夹
     * @param zipFilePath  压缩文件输出路径
     * @throws Exception
     */
    public static void zip(String sourceFolder, String zipFilePath) throws Exception {
        OutputStream out = new FileOutputStream(zipFilePath);
        BufferedOutputStream bos = new BufferedOutputStream(out);
        ZipOutputStream zos = new ZipOutputStream(bos);
        File file = new File(sourceFolder);
        String basePath = null;
        if (file.isDirectory()) {
            basePath = file.getPath();
        } else {
            basePath = file.getParent();
        }
        zipFile(file, basePath, zos);
        zos.closeEntry();
        zos.close();
        bos.close();
        out.close();
    }

    /**
     * <p>
     * 递归压缩文件
     * </p>
     *
     * @param parentFile
     * @param basePath
     * @param zos
     * @throws Exception
     */
    private static void zipFile(File parentFile, String basePath, ZipOutputStream zos) throws Exception {
        File[] files = new File[0];
        if (parentFile.isDirectory()) {
            files = parentFile.listFiles();
        } else {
            files = new File[1];
            files[0] = parentFile;
        }
        String pathName;
        InputStream is;
        BufferedInputStream bis;
        byte[] cache = new byte[CACHE_SIZE];
        for (File file : files) {
            if (file.isDirectory()) {
                pathName = file.getPath().substring(basePath.length() + 1) + "/";
                zos.putNextEntry(new ZipEntry(pathName));
                zipFile(file, basePath, zos);
            } else {
                pathName = file.getPath().substring(basePath.length() + 1);
                is = new FileInputStream(file);
                bis = new BufferedInputStream(is);
                zos.putNextEntry(new ZipEntry(pathName));
                int nRead = 0;
                while ((nRead = bis.read(cache, 0, CACHE_SIZE)) != -1) {
                    zos.write(cache, 0, nRead);
                }
                bis.close();
                is.close();
            }
        }
    }

    /**
     * <p>
     * 解压压缩包
     * </p>
     *
     * @param zipFilePath 压缩文件路径
     * @param dir     压缩包释放目录
     * @throws Exception
     */
    public static void unZip(String zipFilePath, String dir) throws Exception {
        ZipFile sourceApk = new ZipFile(zipFilePath);
        Enumeration<? extends ZipEntry> entries = sourceApk.entries();
        while (entries.hasMoreElements()) {
            ZipEntry entry = entries.nextElement();
            File itemFile = new File(dir, entry.getName());
            if (!entry.isDirectory()) {
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

    }
}
