package com.dongyun.sangdang;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;

import com.gc.materialdesign.widgets.Dialog;

public class MainActivity extends ActionBarActivity {
    Utils utils;

    @SuppressLint("NewApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        utils = new Utils(this);
        utils.registerAlarm();

        getSupportActionBar().setTitle("");
        findViewById(R.id.homepage).setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                Intent src = new Intent(Intent.ACTION_VIEW);
                src.setData(Uri.parse("http://www.sangdang.hs.kr"));
                startActivity(src);
            }
        });
        findViewById(R.id.allmenu).setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                Intent intent = new Intent(MainActivity.this, SubActivity.class);
                startActivity(intent);
            }
        });
        findViewById(R.id.appinfo).setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                Intent intent = new Intent(MainActivity.this, Appinfo.class);
                startActivity(intent);
            }
        });
    }

    // 하드웨어 뒤로가기버튼 이벤트 설정.
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        switch (keyCode) {
            // 하드웨어 뒤로가기 버튼에 따른 이벤트 설정
            case KeyEvent.KEYCODE_BACK:

                Dialog dialog = new Dialog(MainActivity.this, "종료", "어플리케이션을 종료하시겠습니까?");
                dialog.setOnAcceptButtonClickListener(new OnClickListener() {

                    @Override
                    public void onClick(View v) {

                        android.os.Process.killProcess(android.os.Process.myPid());

                    }
                });
                dialog.setOnCancelButtonClickListener(new OnClickListener() {

                    @Override
                    public void onClick(View v) {

                    }
                });
                dialog.show();

            default:
                break;
        }
        return super.onKeyDown(keyCode, event);
    }


}
