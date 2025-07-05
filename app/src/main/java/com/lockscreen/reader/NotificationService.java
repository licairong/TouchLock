package com.lockscreen.reader;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.os.IBinder;
import android.util.Log;

public class NotificationService extends Service {
    private static final String TAG = "NotificationService";
    private static final int NOTIFICATION_ID = 1001;
    private static final String CHANNEL_ID = "touch_lock_channel";
    
    public static final String ACTION_START_NOTIFICATION = "start_notification";
    public static final String ACTION_STOP_NOTIFICATION = "stop_notification";
    public static final String ACTION_TOGGLE_LOCK = "toggle_lock";
    public static final String ACTION_UPDATE_NOTIFICATION = "update_notification";
    
    private NotificationManager notificationManager;
    private boolean isServiceRunning = false;
    private ScreenReceiver screenReceiver;
    
    @Override
    public void onCreate() {
        super.onCreate();
        notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        createNotificationChannel();
        registerScreenReceiver();
        Log.d(TAG, "Notification service created");
    }
    
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (intent != null) {
            String action = intent.getAction();
            if (ACTION_START_NOTIFICATION.equals(action)) {
                startNotification();
            } else if (ACTION_STOP_NOTIFICATION.equals(action)) {
                stopNotification();
            } else if (ACTION_TOGGLE_LOCK.equals(action)) {
                toggleLock();
            } else if (ACTION_UPDATE_NOTIFICATION.equals(action)) {
                updateNotification();
            }
        }
        return START_STICKY;
    }
    
    private void startNotification() {
        if (!isServiceRunning) {
            try {
                Notification notification = createNotification();
                startForeground(NOTIFICATION_ID, notification);
                isServiceRunning = true;
                Log.d(TAG, "Notification started successfully");
            } catch (Exception e) {
                Log.e(TAG, "Failed to start notification", e);
            }
        } else {
            Log.d(TAG, "Notification already running");
        }
    }
    
    private void stopNotification() {
        if (isServiceRunning) {
            stopForeground(true);
            isServiceRunning = false;
            stopSelf();
            Log.d(TAG, "Notification stopped");
        }
    }
    
    private void toggleLock() {
        TouchLockManager manager = TouchLockManager.getInstance();
        if (manager.isLocked()) {
            manager.unlock(this);
        } else {
            manager.lock(this);
        }
        updateNotification();
        Log.d(TAG, "Lock toggled to: " + manager.isLocked());
    }
    
    private void updateNotification() {
        if (isServiceRunning) {
            notificationManager.notify(NOTIFICATION_ID, createNotification());
        }
    }
    
    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(
                CHANNEL_ID,
                "触屏锁定控制",
                NotificationManager.IMPORTANCE_LOW
            );
            channel.setDescription("触屏锁定功能控制面板");
            channel.setShowBadge(false);
            channel.setSound(null, null);
            notificationManager.createNotificationChannel(channel);
        }
    }
    
    private Notification createNotification() {
        boolean isLocked = TouchLockManager.getInstance().isLocked();
        Log.d(TAG, "Creating notification, isLocked: " + isLocked);
        
        // 创建切换按钮的Intent
        Intent toggleIntent = new Intent(this, NotificationService.class);
        toggleIntent.setAction(ACTION_TOGGLE_LOCK);
        int flags = Build.VERSION.SDK_INT >= Build.VERSION_CODES.M ? PendingIntent.FLAG_IMMUTABLE : 0;
        PendingIntent togglePendingIntent = PendingIntent.getService(this, 1, toggleIntent, flags);
        
        // 创建打开应用的Intent
        Intent openIntent = new Intent(this, MainActivity.class);
        PendingIntent openPendingIntent = PendingIntent.getActivity(this, 2, openIntent, flags);
        
        // 创建停止通知的Intent
        Intent stopIntent = new Intent(this, NotificationService.class);
        stopIntent.setAction(ACTION_STOP_NOTIFICATION);
        PendingIntent stopPendingIntent = PendingIntent.getService(this, 3, stopIntent, flags);
        
        // 兼容不同Android版本的通知构建
        Notification.Builder builder;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            builder = new Notification.Builder(this, CHANNEL_ID);
        } else {
            builder = new Notification.Builder(this);
        }
        
        builder.setContentTitle("触屏锁定控制")
            .setContentText(isLocked ? "触屏已锁定 - 点击解锁" : "触屏未锁定 - 点击锁定")
            .setSmallIcon(R.drawable.ic_touch_lock)
            .setContentIntent(openPendingIntent)
            .setOngoing(true);
            
        // 添加操作按钮
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            builder.addAction(
                R.drawable.ic_touch_lock,
                isLocked ? "解锁触屏" : "锁定触屏",
                togglePendingIntent
            ).addAction(
                android.R.drawable.ic_menu_close_clear_cancel,
                "关闭",
                stopPendingIntent
            );
        }
        
        // 设置优先级（Android 7.1以下）
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.O) {
            builder.setPriority(Notification.PRIORITY_LOW);
        }
            
        return builder.build();
    }
    
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
    
    private void registerScreenReceiver() {
        if (screenReceiver == null) {
            screenReceiver = new ScreenReceiver();
            IntentFilter filter = new IntentFilter();
            filter.addAction(Intent.ACTION_SCREEN_OFF);
            filter.addAction(Intent.ACTION_USER_PRESENT);
            registerReceiver(screenReceiver, filter);
            Log.d(TAG, "Screen receiver registered dynamically");
        }
    }
    
    private void unregisterScreenReceiver() {
        if (screenReceiver != null) {
            try {
                unregisterReceiver(screenReceiver);
                screenReceiver = null;
                Log.d(TAG, "Screen receiver unregistered");
            } catch (IllegalArgumentException e) {
                Log.w(TAG, "Screen receiver was not registered");
            }
        }
    }
    
    @Override
    public void onDestroy() {
        super.onDestroy();
        unregisterScreenReceiver();
        isServiceRunning = false;
        Log.d(TAG, "Notification service destroyed");
    }
}