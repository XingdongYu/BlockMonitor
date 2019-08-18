package com.robog.monitor;

import android.os.Looper;

/**
 * Created by yuxingdong on 2019-08-18.
 */
public class BlockMonitor {

    private final StackSampler mStackSampler;

    private static volatile BlockMonitor INSTANCE = null;

    public static void init() {
        if (INSTANCE == null) {
            synchronized (BlockMonitor.class) {
                if (INSTANCE == null) {
                    INSTANCE = new BlockMonitor();
                }
            }
        }
    }

    public static BlockMonitor getInstance() {
        return INSTANCE;
    }

    private BlockMonitor() {
        mStackSampler = new StackSampler(
                Looper.getMainLooper().getThread(), 1000);

        Looper.getMainLooper().setMessageLogging(new LooperMonitor());

    }

    public StackSampler getStackSampler() {
        return mStackSampler;
    }
}
