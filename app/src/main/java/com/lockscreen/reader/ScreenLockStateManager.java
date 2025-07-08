package com.lockscreen.reader;

public class ScreenLockStateManager {
    private static ScreenLockStateManager instance;
    private boolean wasLockedBeforeScreenOff = false;
    private boolean keepLockAfterScreenOff = false;
    
    private ScreenLockStateManager() {}
    
    public static ScreenLockStateManager getInstance() {
        if (instance == null) {
            instance = new ScreenLockStateManager();
        }
        return instance;
    }
    
    public boolean wasLockedBeforeScreenOff() {
        return wasLockedBeforeScreenOff;
    }
    
    public void setWasLockedBeforeScreenOff(boolean wasLocked) {
        this.wasLockedBeforeScreenOff = wasLocked;
    }
    
    public boolean isKeepLockAfterScreenOff() {
        return keepLockAfterScreenOff;
    }
    
    public void setKeepLockAfterScreenOff(boolean keepLock) {
        this.keepLockAfterScreenOff = keepLock;
    }
}