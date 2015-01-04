package com.dongyun.sangdang;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;

public class SubActivity extends ActionBarActivity {

    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub);
        findViewById(R.id.notice).setOnClickListener(
                new View.OnClickListener() {

                    @Override
                    public void onClick(View arg0) {
                        Intent intent = new Intent(SubActivity.this,
                                Notices.class);
                        startActivity(intent);
                    }
                });
        findViewById(R.id.notice_parent).setOnClickListener(
                new View.OnClickListener() {

                    @Override
                    public void onClick(View arg0) {
                        Intent intent = new Intent(SubActivity.this,
                                Notices_Parents.class);
                        startActivity(intent);
                    }
                });
        findViewById(R.id.schoolevent).setOnClickListener(
                new View.OnClickListener() {

                    @Override
                    public void onClick(View arg0) {
                        Intent intent = new Intent(SubActivity.this,
                                SchoolEvent.class);
                        startActivity(intent);
                    }
                });
        findViewById(R.id.meal).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                Intent intent = new Intent(SubActivity.this, Meal.class);
                startActivity(intent);
            }
        });
        findViewById(R.id.schedule).setOnClickListener(
                new View.OnClickListener() {

                    @Override
                    public void onClick(View arg0) {
                        Intent intent = new Intent(SubActivity.this,
                                Schedule.class);
                        startActivity(intent);
                    }
                });
        findViewById(R.id.schoolinfo).setOnClickListener(
                new View.OnClickListener() {

                    @Override
                    public void onClick(View arg0) {
                        Intent intent = new Intent(SubActivity.this,
                                Schoolinfo.class);
                        startActivity(intent);
                    }
                });
        findViewById(R.id.schoolintro).setOnClickListener(
                new View.OnClickListener() {

                    @Override
                    public void onClick(View arg0) {
                        Intent intent = new Intent(SubActivity.this,
                                Schoolintro.class);
                        startActivity(intent);
                    }
                });
        findViewById(R.id.appinfo).setOnClickListener(
                new View.OnClickListener() {

                    @Override
                    public void onClick(View arg0) {
                        Intent intent = new Intent(SubActivity.this,
                                Appinfo.class);
                        startActivity(intent);
                    }
                });
    }

}
