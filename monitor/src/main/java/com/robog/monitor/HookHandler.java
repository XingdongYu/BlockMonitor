package com.robog.monitor;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

import java.lang.reflect.Field;

/**
 * Created by yuxingdong on 2019-08-17.
 */
public class HookHandler {

    private static final String TAG = "HookHandler";

    public static void hook() {
        try {
            final Class<?> activityThreadClass = Class.forName("android.app.ActivityThread");
            final Field field = activityThreadClass.getDeclaredField("sCurrentActivityThread");
            field.setAccessible(true);
            final Object activityThread = field.get(activityThreadClass);

            final Field mH = activityThreadClass.getDeclaredField("mH");
            mH.setAccessible(true);
            final Object handler = mH.get(activityThread);
            Class<?> handlerClass = handler.getClass().getSuperclass();
            final Field callbackField = handlerClass.getDeclaredField("mCallback");
            callbackField.setAccessible(true);
            final HookCallback callback = new HookCallback((Handler) handler);
            callbackField.set(handler, callback);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private static class HookCallback implements Handler.Callback {

        private final Handler mHandler;

        private HookCallback(Handler handler) {
            mHandler = handler;
        }

        @Override
        public boolean handleMessage(Message msg) {
            final long startTime = System.currentTimeMillis();
            mHandler.handleMessage(msg);
            final long costTime = System.currentTimeMillis() - startTime;
            if (costTime > 100) {
                Log.v(TAG, codeToString(msg.what) + " cost " + costTime + "ms");
            }
            return false;
        }
    }

    private static final int BIND_APPLICATION = 110;
    private static final int EXIT_APPLICATION = 111;
    private static final int RECEIVER = 113;
    private static final int CREATE_SERVICE = 114;
    private static final int SERVICE_ARGS = 115;
    private static final int STOP_SERVICE = 116;

    private static final int CONFIGURATION_CHANGED = 118;
    private static final int CLEAN_UP_CONTEXT = 119;
    private static final int GC_WHEN_IDLE = 120;
    private static final int BIND_SERVICE = 121;
    private static final int UNBIND_SERVICE = 122;
    private static final int DUMP_SERVICE = 123;
    private static final int LOW_MEMORY = 124;
    private static final int PROFILER_CONTROL = 127;
    private static final int CREATE_BACKUP_AGENT = 128;
    private static final int DESTROY_BACKUP_AGENT = 129;
    private static final int SUICIDE = 130;
    private static final int REMOVE_PROVIDER = 131;
    private static final int ENABLE_JIT = 132;
    private static final int DISPATCH_PACKAGE_BROADCAST = 133;
    private static final int SCHEDULE_CRASH = 134;
    private static final int DUMP_HEAP = 135;
    private static final int DUMP_ACTIVITY = 136;
    private static final int SLEEPING = 137;
    private static final int SET_CORE_SETTINGS = 138;
    private static final int UPDATE_PACKAGE_COMPATIBILITY_INFO = 139;
    private static final int DUMP_PROVIDER = 141;
    private static final int UNSTABLE_PROVIDER_DIED = 142;
    private static final int REQUEST_ASSIST_CONTEXT_EXTRAS = 143;
    private static final int TRANSLUCENT_CONVERSION_COMPLETE = 144;
    private static final int INSTALL_PROVIDER = 145;
    private static final int ON_NEW_ACTIVITY_OPTIONS = 146;
    private static final int ENTER_ANIMATION_COMPLETE = 149;
    private static final int START_BINDER_TRACKING = 150;
    private static final int STOP_BINDER_TRACKING_AND_DUMP = 151;
    private static final int LOCAL_VOICE_INTERACTION_STARTED = 154;
    private static final int ATTACH_AGENT = 155;
    private static final int APPLICATION_INFO_CHANGED = 156;
    private static final int RUN_ISOLATED_ENTRY_POINT = 158;
    private static final int EXECUTE_TRANSACTION = 159;
    private static final int RELAUNCH_ACTIVITY = 160;

    private static String codeToString(int code) {
        switch (code) {
            case BIND_APPLICATION:
                return "BIND_APPLICATION";
            case EXIT_APPLICATION:
                return "EXIT_APPLICATION";
            case RECEIVER:
                return "RECEIVER";
            case CREATE_SERVICE:
                return "CREATE_SERVICE";
            case SERVICE_ARGS:
                return "SERVICE_ARGS";
            case STOP_SERVICE:
                return "STOP_SERVICE";
            case CONFIGURATION_CHANGED:
                return "CONFIGURATION_CHANGED";
            case CLEAN_UP_CONTEXT:
                return "CLEAN_UP_CONTEXT";
            case GC_WHEN_IDLE:
                return "GC_WHEN_IDLE";
            case BIND_SERVICE:
                return "BIND_SERVICE";
            case UNBIND_SERVICE:
                return "UNBIND_SERVICE";
            case DUMP_SERVICE:
                return "DUMP_SERVICE";
            case LOW_MEMORY:
                return "LOW_MEMORY";
            case PROFILER_CONTROL:
                return "PROFILER_CONTROL";
            case CREATE_BACKUP_AGENT:
                return "CREATE_BACKUP_AGENT";
            case DESTROY_BACKUP_AGENT:
                return "DESTROY_BACKUP_AGENT";
            case SUICIDE:
                return "SUICIDE";
            case REMOVE_PROVIDER:
                return "REMOVE_PROVIDER";
            case ENABLE_JIT:
                return "ENABLE_JIT";
            case DISPATCH_PACKAGE_BROADCAST:
                return "DISPATCH_PACKAGE_BROADCAST";
            case SCHEDULE_CRASH:
                return "SCHEDULE_CRASH";
            case DUMP_HEAP:
                return "DUMP_HEAP";
            case DUMP_ACTIVITY:
                return "DUMP_ACTIVITY";
            case SLEEPING:
                return "SLEEPING";
            case SET_CORE_SETTINGS:
                return "SET_CORE_SETTINGS";
            case UPDATE_PACKAGE_COMPATIBILITY_INFO:
                return "UPDATE_PACKAGE_COMPATIBILITY_INFO";
            case DUMP_PROVIDER:
                return "DUMP_PROVIDER";
            case UNSTABLE_PROVIDER_DIED:
                return "UNSTABLE_PROVIDER_DIED";
            case REQUEST_ASSIST_CONTEXT_EXTRAS:
                return "REQUEST_ASSIST_CONTEXT_EXTRAS";
            case TRANSLUCENT_CONVERSION_COMPLETE:
                return "TRANSLUCENT_CONVERSION_COMPLETE";
            case INSTALL_PROVIDER:
                return "INSTALL_PROVIDER";
            case ON_NEW_ACTIVITY_OPTIONS:
                return "ON_NEW_ACTIVITY_OPTIONS";
            case ENTER_ANIMATION_COMPLETE:
                return "ENTER_ANIMATION_COMPLETE";
            case LOCAL_VOICE_INTERACTION_STARTED:
                return "LOCAL_VOICE_INTERACTION_STARTED";
            case ATTACH_AGENT:
                return "ATTACH_AGENT";
            case APPLICATION_INFO_CHANGED:
                return "APPLICATION_INFO_CHANGED";
            case RUN_ISOLATED_ENTRY_POINT:
                return "RUN_ISOLATED_ENTRY_POINT";
            case EXECUTE_TRANSACTION:
                return "EXECUTE_TRANSACTION";
            case RELAUNCH_ACTIVITY:
                return "RELAUNCH_ACTIVITY";
        }
        return "[" + code + "]";
    }
}
