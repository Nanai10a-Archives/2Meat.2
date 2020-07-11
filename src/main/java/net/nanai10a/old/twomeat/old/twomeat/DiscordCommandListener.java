package net.nanai10a.old.twomeat.old.twomeat;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import javax.annotation.Nonnull;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ScheduledThreadPoolExecutor;

public class DiscordCommandListener extends DiscordListener implements CommandListener {
    private CopyOnWriteArrayList<CommandListener> commandListenerList = new CopyOnWriteArrayList<>();
    private ArrayBlockingQueue<MessageReceivedEvent> commandQueue = new ArrayBlockingQueue<>(20,true);
    private ScheduledThreadPoolExecutor runner = new ScheduledThreadPoolExecutor(3);

    public DiscordCommandListener(int id, JDA jda) {

    }

    public void addListener(@Nonnull CommandListener commandListener) {
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

