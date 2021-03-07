package com.example.yj.bluetoothapplication;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

/**
 * Created by 강호 리 on 2016-08-18.
 */
public class buildingChoice_Activity extends Activity {

    String univ;
    public static final String EXTRA_MEMBER_ID = "member_id";

    protected void onCreate(Bundle saveInstanceState){
        super.onCreate(saveInstanceState);
            setContentView(R.layout.activity_building);

       // choose which building you will take
        final Button building1_oh = (Button) findViewById(R.id.OH);
        final Button building1_nth = (Button) findViewById(R.id.NTH);
        final Button building1_nmh = (Button) findViewById(R.id.NMH);
        final Button building1_hdh = (Button) findViewById(R.id.HDH);
        final Button building1_anh = (Button) findViewById(R.id.ANH);

        Button home_btn = (Button) findViewById(R.id.home);
        Button back_btn = (Button) findViewById(R.id.back);

        home_btn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), logo_Activity.class);
                startActivity(intent);
            }
        });

        back_btn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), logo_Activity.class);
                startActivity(intent);
            }
        });


        building1_oh.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                String building_name1;
                building_name1 = building1_oh.getText().toString();

                Intent intent = new Intent(getApplicationContext(), classroomChoice_Activity.class);
                //intent.putExtra("univ", univ);
                //intent.putExtra("building_name", building_name1);

                startActivity(intent);
            }
        });

        building1_nth.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                String building_name2;
                building_name2 = building1_nth.getText().toString();

                Intent intent = new Intent(getApplicationContext(), classroomChoice_Activity.class);
                //intent.putExtra("univ", univ);
                //intent.putExtra("building_name", building_name2);

                startActivity(intent);
            }
        });

        building1_nmh.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                String building_name3;
                building_name3 = building1_nmh.getText().toString();

                Intent intent = new Intent(getApplicationContext(), classroomChoice_Activity.class);
                //intent.putExtra("univ", univ);
                //intent.putExtra("building_name", building_name3);

                startActivity(intent);
            }
        });

        building1_hdh.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                String building_name4;
                building_name4 = building1_hdh.getText().toString();

                Intent intent = new Intent(getApplicationContext(), classroomChoice_Activity.class);
               // intent.putExtra("univ", univ);
                //intent.putExtra("building_name", building_name4);

                startActivity(intent);
            }
        });

        building1_anh.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                String building_name5;
                building_name5 = building1_anh.getText().toString();

                Intent intent = new Intent(getApplicationContext(), classroomChoice_Activity.class);
                //intent.putExtra("univ", univ);
               // intent.putExtra("building_name", building_name5);

                startActivity(intent);
            }
        });

    }

}
