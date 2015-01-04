package com.dongyun.sangdang;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.TextView;

import com.gc.materialdesign.widgets.Dialog;

import toast.library.meal.MealLibrary;

public class Meal extends ActionBarActivity {

    private ProgressDialog progressDialog;
    TextView bfmon;
    TextView bftue;
    TextView bfwed;
    TextView bfthu;
    TextView bffri;
    TextView lunchmon;
    TextView lunchtue;
    TextView lunchwed;
    TextView lunchthu;
    TextView lunchfri;
    TextView dinnermon;
    TextView dinnertue;
    TextView dinnerwed;
    TextView dinnerthu;
    TextView dinnerfri;
    String[] bmealkcal = new String[7];
    String[] lmealkcal = new String[7];
    String[] dmealkcal = new String[7];
    String[] bfstring = new String[7];
    String[] lunchstring = new String[7];
    String[] dinnerstring = new String[7];

    private final Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meal);if (!isNetworkConnected(this)) {
            new AlertDialog.Builder(this)
                    .setIcon(R.drawable.ic_error)
                    .setTitle("네트워크 연결").setMessage("\n네트워크 연결 상태 확인 후 다시 시도해 주십시요\n")
                    .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            finish();
                        }
                    }).show();
        } else {
        
        findViewById(R.id.mealinfo).setOnClickListener(
                new View.OnClickListener() {

                    @Override
                    public void onClick(View arg0) {
                        Dialog dialog = new Dialog(Meal.this, "알레르기 정보",
                                "①난류\n②우유\n③메밀\n④땅콩\n⑤대두\n⑥밀\n⑦고등어\n⑧게\n⑨새우\n⑩돼지고기\n⑪복숭아\n⑫토마토\n⑬아황산염");
                        dialog.show();
                    }
                });

            bfmon = (TextView) this.findViewById(R.id.bfmon);
            bftue = (TextView) this.findViewById(R.id.bftue);
            bfwed = (TextView) this.findViewById(R.id.bfwed);
            bfthu = (TextView) this.findViewById(R.id.bfthu);
            bffri = (TextView) this.findViewById(R.id.bffri);
            lunchmon = (TextView) this.findViewById(R.id.lunchmon);
            lunchtue = (TextView) this.findViewById(R.id.lunchtue);
            lunchwed = (TextView) this.findViewById(R.id.lunchwed);
            lunchthu = (TextView) this.findViewById(R.id.lunchthu);
            lunchfri = (TextView) this.findViewById(R.id.lunchfri);
            dinnermon = (TextView) this.findViewById(R.id.dinnermon);
            dinnertue = (TextView) this.findViewById(R.id.dinnertue);
            dinnerwed = (TextView) this.findViewById(R.id.dinnerwed);
            dinnerthu = (TextView) this.findViewById(R.id.dinnerthu);
            dinnerfri = (TextView) this.findViewById(R.id.dinnerfri);
            //dinnertext = (TextView)this.findViewById(R.id.dinner);


            final Handler mHandler = new Handler();
            new Thread() {

                public void run() {
                    mHandler.post(new Runnable() {

                        public void run() {
                            String loading = getString(R.string.loading);
                            progressDialog = ProgressDialog.show(Meal.this, "", loading, true);
                        }
                    });
                    bfstring = MealLibrary.getMealNew("cbe.go.kr", "M100000159", "4", "04", "1"); //Get Lunch Menu Date
                    lunchstring = MealLibrary.getMealNew("cbe.go.kr", "M100000159", "4", "04", "2"); //Get Lunch Menu Date
                    dinnerstring = MealLibrary.getMealNew("cbe.go.kr", "M100000159", "4", "04", "3"); //Get Dinner Menu Date
                    bmealkcal = MealLibrary.getKcalNew("cbe.go.kr", "M100000159", "4", "04", "1"); //Get Kcal Data
                    lmealkcal = MealLibrary.getKcalNew("cbe.go.kr", "M100000159", "4", "04", "2"); //Get Kcal Data
                    dmealkcal = MealLibrary.getKcalNew("cbe.go.kr", "M100000159", "4", "04", "3"); //Get Kcal Data


                    mHandler.post(new Runnable() {
                        public void run() {

                            // Array로 순서화
                            TextView[] Bf = {null, bfmon, bftue, bfwed, bfthu, bffri};
                            TextView[] Lunch = {null, lunchmon, lunchtue, lunchwed, lunchthu, lunchfri};
                            TextView[] Dinner = {null, dinnermon, dinnertue, dinnerwed, dinnerthu, dinnerfri};
                            for (int i = 1; i <= 5; i++) {
                                Bf[i].setText(bfstring[i] + "\n" + bmealkcal[i] + "kcal");
                                Lunch[i].setText(lunchstring[i] + "\n" + lmealkcal[i] + "kcal");
                                Dinner[i].setText(dinnerstring[i] + "\n" + dmealkcal[i] + "kcal");

                            }
                            for (int i = 1; i <= 5; i++) {
                                if (bfstring[i] == null || "".equals(bfstring[i]) || " ".equals(bfstring[i])) {
                                    Bf[i].setText(getString(R.string.mealnone));
                                }
                                if (lunchstring[i] == null || "".equals(lunchstring[i]) || " ".equals(lunchstring[i])) {
                                    Lunch[i].setText(getString(R.string.mealnone));
                                }
                                if (dinnerstring[i] == null || "".equals(dinnerstring[i]) || " ".equals(dinnerstring[i])) {
                                    Dinner[i].setText(getString(R.string.mealnone));

                                }

                            }
                            progressDialog.dismiss();
                        }

                    });
                    handler.sendEmptyMessage(0);
                }
            }.start();
        }
    }
    //인터넷 연결 상태 체크
    public boolean isNetworkConnected(Context context){
        boolean isConnected = false;

        ConnectivityManager manager =
                (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo mobile = manager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
        NetworkInfo wifi = manager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);

        if (mobile.isConnected() || wifi.isConnected()){
            isConnected = true;
        }else{
            isConnected = false;
        }
        return isConnected;
    }

}
