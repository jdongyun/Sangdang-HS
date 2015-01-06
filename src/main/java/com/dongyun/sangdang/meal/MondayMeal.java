package com.dongyun.sangdang.meal;

import android.app.Activity;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.dongyun.sangdang.DevLog;
import com.dongyun.sangdang.MealLoadHelper;
import com.dongyun.sangdang.R;
public class MondayMeal extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final String ARG_SECTION_NUMBER = "section_number";
    private String mParam1;
    private String mParam2;
    private OnFragmentInteractionListener mListener;
    String[] bfstring = new String[7];
    String[] lunchstring = new String[7];
    String[] dinnerstring = new String[7];
    String[] bfkcalstring = new String[7];
    String[] lunchkcalstring = new String[7];
    String[] dinnerkcalstring = new String[7];
    String[] bfpeople = new String[7];
    String[] lunchpeople = new String[7];
    String[] dinnerpeople = new String[7];
    TextView BfText;
    TextView LunchText;
    TextView DinnerText;
    SwipeRefreshLayout SRL;

    public static MondayMeal newInstance(int sectionNumber) {
        MondayMeal fragment = new MondayMeal();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }
    public MondayMeal() {
    }


    private final Handler handler = new Handler()
    {
        @Override
        public void handleMessage(Message msg)
        {}
    };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        SRL = (SwipeRefreshLayout)
                inflater.inflate(R.layout.fragment_day_meal, container, false);
        BfText = (TextView)SRL.findViewById(R.id.bftxt);
        LunchText = (TextView)SRL.findViewById(R.id.lunchtxt);
        DinnerText = (TextView)SRL.findViewById(R.id.dinnertxt);
        SRL.setRefreshing(true);
        SRL.setColorSchemeColors(Color.rgb(231, 76, 60),
                Color.rgb(46, 204, 113), Color.rgb(41, 128, 185),
                Color.rgb(241, 196, 15));
        SRL.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                loadMealTask();
            }
        });
        loadMealTask();
        return SRL;
    }

    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnFragmentInteractionListener {
        public void onFragmentInteraction(Uri uri);
    }

    private void loadMealTask(){
        SRL.setRefreshing(true);
        final Handler mHandler = new Handler();
        new Thread()
        {

            public void run()
            {
                mHandler.post(new Runnable(){

                    public void run()
                    {

                    }
                });
                try{
                    bfstring = MealLoadHelper.getMeal("cbe.go.kr", "M100000159", "4", "04", "1"); //Get BreakFast Menu Date
                    lunchstring = MealLoadHelper.getMeal("cbe.go.kr", "M100000159", "4", "04", "2"); //Get Lunch Menu Date
                    dinnerstring = MealLoadHelper.getMeal("cbe.go.kr", "M100000159", "4", "04", "3"); //Get Dinner Menu Date
                    bfkcalstring = MealLoadHelper.getKcal("cbe.go.kr", "M100000159", "4", "04", "1"); //Get Kcal Data
                    lunchkcalstring = MealLoadHelper.getKcal("cbe.go.kr", "M100000159", "4", "04", "2"); //Get Kcal Data
                    dinnerkcalstring = MealLoadHelper.getKcal("cbe.go.kr", "M100000159", "4", "04", "3"); //Get Kcal Data
                    bfpeople = MealLoadHelper.getPeople("cbe.go.kr", "M100000159", "4", "04", "1"); //Get BreakFast People
                    lunchpeople = MealLoadHelper.getPeople("cbe.go.kr", "M100000159", "4", "04", "2"); //Get Lunch People
                    dinnerpeople = MealLoadHelper.getPeople("cbe.go.kr", "M100000159", "4", "04", "3"); //Get Dinner People
                }catch (Exception e){}

                mHandler.post(new Runnable()
                {
                    public void run() {

                        DevLog.d("Setting Text", "Setting Meal Text");
                        DevLog.d("Mon BreakFast", "BreakFast : " + bfstring[1] + "/ Kcal : " + bfkcalstring[1]);
                        DevLog.d("Lunch", "Lunch : " + lunchstring[1] + "/ Kcal : " + lunchkcalstring[1]);
                        DevLog.d("Dinner", "Dinner : " + dinnerstring[1] + "/ Kcal : " + dinnerkcalstring[1]);

                        if (bfstring[1] == null || "".equals(bfstring[1]) || " ".equals(bfstring[1])) {
                            BfText.setText("\n" + getString(R.string.mealnone));
                        } else {
                            BfText.setText("\n" + bfstring[1] + "\n\n" + bfkcalstring[1]);
                        } if (lunchstring[1] == null || "".equals(lunchstring[1]) || " ".equals(lunchstring[1])) {
                            LunchText.setText("\n" + getString(R.string.mealnone));
                        } else {
                            LunchText.setText("\n" + lunchstring[1] + "\n\n" + lunchkcalstring[1]);
                        } if (dinnerstring[1] == null || "".equals(dinnerstring[1]) || " ".equals(dinnerstring[1])) {
                            DinnerText.setText("\n" + getString(R.string.mealnone));
                        } else {
                            DinnerText.setText("\n" + dinnerstring[1] + "\n\n" + dinnerkcalstring[1]);
                        }


                        DevLog.d("DONE", "Done Setting Content");
                        SRL.setRefreshing(false);
                        handler.sendEmptyMessage(0);

                    }
                });
            }
        }.start();
    }
}
