package com.land.netcar.min;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.land.netcar.R;
import com.land.netcar.urls.CloseActivityClass;

public class UpdateActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);
        CloseActivityClass.activityList.add(this);
    }
}
