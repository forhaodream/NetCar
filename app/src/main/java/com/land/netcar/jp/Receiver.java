package com.land.netcar.jp;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import cn.jpush.android.api.JPushInterface;

/**
 * ----------Dragon be here!----------/
 * 　　　┏┓　　　┏┓
 * 　　┏┛┻━━━┛┻┓
 * 　　┃　　　　　　　┃
 * 　　┃　　　━　　　┃
 * 　　┃　┳┛　┗┳　┃
 * 　　┃　　　　　　　┃
 * 　　┃　　　┻　　　┃
 * 　　┃　　　　　　　┃
 * 　　┗━┓　　　┏━┛
 * 　　　　┃　　　┃神兽保佑
 * 　　　　┃　　　┃代码无BUG！
 * 　　　　┃　　　┗━━━┓
 * 　　　　┃　　　　　　　┣┓
 * 　　　　┃　　　　　　　┏┛
 * 　　　　┗┓┓┏━┳┓┏┛
 * 　　　　　┃┫┫　┃┫┫
 * 　　　　　┗┻┛　┗┻┛
 * ━━━━━━神兽出没━━━━━━
 * 作者：wangwei
 * MessageReceiver 创建于 on 2017/11/13 15:10
 */

public class Receiver extends BroadcastReceiver {
    private static final String TAG = "JPush";

    @Override
    public void onReceive(Context context, Intent intent) {
        try {

            Bundle bundle = intent.getExtras();

//            Log.d(TAG, "[MyReceiver] onReceive - " + intent.getAction() + ", extras: " + printBundle(bundle));

            if (JPushInterface.ACTION_REGISTRATION_ID.equals(intent.getAction())) {

                String regId = bundle.getString(JPushInterface.EXTRA_REGISTRATION_ID);

                Log.d(TAG, "[MyReceiver]接收Registration Id : " + regId);

//send the Registration Id to your server...

            } else if (JPushInterface.ACTION_MESSAGE_RECEIVED.equals(intent.getAction())) {

                Log.d(TAG, "[MyReceiver]接收到推送下来的自定义消息: " + bundle.getString(JPushInterface.EXTRA_MESSAGE));

                processCustomMessage(context, bundle);

            } else if (JPushInterface.ACTION_NOTIFICATION_RECEIVED.equals(intent.getAction())) {

                Log.d(TAG, "[MyReceiver]接收到推送下来的通知");

                int notifactionId = bundle.getInt(JPushInterface.EXTRA_NOTIFICATION_ID);

//                Log.d(TAG, "[MyReceiver]接收到推送下来的通知的ID: " + notifactionId);

                processNotification(context, bundle);

            } else if (JPushInterface.ACTION_NOTIFICATION_OPENED.equals(intent.getAction())) {

                Log.d(TAG, "[MyReceiver]用户点击打开了通知");

                processNotificationTitle(context, bundle);

            } else if (JPushInterface.ACTION_RICHPUSH_CALLBACK.equals(intent.getAction())) {

                Log.d(TAG, "[MyReceiver]用户收到到RICH PUSH CALLBACK: " + bundle.getString(JPushInterface.EXTRA_EXTRA));

//在这里根据JPushInterface.EXTRA_EXTRA的内容处理代码，比如打开新的Activity， 打开一个网页等..

            } else if (JPushInterface.ACTION_CONNECTION_CHANGE.equals(intent.getAction())) {

                boolean connected = intent.getBooleanExtra(JPushInterface.EXTRA_CONNECTION_CHANGE, false);

                Log.w(TAG, "[MyReceiver]" + intent.getAction() + " connected state change to " + connected);

            } else {

                Log.d(TAG, "[MyReceiver] Unhandled intent - " + intent.getAction());

            }

        } catch (Exception e) {

        }

    }
    //send msg to MainActivity

    private void processCustomMessage(Context context, Bundle bundle) {

        if (JPushActivity.isForeground) {

            String message = bundle.getString(JPushInterface.EXTRA_MESSAGE);

            String extras = bundle.getString(JPushInterface.EXTRA_EXTRA);

            Intent msgIntent = new Intent(JPushActivity.MESSAGE_RECEIVED_ACTION);

            msgIntent.putExtra(JPushActivity.KEY_MESSAGE, message);

            if (!extras.isEmpty()) {

                try {

                    JSONObject extraJson = new JSONObject(extras);

                    if (extraJson.length() > 0) {

                        msgIntent.putExtra(JPushActivity.KEY_EXTRAS, extras);

                    }

                } catch (JSONException e) {

                }

            }

            LocalBroadcastManager.getInstance(context).sendBroadcast(msgIntent);

        }

    }
    //send msg to MainActivity

    private void processNotification(Context context, Bundle bundle) {

        if (JPushActivity.isForeground) {

            String extras = bundle.getString(JPushInterface.EXTRA_EXTRA);

            String notification = bundle.getString(JPushInterface.EXTRA_ALERT);

            Intent msgIntent = new Intent(JPushActivity.MESSAGE_RECEIVED_ACTION);

            msgIntent.putExtra(JPushActivity.KEY_MESSAGE, notification);

            if (!extras.isEmpty()) {

                try {

                    JSONObject extraJson = new JSONObject(extras);

                    if (extraJson.length() > 0) {

                        msgIntent.putExtra(JPushActivity.KEY_EXTRAS, extras);

                    }

                } catch (JSONException e) {

                }

            }

            LocalBroadcastManager.getInstance(context).sendBroadcast(msgIntent);

        }

    }

    private void processNotificationTitle(Context context, Bundle bundle) {

        if (JPushActivity.isForeground) {

//进入下一个Activity前的处理

//            Intent i = new Intent(context, TestActivity.class);
//
//            i.putExtras(bundle);
//
////i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//
//            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
//
//            context.startActivity(i);

//下一个Activity的处理

//            Intent intent = getIntent();
//
//            if (null != intent) {
//
//                Bundle bundle = getIntent().getExtras();
//
//                String title = bundle.getString(JPushInterface.EXTRA_NOTIFICATION_TITLE);
//
//                String content = bundle.getString(JPushInterface.EXTRA_ALERT);
//
//            }

        }

    }


}
