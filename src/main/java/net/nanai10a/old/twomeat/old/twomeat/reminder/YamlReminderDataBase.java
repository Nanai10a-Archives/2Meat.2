package net.nanai10a.old.twomeat.old.twomeat.reminder;

import net.dv8tion.jda.api.JDA;
import net.nanai10a.old.twomeat.old.twomeat.DiscordListener;
import net.nanai10a.old.twomeat.old.twomeat.Listener;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class YamlReminderDataBase implements ReminderDataBase, Listener {
    private final int id;
    private final JDA jda;

    public  YamlReminderDataBase(int id, JDA jda) {
        this.id = id;
        this.jda = jda;
        DiscordListener.addListener(this);
    }

    @Override
    public void dump(Object data) {

    }

    @Nullable
    @Override
    public Object load() {
        return null;
    }

    @Override
    public void onEvent(@Nonnull Object event) {
        //設定項目がないのでここは保留
    }
}
