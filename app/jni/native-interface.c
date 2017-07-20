#include <jni.h>
#include "native-interface.h"
#include <stdio.h>

JNIEXPORT jint JNICALL Java_com_cc_demo_jni_NativeWrapper_add
        (JNIEnv * env, jclass jc, jint a, jint b)
{
    return (a + b);
}

JNIEXPORT jstring JNICALL Java_com_cc_demo_jni_NativeWrapper_getEmployeeData
        (JNIEnv *env, jobject callingObject, jobject employeeObject)
{
    jclass employeeClass = (*env)->GetObjectClass(env, employeeObject);
    jmethodID getSalaryMethod = (*env)->GetMethodID(env, employeeClass, "getSalary", "()I");
    int salary = (*env)->CallIntMethod(env, employeeObject, getSalaryMethod);

    jclass jclass_JCV = (*env)->FindClass(env, "com/cc/demo/jni/model/Employee");
    jfieldID fid_name = (*env)->GetFieldID(env, jclass_JCV, "name" , "Ljava/lang/String;");
    jstring jstr = (*env)->GetObjectField(env, employeeObject, fid_name);
    const char *name = (*env)->GetStringUTFChars(env, jstr, 0);

    char result[100];
    sprintf(result, "Employee %s, salary %d", name, salary);

    jstring ret = (*env)->NewStringUTF(env, result);
    return ret;
}
