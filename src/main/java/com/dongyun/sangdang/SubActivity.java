package com.dongyun.sangdang;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

public class SubActivity extends Fragment {

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR_PORTRAIT);
        View view = inflater.inflate(R.layout.activity_sub, null);

        view.findViewById(R.id.notice).setOnClickListener(
                new View.OnClickListener() {

                    @Override
                    public void onClick(View arg0) {
                        Intent intent = new Intent(getActivity(),
                                NoticesActivity.class);
                        startActivity(intent);
                    }
                });
        view.findViewById(R.id.notice_parent).setOnClickListener(
                new View.OnClickListener() {

                    @Override
                    public void onClick(View arg0) {
                        Intent intent = new Intent(getActivity(),
                                Notices_Parents.class);
                        startActivity(intent);
                    }
                });
        view.findViewById(R.id.schoolevent).setOnClickListener(
                new View.OnClickListener() {

                    @Override
                    public void onClick(View arg0) {
                        Intent intent = new Intent(getActivity(),
                                SchoolEvent.class);
                        startActivity(intent);
                    }
                });
        view.findViewById(R.id.meal).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                Intent intent = new Intent(getActivity(), Meal.class);
                startActivity(intent);
            }
        });
        view.findViewById(R.id.schedule).setOnClickListener(
                new View.OnClickListener() {

                    @Override
                    public void onClick(View arg0) {
                        Intent intent = new Intent(getActivity(),
                                Schedule.class);
                        startActivity(intent);
                    }
                });
        view.findViewById(R.id.schoolinfo).setOnClickListener(
                new View.OnClickListener() {

                    @Override
                    public void onClick(View arg0) {
                        Intent intent = new Intent(getActivity(),
                                Schoolinfo.class);
                        startActivity(intent);
                    }
                });
        view.findViewById(R.id.schoolintro).setOnClickListener(
                new View.OnClickListener() {

                    @Override
                    public void onClick(View arg0) {
                        Intent intent = new Intent(getActivity(),
                                Schoolintro.class);
                        startActivity(intent);
                    }
                });
        view.findViewById(R.id.appinfo).setOnClickListener(
                new View.OnClickListener() {

                    @Override
                    public void onClick(View arg0) {
                        Intent intent = new Intent(getActivity(),
                                Appinfo.class);
                        startActivity(intent);
                    }
                });
        return view;
    }


}
