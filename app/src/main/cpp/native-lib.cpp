#include <jni.h>
#include <string>

extern "C"
JNIEXPORT jstring JNICALL
Java_com_darkkillen_archmvvm_jni_JniHelper_stringFromJNI(JNIEnv *env, jclass type) {

    std::string hello = "Hello from C++";
    return env->NewStringUTF(hello.c_str());
}

extern "C"
JNIEXPORT jstring JNICALL
Java_com_darkkillen_archmvvm_jni_JniHelper_databaseName(JNIEnv *env, jclass type) {

    return env->NewStringUTF("arch-mvvm.db");
}

extern "C"
JNIEXPORT jstring JNICALL
Java_com_darkkillen_archmvvm_jni_JniHelper_apiService(JNIEnv *env, jclass type) {

    return env->NewStringUTF("https://api.github.com/");
}

extern "C"
JNIEXPORT jstring JNICALL
Java_com_darkkillen_archmvvm_jni_JniHelper_apiServiceMaintenance(JNIEnv *env, jclass type) {

    return env->NewStringUTF("https://www.domain.com/");
}
