package com.jjcl.myaspectj;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.SystemClock;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.jjcl.myaspectj.trace.IssuesListActivity;
import com.jjcl.myaspectj.trace.IssuesMap;
import com.jjcl.myaspectj.trace.ParseIssueUtil;
import com.tencent.matrix.report.Issue;

import org.json.JSONException;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.lang.ref.WeakReference;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private final static File methodFilePath = new File(Environment.getExternalStorageDirectory(), "Debug.methodmap");
    private RecyclerView recyclerView;
    private static final int REQUEST_STORAGE_PERMISSION = 123;

    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static String[] PERMISSIONS_STORAGE = {
            "android.permission.READ_EXTERNAL_STORAGE",
            "android.permission.WRITE_EXTERNAL_STORAGE"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
        //     // Permission is not granted, request it
        //     ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_STORAGE_PERMISSION);
        // } else {
        //     // Permission has already been granted
        //     Toast.makeText(this, "Permission already granted", Toast.LENGTH_SHORT).show();
        // }

        verifyStoragePermissions(this);



        initRecyclerView();

        findViewById(R.id.btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("TAG", "hh download: ");
                download();
            }
        });
    }

    public void initRecyclerView() {
        recyclerView = (RecyclerView) findViewById(R.id.recycler_test);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));


        List<Item> itemList = new ArrayList<>();
        itemList.add(new Item("Title 1", "Description 1"));
        itemList.add(new Item("Title 2", "Description 2"));
        itemList.add(new Item("Title 3", "Description 3"));
        itemList.add(new Item("Title 3", "Description 3"));
        itemList.add(new Item("Title 3", "Description 3"));
        itemList.add(new Item("Title 3", "Description 3"));
        itemList.add(new Item("Title 3", "Description 3"));
        itemList.add(new Item("Title 3", "Description 3"));
        itemList.add(new Item("Title 3", "Description 3"));
        itemList.add(new Item("Title 3", "Description 3"));
        itemList.add(new Item("Title 3", "Description 3"));
        itemList.add(new Item("Title 3", "Description 3"));
        itemList.add(new Item("Title 3", "Description 3"));
        itemList.add(new Item("Title 3", "Description 3"));
        itemList.add(new Item("Title 3", "Description 3"));
        itemList.add(new Item("Title 3", "Description 3"));
        itemList.add(new Item("Title 3", "Description 3"));
        itemList.add(new Item("Title 3", "Description 3"));
        itemList.add(new Item("Title 3", "Description 3"));
        itemList.add(new Item("Title 3", "Description 3"));
        itemList.add(new Item("Title 3", "Description 3"));
        itemList.add(new Item("Title 3", "Description 3"));
        itemList.add(new Item("Title 3", "Description 3"));
        itemList.add(new Item("Title 3", "Description 3"));
        itemList.add(new Item("Title 3", "Description 3"));
        itemList.add(new Item("Title 3", "Description 3"));
        itemList.add(new Item("Title 3", "Description 3"));
        itemList.add(new Item("Title 3", "Description 3"));
        itemList.add(new Item("Title 3", "Description 3"));
        itemList.add(new Item("Title 3", "Description 3"));
        itemList.add(new Item("Title 3", "Description 3"));
        itemList.add(new Item("Title 3", "Description 3"));
        itemList.add(new Item("Title 3", "Description 3"));
        itemList.add(new Item("Title 3", "Description 3"));
        itemList.add(new Item("Title 3", "Description 3"));
        itemList.add(new Item("Title 3", "Description 3"));
        itemList.add(new Item("Title 3", "Description 3"));
        itemList.add(new Item("Title 3", "Description 3"));
        itemList.add(new Item("Title 3", "Description 3"));
        itemList.add(new Item("Title 3", "Description 3"));

        MyAdapter adapter = new MyAdapter(itemList);
        recyclerView.setAdapter(adapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        // try {
        //     Thread.sleep(3000);
        // } catch (Exception e) {
        //
        // }
    }

    public void download() {
        // SystemClock.sleep(6000);

        // try {
        //     Thread.sleep(3000);
        // } catch (Exception e) {
        //
        // }


        Log.e("TAG", "download: ");
        Toast.makeText(MainActivity.this, "dd", Toast.LENGTH_SHORT).show();

        // startActivity(new Intent(MainActivity.this,IssuesListActivity.class));
    }


//     adb shell perfetto -o /data/misc/perfetto-traces/trace_file.perfetto-trace -t 20s \
//     sched freq idle am wm gfx view binder_driver hal dalvik camera input res memory
//
//     curl -O https://raw.githubusercontent.com/google/perfetto/master/tools/record_android_trace
//     python3 record_android_trace -o trace_file.perfetto-trace -t 10s -b 64mb \ sched freq idle am wm gfx view binder_driver hal dalvik camera input res memory
//
//
//     curl -O https://raw.githubusercontent.com/google/perfetto/master/tools/record_android_trace
//     chmod u+x record_android_trace
// ./record_android_trace -c config.pbtx -o trace_file.perfetto-trace -t 10s -b 64mb \
//     sched freq idle am wm gfx view binder_driver hal dalvik camera input res memory

    public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {

        private List<Item> itemList;

        public MyAdapter(List<Item> itemList) {
            this.itemList = itemList;
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_layout, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            Item item = itemList.get(position);
            holder.titleTextView.setText(item.getTitle());
            holder.descriptionTextView.setText(item.getDescription());
        }

        @Override
        public int getItemCount() {
            return itemList.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            public TextView titleTextView;
            public TextView descriptionTextView;

            public ViewHolder(View itemView) {
                super(itemView);
                titleTextView = itemView.findViewById(R.id.title);
                descriptionTextView = itemView.findViewById(R.id.description);
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_STORAGE_PERMISSION) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission granted
                Toast.makeText(this, "Permission granted", Toast.LENGTH_SHORT).show();
            } else {
                // Permission denied
                Toast.makeText(this, "Permission denied", Toast.LENGTH_SHORT).show();
            }
        }
    }


    //被调用的方法
    public void verifyStoragePermissions(Activity activity) {
        try {
            //检测是否有写的权限
            int permission = ActivityCompat.checkSelfPermission(activity,
                    "android.permission.WRITE_EXTERNAL_STORAGE");
            if (permission != PackageManager.PERMISSION_GRANTED) {
                // 没有写的权限，去申请写的权限，会弹出对话框
                ActivityCompat.requestPermissions(activity, PERMISSIONS_STORAGE, REQUEST_EXTERNAL_STORAGE);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (!Settings.canDrawOverlays(this)) {
                Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
                        Uri.parse("package:" + getPackageName()));
                startActivityForResult(intent, 1000);
            }
        }
    }


    //
    // public class LoginActivity extends Activity {
    //     //动态获取权限需要添加的常量
    //
    //
    //     @Override
    //     protected void onCreate(Bundle savedInstanceState) {
    //         super.onCreate(savedInstanceState);
    //         //在onCreate方法这里调用来动态获取权限
    //         verifyStoragePermissions(this);
    //     }
    //
    //     //被调用的方法
    //     public static void verifyStoragePermissions(Activity activity) {
    //         try {
    //             //检测是否有写的权限
    //             int permission = ActivityCompat.checkSelfPermission(activity,
    //                     "android.permission.WRITE_EXTERNAL_STORAGE");
    //             if (permission != PackageManager.PERMISSION_GRANTED) {
    //                 // 没有写的权限，去申请写的权限，会弹出对话框
    //                 ActivityCompat.requestPermissions(activity, PERMISSIONS_STORAGE,REQUEST_EXTERNAL_STORAGE);
    //             }
    //         } catch (Exception e) {
    //             e.printStackTrace();
    //         }
    //     }
    // }

}