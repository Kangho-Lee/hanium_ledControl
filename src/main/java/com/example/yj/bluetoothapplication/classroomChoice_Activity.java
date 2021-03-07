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
public class classroomChoice_Activity extends Activity {

    String univ;
    String building_name;

    protected void onCreate(Bundle saveInstanceState){
        super.onCreate(saveInstanceState);
        setContentView(R.layout.activity_classroom);

        //univ = this.getIntent().getStringExtra("univ").toString();
       // building_name = this.getIntent().getStringExtra("building_name").toString();
        //TextView buildingText = (TextView) findViewById(R.id.buildingText);
       // buildingText.setText(univ + " " + building_name);

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
                Intent intent = new Intent(getApplicationContext(), buildingChoice_Activity.class);
                startActivity(intent);
            }
        });

        final Button btn_101 = (Button) findViewById(R.id.one_one);
        final Button btn_102 = (Button) findViewById(R.id.one_two);

        btn_101.setOnClickListener(new View.OnClickListener() {
           String class_101;

           public void onClick(View v) {
               class_101 = btn_101.getText().toString();
               Intent intent = new Intent(getApplicationContext(), lightChoice_Activity.class);

               //intent.putExtra("univ", univ);
               //intent.putExtra("building_name", building_name);
              intent.putExtra("room_num", class_101);
               startActivity(intent);
           }
       });

        btn_102.setOnClickListener(new View.OnClickListener() {
            String class_102;

            public void onClick(View v) {
                class_102 = btn_102.getText().toString();
                Intent intent = new Intent(getApplicationContext(), lightChoice_Activity.class);

                //intent.putExtra("univ", univ);
                //intent.putExtra("building_name", building_name);
                intent.putExtra("room_num", class_102);
                startActivity(intent);
            }
        });

        Button btnFirst=(Button)findViewById(R.id.FirstF);
        Button btnSecond=(Button)findViewById(R.id.SecondF);
        Button btnThird=(Button)findViewById(R.id.ThirdF);
        Button btnForth=(Button)findViewById(R.id.ForthF);


        View panelFirst=findViewById(R.id.panelFirst);
        panelFirst.setVisibility(View.GONE);

        View panelSecond=findViewById(R.id.panelSecond);
        panelSecond.setVisibility(View.GONE);

        View panelThird=findViewById(R.id.panelThird);
        panelThird.setVisibility(View.GONE);

        View panelForth=findViewById(R.id.panelForth);
        panelForth.setVisibility(View.GONE);


        btnFirst.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                View panelFirst = findViewById(R.id.panelFirst);
                panelFirst.setVisibility(View.VISIBLE);

                View panelSecond = findViewById(R.id.panelSecond);
                panelSecond.setVisibility(View.GONE);

                View panelThird = findViewById(R.id.panelThird);
                panelThird.setVisibility(View.GONE);

                View panelForth = findViewById(R.id.panelForth);
                panelForth.setVisibility(View.GONE);

            }
        });

        btnSecond.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                View panelFirst=findViewById(R.id.panelFirst);
                panelFirst.setVisibility(View.GONE);

                View panelSecond=findViewById(R.id.panelSecond);
                panelSecond.setVisibility(View.VISIBLE);

                View panelThird=findViewById(R.id.panelThird);
                panelThird.setVisibility(View.GONE);

                View panelForth=findViewById(R.id.panelForth);
                panelForth.setVisibility(View.GONE);

            }
        });

        btnThird.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                View panelFirst=findViewById(R.id.panelFirst);
                panelFirst.setVisibility(View.GONE);

                View panelSecond=findViewById(R.id.panelSecond);
                panelSecond.setVisibility(View.GONE);

                View panelThird=findViewById(R.id.panelThird);
                panelThird.setVisibility(View.VISIBLE);

                View panelForth=findViewById(R.id.panelForth);
                panelForth.setVisibility(View.GONE);

            }
        });

        btnForth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                View panelFirst=findViewById(R.id.panelFirst);
                panelFirst.setVisibility(View.GONE);

                View panelSecond=findViewById(R.id.panelSecond);
                panelSecond.setVisibility(View.GONE);

                View panelThird=findViewById(R.id.panelThird);
                panelThird.setVisibility(View.GONE);

                View panelForth=findViewById(R.id.panelForth);
                panelForth.setVisibility(View.VISIBLE);

            }
        });
    }
}
