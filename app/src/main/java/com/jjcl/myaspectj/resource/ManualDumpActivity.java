package com.jjcl.myaspectj.resource;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

import com.jjcl.myaspectj.R;
import com.tencent.matrix.resource.config.SharePluginInfo;

public class ManualDumpActivity extends Activity {
    private static final String TAG = "ManualDumpActivity";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manual_dump);

        ((TextView) findViewById(R.id.leak_activity))
                .setText(getIntent().getStringExtra(SharePluginInfo.ISSUE_ACTIVITY_NAME));
        ((TextView) findViewById(R.id.leak_process))
                .setText(getIntent().getStringExtra(SharePluginInfo.ISSUE_LEAK_PROCESS));

//        final ManualDumpProcessor.ManualDumpData data =
//                getIntent().getParcelableExtra(SharePluginInfo.ISSUE_DUMP_DATA);
//        if (data != null) {
//            ((TextView) findViewById(R.id.reference_chain))
//                    .setText(data.refChain);
//        } else {
//            ((TextView) findViewById(R.id.reference_chain))
//                    .setText(R.string.empty_reference_chain);
//        }
    }
}
