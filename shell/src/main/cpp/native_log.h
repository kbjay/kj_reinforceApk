//
// Created by kb_jay on 2019-07-18.
//
#ifndef NATIVE_LOG_H
#define NATIVE_LOG_H

#include <android/log.h>

#define TAG "reinforce"
#define Logd(...) __android_log_print(ANDROID_LOG_DEBUG,TAG,__VA_ARGS__)
#endif
