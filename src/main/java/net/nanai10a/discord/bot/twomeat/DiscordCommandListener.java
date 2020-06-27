package net.nanai10a.discord.bot.twomeat;

import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import javax.annotation.Nonnull;
import java.util.ArrayList;

public class DiscordCommandListener extends DiscordListener implements CommandListener {
    private static ArrayList<Listener> listenerList = new ArrayList<>();

    public static void addListener(@Nonnull Listener listener) {
        listenerList.add(listener);
    }

    @Override
    public void onEvent(Object event) {
        /*
        これimplementsする必要性が感じられないけどJDAの方はしてるんよな、どういうことだろ
         */
    }

    @Override
    public void onMessageReceived(@Nonnull MessageReceivedEvent event) {
        listenerList.iterator().forEachRemaining((Listener listener) -> listener.onEvent(event));
    }
}

