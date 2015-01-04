package com.dongyun.sangdang;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;

public class Schoolinfo extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schoolinfo);

        View maps_card = findViewById(R.id.maps_card);
        maps_card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent src = new Intent(Intent.ACTION_VIEW);
                src.setData(Uri
                        .parse("https://maps.google.com/maps?t=m&q=상당고등학교&output=classic"));
                startActivity(src);
            }
        });

    }


}
