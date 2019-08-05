//
// Created by kb_jay on 2019-07-18.
//
#include <jni.h>

#ifndef REINFORCE_H
#define REINFORCE_H

#include "native_log.h"

#ifdef __cplusplus


extern "C" {
#endif
JNIEXPORT void JNICALL Java_com_example_shell_JniBridge_reinforce
        (JNIEnv *, jobject, jobject);

#ifdef __cplusplus
}
#endif

#endif
