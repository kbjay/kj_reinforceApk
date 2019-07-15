package com.reinforce.kj.utils

import java.security.MessageDigest
import java.security.NoSuchAlgorithmException
import java.util.zip.Adler32

/**
 *
 * @anthor kb_jay
 * create at 2019/7/11 下午3:21
 */
class DexUtils {
    /**
     * 修改dex头 file_size值
     * @param dexBytes
     */
    static void fixFileSizeHeader(byte[] dexBytes) {
        //新文件长度
        byte[] newfs = intToByte(dexBytes.length)
        byte[] refs = new byte[4]
        //高位在前，低位在前掉个个
        for (int i = 0; i < 4; i++) {
            refs[i] = newfs[newfs.length - 1 - i]
        }
        //修改（32-35）
        System.arraycopy(refs, 0, dexBytes, 32, 4)
    }

    /**
     * int 转字节
     * @param number
     * @return
     */
    static byte[] intToByte(int number) {
        byte[] b = new byte[4]
        for (int i = 3; i >= 0; i--) {
            b[i] = (byte) (number % 256)
            number >>= 8
        }
        return b
    }

    /**
     * 修改dex头 sha1值
     * @param dexBytes
     */
    static void fixSHA1Header(byte[] dexBytes) {
        MessageDigest md = MessageDigest.getInstance("SHA-1")
        //从32为到结束计算sha--1
        md.update(dexBytes, 32, dexBytes.length - 32)
        byte[] newdt = md.digest()
        //修改sha-1值（12-31）
        System.arraycopy(newdt, 0, dexBytes, 12, 20)
    }

    /**
     * 修改dex头，CheckSum 校验码
     * @param dexBytes
     */
    static void fixCheckSumHeader(byte[] dexBytes) {
        Adler32 adler = new Adler32()
        //从12到文件末尾计算校验码
        adler.update(dexBytes, 12, dexBytes.length - 12)
        long value = adler.getValue()
        int va = (int) value
        byte[] newcs = intToByte(va)
        //高位在前，低位在前掉个个
        byte[] recs = new byte[4]
        for (int i = 0; i < 4; i++) {
            recs[i] = newcs[newcs.length - 1 - i]
        }
        //效验码赋值（8-11）
        System.arraycopy(recs, 0, dexBytes, 8, 4)
    }
}