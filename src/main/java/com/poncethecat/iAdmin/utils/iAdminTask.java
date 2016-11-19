package com.poncethecat.iAdmin.utils;


public abstract class iAdminTask implements Runnable {

    private String taskName;

    private iAdminTask() {
    }

    public iAdminTask(String taskName) {
        this.taskName = taskName;
    }

    /**
     * Repeats this task after some time on certain interval
     *
     * @param delay    The delay between first run
     * @param interval Delay between runs
     * @return false id the task name already exists
     */
    public boolean repeat(long delay, long interval) {
        return Scheduler.scheduleRepeating(this, taskName, delay, interval);
    }

    /**
     * Runs this task later
     *
     * @param delay How much later to run it
     */
    public void delay(long delay) {
        Scheduler.delayTask(this, delay);
    }

    /**
     * Cancels this task
     */
    public void cancel() {
        Scheduler.cancelTask(taskName);
    }
}