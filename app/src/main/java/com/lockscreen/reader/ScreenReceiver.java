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
            // 用户解锁屏幕后，根据设置决定是否自动解除锁定
            if (ScreenLockStateManager.getInstance().wasLockedBeforeScreenOff()) {
                if (!ScreenLockStateManager.getInstance().isKeepLockAfterScreenOff()) {
                    // 如果没有开启"锁屏后保持锁定状态"，则自动解除锁定
                    Log.d(TAG, "Screen unlocked - auto unlock touch lock");
                    TouchLockManager.getInstance().unlock(context);
                } else {
                    // 如果开启了"锁屏后保持锁定状态"，则保持锁定
                    Log.d(TAG, "Screen unlocked - keeping touch lock active");
                }
                ScreenLockStateManager.getInstance().setWasLockedBeforeScreenOff(false);
            }
        }
    }
}