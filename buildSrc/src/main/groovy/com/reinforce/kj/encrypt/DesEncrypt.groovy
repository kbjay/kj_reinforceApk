package com.reinforce.kj.encrypt

import javax.crypto.Cipher
import javax.crypto.CipherInputStream
import javax.crypto.spec.SecretKeySpec
import java.security.Key

/**
 * des加密
 *
 * 解密用来测试
 * @anthor kb_jay* create at 2019/7/10 下午8:13
 */
class DesEncrypt implements IEncrypt {
    private Cipher cipherEncrypt
    private Cipher cipherDecrypt

    private Key key(String secret) {
        byte[] keyByte = secret.getBytes()
        byte[] byteTemp = new byte[8]
        for (int i = 0; i < byteTemp.length && i < keyByte.length; i++) {
            byteTemp[i] = keyByte[i]
        }
        return new SecretKeySpec(byteTemp, "DES")
    }

    DesEncrypt(String secret) {
        cipherEncrypt = Cipher.getInstance("DES")
        cipherEncrypt.init(Cipher.ENCRYPT_MODE, key(secret))

        cipherDecrypt = Cipher.getInstance("DES")
        cipherDecrypt.init(Cipher.DECRYPT_MODE, key(secret))
    }
//    todo io标准化
    @Override
    void encrypt(File source, File destFile) {
        InputStream is = new FileInputStream(source)
        OutputStream out = new FileOutputStream(destFile)

        CipherInputStream cis = new CipherInputStream(is, cipherEncrypt)
        byte[] buffer = new byte[1024]
        int r
        while ((r = cis.read(buffer)) > 0) {
            out.write(buffer, 0, r)
        }
        cis.close()
        is.close()
        out.close()
//        try {
//            InputStream is = new FileInputStream(source)
//            File tempFile = new File(source.getParentFile().getAbsolutePath() + "/temp.dex")
//            FileOutputStream tempOs = new FileOutputStream(
//                    tempFile)
//
//            byte[] b = new byte[8]
//
//            int len = -1
//            while ((len = is.read(b)) == 8) {
//                tempOs.write(b, 0, len)
//            }
//            ///添加'\0'
//            if (len > 0) {
//                for (int i = len; i < 8; i++) {
//                    b[i] = '\0'
//                }
//                tempOs.write(b, 0, 8)
//            }
//            tempOs.close()
//
//            FileInputStream fis = new FileInputStream(tempFile)
//
//            OutputStream out = new FileOutputStream(destFile)
//
//
//            CipherInputStream cis = new CipherInputStream(fis, cipherEncrypt)
//            byte[] buffer = new byte[8]
//            int r = -1
//            while ((r = cis.read(buffer)) > 0) {
//                out.write(buffer, 0, r)
//            }
//
//            cis.close()
//            is.close()
//            out.close()
//            tempOs.close()
//            tempFile.delete()
//        } catch (FileNotFoundException e) {
//            e.printStackTrace()
//        } catch (IOException e) {
//            e.printStackTrace()
//        }
    }
}