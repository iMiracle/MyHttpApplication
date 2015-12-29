package com.hxl.miracle.myhttpapplication;

import android.app.Application;
import android.content.Context;

/**
 * Created by MirAcle on 2015/12/24.
 */
public class BaseApplication extends Application {
    public static Context mContext;

    public static BaseApplication getThis() {
        if (mContext instanceof BaseApplication) {
            return (BaseApplication) mContext;
        }
        return null;
    }
}


