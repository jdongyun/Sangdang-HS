package com.dongyun.sangdang;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v4.app.NotificationCompat;

import org.json.JSONException;

/**
 * Created by 동윤 on 2015-01-02.
 */

public class BootReceiver extends BroadcastReceiver {
    NotificationManager notimgr;

    @Override
    public void onReceive(Context context, Intent intent) {
        Utils utils = new Utils(context);

        if (intent.getBooleanExtra("bool", false) || utils.checkNewNotice()) {

            Intent i = new Intent(context, Notices.class);
            PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, i, PendingIntent.FLAG_UPDATE_CURRENT);
            notimgr = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
            NotificationCompat.Builder builder = new NotificationCompat.Builder(context);
            String title = intent.getStringExtra("title");
            DevLog.i("BootRec", title);

            builder.setTicker("새로운 공지사항이 있습니다")
                    .setWhen(System.currentTimeMillis())
                    .setContentTitle(title)
                    .setContentText("새로운 공지사항 확인하러 가기")
                    .setContentIntent(pendingIntent)
                    .setSmallIcon(R.drawable.ic_launcher_small)
                    .setAutoCancel(true)
                    .setContentInfo("상당고등학교");
            notimgr.notify(831, builder.build());
        }

    }


}

