package com.gxu.testapp.ui;

import android.Manifest;
import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.gxu.testapp.R;
import com.xiaomi.mipush.sdk.MiPushClient;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    Button gotowaveeven;
    Button gotopdfdiary;
    Button developing;
    public static boolean isPausePush = false;
    private Toolbar toolbar;

    private static final int REQUEST_EXTERNAL_STORAGE = 1;

    private static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        MiPushClient.resumePush(this, null);
        getPermission();
        gotowaveeven=findViewById(R.id.gotowaveeven);
        gotopdfdiary=findViewById(R.id.gotodiary);
        developing=findViewById(R.id.developing);
        developing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(),"功能待开发中",Toast.LENGTH_SHORT).show();
            }
        });

        toolbar = (Toolbar) findViewById(R.id.Main_toolbar);
        setSupportActionBar(toolbar);


        gotopdfdiary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent();
                intent.setClass(MainActivity.this,PdfDiary.class);
                startActivity(intent);
            }
        });

       gotowaveeven.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               Intent intent=new Intent();
               intent.setClass(MainActivity.this,SinoseimEventList.class);
               startActivity(intent);
           }
       });



    }


    public static int isAppMainActivity(Context context) {
        ActivityManager activityManager = (ActivityManager) context
                .getSystemService(Context.ACTIVITY_SERVICE);
        String packageName = "com.gxu.testapp";
        String bingMapMainActivityClassName = "com.gxu.testapp.ui.AppMainActivity";
        List<ActivityManager.RunningTaskInfo> tasksInfo = activityManager.getRunningTasks(1);
        if (tasksInfo.size() > 0) {
            ComponentName topConponent = tasksInfo.get(0).topActivity;
            if (packageName.equals(topConponent.getPackageName())) {
                if (topConponent.getClassName().equals(
                        bingMapMainActivityClassName)) {
                    Log.d("isAppMainActivity", "AppMainActivity在运行");
                    return 2;
                }
                Log.d("isAppMainActivity", "com.gxu.testapp前台运行");
                return 1;
            } else {
                Log.d("isAppMainActivity", "com.gxu.testapp后台运行");
                return 0;
            }
        }
        return 0;
    }


    public void getPermission(){
        int hasWriteContactsPermission = ContextCompat.checkSelfPermission(MainActivity.this,
                Manifest.permission.READ_EXTERNAL_STORAGE);
        if (hasWriteContactsPermission != PackageManager.PERMISSION_GRANTED) {
            if (!ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this,
                    Manifest.permission.READ_EXTERNAL_STORAGE)) {
                Log.d("yx","get permission");
                ActivityCompat.requestPermissions(MainActivity.this,
                        PERMISSIONS_STORAGE,REQUEST_EXTERNAL_STORAGE);
            }
            Log.d("yx","get permission2");
            ActivityCompat.requestPermissions(MainActivity.this,
                    PERMISSIONS_STORAGE,REQUEST_EXTERNAL_STORAGE);
        }
        Log.d("yx","wait for PERMISSION_GRANTED");
        while (( ContextCompat.checkSelfPermission(MainActivity.this,
                Manifest.permission.READ_EXTERNAL_STORAGE))!= PackageManager.PERMISSION_GRANTED) {
        }
        Log.d("yx","wait for PERMISSION_GRANTED finish");
    }

}