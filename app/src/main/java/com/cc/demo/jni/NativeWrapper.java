package com.cc.demo.jni;

/*

This is the starting point where you define your JNI interface.

Once this file is created do:

$ cd app/src/main/java/com/cc/demo/jni
$ javac -d . ./NativeWrapper.java
$ javah -jni com.cc.demo.jni.NativeWrapper

Delete intermediate file com.cc.demo.jni.NativeWrapper.class

Rename com_cc_demo_jni_NativeWrapper.h to add.h and move it to
app/jni

Add the following to app/jni folder:
- add.c
- Android.mk
- Application.mk

Add CMakeLists.txt to app folder, specifying the add.c source file.

Update app/build.gradle to specify cmake path, flags, arguments

Now build normally:
./gradlew assembleDebug

Look out for
Build add arm64-v8a
[1/1] Linking C shared library ../../../../build/intermediates/cmake/debug/obj/arm64-v8a/libadd.so
...

It should generate
app/build/intermediates/cmake/debug/obj/arm64-v8a/libadd.so

In JAVA whenever you use NativeWrapper.add() make sure you include
    static {
        System.loadLibrary("add");
    }
see MainActivity.java

 */

public class NativeWrapper {

    public static native int add(int a, int b);

}
