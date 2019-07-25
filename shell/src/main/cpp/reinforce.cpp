//
// Created by kb_jay on 2019-07-18.
//

#include <string>
#include "reinforce.h"
#include "util.h"

extern "C" void JNICALL Java_com_example_shell_JniBridge_reinforce
        (JNIEnv *env, jobject jobj, jobject context) {

    char *apkPath = getApkPath(env, context);
    Logd("apkPath -->%s", apkPath);

    jobject rootFile = env->CallObjectMethod(context, env->GetMethodID(env->GetObjectClass(context),
                                                                       "getDir",
                                                                       "(Ljava/lang/String;I)Ljava/io/File;"),
                                             env->NewStringUTF("reinforce"), 0);
    jstring rootPath = static_cast<jstring>(env->CallObjectMethod(rootFile,
                                                                  env->GetMethodID(
                                                                          env->GetObjectClass(
                                                                                  rootFile),
                                                                          "getAbsolutePath",
                                                                          "()Ljava/lang/String;")));
    char *dest = const_cast<char *>(env->GetStringUTFChars(rootPath, NULL));

    Logd("unzip root  -->%s", dest);
    //todo
    moveDex(env, apkPath, dest);

//    std::string temp =dest;
//    temp+="/sign.apk";
//    moveDex(env, const_cast<char *>(temp.data()), dest);
    Logd("move dex success");
    removeShell(env, dest);
    Logd("removeShell success");
    decryptDexInDir(env, dest);
    Logd("decrypt success");
    loadDex(env,context,dest);
    Logd("load success");
}

