package com.apujadas.todolist.resilience.cache;

import java.util.Timer;
import java.util.TimerTask;

public class SlidingCacheExpirationPolicy implements CacheExpirationPolicy {
    private boolean isCacheValid = false;
    private Timer timer = new Timer();

    @Override
    public boolean isValid() {
        return isCacheValid;
    }

    @Override
    public void invalidate() {
        isCacheValid = false;
    }

    @Override
    public void newData() {
        isCacheValid = true;
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                invalidate();
            }
        };
        timer.cancel();
        timer = new Timer();
        timer.schedule(task, 60000 * 5);
    }
}
