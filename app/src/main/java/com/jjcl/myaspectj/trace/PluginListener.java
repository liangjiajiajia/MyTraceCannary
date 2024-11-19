package com.jjcl.myaspectj.trace;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Looper;

import com.jjcl.myaspectj.MyLog;
import com.jjcl.myaspectj.trace.IssueFilter;
import com.tencent.matrix.plugin.DefaultPluginListener;
import com.tencent.matrix.report.Issue;
import com.tencent.matrix.util.MatrixLog;

import java.lang.ref.SoftReference;

public class PluginListener extends DefaultPluginListener {
    public static final String TAG = "Matrix.PluginListener";
    public SoftReference<Context> softReference;
    private final Handler mHandler = new Handler(Looper.getMainLooper());
    public PluginListener(Context context) {
        super(context);
        softReference = new SoftReference<>(context);
    }

    @Override
    public void onReportIssue(Issue issue) {
        super.onReportIssue(issue);
        MatrixLog.e(TAG, issue.toString());
        IssuesMap.put(IssueFilter.getCurrentFilter(), issue);
        MyLog.e("Issue",issue.toString());
        jumpToIssueActivity();
    }

    public void jumpToIssueActivity() {
        Context context = softReference.get();
        Intent intent = new Intent(context, IssuesListActivity.class);

        if (context instanceof Application) {
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        }

        context.startActivity(intent);
    }
}
