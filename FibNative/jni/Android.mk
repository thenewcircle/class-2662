LOCAL_PATH := $(call my-dir)
include $(CLEAR_VARS)

LOCAL_MODULE    := FibNative
LOCAL_LDLIBS += -llog
LOCAL_SRC_FILES := FibNative.cpp

include $(BUILD_SHARED_LIBRARY)
