package com.robog.monitor;

import android.util.Printer;


/**
 * Created by yuxingdong on 2019-08-17.
 */
public class LooperMonitor implements Printer {

    private boolean mIsStart = true;

    private long mStartTime;

    @Override
    public void println(String x) {

        if (mIsStart) {
            mStartTime = System.currentTimeMillis();
            BlockMonitor.getInstance().getStackSampler().start();
        } else {
            final long endTime = System.currentTimeMillis();

            final long costTime = endTime - mStartTime;
            if (costTime > 100) {
               notifyBlockEvent(endTime);
            }
            BlockMonitor.getInstance().getStackSampler().stop();
        }
        mIsStart = !mIsStart;

    }

    private void notifyBlockEvent(long endTime) {
        BlockMonitor.getInstance()
                .getStackSampler()
                .printThreadStackEntries(mStartTime, endTime);
    }

}
