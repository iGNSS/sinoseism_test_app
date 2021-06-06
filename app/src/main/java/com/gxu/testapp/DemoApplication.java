package com.gxu.testapp;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.AlertDialog;
import android.app.Application;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.Process;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.gxu.testapp.event.AlertEvent;
import com.gxu.testapp.event.CloseWarningEvent;
import com.gxu.testapp.service.WarningService;
import com.gxu.testapp.ui.MainActivity;
import com.xiaomi.channel.commonutils.logger.LoggerInterface;
import com.xiaomi.mipush.sdk.Logger;
import com.xiaomi.mipush.sdk.MiPushClient;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.List;

public class DemoApplication extends Application implements Application.ActivityLifecycleCallbacks{

    public static final String APP_ID = "2882303761519920493";
    public static final String APP_KEY = "5111992054493";
    public static final String TAG = "com.gxu.testapp";

    private static int activityCount = 0;
    private Context mContext;
    private Handler mHandler;

    @Override
    public void onCreate() {
        super.onCreate();
        EventBus.getDefault().register(this);
        registerActivityLifecycleCallbacks(this);
        mHandler = new Handler(getMainLooper());
        //初始化push推送服务
        if(shouldInit()) {
            MiPushClient.registerPush(this, APP_ID, APP_KEY);
            MiPushClient.subscribe(this, "gxu-innovation-sinoseism", null);
        }
        startService(new Intent(this, WarningService.class));
        //打开Log
        LoggerInterface newLogger = new LoggerInterface() {

            @Override
            public void setTag(String tag) {
                // ignore
            }

            @Override
            public void log(String content, Throwable t) {
                Log.d(TAG, content, t);
            }

            @Override
            public void log(String content) {
                Log.d(TAG, content);
            }
        };
        Logger.setLogger(this, newLogger);
    }

    private boolean shouldInit() {
        ActivityManager am = ((ActivityManager) getSystemService(Context.ACTIVITY_SERVICE));
        List<ActivityManager.RunningAppProcessInfo> processInfos = am.getRunningAppProcesses();
        String mainProcessName = getApplicationInfo().processName;
        int myPid = Process.myPid();
        for (ActivityManager.RunningAppProcessInfo info : processInfos) {
            if (info.pid == myPid && mainProcessName.equals(info.processName)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void onActivityCreated(@NonNull Activity activity, @Nullable Bundle savedInstanceState) {
        Log.d(TAG, "[onActivityCreated]");
        if (activity.getParent() != null){
            mContext = activity.getParent();
        }else {
            mContext = activity;
        }
    }

    @Override
    public void onActivityStarted(@NonNull Activity activity) {
        Log.d(TAG, "[onActivityStarted]");
        if (activity.getParent() != null){
            mContext = activity.getParent();
        }else {
            mContext = activity;
        }
    }

    @Override
    public void onActivityResumed(@NonNull Activity activity) {
        activityCount++;
        Log.d(TAG, "[onActivityResumed]");
        if (activity.getParent() != null){
            mContext = activity.getParent();
        }else {
            mContext = activity;
        }
    }

    @Override
    public void onActivityPaused(@NonNull Activity activity) {
        Log.d(TAG, "[onActivityPaused]");
    }

    @Override
    public void onActivityStopped(@NonNull Activity activity) {
        Log.d(TAG, "[onActivityStopped]");
        activityCount--;
    }

    @Override
    public void onActivitySaveInstanceState(@NonNull Activity activity, @NonNull Bundle outState) {
        Log.d(TAG, "[onActivitySaveInstanceState]");
    }

    @Override
    public void onActivityDestroyed(@NonNull Activity activity) {
        Log.d(TAG, "[onActivityDestroyed]");
    }

    public static boolean isAppForeground(){
        return activityCount > 0;
    }

    @Subscribe
    public void onAlertEventArrived(AlertEvent event){
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                builder.setTitle("微震预警")
                        .setMessage("监测地点发生超过阈值的微震事件，请核实！")
                        .setPositiveButton("已清楚，关闭警告声", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                EventBus.getDefault().post(new CloseWarningEvent());
                            }
                        }).setCancelable(false);
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
            }
        });

    }
}
