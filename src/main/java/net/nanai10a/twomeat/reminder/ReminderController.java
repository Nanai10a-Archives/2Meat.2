package net.nanai10a.twomeat.reminder;

import net.dv8tion.jda.api.JDA;
import net.nanai10a.twomeat.*;

import javax.annotation.Nonnull;
import java.util.regex.Pattern;

public class ReminderController implements CommandListener {
    /*
    一個しかインスタンスを作らないクラス。(但し機能の複製が可能になると複数個出来るが、管理するnumがもう一次元増える…)
     */
    private JDA jda;

    public ReminderController(@Nonnull JDA jda) {
        this.jda = jda;
        //DiscordCommandListener.addListener(this);
        /*
        初期化しましょう
         */
        /*
        以下仮実装
         */
    }

    @Override
    public void onCommandEvent(@Nonnull ProcessedCommand item) {
        if (item.getPatternMatched()) {
            if (item.getServiceName().equals("ReminderController") &&
                    item.getMethodName().equals("createReminder") &&
                    item.getId().equals(0)) {
                if (Pattern.matches("\\d", item.getArg())) {
                    new Reminder(Integer.parseInt(item.getArg()), jda);
                } else {
                    item.getMessageChannel().sendMessage("2-ReminderController:idが不正です").queue();
                }
            }
        }
    }

    private void createReminder(int id) {
        new Reminder(id, jda);
    }
}

/*
if (item.getPatternMatched()) {
            if (item.getServiceName().equals("ReminderController") &&
                    item.getMethodName().equals("createReminder") &&
                    item.getId().equals(0)) {

            }
        }
 */