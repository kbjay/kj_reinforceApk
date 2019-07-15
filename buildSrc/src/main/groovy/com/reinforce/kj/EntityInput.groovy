package com.reinforce.kj

/**
 *
 * @anthor kb_jay
 * create at 2019/7/9 下午3:00
 */
class EntityInput {
    /**
     * 源apk
     */
    File sourceApk
    /**
     * 壳aar
     */
    File shellAar
    /**
     * 加密类型
     */
    int encriptionType
    /**
     * 签名文件
     */
    String signKeyStore

    /**
     * 签名alias
     */
    String signAlias

    /**
     * sdk 目录（用于将jar转dex 壳aar中）
     */
    String sdkPath

    /**
     * des 加密密钥
     */
    String secretKey

    /**
     * 签名工具路径
     */
    String jdkPath


    @Override
    String toString() {
        return "EntityInput{" +
                "sourceApk=" + sourceApk +
                ", shellAar=" + shellAar +
                ", encriptionType=" + encriptionType +
                ", signKeyStore=" + signKeyStore +
                ", sdkPath='" + sdkPath + '\'' +
                ", secretKey='" + secretKey + '\'' +
                ", jdkPath='" + jdkPath + '\'' +
                '}'
    }
}