package com.eighteengray.commonutilsdemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.eighteengray.commonutillibrary.FontUtils;
import com.eighteengray.commonutillibrary.ScreenUtils;


public class MainActivity extends AppCompatActivity
{
    TextView tv1;
    TextView tv2;
    TextView tv3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tv1 = (TextView) findViewById(R.id.tv1);
        tv2 = (TextView) findViewById(R.id.tv2);
        tv3 = (TextView) findViewById(R.id.tv3);
        float length = FontUtils.getFontlength(tv1.getPaint(), tv1.getText().toString());
        float height = FontUtils.getFontHeight(tv1.getPaint());
        float leading = FontUtils.getFontLeading(tv1.getPaint());
        int screenWidth = ScreenUtils.getScreenWidth(MainActivity.this);
        int screenHeight = ScreenUtils.getScreenHeight(MainActivity.this);
        tv2.setText("length=" + length + "  " + "height=" + height + "  " + "leading=" + leading);
        tv3.setText("screenWidth=" + screenWidth + "  " + "screenHeight=" + screenHeight);
        ListView listView = new ListView(MainActivity.this);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        });

        //监听虚拟键盘是否显示
        // activityRoot是该Activity的最外层Layout
        final View activityRootView = findViewById(R.id.activity_main);
        //给该layout设置监听，监听其布局发生变化事件

        activityRootView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener()
        {
            @Override
            public void onGlobalLayout ()
            {
                //比较activityRootView的根布局与activityRootView的大小，activityRootView因为虚拟键盘的弹出而变小了
                int heightDiff = activityRootView.getRootView().getHeight() - activityRootView.getHeight();
                if (heightDiff > 100)
                {
                    //大小超过100时，一般为显示虚拟键盘事件

                } else
                {
                    //大小小于100时，为不显示虚拟键盘或虚拟键盘隐藏

                }
            }
        });


    }
}
