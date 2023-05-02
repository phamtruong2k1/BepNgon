package com.phamtruong.bepngon.util;

import android.app.PendingIntent;
import android.os.Build;

public class FlagUtil {
    public static int getFlag(int flagMust) {
        int flag;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            flag = PendingIntent.FLAG_IMMUTABLE | flagMust;
        } else {
            flag = flagMust;
        }
        return flag;
    }
}