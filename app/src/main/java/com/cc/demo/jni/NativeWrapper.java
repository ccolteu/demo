package com.cc.demo.jni;

/*

This is the starting point where you define your JNI interface.

Once this file is created do:

$ cd app/src/main/java/com/cc/demo/jni
$ javac -d . ./NativeWrapper.java
$ javah -jni com.cc.demo.jni.NativeWrapper

Delete intermediate file com.cc.demo.jni.NativeWrapper.class

Rename com_cc_demo_jni_NativeWrapper.h to native-interface.h and move it to
app/jni

Add the following to app/jni folder:
- native-interface.c
- Android.mk
- Application.mk

Note that Android.mk specifies
- the library name native-lib
- the source file(s) native-interface.c

Add CMakeLists.txt to app folder, specifying
- the native-lib library name
- the native-interface.c source file.

Update app/build.gradle to specify cmake path, flags, arguments

Build -> Clean Project
Build -> Make Project
This is needed to generate
app/.externalNativeBuild and its sub folders

Now build normally:
./gradlew assembleDebug

Look out for
Build add arm64-v8a
[1/1] Linking C shared library ../../../../build/intermediates/cmake/debug/obj/arm64-v8a/libnative-lib.so
...

The build should generate
app/build/intermediates/cmake/debug/obj/arm64-v8a/libnative-lib.so

In JAVA whenever you use NativeWrapper function calls make sure you include
    static {
        System.loadLibrary("native-lib");
    }
see MainActivity.java

*/

import com.cc.demo.jni.model.Employee;

public class NativeWrapper {
    public static native int add(int a, int b);
    public static native String getEmployeeData(Employee employee);
}
