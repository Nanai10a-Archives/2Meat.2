package net.nanai10a.discord.bot.twomeat.reminder;

import net.dv8tion.jda.api.JDA;
import net.nanai10a.discord.bot.twomeat.DiscordListener;
import net.nanai10a.discord.bot.twomeat.Listener;

import javax.annotation.Nonnull;

public class Reminder implements Listener {
    private JDA jda;
    private int id;

    public Reminder(int id, JDA jda) {
        this.id = id;
        this.jda = jda;
        DiscordListener.addListener(this);
        new ReminderTimer(id, jda);
        new DiscordReminderSender(id, jda);
        new YamlReminderDataBase(id, jda);
    }

    @Override
    public void onEvent(@Nonnull Object event) {
        /*
        コマンド検知→privateメソッド呼び出し
        Reminderインスタンスを(変更・)削除ができます
         */
    }

    private void changeReminder() {

    }

    private void deleteReminder() {

    }
}
