package com.ywzheng.web;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private String mUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.onClick).setOnClickListener(this);
        findViewById(R.id.onClick2).setOnClickListener(this);
        findViewById(R.id.onClick3).setOnClickListener(this);

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.onClick:
                mUrl = "http://gou.jd.com";
                WebViewActivity.launch(this, mUrl);
                break;
            case R.id.onClick2:
                mUrl = "file:///android_asset/intro.html";
                WebViewActivity.launch(this, mUrl);
                break;
            default:
                break;
        }
    }
}
