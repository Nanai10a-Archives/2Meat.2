package net.nanai10a.discord.bot.twomeat.reminder;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class Reminder extends ListenerAdapter {
    private static JDA jda;

    public static void ActiveReminder(JDA gettedjda) {
        jda = gettedjda;
        /*
        初期化しましょう
         */
    }

    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
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
