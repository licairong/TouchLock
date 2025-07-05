package com.lockscreen.reader;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class ScreenReceiver extends BroadcastReceiver {
    private static final String TAG = "ScreenReceiver";
    
    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        Log.d(TAG, "Received action: " + action);
        
        if (Intent.ACTION_SCREEN_OFF.equals(action)) {
            // 屏幕关闭时，如果当前是锁定状态，记录状态
            if (TouchLockManager.getInstance().isLocked()) {
                Log.d(TAG, "Screen off - touch lock was active");
                // 保存锁定状态，等待解锁后恢复
                ScreenLockStateManager.getInstance().setWasLockedBeforeScreenOff(true);
            }
        } else if (Intent.ACTION_USER_PRESENT.equals(action)) {
            // 用户解锁屏幕后，如果之前是锁定状态，则解除锁定
            if (ScreenLockStateManager.getInstance().wasLockedBeforeScreenOff()) {
                Log.d(TAG, "Screen unlocked - restoring touch unlock");
                TouchLockManager.getInstance().unlock(context);
                ScreenLockStateManager.getInstance().setWasLockedBeforeScreenOff(false);
            }
        }
    }
}