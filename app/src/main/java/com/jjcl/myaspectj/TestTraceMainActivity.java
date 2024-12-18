package com.jjcl.myaspectj;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Debug;
import android.os.SystemClock;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.jjcl.myaspectj.resource.TestLeakActivity;
import com.jjcl.myaspectj.trace.IssueFilter;
import com.jjcl.myaspectj.utils.MemoryInfo;
import com.tencent.matrix.AppActiveMatrixDelegate;
import com.tencent.matrix.Matrix;
import com.tencent.matrix.listeners.IAppForeground;
import com.tencent.matrix.plugin.Plugin;
import com.tencent.matrix.trace.TracePlugin;
import com.tencent.matrix.trace.core.AppMethodBeat;
import com.tencent.matrix.trace.tracer.SignalAnrTracer;
import com.tencent.matrix.trace.view.FrameDecorator;
import com.tencent.matrix.util.MatrixLog;

public class TestTraceMainActivity extends Activity implements IAppForeground {
    private static String TAG = "Matrix.TestTraceMainActivity";
    FrameDecorator decorator;
    private static final int PERMISSION_REQUEST_CODE = 0x02;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test_trace);
        IssueFilter.setCurrentFilter(IssueFilter.ISSUE_TRACE);

        Plugin plugin = Matrix.with().getPluginByClass(TracePlugin.class);
        if (!plugin.isPluginStarted()) {
            MatrixLog.i(TAG, "plugin-trace start");
            plugin.start();
        }
        decorator = FrameDecorator.getInstance(this);
        if (!canDrawOverlays()) {
            requestWindowPermission();
        } else {
            decorator.show();
        }

        AppActiveMatrixDelegate.INSTANCE.addListener(this);

        MemoryInfo memoryInfo = new MemoryInfo(this);
        memoryInfo.printMemoryInfo();
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        MatrixLog.i(TAG, "requestCode:%s resultCode:%s", requestCode, resultCode);
        if (requestCode == PERMISSION_REQUEST_CODE) {
            if (canDrawOverlays()) {
                decorator.show();
            } else {
                Toast.makeText(this, "fail to request ACTION_MANAGE_OVERLAY_PERMISSION", Toast.LENGTH_LONG).show();
            }
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        Plugin plugin = Matrix.with().getPluginByClass(TracePlugin.class);
        if (plugin.isPluginStarted()) {
            MatrixLog.i(TAG, "plugin-trace stop");
            plugin.stop();
        }
        if (canDrawOverlays()) {
            decorator.dismiss();
        }
        AppActiveMatrixDelegate.INSTANCE.removeListener(this);
    }

    private boolean canDrawOverlays() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            return Settings.canDrawOverlays(this);
        } else {
            return true;
        }
    }

    private void requestWindowPermission() {
        Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
                Uri.parse("package:" + getPackageName()));
        startActivityForResult(intent, PERMISSION_REQUEST_CODE);
    }


    public void testEnter(View view) {
        Intent intent = new Intent(this, TestEnterActivity.class);
        startActivity(intent);
    }

    public void testFps(View view) {
        Intent intent = new Intent(this, TestFpsActivity.class);
        startActivity(intent);
    }

    public void testJankiess(View view) {
        A();
    }

    public void testANR(final View view) {
        A();
    }

    public void testPrintTrace(final View view) {
        SignalAnrTracer.printTrace();
    }

    public void testSignalANR(final View view) {
        try {
            Thread.sleep(20000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void A() {
        B();
        H();
        L();
        SystemClock.sleep(800);
    }

    private void B() {
        C();
        G();
        SystemClock.sleep(200);
    }

    private void C() {
        D();
        E();
        F();
        SystemClock.sleep(100);
    }

    private void D() {
        SystemClock.sleep(20);
    }

    private void E() {
        SystemClock.sleep(20);
    }

    private void F() {
        SystemClock.sleep(20);
    }

    private void G() {
        SystemClock.sleep(20);
    }

    private void H() {
        SystemClock.sleep(20);
        I();
        J();
        K();
    }

    private void I() {
        SystemClock.sleep(20);
    }

    private void J() {
        SystemClock.sleep(6);
    }

    private void K() {
        SystemClock.sleep(10);
    }


    private void L() {
        SystemClock.sleep(10000);
    }

    private boolean isStop = false;

    public void stopAppMethodBeat(View view) {
        AppMethodBeat appMethodBeat = Matrix.with().getPluginByClass(TracePlugin.class).getAppMethodBeat();
        if (isStop) {
            Toast.makeText(this, "start AppMethodBeat", Toast.LENGTH_LONG).show();
            appMethodBeat.onStart();
        } else {
            Toast.makeText(this, "stop AppMethodBeat", Toast.LENGTH_LONG).show();
            appMethodBeat.onStop();
        }
        isStop = !isStop;
    }

    public void evilMethod5(boolean is) {
        try {
            if (is) {
                Thread.sleep(800);
                throw new AssertionArrayException();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            Log.i("", "");
        }
        return;
    }

    public void testResourceLeak(View view) {
        Intent intent = new Intent(this, TestLeakActivity.class);
        startActivity(intent);
    }

    class AssertionArrayException extends Exception {

    }

    public void testInnerSleep() {
        SystemClock.sleep(6000);
    }

    private void tryHeavyMethod() {
        Debug.getMemoryInfo(new Debug.MemoryInfo());
    }

    @Override
    public void onForeground(boolean isForeground) {
        if (!canDrawOverlays()) {
            return;
        }
        if (!isForeground) {
            decorator.dismiss();
        } else {
            decorator.show();
        }
    }
}
