package com.reinforce.kj.encrypt
/**
 *
 * @anthor kb_jay
 * create at 2019/7/10 下午7:17
 */
interface IEncrypt {
    /**
     * 加密
     * @param source
     * @param des
     */
    void encrypt(File source, File des)
    /**
     * 解密（测试用）
     * @param source
     * @param des
     */
    void decrypt(File source, File des)
}