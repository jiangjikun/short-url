package com.spiderdt.common.tasks;

import java.util.concurrent.TimeUnit;

/**
 * @author Kevin
 * @version V1.0
 * @Title:
 * @Package com.spiderdt.common.tasks
 * @Description:
 * @date 2017/7/6 18:37
 */
public abstract class DelayTask implements Runnable {


    private int delay;
    private TimeUnit timeUnit;

    public DelayTask(int delay, TimeUnit timeUnit) {
        this.delay = delay;
        this.timeUnit = timeUnit;
    }

    public int getDelay() {
        return delay;
    }

    public TimeUnit getTimeUnit() {
        return timeUnit;
    }

    @Override
    public String toString() {
        return "DelayTask{" +
                "delay=" + delay +
                ", timeUnit=" + timeUnit +
                '}';
    }
}
