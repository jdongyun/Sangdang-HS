package com.dongyun.sangdang.meal;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ScrollView;
import android.widget.TextView;

import com.dongyun.sangdang.R;

public class ThursdayMeal extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final String ARG_SECTION_NUMBER = "section_number";
    private String mParam1;
    private String mParam2;
    private OnFragmentInteractionListener mListener;
    String bfstring;
    String lunchstring;
    String dinnerstring;
    String bfkcalstring;
    String lunchkcalstring;
    String dinnerkcalstring;
    TextView BfText;
    TextView LunchText;
    TextView DinnerText;
    ScrollView sv;

    public static ThursdayMeal newInstance(int sectionNumber) {
        ThursdayMeal fragment = new ThursdayMeal();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    public ThursdayMeal() {
    }


    private final Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
        }
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
        sv = (ScrollView)
                inflater.inflate(R.layout.fragment_day_meal, container, false);
        BfText = (TextView) sv.findViewById(R.id.bftxt);
        LunchText = (TextView) sv.findViewById(R.id.lunchtxt);
        DinnerText = (TextView) sv.findViewById(R.id.dinnertxt);
        loadMealTask();
        return sv;
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

    private void loadMealTask() {
        final Handler mHandler = new Handler();

        mHandler.post(new Runnable() {
            public void run() {
                SharedPreferences pref = getActivity().getSharedPreferences("sangdang", Context.MODE_PRIVATE);
                bfstring = pref.getString("meal_bf_4", ""); //Get BreakFast Menu Date
                lunchstring = pref.getString("meal_lunch_4", ""); //Get Lunch Menu Date
                dinnerstring = pref.getString("meal_dinner_4", ""); //Get Dinner Menu Date
                bfkcalstring = pref.getString("meal_bf_kcal_4", ""); //Get Kcal Data
                lunchkcalstring = pref.getString("meal_lunch_kcal_4", ""); //Get Kcal Data
                dinnerkcalstring = pref.getString("meal_dinner_kcal_4", ""); //Get Kcal Data


                if (bfstring == null || "".equals(bfstring) || " ".equals(bfstring)) {
                    BfText.setText("\n" + getString(R.string.mealnone));
                } else {
                    BfText.setText("\n" + bfstring + "\n\n" + bfkcalstring);
                }
                if (lunchstring == null || "".equals(lunchstring) || " ".equals(lunchstring)) {
                    LunchText.setText("\n" + getString(R.string.mealnone));
                } else {
                    LunchText.setText("\n" + lunchstring + "\n\n" + lunchkcalstring);
                }
                if (dinnerstring == null || "".equals(dinnerstring) || " ".equals(dinnerstring)) {
                    DinnerText.setText("\n" + getString(R.string.mealnone));
                } else {
                    DinnerText.setText("\n" + dinnerstring + "\n\n" + dinnerkcalstring);
                }
            }

        });
        handler.sendEmptyMessage(0);
    }
}
