package com.lockscreen.reader;

import android.content.ComponentName;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.service.quicksettings.TileService;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;

public class MainActivity extends AppCompatActivity {
    
    private TextView statusText;
    private Button permissionButton;
    private Button addTileButton;
    private Button stopNotificationButton;
    private SwitchCompat keepLockSwitch;
    private SharedPreferences sharedPreferences;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        statusText = findViewById(R.id.status_text);
        permissionButton = findViewById(R.id.permission_button);
        addTileButton = findViewById(R.id.add_tile_button);
        stopNotificationButton = findViewById(R.id.stop_notification_button);
        keepLockSwitch = findViewById(R.id.keep_lock_switch);
        
        sharedPreferences = getSharedPreferences("TouchLockSettings", MODE_PRIVATE);
        
        permissionButton.setOnClickListener(v -> {
            if (!PermissionHelper.hasOverlayPermission(this)) {
                PermissionHelper.requestOverlayPermission(this);
            } else {
                Toast.makeText(this, "权限已授予", Toast.LENGTH_SHORT).show();
            }
        });
        
        addTileButton.setOnClickListener(v -> {
            // 检查通知权限（Android 13+）
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                if (checkSelfPermission(android.Manifest.permission.POST_NOTIFICATIONS) 
                    != android.content.pm.PackageManager.PERMISSION_GRANTED) {
                    requestPermissions(new String[]{android.Manifest.permission.POST_NOTIFICATIONS}, 1001);
                    return;
                }
            }
            
            Intent intent = new Intent(this, NotificationService.class);
            intent.setAction(NotificationService.ACTION_START_NOTIFICATION);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                startForegroundService(intent);
            } else {
                startService(intent);
            }
            Toast.makeText(this, "通知栏控制已启用", Toast.LENGTH_SHORT).show();
            updateUI();
        });
        
        stopNotificationButton.setOnClickListener(v -> {
            Intent intent = new Intent(this, NotificationService.class);
            intent.setAction(NotificationService.ACTION_STOP_NOTIFICATION);
            startService(intent);
            Toast.makeText(this, "通知栏控制已关闭", Toast.LENGTH_SHORT).show();
            updateUI();
        });
        
        // 设置开关状态
        boolean keepLockEnabled = sharedPreferences.getBoolean("keep_lock_after_screen_off", false);
        keepLockSwitch.setChecked(keepLockEnabled);
        ScreenLockStateManager.getInstance().setKeepLockAfterScreenOff(keepLockEnabled);
        
        keepLockSwitch.setOnCheckedChangeListener((buttonView, isChecked) -> {
            sharedPreferences.edit().putBoolean("keep_lock_after_screen_off", isChecked).apply();
            ScreenLockStateManager.getInstance().setKeepLockAfterScreenOff(isChecked);
        });
        
        updateUI();
    }
    
    @Override
    protected void onResume() {
        super.onResume();
        updateUI();
    }
    
    private void updateUI() {
        boolean hasPermission = PermissionHelper.hasOverlayPermission(this);
        boolean isLocked = TouchLockManager.getInstance().isLocked();
        boolean isNotificationRunning = isNotificationServiceRunning();
        
        if (!hasPermission) {
            statusText.setText("需要悬浮窗权限才能使用触屏锁定功能");
            permissionButton.setText("授予权限");
            permissionButton.setEnabled(true);
            addTileButton.setEnabled(false);
            stopNotificationButton.setVisibility(android.view.View.GONE);
        } else {
            if (isNotificationRunning) {
                statusText.setText(isLocked ? "触屏已锁定\n\n通知栏控制已启用\n在通知栏点击按钮切换锁定状态" : "触屏未锁定\n\n通知栏控制已启用\n在通知栏点击「锁定触屏」按钮");
                addTileButton.setText("通知栏控制已启用");
                addTileButton.setEnabled(false);
                stopNotificationButton.setVisibility(android.view.View.VISIBLE);
            } else {
                statusText.setText("使用方法：\n1. 点击「启用通知栏控制」\n2. 在通知栏使用快捷按钮控制触屏锁定\n3. 阅读时一键锁定，不需要的时候一键解锁");
                addTileButton.setText("启用通知栏控制");
                addTileButton.setEnabled(true);
                stopNotificationButton.setVisibility(android.view.View.GONE);
            }
            permissionButton.setText("权限已授予");
            permissionButton.setEnabled(false);
        }
    }
    
    private boolean isNotificationServiceRunning() {
        // 简单检查：通过尝试启动更新通知来判断服务是否运行
        // 实际应用中可以通过其他方式检查服务状态
        return TouchLockManager.getInstance().isLocked(); // 临时实现
    }
}