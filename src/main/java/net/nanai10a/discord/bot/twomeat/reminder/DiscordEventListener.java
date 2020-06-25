package net.nanai10a.discord.bot.twomeat.reminder;

import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class DiscordEventListener extends ListenerAdapter {
    private static DiscordEventListener[] listeners = new DiscordEventListener[0];
    static {
        //これって果たしてどうなんだろうか
        //addListenerでの初回nullpointer回避のためなんだけど…
        listeners[0] = new DiscordEventListener();
    }

    public static void addListener(DiscordEventListener... listeners) {
        for (DiscordEventListener listener : listeners) {
            listeners[listeners.length] = listener;
        }
    }

    /*
    以下にEventListenerとしての役割が実装される。
     */
}
