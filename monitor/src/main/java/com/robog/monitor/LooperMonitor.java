package com.robog.monitor;

import android.os.SystemClock;
import android.util.Log;
import android.util.Printer;


/**
 * Created by yuxingdong on 2019-08-17.
 */
public class LooperMonitor implements Printer {

    private static final String TAG = "LooperMonitor";

    private boolean isFirst = true;

    private boolean isStart = true;

    private long startTime;

    private long startThreadTime;

    @Override
    public void println(String x) {
        if (isFirst) {
            isFirst = false;
            return;
        }

        if (isStart) {
            startTime = System.currentTimeMillis();
            startThreadTime = SystemClock.currentThreadTimeMillis();
        } else {
            final long endTime = System.currentTimeMillis();
            final long endThreadTime = SystemClock.currentThreadTimeMillis();

            final long costTime = endTime - startTime;
            if (costTime > 100) {
                Log.e(TAG, "println: " + costTime);

            }
        }
        isStart = !isStart;

    }


}
