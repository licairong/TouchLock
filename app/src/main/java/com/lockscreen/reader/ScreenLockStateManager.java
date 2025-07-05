package com.lockscreen.reader;

public class ScreenLockStateManager {
    private static ScreenLockStateManager instance;
    private boolean wasLockedBeforeScreenOff = false;
    
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
}