package net.nanai10a.discord.bot.twomeat.reminder;

import net.dv8tion.jda.api.JDA;
import net.nanai10a.discord.bot.twomeat.CommandProcessingService;
import net.nanai10a.discord.bot.twomeat.DiscordListener;
import net.nanai10a.discord.bot.twomeat.Listener;
import net.nanai10a.discord.bot.twomeat.ProcessedCommand;

import javax.annotation.Nonnull;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

public class ReminderTimer implements Listener {
    private Integer id;
    private JDA jda;
    private Timer timer = new Timer(true);

    public ReminderTimer(Integer id, JDA jda) {
        this.id = id;
        this.jda = jda;
        DiscordListener.addListener(this);
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
    public void onEvent(@Nonnull Object event) {
        ProcessedCommand processedCommand = CommandProcessingService.process(event, "ReminderTimer", id);
            if (processedCommand.judgeMethodName("ReminderTimer")) {
                String arg = processedCommand.getArg();
            } else {

            }
    }//elseはいらんやろ、実質ListenerAdapterのそれと一緒だし、空実装だと思えば
}

class ReminderTimerTask extends TimerTask {

    public ReminderTimerTask() {

    }

    @Override
    public void run() {

    }
}