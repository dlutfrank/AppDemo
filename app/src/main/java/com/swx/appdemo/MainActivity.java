package com.swx.appdemo;

import android.annotation.TargetApi;
import android.app.Activity;
import android.os.Build;
import android.os.Bundle;

public class MainActivity extends Activity {
    public static final String ID = "_ID";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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
