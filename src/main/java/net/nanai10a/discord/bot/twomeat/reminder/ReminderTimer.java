package net.nanai10a.discord.bot.twomeat.reminder;

import net.dv8tion.jda.api.events.GenericEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.util.Date;
import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ScheduledThreadPoolExecutor;

public class ReminderTimer extends ListenerAdapter {


    private int id;
    private Timer timer = new Timer(true);
    //private HashMap<String, >

    public ReminderTimer(int id) {
        this.id = id;
        //DiscordEventListener.addListener(this);
    }
    public void addSchedule(TimerTask task, Date date) {
        timer.schedule(task, date);
    }
    public void changeSchedule() {
        //timer.schedule();
    }
    public void deleteSchedule() {

    }

    //@Override
    public void EventAction(GenericEvent event, DiscordEvent discordEvent, Object... others) {

    }
}

class ReminderTimerTask extends TimerTask {


    public ReminderTimerTask(Notification notification, Object information) {

    }

    @Override
    public void run() {

    }
}