package com.darkkillen.archmvvm.jni;

public class JniHelper {

    /**
     * A native method that is implemented by the 'native-lib' native library,
     * which is packaged with this application.
     */
    static {
        System.loadLibrary("native-lib");
    }

    public native static String stringFromJNI();
    public native static String databaseName();
    public native static String apiService();
    public native static String apiServiceMaintenance();
}
