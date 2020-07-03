package net.nanai10a.twomeat;

import net.dv8tion.jda.api.JDA;
import net.nanai10a.twomeat.reminder.DiscordReminderSender;
import net.nanai10a.twomeat.reminder.Reminder;
import net.nanai10a.twomeat.reminder.ReminderTimer;
import net.nanai10a.twomeat.reminder.YamlReminderDataBase;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.concurrent.ConcurrentHashMap;

public class IdController implements Listener {
    private static ConcurrentHashMap<Integer, Id> idRepository = new ConcurrentHashMap<>();
    private static JDA jda;

    public static void createId(int id) {
        try {
            if (!idRepository.containsKey(id)) {
                idRepository.putIfAbsent(id, new Id(id));
                //Done通知
            } else {
                //その番号は既に登録されてるよ通知
            }
        } catch (NullPointerException e) {
            e.printStackTrace();
            //nullpointerしました通知
        }
    }

    public static void register(@Nonnull int id, @Nonnull IdCommand command, @Nullable Object object) {
        /*
        ここは管理インスタンスが増える度にcaseを追加しないといけない。
        もっといい方法があればそっちにする。
        ↓
        Idインスタンスの方に移った。
         */
        try {
            if (idRepository.containsKey(id)) {
                idRepository.get(id).registerInstance(command, object);
            } else {
                //その番号は存在しないよ通知
            }
        } catch(NullPointerException nulle) {
            nulle.printStackTrace();
            //nullpointerしました通知
        }
    }
    public static void containObjects(int id) {
        try {
            if (idRepository.containsKey(id)) {
                /*
                ここで何が登録されているのかをbooleanで出力する
                 */
            } else {
                //その番号は存在しないよ通知
            }
        } catch(NullPointerException nulle) {
            nulle.printStackTrace();
            //nullpointerしました通知
        }
    }

    @Override
    public void onEvent(@Nonnull Object event) {

    }
}

class Id extends Object implements Listener {
    private int id;
    private Reminder reminder;
    private ReminderTimer reminderTimer;
    private DiscordReminderSender discordReminderSender;
    private YamlReminderDataBase yamlReminderDataBase;

    public Id(int id) {
        this.id = id;
    }

    public Object getInstance(@Nonnull IdCommand command) {
        switch (command) {
            case REMINDER:
                return reminder;
            case REMINDERTIMER:
                return reminderTimer;
            case YAMLREMINDERDATABASE:
                return yamlReminderDataBase;
            case DISCORDREMINDERSENDER:
                return discordReminderSender;
        }
        return new Object();//ここどうにかならんのか
    }

    public void registerInstance(@Nonnull IdCommand command, @Nonnull Object instance) {
        switch (command) {
            case REMINDER:
                this.reminder = (Reminder)instance;
            case REMINDERTIMER:
                this.reminderTimer = (ReminderTimer)instance;
            case YAMLREMINDERDATABASE:
                this.yamlReminderDataBase = (YamlReminderDataBase)instance;
            case DISCORDREMINDERSENDER:
                this.discordReminderSender = (DiscordReminderSender)instance;
        }
    }

    @Override
    public void onEvent(@Nonnull Object event) {

    }
}

enum IdCommand {
    REMINDER,
    REMINDERTIMER,
    DISCORDREMINDERSENDER,
    YAMLREMINDERDATABASE
}