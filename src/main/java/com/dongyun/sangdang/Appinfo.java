package com.dongyun.sangdang;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

import de.psdev.licensesdialog.LicensesDialog;
import de.psdev.licensesdialog.licenses.ApacheSoftwareLicense20;
import de.psdev.licensesdialog.licenses.License;
import de.psdev.licensesdialog.model.Notice;

public class Appinfo extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appinfo);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // Get app version name from Manifest
        String app_ver = null;
        try {
            app_ver = this.getPackageManager().getPackageInfo(this.getPackageName(), 0).versionName;
        } catch (NameNotFoundException e) {
            e.printStackTrace();
        }

        // Set app version name text
        TextView version = (TextView) findViewById(R.id.version);
        version.setText("Version " + app_ver);

        TextView src = (TextView) findViewById(R.id.src);
        src.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent src = new Intent(Intent.ACTION_VIEW);
                src.setData(Uri.parse("http://github.com/wfmtooopa"));
                startActivity(src);
            }
        });

        TextView readme = (TextView) findViewById(R.id.readme);
        readme.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent readme = new Intent(Appinfo.this, Doc_Readme.class);
                startActivity(readme);
            }
        });

        TextView contrubutors = (TextView) findViewById(R.id.contrubutors);
        contrubutors.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent contrubutors = new Intent(Appinfo.this,
                        Doc_Contributors.class);
                startActivity(contrubutors);
            }
        });
    }

    public void onStop() {
        super.onStop();

    }

    protected void onDestroy() {
        super.onDestroy();

    }

    public void notice(final View view) {
        new LicensesDialog.Builder(this).setNotices(R.raw.licenses).build()
                .show();
    }

    public void copying(final View view) {
        final String name = "Sutaek High School Application for Android";
        final String url = "http://kbj9704.blog.me";
        final String copyright = "Copyright (C) 2013 ByoungJune Kim<kbj9704@gmail.com>";
        final License license = new ApacheSoftwareLicense20();
        final Notice notice = new Notice(name, url, copyright, license);
        new LicensesDialog.Builder(this).setNotices(notice).build().show();
    }



}