package com.dongyun.sangdang.fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.TextView;

import com.dongyun.sangdang.activity.Schedule;
import com.dongyun.sangdang.activity.SchoolEvent;
import com.dongyun.sangdang.util.DevLog;
import com.dongyun.sangdang.util.MealLoadHelper;
import com.dongyun.sangdang.R;
import com.dongyun.sangdang.activity.Meal;
import com.dongyun.sangdang.activity.NoticesActivity;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.w3c.dom.Text;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

import de.keyboardsurfer.android.widget.crouton.Crouton;
import de.keyboardsurfer.android.widget.crouton.Style;

public class Favorites extends Fragment {
    String mMealString;
    String mNoticesParentString;
    String mNoticesParentDate;
    String mEventString;
    String mEventDate;
    String mScheduleTitle;

    int AMorPM;
    int MONTH;
    int DAYofWEEK;
    int DAYofMONTH;

    String[] lunchstring = new String[7];
    String[] dinnerstring = new String[7];
    private ArrayList<String> titlearray_np; //가정통신문 제목
    private ArrayList<String> datearray_np; //가정통신문 올린 날짜
    private ArrayList<String> titlearray_ev; //학교 행사 제목
    private ArrayList<String> datearray_ev; //학교 행사 올린 날짜
    private ArrayList<String> titlearray_sc; //학사 일정 제목

    private TextView mMealView;
    private TextView mNParentsTitleView;
    private TextView mNParentsDateView;
    private TextView mEventTitleView;
    private TextView mEventsDateView;
    private TextView mScheduleTitleView;
    private TextView DAY;
    private TextView mMealTime;

    private SwipeRefreshLayout SRL;

    SharedPreferences sp;
    View view;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.activity_favorites, null);
        Calendar Cal = Calendar.getInstance();
        AMorPM = Cal.get(Calendar.AM_PM);
        MONTH = Cal.get(Calendar.MONTH);
        DAYofWEEK = Cal.get(Calendar.DAY_OF_WEEK);
        DAYofMONTH = Cal.get(Calendar.DAY_OF_MONTH);

        DevLog.d("DAYofMONTH", String.valueOf(Cal.get(Calendar.DAY_OF_MONTH)));

        mMealView = (TextView) view.findViewById(R.id.mealdata);
        mNParentsTitleView = (TextView) view.findViewById(R.id.notiparentdata);
        mNParentsDateView = (TextView) view.findViewById(R.id.notiparentdate);
        mEventTitleView = (TextView) view.findViewById(R.id.eventsdata);
        mEventsDateView = (TextView) view.findViewById(R.id.eventsdate);
        mScheduleTitleView = (TextView) view.findViewById(R.id.scheduledata);
        DAY = (TextView) view.findViewById(R.id.day);
        mMealTime = (TextView) view.findViewById(R.id.mealtime);

        SRL = (SwipeRefreshLayout)view.findViewById(R.id.swiperefresh);
        SRL.setColorSchemeColors(Color.rgb(231, 76, 60),
                Color.rgb(46, 204, 113), Color.rgb(41, 128, 185),
                Color.rgb(241, 196, 15));

        SRL.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if (!isNetworkConnected(getActivity())) {
                    Crouton.makeText(getActivity(), R.string.network_connection_warning, Style.INFO).show();
                    SRL.setRefreshing(false);
                }
                else{
                    if (sp.getBoolean("meal", true) && isNetworkConnected(getActivity())) getMeal();
                    if (sp.getBoolean("notices_parents", true) && isNetworkConnected(getActivity())) getNParents();
                    if (sp.getBoolean("schedule", true) && isNetworkConnected(getActivity())) getSchedule();
                    if (sp.getBoolean("event", true) && isNetworkConnected(getActivity())) getEvent();
                }
            }
        });

        View meal = view.findViewById(R.id.meal);
        View notices_parents = view.findViewById(R.id.notices_parents);
        View events = view.findViewById(R.id.events);
        View schedule = view.findViewById(R.id.schedule);
        sp = getActivity().getSharedPreferences("sangdang_pref", Context.MODE_PRIVATE);

        if (sp.getBoolean("meal", true)) {
                meal.setVisibility(View.VISIBLE);
                if (isNetworkConnected(getActivity())) {
                    getMeal();
                }
        } else {
            meal.setVisibility(View.GONE);
        }

        if (sp.getBoolean("notices_parents", true)) {
            notices_parents.setVisibility(View.VISIBLE);
            if (isNetworkConnected(getActivity())) {
                getNParents();
            }
        } else {
            notices_parents.setVisibility(View.GONE);
        }

        if (sp.getBoolean("event", true)) {
            events.setVisibility(View.VISIBLE);
            if (isNetworkConnected(getActivity())) {
                getEvent();
            }
        } else {
            events.setVisibility(View.GONE);
        }

        if (sp.getBoolean("schedule", true)) {
            schedule.setVisibility(View.VISIBLE);
            if (isNetworkConnected(getActivity())) {
                getSchedule();
            }
        } else {
            schedule.setVisibility(View.GONE);
        }

        meal.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), Meal.class);
                startActivity(intent);
            }
        });

        notices_parents.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), NoticesActivity.class);
                intent.putExtra("notices_parents", true);
                startActivity(intent);
            }
        });

        events.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), SchoolEvent.class);
                startActivity(intent);
            }
        });

        schedule.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), Schedule.class);
                startActivity(intent);
            }
        });

        if (isNetworkConnected(getActivity())) {
            //networkTask();
        }
        return view;
    }

    void networkTask() {

        final Handler mHandler = new Handler();
        new Thread() {

            public void run() {
                mHandler.post(new Runnable() {

                    public void run() {
                    }
                });
                try {
                    lunchstring = MealLoadHelper.getMeal("cbe.go.kr", "M100000159", "4", "04", "2"); //Get Lunch Menu Date
                    dinnerstring = MealLoadHelper.getMeal("cbe.go.kr", "M100000159", "4", "04", "3"); //Get Dinner Menu Date
                } catch (Exception e) {
                }


                try {
                    titlearray_np = new ArrayList<String>();
                    datearray_np = new ArrayList<String>();
                    Document doc = Jsoup.connect("http://www.sangdang.hs.kr/index.jsp?SCODE=S0000000206&mnu=M001006002").get();
                    Elements rawdata = doc.select("#m_mainList tbody tr td div.m_ltitle a");
                    Elements rawdatedata = doc.select("td:eq(3)");
                    String titlestring = rawdata.toString();
                    DevLog.i("Notices", "Parsed Strings" + titlestring);

                    for (Element el : rawdata) {
                        String titledata = el.attr("title");
                        titlearray_np.add(titledata); // add value to ArrayList
                    }

                    for (Element el : rawdatedata) {
                        String datedata = el.text();
                        DevLog.d("Date", el.text());
                        datearray_np.add(datedata);
                    }

                    DevLog.i("Notices", "Parsed Array Strings" + titlearray_np);


                } catch (IOException e) {
                    e.printStackTrace();

                }


                try {
                    titlearray_ev = new ArrayList<String>();
                    datearray_ev = new ArrayList<String>();
                    Document doc = Jsoup.connect("http://sangdang.hs.kr/index.jsp?SCODE=S0000000206&mnu=M001006001").get();
                    Elements rawdata = doc.select("#m_mainList tbody tr td div.m_ltitle a");
                    Elements rawdatedata = doc.select("td:eq(3)");
                    String titlestring = rawdata.toString();
                    DevLog.i("Notices", "Parsed Strings" + titlestring);

                    for (Element el : rawdata) {
                        String titledata = el.attr("title");
                        titlearray_ev.add(titledata); // add value to ArrayList
                    }

                    for (Element el : rawdatedata) {
                        String datedata = el.text();
                        DevLog.d("Date", el.text());
                        datearray_ev.add(datedata);
                    }

                    DevLog.i("Notices", "Parsed Array Strings" + titlearray_np);


                } catch (IOException e) {
                    e.printStackTrace();

                }


                try {
                    titlearray_sc = new ArrayList<String>();
                    Document doc = Jsoup.connect("http://www.sangdang.hs.kr/index.jsp?SCODE=S0000000206&mnu=M001001005").get();
                    Elements rawscheduledata = doc.select("#s_menuContent #m_content table tbody tr td div.fwn"); // Get

                    for (Element el : rawscheduledata) {
                        String scheduledata = el.text();
                        titlearray_sc.add(scheduledata);
                    }
                    DevLog.d("Schedule", titlearray_sc.toString());
                } catch (IOException e) {
                    e.printStackTrace();

                }


                mHandler.post(new Runnable() {
                    public void run() {
                        if (AMorPM == Calendar.AM) {
                            mMealString = lunchstring[DAYofWEEK - 1];
                            mMealTime.setText(R.string.lunch);
                            mMealView.setText("hihi");
                        } else {
                            mMealString = dinnerstring[DAYofWEEK - 1] + "\n";
                            mMealTime.setText(R.string.dinner);
                            mMealView.setText("hihi");
                        }
                        try {
                            mNoticesParentString = titlearray_np.get(0);
                            mNoticesParentDate = datearray_np.get(0);
                            mEventString = titlearray_ev.get(0);
                            mEventDate = datearray_ev.get(0);
                            Date d = new Date();
                            SimpleDateFormat sdf = new SimpleDateFormat("dd");
                            mScheduleTitle = titlearray_sc.get(Integer.parseInt(sdf.format(d)) - 1);
                            if (mMealString.trim().equals("")) {
                                mMealString = getResources().getString(R.string.mealnone);
                            }
                        } catch (Exception e) {
                            mNoticesParentString = getResources().getString(R.string.error);
                            mEventString = getResources().getString(R.string.error);
                            mScheduleTitle = getResources().getString(R.string.error);
                        }


                        mHandler.sendEmptyMessage(0);
                        setContentData();
                    }
                });

            }
        }.start();


    }
    public void getMeal() {
        SRL.setRefreshing(true);
        final Handler mHandler = new Handler();
        new Thread() {

            public void run() {
                try {
                    lunchstring = MealLoadHelper.getMeal("cbe.go.kr", "M100000159", "4", "04", "2"); //Get Lunch Menu Date
                    dinnerstring = MealLoadHelper.getMeal("cbe.go.kr", "M100000159", "4", "04", "3"); //Get Dinner Menu Date
                } catch (Exception e) {
                    e.printStackTrace();
                }

                mHandler.post(new Runnable() {
                    public void run() {
                        if (AMorPM == Calendar.AM) {
                            mMealString = lunchstring[DAYofWEEK - 1];
                            mMealTime.setText(R.string.lunch);
                            mMealView.setText("hihi");
                        } else {
                            mMealString = dinnerstring[DAYofWEEK - 1] + "\n";
                            mMealTime.setText(R.string.dinner);
                            mMealView.setText("hihi");
                        }
                        if (mMealString.trim().equals("")) {
                            mMealString = getResources().getString(R.string.mealnone);
                        }

                        SRL.setRefreshing(false);
                        mHandler.sendEmptyMessage(0);
                        TimeZone kst = TimeZone.getTimeZone("KST");
                        Calendar cal = Calendar.getInstance(kst);
                        mMealView.setText(mMealString);
                        DAY.setText((cal.get(Calendar.MONTH) + 1) + "월 "
                                + cal.get(Calendar.DATE) + "일 ");
                    }
                });

            }
        }.start();


    }

    void getNParents() {
        SRL.setRefreshing(true);

        final Handler mHandler = new Handler();
        new Thread() {

            public void run() {
                try {
                    titlearray_np = new ArrayList<String>();
                    datearray_np = new ArrayList<String>();
                    Document doc = Jsoup.connect("http://www.sangdang.hs.kr/index.jsp?SCODE=S0000000206&mnu=M001006002").get();
                    Elements rawdata = doc.select("#m_mainList tbody tr td div.m_ltitle a");
                    Elements rawdatedata = doc.select("td:eq(3)");
                    String titlestring = rawdata.toString();
                    DevLog.i("Notices", "Parsed Strings" + titlestring);

                    for (Element el : rawdata) {
                        String titledata = el.attr("title");
                        titlearray_np.add(titledata); // add value to ArrayList
                    }

                    for (Element el : rawdatedata) {
                        String datedata = el.text();
                        DevLog.d("Date", el.text());
                        datearray_np.add(datedata);
                    }

                    DevLog.i("Notices", "Parsed Array Strings" + titlearray_np);


                } catch (IOException e) {
                    e.printStackTrace();

                }


                mHandler.post(new Runnable() {
                    public void run() {
                        try {
                            mNoticesParentString = titlearray_np.get(0);
                            mNoticesParentDate = datearray_np.get(0);
                        } catch (Exception e) {
                            mNoticesParentString = getResources().getString(R.string.error);
                        }

                        mHandler.sendEmptyMessage(0);
                        SRL.setRefreshing(false);
                        mNParentsTitleView.setText(mNoticesParentString);
                        mNParentsDateView.setText("등록일 : " + mNoticesParentDate);
                    }
                });

            }
        }.start();
    }


    void getEvent() {
        SRL.setRefreshing(true);

        final Handler mHandler = new Handler();
        new Thread() {

            public void run() {


                try {
                    titlearray_ev = new ArrayList<String>();
                    datearray_ev = new ArrayList<String>();
                    Document doc = Jsoup.connect("http://sangdang.hs.kr/index.jsp?SCODE=S0000000206&mnu=M001006001").get();
                    Elements rawdata = doc.select("#m_mainList tbody tr td div.m_ltitle a");
                    Elements rawdatedata = doc.select("td:eq(3)");
                    String titlestring = rawdata.toString();
                    DevLog.i("Notices", "Parsed Strings" + titlestring);

                    for (Element el : rawdata) {
                        String titledata = el.attr("title");
                        titlearray_ev.add(titledata); // add value to ArrayList
                    }

                    for (Element el : rawdatedata) {
                        String datedata = el.text();
                        DevLog.d("Date", el.text());
                        datearray_ev.add(datedata);
                    }

                    DevLog.i("Notices", "Parsed Array Strings" + titlearray_np);


                } catch (IOException e) {
                    e.printStackTrace();

                }



                mHandler.post(new Runnable() {
                    public void run() {
                        try {
                            mEventString = titlearray_ev.get(0);
                            mEventDate = datearray_ev.get(0);
                        } catch (Exception e) {
                            mEventString = getResources().getString(R.string.error);
                        }


                        mHandler.sendEmptyMessage(0);
                        SRL.setRefreshing(false);
                        mEventTitleView.setText(mEventString);
                        mEventsDateView.setText("등록일 : " + mEventDate);
                    }
                });

            }
        }.start();
    }

    void getSchedule() {
        SRL.setRefreshing(true);

        final Handler mHandler = new Handler();
        new Thread() {

            public void run() {


                try {
                    titlearray_sc = new ArrayList<String>();
                    Document doc = Jsoup.connect("http://www.sangdang.hs.kr/index.jsp?SCODE=S0000000206&mnu=M001001005").get();
                    Elements rawscheduledata = doc.select("#s_menuContent #m_content table tbody tr td div.fwn"); // Get

                    for (Element el : rawscheduledata) {
                        String scheduledata = el.text();
                        titlearray_sc.add(scheduledata);
                    }
                    DevLog.d("Schedule", titlearray_sc.toString());
                } catch (IOException e) {
                    e.printStackTrace();

                }


                mHandler.post(new Runnable() {
                    public void run() {
                        try {
                            Date d = new Date();
                            SimpleDateFormat sdf = new SimpleDateFormat("dd");
                            mScheduleTitle = titlearray_sc.get(Integer.parseInt(sdf.format(d)) - 1);
                        } catch (Exception e) {
                            mScheduleTitle = getResources().getString(R.string.error);
                        }

                        mHandler.sendEmptyMessage(0);
                        SRL.setRefreshing(false);
                        if (mScheduleTitle.equals("")) {
                            mScheduleTitleView.setText("오늘의 일정이 없습니다.");
                        } else {
                            mScheduleTitleView.setText("오늘의 일정 : " + mScheduleTitle);
                        }
                    }
                });

            }
        }.start();

    }



    @Override
    public void onResume() {
        super.onResume();
        View meal = view.findViewById(R.id.meal);
        View notices_parents = view.findViewById(R.id.notices_parents);
        View events = view.findViewById(R.id.events);
        View schedule = view.findViewById(R.id.schedule);

        if (sp.getBoolean("meal", true)) {
            meal.setVisibility(View.VISIBLE);
            if (isNetworkConnected(getActivity())) {
                getMeal();
            }
        } else {
            meal.setVisibility(View.GONE);
        }

        if (sp.getBoolean("notices_parents", true)) {
            notices_parents.setVisibility(View.VISIBLE);
            if (isNetworkConnected(getActivity())) {
                getNParents();
            }
        } else {
            notices_parents.setVisibility(View.GONE);
        }

        if (sp.getBoolean("event", true)) {
            events.setVisibility(View.VISIBLE);
            if (isNetworkConnected(getActivity())) {
                getEvent();
            }
        } else {
            events.setVisibility(View.GONE);
        }

        if (sp.getBoolean("schedule", true)) {
            schedule.setVisibility(View.VISIBLE);
            if (isNetworkConnected(getActivity())) {
                getSchedule();
            }
        } else {
            schedule.setVisibility(View.GONE);
        }
    }

    void setContentData() {






    }

    // 인터넷 연결 상태 체크
    public boolean isNetworkConnected(Context context) {
        boolean isConnected = false;

        ConnectivityManager manager = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo mobile = manager
                .getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
        NetworkInfo wifi = manager
                .getNetworkInfo(ConnectivityManager.TYPE_WIFI);

        if (mobile.isConnected() || wifi.isConnected()) {
            isConnected = true;
        } else {
            isConnected = false;
        }
        return isConnected;
    }


}