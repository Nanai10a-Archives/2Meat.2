package net.nanai10a.discord.bot.twomeat.reminder;

import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

public class ReminderTimer extends ListenerAdapter {


    private int id;
    private Timer timer = new Timer(true);

    public ReminderTimer(int id) {
        this.id = id;
    }

    @Override
    public void onMessageReceived(MessageReceivedEvent event) {

    }

    private void addSchedule(TimerTask task, Date date) {
        timer.schedule(task, date);
    }
    private void changeSchedule() {
        //timer.schedule();
    }
    private void deleteSchedule() {

    }
}
