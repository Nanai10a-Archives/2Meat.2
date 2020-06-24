package net.nanai10a.discord.bot.twomeat.reminder;

import java.util.concurrent.ConcurrentHashMap;

public class Id extends Object {
    private static ConcurrentHashMap<Integer, Id> idRepository = new ConcurrentHashMap<>();

    private int num;
    private Reminder reminder;
    private ReminderTimer reminderTimer;


    private Id(int num) {
        this.num = num;
    }

    public static void createId(int num) {
        try {
            if (!idRepository.containsKey(num)) {
                idRepository.putIfAbsent(num, new Id(num));
                //Done通知
            } else {
                //その番号は既に登録されてるよ通知
            }
        } catch (NullPointerException e) {
            e.printStackTrace();
            //nullpointerしました通知
        }
    }

    public static void register(int num, IdCommand command, Object object) {
        /*
        ここは管理インスタンスが増える度にcaseを追加しないといけない。
        もっといい方法があればそっちにする。
         */
        try {
            if (idRepository.containsKey(num)) {
                switch (command) {
                    case REMINDER:
                        idRepository.get(num).reminder = (Reminder) object;
                        //Reminderを登録しました通知
                        break;
                    case REMINDERTIMER:
                        idRepository.get(num).reminderTimer = (ReminderTimer) object;
                        //ReminderTimerを登録しました通知
                        break;
                }
            } else {
                //その番号は存在しないよ通知
            }
        } catch(NullPointerException nulle) {
            nulle.printStackTrace();
            //nullpointerしました通知
        }
    }
    public static void containObjects(int num) {
        try {
            if (idRepository.containsKey(num)) {
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
}

enum IdCommand {
    REMINDER,
    REMINDERTIMER
}
