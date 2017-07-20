#include <jni.h>

#ifndef _Included_com_cc_demo_jni_NativeWrapper
#define _Included_com_cc_demo_jni_NativeWrapper
#ifdef __cplusplus
extern "C" {
#endif

JNIEXPORT jint JNICALL Java_com_cc_demo_jni_NativeWrapper_add
  (JNIEnv *, jclass, jint, jint);

JNIEXPORT jstring JNICALL Java_com_cc_demo_jni_NativeWrapper_getEmployeeData
        (JNIEnv *, jobject, jobject);

#ifdef __cplusplus
}
#endif
#endif
