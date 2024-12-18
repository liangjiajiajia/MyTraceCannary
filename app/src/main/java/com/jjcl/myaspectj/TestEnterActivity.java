package com.jjcl.myaspectj;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.jjcl.myaspectj.trace.IssueFilter;

public class TestEnterActivity extends Activity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test_fps_layout);
        findViewById(R.id.content).setVisibility(View.GONE);

        IssueFilter.setCurrentFilter(IssueFilter.ISSUE_TRACE);
        ListView listView = findViewById(R.id.list_view);
        String[] data = new String[200];
        for (int i = 0; i < 200; i++) {
            data[i] = "MatrixTrace:" + i;
        }
        listView.setAdapter(new ArrayAdapter(this, android.R.layout.simple_list_item_1, data));
        SystemClock.sleep(3000);
    }
}
