package com.swx.appdemo;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends Activity {
    public static final String ID = "_ID";
    public static final String TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
    }

    private void init() {
        View view = findViewById(R.id.iv_item_icon);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, SecondActivity.class);
                startActivity(intent);
            }
        });
    }


    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR1)
    private String getInstance(Bundle bundle) {
        String result = null;
        if (bundle != null) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR1) {
                result = bundle.getString(ID, "99010");
            } else {
                result = bundle.getString(ID);
            }
        }
        return result;
    }
}
