package net.nanai10a.old.twomeat.old.twomeat.reminder;

import net.dv8tion.jda.api.JDA;
import net.nanai10a.old.twomeat.old.twomeat.CommandListener;
import net.nanai10a.old.twomeat.old.twomeat.ProcessedCommand;

import javax.annotation.Nonnull;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

public class ReminderTimer implements CommandListener {
    private final Integer id;
    private final JDA jda;
    private final Timer timer = new Timer(true);

    public ReminderTimer(Integer id, JDA jda) {
        this.id = id;
        this.jda = jda;
        //DiscordCommandListener.addListener(this);
    }

    private void addSchedule(TimerTask task, Date date) {
        timer.schedule(task, date);
    }
    private void changeSchedule() {
        //timer.schedule();
    }
    private void deleteSchedule() {

    }

    @Override
    public void onCommandEvent(@Nonnull ProcessedCommand item) {

    }
}

class ReminderTimerTask extends TimerTask {

    public ReminderTimerTask() {

    }

    @Override
    public void run() {

    }
}