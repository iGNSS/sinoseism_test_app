package com.gxu.testapp.receiver;

import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;

import com.gxu.testapp.DemoApplication;
import com.gxu.testapp.event.AlertEvent;
import com.gxu.testapp.event.CloseWarningEvent;
import com.gxu.testapp.event.SinoseismEvent;
import com.gxu.testapp.ui.SinoseismInfoActivity;
import com.xiaomi.mipush.sdk.ErrorCode;
import com.xiaomi.mipush.sdk.MiPushClient;
import com.xiaomi.mipush.sdk.MiPushCommandMessage;
import com.xiaomi.mipush.sdk.MiPushMessage;
import com.xiaomi.mipush.sdk.PushMessageReceiver;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import static com.gxu.testapp.ui.MainActivity.isAppMainActivity;

public class DemoMessageReceiver extends PushMessageReceiver {

    private String mRegId;
    private long mResultCode = -1;
    private String mReason;
    private String mCommand;
    private String mMessage;
    private String mTopic;
    private String mAlias;
    private String mUserAccount;
    private String mStartTime;
    private String mEndTime;

    private MediaPlayer mMediaPlayer;

    @Override
    public void onReceivePassThroughMessage(Context context, MiPushMessage message) {
        mMessage = message.getContent();
        if(!TextUtils.isEmpty(message.getTopic())) {
            mTopic=message.getTopic();
        } else if(!TextUtils.isEmpty(message.getAlias())) {
            mAlias=message.getAlias();
        } else if(!TextUtils.isEmpty(message.getUserAccount())) {
            mUserAccount=message.getUserAccount();
        }
    }
    @Override
    public void onNotificationMessageClicked(Context context, MiPushMessage message) {
        mMessage = message.getContent();
        if(!TextUtils.isEmpty(message.getTopic())) {
            mTopic=message.getTopic();
        } else if(!TextUtils.isEmpty(message.getAlias())) {
            mAlias=message.getAlias();
        } else if(!TextUtils.isEmpty(message.getUserAccount())) {
            mUserAccount=message.getUserAccount();
        }
        EventBus.getDefault().post(new CloseWarningEvent());

        String tmp_data = message.getContent();
        try {
            JSONObject jsonObject=new JSONObject(tmp_data);
            String data=jsonObject.getJSONObject("sinoseismEventEntity").toString();
            Log.d("datatest", data);
            if (DemoApplication.isAppForeground()){
                if(isAppMainActivity(context.getApplicationContext())==2) {
                    EventBus.getDefault().post(new SinoseismEvent(data));
                }else{
                    Intent intent = new Intent(context, SinoseismInfoActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                    Bundle bundle = new Bundle();
                    bundle.putString("data",data);
                    intent.putExtras(bundle);
                    context.startActivity(intent);
                }
            }else {
                //如果APP在后台运行
                Intent intent=new Intent(context, SinoseismInfoActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_NEW_TASK);
                Bundle bundle = new Bundle();
                bundle.putString("data",data);
                intent.putExtras(bundle);
                context.startActivity(intent);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }



    }
    @Override
    public void onNotificationMessageArrived(Context context, MiPushMessage message) {
        mMessage = message.getContent();

        if(!TextUtils.isEmpty(message.getTopic())) {
            mTopic=message.getTopic();
        } else if(!TextUtils.isEmpty(message.getAlias())) {
            mAlias=message.getAlias();
        } else if(!TextUtils.isEmpty(message.getUserAccount())) {
            mUserAccount=message.getUserAccount();
        }

        String tmp_data = message.getContent();

        try {
            JSONObject jsonObject=new JSONObject(tmp_data);
            int tmp_flag=jsonObject.getInt("flag");
            EventBus.getDefault().post(new AlertEvent(tmp_flag));
            String data=jsonObject.getJSONObject("sinoseismEventEntity").toString();

            if (DemoApplication.isAppForeground()) {
                //如果APP在前台运行
                EventBus.getDefault().post(new SinoseismEvent(data));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }



    }
    @Override
    public void onCommandResult(Context context, MiPushCommandMessage message) {
        String command = message.getCommand();
        List<String> arguments = message.getCommandArguments();
        String cmdArg1 = ((arguments != null && arguments.size() > 0) ? arguments.get(0) : null);
        String cmdArg2 = ((arguments != null && arguments.size() > 1) ? arguments.get(1) : null);
        if (MiPushClient.COMMAND_REGISTER.equals(command)) {
            Log.d("cylog", "Register Error Code:" + message.getResultCode());
            if (message.getResultCode() == ErrorCode.SUCCESS) {
                mRegId = cmdArg1;
            }
        } else if (MiPushClient.COMMAND_SET_ALIAS.equals(command)) {
            Log.d("cylog", "Set Alias Error Code:" + message.getResultCode());
            if (message.getResultCode() == ErrorCode.SUCCESS) {
                mAlias = cmdArg1;
            }
        } else if (MiPushClient.COMMAND_UNSET_ALIAS.equals(command)) {
            Log.d("cylog", "Unset Alias Error Code:" + message.getResultCode());
            if (message.getResultCode() == ErrorCode.SUCCESS) {
                mAlias = cmdArg1;
            }
        } else if (MiPushClient.COMMAND_SUBSCRIBE_TOPIC.equals(command)) {
            Log.d("cylog", "Subscribe topic Error Code:" + message.getResultCode());
            if (message.getResultCode() == ErrorCode.SUCCESS) {
                mTopic = cmdArg1;
            }
        } else if (MiPushClient.COMMAND_UNSUBSCRIBE_TOPIC.equals(command)) {
            Log.d("cylog", "Unregister topic Error Code:" + message.getResultCode());
            if (message.getResultCode() == ErrorCode.SUCCESS) {
                mTopic = cmdArg1;
            }
        } else if (MiPushClient.COMMAND_SET_ACCEPT_TIME.equals(command)) {
            Log.d("cylog", "Set Accept Time Error Code:" + message.getResultCode());
            if (message.getResultCode() == ErrorCode.SUCCESS) {
                mStartTime = cmdArg1;
                mEndTime = cmdArg2;
            }
        }
    }
    @Override
    public void onReceiveRegisterResult(Context context, MiPushCommandMessage message) {
        String command = message.getCommand();
        List<String> arguments = message.getCommandArguments();
        String cmdArg1 = ((arguments != null && arguments.size() > 0) ? arguments.get(0) : null);
        String cmdArg2 = ((arguments != null && arguments.size() > 1) ? arguments.get(1) : null);
        if (MiPushClient.COMMAND_REGISTER.equals(command)) {
            if (message.getResultCode() == ErrorCode.SUCCESS) {
                Log.d("cylog", "[onReceiveRegisterResult]Register Error Code:" + message.getResultCode());
                mRegId = cmdArg1;
            }
        }
    }
}
