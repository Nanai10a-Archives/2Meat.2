package net.nanai10a.old.twomeat.old.twomeat.reminder;

import net.dv8tion.jda.api.JDA;
import net.nanai10a.old.twomeat.old.twomeat.CommandListener;
import net.nanai10a.old.twomeat.old.twomeat.ProcessedCommand;

import javax.annotation.Nonnull;

public class Reminder implements CommandListener {
    private final Integer id;
    private final JDA jda;
    private final ReminderTimer reminderTimer;
    private final ReminderSender ReminderSender;
    private final ReminderDataBase reminderDataBase;

    public Reminder(Integer id, JDA jda) {
        jda.getTextChannelById("716658620887990312").sendMessage("2-Reminder(Test):作り出す手前まで来てますよ、いいね。").queue();
        this.id = id;
        this.jda = jda;
        this.reminderTimer = new ReminderTimer(id, jda);
        this.ReminderSender = new DiscordReminderSender(id, jda);
        this.reminderDataBase = new YamlReminderDataBase(id, jda);

        //DiscordCommandListener.addListener(this);
    }

    @Override
    public void onCommandEvent(@Nonnull ProcessedCommand item) {
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
