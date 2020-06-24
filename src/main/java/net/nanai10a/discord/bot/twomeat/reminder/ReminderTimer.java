package net.nanai10a.discord.bot.twomeat.reminder;

import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ScheduledThreadPoolExecutor;

public class ReminderTimer extends Object {


    private int id;
    private Timer timer = new Timer(true);

    public ReminderTimer(int id) {
        this.id = id;
    }
    public void addSchedule(TimerTask task, Date date) {
        timer.schedule(task, date);
    }
    public void changeSchedule() {
        //timer.schedule();
    }
    public void deleteSchedule() {

    }
}
