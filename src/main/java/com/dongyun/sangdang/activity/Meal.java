package com.dongyun.sangdang.activity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dongyun.sangdang.util.DevLog;
import com.dongyun.sangdang.util.MealLoadHelper;
import com.dongyun.sangdang.R;
import com.dongyun.sangdang.meal.FridayMeal;
import com.dongyun.sangdang.meal.MondayMeal;
import com.dongyun.sangdang.meal.ThursdayMeal;
import com.dongyun.sangdang.meal.TuesdayMeal;
import com.dongyun.sangdang.meal.WednesdayMeal;
import com.gc.materialdesign.widgets.Dialog;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class Meal extends ActionBarActivity implements ActionBar.TabListener {

    SectionsPagerAdapter mSectionsPagerAdapter;
    ViewPager mViewPager;
    String[] bfstring = new String[7];
    String[] lunchstring = new String[7];
    String[] dinnerstring = new String[7];
    String[] bfkcalstring = new String[7];
    String[] lunchkcalstring = new String[7];
    String[] dinnerkcalstring = new String[7];
    String[] mealpos = {"월요일", "화요일", "수요일", "목요일", "금요일"};
    ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meal);

        if (!isNetworkConnected(this)) {
            new AlertDialog.Builder(this)
                    .setIcon(R.drawable.ic_error)
                    .setTitle("네트워크 연결")
                    .setMessage("\n네트워크 연결 상태 확인 후 다시 시도해 주십시요\n")
                    .setPositiveButton("확인",
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog,
                                                    int which) {
                                    finish();
                                }
                            }).show();
            DevLog.d("Network", "Network Connection Fail");
        } else {

            final ActionBar actionBar = getSupportActionBar();
            actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
            mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());
            mViewPager = (ViewPager) findViewById(R.id.pager);
            mViewPager.setAdapter(mSectionsPagerAdapter);
            mViewPager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
                @Override
                public void onPageSelected(int position) {
                    actionBar.setSelectedNavigationItem(position);
                }
            });
            loadMealTask();

            for (int i = 0; i < mSectionsPagerAdapter.getCount(); i++) {
                actionBar.addTab(
                        actionBar.newTab()
                                .setText(mSectionsPagerAdapter.getPageTitle(i))
                                .setTabListener(this));
            }
            actionBar.setDisplayHomeAsUpEnabled(true);
            Calendar Cal = Calendar.getInstance();

            int TODAY = Cal.get(Calendar.DAY_OF_WEEK);
            DevLog.d("TODAY", "TODAY is -" + TODAY);
            switch (TODAY) {
                case 0:
                    actionBar.setSelectedNavigationItem(0);
                    break;
                case 1:
                    actionBar.setSelectedNavigationItem(0);
                    break;
                case 3:
                    actionBar.setSelectedNavigationItem(1);
                    break;
                case 4:
                    actionBar.setSelectedNavigationItem(2);
                    break;
                case 5:
                    actionBar.setSelectedNavigationItem(3);
                    break;
                case 6:
                    getSupportActionBar().setSelectedNavigationItem(4);
                    break;
                case 7:
                    getSupportActionBar().setSelectedNavigationItem(4);

            }
            findViewById(R.id.mealinfo).setOnClickListener(
                    new View.OnClickListener() {

                        @Override
                        public void onClick(View arg0) {
                            Dialog dialog = new Dialog(Meal.this, "알레르기 정보",
                                    "①난류\n②우유\n③메밀\n④땅콩\n⑤대두\n⑥밀\n⑦고등어\n⑧게\n⑨새우\n⑩돼지고기\n⑪복숭아\n⑫토마토\n⑬아황산염");
                            dialog.show();
                        }
                    });
        }

    }


    @Override
    public void onTabSelected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
        mViewPager.setCurrentItem(tab.getPosition());
    }

    @Override
    public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
    }

    @Override
    public void onTabReselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
    }

    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {

            Fragment frag = PlaceholderFragment.newInstance(position + 1);
            switch (position) {
                case 0:
                    frag = MondayMeal.newInstance(0);
                    break;
                case 1:
                    frag = TuesdayMeal.newInstance(1);
                    break;
                case 2:
                    frag = WednesdayMeal.newInstance(2);
                    break;
                case 3:
                    frag = ThursdayMeal.newInstance(3);
                    break;
                case 4:
                    frag = FridayMeal.newInstance(4);
                    break;
            }
            return frag;
        }

        @Override
        public int getCount() {
            return 5;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            Locale l = Locale.getDefault();
            return mealpos[position].toUpperCase(l);
        }
    }

    public static class PlaceholderFragment extends Fragment {

        private static final String ARG_SECTION_NUMBER = "section_number";

        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_day_meal, container, false);
            return rootView;
        }
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

    // 하드웨어 뒤로가기버튼 이벤트 설정.
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        switch (keyCode) {
            // 하드웨어 뒤로가기 버튼에 따른 이벤트 설정
            case KeyEvent.KEYCODE_BACK:
                android.app.FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.commit();
        }
        return super.onKeyDown(keyCode, event);
    }

    private void loadMealTask(){
        final Handler mHandler = new Handler();

        dialog = new ProgressDialog(this);
        dialog.setMessage("잠시만 기다려 주세요..");
        dialog.setIndeterminate(true);
        dialog.setCancelable(false);
        dialog.show();

        SharedPreferences pref = getApplicationContext().getSharedPreferences("sangdang", Context.MODE_PRIVATE);
        final SharedPreferences.Editor edit = pref.edit();
        DateFormat df = new SimpleDateFormat("yyyyMMdd");
        Date da = new Date();
        final int datef = Integer.parseInt(df.format(da));
        final int todaymeal = pref.getInt("meal_today", 0);
        DevLog.i("MEAL", Integer.toString(datef)+ " " + Integer.toString(todaymeal));


        new Thread()
        {


            public void run()
            {

                mHandler.post(new Runnable(){

                    public void run()
                    {

                    }
                });
                if(todaymeal !=0 && datef > todaymeal) {

                        try {
                            bfstring = MealLoadHelper.getMeal("cbe.go.kr", "M100000159", "4", "04", "1"); //Get BreakFast Menu Date
                            lunchstring = MealLoadHelper.getMeal("cbe.go.kr", "M100000159", "4", "04", "2"); //Get Lunch Menu Date
                            dinnerstring = MealLoadHelper.getMeal("cbe.go.kr", "M100000159", "4", "04", "3"); //Get Dinner Menu Date
                            bfkcalstring = MealLoadHelper.getKcal("cbe.go.kr", "M100000159", "4", "04", "1"); //Get Kcal Data
                            lunchkcalstring = MealLoadHelper.getKcal("cbe.go.kr", "M100000159", "4", "04", "2"); //Get Kcal Data
                            dinnerkcalstring = MealLoadHelper.getKcal("cbe.go.kr", "M100000159", "4", "04", "3"); //Get Kcal Data
                        } catch (Exception e) {
                        }

                } else if(todaymeal == 0) {
                    try {
                        bfstring = MealLoadHelper.getMeal("cbe.go.kr", "M100000159", "4", "04", "1"); //Get BreakFast Menu Date
                        lunchstring = MealLoadHelper.getMeal("cbe.go.kr", "M100000159", "4", "04", "2"); //Get Lunch Menu Date
                        dinnerstring = MealLoadHelper.getMeal("cbe.go.kr", "M100000159", "4", "04", "3"); //Get Dinner Menu Date
                        bfkcalstring = MealLoadHelper.getKcal("cbe.go.kr", "M100000159", "4", "04", "1"); //Get Kcal Data
                        lunchkcalstring = MealLoadHelper.getKcal("cbe.go.kr", "M100000159", "4", "04", "2"); //Get Kcal Data
                        dinnerkcalstring = MealLoadHelper.getKcal("cbe.go.kr", "M100000159", "4", "04", "3"); //Get Kcal Data
                    } catch (Exception e) {
                    }

                }

                mHandler.post(new Runnable()
                {
                    public void run() {

                        if(todaymeal !=0 && datef > todaymeal) {

                            for (int i = 1; i <= 5; i++) {
                                edit.putString("meal_bf_" + i, bfstring[i]);
                                edit.putString("meal_bf_kcal_" + i, bfkcalstring[i]);
                                edit.putString("meal_lunch_" + i, lunchstring[i]);
                                edit.putString("meal_lunch_kcal_" + i, lunchkcalstring[i]);
                                edit.putString("meal_dinner_" + i, dinnerstring[i]);
                                edit.putString("meal_dinner_kcal_" + i, dinnerkcalstring[i]);
                                edit.commit();

                            }
                        } else if(todaymeal == 0) {
                            for (int i = 1; i <= 5; i++) {
                                edit.putString("meal_bf_" + i, bfstring[i]);
                                edit.putString("meal_bf_kcal_" + i, bfkcalstring[i]);
                                edit.putString("meal_lunch_" + i, lunchstring[i]);
                                edit.putString("meal_lunch_kcal_" + i, lunchkcalstring[i]);
                                edit.putString("meal_dinner_" + i, dinnerstring[i]);
                                edit.putString("meal_dinner_kcal_" + i, dinnerkcalstring[i]);
                                edit.commit();

                            }
                        }

                        if(todaymeal !=0 && datef > todaymeal) {
                                edit.putInt("meal_today", datef).apply();
                                finish();
                                startActivity(getIntent());

                        } else if(todaymeal == 0){
                            edit.putInt("meal_today", datef).commit();
                            finish();
                            startActivity(getIntent());
                        }

                        DevLog.d("DONE", "Done Setting Content");
                        //SRL.setRefreshing(false);
                        dialog.dismiss();
                    }
                });

            }
        }.start();
    }
}