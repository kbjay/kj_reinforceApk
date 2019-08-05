//
// Created by kb_jay on 2019-07-18.
//

#include "util.h"
#include "native_log.h"
#include <fstream>
#include <dirent.h>
#include <strstream>
#include <cstdio>

/**
 * 获取apk路径
 * @param pEnv
 * @param pJobject
 * @return
 */
char *getApkPath(JNIEnv *env, jobject context) {
    jobject appInfo = env->CallObjectMethod(context, env->GetMethodID(env->GetObjectClass(context),
                                                                      "getApplicationInfo",
                                                                      "()Landroid/content/pm/ApplicationInfo;"));
    jstring apkPath = static_cast<jstring>(env->GetObjectField(appInfo,
                                                               env->GetFieldID(
                                                                       env->GetObjectClass(appInfo),
                                                                       "sourceDir",
                                                                       "Ljava/lang/String;")));
    jboolean isCopy = JNI_TRUE;
    return const_cast<char *>(env->GetStringUTFChars(apkPath, &isCopy));
}

/**
 * 解压缩
 * @param env
 * @param apkPath
 * @param dest
 */
void moveDex(JNIEnv *env, char *apkPath, char *dest) {
    jclass apkUtils = env->FindClass("com/example/shell/ApkUtils");
    env->CallStaticVoidMethod(apkUtils, env->GetStaticMethodID(apkUtils, "moveDex",
                                                               "(Ljava/lang/String;Ljava/lang/String;)V"),
                              env->NewStringUTF(apkPath), env->NewStringUTF(dest));
}

void removeShell(JNIEnv *env, char *dest) {

    std::string temp = dest;
    temp += "/classes.dex";

    //获取文件长度
    std::ifstream source;
    source.open(temp, std::ifstream::binary);
    source.seekg(-4, std::ios::end);
    char b[4];
    source.read(b, 4);

    int length = (b[3] & 0xFF) |
                 (b[2] & 0xFF) << 8 |
                 (b[1] & 0xFF) << 16 |
                 (b[0] & 0xFF) << 24;

    Logd("real dex length ->%d", length);

    source.seekg(-length - 4, std::ios::end);

    char tt[length];
    source.read(tt, length);
    source.close();

    std::ofstream end;
    end.open(temp, std::ofstream::binary);
    end.write(tt, length);
    end.close();
}

//todo des 替换
void decryptDexInDir(JNIEnv *env, char *dir) {
    jclass pJclass = env->FindClass("com/example/shell/DESUtils");

    jmethodID pID = env->GetMethodID(pJclass, "<init>", "(Ljava/lang/String;)V");

    jobject pJobject = env->NewObject(pJclass, pID, env->NewStringUTF("12345678"));
    //找到file，执行解密
    DIR *p_dir = opendir(dir);
    struct dirent *p_dirent;

    if (p_dir == NULL) {
        Logd("error->>open dir failed!!!");
        return;
    }
    while ((p_dirent = readdir(p_dir))) {
        std::string sub(".dex");
        std::string s(p_dirent->d_name);
        if (s != "." && s != ".." && s.rfind(sub) == (s.length() - sub.length()) ? 1 : 0) {
            Logd("file name-> %s", s.data());
            std::string name(std::string(dir) + "/" + s);
            std::string name_(std::string(dir) + "/_" + s);

            jmethodID pJmethodID = env->GetMethodID(pJclass, "decrypt", "(Ljava/lang/String;Ljava/lang/String;)V");
            env->CallVoidMethod(pJobject,pJmethodID,env->NewStringUTF(name.data()),env->NewStringUTF(name_.data()));

            remove(name.data());
            rename(name_.data(), name.data());
        }
    }
    closedir(p_dir);
}

void loadDex(JNIEnv *env, jobject context, char *dest) {
    jclass v19 = env->FindClass("com/example/shell/V19Utils");
    env->CallStaticVoidMethod(v19,
                              env->GetStaticMethodID(v19, "installDir",
                                                     "(Landroid/content/Context;Ljava/lang/String;)V"),
                              context,
                              env->NewStringUTF(dest)
    );
}