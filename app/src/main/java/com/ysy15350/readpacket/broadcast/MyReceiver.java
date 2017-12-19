package com.ysy15350.readpacket.broadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.ysy15350.readpacket.MainActivity;

import java.util.ArrayList;
import java.util.List;

import cn.jpush.android.api.JPushInterface;

/**
 * Created by yangshiyou on 2017/9/27.
 */

public class MyReceiver extends BroadcastReceiver {

    private static final String TAG = "MyReceiver";


    @Override
    public void onReceive(Context context, Intent intent) {


        Bundle bundle = intent.getExtras();

        Log.d(TAG, "[MyReceiver] onReceive - " + intent.getAction()
                + ", extras: " + printBundle(bundle));

        Log.d(TAG, "---------------------------onReceive: ---------------------------");

        if (JPushInterface.ACTION_REGISTRATION_ID.equals(intent.getAction())) {
            String regId = bundle
                    .getString(JPushInterface.EXTRA_REGISTRATION_ID);
            Log.d(TAG, "[MyReceiver] 接收Registration Id : " + regId);
            // send the Registration Id to your server...

        } else if (JPushInterface.ACTION_MESSAGE_RECEIVED.equals(intent
                .getAction())) {

            Log.d(TAG,
                    "[MyReceiver] 接收到推送下来的自定义消息: "
                            + bundle.getString(JPushInterface.EXTRA_MESSAGE));

            processCustomMessage(context, intent);

        } else if (JPushInterface.ACTION_NOTIFICATION_RECEIVED.equals(intent
                .getAction())) {
            Log.d(TAG, "[MyReceiver] 接收到推送下来的通知");

            int notifactionId = bundle
                    .getInt(JPushInterface.EXTRA_NOTIFICATION_ID);
            Log.d(TAG, "[MyReceiver] 接收到推送下来的通知的ID: " + notifactionId);


            //processNotification(context, intent);


        } else if (JPushInterface.ACTION_NOTIFICATION_OPENED.equals(intent
                .getAction())) {
            Log.d(TAG, "[MyReceiver] 用户点击打开了通知");

            String activityName = "MainActivity";
            String param = "";

            // 打开自定义的Activity
            Intent i = new Intent(context, MainActivity.class);

            String extras = bundle.getString(JPushInterface.EXTRA_EXTRA);
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

            context.startActivity(i);


        } else if (JPushInterface.ACTION_RICHPUSH_CALLBACK.equals(intent
                .getAction())) {

            Log.d(TAG,
                    "[MyReceiver] 用户收到到RICH PUSH CALLBACK: "
                            + bundle.getString(JPushInterface.EXTRA_EXTRA));
            // 在这里根据 JPushInterface.EXTRA_EXTRA 的内容处理代码，比如打开新的Activity，
            // 打开一个网页等..

        } else if (JPushInterface.ACTION_CONNECTION_CHANGE.equals(intent
                .getAction())) {
            boolean connected = intent.getBooleanExtra(
                    JPushInterface.EXTRA_CONNECTION_CHANGE, false);
            Log.w(TAG, "[MyReceiver]" + intent.getAction()
                    + " connected state change to " + connected);
        } else {
            Log.d(TAG, "[MyReceiver] Unhandled intent - " + intent.getAction());
        }
    }

    // 打印所有的 intent extra 数据
    private static String printBundle(Bundle bundle) {
        StringBuilder sb = new StringBuilder();
        for (String key : bundle.keySet()) {
            if (key.equals(JPushInterface.EXTRA_NOTIFICATION_ID)) {
                sb.append("\nkey:" + key + ", value:" + bundle.getInt(key));
            } else if (key.equals(JPushInterface.EXTRA_CONNECTION_CHANGE)) {
                sb.append("\nkey:" + key + ", value:" + bundle.getBoolean(key));
            } else {
                sb.append("\nkey:" + key + ", value:" + bundle.getString(key));
            }
        }
        return sb.toString();
    }


    /**
     * 处理通知消息
     *
     * @param context
     * @param intent
     */
    private void processNotification(Context context, Intent intent) {
        Bundle bundle = intent.getExtras();

        Log.d(TAG, "processCustomMessage: --------------------------------------------通知消息--------------------------------------------");

        for (String key : bundle.keySet()) {

            Log.d(TAG, "processNotification() called with: key = [" + key + "], content = [" + bundle.getString(key) + "]");

            if (key.equals(JPushInterface.EXTRA_NOTIFICATION_ID)) {
                //sb.append("\nkey:" + key + ", value:" + bundle.getInt(key));
            } else if (key.equals(JPushInterface.EXTRA_CONNECTION_CHANGE)) {
                //sb.append("\nkey:" + key + ", value:" + bundle.getBoolean(key));
            } else if (key.equals(JPushInterface.EXTRA_EXTRA)) {
                String content = bundle.getString(key);//通知消息内容

            } else if (key.equals(JPushInterface.EXTRA_ALERT)) {
                String content = bundle.getString(key);//通知消息内容
                //printText(content);

            } else {
                //sb.append("\nkey:" + key + ", value:" + bundle.getString(key));
            }
        }

        Log.d(TAG, "processCustomMessage: --------------------------------------------通知消息end--------------------------------------------");
    }


    /**
     * 处理接收到的自定义消息
     *
     * @param context
     * @param intent
     */
    private void processCustomMessage(Context context, Intent intent) {

        Bundle bundle = intent.getExtras();

        String message = bundle.getString(JPushInterface.EXTRA_MESSAGE);
        String extras = bundle.getString(JPushInterface.EXTRA_EXTRA);

        Log.d(TAG, "processCustomMessage: --------------------------------------------自定义消息--------------------------------------------");
        Log.d(TAG, "processCustomMessage: message=" + message);
        Log.d(TAG, "processCustomMessage: extras=" + extras);


        Log.d(TAG, "processCustomMessage: --------------------------------------------自定义消息end--------------------------------------------");


        if (listeners != null && !listeners.isEmpty()) {
            for (ReceiverListener listener :
                    listeners) {
                listener.test(1, message);
            }
        }
    }


    private static List<ReceiverListener> listeners = new ArrayList<>();

    public static void addReceiverListener(ReceiverListener listener) {
        listeners.add(listener);
    }

    /**
     * 通知监听
     */
    public interface ReceiverListener {
        public void test(int type, String message);

    }


}