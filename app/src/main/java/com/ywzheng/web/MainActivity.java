package com.ywzheng.web;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

    }

    @OnClick({R.id.onClick, R.id.onClick2, R.id.onClick3})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.onClick:
                startActivity(new Intent(this, WebViewActivity.class));
                break;
            case R.id.onClick2:
                break;
            case R.id.onClick3:
                break;
        }
    }
}
