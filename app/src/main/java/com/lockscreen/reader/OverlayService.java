package com.lockscreen.reader;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.os.Build;
import android.os.IBinder;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;

public class OverlayService extends Service {
    private static final String TAG = "OverlayService";
    private static final int NOTIFICATION_ID = 1001;
    private static final String CHANNEL_ID = "overlay_channel";
    
    public static final String ACTION_START_OVERLAY = "start_overlay";
    public static final String ACTION_STOP_OVERLAY = "stop_overlay";
    
    private WindowManager windowManager;
    private View overlayView;
    private boolean isOverlayShowing = false;
    
    @Override
    public void onCreate() {
        super.onCreate();
        windowManager = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
        createNotificationChannel();
        Log.d(TAG, "Service created");
    }
    
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (intent != null) {
            String action = intent.getAction();
            if (ACTION_START_OVERLAY.equals(action)) {
                startOverlay();
            } else if (ACTION_STOP_OVERLAY.equals(action)) {
                stopOverlay();
            }
        }
        return START_STICKY;
    }
    
    private void startOverlay() {
        if (isOverlayShowing) {
            return;
        }
        
        // 创建前台服务通知
        startForeground(NOTIFICATION_ID, createNotification());
        
        // 创建透明覆盖层
        overlayView = new View(this) {
            @Override
            public boolean onTouchEvent(MotionEvent event) {
                // 拦截所有触摸事件，不做任何处理
                return true;
            }
        };
        
        // 设置窗口参数
        WindowManager.LayoutParams params = new WindowManager.LayoutParams(
            WindowManager.LayoutParams.MATCH_PARENT,
            WindowManager.LayoutParams.MATCH_PARENT,
            Build.VERSION.SDK_INT >= Build.VERSION_CODES.O 
                ? WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY
                : WindowManager.LayoutParams.TYPE_SYSTEM_OVERLAY,
            WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE |
            WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL |
            WindowManager.LayoutParams.FLAG_WATCH_OUTSIDE_TOUCH,
            PixelFormat.TRANSLUCENT
        );
        
        params.gravity = Gravity.TOP | Gravity.LEFT;
        params.x = 0;
        params.y = 0;
        
        try {
            windowManager.addView(overlayView, params);
            isOverlayShowing = true;
            Log.d(TAG, "Overlay started");
        } catch (Exception e) {
            Log.e(TAG, "Failed to add overlay", e);
        }
    }
    
    private void stopOverlay() {
        if (!isOverlayShowing) {
            return;
        }
        
        try {
            if (overlayView != null) {
                windowManager.removeView(overlayView);
                overlayView = null;
            }
            isOverlayShowing = false;
            Log.d(TAG, "Overlay stopped");
        } catch (Exception e) {
            Log.e(TAG, "Failed to remove overlay", e);
        }
        
        // 停止前台服务
        stopForeground(true);
        stopSelf();
    }
    
    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(
                CHANNEL_ID,
                "触屏锁定服务",
                NotificationManager.IMPORTANCE_LOW
            );
            channel.setDescription("触屏锁定功能正在运行");
            channel.setShowBadge(false);
            
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }
    
    private Notification createNotification() {
        return new Notification.Builder(this, CHANNEL_ID)
            .setContentTitle("触屏锁定")
            .setContentText("触屏锁定中，按电源键锁屏后解锁可恢复")
            .setSmallIcon(R.drawable.ic_touch_lock)
            .setOngoing(true)
            .build();
    }
    
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
    
    @Override
    public void onDestroy() {
        super.onDestroy();
        stopOverlay();
        Log.d(TAG, "Service destroyed");
    }
}