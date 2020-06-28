package net.nanai10a.discord.bot.twomeat;

import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import javax.annotation.Nonnull;
import java.util.ArrayList;

public class DiscordCommandListener extends DiscordListener implements CommandListener {
    private static ArrayList<CommandListener> commandListenerList = new ArrayList<>();

    public static void addListener(@Nonnull CommandListener commandListener) {
        commandListenerList.add(commandListener);
    }

    @Override
    public void onEvent(Object event) {}
    @Override
    public void onCommandEvent(@Nonnull ProcessedCommand item) {}
    /*
    これimplementsする必要性が感じられないけどJDAの方はしてるんよな、どういうことだろ
    */

    @Override
    public void onMessageReceived(@Nonnull MessageReceivedEvent event) {
        //java.util.ConcurrentModificationExceptionを吐くことがある
        commandListenerList.iterator().forEachRemaining((CommandListener commandListener) ->
                        commandListener.onCommandEvent(DiscordCommandProcessingService.process(event))
        );
    }
}

