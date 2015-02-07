package com.dongyun.sangdang.fragment;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dongyun.sangdang.R;
import com.dongyun.sangdang.activity.Schoolintro;
import com.dongyun.sangdang.ui.FloatingActionButton;

import javax.annotation.Nullable;

public class Schoolinfo extends Fragment {

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_schoolinfo, null);

        View maps_card = view.findViewById(R.id.maps);
        maps_card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent src = new Intent(Intent.ACTION_VIEW);
                src.setData(Uri
                        .parse("https://maps.google.com/maps?t=m&q=상당고등학교&output=classic"));
                startActivity(src);
            }
        });

        View more_info_card = view.findViewById(R.id.moreinfo);
        more_info_card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent src = new Intent(getActivity(), Schoolintro.class);
                startActivity(src);
            }
        });

        FloatingActionButton fax = (FloatingActionButton) view.findViewById(R.id.fax);
        fax.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:043-294-6145"));
                startActivity(intent);
            }
        });
        FloatingActionButton techersroom = (FloatingActionButton) view.findViewById(R.id.teachers_room);
        techersroom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:043-294-6142"));
                startActivity(intent);
            }
        });
        FloatingActionButton administrationoffice = (FloatingActionButton) view.findViewById(R.id.administration_office);
        administrationoffice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:043-294-6144"));
                startActivity(intent);
            }
        });

        return view;
    }

}
