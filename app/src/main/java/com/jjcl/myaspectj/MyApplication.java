package com.jjcl.myaspectj;

import android.app.Application;
import android.content.Intent;
import android.os.Build;
import android.os.Environment;
import android.util.Log;

import com.jjcl.myaspectj.resource.DynamicConfigImplDemo;
import com.jjcl.myaspectj.resource.ManualDumpActivity;
import com.jjcl.myaspectj.trace.DynamicConfigImpl;
import com.jjcl.myaspectj.trace.PluginListener;
import com.tencent.matrix.Matrix;
import com.tencent.matrix.resource.ResourcePlugin;
import com.tencent.matrix.resource.config.ResourceConfig;
import com.tencent.matrix.trace.TracePlugin;
import com.tencent.matrix.trace.config.TraceConfig;
import com.tencent.matrix.util.MatrixLog;

import java.io.File;

import javax.security.auth.login.LoginException;

public class MyApplication extends Application {

    private static final String TAG = MyApplication.class.getSimpleName();

    @Override
    public void onCreate() {
        super.onCreate();

        //TraceCannary
        DynamicConfigImpl dynamicConfig = new DynamicConfigImpl();

        //Resource
        DynamicConfigImplDemo dynamicConfig2 = new DynamicConfigImplDemo();
        ResourcePlugin resourcePlugin = configureResourcePlugin(dynamicConfig2);


        Matrix.Builder builder = new Matrix.Builder(this);

        builder.pluginListener(new PluginListener(this));

        //TraceCannary
        TracePlugin tracePlugin = configureTracePlugin(dynamicConfig);
        builder.plugin(tracePlugin);

        //Resource
        builder.plugin(resourcePlugin);

        Matrix.init(builder.build());
        tracePlugin.start();

        resourcePlugin.start();





    }

    private TracePlugin configureTracePlugin(DynamicConfigImpl dynamicConfig) {

        boolean fpsEnable = dynamicConfig.isFPSEnable();
        boolean traceEnable = dynamicConfig.isTraceEnable();
        boolean signalAnrTraceEnable = dynamicConfig.isSignalAnrTraceEnable();

        File traceFileDir = new File(getApplicationContext().getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS), "matrix_trace");
        // File traceFileDir = new File(Environment.getExternalStorageDirectory()+ File.separator+"asd935a", "matrix_trace");
        Log.e(TAG, "configureTracePlugin: "+getApplicationContext().getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS) );
        if (!traceFileDir.exists()) {
            if (traceFileDir.mkdirs()) {
                MatrixLog.e(TAG, "failed to create traceFileDir");
            }
        }

        File anrTraceFile = new File(traceFileDir, "anr_trace");
        File printTraceFile = new File(traceFileDir, "print_trace");

        Log.e(TAG, "configureTracePlugin, fpsEnable:"+fpsEnable+" traceEnable:"+traceEnable+" " );

        TraceConfig traceConfig = new TraceConfig.Builder()
                .dynamicConfig(dynamicConfig)
                .enableFPS(fpsEnable) // 帧率
                .enableEvilMethodTrace(traceEnable) // 耗时方法
                .enableAnrTrace(traceEnable) // ANR
                .enableStartup(traceEnable) // 启动
                .enableIdleHandlerTrace(traceEnable)
                .enableMainThreadPriorityTrace(true)
                .enableSignalAnrTrace(signalAnrTraceEnable)
                .anrTracePath(anrTraceFile.getAbsolutePath())
                .printTracePath(printTraceFile.getAbsolutePath())
                // 换成自己项目启动页
                .splashActivities("com.jjcl.myaspectj.MainActivity;") // 可指定多个启动页，使用分号 ";" 分割
                .isDebug(true)
                .isDevEnv(false)
                .build();

        return new TracePlugin(traceConfig);
    }


    private ResourcePlugin configureResourcePlugin(DynamicConfigImplDemo dynamicConfig) {
        Intent intent = new Intent();
        ResourceConfig.DumpMode mode = ResourceConfig.DumpMode.MANUAL_DUMP;
        MatrixLog.i(TAG, "Dump Activity Leak Mode=%s", mode);
        // intent.setClassName(this.getPackageName(), "com.tencent.mm.ui.matrix.ManualDumpActivity");
        intent.setClassName(this.getPackageName(), "com.jjcl.myaspectj.resource.ManualDumpActivity");
        ResourceConfig resourceConfig = new ResourceConfig.Builder()
                .dynamicConfig(dynamicConfig)
                .setAutoDumpHprofMode(mode)
                .setManualDumpTargetActivity(ManualDumpActivity.class.getName())
                .setManufacture(Build.MANUFACTURER)
                .build();
        ResourcePlugin.activityLeakFixer(this);




//         // 用于在用户点击生成的问题通知时，通过这个 Intent 跳转到指定的 Activity
//         Intent intent = new Intent();
//         intent.setClassName(this.getPackageName(), "com.tencent.mm.ui.matrix.ManualDumpActivity");
//
//         ResourceConfig resourceConfig = new ResourceConfig.Builder()
//                 .dynamicConfig(new DynamicConfigImplDemo()) // 用于动态获取一些自定义的选项，不同 Plugin 有不同的选项
//                 .setAutoDumpHprofMode(ResourceConfig.DumpMode.AUTO_DUMP) // 自动生成 Hprof 文件
// //        .setDetectDebuger(true) //matrix test code
//                 .setNotificationContentIntent(intent) // 问题通知
//                 .build();
//
//         builder.plugin(new ResourcePlugin(resourceConfig));
//
//         // 这个类可用于修复一些内存泄漏问题
//         ResourcePlugin.activityLeakFixer(this);

        return new ResourcePlugin(resourceConfig);
    }
}
