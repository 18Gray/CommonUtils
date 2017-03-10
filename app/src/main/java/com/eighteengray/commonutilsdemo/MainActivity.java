package com.eighteengray.commonutilsdemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.eighteengray.commonutilslibrary.DimenUtil;
import com.eighteengray.commonutilslibrary.FontUtils;
import com.eighteengray.commonutilslibrary.ScreenUtils;

import butterknife.BindView;
import butterknife.ButterKnife;


public class MainActivity extends AppCompatActivity
{
    @BindView(R.id.tv1)
    TextView tv1;
    @BindView(R.id.tv2)
    TextView tv2;
    @BindView(R.id.tv3)
    TextView tv3;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

//        tv1 = (TextView) findViewById(R.id.tv1);
//        tv2 = (TextView) findViewById(R.id.tv2);
//        tv3 = (TextView) findViewById(R.id.tv3);
        float length = FontUtils.getFontlength(tv1.getPaint(), tv1.getText().toString());
        float height = FontUtils.getFontHeight(tv1.getPaint());
        float leading = FontUtils.getFontLeading(tv1.getPaint());
        int screenWidth = ScreenUtils.getScreenWidth(MainActivity.this);
        int screenHeight = ScreenUtils.getScreenHeight(MainActivity.this);
        tv2.setText("length=" + length + "  " + "height=" + height + "  " + "leading=" + leading);
        tv3.setText("screenWidth=" + screenWidth + "  " + "screenHeight=" + screenHeight);

        ListView listView = new ListView(MainActivity.this);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {

            }
        });



    }
}
