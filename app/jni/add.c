#include <jni.h>
#include "add.h"

JNIEXPORT jint JNICALL Java_com_cc_demo_jni_NativeWrapper_add
        (JNIEnv * je, jclass jc, jint a, jint b)
{
    return (a + b);
}