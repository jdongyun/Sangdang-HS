package com.dongyun.sangdang;

import android.util.Log;

/**
 * Created by 동윤 on 2014-12-30.
 */
public class DevLog {
    static boolean LOG = true;
    static void i(String TAG, String LogStr) {
        if(LOG) {
            Log.i(TAG, LogStr);
        }
    }
    static void d(String TAG, String LogStr) {
        if(LOG) {
            Log.d(TAG, LogStr);
        }
    }
}
