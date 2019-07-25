//
// Created by kb_jay on 2019-07-18.
//
#include <jni.h>
#include "des.h"

#ifndef JNIUNZIPAPK_REINFORCE_UTIL_H
#define JNIUNZIPAPK_REINFORCE_UTIL_H

char *getApkPath(JNIEnv *, jobject);

void moveDex(JNIEnv *env, char *apkPath, char *dest);

void removeShell(JNIEnv *, char *);

void decryptDexInDir(JNIEnv *, char *);

void loadDex(JNIEnv *, jobject ,char *);

#endif //JNIUNZIPAPK_REINFORCE_UTIL_H
