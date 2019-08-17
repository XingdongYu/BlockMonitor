package com.robog.blockmonitor;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        doWork();
    }

    private void doWork() {
        final long startTime = System.currentTimeMillis();
        while ((System.currentTimeMillis() - startTime) < 200) {

        }
    }
}
