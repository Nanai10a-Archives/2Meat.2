package net.nanai10a.discord.bot.twomeat.reminder;

import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import javax.annotation.Nonnull;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

public class ReminderTimer extends Object implements Listener {


    private int id;
    private Timer timer = new Timer(true);

    public ReminderTimer(int id) {
        this.id = id;
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
        if (event instanceof MessageReceivedEvent /*|| 追加するときはここに*/) {

        }//elseはいらんやろ、実質ListenerAdapterのそれと一緒だし、空実装だと思えば
        /*
        まず何のeventインスタンスなのか判定してそれにアップキャストする
        text:んでもってコマンド(startWith)を判定する
        そしてidを参照する
        アクションする
         */
    }
}
