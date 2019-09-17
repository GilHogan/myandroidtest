package com.nextreal.vr.myandroidtest.presentation;

import android.content.Context;
import android.hardware.display.DisplayManager;
import android.view.Display;

/**
 * @Author: Guhuangjin
 * @DATE: 2019/9/17  10:33
 * @Description:
 * @Email: huangjin.gu@nextreal.tech
 */
public class util {

    /**
     * 获取设备所有屏幕
     */
    public Display[] getDisplays(Context context) {
        DisplayManager displayManager = (DisplayManager) context.getSystemService(Context.DISPLAY_SERVICE);
        return displayManager.getDisplays();
    }
}
