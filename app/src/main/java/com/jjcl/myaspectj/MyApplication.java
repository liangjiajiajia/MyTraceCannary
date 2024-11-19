package com.jjcl.myaspectj;

import android.app.Application;
import android.os.Environment;
import android.util.Log;

import com.jjcl.myaspectj.trace.DynamicConfigImpl;
import com.jjcl.myaspectj.trace.PluginListener;
import com.tencent.matrix.Matrix;
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

        DynamicConfigImpl dynamicConfig = new DynamicConfigImpl();

        Matrix.Builder builder = new Matrix.Builder(this);

        builder.pluginListener(new PluginListener(this));

        TracePlugin tracePlugin = configureTracePlugin(dynamicConfig);
        builder.plugin(tracePlugin);

        Matrix.init(builder.build());
        tracePlugin.start();

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
}
