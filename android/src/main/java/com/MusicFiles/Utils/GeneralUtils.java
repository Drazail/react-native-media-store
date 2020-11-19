package com.MusicFiles.Utils;

import android.os.Bundle;

import com.facebook.react.bridge.Promise;

public class GeneralUtils {

    public static final String LOG = "RNGetMusicFiles";

    public static Runnable toRunnable(Promise promise) {
        return () -> promise.resolve(null);
    }

    public static long toMillis(double seconds) {
        return (long) (seconds * 1000);
    }

    public static double toSeconds(long millis) {
        return millis / 1000D;
    }

    public static int getInt(Bundle data, String key, int defaultValue) {
        Object value = data.get(key);
        if (value instanceof Number) {
            return ((Number) value).intValue();
        }
        return defaultValue;
    }

}
