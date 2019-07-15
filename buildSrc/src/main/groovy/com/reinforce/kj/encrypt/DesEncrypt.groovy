package com.reinforce.kj.encrypt

import javax.crypto.Cipher
import javax.crypto.CipherInputStream
import javax.crypto.spec.SecretKeySpec
import java.security.Key

/**
 * des加密
 *
 * 解密用来测试
 * @anthor kb_jay
 * create at 2019/7/10 下午8:13
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
    }

    @Override
    void decrypt(File source, File des) {
        OutputStream os = new FileOutputStream(des)
        InputStream is = new FileInputStream(source)
        CipherInputStream cis = new CipherInputStream(is, cipherDecrypt)

        byte[] buffer = new byte[1024]
        int len
        while ((len = cis.read(buffer)) > 0) {
            os.write(buffer, 0, len)
        }
        os.close()
        cis.close()
        is.close()
    }
}