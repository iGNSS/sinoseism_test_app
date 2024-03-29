package com.gxu.testapp.service;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.media.RingtoneManager;
import android.os.Build;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;

import com.gxu.testapp.R;
import com.gxu.testapp.event.AlertEvent;
import com.gxu.testapp.event.CloseWarningEvent;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

public class WarningService extends Service {

    private MediaPlayer mMediaPlayer1;
    private MediaPlayer mMediaPlayer2;

    @Override
    public void onCreate() {
        super.onCreate();
        EventBus.getDefault().register(this);
//        mMediaPlayer = MediaPlayer.create(getApplication(),
//                RingtoneManager.getActualDefaultRingtoneUri(getApplication(), RingtoneManager.TYPE_RINGTONE));
        mMediaPlayer1 = MediaPlayer.create(getApplication(), R.raw.emergency);
        mMediaPlayer1.setLooping(true);

        mMediaPlayer2 = MediaPlayer.create(getApplication(), R.raw.alarm_music);
        mMediaPlayer2.setLooping(true);
        //mMediaPlayer2=MediaPlayer.create(getApplicationContext(),R.raw.alarm_music);
        //mMediaPlayer2.setLooping(true);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
        stopForeground(true);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        String CHANNEL_ONE_ID = "CHANNEL_ONE_ID";
        String CHANNEL_ONE_NAME= "CHANNEL_ONE_ID";
        NotificationChannel notificationChannel= null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            notificationChannel = new NotificationChannel(CHANNEL_ONE_ID, CHANNEL_ONE_NAME, NotificationManager.IMPORTANCE_HIGH);
            notificationChannel.enableLights(true);
            notificationChannel.setLightColor(Color.RED);
            notificationChannel.setShowBadge(true);
            notificationChannel.setLockscreenVisibility(Notification.VISIBILITY_PUBLIC);
            NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
            manager.createNotificationChannel(notificationChannel);
        }

        Notification.Builder builder;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            builder = new Notification.Builder(this, CHANNEL_ONE_ID);
        }else {
            builder = new Notification.Builder(this);
        }
        builder.setLargeIcon(BitmapFactory.decodeResource(this.getResources(), R.drawable.mipush_notification))
                .setContentTitle("西大创新微震监测系统")
                .setSmallIcon(R.drawable.mipush_notification)
                .setContentText("正在后台运行")
                .setWhen(System.currentTimeMillis());
        Notification notification = builder.build();
        notification.defaults = Notification.DEFAULT_SOUND;
        startForeground(100, notification);

        return super.onStartCommand(intent, flags, startId);
    }

    @Subscribe
    public void onAlertEventArrived(AlertEvent event){
        int tmp_flag=event.getFlag();
        Log.d("tmp_flag", String.valueOf(tmp_flag));
        if(tmp_flag==1) {

            if (mMediaPlayer2.isPlaying()){
                mMediaPlayer2.pause();
                mMediaPlayer2.seekTo(0);
            }

            if (mMediaPlayer1 != null) {
                if (mMediaPlayer1.isPlaying()) {
                    mMediaPlayer1.pause();
                    mMediaPlayer1.seekTo(0);
                }
                mMediaPlayer1.start();
            }
        }
        else
        {
            if (mMediaPlayer1.isPlaying()){
                mMediaPlayer1.pause();
                mMediaPlayer1.seekTo(0);
            }

            if (mMediaPlayer2 != null) {
                if (mMediaPlayer2.isPlaying()) {
                    mMediaPlayer2.pause();
                    mMediaPlayer2.seekTo(0);
                }
                mMediaPlayer2.start();
            }
        }
    }

    @Subscribe
    public void onCloseWarningEventArrived(CloseWarningEvent event){
        if (mMediaPlayer1.isPlaying()){
            mMediaPlayer1.pause();
            mMediaPlayer1.seekTo(0);
        }
        if (mMediaPlayer2.isPlaying()){
            mMediaPlayer2.pause();
            mMediaPlayer2.seekTo(0);
        }
    }
}
