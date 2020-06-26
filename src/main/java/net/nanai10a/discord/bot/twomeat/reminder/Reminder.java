package net.nanai10a.discord.bot.twomeat.reminder;

import javax.annotation.Nonnull;

public class Reminder extends Object implements Listener {
    //private static JDA jda;

    public static void ActiveReminder(/*@Nonnull JDA gettedjda*/) {
        //jda = gettedjda;
        /*
        初期化しましょう
         */
    }

    private Reminder(int num) {
        DiscordListener.addListener(this);
    }

    @Override
    public void onEvent(@Nonnull Object event) {
        /*
        コマンド検知→privateメソッド呼び出し
        Reminderインスタンスを作る(・変更)・削除ができます
         */
    }

    private static void createReminder() {

    }

    private static void changeReminder() {

    }

    private static void deleteReminder() {

    }
}
