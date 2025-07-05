package com.lockscreen.reader;

import android.content.Intent;
import android.os.Build;
import android.service.quicksettings.Tile;
import android.service.quicksettings.TileService;
import android.util.Log;

public class TouchLockTileService extends TileService {
    private static final String TAG = "TouchLockTileService";
    
    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onTileAdded() {
        super.onTileAdded();
        Log.d(TAG, "Tile added");
        updateTile();
    }

    @Override
    public void onTileRemoved() {
        super.onTileRemoved();
        Log.d(TAG, "Tile removed");
    }

    @Override
    public void onStartListening() {
        super.onStartListening();
        updateTile();
    }

    @Override
    public void onStopListening() {
        super.onStopListening();
    }

    @Override
    public void onClick() {
        super.onClick();
        
        // 检查是否有悬浮窗权限
        if (!PermissionHelper.hasOverlayPermission(this)) {
            // 启动主活动申请权限
            Intent intent = new Intent(this, MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            return;
        }
        
        // 切换锁定状态
        boolean currentState = TouchLockManager.getInstance().isLocked();
        if (currentState) {
            // 当前已锁定，解锁
            TouchLockManager.getInstance().unlock(this);
        } else {
            // 当前未锁定，锁定
            TouchLockManager.getInstance().lock(this);
        }
        
        updateTile();
        
        // 如果启用了锁定，最小化到后台
        if (TouchLockManager.getInstance().isLocked()) {
            // 回到桌面
            Intent homeIntent = new Intent(Intent.ACTION_MAIN);
            homeIntent.addCategory(Intent.CATEGORY_HOME);
            homeIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(homeIntent);
        }
    }

    private void updateTile() {
        Tile tile = getQsTile();
        if (tile != null) {
            boolean isLocked = TouchLockManager.getInstance().isLocked();
            tile.setState(isLocked ? Tile.STATE_ACTIVE : Tile.STATE_INACTIVE);
            tile.setLabel(getString(isLocked ? R.string.tile_active : R.string.tile_inactive));
            tile.updateTile();
        }
    }
}