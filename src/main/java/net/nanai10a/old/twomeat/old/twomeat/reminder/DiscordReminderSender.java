package net.nanai10a.old.twomeat.old.twomeat.reminder;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.nanai10a.old.twomeat.old.twomeat.DiscordListener;
import net.nanai10a.old.twomeat.old.twomeat.Listener;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class DiscordReminderSender implements ReminderSender, Listener {
    private final int id;
    private final JDA jda;

    private MessageChannel targetChannel;

    public DiscordReminderSender(int id, JDA jda) {
        //設定を保持できる方のインスタンスのコンストラクタ
        this.id = id;
        this.jda = jda;
        DiscordListener.addListener(this);
    }

    @Override
    public void Send(@Nullable Object item) {
        if (item instanceof Message) {
            targetChannel.sendMessage((Message) item).queue();
        } else {
            throw new IllegalArgumentException(System.getProperty("line.separator") +
                    "[2:DiscordReminderSender<" +
                    id +
                    ">.Send(item)] | 引数がMessageインスタンスではありません");
        }
    }

    @Override
    public void onEvent(@Nonnull Object event) {
        //設定項目がないのでここは保留
    }
}
