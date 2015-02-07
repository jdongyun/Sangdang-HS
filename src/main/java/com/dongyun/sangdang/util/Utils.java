package com.dongyun.sangdang.util;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import com.dongyun.sangdang.util.BootReceiver;
import com.dongyun.sangdang.util.DevLog;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.IOException;
import java.util.Calendar;

/**
 * Created by 동윤 on 2015-01-02.
 */
@SuppressWarnings("deprecation")
public class Utils {
    Context context = null;
    private boolean b = false;
    private static String URL = "http://www.sangdang.hs.kr/index.jsp?SCODE=S0000000206&mnu=M001006002";
    String titledata = "";

    public Utils(Context context) {
        this.context = context;
    }

    public boolean checkNewNotice() {

        new Thread(new Runnable() {

            @Override
            public void run() {
                try {

                    Document doc = Jsoup.connect(URL).get();
                    Element rawtitledata = doc.select("#m_mainList tbody tr td div.m_ltitle a").first(); // Get
                    Element rawnumdata = doc.select("td:eq(0)").first();
                    titledata = rawtitledata.attr("title");

                    SharedPreferences pref = context.getSharedPreferences("sangdang", Context.MODE_PRIVATE);
                    SharedPreferences.Editor edit = pref.edit();
                    String num = rawnumdata.text();

                    DevLog.i("Util", num);
                    int intnum = Integer.parseInt(num);
                    edit.putInt("notice_num_svr", intnum).apply();

                    final int noticenum = pref.getInt("notice_num_svr", 0);
                    final int savednum = pref.getInt("notice_num", 0);
                    if (noticenum != 0) {
                        if (savednum == 0) {
                            edit.putInt("notice_num", noticenum).apply();
                            setTrue(false);
                        } else {
                            if (noticenum > savednum) {
                                int line = noticenum - savednum;
                                edit.putInt("notice_num", noticenum).apply();
                                DevLog.i("Utils", Integer.toString(noticenum));
                                setTrue(true);
                                Intent in = new Intent(context, BootReceiver.class);
                                in.putExtra("bool", true);
                                in.putExtra("line", line);
                                in.putExtra("title", titledata);
                                context.sendBroadcast(in);
                                return;
                            } else {
                                setTrue(false);
                            }
                        }
                    }
                } catch (IOException e) {
                    DevLog.i("Utils", "IOException");
                    e.printStackTrace();

                }


            }
        }).start();
        return b;

    }

    public void registerAlarm() {

        Intent intent = new Intent(context, BootReceiver.class);
        PendingIntent sender = PendingIntent.getBroadcast(context, 0, intent, 0);

        AlarmManager am = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        am.cancel(sender);
        am.setRepeating(AlarmManager.RTC_WAKEUP, Calendar.getInstance().getTimeInMillis(), 3 * 60 * 60 * 1000, sender);
    }

    private boolean setTrue(boolean b) {
        this.b = b;
        return this.b;
    }

}
