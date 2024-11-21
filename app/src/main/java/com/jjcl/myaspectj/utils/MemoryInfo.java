package com.jjcl.myaspectj.utils;

import android.app.ActivityManager;
import android.content.Context;
import android.os.Debug;

public class MemoryInfo {
    private ActivityManager activityManager;

    public MemoryInfo(Context context) {
        activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
    }

    public void printMemoryInfo() {
        // 获取Android系统为应用分配的内存大小
        ActivityManager.MemoryInfo memoryInfo = new ActivityManager.MemoryInfo();
        activityManager.getMemoryInfo(memoryInfo);
        long availableMemory = memoryInfo.availMem;//（单位：B）
        long totalMemory = memoryInfo.totalMem;
        long threshold = memoryInfo.threshold;

        float maxMemory = (float) (Runtime.getRuntime().maxMemory() * 1.0/ (1024 * 1024));

        // 获取应用实际使用的内存大小
        Debug.MemoryInfo[] debugMemoryInfoArray = activityManager.getProcessMemoryInfo(new int[]{android.os.Process.myPid()});
        Debug.MemoryInfo debugMemoryInfo = debugMemoryInfoArray[0];
        float memoryUsed = debugMemoryInfo.getTotalPss(); // 获取应用实际使用的内存大小（单位：KB）

        // 打印信息
        JDebug.print("Available Memory: " + availableMemory/(1024 * 1024));
        JDebug.print("Total Memory: " + totalMemory/(1024 * 1024));
        JDebug.print("Memory Threshold: " + threshold/(1024 * 1024));
        // JDebug.print("Memory Used by App: " + memoryUsed + " KB");
        JDebug.print("Memory Used by App: " + memoryUsed/(1024 ) + " M");
        JDebug.print("最大分配内存: " + maxMemory + " M");
    }
}