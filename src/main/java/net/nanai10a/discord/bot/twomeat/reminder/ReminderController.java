package net.nanai10a.discord.bot.twomeat.reminder;

import net.dv8tion.jda.api.JDA;
import net.nanai10a.discord.bot.twomeat.DiscordListener;
import net.nanai10a.discord.bot.twomeat.Listener;

import javax.annotation.Nonnull;

public class ReminderController implements Listener {
    /*
    一個しかインスタンスを作らないクラス。(但し機能の複製が可能になると複数個出来るが、管理するnumがもう一次元増える…)
     */
    private JDA jda;

    public ReminderController(@Nonnull JDA jda) {
        this.jda = jda;
        DiscordListener.addListener(this);
        /*
        初期化しましょう
         */
        /*
        以下仮実装
         */
    }

    private void createReminder(int id) {
        new Reminder(id, jda);
    }
    @Override
    public void onEvent(@Nonnull Object event) {
    }
}
