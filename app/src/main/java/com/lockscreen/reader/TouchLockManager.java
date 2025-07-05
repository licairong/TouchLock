package com.lockscreen.reader;

import android.content.Context;
import android.content.Intent;
import android.os.Build;

public class TouchLockManager {
    private static TouchLockManager instance;
    private boolean isLocked = false;
    
    private TouchLockManager() {}
    
    public static TouchLockManager getInstance() {
        if (instance == null) {
            instance = new TouchLockManager();
        }
        return instance;
    }
    
    public boolean isLocked() {
        return isLocked;
    }
    
    public void lock(Context context) {
        if (!isLocked) {
            isLocked = true;
            // 启动覆盖层服务
            Intent intent = new Intent(context, OverlayService.class);
            intent.setAction(OverlayService.ACTION_START_OVERLAY);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                context.startForegroundService(intent);
            } else {
                context.startService(intent);
            }
            
            // 更新通知
            updateNotification(context);
        }
    }
    
    public void unlock(Context context) {
        if (isLocked) {
            isLocked = false;
            // 停止覆盖层服务
            Intent intent = new Intent(context, OverlayService.class);
            intent.setAction(OverlayService.ACTION_STOP_OVERLAY);
            context.startService(intent);
            
            // 更新通知
            updateNotification(context);
        }
    }
    
    private void updateNotification(Context context) {
        Intent intent = new Intent(context, NotificationService.class);
        intent.setAction(NotificationService.ACTION_UPDATE_NOTIFICATION);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            context.startForegroundService(intent);
        } else {
            context.startService(intent);
        }
    }
}