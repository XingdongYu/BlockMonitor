package com.robog.blockmonitor;

import android.app.Application;
import android.content.Context;
import com.robog.monitor.BlockMonitor;

/**
 * Created by yuxingdong on 2019-08-17.
 */
public class App extends Application {

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        BlockMonitor.init();
    }
}
