package com.example.shell;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.CipherInputStream;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.util.BitSet;

/**
 * AES 对称算法加密/解密工具类
 *
 * @author xietansheng
 */
public class DESUtils {

    private Cipher cipherEncrypt;
    private Cipher cipherDecrypt;

    private Key key(String secret) {
        byte[] keyByte = secret.getBytes();
        byte[] byteTemp = new byte[8];
        for (int i = 0; i < byteTemp.length && i < keyByte.length; i++) {
            byteTemp[i] = keyByte[i];
        }
        return new SecretKeySpec(byteTemp, "DES");
    }

    public DESUtils(String secret) {
        try {
            cipherEncrypt = Cipher.getInstance("DES");
            cipherEncrypt.init(Cipher.ENCRYPT_MODE, key(secret));

            cipherDecrypt = Cipher.getInstance("DES");
            cipherDecrypt.init(Cipher.DECRYPT_MODE, key(secret));
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        }
    }

    public void encrypt(File source, File destFile) {
        InputStream is = null;
        try {
            is = new FileInputStream(source);
            OutputStream out = new FileOutputStream(destFile);

            CipherInputStream cis = new CipherInputStream(is, cipherEncrypt);
            byte[] buffer = new byte[1024];
            int r = -1;
            while ((r = cis.read(buffer)) > 0) {
                out.write(buffer, 0, r);
            }
            cis.close();
            is.close();
            out.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void decrypt(File source, File des) {
        OutputStream os = null;
        try {
            os = new FileOutputStream(des);
            InputStream is = new FileInputStream(source);
            CipherInputStream cis = new CipherInputStream(is, cipherDecrypt);

            byte[] buffer = new byte[1024];
            int len = -1;
            while ((len = cis.read(buffer)) > 0) {
                os.write(buffer, 0, len);
            }
            os.close();
            cis.close();
            is.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void decrypt(String source, String des) {
        OutputStream os = null;
        try {
            os = new FileOutputStream(des);
            InputStream is = new FileInputStream(source);
            CipherInputStream cis = new CipherInputStream(is, cipherDecrypt);

            byte[] buffer = new byte[1024];
            int len = -1;
            while ((len = cis.read(buffer)) > 0) {
                os.write(buffer, 0, len);
            }
            os.close();
            cis.close();
            is.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public byte[] decrypt(byte[] arr) {
        try {
            return cipherDecrypt.doFinal(arr);
        } catch (BadPaddingException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        }
        return null;
    }
}
