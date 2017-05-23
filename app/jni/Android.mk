LOCAL_PATH := $(call my-dir)

include $(CLEAR_VARS)

LOCAL_MODULE    := add
LOCAL_SRC_FILES := add.c

include $(BUILD_SHARED_LIBRARY)
